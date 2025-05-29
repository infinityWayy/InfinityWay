package huix.infinity.common.world.item;

import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class ShearsTool extends IFWTieredItem {
    public ShearsTool(IFWTier tier, Properties properties) {
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
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = level.getBlockState(blockpos).getBlock();
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        if (Screen.hasControlDown() && block instanceof GrowingPlantHeadBlock growingplantheadblock && !growingplantheadblock.isMaxAge(blockstate)) {
            if (player instanceof ServerPlayer)
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
            level.playSound(player, blockpos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            BlockState blockstate1 = growingplantheadblock.getMaxAgeState(blockstate);
            level.setBlockAndUpdate(blockpos, blockstate1);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), blockstate1));
            if (player != null) itemstack.hurtAndBreak(20, player, LivingEntity.getSlotForHand(context.getHand()));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (!level.isClientSide && blockstate.is(IFWBlockTags.SHEARS_EFFECTIVE) && !player.getCooldowns().isOnCooldown(itemstack.getItem())) {
            level.removeBlock(blockpos, false);
            Block.popResource(level, blockpos, new ItemStack(block));
            player.swing(context.getHand());
            player.getCooldowns().addCooldown(itemstack.getItem(), 20);
            player.awardStat(Stats.BLOCK_MINED.get(block), 1);
            player.causeFoodExhaustion(0.01F);
            level.playSound(null, blockpos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            itemstack.hurtAndBreak(20, player, LivingEntity.getSlotForHand(context.getHand()));
            return InteractionResult.SUCCESS;
        } else

            return super.useOn(context);
    }
        public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
            if (entity instanceof Shearable shearable && shearable.readyForShearing()) {
                if (!player.level().isClientSide) {
                    shearable.shear(SoundSource.PLAYERS);
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
            return InteractionResult.PASS;
        }

    }
