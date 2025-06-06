package huix.infinity.mixin.world.entity;

import com.mojang.authlib.GameProfile;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.extension.func.PlayerExtension;
import huix.infinity.util.ReflectHelper;
import huix.infinity.util.WorldHelper;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerExtension {



    @Overwrite
    public int getXpNeededForNextLevel() {
        return Math.abs(this.experienceLevel < 0 ? 20 : 10 * this.experienceLevel + 20);
    }

    @Overwrite
    public void giveExperiencePoints(int xpPoints) {
        PlayerXpEvent.XpChange event = new PlayerXpEvent.XpChange(ReflectHelper.dyCast(this), xpPoints);
        if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
            xpPoints = event.getAmount();
            this.increaseScore(xpPoints);

            if (this.experienceLevel <= 0 && xpPoints < 0) {
                this.giveExperienceLevels(Math.max(-(-xpPoints / 20), -40));
            } else {
                this.experienceProgress += (float)xpPoints / (float)this.getXpNeededForNextLevel();

                while(this.experienceProgress < 0.0F) {
                    float f = this.experienceProgress * (float)this.getXpNeededForNextLevel();
                    if (this.experienceLevel > 0) {
                        this.giveExperienceLevels(-1);
                        this.experienceProgress = 1.0F + f / (float)this.getXpNeededForNextLevel();
                    } else {
                        this.giveExperienceLevels(-1);
                        this.experienceProgress = 0.0F;
                    }
                }

                while(this.experienceProgress >= 1.0F) {
                    this.experienceProgress = (this.experienceProgress - 1.0F) * (float)this.getXpNeededForNextLevel();
                    this.giveExperienceLevels(1);
                    this.experienceProgress /= (float)this.getXpNeededForNextLevel();
                }
            }

            this.ifw_updateTotalExperience();
        }
    }
    @Overwrite
    public void giveExperienceLevels(int levels) {
        PlayerXpEvent.LevelChange event = new PlayerXpEvent.LevelChange(ReflectHelper.dyCast(this), levels);
        if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
            levels = event.getLevels();
            this.experienceLevel += levels;

            this.experienceLevel = Math.max(this.experienceLevel, -40);

            if (levels > 0 && this.experienceLevel % 5 == 0 && (float)this.lastLevelUpTime < (float)this.tickCount - 100.0F) {
                float f = this.experienceLevel > 30 ? 1.0F : (float)this.experienceLevel / 30.0F;
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_LEVELUP, this.getSoundSource(), f * 0.75F, 1.0F);
                this.lastLevelUpTime = this.tickCount;
            }

        }
        this.ifw_updateTotalExperience();
    }

    @Overwrite
    protected int getBaseExperienceReward() {
        if (this.experienceLevel > 0 && !this.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && !this.isSpectator()) {
            return this.totalExperience / 3;
        } else {
            return 0;
        }
    }
    @Overwrite
    public double blockInteractionRange() {
        return this.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE) + this.getMainHandItem().getItem().getReachBonus();
    }
    @Overwrite
    public double entityInteractionRange() {
        return this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) + this.getMainHandItem().getItem().getReachBonus();
    }

    @Overwrite
    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.ATTACK_DAMAGE, 1.0F).add(Attributes.MOVEMENT_SPEED, 0.1F)
                .add(Attributes.ATTACK_SPEED).add(Attributes.LUCK)
                .add(Attributes.BLOCK_INTERACTION_RANGE, 3.0F).add(Attributes.ENTITY_INTERACTION_RANGE, 2.0F)
                .add(Attributes.BLOCK_BREAK_SPEED).add(Attributes.SUBMERGED_MINING_SPEED).add(Attributes.SNEAKING_SPEED).add(Attributes.MINING_EFFICIENCY)
                .add(Attributes.SWEEPING_DAMAGE_RATIO).add(NeoForgeMod.CREATIVE_FLIGHT);
    }

    @Unique
    @Override
    public void ifw_updateTotalExperience() {
        if (this.experienceLevel >= 0) {
            this.totalExperience = (int)(5.0 * Math.pow(this.experienceLevel, 2.0) + (double)(15 * this.experienceLevel)) +
                    Mth.ceil(this.experienceProgress * (float)this.getXpNeededForNextLevel());
        } else {
            this.totalExperience = this.experienceLevel * 20;
        }
        this.ifw_levelUpdate();
    }
    @Unique
    public void ifw_levelUpdate() {
        int modifyValue = this.ifw_modifyValue();
        this.ifw_maxHealth(modifyValue);
        this.getFoodData().ifw_maxFoodLevel(modifyValue);

        if (modifyValue < this.getFoodData().getFoodLevel())
            this.getFoodData().setFoodLevel(modifyValue);
        if (modifyValue < this.getFoodData().getSaturationLevel())
            this.getFoodData().setSaturation(modifyValue);
    }

    @Unique
    private int ifw_modifyValue() {
        return Math.max(Math.min(6 + this.experienceLevel / 5 * 2, 20), 6);
    }

    @Unique
    public void ifw_maxHealth(final float health) {
        Objects.requireNonNull(this.getAttributes().getInstance(Attributes.MAX_HEALTH)).setBaseValue(health);
    }

    @Inject(at = @At("HEAD"), method = "eat")
    private void playerEat(Level level, ItemStack food, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir){
        if (food.get(IFWDataComponents.ifw_food_data.get()) != null)
            this.getFoodData().eat(food.get(IFWDataComponents.ifw_food_data.get()));
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isAutoSpinAttack()Z"))
    public void moreFoodExhaustion(Entity target, CallbackInfo ci) {
        this.causeFoodExhaustion(0.3F);
    }


    @Shadow
    private int sleepCounter;

    @Unique
    @Override
    public boolean canResetTimeBySleeping() {
        long hour = WorldHelper.worldHour(this.level().dayTime());
        return this.isSleeping() && this.sleepCounter >= 100 && (hour <= 5 || hour >= 21);
    }


    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public int experienceLevel;
    @Shadow
    public int totalExperience;
    @Shadow
    private int lastLevelUpTime;
    @Shadow
    public float experienceProgress;
    @Shadow @Final private GameProfile gameProfile;
    @Shadow private int currentImpulseContextResetGraceTime;
    @Shadow private boolean ignoreFallDamageFromCurrentImpulse;
    @Shadow @Nullable public Vec3 currentImpulseImpactPos;

    @Shadow public abstract void setLastDeathLocation(Optional<GlobalPos> lastDeathLocation);

    @Shadow protected abstract void setShoulderEntityRight(CompoundTag entityCompound);

    @Shadow protected abstract void setShoulderEntityLeft(CompoundTag entityCompound);

    @Shadow protected PlayerEnderChestContainer enderChestInventory;
    @Shadow @Final private Abilities abilities;
    @Shadow protected FoodData foodData;

    @Shadow public abstract void setScore(int score);

    @Shadow public int enchantmentSeed;
    @Shadow @Final private Inventory inventory;

    @Shadow public abstract void causeFoodExhaustion(float exhaustion);

    @Shadow public abstract FoodData getFoodData();
    @Shadow
    public void increaseScore(int score) {
    }

    @Shadow
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Shadow
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return null;
    }

    @Shadow
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
    }

    @Shadow
    public HumanoidArm getMainArm() {
        return null;
    }
}
