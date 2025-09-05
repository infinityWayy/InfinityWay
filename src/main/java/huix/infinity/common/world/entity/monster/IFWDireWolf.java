package huix.infinity.common.world.entity.monster;

import huix.infinity.common.world.entity.ai.HostileToPlayersTargetGoal;
import huix.infinity.common.world.entity.ai.NonTamedTargetGoal;
import huix.infinity.common.world.entity.mob.IFWChicken;
import huix.infinity.common.world.entity.mob.IFWCow;
import huix.infinity.common.world.entity.mob.IFWPig;
import huix.infinity.common.world.entity.mob.IFWSheep;
import huix.infinity.util.WorldHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class IFWDireWolf extends TamableAnimal implements NeutralMob {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(IFWDireWolf.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> DATA_ANGER_TARGET = SynchedEntityData.defineId(IFWDireWolf.class, EntityDataSerializers.OPTIONAL_UUID);

    private int tamingCooldown = 0;
    private int angerCooldown = 0;
    private DyeColor collarColor = DyeColor.RED;
    private boolean wasWet = false;
    private boolean isShaking = false;
    private float shakeAnim = 0.0F, shakeAnimO = 0.0F;
    private float interestedAngle = 0.0F, interestedAngleO = 0.0F;
    private boolean interested = false;
    private int hostileToPlayersCountdown = 0;
    private boolean witchAlly = false;
    private boolean isAttacking = false;

    public IFWDireWolf(EntityType<? extends IFWDireWolf> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
        builder.define(DATA_ANGER_TARGET, Optional.empty());
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(4, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new HostileToPlayersTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NonTamedTargetGoal<>(this, IFWSheep.class, false));
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, IFWCow.class, false));
        this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, IFWPig.class, false));
        this.targetSelector.addGoal(6, new NonTamedTargetGoal<>(this, IFWChicken.class, false));
        this.targetSelector.addGoal(7, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(8, new OwnerHurtTargetGoal(this));
    }

    public boolean isHostileToPlayers() {
        return hostileToPlayersCountdown > 0;
    }

    public void setHostileToPlayers(int ticks) {
        this.hostileToPlayersCountdown = ticks;
    }

    public void setWitchAlly(boolean flag) {
        this.witchAlly = flag;
    }

    public boolean isWitchAlly() {
        return this.witchAlly;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isAttacking() {
        return this.isAttacking;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);

            double expected = isTame() ? 24.0D : 16.0D;
            if (getAttribute(Attributes.MAX_HEALTH) != null && Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).getBaseValue() != expected) {
                Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(expected);
            }
            if (!isBaby() && !isTame() && !isInLove() && !isBlueMoonNight(level()) && this.random.nextFloat() < 0.004F) {
                Player player = level().getNearestPlayer(this, 4.0F);
                if (player != null) {
                    setTarget(player);
                    angerCooldown = 800 + random.nextInt(100);
                }
            }
            if (angerCooldown > 0) {
                angerCooldown--;
                if (angerCooldown == 0) setTarget(null);
            }
        }
        if (!level().isClientSide) {
            if (hostileToPlayersCountdown > 0) {
                hostileToPlayersCountdown--;
            }
            if (witchAlly) {
                hostileToPlayersCountdown = 9999999;
            }
        }
        if (tamingCooldown > 0) tamingCooldown--;
        shakeAnimO = shakeAnim;
        if (this.isDireWolfWet()) {
            wasWet = true;
            isShaking = false;
            shakeAnim = 0.0F;
            shakeAnimO = 0.0F;
        } else if (wasWet && !this.isDireWolfWet() && !isShaking && this.onGround()) {
            isShaking = true;
            shakeAnim = 0.0F;
            shakeAnimO = 0.0F;
        } else if ((wasWet || isShaking) && isShaking) {
            if (shakeAnim == 0.0F) {
                this.playSound(SoundEvents.WOLF_SHAKE, getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }
            shakeAnimO = shakeAnim;
            shakeAnim += 0.05F;
            if (shakeAnimO >= 2.0F) {
                wasWet = false;
                isShaking = false;
                shakeAnimO = 0.0F;
                shakeAnim = 0.0F;
            }
            if (shakeAnim > 0.4F && level().isClientSide) {
                float y = (float) this.getY();
                int count = (int) (Math.sin((shakeAnim - 0.4F) * Math.PI) * 7.0F);
                for (int i = 0; i < count; ++i) {
                    double dx = (random.nextFloat() * 2.0 - 1.0) * this.getBbWidth() * 0.5;
                    double dz = (random.nextFloat() * 2.0 - 1.0) * this.getBbWidth() * 0.5;
                    level().addParticle(net.minecraft.core.particles.ParticleTypes.SPLASH,
                            getX() + dx, y + 0.8, getZ() + dz,
                            getDeltaMovement().x, getDeltaMovement().y, getDeltaMovement().z);
                }
            }
        }
        this.interestedAngleO = this.interestedAngle;
        this.interested = false;
        Player nearest = this.level().getNearestPlayer(this, 8.0D);
        if (!isAngry() && nearest != null && (nearest.getMainHandItem().is(Items.BONE) || nearest.getOffhandItem().is(Items.BONE))) {
            this.interested = true;
        }
        if (isInterested()) {
            this.interestedAngle += (1.0F - this.interestedAngle) * 0.4F;
        } else {
            this.interestedAngle += (0.0F - this.interestedAngle) * 0.4F;
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (!level().isClientSide && !isTame()) {
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity living) {
                this.setPersistentAngerTarget(living.getUUID());
                this.setRemainingPersistentAngerTime(600 + random.nextInt(400));
                this.setTarget(living);
            }
        }
        return result;
    }

    private boolean isBlueMoonNight(LevelAccessor level) {
        return WorldHelper.isBlueMoon(level);
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 2;
    }

    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (isBaby() || isTame() || isInLove() || isBlueMoonNight(level()) || (getTarget() == null && this.random.nextFloat() < 0.1F))
            return SoundEvents.WOLF_AMBIENT;
        return SoundEvents.WOLF_GROWL;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.WOLF_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 1.5F;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.8F;
    }

    public boolean isDireWolfWet() {
        return wasWet;
    }

    public boolean isDireWolfShaking() {
        return isShaking;
    }

    @Override
    public boolean isAngry() {
        return getRemainingPersistentAngerTime() > 0 && getPersistentAngerTarget() != null && !isTame();
    }

    public boolean isInterested() {
        return this.interested;
    }

    public float getHeadRollAngle(float partialTick) {
        if (this.isDireWolfShaking()) {
            float shake = shakeAnimO + (shakeAnim - shakeAnimO) * partialTick;
            return shake * 0.15F * (float) Math.PI;
        } else if (isInterested()) {
            return Mth.lerp(partialTick, this.interestedAngleO, this.interestedAngle) * 0.15F * (float) Math.PI;
        } else {
            return 0.0F;
        }
    }

    public float getBodyRollAngle(float partialTick, float offset) {
        if (!this.isDireWolfShaking()) return 0.0F;
        float f = (shakeAnimO + (shakeAnim - shakeAnimO) * partialTick + offset) / 1.8F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        return Mth.sin(f * (float) Math.PI) * Mth.sin(f * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
    }

    public DyeColor getCollarColor() {
        return collarColor;
    }

    public void setCollarColor(DyeColor color) {
        this.collarColor = color;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TamingCooldown", tamingCooldown);
        tag.putInt("AngerCooldown", angerCooldown);
        tag.putString("CollarColor", collarColor.getName());
        tag.putBoolean("WasWet", wasWet);
        tag.putBoolean("IsShaking", isShaking);
        tag.putFloat("ShakeAnim", shakeAnim);
        tag.putFloat("ShakeAnimO", shakeAnimO);
        tag.putInt("HostileToPlayersCountdown", hostileToPlayersCountdown);
        tag.putBoolean("WitchAlly", witchAlly);
        tag.putBoolean("IsAttacking", isAttacking);
        this.addPersistentAngerSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        tamingCooldown = tag.getInt("TamingCooldown");
        angerCooldown = tag.getInt("AngerCooldown");
        try {
            collarColor = DyeColor.byName(tag.getString("CollarColor"), DyeColor.RED);
        } catch (Exception e) {
            collarColor = DyeColor.RED;
        }
        wasWet = tag.getBoolean("WasWet");
        isShaking = tag.getBoolean("IsShaking");
        shakeAnim = tag.getFloat("ShakeAnim");
        shakeAnimO = tag.getFloat("ShakeAnimO");
        hostileToPlayersCountdown = tag.getInt("HostileToPlayersCountdown");
        witchAlly = tag.getBoolean("WitchAlly");
        isAttacking = tag.getBoolean("IsAttacking");
        this.readPersistentAngerSaveData(this.level(), tag);
    }

    private int getTamingOutcome(Player player) {
        float roll = this.random.nextFloat();
        if (roll < 0.2F) return -1;
        else if (roll < 0.4F) return 0;
        else if (roll > 0.95F) return 1;
        else {
            roll += this.random.nextFloat() * player.experienceLevel * 0.02F;
            return roll < 0.5F ? -1 : (roll < 1.0F ? 0 : 1);
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.isTame() && stack.getItem() instanceof DyeItem dye) {
            DyeColor color = dye.getDyeColor();
            if (color != this.getCollarColor()) {
                this.setCollarColor(color);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                return InteractionResult.sidedSuccess(level().isClientSide);
            }
        }
        if (!isTame() && !isAngry() && stack.is(Items.BONE) && tamingCooldown == 0) {
            if (!level().isClientSide) {
                int outcome = getTamingOutcome(player);
                tamingCooldown = 100;
                stack.shrink(1);
                if (outcome > 0) {
                    this.tame(player);
                    this.setOrderedToSit(true);
                    this.setCollarColor(DyeColor.RED);
                    this.level().broadcastEntityEvent(this, (byte)7);
                    if (this.random.nextBoolean()) {
                        this.playSound(SoundEvents.WOLF_AMBIENT, 1.0F, 1.0F);
                    } else {
                        this.playSound(SoundEvents.WOLF_PANT, 1.0F, 1.0F);
                    }
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                    this.playSound(SoundEvents.WOLF_WHINE, 1.0F, 1.0F);
                    if (outcome < 0 && !isBlueMoonNight(level())) {
                        setTarget(player);
                        angerCooldown = 800 + random.nextInt(100);
                    }
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        if (this.isTame() && isOwnedBy(player) && !level().isClientSide && !(stack.getItem() instanceof DyeItem) && !stack.is(Items.BONE)) {
            this.setOrderedToSit(!this.isOrderedToSit());
            this.jumping = false;
            this.getNavigation().stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 7) {
            for (int i = 0; i < 7; ++i) {
                double dx = this.random.nextGaussian() * 0.02D;
                double dy = this.random.nextGaussian() * 0.02D;
                double dz = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.HEART,
                        this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D),
                        dx, dy, dz);
            }
        } else if (id == 6) {
            for (int i = 0; i < 7; ++i) {
                double dx = this.random.nextGaussian() * 0.02D;
                double dy = this.random.nextGaussian() * 0.02D;
                double dz = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.SMOKE,
                        this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D),
                        dx, dy, dz);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target instanceof Player && !this.isTame()) {
            setHostileToPlayers(800 + random.nextInt(100));
        }
        if (target != null && !this.isTame()) {
            angerCooldown = 800 + random.nextInt(100);
        }
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return this.entityData.get(DATA_ANGER_TARGET).orElse(null);
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.entityData.set(DATA_ANGER_TARGET, Optional.ofNullable(uuid));
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(600 + this.random.nextInt(400));
    }

}