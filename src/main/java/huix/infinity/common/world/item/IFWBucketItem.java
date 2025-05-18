package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.extension.func.BucketPickupExtension;
import huix.infinity.util.BucketHelper;
import huix.infinity.util.WorldHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class IFWBucketItem extends BucketItem {
    private final IFWTier tier;
    public IFWBucketItem(Fluid content, IFWTier tier, Properties properties) {
        super(content, properties.stacksTo(1));
        this.tier = tier;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack handItem = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(
                level, player, this.content == Fluids.EMPTY ? (Screen.hasControlDown() ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.ANY) : ClipContext.Fluid.NONE
        );
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(handItem);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(handItem);
        } else {
            BlockPos hitPos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = hitPos.relative(direction);
            if (!level.mayInteract(player, hitPos) || !player.mayUseItemAt(blockpos1, direction, handItem)) {
                return InteractionResultHolder.fail(handItem);
            } else if (this.content == Fluids.EMPTY) {
                BlockState hitBlock = level.getBlockState(hitPos);
                if (hitBlock.getBlock() instanceof BucketPickupExtension pickup) {
                    ItemStack pickupBlock = pickup.ifw_pickupBlock(player, level, hitPos, hitBlock, this.tier());
                    if (!pickupBlock.isEmpty()) {
                        player.awardStat(Stats.ITEM_USED.get(this));
                        pickup.getPickupSound(hitBlock).ifPresent(event -> player.playSound(event, 1.0F, 1.0F));
                        level.gameEvent(player, GameEvent.FLUID_PICKUP, hitPos);
                        ItemStack filledItem = ItemUtils.createFilledResult(handItem, player, pickupBlock);
                        if (!level.isClientSide) {
                            CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, pickupBlock);
                        }
                        return InteractionResultHolder.sidedSuccess(filledItem, level.isClientSide());
                    }
                }

                return InteractionResultHolder.fail(handItem);
            } else {
                BlockState blockstate = level.getBlockState(hitPos);
                BlockPos blockpos2 = canBlockContainFluid(player, level, hitPos, blockstate) ? hitPos : blockpos1;
                if (this.emptyContents(player, level, blockpos2, blockhitresult, handItem)) {
                    this.checkExtraContent(player, level, handItem, blockpos2);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos2, handItem);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    ItemStack itemstack1 = ItemUtils.createFilledResult(handItem, player, ifw_emptySuccessItem(handItem, player, this.tier()));
                    return InteractionResultHolder.sidedSuccess(itemstack1, level.isClientSide());
                } else {
                    return InteractionResultHolder.fail(handItem);
                }
            }
        }
    }


    public static ItemStack ifw_emptySuccessItem(ItemStack stack, Player player, IFWTier tier) {
        return !player.hasInfiniteMaterials() ? BucketHelper.emptyBucket(tier) : stack;
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack containerStack, BlockPos pos) {
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult hitResult, @Nullable ItemStack container) {
        if (!(this.content instanceof FlowingFluid flowingfluid)) {
            return false;
        } else {
            BlockState blockstate = level.getBlockState(pos);
            Block block  = blockstate.getBlock();
            boolean canBeReplaced = blockstate.canBeReplaced(this.content);
            boolean flag1 = blockstate.isAir() || canBeReplaced || block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(player, level, pos, blockstate, this.content);
            Optional<FluidStack> containedFluidStack = Optional.ofNullable(container).flatMap(FluidUtil::getFluidContained);
            if (!flag1) {
                return hitResult != null && this.emptyContents(player, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null, container);
            } else if (containedFluidStack.isPresent() && this.content.getFluidType().isVaporizedOnPlacement(level, pos, containedFluidStack.get())) {
                this.content.getFluidType().onVaporize(player, level, pos, containedFluidStack.get());
                return true;
            } else if (level.dimensionType().ultraWarm() && this.content.is(FluidTags.WATER)) {
                int l = pos.getX();
                int i = pos.getY();
                int j = pos.getZ();
                level.playSound(
                        player,
                        pos,
                        SoundEvents.FIRE_EXTINGUISH,
                        SoundSource.BLOCKS,
                        0.5F,
                        2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F
                );

                for (int k = 0; k < 8; k++) {
                    level.addParticle(
                            ParticleTypes.LARGE_SMOKE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), 0.0, 0.0, 0.0
                    );
                }

                return true;
            } else {
                if (block instanceof LiquidBlockContainer blockContainer && blockContainer.canPlaceLiquid(player, level, pos, blockstate, this.content)) {
                    blockContainer.placeLiquid(level, pos, blockstate, flowingfluid.getSource(false));
                    this.playEmptySound(player, level, pos);
                    return true;
                }

                if (!level.isClientSide && canBeReplaced && !blockstate.liquid()) {
                    level.destroyBlock(pos, true);
                }

                FluidState originalState = blockstate.getFluidState();
                if (originalState.getType().isSame(this.content) && originalState.isSource())
                    return true;
                else {
                    BlockState fluidState = this.content.defaultFluidState().createLegacyBlock();
                    if (player != null && player.totalExperience >= 100 && Screen.hasControlDown()) {
                        player.giveExperiencePoints(-100);
                    } else {
                        int i = 48;
                        if (this.content.isSame(Fluids.WATER)) i = 16;
                        WorldHelper.DELAYED_BLOCKS.computeIfAbsent(level.dimension(), k -> new HashMap<>()).put(pos, i);
                    }

                    if (!level.setBlock(pos, fluidState, 11) && !blockstate.getFluidState().isSource())
                        return false;
                    else {
                        this.playEmptySound(player, level, pos);
                        return true;
                    }
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            if (this.content.isSame(Fluids.EMPTY) && player.isEyeInFluid(FluidTags.WATER)) {
                player.getInventory().setItem(slotId, BucketHelper.waterBucket(this.tier()));
            }
            if (this.content.isSame(Fluids.LAVA) && player.isEyeInFluid(FluidTags.WATER)) {
                player.getInventory().setItem(slotId, BucketHelper.stoneBucket(this.tier()));
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.ifw.placeBucketAsSource").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    public IFWTier tier() {
        return this.tier;
    }
}
