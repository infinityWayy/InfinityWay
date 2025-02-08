package huix.infinity.mixin.world.entity.projectile;

import huix.infinity.common.world.item.IFWItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin extends Projectile {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FishingHook;setHookedEntity(Lnet/minecraft/world/entity/Entity;)V")
            , method = "onHitEntity")
    private void injectDamage(EntityHitResult result, CallbackInfo ci) {
        DamageSources sources = result.getEntity().damageSources();
        result.getEntity().hurt(sources.generic(), 1);
    }

    @Overwrite
    private void catchingFish(BlockPos pos) {
        ServerLevel serverlevel = (ServerLevel) this.level();
        int i = 1;
        BlockPos blockpos = pos.above();
        if (this.random.nextFloat() < 0.25F && this.level().isRainingAt(blockpos)) {
            ++i;
        }

        if (this.random.nextFloat() < 0.5F && !this.level().canSeeSky(blockpos)) {
            --i;
        }

        if (this.nibble > 0) {
            --this.nibble;
            if (this.nibble <= 0) {
                this.timeUntilLured = 0;
                this.timeUntilHooked = 0;
                this.getEntityData().set(DATA_BITING, false);
            }
        } else {
            float f5;
            float f6;
            float f7;
            double d4;
            double d5;
            double d6;
            BlockState blockstate1;
            if (this.timeUntilHooked > 0) {
                this.timeUntilHooked -= i;
                if (this.timeUntilHooked > 0) {
                    this.fishAngle += (float)this.random.triangle(0.0, 9.188);
                    f5 = this.fishAngle * 0.017453292F;
                    f6 = Mth.sin(f5);
                    f7 = Mth.cos(f5);
                    d4 = this.getX() + (double)(f6 * (float)this.timeUntilHooked * 0.1F);
                    d5 = (float)Mth.floor(this.getY()) + 1.0F;
                    d6 = this.getZ() + (double)(f7 * (float)this.timeUntilHooked * 0.1F);
                    blockstate1 = serverlevel.getBlockState(BlockPos.containing(d4, d5 - 1.0, d6));
                    if (blockstate1.is(Blocks.WATER)) {
                        if (this.random.nextFloat() < 0.015F) {
                            serverlevel.sendParticles(ParticleTypes.BUBBLE, d4, d5 - 0.10000000149011612, d6, 1, (double)f6, 0.1, (double)f7, 0.0);
                        }
                        if (this.random.nextFloat() < 0.15F) {
                            float f3 = f6 * 0.04F;
                            float f4 = f7 * 0.04F;
                            serverlevel.sendParticles(ParticleTypes.FISHING, d4, d5, d6, 0, f4, 0.01, -f3, 1.0);
                            serverlevel.sendParticles(ParticleTypes.FISHING, d4, d5, d6, 0, -f4, 0.01, f3, 1.0);
                        }
                    }
                } else {
                    this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                    double d3 = this.getY() + 0.5;
                    serverlevel.sendParticles(ParticleTypes.BUBBLE, this.getX(), d3, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), (double)this.getBbWidth(), 0.0, (double)this.getBbWidth(), 0.20000000298023224);
                    serverlevel.sendParticles(ParticleTypes.FISHING, this.getX(), d3, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), (double)this.getBbWidth(), 0.0, (double)this.getBbWidth(), 0.20000000298023224);
                    this.nibble = Mth.nextInt(this.random, 20, 40);
                    this.getEntityData().set(DATA_BITING, true);
                }
            } else if (this.timeUntilLured > 0) {
                this.timeUntilLured -= i;
                f5 = 0.15F;
                if (this.timeUntilLured < 20) {
                    f5 += (float)(20 - this.timeUntilLured) * 0.005F;
                } else if (this.timeUntilLured < 40) {
                    f5 += (float)(40 - this.timeUntilLured) * 0.002F;
                } else if (this.timeUntilLured < 60) {
                    f5 += (float)(60 - this.timeUntilLured) * 0.001F;
                }

                if (this.random.nextFloat() < f5) {
                    f6 = Mth.nextFloat(this.random, 0.0F, 360.0F) * 0.017453292F;
                    f7 = Mth.nextFloat(this.random, 25.0F, 60.0F);
                    d4 = this.getX() + (double)(Mth.sin(f6) * f7) * 0.1;
                    d5 = (float)Mth.floor(this.getY()) + 1.0F;
                    d6 = this.getZ() + (double)(Mth.cos(f6) * f7) * 0.1;
                    blockstate1 = serverlevel.getBlockState(BlockPos.containing(d4, d5 - 1.0, d6));
                    if (blockstate1.is(Blocks.WATER)) {
                        serverlevel.sendParticles(ParticleTypes.SPLASH, d4, d5, d6, 2 + this.random.nextInt(2), 0.10000000149011612, 0.0, 0.10000000149011612, 0.0);
                    }
                }

                if (ifw_checkForBite(pos)) {
                    this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
                    this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
                }
            } else {
                this.timeUntilLured = Mth.nextInt(this.random, 100, 600);
                this.timeUntilLured -= this.lureSpeed + (ifw_hasRawWorm() ? 10 : 2);
            }
        }
    }

    @Unique
    private boolean ifw_checkForBite(BlockPos blockpos) {
        float dayTime = this.level().getDayTime();
        float timeFactor = Math.min(Math.abs(dayTime - 5500), Math.abs(dayTime - 17500)) / 600.0F;
        float chance =  Mth.clamp((int)(600.0F * timeFactor), 600, 2400);

        if (!this.level().canSeeSky(blockpos))
            chance /= 2;
        if (this.level().isThundering())
            chance /= 2;
        if (this.level().isRainingAt(blockpos))
            chance *= 0.5F;
        if (ifw_hasRawWorm())
            chance *= 0.5F;

        for (int i = 0; i < this.lureSpeed; ++i)
            chance *= 0.9F;

        return this.getRandom().nextInt(Math.round(chance)) == 0 && this.timeUntilLured <= 0;
    }

    @Unique
    private boolean ifw_hasRawWorm() {
        return this.getPlayerOwner().getInventory().hasAnyOf(Set.of(IFWItems.worm.get()));
    }

    @Shadow private int nibble;

    @Shadow private int timeUntilLured;

    @Shadow private int timeUntilHooked;

    @Shadow @Final private static EntityDataAccessor<Boolean> DATA_BITING;

    @Shadow private float fishAngle;

    @Shadow @Final private int lureSpeed;

    @Shadow protected abstract void setHookedEntity(@Nullable Entity hookedEntity);

    @Shadow @javax.annotation.Nullable public abstract Player getPlayerOwner();

    protected FishingHookMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }
}
