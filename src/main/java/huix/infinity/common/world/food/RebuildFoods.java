package huix.infinity.common.world.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class RebuildFoods {
    public static final FoodProperties APPLE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(2).build();
    public static final FoodProperties BAKED_POTATO = (new FoodProperties.Builder()).nutrition(2).saturationModifier(6).build();
    public static final FoodProperties BEEF = (new FoodProperties.Builder()).nutrition(5).saturationModifier(5).build();
    public static final FoodProperties BEETROOT = (new FoodProperties.Builder()).nutrition(1).saturationModifier(2).build();
    public static final FoodProperties BREAD = (new FoodProperties.Builder()).nutrition(2).saturationModifier(8).build();
    public static final FoodProperties CARROT = (new FoodProperties.Builder()).nutrition(2).saturationModifier(1).build();
    public static final FoodProperties CHICKEN = (new FoodProperties.Builder()).nutrition(3).saturationModifier(3)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 150, 0), 0.3F).build();
    public static final FoodProperties COOKED_BEEF = (new FoodProperties.Builder()).nutrition(10).saturationModifier(10).build();
    public static final FoodProperties COOKED_MUTTON = (new FoodProperties.Builder()).nutrition(6).saturationModifier(6).build();
    public static final FoodProperties COOKED_PORKCHOP = (new FoodProperties.Builder()).nutrition(8).saturationModifier(8).build();
    public static final FoodProperties COOKED_RABBIT = (new FoodProperties.Builder()).nutrition(4).saturationModifier(6).build();
    public static final FoodProperties COOKED_SALMON = (new FoodProperties.Builder()).nutrition(6).saturationModifier(6F).build();
    public static final FoodProperties COOKED_CHICKEN = (new FoodProperties.Builder()).nutrition(6).saturationModifier(6F).build();
    public static final FoodProperties COOKIE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(3).build();
    public static final FoodProperties DRIED_KELP = (new FoodProperties.Builder()).saturationModifier(1).build();
    public static final FoodProperties ENCHANTED_GOLDEN_APPLE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(2).alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1800, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800, 0), 1.0F).build();
    public static final FoodProperties GOLDEN_APPLE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(2)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0), 1.0F).build();
    public static final FoodProperties GOLDEN_CARROT = (new FoodProperties.Builder()).nutrition(2).saturationModifier(1).build();
    public static final FoodProperties HONEY_BOTTLE = (new FoodProperties.Builder()).nutrition(3).saturationModifier(1).build();
    public static final FoodProperties MELON_SLICE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(1).build();
    public static final FoodProperties MUTTON = (new FoodProperties.Builder()).nutrition(3).saturationModifier(3).build();
    public static final FoodProperties POISONOUS_POTATO = (new FoodProperties.Builder()).saturationModifier(1)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0), 0.6F).build();
    public static final FoodProperties PORKCHOP = (new FoodProperties.Builder()).nutrition(4).saturationModifier(4).build();
    public static final FoodProperties POTATO = (new FoodProperties.Builder()).nutrition(1).saturationModifier(3).build();
    public static final FoodProperties PUFFERFISH = (new FoodProperties.Builder()).nutrition(1).saturationModifier(1)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 1200, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 2), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200, 0), 1.0F).build();
    public static final FoodProperties PUMPKIN_PIE = (new FoodProperties.Builder()).nutrition(4).saturationModifier(10).build();
    public static final FoodProperties RABBIT = (new FoodProperties.Builder()).nutrition(2).saturationModifier(2).build();
    public static final FoodProperties ROTTEN_FLESH = (new FoodProperties.Builder()).nutrition(2).saturationModifier(1)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 300, 0), 0.8F).build();
    public static final FoodProperties SPIDER_EYE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(1)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 300, 0), 1.0F).build();
    public static final FoodProperties SWEET_BERRIES = (new FoodProperties.Builder()).nutrition(1).saturationModifier(1).build();
    public static final FoodProperties GLOW_BERRIES = (new FoodProperties.Builder()).saturationModifier(1).build();
    public static final FoodProperties CHORUS_FRUIT = (new FoodProperties.Builder()).nutrition(2).saturationModifier(1).build();
    public static final FoodProperties COD = (new FoodProperties.Builder()).nutrition(2).saturationModifier(1).build();
    public static final FoodProperties COOKED_COD = (new FoodProperties.Builder()).nutrition(5).saturationModifier(2).build();
    public static final FoodProperties SALMON = (new FoodProperties.Builder()).nutrition(3).saturationModifier(3).build();
    public static final FoodProperties TROPICAL_FISH = (new FoodProperties.Builder()).nutrition(1).saturationModifier(1).build();
    public static final FoodProperties OMINOUS_BOTTLE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(1).build();

}

