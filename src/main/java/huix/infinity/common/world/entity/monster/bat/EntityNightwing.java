package huix.infinity.common.world.entity.monster.bat;

import huix.infinity.common.world.entity.mob.Livestock;
import huix.infinity.common.core.tag.IFWItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class EntityNightwing extends EntityVampireBat {
    private int attackCooldown;

    public EntityNightwing(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return EntityVampireBat.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }

        if (this.tickCount % 20 == 0 && !this.level().isClientSide) {
            LivingEntity bestPrey = null;
            double bestDist = 32.0D;
            for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(bestDist))) {
                if (e != this && this.preysUpon(e)) {
                    double dist = this.distanceToSqr(e);
                    if (dist < bestDist * bestDist) {
                        bestDist = Math.sqrt(dist);
                        bestPrey = e;
                    }
                }
            }
            if (bestPrey != null) {
                this.setTarget(bestPrey);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (isInSunlight()) {
                this.hurt(this.damageSources().source(DamageTypes.ON_FIRE), 1000.0F);
            } else if (this.tickCount % 40 == 0) {
                float brightness = getBlockBrightness();
                int amountToHeal = (int) ((0.4F - brightness) * 10.0F);
                if (amountToHeal > 0) {
                    this.heal(amountToHeal);
                }
            }
        }
    }

    private boolean isInSunlight() {
        if (this.level().isDay() && !this.level().isClientSide) {
            BlockPos pos = this.blockPosition().above();
            return this.level().canSeeSky(pos) && this.level().getBrightness(LightLayer.SKY, pos) > 8;
        }
        return false;
    }

    private float getBlockBrightness() {
        BlockPos pos = this.blockPosition();
        return this.level().getBrightness(LightLayer.BLOCK, pos) / 15.0F;
    }

    @Override
    protected boolean preysUpon(Entity entity) {
        if (entity instanceof Player player && !player.isCreative()) return true;
        if (entity instanceof Animal) return true;
        if (entity instanceof Livestock) return true;
        return entity instanceof Villager;
    }

    private boolean isSilverOrEnchanted(ItemStack stack) {

        if (stack.isEmpty()) return false;

        if (stack.is(IFWItemTags.SILVER_ITEM)) return true;

        if (stack.isEnchanted()) return true;

        return false;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {

        Entity direct = source.getDirectEntity();
        Entity attacker = source.getEntity();

        boolean valid = false;

        if (attacker instanceof LivingEntity living) {
            ItemStack main = living.getMainHandItem();
            if (isSilverOrEnchanted(main)) {
                valid = true;
            }
        }

        if (!valid && direct != null) {
            if (direct instanceof Projectile) {
                Entity owner = ((Projectile) direct).getOwner();

                if (owner instanceof LivingEntity shooter) {
                    ItemStack weapon = shooter.getMainHandItem();
                    if (isSilverOrEnchanted(weapon)) {
                        valid = true;
                    }
                }

                if (direct instanceof net.minecraft.world.entity.projectile.AbstractArrow arrow) {
                    ItemStack arrowStack = arrow.getPickupItemStackOrigin();
                    if (isSilverOrEnchanted(arrowStack)) {
                        valid = true;
                    }
                } else if (direct instanceof net.minecraft.world.entity.projectile.ThrownTrident trident) {
                    ItemStack tridentStack = trident.getPickupItemStackOrigin();
                    if (isSilverOrEnchanted(tridentStack)) {
                        valid = true;
                    }
                }
            }
        }

        if (source.is(DamageTypes.MAGIC) || source.is(DamageTypes.ON_FIRE)) {
            valid = true;
        }

        if (!valid) {

            this.playSound(net.minecraft.sounds.SoundEvents.ZOMBIE_INFECT, 0.5F, 2.0F);
            return false;
        }

        return super.hurt(source, amount);
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
            }
            this.attackCooldown = 20;
        }
        return false;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {

        if (source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.MAGIC)) return false;

        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isNightwing() {
        return true;
    }

}