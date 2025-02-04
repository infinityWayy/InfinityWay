package huix.infinity.mixin.world.food;

import huix.infinity.common.world.effect.IFWMobEffects;
import huix.infinity.common.world.entity.player.NutritionalStatus;
import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.func_extension.FoodDataExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( FoodData.class )
public class FoodDataMixin implements FoodDataExtension {
    @Shadow
    private int foodLevel;
    @Shadow
    private float saturationLevel;
    @Shadow
    private float exhaustionLevel;
    @Shadow
    private int tickTimer;
    @Shadow
    private int lastFoodLevel;

    @Unique
    private NutritionalStatus nutritionalStatus = NutritionalStatus.NONE;
    @Unique
    private int maxFoodLevel = 6;
    @Unique
    private int phytonutrients = 160000;
    @Unique
    private int protein = 160000;
    @Unique
    private int nutritionalStageTimer;
    @Unique
    private int insulinResponse;
    @Unique
    private int lastInsulinResponse;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void giveValue(CallbackInfo ci){
        this.foodLevel = 6;
        this.lastFoodLevel = 6;
        this.saturationLevel = 2.0F;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getFloat(Ljava/lang/String;)F"
            , ordinal = 1), method = "readAdditionalSaveData")
    private void readNBT(CompoundTag compound, CallbackInfo ci){
        this.maxFoodLevel = compound.getInt("maxFoodLevel");
        this.phytonutrients = compound.getInt("phytonutrients");
        this.protein = compound.getInt("protein");
        this.insulinResponse = compound.getInt("insulinResponse");
        this.nutritionalStatus = NutritionalStatus.values()[compound.getInt("nutritionalStatus")];
        this.nutritionalStageTimer = compound.getInt("nutritionalStageTimer");
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    private void injectNBT(CompoundTag compound, CallbackInfo ci){
        compound.putInt("maxFoodLevel", this.maxFoodLevel);
        compound.putInt("phytonutrients", this.phytonutrients);
        compound.putInt("protein", this.protein);
        compound.putInt("insulinResponse", this.insulinResponse);
        compound.putInt("nutritionalStatus", this.nutritionalStatus.ordinal());
        compound.putInt("nutritionalStageTimer", this.nutritionalStageTimer);
    }


    @Overwrite
    private void add(int foodLevel, float saturationLevel) {
        this.foodLevel = Mth.clamp(foodLevel + this.foodLevel, 0, this.maxFoodLevel);
        this.saturationLevel = Mth.clamp(saturationLevel + this.saturationLevel, 0.0F, this.maxFoodLevel);
    }

    @Overwrite
    public void eat(int foodLevelModifier, float saturationLevelModifier) {
        this.add(foodLevelModifier, FoodConstants.saturationByModifier(foodLevelModifier, saturationLevelModifier));
    }

    @Overwrite
    public void eat(FoodProperties foodProperties) {
        this.add(foodProperties.nutrition(), foodProperties.saturation());
    }

    @Overwrite
    public boolean needsFood() {
        return this.foodLevel <= this.maxFoodLevel && this.saturationLevel <= this.maxFoodLevel;
    }

    @Unique
    @Override
    public void eat(IFWFoodProperties foodProperties) {
        this.addNutrition(foodProperties.phytonutrients(), foodProperties.protein(), foodProperties.insulinResponse());
    }

    @Unique
    public void addNutrition(int phytonutrients, int protein, int IR) {
        this.ifw_phytonutrients(Math.min(this.phytonutrients + phytonutrients, 160000));
        this.ifw_protein(Math.min(this.protein + protein, 160000));
        this.ifw_insulinResponse(this.insulinResponse + IR);
    }

    @Overwrite
    public void tick(Player player) {
        Difficulty difficulty = player.level().getDifficulty();
        this.lastFoodLevel = this.foodLevel;
        if (this.exhaustionLevel > 4.0F) {
            this.exhaustionLevel -= 4.0F;
            if (this.saturationLevel > 0.0F)
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            else if (difficulty != Difficulty.PEACEFUL)
                this.foodLevel = Math.max(this.foodLevel - 1, 0);

            if (this.foodLevel == 0)
                clearTickTimer();
        }
        ++this.tickTimer;

        //heal or damage
        if (healTick(player)) {
            if (useFoodHeal(player)) {
                player.heal(1.0F);
                this.addExhaustion(6.0F);
            }
            clearTickTimer();
        }

        if (damageTick()) {
            player.hurt(player.damageSources().starve(), 1.0F);
            clearTickTimer();
        }

        //NutritionalStatus resolve
        this.decreaseNutrition();
        if (hasNutrition()) {
            this.nutritionalStageTimer = 0;
            this.nutritionalStatus(NutritionalStatus.NONE);
        } else {
            this.nutritionalStatus(NutritionalStatus.LIGHT);
            ++this.nutritionalStageTimer;
            if (nonNutrition()) {
                this.nutritionalStatus(NutritionalStatus.SERIOUS);
            }
        }

        //insulinResponsea
        final int stage1 = 115200;
        final int stage2 = 172800;
        float irExhaustion = 0.0F;
        if (ifw_irLight()) {
            irExhaustion = 0.1F;
            if (this.insulinResponse > stage1) {
                irExhaustion = 0.2F;
                if (this.insulinResponse > stage2) {
                    irExhaustion = 0.4F;
                    ifw_addEffect(player, 9000.0F, 5400.0F, 259200, 2);
                } else
                    ifw_addEffect(player, 5400.0F, 3600.0F, stage2, 1);
            } else
                ifw_addEffect(player, 600.0F, 5400.0F, stage1, 0);
        }

        if (this.tickTimer % 20 == 0)
            this.addExhaustion(irExhaustion);

        ifw_removeIR();
        this.lastInsulinResponse = this.insulinResponse;
    }

    @Overwrite
    public void addExhaustion(float exhaustion) {
        exhaustion = (float)(exhaustion * 1.25);
        exhaustion *= this.nutritionalStatus.exhaustionTimes();
        this.exhaustionLevel = Math.min(this.exhaustionLevel + exhaustion, 40.0F);
    }

    @Unique
    private boolean ifw_irLight() {
        return this.insulinResponse > 76800 && this.insulinResponse > this.lastInsulinResponse;
    }

    @Unique
    private void ifw_removeIR() {
        if (this.insulinResponse > 0)
            --this.insulinResponse;
    }

    @Unique
    private void ifw_addEffect(Player player, float start, float times,  float now, int amplifier) {
        player.addEffect(new MobEffectInstance(IFWMobEffects.insulin_resistance,
                20 * (int)(start + times * (1.0F - 1.0E-5F * (now - this.insulinResponse))), amplifier, true, false));
    }


    @Unique
    @Override
    public void ifw_maxFoodLevel(int maxFoodLevel) {
        this.maxFoodLevel = maxFoodLevel;
    }

    @Unique
    @Override
    public int ifw_maxFoodLevel() {
        return this.maxFoodLevel;
    }

    @Override
    public int ifw_nutritionalStatusByINT() {
        return this.nutritionalStatus.ordinal();
    }

    @Override
    public void ifw_nutritionalStatusByINT(int nutritionalStatus) {
        this.nutritionalStatus = NutritionalStatus.values()[nutritionalStatus];
    }

    @Override
    public NutritionalStatus ifw_nutritionalStatus() {
        return this.nutritionalStatus;
    }

    @Override
    public int ifw_protein() {
        return this.protein;
    }

    @Override
    public void ifw_protein(int protein) {
        this.protein = protein;
    }

    @Override
    public int ifw_phytonutrients() {
        return this.phytonutrients;
    }

    @Unique
    public void ifw_insulinResponse(int insulinResponse) {
        this.insulinResponse = insulinResponse;
    }

    @Unique
    public int ifw_insulinResponse() {
        return this.insulinResponse;
    }

    @Override
    public void ifw_phytonutrients(int phytontrients) {
        this.phytonutrients = phytontrients;
    }

    @Unique
    private void nutritionalStatus(NutritionalStatus nutritionalStatus) {
        this.nutritionalStatus = nutritionalStatus;
    }
    @Unique
    private void decreaseNutrition() {
        if (this.phytonutrients > 0) {
            --this.phytonutrients;
        }

        if (this.protein > 0) {
            --this.protein;
        }
    }
    @Unique
    private boolean hasNutrition() {
        return this.phytonutrients != 0 && this.protein != 0;
    }
    @Unique
    private boolean nonNutrition() {
        return this.nutritionalStageTimer > 2000 || this.phytonutrients == 0 && this.protein == 0;
    }


    @Unique
    private int naturalHealTick() {
        return 1280;
    }
    @Unique
    private boolean useFoodHeal(final Player player) {
        return player.getHealth() > 0.0F && player.getHealth() < player.getMaxHealth();
    }
    @Unique
    private boolean healTick(final Player player) {
        int result = this.naturalHealTick() * this.nutritionalStatus.naturalHealSpeedTimes();
        if (player.isSleeping())
            result /= 8;

        return player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)
                 && this.tickTimer >= result;
    }
    @Unique
    private boolean damageTick() {
        return this.foodLevel <= 0 && this.saturationLevel <= 0.0F && this.tickTimer >= 300;
    }
    @Unique
    private void clearTickTimer() {
        this.tickTimer = 0;
    }


}
