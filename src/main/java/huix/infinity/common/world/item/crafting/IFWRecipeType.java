package huix.infinity.common.world.item.crafting;

import huix.infinity.common.world.effect.UnClearEffect;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWRecipeType {

    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, InfinityWay.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<LevelCookingRecipe>> ifw_smelting = RECIPES.register("ifw_smelting",
            location -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_smelting")));
}
