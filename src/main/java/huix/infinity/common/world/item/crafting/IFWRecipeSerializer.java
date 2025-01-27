package huix.infinity.common.world.item.crafting;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public interface IFWRecipeSerializer {
    RecipeSerializer<IFWSmeltingRecipe> ifw_smelting_recipe = RecipeSerializer.register(
            "ifw_smelting", new SimpleLevelCookingSerializer<>(IFWSmeltingRecipe::new, 200));
}
