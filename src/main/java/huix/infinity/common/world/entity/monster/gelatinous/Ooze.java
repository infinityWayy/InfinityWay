package huix.infinity.common.world.entity.monster.gelatinous;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.IFWTieredItem;
import huix.infinity.common.world.item.tier.IFWArmorMaterials;
import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Ooze extends GelatinousCube {
    private static final EntityDataAccessor<Boolean> DATA_IS_CLIMBING =
            SynchedEntityData.defineId(Ooze.class, EntityDataSerializers.BOOLEAN);

    public Ooze(EntityType<? extends Ooze> entityType, Level level) {
        super(entityType, level);
        this.randomDamage = 4;
        this.baseDamage = 1;
        // 使用特殊的爬行移动控制器
        this.moveControl = new OozeMoveControl(this);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_CLIMBING, false);
    }

    @Override
    protected void registerGoals() {
        // 不使用父类的跳跃AI，改用爬行AI
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new OozeMeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(3, new OozeWanderGoal(this, 0.8));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector
                .addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, p_352812_ -> Math.abs(p_352812_.getY() - this.getY()) <= 4.0));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    /**
     * 伤害免疫检查 - 只有附魔武器或特定伤害类型有效
     */
    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {

        if (damageSource.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }

        // 魔法伤害可以造成伤害
        if (damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypes.INDIRECT_MAGIC)) {
            return false;
        }

        // 熔岩伤害可以造成伤害（但不是火焰）
        if (damageSource.is(DamageTypes.LAVA)) {
            return false;
        }

        // 检查是否为玩家攻击
        if (damageSource.getEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();
            // 只有附魔武器能造成伤害
            if (!weapon.isEnchanted()) {
                return true;
            }
        }

        return super.isInvulnerableTo(damageSource);
    }

    public void setClimbing(boolean climbing) {
        this.entityData.set(DATA_IS_CLIMBING, climbing);
    }

    @Override
    public boolean onClimbable() {
        return this.entityData.get(DATA_IS_CLIMBING);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            // 检测是否碰到墙壁来决定爬行状态
            this.setClimbing(this.horizontalCollision);
        }
    }

    @Override
    boolean canItemBeCorrodedByAcid(ItemStack itemStack) {
        if (itemStack.is(IFWItemTags.ACID_IMMUNE)) {
            return false;
        }
        if (itemStack.getItem() instanceof ItemNameBlockItem blockItem) {
            BlockState state = blockItem.getBlock().defaultBlockState();
            if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
                return false;
            }
        }
        if (itemStack.getItem() instanceof IFWTieredItem ifwTieredItem) {
            switch (ifwTieredItem.ifwTier()) {
                case IFWTiers.GOLD, IFWTiers.MITHRIL, IFWTiers.ADAMANTIUM -> {
                    return false;
                }
                default -> {
                    return true;
                }
            }
        }
        if (itemStack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getMaterial().is(IFWArmorMaterials.golden) ||
                    armorItem.getMaterial().is(IFWArmorMaterials.mithril) ||
                    armorItem.getMaterial().is(IFWArmorMaterials.adamantium)) {
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public void setSize(int size) {

        super.setSize(Math.min(size, 2));
    }

    @Override
    public boolean canCorodeBlocks() {
        return true;
    }

    @Override
    public boolean isAcidic() {
        return true;
    }

    @Override
    public int getAttackStrengthMultiplier() {
        return 3;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.LAVA) ||
                damageSource.is(DamageTypes.MAGIC) ||
                damageSource.is(DamageTypes.INDIRECT_MAGIC)) {
            return super.hurt(damageSource, amount);
        }

        return super.hurt(damageSource, amount * 0.5f);
    }

    @Override
    public boolean canBeAffected(@NotNull MobEffectInstance effectInstance) {
        return super.canBeAffected(effectInstance);
    }

    @Override
    public @NotNull MobCategory getClassification(boolean forSpawnCount) {
        return MobCategory.MONSTER;
    }

    public float getClimbingSpeed() {
        return 0.2f;
    }

    @Override
    protected ItemStack getSlimeBallDrop() {
        return new ItemStack(IFWItems.ooze_ball.get());
    }

    @Override
    protected GelatinousCube createSmallerSlime(int size) {
        if (size < 1) return null;

        @SuppressWarnings("unchecked")
        Ooze smallOoze = new Ooze((EntityType<? extends Ooze>) this.getType(), this.level());
        smallOoze.setSize(size);
        return smallOoze;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    // 重写跳跃相关方法，让Ooze不跳跃
    @Override
    protected int getJumpDelay() {
        return Integer.MAX_VALUE; // 永远不跳跃
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getExperienceReward() * 2; // 因为更难杀死，给予更多经验
    }

    // 爬行攻击AI
    static class OozeMeleeAttackGoal extends MeleeAttackGoal {

        public OozeMeleeAttackGoal(Ooze ooze, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(ooze, speedModifier, followingTargetEvenIfNotSeen);
        }

        @Override
        protected void checkAndPerformAttack(@NotNull LivingEntity enemy) {
            if (this.canPerformAttack(enemy)) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(enemy);
            }
        }

        @Override
        public boolean canPerformAttack(@NotNull LivingEntity enemy) {
            return this.getTicksUntilNextAttack() <= 0 &&
                    this.mob.distanceToSqr(enemy) < this.getAttackReachSqr(enemy);
        }

        protected double getAttackReachSqr(LivingEntity enemy) {
            return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + enemy.getBbWidth();
        }
    }

    // 爬行漫游AI
    static class OozeWanderGoal extends WaterAvoidingRandomStrollGoal {
        private final Ooze ooze;

        public OozeWanderGoal(Ooze ooze, double speedModifier) {
            super(ooze, speedModifier);
            this.ooze = ooze;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.ooze.getTarget() == null;
        }
    }

    // 特殊的爬行移动控制器
    static class OozeMoveControl extends MoveControl {
        private final Ooze ooze;

        public OozeMoveControl(Ooze ooze) {
            super(ooze);
            this.ooze = ooze;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.MOVE_TO && !this.ooze.getNavigation().isDone()) {
                Vec3 vec3 = new Vec3(this.wantedX - this.ooze.getX(), this.wantedY - this.ooze.getY(), this.wantedZ - this.ooze.getZ());
                double length = vec3.length();

                if (length < this.ooze.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    this.ooze.setDeltaMovement(this.ooze.getDeltaMovement().scale(0.5));
                } else {
                    // 平滑的爬行移动，而不是跳跃
                    Vec3 movement = vec3.scale(this.speedModifier * 0.1 / length);
                    this.ooze.setDeltaMovement(this.ooze.getDeltaMovement().add(movement));

                    // 设置合适的移动速度
                    this.ooze.setSpeed((float)(this.speedModifier * this.ooze.getAttributeValue(Attributes.MOVEMENT_SPEED)));

                    // 爬行时的特殊处理
                    if (this.ooze.onClimbable() && this.wantedY > this.ooze.getY()) {
                        this.ooze.setDeltaMovement(this.ooze.getDeltaMovement().add(0, this.ooze.getClimbingSpeed() * 0.2, 0));
                    }

                    // 确保Ooze贴地移动
                    if (this.ooze.onGround()) {
                        Vec3 deltaMovement = this.ooze.getDeltaMovement();
                        this.ooze.setDeltaMovement(deltaMovement.x, Math.max(deltaMovement.y, -0.1), deltaMovement.z);
                    }
                }

                // 设置朝向
                if (length > 0.1) {
                    float yRot = (float) (Mth.atan2(vec3.z, vec3.x) * (180.0 / Math.PI)) - 90.0F;
                    this.ooze.setYRot(this.rotlerp(this.ooze.getYRot(), yRot, 30.0F));
                }
            } else {
                this.ooze.setSpeed(0.0F);
            }
        }
    }
}