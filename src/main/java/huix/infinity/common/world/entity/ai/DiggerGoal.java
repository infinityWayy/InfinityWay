package huix.infinity.common.world.entity.ai;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.entity.monster.digger.Digger;
import huix.infinity.common.world.item.IFWTieredItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DiggerGoal extends Goal {
    private float MAX_DIG_DISTANCE = 3.0f;
    private final Digger digger;
    private int lastBreakProgress = -1; // 用于追踪上一次的破坏进度

    public DiggerGoal(Digger digger) {
        this.digger = digger;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.digger.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (this.digger.hasLineOfSight(target) && this.digger.isWithinMeleeAttackRange(target)) {
            return false;
        }

        ItemStack tool = this.digger.getMainHandItem();
        if (tool.is(IFWItemTags.NO_MINING_TOOLS)) return false;

        // 如果已经有挖掘目标，继续使用
        if (digger.getDigTarget() != null) {
            return true;
        }

        // 寻找可以挖掘的方块
        BlockPos targetBlock = findDiggableBlock(target);
        if (targetBlock != null) {
            digger.setDigTarget(targetBlock);
            return true;
        }
        return false;
    }

    private BlockPos findDiggableBlock(LivingEntity target) {
        // 获取僵尸和目标之间的距离
        double distanceToTarget = this.digger.distanceToSqr(target);

        // 如果目标太远，不考虑挖掘
        if (distanceToTarget > 16.0D) { // 4 blocks squared
            return null;
        }

        // 获取到目标的路径
        Path path = this.digger.getNavigation().createPath(target, 0);
        if (path == null || path.getEndNode() == null ||
                path.getEndNode().asBlockPos().equals(digger.blockPosition())) {
            // 如果无法找到路径，检查周围可挖掘的方块
            return findNearestDiggableBlock(target.blockPosition());
        }

        // 检查路径上的障碍物
        for (int i = 0; i < Math.min(path.getNodeCount(), 5); i++) {
            Node node = path.getNode(i);
            BlockPos pos = new BlockPos(node.x, node.y + 1, node.z);

            // 检查该位置和上方一格的方块（因为是两格高的生物）
            if (isDiggableBlock(pos) || isDiggableBlock(pos.above())) {
                // 确保方块在挖掘范围内
                if (this.digger.distanceToSqr(Vec3.atCenterOf(pos)) <= MAX_DIG_DISTANCE * MAX_DIG_DISTANCE) {
                    if (isDiggableBlock(pos) && hasLineOfSight(pos)) {
                        return pos;
                    }
                }
            }
        }

        return null;
    }

    private BlockPos findNearestDiggableBlock(BlockPos targetPos) {
        BlockPos nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        int diggerY = digger.blockPosition().getY();
        int targetY = targetPos.getY();

        // 定义Y轴搜索顺序
        int[] ySearchOrder;

        if (diggerY == targetY) {
            // 当在同一高度时，从中间向两边搜索
            ySearchOrder = new int[] {1, 0, -1, 2, -2};
        } else if (diggerY < targetY) {
            // 当目标在上方时，从上往下搜索
            ySearchOrder = new int[] {2, 1, 0, -1, -2};
        } else {
            // 当目标在下方时，从下往上搜索
            ySearchOrder = new int[] {-2, -1, 0, 1, 2};
        }

        int[] xzSearchOrder = new int[] {0, 1, -1, 2, -2};

        // 在XZ平面上进行搜索
        for (int x : xzSearchOrder) {
            for (int z : xzSearchOrder) {
                // 按照确定的顺序搜索Y轴
                for (int yOffset : ySearchOrder) {
                    BlockPos checkPos = targetPos.offset(x, yOffset, z);

                    // 检查方块是否在挖掘范围内
                    double distance = this.digger.distanceToSqr(Vec3.atCenterOf(checkPos));
                    if (distance > MAX_DIG_DISTANCE * MAX_DIG_DISTANCE) {
                        continue;
                    }

                    // 检查方块是否可挖掘且有视线
                    if (isDiggableBlock(checkPos) && hasLineOfSight(checkPos)) {
                        // 计算到目标的曼哈顿距离
                        double manhattanDist = targetPos.distManhattan(checkPos);
                        if (manhattanDist < nearestDistance) {
                            nearestDistance = manhattanDist;
                            nearest = checkPos;
                        }
                    }
                }
            }
        }

        return nearest;
    }

    private boolean isDiggableBlock(BlockPos pos) {
        Level level = this.digger.level();
        BlockState state = level.getBlockState(pos);
        float defaultDestroyTime = state.getBlock().defaultDestroyTime();

        if (state.isEmpty()) {
            return false;
        }

        if (defaultDestroyTime <= 1) {
            return true;
        }

        // 检查方块是否可以被当前工具挖掘
        ItemStack tool = this.digger.getMainHandItem();

        return (tool.getItem() instanceof IFWTieredItem tieredItem && tieredItem.isDamageable(state))
                 || tool.isCorrectToolForDrops(state);
    }

    private float calculateDiggingSpeed(ItemStack tool, BlockState state, BlockPos pos) {
        float baseSpeed = tool.getDestroySpeed(state);
        float blockHardness = state.getDestroySpeed(digger.level(), pos);

        if (blockHardness == -1.0F) {
            return 0.0F; // 不可破坏的方块
        }

        // 基础速度除以方块硬度，再加上一个基础值
        return (baseSpeed / (blockHardness * 30.0F)) + 0.005F;
    }

    @Override
    public void tick() {
        BlockPos targetBlock = digger.getDigTarget();
        if (targetBlock == null) return;

        // 确保僵尸面向目标方块
        this.digger.getLookControl().setLookAt(
                targetBlock.getX() + 0.5,
                targetBlock.getY() + 0.5,
                targetBlock.getZ() + 0.5
        );

        // 获取当前进度
        float progress = digger.getDigProgress();
        BlockState blockState = digger.level().getBlockState(targetBlock);
        ItemStack stack = digger.getMainHandItem();
        this.MAX_DIG_DISTANCE = 3 + stack.getItem().getReachBonus();
        // 计算挖掘速度
        float speedMultiplier = calculateDiggingSpeed(stack, blockState, targetBlock);
        progress += speedMultiplier / 30;
        digger.setDigProgress(progress);

        // 计算当前破坏进度（0-9）
        int breakProgress = (int) (progress * 10.0F);

        // 当进入新的破坏阶段时摆动手臂
        if (breakProgress > lastBreakProgress) {
            swingArm(blockState, targetBlock);

            // 更新方块破坏进度
            digger.level().destroyBlockProgress(
                    digger.getId(),
                    targetBlock,
                    Math.min(breakProgress, 9)
            );

            lastBreakProgress = breakProgress;
        }

        // 完成挖掘
        if (progress >= 1.0F) {
            digger.level().destroyBlock(targetBlock, true);
            digger.clearDigging();
        }
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.digger.getTarget();
        if (target == null) return false;

        if (this.digger.hasLineOfSight(target) && this.digger.isWithinMeleeAttackRange(target)) {
            return false;
        }

        BlockPos targetBlock = digger.getDigTarget();
        if (targetBlock == null) return false;

        Path path = this.digger.getNavigation().createPath(target, 0);
        if (path != null && path.getEndNode() != null && !path.getEndNode().asBlockPos().equals(digger.blockPosition())) {
            // 如果我们正在定位并且离目标还很远，现在还不用开始挖掘
            double distanceToTarget = this.digger.distanceToSqr(target);
            if (distanceToTarget > 4) {
                return false;
            }
        }

        // 检查方块是否可以被挖掘
        return isDiggableBlock(targetBlock) && hasLineOfSight(targetBlock);
    }

    private boolean hasLineOfSight(BlockPos targetPos) {
        Level level = digger.level();
        Vec3 diggerEyes = digger.getEyePosition().add(0, -0.3f, 0);
        Vec3 blockCenter = new Vec3(
                targetPos.getX() + 0.5,
                targetPos.getY() + 0.5,
                targetPos.getZ() + 0.5
        );

        // 使用射线追踪检查视线
        BlockHitResult result = level.clip(new ClipContext(
                diggerEyes,
                blockCenter,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                digger
        ));

        return result.getBlockPos().equals(targetPos);
    }

    private void swingArm(BlockState blockState, BlockPos targetBlock) {
        digger.swing(InteractionHand.MAIN_HAND);
        Level level = digger.level();
        // 播放方块破坏效果
        if (blockState.is(BlockTags.IMPERMEABLE)) {
            level.playSound(null, targetBlock, SoundEvents.GLASS_BREAK,
                    SoundSource.BLOCKS, 1.0f, 0.8f);
        } else {
            level.levelEvent(2001, targetBlock, Block.getId(blockState));
        }
    }

    @Override
    public void stop() {
        BlockPos targetBlock = digger.getDigTarget();
        if (targetBlock != null) {
            // 清除方块破坏进度显示
            digger.level().destroyBlockProgress(digger.getId(), targetBlock, -1);
        }
        lastBreakProgress = 0;
        digger.clearDigging();
    }
}
