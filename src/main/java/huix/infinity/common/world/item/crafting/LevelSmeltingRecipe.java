package huix.infinity.common.world.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class LevelSmeltingRecipe extends LevelCookingRecipe{
    public LevelSmeltingRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, int cookingLevel) {
        super(IFWRecipeTypes.ifw_smelting.get(), group, category, ingredient, result, experience, cookingTime, cookingLevel);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return IFWRecipeSerializers.level_smelting.get();
    }
}
