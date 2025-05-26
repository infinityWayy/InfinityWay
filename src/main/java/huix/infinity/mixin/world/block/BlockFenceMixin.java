package huix.infinity.mixin.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossCollisionBlock.class)
public class BlockFenceMixin {

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void makeClimbableForPlayer(BlockState state, BlockGetter level, BlockPos pos,
                                        CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!(state.getBlock() instanceof FenceBlock)) {
            return;
        }
        if (context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if (entity instanceof Player) {
                VoxelShape climbableShape = Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                cir.setReturnValue(climbableShape);
            }
        }
    }
}