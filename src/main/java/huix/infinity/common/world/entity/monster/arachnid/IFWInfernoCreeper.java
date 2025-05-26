package huix.infinity.common.world.entity.monster.arachnid;

import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class IFWInfernoCreeper extends Monster implements PowerableMob {
    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR;
    private static final EntityDataAccessor<Boolean> DATA_IS_POWERED;
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED;
    private int oldSwell;
    private int swell;
    private int maxSwell = 30;
    private int explosionRadius = 6;
    private float maxExplosionDamage = 33.0F;
    private int droppedSkulls;

    public IFWInfernoCreeper(EntityType<? extends IFWInfernoCreeper> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new InfernoCreeperSwellGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6.0F, 1.0, 1.2));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6.0F, 1.0, 1.2));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    public int getMaxFallDistance() {
        return this.getTarget() == null ? this.getComfortableFallDistance(0.0F) : this.getComfortableFallDistance(this.getHealth() - 1.0F);
    }

    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        boolean flag = super.causeFallDamage(fallDistance, multiplier, source);
        this.swell += (int)(fallDistance * 1.5F);
        if (this.swell > this.maxSwell - 5) {
            this.swell = this.maxSwell - 5;
        }
        return flag;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SWELL_DIR, -1);
        builder.define(DATA_IS_POWERED, false);
        builder.define(DATA_IS_IGNITED, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.entityData.get(DATA_IS_POWERED)) {
            compound.putBoolean("powered", true);
        }
        compound.putShort("Fuse", (short)this.maxSwell);
        compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
        compound.putFloat("MaxExplosionDamage", this.maxExplosionDamage);
        compound.putBoolean("ignited", this.isIgnited());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(DATA_IS_POWERED, compound.getBoolean("powered"));
        if (compound.contains("Fuse", 99)) {
            this.maxSwell = compound.getShort("Fuse");
        }
        if (compound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
        if (compound.contains("MaxExplosionDamage", 99)) {
            this.maxExplosionDamage = compound.getFloat("MaxExplosionDamage");
        }
        if (compound.getBoolean("ignited")) {
            this.ignite();
        }
    }

    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explodeCreeper();
            }
        }
        super.tick();
    }

    public void setTarget(@Nullable LivingEntity target) {
        if (!(target instanceof Goat)) {
            super.setTarget(target);
        }
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.CREEPER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CREEPER_DEATH;
    }

    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        Entity entity = damageSource.getEntity();
        if (entity != this && entity instanceof IFWInfernoCreeper infernoCreeper) {
            if (infernoCreeper.canDropMobsSkull()) {
                infernoCreeper.increaseDroppedSkulls();
                this.spawnAtLocation(Items.CREEPER_HEAD);
            }
        }
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && entity instanceof LivingEntity) {
            entity.setRemainingFireTicks(100);
        }
        return flag;
    }

    public boolean isPowered() {
        return this.entityData.get(DATA_IS_POWERED);
    }

    public float getSwelling(float partialTicks) {
        return Mth.lerp(partialTicks, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }

    public int getSwellDir() {
        return this.entityData.get(DATA_SWELL_DIR);
    }

    public void setSwellDir(int state) {
        this.entityData.set(DATA_SWELL_DIR, state);
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt lightning) {
        super.thunderHit(level, lightning);
        this.entityData.set(DATA_IS_POWERED, true);
        // 播放充能音效
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, this.getSoundSource(), 1.0F, 0.8F);
    }

    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(ItemTags.CREEPER_IGNITERS)) {
            SoundEvent soundevent = itemstack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
            this.level().playSound(player, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level().isClientSide) {
                this.ignite();
                if (!itemstack.isDamageableItem()) {
                    itemstack.shrink(1);
                } else {
                    itemstack.hurtAndBreak(1, player, getSlotForHand(hand));
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    private void explodeCreeper() {
        if (!this.level().isClientSide) {
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;

            // 自定义爆炸：保持半径为6，但使用自定义伤害计算
            this.customExplosion(f);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GENERIC_EXPLODE, this.getSoundSource(),
                    3.0F, 0.8F + this.random.nextFloat() * 0.4F);
            // 在爆炸后生成火焰方块
            this.spawnFireBlocks(f);

            this.spawnLingeringCloud();
            this.triggerOnDeathMobEffects(RemovalReason.KILLED);
            this.discard();
        }
    }

    // 自定义爆炸方法
    private void customExplosion(float powerMultiplier) {
        if (this.level().isClientSide) return;

        // 如果是充能状态，使用更大的爆炸范围和伤害
        float actualExplosionRadius = this.isPowered() ? this.explosionRadius * 2.0F : this.explosionRadius;
        float actualMaxDamage = this.isPowered() ? this.maxExplosionDamage * 2.0F : this.maxExplosionDamage;

        // 创建视觉和声音效果（使用较小的爆炸强度以避免过度破坏方块）
        this.level().explode(this, this.getX(), this.getY(), this.getZ(),
                Math.min(6.0F, actualExplosionRadius * powerMultiplier * 0.7F),
                ExplosionInteraction.MOB);

        // 自定义伤害计算
        this.dealCustomExplosionDamage(powerMultiplier, actualExplosionRadius, actualMaxDamage);
    }

    // 自定义伤害计算方法
    private void dealCustomExplosionDamage(float powerMultiplier, float explosionRadius, float maxDamage) {
        float actualMaxDamage = maxDamage * powerMultiplier;
        double explosionRange = explosionRadius * powerMultiplier;

        // 获取爆炸范围内的所有实体
        AABB explosionBounds = new AABB(
                this.getX() - explosionRange, this.getY() - explosionRange, this.getZ() - explosionRange,
                this.getX() + explosionRange, this.getY() + explosionRange, this.getZ() + explosionRange
        );

        List<LivingEntity> entitiesInRange = this.level().getEntitiesOfClass(LivingEntity.class, explosionBounds);

        for (LivingEntity entity : entitiesInRange) {
            if (entity == this) continue; // 跳过自己

            double distance = this.distanceTo(entity);
            if (distance <= explosionRange) {
                // 计算基于距离的伤害衰减
                double damageMultiplier = 1.0 - (distance / explosionRange);
                damageMultiplier = Math.max(0.0, damageMultiplier); // 确保不为负数

                float damage = (float)(actualMaxDamage * damageMultiplier);

                // 应用伤害
                if (damage > 0) {
                    DamageSource explosionDamage = this.damageSources().explosion(this, this);
                    entity.hurt(explosionDamage, damage);

                    // 对受害者施加燃烧效果
                    entity.setRemainingFireTicks(100);

                    // 添加击退效果
                    Vec3 knockbackDirection = entity.position().subtract(this.position()).normalize();
                    double knockbackStrength = damageMultiplier * 0.5; // 击退强度
                    entity.setDeltaMovement(entity.getDeltaMovement().add(
                            knockbackDirection.x * knockbackStrength,
                            knockbackDirection.y * knockbackStrength * 0.5, // 减少垂直击退
                            knockbackDirection.z * knockbackStrength
                    ));

                    // 对玩家添加失明效果
                    if (entity instanceof Player) {
                        // 计算失明持续时间：距离越近，失明时间越长
                        // 最远处(边缘)：1秒(20 ticks)，最近处(中心)：6秒(120 ticks)
                        // 充能状态下失明时长翻倍
                        int baseDuration = (int)(20 + (100 * damageMultiplier));
                        int blindnessDuration = this.isPowered() ? baseDuration * 2 : baseDuration;

                        MobEffectInstance blindnessEffect = new MobEffectInstance(
                                MobEffects.BLINDNESS,
                                blindnessDuration,
                                0,
                                false,
                                true
                        );
                        entity.addEffect(blindnessEffect);
                    }
                }
            }
        }
    }

    private void spawnFireBlocks(float powerMultiplier) {
        if (this.level().isClientSide) return;

        int radius = (int)((this.isPowered() ? this.explosionRadius * 0.8F : this.explosionRadius) * powerMultiplier);
        BlockPos center = this.blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double distance = Math.sqrt(x * x + y * y + z * z);
                    if (distance <= radius) {
                        BlockPos pos = center.offset(x, y, z);

                        double fireChance = 1.0 - (distance / radius);
                        if (this.random.nextDouble() < fireChance * 0.6) {
                            if (this.level().isEmptyBlock(pos) &&
                                    this.level().getBlockState(pos.below()).isSolid()) {
                                this.level().setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }
    }

    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = this.getActiveEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            areaeffectcloud.setRadius(2.5F);
            areaeffectcloud.setRadiusOnUse(-0.5F);
            areaeffectcloud.setWaitTime(10);
            areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());

            for(MobEffectInstance mobeffectinstance : collection) {
                areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
            }

            this.level().addFreshEntity(areaeffectcloud);
        }
    }

    public boolean isIgnited() {
        return this.entityData.get(DATA_IS_IGNITED);
    }

    public void ignite() {
        this.entityData.set(DATA_IS_IGNITED, true);
    }

    public boolean canDropMobsSkull() {
        return this.isPowered() && this.droppedSkulls < 1;
    }

    public void increaseDroppedSkulls() {
        ++this.droppedSkulls;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    // 获取和设置最大爆炸伤害的方法
    public float getMaxExplosionDamage() {
        return this.maxExplosionDamage;
    }

    public void setMaxExplosionDamage(float damage) {
        this.maxExplosionDamage = damage;
    }

    static {
        DATA_SWELL_DIR = SynchedEntityData.defineId(IFWInfernoCreeper.class, EntityDataSerializers.INT);
        DATA_IS_POWERED = SynchedEntityData.defineId(IFWInfernoCreeper.class, EntityDataSerializers.BOOLEAN);
        DATA_IS_IGNITED = SynchedEntityData.defineId(IFWInfernoCreeper.class, EntityDataSerializers.BOOLEAN);
    }

    public static class InfernoCreeperSwellGoal extends net.minecraft.world.entity.ai.goal.Goal {
        private final IFWInfernoCreeper creeper;
        private LivingEntity target;

        public InfernoCreeperSwellGoal(IFWInfernoCreeper creeper) {
            this.creeper = creeper;
            this.setFlags(java.util.EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.creeper.getTarget();
            return this.creeper.getSwellDir() > 0 || livingentity != null && this.creeper.distanceToSqr(livingentity) < 9.0;
        }

        public void start() {
            this.creeper.getNavigation().stop();
            this.target = this.creeper.getTarget();
        }

        public void stop() {
            this.target = null;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.target == null) {
                this.creeper.setSwellDir(-1);
            } else if (this.creeper.distanceToSqr(this.target) > 49.0) {
                this.creeper.setSwellDir(-1);
            } else if (!this.creeper.getSensing().hasLineOfSight(this.target)) {
                this.creeper.setSwellDir(-1);
            } else {
                this.creeper.setSwellDir(1);
            }
        }
    }
}