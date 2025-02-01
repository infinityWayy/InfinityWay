package huix.infinity.mixin.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {
    public LeavesBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (random.nextInt(250) == 0) {
            dropResources(state, level, pos);
        }
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(
            @NotNull BlockState state,
            @NotNull BlockGetter level,
            @NotNull BlockPos pos,
            @NotNull CollisionContext context
    ) {
        // 通过 CollisionContext 获取当前碰撞的实体
        if (context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();

            // 如果是目标实体（掉落物或箭），返回空碰撞箱
            if (entity instanceof ItemEntity || entity instanceof AbstractArrow) {
                return Shapes.empty();
            }
        }

        // 其他情况返回默认碰撞箱
        return super.getCollisionShape(state, level, pos, context);
    }
}
