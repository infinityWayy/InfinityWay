package huix.infinity.common.world.item.crafting;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, InfinityWay.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<EnchantmentRecipe>> enchantment = RECIPES.register("enchantment",
            location -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID,"enchantment")));
}
