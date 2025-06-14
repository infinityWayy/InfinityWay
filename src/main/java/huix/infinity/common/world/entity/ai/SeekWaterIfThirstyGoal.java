package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.mob.Livestock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Unique;

import java.util.EnumSet;

public class SeekWaterIfThirstyGoal extends Goal {

    protected final PathfinderMob mob;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected boolean isRunning;

    public SeekWaterIfThirstyGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof Livestock livestock) {
            if (!livestock.isThirsty() || livestock.isInWater() ||
                    livestock.isInRain() || livestock.isNearWater() || isRunning) {
                return false;
            }
            return getMovementPath(livestock);
        }
        return false;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1);
        this.isRunning = true;
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    private boolean getMovementPath(Livestock livestock) {
    int waterCheckDistance = livestock.isDesperateForWater()? 24: livestock.isVeryThirsty()? 16: 8;
        BlockPos waterPos = this.lookForWater(this.mob.level(), this.mob, waterCheckDistance);
        if (waterPos != null) {
            this.setTargetPosition(waterPos);
            livestock.setMemoryWater(waterPos);
            return true;
        } else {
            BlockPos memoryWater = livestock.getMemoryWater();
            if (memoryWater != null && livestock.distanceToSqr(memoryWater.getCenter()) <= 32 * 32
                    && livestock.level().isWaterAt(memoryWater)) {
                this.setTargetPosition(memoryWater);
                return true;
            }
            Vec3 posAway = DefaultRandomPos.getPosAway(this.mob, 32, 16, this.mob.position());
            if (posAway != null) {
                this.setTargetPosition(posAway);
                return true;
            }
        }
        return false;
    }

    protected BlockPos lookForWater(BlockGetter level, Entity entity, int range) {
        return BlockPos.findClosestMatch(entity.blockPosition(), range, range / 4,
                (p_196649_) -> level.getFluidState(p_196649_).is(FluidTags.WATER)).orElse(null);
    }

    /**
     * Sets the target position coordinates.
     *
     * @param pos BlockPos containing target coordinates
     */
    @Unique
    private void setTargetPosition(BlockPos pos) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
    }

    /**
     * Sets the target position using a Vec3.
     *
     * @param pos Vec3 containing target coordinates
     */
    @Unique
    private void setTargetPosition(Vec3 pos) {
        this.posX = pos.x;
        this.posY = pos.y;
        this.posZ = pos.z;
    }
}
