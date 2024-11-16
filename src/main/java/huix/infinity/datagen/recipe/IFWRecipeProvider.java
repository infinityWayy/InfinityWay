package huix.infinity.datagen.recipe;

import huix.infinity.common.block.IFWBlocks;
import huix.infinity.common.item.IFWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class IFWRecipeProvider extends RecipeProvider {
    public IFWRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        nineBlockStorageRecipesWithCustomPacking(
                recipeOutput, RecipeCategory.MISC, IFWItems.adamantium_ingot, RecipeCategory.BUILDING_BLOCKS,
                IFWBlocks.adamantium_block, "adamantium_block_from_ingot", "adamantium_block"
        );
    }
}
