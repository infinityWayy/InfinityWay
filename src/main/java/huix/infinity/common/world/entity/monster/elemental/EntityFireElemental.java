package huix.infinity.common.world.entity.monster.elemental;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityFireElemental extends Monster {
    private int ticksUntilNextFireSound = 0;
    private int ticksUntilNextFizzSound = 0;

    public EntityFireElemental(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypes.DROWN)) return false;
        if (source.getDirectEntity() instanceof Snowball) return false;
        if (source.is(DamageTypes.MAGIC)) {
            Entity direct = source.getDirectEntity();
            if (direct instanceof net.minecraft.world.entity.projectile.Arrow arrow) {
                if (arrow.getOwner() instanceof LivingEntity living) {
                    ItemStack bow = living.getMainHandItem();
                    Registry<Enchantment> enchantmentRegistry = level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
                    Holder<Enchantment> flameHolder = enchantmentRegistry.getHolderOrThrow(Enchantments.FLAME);
                    if (!bow.isEnchanted() || bow.getEnchantmentLevel(flameHolder) <= 0) {
                        return false;
                    }
                }
            }
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide && this.isInWaterOrRain()) {
            for (int i = 0; i < (this.isInWater() ? 10 : 1); ++i) {
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.CLOUD,
                        this.getX() + (this.random.nextDouble() - 0.5) * this.getBbWidth(),
                        this.getY() + this.random.nextDouble() * this.getBbHeight(),
                        this.getZ() + (this.random.nextDouble() - 0.5) * this.getBbWidth(),
                        0.0, 0.05, 0.0);
            }
        }
        if (!this.level().isClientSide) {
            if (this.tickCount % 40 == 0) {
                this.hurt(this.damageSources().drown(), 1.0F);
            }
            if (this.isInWaterOrRain()) {
                if (--ticksUntilNextFizzSound <= 0) {
                    this.playSound(SoundEvents.FIRE_EXTINGUISH, 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                    this.ticksUntilNextFizzSound = this.random.nextInt(7) + 2;
                    if (this.random.nextInt(this.isInWater() ? 1 : 4) == 0) {
                        this.hurt(this.damageSources().drown(), 1.0F);
                    }
                }
            } else if (--ticksUntilNextFireSound <= 0) {
                this.playSound(SoundEvents.FIRE_AMBIENT, 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F);
                this.ticksUntilNextFireSound = this.random.nextInt(21) + 30;
                if (this.isInLava()) {
                    this.heal(4.0F);
                }
            }
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.ticksUntilNextFireSound = tag.getInt("FireSoundTick");
        this.ticksUntilNextFizzSound = tag.getInt("FizzSoundTick");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("FireSoundTick", this.ticksUntilNextFireSound);
        tag.putInt("FizzSoundTick", this.ticksUntilNextFizzSound);
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof LivingEntity living) {
            ItemStack weapon = living.getMainHandItem();
            if (!weapon.isEnchanted()) {
                this.playSound(SoundEvents.FIRE_EXTINGUISH, 0.5F, 2.0F);
                return false;
            }
        }
        return super.hurt(damageSource, amount);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result && target instanceof LivingEntity living) {
            living.setRemainingFireTicks(120);
        }
        return result;
    }

    @Override
    public boolean isOnFire() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.EMPTY;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, net.minecraft.world.level.block.state.@NotNull BlockState blockIn) {}

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 3;
    }

}