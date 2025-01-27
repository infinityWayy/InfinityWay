package huix.infinity.func_extension;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface CookingRecipeFactoryExtension<T extends AbstractCookingRecipe> {

    default T create(String var1, CookingBookCategory var2, Ingredient var3, ItemStack var4, float var5, int var6, int cookingLevel) {
        AbstractCookingRecipe.Factory<T> in = (AbstractCookingRecipe.Factory<T>) this;
        return (T) in.create(var1, var2, var3, var4, var5, var6).cookingLevel(cookingLevel);
    }
}
