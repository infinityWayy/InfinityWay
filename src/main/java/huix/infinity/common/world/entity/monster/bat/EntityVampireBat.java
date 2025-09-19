package huix.infinity.common.world.entity.monster.bat;

import huix.infinity.common.world.entity.mob.Livestock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.util.Mth;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityVampireBat extends Monster {
    private int attackCooldown;
    private int feedCooldown;
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(EntityVampireBat.class, EntityDataSerializers.BYTE);
    private static final int FLAG_RESTING = 1;public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState restAnimationState = new AnimationState();

    public EntityVampireBat(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        if (!level.isClientSide) {
            this.setResting(true);
        }
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Bat.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Livestock.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, true));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.feedCooldown = tag.getInt("FeedCooldown");
        this.entityData.set(DATA_ID_FLAGS, tag.getByte("BatFlags"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("FeedCooldown", this.feedCooldown);
        tag.putByte("BatFlags", this.entityData.get(DATA_ID_FLAGS));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
        if (this.feedCooldown > 0) {
            if (this.getHealth() < this.getMaxHealth()) {
                this.feedCooldown = 0;
            } else if (--this.feedCooldown > 0) {
                LivingEntity target = this.getTarget();
                if (target != null && !this.preysUpon(target)) {
                    this.setTarget(null);
                }
            }
        }
        // Hanging/flying behavior
        BlockPos blockpos = this.blockPosition();
        BlockPos blockpos1 = blockpos.above();
        if (this.isResting()) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setPosRaw(this.getX(), (double)Mth.floor(this.getY()) + 1.0F - this.getBbHeight(), this.getZ());
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }
        // Hanging/flying state switch
        boolean canHang = !this.isResting()
                && this.getTarget() == null
                && this.level().getBlockState(blockpos1).isRedstoneConductor(this.level(), blockpos);
        boolean shouldFly = this.isResting() && (this.getTarget() != null || !this.level().getBlockState(blockpos1).isRedstoneConductor(this.level(), blockpos));
        if (canHang) {
            this.setResting(true);
        } else if (shouldFly) {
            this.setResting(false);
        }
        this.setupAnimationStates();
    }

    private void setupAnimationStates() {
        if (this.isResting()) {
            this.flyAnimationState.stop();
            this.restAnimationState.startIfStopped(this.tickCount);
        } else {
            this.restAnimationState.stop();
            this.flyAnimationState.startIfStopped(this.tickCount);
        }
    }

    protected boolean preysUpon(Entity entity) {
        if (this.feedCooldown > 0) {
            return entity instanceof Player player && !player.isCreative();
        } else {
            return (entity instanceof Player player && !player.isCreative())
                    || entity instanceof Animal
                    || entity instanceof Livestock
                    || entity instanceof Villager;
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        if (this.attackCooldown > 0) return false;
        if (target == this.getTarget() && this.distanceTo(target) < 1.5) {

            boolean result = super.doHurtTarget(target);
            if (result) {
                this.heal(2.0F);
                if (target instanceof Animal animal && animal.getHealth() > 0.0F && animal.getTarget() == null) {
                    animal.setTarget(this);
                }
                if (!(this instanceof EntityGiantVampireBat) && this.getHealth() >= this.getMaxHealth()) {
                    this.feedCooldown = 1200;
                }
            }
            this.attackCooldown = 20;
        }
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_FLAGS, (byte)0);
    }

    public boolean isResting() {
        return (this.entityData.get(DATA_ID_FLAGS) & FLAG_RESTING) != 0;
    }

    public void setResting(boolean resting) {
        byte b0 = this.entityData.get(DATA_ID_FLAGS);
        if (resting) {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | FLAG_RESTING));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~FLAG_RESTING));
        }
    }

    @Override
    public boolean isNoAi() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    public boolean isNightwing() {
        return false;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.BAT_AMBIENT;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.BAT_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.BAT_DEATH;
    }
}