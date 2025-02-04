package huix.infinity.mixin.world.block;

import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.common.world.block.IFWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class DirtBlockMixin {
    @Shadow protected abstract Block asBlock();

    @Inject(method = "onPlace", at = @At("TAIL"))
    private void onPlaced(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving, CallbackInfo ci) {
        if (state.is(IFWBlockTags.FALLEN_DIRT) && !level.isClientSide) {
            level.scheduleTick(pos, asBlock(), 2);
        }
    }

    @Inject(method = "updateShape", at = @At("TAIL"))
    private void onNeighborChanged(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (state.is(IFWBlockTags.FALLEN_DIRT) && !level.isClientSide()) {
            level.scheduleTick(pos, asBlock(), 2);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onScheduledTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (state.is(IFWBlockTags.FALLEN_DIRT)) {
            if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
                FallingBlockEntity.fall(level, pos, state);
                ci.cancel();
            }
        }
    }
}

