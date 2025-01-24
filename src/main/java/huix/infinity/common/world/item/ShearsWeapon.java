package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class ShearsWeapon extends IFWTieredItem {
    public ShearsWeapon(IFWTier tier, Properties properties) {
        super(tier, 2, properties);
    }

    public static Tool createToolProperties() {
        return new Tool(
            List.of(
                Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F),
                Tool.Rule.overrideSpeed(BlockTags.LEAVES, 15.0F),
                Tool.Rule.overrideSpeed(BlockTags.WOOL, 5.0F),
                Tool.Rule.overrideSpeed(List.of(Blocks.VINE, Blocks.GLOW_LICHEN), 2.0F)
            ),
            1.0F,
            20
        );
    }

    @Override
    public float getReachBonus() {
        return 0.5F;
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return 1.0F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity) {
        if (!level.isClientSide && !blockState.is(BlockTags.FIRE)) {
            itemStack.hurtAndBreak(1, livingEntity, EquipmentSlot.MAINHAND);
        }

        return blockState.is(BlockTags.LEAVES)
            || blockState.is(Blocks.COBWEB)
            || blockState.is(Blocks.SHORT_GRASS)
            || blockState.is(Blocks.FERN)
            || blockState.is(Blocks.DEAD_BUSH)
            || blockState.is(Blocks.HANGING_ROOTS)
            || blockState.is(Blocks.VINE)
            || blockState.is(Blocks.TRIPWIRE)
            || blockState.is(BlockTags.WOOL);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.getBlock() instanceof GrowingPlantHeadBlock growingplantheadblock && !growingplantheadblock.isMaxAge(blockstate)) {
            Player player = context.getPlayer();
            ItemStack itemstack = context.getItemInHand();
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
            }

            level.playSound(player, blockpos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            BlockState blockstate1 = growingplantheadblock.getMaxAgeState(blockstate);
            level.setBlockAndUpdate(blockpos, blockstate1);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), blockstate1));
            if (player != null) {
                itemstack.hurtAndBreak(20, player, LivingEntity.getSlotForHand(context.getHand()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }
}
