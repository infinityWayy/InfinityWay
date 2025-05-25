package huix.infinity.common.world.entity.monster.arachnid;

import huix.infinity.common.world.entity.projectile.IFWWebProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * IFW蜘蛛纲目基类 - 继承Monster并融合原版Spider和MITE特性
 */
public abstract class IFWArachnid extends Monster {

    // 数据同步器 - 原版Spider的爬墙标志
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(IFWArachnid.class, EntityDataSerializers.BYTE);

    // 蛛网数量系统
    private static final EntityDataAccessor<Integer> WEB_COUNT = SynchedEntityData.defineId(IFWArachnid.class, EntityDataSerializers.INT);

    // 蛛网射击相关计时器
    protected int webShootTimer = 0;

    // 跳跃攻击冷却时间
    protected int jumpAttackCooldown = 0;

    // 原版Spider常量
    private static final float SPIDER_SPECIAL_EFFECT_CHANCE = 0.1F;

    // 预判机制
    private static final int JUMP_ATTACK_CHANCE = 10; // 10%概率跳跃攻击
    private static final float JUMP_ATTACK_MIN_DISTANCE = 2.0F;
    private static final float JUMP_ATTACK_MAX_DISTANCE = 6.0F;
    private static final double WEB_SHOOT_RANGE = 8.0D;
    private static final int PREDICTION_LEAD = 10; // 10tick预判提前量
    private static final float WEB_PROJECTILE_SPEED = 0.8F;
    private static final float GRAVITY_COMPENSATION_FACTOR = 0.2F;

    public IFWArachnid(EntityType<? extends IFWArachnid> entityType, Level level) {
        super(entityType, level);

        // 蛛网数量初始化
        if (!level.isClientSide && !isPhaseSpider()) {
            initializeWebCount();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (byte)0);  // 原版Spider爬墙标志
        builder.define(WEB_COUNT, 0);            // 蛛网数量
    }

    /**
     * 蛛网数量初始化
     * 普通蜘蛛：70%概率0个，20%概率1个，10%概率2个
     * 特殊蜘蛛：60%概率0个，30%概率1个，10%概率2个
     */
    protected void initializeWebCount() {
        RandomSource random = this.level().getRandom();
        int webCount = random.nextInt(4); // 0-3个蛛网，各25%概率

        // 如果有蛛网且不是特殊类型，减少1个
        if (webCount > 0 && !isSpecialSpiderType()) {
            webCount--;
        }

        setWebCount(webCount);
    }

