package huix.infinity.mixin.world.block;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.func_extension.BucketPickupExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin implements BucketPickupExtension {
    @Shadow @Final public FlowingFluid fluid;

    @Override
    public ItemStack ifw_pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state, IFWTier tier) {
        FluidState fluidState = state.getFluidState();
        if (fluidState.getType() instanceof FlowingFluid) {
            BlockState replacedState = fluidState.isSource() ? state : Blocks.AIR.defaultBlockState();
            level.setBlock(pos, replacedState, 11);
        }
        return new ItemStack(this.fluid.getBucket(player, level, tier));
    }

}
