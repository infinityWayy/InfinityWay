package huix.infinity.datagen.recipe;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class IFWRecipeProvider extends RecipeProvider {
    public IFWRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(final @NotNull RecipeOutput recipeOutput) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.KELP), RecipeCategory.FOOD, Items.DRIED_KELP, 0.1F, 200)
                .unlockedBy("has_kelp", has(Blocks.KELP)).cookingLevel(5)
                .save(recipeOutput, "dried_kelp_smelting");


        nineBlockStorageRecipesWithCustomPacking(
                recipeOutput, RecipeCategory.MISC, IFWItems.adamantium_ingot, RecipeCategory.BUILDING_BLOCKS,
                IFWBlocks.adamantium_block, "adamantium_block_from_ingot", "adamantium_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_hatchet)
                .define('X', Items.FLINT)
                .define('Y', Items.STICK)
                .define('Z', IFWItemTags.string)
                .pattern("YX")
                .pattern("YZ")
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_axe)
                .define('X', Items.FLINT)
                .define('Y', Items.STICK)
                .define('Z', IFWItemTags.string)
                .pattern("XX")
                .pattern("YX")
                .pattern("YZ")
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_shovel)
                .define('X', Items.FLINT)
                .define('Y', Items.STICK)
                .define('Z', IFWItemTags.string)
                .pattern("XZ")
                .pattern("Y ")
                .pattern("Y ")
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_knife)
                .define('X', Items.FLINT)
                .define('Y', Items.STICK)
                .define('Z', IFWItemTags.string)
                .pattern("XZ")
                .pattern("Y ")
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.adamantium_axe)
                .define('X', IFWItems.adamantium_ingot)
                .define('Y', Items.STICK)
                .pattern("XX")
                .pattern("YX")
                .pattern("Y ")
                .unlockedBy("has_adamantium_ingot", has(IFWItems.adamantium_ingot))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.ancient_metal_axe)
                .define('X', IFWItems.ancient_metal_ingot)
                .define('Y', Items.STICK)
                .pattern("XX")
                .pattern("YX")
                .pattern("Y ")
                .unlockedBy("has_adamantium_ingot", has(IFWItems.adamantium_ingot))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.mithril_axe)
                .define('X', IFWItems.mithril_ingot)
                .define('Y', Items.STICK)
                .pattern("XX")
                .pattern("YX")
                .pattern("Y ")
                .unlockedBy("has_mithril_ingot", has(IFWItems.mithril_ingot))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.silver_axe)
                .define('X', IFWItems.silver_ingot)
                .define('Y', Items.STICK)
                .pattern("XX")
                .pattern("YX")
                .pattern("Y ")
                .unlockedBy("has_silver_ingot", has(IFWItems.silver_ingot))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.copper_axe)
                .define('X', Items.COPPER_INGOT)
                .define('Y', Items.STICK)
                .pattern("XX")
                .pattern("YX")
                .pattern("Y ")
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.gold_axe)
                .define('X', Items.GOLD_INGOT)
                .define('Y', Items.STICK)
                .pattern("XX")
                .pattern("YX")
                .pattern("Y ")
                .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.iron_axe)
                .define('X', Items.IRON_AXE)
                .define('Y', Items.STICK)
                .pattern("XX")
                .pattern("YX")
                .pattern("Y ")
                .unlockedBy("has_iron_axe", has(Items.IRON_AXE))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.adamantium_bars, 6)
                .requires(IFWItems.adamantium_ingot, 6)
                .unlockedBy("has_adamantium_ingot", has(IFWItems.adamantium_ingot))
                .save(recipeOutput);
    }
}
