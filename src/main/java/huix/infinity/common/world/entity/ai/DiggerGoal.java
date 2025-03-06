package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.monster.Digger;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import java.util.EnumSet;

public class DiggerGoal extends Goal {
    private Digger digger;
    private static boolean PLAYER_ATTACKS_RESET_DIGGING = false;
    public DiggerGoal(Digger attacker) {
        this.digger = attacker;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // 阻止挖掘的物品检查（保持逻辑不变）
        if (this.digger.isHoldingItemThatPreventsDigging()) {
            return false;
        }

        boolean hasLineOfSight = this.digger.getTarget() != null &&
                this.digger.getSensing().hasLineOfSight(this.digger.getTarget());

        // 生物是否未启用挖掘而且看不见目标
        if (!this.digger.isDiggingEnabled() && !hasLineOfSight) {
            return false;
        }

        // 生物挖掘是否会被攻击打断
        if (this.digger.getLastHurtByMobTimestamp() > 0 && PLAYER_ATTACKS_RESET_DIGGING) {
            return false;
        }

        // 生物和目标是否在同一位置
        LivingEntity target = this.digger.getTarget();
        if (target == null || this.digger.blockPosition().equals(target.blockPosition())) {
            return false;
        }

        // 生物是否可以破坏选中方块
        if (this.digger.isDestroyingBlock &&
                this.digger.canDestroyBlock(this.digger.destroyBlockPos, true)) {
            return true;
        }

        // 在没开始挖掘前有几率放弃挖掘
        if (!this.digger.isDestroyingBlock &&
                this.digger.getRandom().nextInt(20) != 0) {
            return false;
        }

        // 距离检查
        double distance = this.digger.distanceTo(target);
        if (distance > 16.0) {
            return false;
        }

        // 腿部挖掘检查
        if (distance * distance > 2.0f) {
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(
                    target.getBlockX(),
                    target.getBlockY(),
                    target.getBlockZ()
            );

            for (int y = mutablePos.getY(); y >= this.digger.getBlockY(); y--) {
                mutablePos.setY(y);
                if (this.digger.setBlockToDig(mutablePos, true)) {
                    return true;
                }
            }
        }

        // 最大追踪挖掘距离
        double maxDistance = hasLineOfSight ? 8.0 : this.digger.isAggressive() ? 6.0 : 4.0;
        if (distance > maxDistance) {
            return false;
        }

        // 如果存在寻路路径，取消挖掘
        Path path = this.digger.getNavigation().createPath(target, 0);
        if (path != null && path.canReach()) {
            return false;
        }

        // 如果能够看见目标，且目标在攻击范围内，取消挖掘
        if (this.digger.hasLineOfSight(target) && this.digger.isWithinMeleeAttackRange(target)) {
            return false;
        }

        // 使用现代 BlockPos 和 Vec3 坐标系统
        BlockPos targetBlockPos = target.blockPosition();
        Vec3 targetCenterPos = target.getBoundingBox().getCenter();

        // 使用现代射线追踪系统
        Level world = this.digger.level();

        // 第一部分检测（上方空间）
        if (checkUpperSpace(world, targetBlockPos, targetCenterPos)) {
            return true;
        }

        // 第二部分检测（直接路径）
        if (checkDirectPath(world, targetCenterPos)) {
            return true;
        }

        // 第三部分检测（腿部位置）
        return checkLegacyPath(world, targetCenterPos);
    }

    private boolean checkUpperSpace(Level world, BlockPos targetPos, Vec3 targetCenter) {
        // 检查上方方块是否为空气或可通行
        BlockPos upperPos = targetPos.above();
        if (world.getBlockState(upperPos).isAir() || world.getBlockState(upperPos).isCollisionShapeFullBlock(world, upperPos)) {
            // 创建射线追踪上下文
            ClipContext context = new ClipContext(
                    this.digger.getEyePosition(1.0f),
                    targetCenter.add(0, 1, 0),
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this.digger
            );

            BlockHitResult hitResult = world.clip(context);
            return processHitResult(hitResult, world);
        }
        return false;
    }

    private boolean checkDirectPath(Level world, Vec3 targetCenter) {
        ClipContext context = new ClipContext(
                this.digger.getEyePosition(1.0f),
                targetCenter,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this.digger
        );

        BlockHitResult hitResult = world.clip(context);
        return processHitResult(hitResult, world);
    }

    private boolean checkLegacyPath(Level world, Vec3 targetCenter) {
        // 获取腿部位置
        Vec3 legPos = this.digger.getLegPosition();
        ClipContext context = new ClipContext(
                legPos,
                targetCenter,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this.digger
        );

        BlockHitResult hitResult = world.clip(context);
        if (hitResult.getType() == HitResult.Type.MISS) return false;

        BlockPos hitPos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(hitPos);

        // 权限和有效性检查
        if (isRestrictedBlock(state) &&
                !this.digger.isEffectiveTool(state) &&
                !this.digger.isTargetingPlayer()) {
            return false;
        }

        // 检查上方方块状态
        BlockPos upperPos = hitPos.above();
        boolean upperBlockValid = world.getBlockState(upperPos).isAir() ||
                this.digger.blockWillFall(upperPos);

        return upperBlockValid && this.digger.setBlockToDig(hitPos, false);
    }

