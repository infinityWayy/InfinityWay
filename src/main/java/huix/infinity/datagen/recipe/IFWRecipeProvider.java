package huix.infinity.datagen.recipe;

import huix.infinity.common.tag.IFWItemTags;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class IFWRecipeProvider extends RecipeProvider {
    public IFWRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(final @NotNull RecipeOutput recipeOutput) {
        nineBlockStorageRecipesWithCustomPacking(
                recipeOutput, RecipeCategory.MISC, IFWItems.adamantium_ingot, RecipeCategory.BUILDING_BLOCKS,
                IFWBlocks.adamantium_block, "adamantium_block_from_ingot", "adamantium_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_hatchet)
                .define('X', Items.FLINT)
                .define('Y', Items.STICK)
                .define('Z', IFWItemTags.string)
                .pattern("XZ")
                .pattern("Y ")
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.adamantium_bars, 6)
                .requires(IFWItems.adamantium_ingot, 6)
                .unlockedBy("has_adamantium_ingot", has(IFWItems.adamantium_ingot))
                .save(recipeOutput);
    }
}
