package huix.infinity.datagen.recipe;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.crafting.CookingLevelRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
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
        CookingLevelRecipeBuilder.smelting(Ingredient.of(IFWItems.raw_adamantium), RecipeCategory.MISC, IFWItems.adamantium_ingot,
                        100.0F, 200, 4)
                .unlockedBy("has_raw_adamantium", has(IFWItems.raw_adamantium))
                .save(recipeOutput, "raw_adamantium_smelting");

        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.POTATO), RecipeCategory.FOOD, Items.BAKED_POTATO, 0.35F, 200)
                .unlockedBy("has_potato", has(Items.POTATO))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.CLAY_BALL), RecipeCategory.MISC, Items.BRICK, 0.3F, 200)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(ItemTags.LOGS_THAT_BURN), RecipeCategory.MISC, Items.CHARCOAL, 0.15F, 200)
                .unlockedBy("has_log", has(ItemTags.LOGS_THAT_BURN))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.CHORUS_FRUIT), RecipeCategory.MISC, Items.POPPED_CHORUS_FRUIT, 0.1F, 200)
                .unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.BEEF), RecipeCategory.FOOD, Items.COOKED_BEEF, 0.35F, 200)
                .unlockedBy("has_beef", has(Items.BEEF))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.CHICKEN), RecipeCategory.FOOD, Items.COOKED_CHICKEN, 0.35F, 200)
                .unlockedBy("has_chicken", has(Items.CHICKEN))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.COD), RecipeCategory.FOOD, Items.COOKED_COD, 0.35F, 200)
                .unlockedBy("has_cod", has(Items.COD))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.KELP), RecipeCategory.FOOD, Items.DRIED_KELP, 0.1F, 200)
                .unlockedBy("has_kelp", has(Blocks.KELP))
                .save(recipeOutput, getSmeltingRecipeName(Items.DRIED_KELP));
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.SALMON), RecipeCategory.FOOD, Items.COOKED_SALMON, 0.35F, 200)
                .unlockedBy("has_salmon", has(Items.SALMON))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.MUTTON), RecipeCategory.FOOD, Items.COOKED_MUTTON, 0.35F, 200)
                .unlockedBy("has_mutton", has(Items.MUTTON))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.PORKCHOP), RecipeCategory.FOOD, Items.COOKED_PORKCHOP, 0.35F, 200)
                .unlockedBy("has_porkchop", has(Items.PORKCHOP))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.RABBIT), RecipeCategory.FOOD, Items.COOKED_RABBIT, 0.35F, 200)
                .unlockedBy("has_rabbit", has(Items.RABBIT))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(ItemTags.SMELTS_TO_GLASS), RecipeCategory.BUILDING_BLOCKS, Blocks.GLASS.asItem(), 0.1F, 200)
                .unlockedBy("has_smelts_to_glass", has(ItemTags.SMELTS_TO_GLASS))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.SEA_PICKLE), RecipeCategory.MISC, Items.LIME_DYE, 0.1F, 200)
                .unlockedBy("has_sea_pickle", has(Blocks.SEA_PICKLE))
                .save(recipeOutput, getSmeltingRecipeName(Items.LIME_DYE));
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.CACTUS.asItem()), RecipeCategory.MISC, Items.GREEN_DYE, 1.0F, 200)
                .unlockedBy("has_cactus", has(Blocks.CACTUS))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(
                                IFWItems.golden_pickaxe,
                                IFWItems.golden_shovel,
                                IFWItems.golden_axe,
                                IFWItems.golden_hoe,
                                IFWItems.golden_sword,
                                IFWItems.golden_helmet,
                                IFWItems.golden_chestplate,
                                IFWItems.golden_leggings,
                                IFWItems.golden_boots
                        ),
                        RecipeCategory.MISC,
                        Items.GOLD_NUGGET,
                        0.1F,
                        200
                )
                .unlockedBy("has_golden_pickaxe", has(IFWItems.golden_pickaxe))
                .unlockedBy("has_golden_shovel", has(IFWItems.golden_shovel))
                .unlockedBy("has_golden_axe", has(IFWItems.golden_axe))
                .unlockedBy("has_golden_hoe", has(IFWItems.golden_hoe))
                .unlockedBy("has_golden_sword", has(IFWItems.golden_sword))
                .unlockedBy("has_golden_helmet", has(IFWItems.golden_helmet))
                .unlockedBy("has_golden_chestplate", has(IFWItems.golden_chestplate))
                .unlockedBy("has_golden_leggings", has(IFWItems.golden_leggings))
                .unlockedBy("has_golden_boots", has(IFWItems.golden_boots))
                .save(recipeOutput, getSmeltingRecipeName(Items.GOLD_NUGGET));
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(
                                Items.IRON_PICKAXE,
                                Items.IRON_SHOVEL,
                                Items.IRON_AXE,
                                Items.IRON_HOE,
                                Items.IRON_SWORD,
                                Items.IRON_HELMET,
                                Items.IRON_CHESTPLATE,
                                Items.IRON_LEGGINGS,
                                Items.IRON_BOOTS,
                                Items.IRON_HORSE_ARMOR,
                                Items.CHAINMAIL_HELMET,
                                Items.CHAINMAIL_CHESTPLATE,
                                Items.CHAINMAIL_LEGGINGS,
                                Items.CHAINMAIL_BOOTS
                        ),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        0.1F,
                        200
                )
                .unlockedBy("has_iron_pickaxe", has(Items.IRON_PICKAXE))
                .unlockedBy("has_iron_shovel", has(Items.IRON_SHOVEL))
                .unlockedBy("has_iron_axe", has(Items.IRON_AXE))
                .unlockedBy("has_iron_hoe", has(Items.IRON_HOE))
                .unlockedBy("has_iron_sword", has(Items.IRON_SWORD))
                .unlockedBy("has_iron_helmet", has(Items.IRON_HELMET))
                .unlockedBy("has_iron_chestplate", has(Items.IRON_CHESTPLATE))
                .unlockedBy("has_iron_leggings", has(Items.IRON_LEGGINGS))
                .unlockedBy("has_iron_boots", has(Items.IRON_BOOTS))
                .unlockedBy("has_iron_horse_armor", has(Items.IRON_HORSE_ARMOR))
                .unlockedBy("has_chainmail_helmet", has(Items.CHAINMAIL_HELMET))
                .unlockedBy("has_chainmail_chestplate", has(Items.CHAINMAIL_CHESTPLATE))
                .unlockedBy("has_chainmail_leggings", has(Items.CHAINMAIL_LEGGINGS))
                .unlockedBy("has_chainmail_boots", has(Items.CHAINMAIL_BOOTS))
                .save(recipeOutput, getSmeltingRecipeName(Items.IRON_NUGGET));
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.CLAY), RecipeCategory.BUILDING_BLOCKS, Blocks.TERRACOTTA.asItem(), 0.35F, 200)
                .unlockedBy("has_clay_block", has(Blocks.CLAY))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.NETHERRACK), RecipeCategory.MISC, Items.NETHER_BRICK, 0.1F, 200)
                .unlockedBy("has_netherrack", has(Blocks.NETHERRACK))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.NETHER_QUARTZ_ORE), RecipeCategory.MISC, Items.QUARTZ, 0.2F, 200)
                .unlockedBy("has_nether_quartz_ore", has(Blocks.NETHER_QUARTZ_ORE))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.WET_SPONGE), RecipeCategory.BUILDING_BLOCKS, Blocks.SPONGE.asItem(), 0.15F, 200)
                .unlockedBy("has_wet_sponge", has(Blocks.WET_SPONGE))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.COBBLESTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.STONE.asItem(), 0.1F, 200)
                .unlockedBy("has_cobblestone", has(Blocks.COBBLESTONE))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.STONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE.asItem(), 0.1F, 200)
                .unlockedBy("has_stone", has(Blocks.STONE))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.SANDSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_SANDSTONE.asItem(), 0.1F, 200)
                .unlockedBy("has_sandstone", has(Blocks.SANDSTONE))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.RED_SANDSTONE), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_RED_SANDSTONE.asItem(), 0.1F, 200
                )
                .unlockedBy("has_red_sandstone", has(Blocks.RED_SANDSTONE))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.QUARTZ_BLOCK), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_QUARTZ.asItem(), 0.1F, 200)
                .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, Blocks.CRACKED_STONE_BRICKS.asItem(), 0.1F, 200)
                .unlockedBy("has_stone_bricks", has(Blocks.STONE_BRICKS))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.BLACK_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BLACK_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_black_terracotta", has(Blocks.BLACK_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.BLUE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_blue_terracotta", has(Blocks.BLUE_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.BROWN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.BROWN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_brown_terracotta", has(Blocks.BROWN_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.CYAN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.CYAN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_cyan_terracotta", has(Blocks.CYAN_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.GRAY_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_gray_terracotta", has(Blocks.GRAY_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.GREEN_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.GREEN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_green_terracotta", has(Blocks.GREEN_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.LIGHT_BLUE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_light_blue_terracotta", has(Blocks.LIGHT_BLUE_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.LIGHT_GRAY_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_light_gray_terracotta", has(Blocks.LIGHT_GRAY_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.LIME_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.LIME_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_lime_terracotta", has(Blocks.LIME_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.MAGENTA_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.MAGENTA_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_magenta_terracotta", has(Blocks.MAGENTA_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.ORANGE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.ORANGE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_orange_terracotta", has(Blocks.ORANGE_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.PINK_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.PINK_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_pink_terracotta", has(Blocks.PINK_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.PURPLE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.PURPLE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_purple_terracotta", has(Blocks.PURPLE_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.RED_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.RED_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
                .unlockedBy("has_red_terracotta", has(Blocks.RED_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.WHITE_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.WHITE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_white_terracotta", has(Blocks.WHITE_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(Blocks.YELLOW_TERRACOTTA), RecipeCategory.DECORATIONS, Blocks.YELLOW_GLAZED_TERRACOTTA.asItem(), 0.1F, 200
                )
                .unlockedBy("has_yellow_terracotta", has(Blocks.YELLOW_TERRACOTTA))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.ANCIENT_DEBRIS), RecipeCategory.MISC, Items.NETHERITE_SCRAP, 2.0F, 200)
                .unlockedBy("has_ancient_debris", has(Blocks.ANCIENT_DEBRIS))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.BASALT), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_BASALT, 0.1F, 200)
                .unlockedBy("has_basalt", has(Blocks.BASALT))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.COBBLED_DEEPSLATE), RecipeCategory.BUILDING_BLOCKS, Blocks.DEEPSLATE, 0.1F, 200)
                .unlockedBy("has_cobbled_deepslate", has(Blocks.COBBLED_DEEPSLATE))
                .save(recipeOutput);
        
        
        

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
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.golden_axe)
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
