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

    public static ItemStack tryPickupWater(Level level, Player player, InteractionHand hand) {
        ItemStack handItem = player.getItemInHand(hand);

        if (!isEmptyBowl(handItem)) {
            return ItemStack.EMPTY;
        }

        BlockHitResult result = getPlayerPOVHitResult(level, player);
        if (result.getType() != HitResult.Type.BLOCK) {
            return ItemStack.EMPTY;
        }

        BlockPos hitPos = result.getBlockPos();
        FluidState fluidState = level.getBlockState(hitPos).getFluidState();

        if (fluidState.is(Fluids.WATER) && fluidState.isSource()) {
            ItemStack waterBowl = new ItemStack(IFWItems.water_bowl.get());
            player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
            Item item = level.getBlockState(hitPos).getBlock().asItem();

            if (item != Items.AIR) {
                player.awardStat(Stats.ITEM_USED.get(item));
            }

            level.gameEvent(player, GameEvent.FLUID_PICKUP, hitPos);

            if (!player.isCreative()) {
                handItem.shrink(1);
            }

            return waterBowl;
        }

        return ItemStack.EMPTY;
    }

    public static boolean isEmptyBowl(ItemStack stack) {
        return stack.is(Items.BOWL);
    }

    private static BlockHitResult getPlayerPOVHitResult(Level level, Player player) {
        return Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
    }
}