package huix.infinity.mixin.world.entity;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.func_extension.PlayerExtension;
import huix.infinity.util.ReflectHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin( Player.class )
public abstract class PlayerMixin extends LivingEntity implements PlayerExtension {

    @Shadow public abstract FoodData getFoodData();

    @Shadow protected FoodData foodData;

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
        final double i = this.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
        if (!this.getMainHandItem().isEmpty())
            return i + this.getMainHandItem().getItem().getReachBonus();
        return i;
    }
    @Overwrite
    public double entityInteractionRange() {
        final double i = this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
        if (!this.getMainHandItem().isEmpty())
            return i + this.getMainHandItem().getItem().getReachBonus();
        return i;
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
        final float modifyValue = this.ifw_modifyValue();
        this.ifw_maxHealth(modifyValue);
        this.foodData.ifw_maxFoodLevel((int) modifyValue);
    }
    @Unique
    private float ifw_modifyValue() {
        final int max_level = this.experienceLevel >= 35 ? 35 : this.experienceLevel;
        return Math.max((float) max_level / 5 * 2 + 6.0F, 1.0F);
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


    @Shadow
    private int sleepCounter;

    @Unique
    @Override
    public boolean canResetTimeBySleeping() {
        long time = this.level().dayTime();
        return this.isSleeping() && this.sleepCounter >= 100 && time > 15000L && time < 23000L;
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
