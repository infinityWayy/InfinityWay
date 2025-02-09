package huix.infinity.common.world.item.crafting;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class EnchantmentRecipe implements Recipe<SingleRecipeInput> {

    private final Ingredient ingredient;
    private final ItemStack result;
    private final float experience;

    public EnchantmentRecipe(final Ingredient ingredient, final ItemStack result, final float experience) {
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
    }

    public Ingredient ingredient() {
        return this.ingredient;
    }

    public float experience() {
        return this.experience;
    }

    public ItemStack result() {
        return this.result;
    }

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return this.ingredient.test(singleRecipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput singleRecipeInput, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IFWRecipeSerializers.enchantment.get();
    }

    @Override
    public RecipeType<?> getType() {
        return IFWRecipeTypes.enchantment.get();
    }

    @FunctionalInterface
    public interface Factory<T extends EnchantmentRecipe> {
        T create(Ingredient ingredient, ItemStack result, float experience);
    }
}
