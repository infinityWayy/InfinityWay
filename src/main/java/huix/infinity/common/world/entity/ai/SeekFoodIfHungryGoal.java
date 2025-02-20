package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.animal.Livestock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Set;

public class SeekFoodIfHungryGoal extends Goal {
    protected final PathfinderMob mob;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected boolean isRunning;
    public SeekFoodIfHungryGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof Livestock livestock) {
            if (!livestock.isHungry() || livestock.isNearFood() || isRunning) {
                return false;
            }
            return getMovementPath(livestock);
        }
        return false;
    }

    public boolean isRunning() {
        return this.isRunning;
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

    protected boolean getMovementPath(Livestock livestock) {
        // 状态判断
        boolean isVeryHungry = livestock.isVeryHungry();
        boolean isDesperate = livestock.isDesperateForFood();

        // 动态参数设置
        final int searchRadius = isDesperate ? 24 : (isVeryHungry ? 16 : 8);
        final int verticalSearch = searchRadius / 8;

        // 使用新版 BlockPos 替代旧坐标系统
        BlockPos centerPos = this.mob.blockPosition();

        // 获取可食用方块集合
        final Set<Block> foodBlocks = livestock.getFoodBlocks();

        // 寻找符合要求的方块
        for (BlockPos candidate: BlockPos.withinManhattan(centerPos, searchRadius, verticalSearch, searchRadius)) {
            if (foodBlocks.contains(this.mob.level().getBlockState(candidate).getBlock())) {
                if (this.mob.getNavigation().createPath(candidate.getX(), candidate.getY(), candidate.getZ(), 1) != null) {
                    this.posX = candidate.getX();
                    this.posY = candidate.getY();
                    this.posZ = candidate.getZ();
                    return true;
                }
            }
        }

        // 默认逃离路径
        Vec3 currentPos = this.mob.position();

        Vec3 posAway = DefaultRandomPos.getPosAway(this.mob, 32, 16, currentPos);
        if (posAway != null) {
            this.posX = posAway.x;
            this.posY = posAway.y;
            this.posZ = posAway.z;
            return true;
        }
        return false;
    }
}
