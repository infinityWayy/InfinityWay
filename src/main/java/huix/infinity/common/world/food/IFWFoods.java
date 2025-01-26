package huix.infinity.common.world.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class IFWFoods {
    public static final IFWFoodProperties apple = (new IFWFoodProperties.Builder()).phytonutrients(8000).build();
    public static final IFWFoodProperties beef = (new IFWFoodProperties.Builder()).protein(40000).build();
    public static final IFWFoodProperties beefroot = (new IFWFoodProperties.Builder()).phytonutrients(3000).insulinResponse(4800).build();
    public static final IFWFoodProperties beefroot_soup = (new IFWFoodProperties.Builder()).phytonutrients(18000).insulinResponse(9600).soup().build();
    public static final IFWFoodProperties carrot = (new IFWFoodProperties.Builder()).phytonutrients(16000).build();
    public static final IFWFoodProperties chicken = (new IFWFoodProperties.Builder()).protein(24000).meat().build();
    public static final IFWFoodProperties cod = (new IFWFoodProperties.Builder()).protein(16000).build();
    public static final IFWFoodProperties cooked_beef = (new IFWFoodProperties.Builder()).protein(80000).meat().build();
    public static final IFWFoodProperties cooked_chick = (new IFWFoodProperties.Builder()).protein(48000).meat().build();
    public static final IFWFoodProperties cooked_cod = (new IFWFoodProperties.Builder()).protein(40000).build();
    public static final IFWFoodProperties cooked_mutton = (new IFWFoodProperties.Builder()).protein(48000).meat().build();
    public static final IFWFoodProperties cooked_porkchop = (new IFWFoodProperties.Builder()).protein(32000).meat().build();
    public static final IFWFoodProperties cooked_rabbit = (new IFWFoodProperties.Builder()).protein(24000).meat().build();
    public static final IFWFoodProperties cooked_salmon = (new IFWFoodProperties.Builder()).protein(48000).build();
    public static final IFWFoodProperties cookie = (new IFWFoodProperties.Builder()).insulinResponse(1200).snack().build();
    public static final IFWFoodProperties dried_kelp = (new IFWFoodProperties.Builder()).snack().build();
    public static final IFWFoodProperties enchanted_golden_apple = (new IFWFoodProperties.Builder()).phytonutrients(8000).insulinResponse(4800).build();
    public static final IFWFoodProperties golden_apple = (new IFWFoodProperties.Builder()).phytonutrients(8000).insulinResponse(4800).build();
    public static final IFWFoodProperties golden_carror = (new IFWFoodProperties.Builder()).phytonutrients(16000).build();
    public static final IFWFoodProperties honey_bottle = (new IFWFoodProperties.Builder()).insulinResponse(14000).build();
    public static final IFWFoodProperties melon_slice = (new IFWFoodProperties.Builder()).phytonutrients(8000).insulinResponse(4800).build();
    public static final IFWFoodProperties mutton = (new IFWFoodProperties.Builder()).protein(24000).meat().build();
    public static final IFWFoodProperties porkchop = (new IFWFoodProperties.Builder()).protein(32000).meat().build();
    public static final IFWFoodProperties pumpkin_pie = (new IFWFoodProperties.Builder()).protein(48000).phytonutrients(48000).insulinResponse(4800).build();
    public static final IFWFoodProperties rabbit = (new IFWFoodProperties.Builder()).protein(24000).meat().build();
    public static final IFWFoodProperties rabbit_stew = (new IFWFoodProperties.Builder()).protein(80000).phytonutrients(80000).build();
    public static final IFWFoodProperties rotten_flesh = (new IFWFoodProperties.Builder()).protein(8000).meat().build();
    public static final IFWFoodProperties salmon = (new IFWFoodProperties.Builder()).protein(16000).meat().build();
    public static final IFWFoodProperties spider_eye = (new IFWFoodProperties.Builder()).protein(8000).meat().build();
    public static final IFWFoodProperties sweet_berries = (new IFWFoodProperties.Builder()).phytonutrients(8000).insulinResponse(4800).build();



    public static final FoodProperties wheat_seeds = (new FoodProperties.Builder()).saturationModifier(0.3F).build();
    public static final FoodProperties salad = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.3F).build();
    public static final IFWFoodProperties ifw_salad = (new IFWFoodProperties.Builder()).phytonutrients(8000).build();
    public static final FoodProperties milk_bowl = (new FoodProperties.Builder()).nutrition(1).build();
    public static final IFWFoodProperties ifw_milk_bowl = (new IFWFoodProperties.Builder()).protein(8000).soup().build();
    public static final FoodProperties milk_bucket = (new FoodProperties.Builder()).nutrition(4).build();
    public static final IFWFoodProperties ifw_milk_bucket = (new IFWFoodProperties.Builder()).protein(32000).build();
    public static final FoodProperties brown_mushroom = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F).build();
    public static final FoodProperties red_mushroom = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200, 1), 1.0F).build();
    public static final FoodProperties pumpkin_seed = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F).build();
    public static final FoodProperties sugar = (new FoodProperties.Builder()).saturationModifier(0.3F).build();
    public static final IFWFoodProperties ifw_sugar = (new IFWFoodProperties.Builder()).insulinResponse(4800).build();
    public static final FoodProperties egg = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F).build();
    public static final IFWFoodProperties ifw_egg = (new IFWFoodProperties.Builder()).protein(24000).build();
    public static final IFWFoodProperties ifw_bowl_water = (new IFWFoodProperties.Builder()).soup().build();
    public static final FoodProperties cheese = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.9F).build();
    public static final IFWFoodProperties ifw_cheese = (new IFWFoodProperties.Builder()).protein(24000).build();
    public static final FoodProperties dough = (new FoodProperties.Builder()).nutrition(2).saturationModifier(1.8F).build();
    public static final FoodProperties chocolate = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.9F).build();
    public static final IFWFoodProperties ifw_chocolate = (new IFWFoodProperties.Builder()).insulinResponse(4800).build();
    public static final FoodProperties cereal = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.6F).build();
    public static final IFWFoodProperties ifw_cereal = (new IFWFoodProperties.Builder()).protein(16000).build();
    public static final FoodProperties pumpkin_soup = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.6F).build();
    public static final IFWFoodProperties ifw_pumpkin_soup = (new IFWFoodProperties.Builder()).protein(16000).soup().build();
    public static final FoodProperties mushroom_soup_cream = (new FoodProperties.Builder()).nutrition(3).saturationModifier(1.5F).build();
    public static final IFWFoodProperties ifw_mushroom_soup_cream = (new IFWFoodProperties.Builder()).protein(40000).soup().build();
    public static final FoodProperties vegetable_soup = (new FoodProperties.Builder()).nutrition(6).saturationModifier(1.8F).build();
    public static final IFWFoodProperties ifw_vegetable_soup = (new IFWFoodProperties.Builder()).phytonutrients(48000).build();
    public static final FoodProperties vegetable_soup_cream = (new FoodProperties.Builder()).nutrition(7).saturationModifier(2.1F).build();
    public static final IFWFoodProperties ifw_vegetable_soup_cream = (new IFWFoodProperties.Builder()).protein(56000).phytonutrients(56000).soup().build();
    public static final FoodProperties chicken_soup = (new FoodProperties.Builder()).nutrition(10).saturationModifier(3.0F).build();
    public static final IFWFoodProperties ifw_chicken_soup = (new IFWFoodProperties.Builder()).protein(80000).phytonutrients(80000).soup().build();
    public static final FoodProperties beef_stew = (new FoodProperties.Builder()).nutrition(16).saturationModifier(3.8F).build();
    public static final IFWFoodProperties ifw_beef_stew = (new IFWFoodProperties.Builder()).protein(128000).phytonutrients(128000).soup().build();
    public static final FoodProperties porridge = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.6F).build();
    public static final IFWFoodProperties ifw_porridge = (new IFWFoodProperties.Builder()).phytonutrients(16000).insulinResponse(9600).soup().build();
    public static final FoodProperties sorbet = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.6F).build();
    public static final IFWFoodProperties ifw_sorbet = (new IFWFoodProperties.Builder()).phytonutrients(16000).insulinResponse(9600).build();
    public static final FoodProperties mashed_potato = (new FoodProperties.Builder()).nutrition(12).saturationModifier(2.4F).build();
    public static final IFWFoodProperties ifw_mashed_potato = (new IFWFoodProperties.Builder()).protein(64000).build();
    public static final FoodProperties ice_cream = (new FoodProperties.Builder()).nutrition(5).saturationModifier(1.6F).build();
    public static final IFWFoodProperties ifw_ice_cream = (new IFWFoodProperties.Builder()).protein(32000).insulinResponse(4800).build();
    public static final FoodProperties orange = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.3F).build();
    public static final IFWFoodProperties ifw_orange = (new IFWFoodProperties.Builder()).phytonutrients(8000).insulinResponse(4800).build();
    public static final FoodProperties banana = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.3F).build();
    public static final IFWFoodProperties ifw_banana = (new IFWFoodProperties.Builder()).phytonutrients(8000).insulinResponse(4800).build();
    public static final FoodProperties cooked_worm = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.3F).build();
    public static final IFWFoodProperties ifw_cooked_worm = (new IFWFoodProperties.Builder()).protein(8000).build();
    public static final FoodProperties worm = (new FoodProperties.Builder()).nutrition(1).build();
    public static final IFWFoodProperties ifw_worm = (new IFWFoodProperties.Builder()).protein(8000).build();
    public static final FoodProperties blueberry = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.3F).build();
    public static final IFWFoodProperties ifw_blueberry = (new IFWFoodProperties.Builder()).phytonutrients(8000).insulinResponse(4800).build();
    public static final FoodProperties nether_wart = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.5F).build();
    public static final FoodProperties melon_seed = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.3F).build();
    public static final FoodProperties beetroot_seeds = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.4F).build();
}
