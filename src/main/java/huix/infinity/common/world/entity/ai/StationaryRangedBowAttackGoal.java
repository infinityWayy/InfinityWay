package huix.infinity.common.world.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class StationaryRangedBowAttackGoal<T extends Monster & RangedAttackMob> extends Goal {
    private final T mob;
    private final double speedModifier;
    private int minAttackInterval;
    private final int maxAttackInterval;
    private final float attackRadiusSqr;
    private int attackTime = -1;
    private int seeTime;
    private LivingEntity target;

    public StationaryRangedBowAttackGoal(T mob, double speedModifier, int minAttackInterval, int maxAttackInterval, float attackRadius) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.minAttackInterval = minAttackInterval;
        this.maxAttackInterval = maxAttackInterval;
        this.attackRadiusSqr = attackRadius * attackRadius * 4.0F; // 原版乘以4.0F
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public StationaryRangedBowAttackGoal(T mob, double speedModifier, int attackInterval, float attackRadius) {
        this(mob, speedModifier, attackInterval, attackInterval, attackRadius);
    }

    public void setMinAttackInterval(int minAttackInterval) {
        this.minAttackInterval = minAttackInterval;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return false;
        } else {
            this.target = target;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() || !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        super.start();
        this.mob.setAggressive(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
        this.mob.setAggressive(false);
        this.mob.stopUsingItem(); // 停止使用物品（停止拉弓动画）
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.target == null) {
            return;
        }

        double distanceSqr = this.mob.distanceToSqr(this.target.getX(), this.target.getBoundingBox().minY, this.target.getZ());
        Level level = this.mob.level();

        // 复刻原版的视线检测逻辑
        Vec3 eyePos = new Vec3(this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        Vec3 targetEyePos = new Vec3(this.target.getX(), this.target.getEyeY(), this.target.getZ());

        BlockHitResult raycast = level.clip(new ClipContext(
                eyePos,
                targetEyePos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this.mob
        ));

        boolean hasLineOfSight = raycast.getType() == HitResult.Type.MISS;

        // 如果有障碍物，尝试射击目标脚部位置
        if (!hasLineOfSight) {
            Vec3 targetFootPos = new Vec3(this.target.getX(), this.target.getY() + this.target.getBbHeight() * 0.25F, this.target.getZ());
            raycast = level.clip(new ClipContext(
                    eyePos,
                    targetFootPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this.mob
            ));
            hasLineOfSight = raycast.getType() == HitResult.Type.MISS;
        }

        if (hasLineOfSight) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        // 复刻原版的移动和攻击逻辑
        if (distanceSqr <= (double)this.attackRadiusSqr && this.seeTime >= 20) {
            // 在射击范围内且有视线 - 停止移动
            this.mob.getNavigation().stop();
        } else {
            // 距离太远或没有视线 - 移动接近目标
            this.mob.getNavigation().moveTo(this.target, this.speedModifier);
        }

        // 始终看向目标
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        // 复刻原版的攻击时机逻辑
        if (--this.attackTime == 0) {
            if (distanceSqr > (double)this.attackRadiusSqr || !hasLineOfSight) {
                return;
            }

            float distance = Mth.sqrt((float)distanceSqr) / (this.attackRadiusSqr / 4.0F); // 原版计算方式
            float velocity = distance;
            if (velocity < 0.1F) {
                velocity = 0.1F;
            }
            if (velocity > 1.0F) {
                velocity = 1.0F;
            }

            // 检查骷髅位置是否适合射击（复刻原版检测）
            if (this.mob.level().isEmptyBlock(this.mob.blockPosition())) {
                this.mob.performRangedAttack(this.target, velocity);
            }

            // 设置下次攻击时间，并考虑狂暴状态
            this.attackTime = this.maxAttackInterval;
            if (this.mob instanceof huix.infinity.common.world.entity.monster.skeleton.IFWSkeleton skeleton && skeleton.isFrenziedByBoneLord()) {
                this.attackTime = (int)((float)this.attackTime * 0.67F); // 原版狂暴加速
            }
        } else if (this.attackTime < 0) {
            // 初始化攻击时间
            this.attackTime = this.maxAttackInterval;
            if (this.mob instanceof huix.infinity.common.world.entity.monster.skeleton.IFWSkeleton skeleton && skeleton.isFrenziedByBoneLord()) {
                this.attackTime = (int)((float)this.attackTime * 0.67F);
            }
        }

        // 添加拉弓动画逻辑
        if (this.attackTime >= 0 && distanceSqr <= (double)this.attackRadiusSqr && hasLineOfSight) {
            // 计算拉弓进度
            int bowPullTime = this.maxAttackInterval - this.attackTime;

            // 开始拉弓动画
            if (bowPullTime == 1) {
                this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.mob, Items.BOW));
            }

            // 保持拉弓状态
            if (bowPullTime > 0 && this.mob.getUseItem().isEmpty()) {
                this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.mob, Items.BOW));
            }
        } else {
            // 不在攻击状态时停止拉弓
            if (this.mob.isUsingItem()) {
                this.mob.stopUsingItem();
            }
        }
    }
}