package huix.infinity.common.world.entity.monster.gelatinous;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagmaCube extends GelatinousCube {
    private int ticksUntilNextFizzSound;

    public MagmaCube(EntityType<? extends MagmaCube> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isInWater()) {
            if (this.level().isClientSide) {
                for (int i = 0; i < (this.isInWater() ? 10 : 1); ++i) {
                    this.level().addParticle(ParticleTypes.LARGE_SMOKE,
                            this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0),
                            0.0, 0.0, 0.0);
                }
            } else if (--this.ticksUntilNextFizzSound <= 0) {
                this.playSound(SoundEvents.LAVA_EXTINGUISH, 0.7F,
                        1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                this.ticksUntilNextFizzSound = this.random.nextInt(7) + 2;

                if (this.random.nextInt(this.isInWater() ? 4 : 16) == 0) {
                    this.hurt(this.damageSources().drown(), 1.0F);
                }
            }
        }
    }

    @Override
    public boolean canCorodeBlocks() {
        return false;
    }

    @Override
    public boolean canDissolveItem(ItemStack item) {
        return false;
    }

    @Override
    public boolean damageItem(ItemEntity item) {
        return false;
    }

    @Override
    public boolean canDamageItem(ItemStack item) {
        return false;
    }

    @Override
    public boolean isAcidic() {
        return false;
    }

    @Override
    public int getAttackStrengthMultiplier() {
        return 2;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.ON_FIRE) ||
                damageSource.is(DamageTypes.LAVA) ||
                damageSource.is(DamageTypes.HOT_FLOOR)) {
            return false;
        }

        if (damageSource.is(DamageTypes.DROWN)) {
            return super.hurt(damageSource, amount * 2.0f);
        }

        return super.hurt(damageSource, amount);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);

        if (this.getSize() > 1) {
            int drops = this.random.nextInt(4) - 2;
            for (int i = 0; i < drops; ++i) {
                this.spawnAtLocation(Items.MAGMA_CREAM);
            }
        }
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return this.getSize() > 1 ? SoundEvents.MAGMA_CUBE_HURT : SoundEvents.MAGMA_CUBE_HURT_SMALL;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getDeathSound() {
        return this.getSize() > 1 ? SoundEvents.MAGMA_CUBE_DEATH : SoundEvents.MAGMA_CUBE_DEATH_SMALL;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getSquishSound() {
        return this.getSize() > 1 ? SoundEvents.MAGMA_CUBE_SQUISH : SoundEvents.MAGMA_CUBE_SQUISH_SMALL;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getJumpSound() {
        return SoundEvents.MAGMA_CUBE_JUMP;
    }

    @Override
    protected ItemStack getSlimeBallDrop() {
        return new ItemStack(Items.MAGMA_CREAM);
    }

    @Override
    protected GelatinousCube createSmallerSlime(int size) {
        if (size < 1) return null;

        @SuppressWarnings("unchecked")
        MagmaCube smallMagmaCube = new MagmaCube((EntityType<? extends MagmaCube>) this.getType(), this.level());
        smallMagmaCube.setSize(size);
        return smallMagmaCube;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

}