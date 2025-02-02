package huix.infinity.mixin.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    public FallingBlockEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;onGround()Z"))
    public boolean onGround(FallingBlockEntity fallingBlockEntity) {
        if (this.onGround()) {
            return true;
        } else {
            BlockPos pos = fallingBlockEntity.blockPosition().below();
            BlockState state = this.level().getBlockState(pos);
            VoxelShape collisionShape = state.getCollisionShape(this.level(), pos);
            double collisionHeight = collisionShape.isEmpty() ? 0.0 : collisionShape.max(Direction.Axis.Y);
            return !FallingBlock.isFree(state) && collisionHeight < 1;
        }
    }
}
