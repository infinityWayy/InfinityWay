package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.monster.InvisibleStalker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class SeekTorchGoal extends Goal {
    private final InvisibleStalker stalker;
    private final float speed;
    private BlockPos targetPos;
    private int searchCooldown;

    public SeekTorchGoal(InvisibleStalker stalker, float speed) {
        this.stalker = stalker;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // 如果正在攻击，不寻找火把
        if (this.stalker.getTarget() != null) {
            return false;
        }

        if (--this.searchCooldown > 0) {
            return false;
        }

        this.searchCooldown = 20 + this.stalker.getRandom().nextInt(40);
        return this.findNearestTorch();
    }

    @Override
    public boolean canContinueToUse() {
        // 如果有攻击目标，停止寻找火把
        if (this.stalker.getTarget() != null) {
            return false;
        }

        if (this.targetPos == null) return false;
        if (!this.isValidTorch(this.targetPos)) return false;

        // 如果已经很接近目标，停止AI让 tryDisableNearbyLightSource 接管
        double distance = this.stalker.distanceToSqr(
                this.targetPos.getX() + 0.5,
                this.targetPos.getY() + 0.5,
                this.targetPos.getZ() + 0.5
        );

        if (distance <= 4.0) { // 2格距离内停止AI
            return false;
        }

        return this.stalker.getNavigation().isInProgress();
    }

    @Override
    public void start() {
        if (this.targetPos != null) {
            this.stalker.getNavigation().moveTo(
                    this.targetPos.getX() + 0.5,
                    this.targetPos.getY(),
                    this.targetPos.getZ() + 0.5,
                    this.speed
            );
        }
    }

    @Override
    public void stop() {
        this.targetPos = null;
        this.stalker.getNavigation().stop();
    }

    private boolean findNearestTorch() {
        Level level = this.stalker.level();
        BlockPos mobPos = this.stalker.blockPosition();
        int searchRadius = 16; // 较大的搜索范围来寻找目标
        BlockPos closestPos = null;
        double closestDistance = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(
                mobPos.offset(-searchRadius, -4, -searchRadius),
                mobPos.offset(searchRadius, 4, searchRadius))) {

            if (this.isValidTorch(pos)) {
                double distance = mobPos.distSqr(pos);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPos = pos;
                }
            }
        }

        if (closestPos != null) {
            this.targetPos = closestPos;
            return true;
        }
        return false;
    }

    private boolean isValidTorch(BlockPos pos) {
        if (pos == null) return false;

        Level level = this.stalker.level();
        if (!level.isLoaded(pos)) return false;

        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof TorchBlock ||
                state.getBlock() instanceof WallTorchBlock ||
                state.is(Blocks.TORCH) ||
                state.is(Blocks.WALL_TORCH) ||
                state.is(Blocks.JACK_O_LANTERN);
    }
}