    /**
     * 原版Spider的导航系统 - 支持爬墙
     */
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    /**
     * 原版Spider的属性设置
     */
    public static AttributeSupplier.Builder createArachnidAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)    // 原版Spider血量
                .add(Attributes.MOVEMENT_SPEED, 0.3D) // 原版Spider速度
                .add(Attributes.FOLLOW_RANGE, 28.0D)  // 跟随距离
                .add(Attributes.ATTACK_DAMAGE, 4.0D); // 攻击力
    }

    @Override
    protected void registerGoals() {
        // 基础AI目标 - 按原版Spider顺序
        this.goalSelector.addGoal(1, new FloatGoal(this));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Armadillo.class, 6.0F, 1.0D, 1.2D,
                (armadillo) -> !((Armadillo)armadillo).isScared())); // 转换为Armadillo类型

        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F)); // 原版跳跃攻击
        this.goalSelector.addGoal(4, new IFWArachnidAttackGoal(this));  // 增强的近战攻击
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        // 格蛛网射击目标
        this.goalSelector.addGoal(2, new WebShootGoal(this));

        // 目标选择
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new IFWSpiderTargetGoal<>(this, Player.class));
        this.targetSelector.addGoal(3, new IFWSpiderTargetGoal<>(this, IronGolem.class));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Chicken.class, true)); // 鸡类目标
    }

    @Override
    public void tick() {
        super.tick();

        // 更新计时器
        if (webShootTimer > 0) webShootTimer--;
        if (jumpAttackCooldown > 0) jumpAttackCooldown--;

        if (!this.level().isClientSide) {
            // 原版Spider爬墙逻辑
            this.setClimbing(this.horizontalCollision);

            // 和平检查
            checkSwitchingToPeaceful();

            // 蛛网射击检查
            checkWebShooting();
        }
    }

    /**
     * 原版Spider音效系统
     */
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    /**
     * 原版Spider爬墙系统
     */
    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean climbing) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (climbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }
        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    /**
     * 原版Spider蛛网免疫
     */
    @Override
    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier) {
        if (!state.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(state, motionMultiplier);
        }
    }

    /**
     * 原版Spider毒素免疫
     */
    @Override
    public boolean canBeAffected(MobEffectInstance potionEffect) {
        return potionEffect.is(MobEffects.POISON) ? false : super.canBeAffected(potionEffect);
    }

    /**
     * 原版Spider骷髅骑乘和随机效果
     */
    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);

        RandomSource randomsource = level.getRandom();

        // 原版1%概率生成骷髅骑手
        if (randomsource.nextInt(100) == 0) {
            Skeleton skeleton = EntityType.SKELETON.create(this.level());
            if (skeleton != null) {
                skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                skeleton.finalizeSpawn(level, difficulty, spawnType, null);
                skeleton.startRiding(this);
            }
        }

        // 原版随机效果系统
        if (spawnGroupData == null) {
            spawnGroupData = new SpiderEffectsGroupData();
            if (level.getDifficulty() == Difficulty.HARD &&
                    randomsource.nextFloat() < SPIDER_SPECIAL_EFFECT_CHANCE * difficulty.getSpecialMultiplier()) {
                ((SpiderEffectsGroupData)spawnGroupData).setRandomEffect(randomsource);
            }
        }

        if (spawnGroupData instanceof SpiderEffectsGroupData spider$spidereffectsgroupdata) {
            Holder<MobEffect> holder = spider$spidereffectsgroupdata.effect;
            if (holder != null) {
                this.addEffect(new MobEffectInstance(holder, -1));
            }
        }

        return spawnGroupData;
    }

    /**
     * 原版Spider载具附着点
     */
    @Override
    public Vec3 getVehicleAttachmentPoint(Entity entity) {
        return entity.getBbWidth() <= this.getBbWidth() ?
                new Vec3(0.0D, 0.3125D * this.getScale(), 0.0D) :
                super.getVehicleAttachmentPoint(entity);
    }

    /**
     * 和平模式检查
     */
    protected void checkSwitchingToPeaceful() {
        LivingEntity target = getTarget();
        if (target instanceof Player &&
                peacefulDuringDay() &&
                getLightLevelDependentMagicValue() > 0.5F &&
                isOutdoors() &&
                !(getLastHurtByMob() instanceof Player) &&
                this.random.nextInt(100) == 0) {

            setTarget(null);
        }
    }

    /**
     * 蛛网射击检查
     */
    protected void checkWebShooting(
    ) {
        if (getWebCount() > 0 && this.tickCount % getTicksBetweenWebThrows() == 0) {
            LivingEntity target = getTarget();
            if (target != null) {
                double distance = distanceTo(target);
                if (distance <= WEB_SHOOT_RANGE) {
                    if (performRaycastCheck(target)) {
                        attackEntityWithRangedAttack(target, 1.0F);
                    }
                }
            }
        }
    }

    /**
     * 射线检测
     */
    protected boolean performRaycastCheck(LivingEntity target) {
        Vec3 eyePos = this.getEyePosition();
        Vec3 targetEyePos = target.getEyePosition();

        ClipContext eyeContext = new ClipContext(
                eyePos, targetEyePos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this
        );

        BlockHitResult eyeHit = this.level().clip(eyeContext);

        if (eyeHit.getType() != HitResult.Type.MISS) {
            Vec3 targetFeetPos = target.position().add(0, target.getBbHeight() * 0.25F, 0);
            ClipContext feetContext = new ClipContext(
                    eyePos, targetFeetPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this
            );

            BlockHitResult feetHit = this.level().clip(feetContext);
            return feetHit.getType() == HitResult.Type.MISS;
        }

        return true;
    }

    /**
     * 发射蛛网
     */
    public void attackEntityWithRangedAttack(LivingEntity target, float accuracy) {
        IFWWebProjectileEntity webProjectile = new IFWWebProjectileEntity(this.level(), this);

        // 预判逻辑
        double deltaX = target.position().x + (target.getDeltaMovement().x * PREDICTION_LEAD) - this.getX();
        double deltaY = target.getY() + target.getEyeHeight() - 1.1F - webProjectile.getY();
        double deltaZ = target.position().z + (target.getDeltaMovement().z * PREDICTION_LEAD) - this.getZ();

        // 重力补偿
        float horizontalDistance = (float) Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float gravityCompensation = horizontalDistance * GRAVITY_COMPENSATION_FACTOR;

        webProjectile.shoot(deltaX, deltaY + gravityCompensation, deltaZ, WEB_PROJECTILE_SPEED, 0.0F);

        this.playSound(SoundEvents.ARROW_SHOOT, 1.0F,
                1.0F / (this.random.nextFloat() * 0.4F + 0.8F));

        this.level().addFreshEntity(webProjectile);

        // 火焰效果
        if (isSpecialSpiderType() || this.isOnFire()) {
            webProjectile.setRemainingFireTicks(200);
        }

        setWebCount(getWebCount() - 1);
    }

    /**
     * 跳跃攻击
     */
    protected void performJumpAttack(Entity target, float distance) {
        if (distance > JUMP_ATTACK_MIN_DISTANCE &&
                distance < JUMP_ATTACK_MAX_DISTANCE &&
                canJump() &&
                this.random.nextInt(JUMP_ATTACK_CHANCE) == 0 &&
                this.onGround() &&
                jumpAttackCooldown <= 0) {

            double deltaX = target.getX() - this.getX();
            double deltaZ = target.getZ() - this.getZ();
            float horizontalDistance = (float) Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

            this.setDeltaMovement(
                    deltaX / horizontalDistance * 0.5F * 0.8F + this.getDeltaMovement().x * 0.2F,
                    0.4F,
                    deltaZ / horizontalDistance * 0.5F * 0.8F + this.getDeltaMovement().z * 0.2F
            );

            this.setYRot((float) Math.toDegrees(Math.atan2(-deltaX, deltaZ)));
            jumpAttackCooldown = 60;
        }
    }

    /**
     * 蛛丝掉落设定
     */
    @Override
    protected void dropFromLootTable(DamageSource damageSource, boolean hitByPlayer) {
        super.dropFromLootTable(damageSource, hitByPlayer);

        // 只有被玩家击杀才掉落剩余蛛网为蛛丝
        if (hitByPlayer) {
            int webCount = getWebCount();

            // 相位蜘蛛不掉落蛛丝
            if (this instanceof IFWPhaseSpider) {
                return; // 相位蜘蛛不掉落任何蛛丝
            }

            // 其他蜘蛛掉落剩余蛛网为蛛丝
            for (int i = 0; i < webCount; i++) {
                this.spawnAtLocation(net.minecraft.world.item.Items.STRING);
            }
        }
    }
    // 抽象和虚拟方法
    protected boolean isPhaseSpider() {
        return false;
    }

    protected boolean isSpecialSpiderType() {
        return false;
    }

    protected int getTicksBetweenWebThrows() {
        return isSpecialSpiderType() ? 200 : 500;
    }

    protected boolean peacefulDuringDay() {
        return true;
    }

    public boolean canJump() {
        return true;
    }

    protected boolean isOutdoors() {
        BlockPos pos = blockPosition();
        return this.level().getBrightness(LightLayer.SKY, pos) > 0;
    }

    public boolean requiresLineOfSightToTargets() {
        return false;
    }

    protected float getExperienceMultiplier() {
        return 1.0f;
    }

    public int getExperienceReward(ServerLevel serverLevel, Player player) {
        int baseExperience = super.getExperienceReward(serverLevel, player);
        float multiplier = getExperienceMultiplier();
        return Math.round(baseExperience * multiplier);
    }

    // Getter/Setter 方法
    public int getWebCount() {
        return this.entityData.get(WEB_COUNT);
    }

    public void setWebCount(int count) {
        this.entityData.set(WEB_COUNT, Math.max(0, count));
    }

    // NBT 保存/读取
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("num_webs", (byte)getWebCount());
        compound.putInt("WebShootTimer", webShootTimer);
        compound.putInt("JumpAttackCooldown", jumpAttackCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setWebCount(compound.getByte("num_webs"));
        webShootTimer = compound.getInt("WebShootTimer");
        jumpAttackCooldown = compound.getInt("JumpAttackCooldown");
    }

    // 原版Spider效果数据类
    public static class SpiderEffectsGroupData implements SpawnGroupData {
        @Nullable
        public Holder<MobEffect> effect;

        public void setRandomEffect(RandomSource random) {
            int i = random.nextInt(5);
            if (i <= 1) {
                this.effect = MobEffects.MOVEMENT_SPEED;
            } else if (i <= 2) {
                this.effect = MobEffects.DAMAGE_BOOST;
            } else if (i <= 3) {
                this.effect = MobEffects.REGENERATION;
            } else if (i <= 4) {
                this.effect = MobEffects.INVISIBILITY;
            }
        }
    }

    // AI 目标类
    private static class WebShootGoal extends Goal {
        private final IFWArachnid arachnid;
        private LivingEntity target;

        public WebShootGoal(IFWArachnid arachnid) {
            this.arachnid = arachnid;
        }

        @Override
        public boolean canUse() {
            this.target = this.arachnid.getTarget();
            return this.target != null &&
                    this.arachnid.getWebCount() > 0 &&
                    this.arachnid.distanceTo(this.target) <= WEB_SHOOT_RANGE;
        }

        @Override
        public void tick() {
            if (this.target != null) {
                this.arachnid.getLookControl().setLookAt(this.target);
            }
        }
    }

    private static class IFWArachnidAttackGoal extends MeleeAttackGoal {
        private final IFWArachnid arachnid;

        public IFWArachnidAttackGoal(IFWArachnid arachnid) {
            super(arachnid, 1.0D, true);
            this.arachnid = arachnid;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.mob.isVehicle(); // 原版Spider逻辑
        }

        @Override
        public boolean canContinueToUse() {
            // 原版Spider光照检查逻辑
            float f = this.mob.getLightLevelDependentMagicValue();
            if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
                this.mob.setTarget(null);
                return false;
            }
            return super.canContinueToUse();
        }

        @Override
        public void tick() {
            super.tick();

            // 跳跃攻击
            LivingEntity target = this.arachnid.getTarget();
            if (target != null) {
                float distance = this.arachnid.distanceTo(target);
                this.arachnid.performJumpAttack(target, distance);
            }
        }
    }

    private static class IFWSpiderTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        private final IFWArachnid arachnid;

        public IFWSpiderTargetGoal(IFWArachnid spider, Class<T> entityTypeToTarget) {
            super(spider, entityTypeToTarget, true);
            this.arachnid = spider;
        }

        @Override
        public boolean canUse() {
            // 原版Spider光照检查 + 和平逻辑
            float brightness = this.arachnid.getLightLevelDependentMagicValue();
            if (brightness >= 0.5F && this.arachnid.peacefulDuringDay() && this.arachnid.isOutdoors()) {
                return false;
            }
            return super.canUse();
        }
    }
}