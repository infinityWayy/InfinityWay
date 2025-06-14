package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.mob.Livestock;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SeekOpenSpaceIfCrowdedGoal extends Goal {

    protected final PathfinderMob mob;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected boolean isRunning;

    public SeekOpenSpaceIfCrowdedGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof Livestock livestock) {
            if (livestock.freedom() > 0.5F || !livestock.isCrowded() || isRunning) {
                return false;
            }
            if (this.mob.getRandom().nextInt(livestock.freedom() < 0.25f ? 10 : 40) == 0) {
                return getMovementPath(livestock);
            }
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
        Vec3 newPosition = DefaultRandomPos.getPos(this.mob, 5, 4);
        if (newPosition != null && !livestock.isCrowded(
                newPosition.x, newPosition.y, newPosition.z)) {
            this.posX = newPosition.x;
            this.posY = newPosition.y;
            this.posZ = newPosition.z;
            return true;
        }
        return false;
    }
}
