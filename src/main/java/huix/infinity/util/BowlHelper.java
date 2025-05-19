package huix.infinity.util;

import huix.infinity.common.world.item.IFWItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BowlHelper {

    /**
     * 尝试使用空碗接水，成功则返回 ifw:water_bowl（每次只处理一个）
     */
    public static ItemStack tryPickupWater(Level level, Player player, InteractionHand hand) {
        ItemStack handItem = player.getItemInHand(hand);

        // 确保当前物品是空碗（如 Items.BOWL）
        if (!isEmptyBowl(handItem)) {
            return ItemStack.EMPTY;
        }

        BlockHitResult result = getPlayerPOVHitResult(level, player);
        if (result.getType() != HitResult.Type.BLOCK) {
            return ItemStack.EMPTY;
        }

        BlockPos hitPos = result.getBlockPos();
        FluidState fluidState = level.getBlockState(hitPos).getFluidState();

        // 判断是否是水源
        if (fluidState.is(Fluids.WATER) && fluidState.isSource()) {
            // 创建一个水碗
            ItemStack waterBowl = new ItemStack(IFWItems.water_bowl.get());

            // 播放音效
            player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);

            // 统计信息
            Item item = level.getBlockState(hitPos).getBlock().asItem();
            if (item != Items.AIR) {
                player.awardStat(Stats.ITEM_USED.get(item));
            }

            // 触发事件
            level.gameEvent(player, GameEvent.FLUID_PICKUP, hitPos);

            // 消耗一个空碗
            if (!player.isCreative()) {
                handItem.shrink(1); // 只消耗一个
            }

            return waterBowl;
        }

        return ItemStack.EMPTY;
    }

    /**
     * 判断是否是空碗（例如原版木碗）
     */
    public static boolean isEmptyBowl(ItemStack stack) {
        return stack.is(Items.BOWL);
    }

    /**
     * 获取玩家视角下的命中结果
     */
    private static BlockHitResult getPlayerPOVHitResult(Level level, Player player) {
        return net.minecraft.world.item.Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
    }
}