package huix.infinity.common.world.entity.monster.arachnid;

import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class IFWDemonSpider extends IFWArachnid {

    public IFWDemonSpider(EntityType<? extends IFWArachnid> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createArachnidAttributes()
                .add(Attributes.MAX_HEALTH, 18.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
        // 移除移动速度设置，使用父类默认值
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // 添加熔岩逃避AI
        this.goalSelector.addGoal(1, new GetOutOfLavaGoal(this));
    }

    @Override
    protected boolean isSpecialSpiderType() {
        return true;
    }

    protected int getWebShootInterval() {
        return 200;
    }

    @Override
    protected boolean peacefulDuringDay() {
        return false;
    }

    @Override
    protected float getExperienceMultiplier() {
        return 3.0f;
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean result = super.doHurtTarget(target);

        if (result && target instanceof LivingEntity livingTarget) {
            // 中毒效果 24秒
            livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 480, 0));
            // 缓慢效果 2.5秒，6级强度
            livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 5));
        }

        return result;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.LAVA) || super.canStandOnFluid(fluidState);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        // 火焰和熔岩伤害免疫
        return damageSource.is(net.minecraft.tags.DamageTypeTags.IS_FIRE) ||
                super.isInvulnerableTo(damageSource);
    }

    // 自定义熔岩逃避AI Goal
    public static class GetOutOfLavaGoal extends Goal {
        private final IFWDemonSpider spider;
        private double targetX;
        private double targetY;
        private double targetZ;
        private final double speedModifier = 1.0;

        public GetOutOfLavaGoal(IFWDemonSpider spider) {
            this.spider = spider;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            // 检查蜘蛛是否在熔岩中
            if (!spider.isInLava()) {
                return false;
            }

            // 寻找最近的安全位置
            Vec3 safePos = findSafePosition();
            if (safePos != null) {
                this.targetX = safePos.x;
                this.targetY = safePos.y;
                this.targetZ = safePos.z;
                return true;
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return !spider.getNavigation().isDone() && spider.isInLava();
        }

        @Override
        public void start() {
            spider.getNavigation().moveTo(targetX, targetY, targetZ, speedModifier);
        }

        @Override
        public void stop() {
            spider.getNavigation().stop();
        }

        private Vec3 findSafePosition() {
            BlockPos spiderPos = spider.blockPosition();

            // 在周围8x8区域寻找安全位置
            for (int x = -4; x <= 4; x++) {
                for (int z = -4; z <= 4; z++) {
                    for (int y = -2; y <= 2; y++) {
                        BlockPos checkPos = spiderPos.offset(x, y, z);

                        // 检查位置是否安全（不是熔岩且可以站立）
                        if (isSafePosition(checkPos)) {
                            return Vec3.atCenterOf(checkPos);
                        }
                    }
                }
            }
            return null;
        }

        private boolean isSafePosition(BlockPos pos) {
            Level level = spider.level();

            // 检查脚下是否是固体方块
            if (!level.getBlockState(pos.below()).isSolidRender(level, pos.below())) {
                return false;
            }

            // 检查位置本身不是熔岩或固体方块
            FluidState fluidState = level.getFluidState(pos);
            if (fluidState.is(FluidTags.LAVA) ||
                    level.getBlockState(pos).isSolidRender(level, pos)) {
                return false;
            }

            // 检查头部空间
            if (level.getBlockState(pos.above()).isSolidRender(level, pos.above())) {
                return false;
            }

            return true;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return IFWSoundEvents.DEMON_SPIDER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return IFWSoundEvents.DEMON_SPIDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IFWSoundEvents.DEMON_SPIDER_DEATH.get();
    }
}