    private boolean processHitResult(BlockHitResult hitResult, Level world) {
        if (hitResult.getType() != HitResult.Type.BLOCK) return false;

        BlockPos hitPos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(hitPos);

        if (!isValidBlock(state)) return false;

        // 遍历 Y 轴检测
        int attackerY = Mth.floor(this.digger.getY());
        for (int y = hitPos.getY() + 1; y >= attackerY; y--) {
            BlockPos currentPos = new BlockPos(hitPos.getX(), y, hitPos.getZ());
            if (this.digger.setBlockToDig(currentPos, true)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidBlock(BlockState state) {
        return !isRestrictedBlock(state) ||
                this.digger.isEffectiveTool(state) ||
                this.digger.isTargetingPlayer();
    }

    public boolean isRestrictedBlock(BlockState state) {
        return state.is(Tags.Blocks.FENCES);
    }

    private BlockHitResult getIntersectingBlock(Vec3 attackerEyePos, Vec3 targetPos) {
        return this.digger.level().clip(new ClipContext(
                attackerEyePos,
                targetPos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this.digger
        ));
    }

    private boolean couldGetCloserByPathing() {
        LivingEntity target = this.digger.getTarget();
        if (target == null) return false;

        double distance = this.digger.distanceToSqr(target);
        Path path = this.digger.getNavigation().createPath(target, 16);
        if (path == null || path.getEndNode() == null) return false;

        Node finalNode = path.getEndNode();
        Vec3 pathPos = new Vec3(
                finalNode.x + 0.5,
                finalNode.y,
                finalNode.z + 0.5
        );

        return pathPos.distanceToSqr(target.position()) < distance - 4.0;
    }

    private boolean couldHitTargetByPathing() {
        LivingEntity target = this.digger.getTarget();
        if (target == null) return false;

        Path path = this.digger.getNavigation().createPath(target, 0);
        return path != null && path.canReach();
    }

    @Override
    public void start() {
        this.digger.isDestroyingBlock = true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.digger.isHoldingItemThatPreventsDigging()) {
            return false;
        }

        // 替换旧的AI任务检查逻辑
        MeleeAttackGoal attackGoal = (MeleeAttackGoal) this.digger.goalSelector.getAvailableGoals()
                .stream()
                .filter(g -> g.getGoal() instanceof MeleeAttackGoal)
                .findFirst()
                .map(WrappedGoal::getGoal)
                .orElse(null);

        if (attackGoal != null) {
            if (attackGoal.ticksUntilNextAttack > 0) {
                attackGoal.ticksUntilNextAttack--;
            }
            if (this.digger.getTarget() != null && attackGoal.canPerformAttack(this.digger.getTarget())) {
                attackGoal.tick();
                return false;
            }
        }

        if (this.digger.destroyPauseTicks > 0) {
            return this.digger.destroyPauseTicks != 1 || !this.couldGetCloserByPathing();
        }

        if (!this.digger.isDestroyingBlock) {
            return false;
        }

        if (!this.digger.canDestroyBlock(this.digger.destroyBlockPos, true)) {
            return false;
        }

        if (this.digger.getLastHurtByMobTimestamp() > 0 && PLAYER_ATTACKS_RESET_DIGGING) {
            return false;
        }

        LivingEntity target = this.digger.getTarget();
        if (target == null) return false;

        if (this.digger.blockPosition().equals(target.blockPosition())) {
            return false;
        }

        return this.digger.getRandom().nextInt(10) != 0 || !this.couldHitTargetByPathing();
    }

    @Override
    public void tick() {
        BlockPos pos = this.digger.destroyBlockPos;
        this.digger.getLookControl().setLookAt(
                pos.getX(), pos.getY(), pos.getZ(),
                (float)(this.digger.getMaxHeadYRot() + 20),
                (float)this.digger.getMaxHeadXRot());

        if (this.digger.destroyPauseTicks > 0) {
            this.digger.destroyPauseTicks --;
            return;
        }

        if (this.digger.destroyBlockCoolOff == 10) {
            this.digger.swing(InteractionHand.MAIN_HAND);
        }

        if (--this.digger.destroyBlockCoolOff > 0) return;

        this.digger.destroyBlockCoolOff = this.digger.getCoolOffForBlock();
        this.digger.partiallyDestroyBlock();
    }

    @Override
    public void stop() {
        this.digger.cancelBlockDestruction();
    }
}
