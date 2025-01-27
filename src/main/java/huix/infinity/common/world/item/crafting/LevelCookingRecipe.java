package huix.infinity.common.world.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class LevelCookingRecipe extends AbstractCookingRecipe {
    private int cookingLevel;

    public LevelCookingRecipe(RecipeType<?> type, String group, CookingBookCategory category,
                              Ingredient ingredient, ItemStack result, float experience, int cookingTime, int cookingLevel) {
        super(type, group, category, ingredient, result, experience, cookingTime);
        this.cookingLevel = cookingLevel;
    }


    public int cookingLevel() {
        return this.cookingLevel;
    }

    public LevelCookingRecipe cookingLevel(int cookingLevel) {
        this.cookingLevel = cookingLevel;
        return this;
    }


    public interface Factory<T extends LevelCookingRecipe> {
        T create(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, int cookingLevel);
    }
}
