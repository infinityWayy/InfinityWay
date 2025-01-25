package huix.infinity.mixin.world.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Foods.class)
public class FoodsMixin {
//
//    @Redirect(at = @At(value = "NEW", target = "", ordinal = 0)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildChicken() {
//        return (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F)
//                .effect(() -> new MobEffectInstance(MobEffects.POISON, 150, 0), 0.3F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 3)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildCookedBeef() {
//        return (new FoodProperties.Builder()).nutrition(10).saturationModifier(1.0F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 6)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildCookedMutton() {
//        return (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.9F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 7)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildCookedPorkchop() {
//        return (new FoodProperties.Builder()).nutrition(8).saturationModifier(1.0F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 8)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildCookedRabbit() {
//        return (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.5F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 9)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildCookedSalmon() {
//        return (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.8F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 10)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildCookie() {
//        return (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.5F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 11)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildDriedKelp() {
//        return (new FoodProperties.Builder()).nutrition(0).saturationModifier(0.5F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 12)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildEnchantedGoldenApple() {
//        return (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.6F).alwaysEdible()
//                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1800, 1), 1.0F)
//                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), 1.0F)
//                .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800, 0), 1.0F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 13)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildGoldenApple() {
//        return (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.6F)
//                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0), 1.0F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 14)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildGoldenCarrot() {
//        return (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.6F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 15)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildHoneyBottle() {
//        return (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.1F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 16)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildMelonSlice() {
//        return (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.3F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 17)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildMutton() {
//        return (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.6F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 18)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildPoisonouspPotato() {
//        return (new FoodProperties.Builder()).nutrition(0).saturationModifier(0.4F)
//                .effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0), 0.6F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 19)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildPorkchop() {
//        return (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.6F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 20)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildPotato() {
//        return (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.8F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 21)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildPufferFish() {
//        return (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F)
//                .effect(() -> new MobEffectInstance(MobEffects.POISON, 1200, 1), 1.0F)
//                .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 2), 1.0F)
//                .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200, 0), 1.0F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 22)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildPumpkinPie() {
//        return (new FoodProperties.Builder()).nutrition(5).saturationModifier(3.0F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 23)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildRabbit() {
//        return (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.6F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 24)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildRottenFlesh() {
//        return (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.1F)
//                .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F)
//                .effect(() -> new MobEffectInstance(MobEffects.POISON, 300, 0), 0.8F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 26)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildSpiderEye() {
//        return (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.0F)
//                .effect(() -> new MobEffectInstance(MobEffects.POISON, 300, 0), 1.0F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 27)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildSweetBerries() {
//        return (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.3F);
//    }
//    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodProperties$Builder;", ordinal = 28)
//            , method = "<clinit>")
//    private static FoodProperties.Builder rebuildGlowBerries() {
//        return (new FoodProperties.Builder()).nutrition(0).saturationModifier(0.3F);
//    }
}
