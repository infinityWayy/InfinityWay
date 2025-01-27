package huix.infinity.common.world.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;

public class IFWSmeltingRecipe extends AbstractCookingLevelRecipe {
    public IFWSmeltingRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, int cookingLevel) {
        super(RecipeType.SMELTING, group, category, ingredient, result, experience, cookingTime, cookingLevel);
    }
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(Blocks.FURNACE);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IFWRecipeSerializer.ifw_smelting_recipe;
    }
}
