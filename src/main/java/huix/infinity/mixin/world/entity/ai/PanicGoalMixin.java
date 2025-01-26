package huix.infinity.mixin.world.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Objects;

@Mixin(PanicGoal.class)
public abstract class PanicGoalMixin {
    @Unique
    public int infinityWay$damageTick = 0;

    @Unique
    public Vec3 infinityWay$sourcePosition;

    @Shadow @Final protected PathfinderMob mob;

    @Shadow protected double posX;

    @Shadow protected double posY;

    @Shadow protected double posZ;

    @Shadow protected abstract boolean shouldPanic();

    @Shadow @Nullable protected abstract BlockPos lookForWater(BlockGetter level, Entity entity, int range);

    @Shadow protected abstract boolean findRandomPosition();

    @Mutable
    @Shadow @Final protected double speedModifier;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/PathfinderMob;D)V", at = @At("TAIL"))
    public void init(PathfinderMob mob, double speedModifier, CallbackInfo ci) {
        this.speedModifier = speedModifier + 0.2F;
    }

    /**
     * @author limingzxc
     * @reason 增加恐慌的时间
     */
    @Overwrite
    public boolean canUse() {

        if ( this.shouldPanic() ) {
            infinityWay$damageTick = 20;
            infinityWay$sourcePosition = Objects.requireNonNull(mob.getLastDamageSource()).getSourcePosition();
        }

        if ( infinityWay$damageTick > 0 ) {

            infinityWay$damageTick--;

            if ( this.mob.isOnFire() ) {
                BlockPos blockpos = this.lookForWater(this.mob.level(), this.mob, 5);
                if (blockpos != null) {
                    this.posX = blockpos.getX();
                    this.posY = blockpos.getY();
                    this.posZ = blockpos.getZ();
                    return true;
                }
            }

            if ( mob.position().distanceTo(new Vec3(posX, posY, posZ)) > 10 * 10
                || mob.position().distanceTo(new Vec3(posX, posY, posZ)) < 2) {
                return this.findRandomPosition();
            }
            return true;
        }

        return false;
    }

    // 选取路径点时扩大范围和距离，并远离伤害源
    @Redirect(method = "findRandomPosition", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/ai/util/DefaultRandomPos;getPos(Lnet/minecraft/world/entity/PathfinderMob;II)Lnet/minecraft/world/phys/Vec3;"))
    public Vec3 expandPathFind(PathfinderMob mob, int radius, int verticalDistance) {
        return DefaultRandomPos.getPosAway(this.mob, 10, 7, this.infinityWay$sourcePosition);
    }
}
