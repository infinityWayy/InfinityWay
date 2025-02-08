package huix.infinity.datagen.recipe;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.crafting.CookingLevelRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class IFWRecipeProvider extends RecipeProvider {
    public IFWRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(final @NotNull RecipeOutput recipeOutput) {
        cookingRecipe(recipeOutput);
        foodRecipe(recipeOutput);
        rebuildRecipe(recipeOutput);

        {
            fishingRodRecipe(recipeOutput, IFWItems.obsidian_fishing_rod, IFWItems.obsidian_shard);
            fishingRodRecipe(recipeOutput, IFWItems.flint_fishing_rod, Items.FLINT);
            bucketRecipe(recipeOutput, IFWItems.copper_bucket, Items.COPPER_INGOT);
            fishingRodRecipe(recipeOutput, IFWItems.copper_fishing_rod, IFWItems.copper_nugget);
            fishingRodRecipe(recipeOutput, IFWItems.iron_fishing_rod, Items.IRON_NUGGET);
            bucketRecipe(recipeOutput, IFWItems.silver_bucket, IFWItems.silver_ingot);
            fishingRodRecipe(recipeOutput, IFWItems.silver_fishing_rod, IFWItems.silver_nugget);
            bucketRecipe(recipeOutput, IFWItems.gold_bucket, Items.GOLD_INGOT);
            fishingRodRecipe(recipeOutput, IFWItems.gold_fishing_rod, Items.GOLD_NUGGET);
            bucketRecipe(recipeOutput, IFWItems.ancient_metal_bucket, IFWItems.ancient_metal_ingot);
            fishingRodRecipe(recipeOutput, IFWItems.ancient_metal_fishing_rod, IFWItems.ancient_metal_nugget);
            bucketRecipe(recipeOutput, IFWItems.mithril_bucket, IFWItems.mithril_ingot);
            fishingRodRecipe(recipeOutput, IFWItems.mithril_fishing_rod, IFWItems.mithril_nugget);
            bucketRecipe(recipeOutput, IFWItems.adamantium_bucket, IFWItems.adamantium_ingot);
            fishingRodRecipe(recipeOutput, IFWItems.adamantium_fishing_rod, IFWItems.adamantium_nugget);
        }
        {
            anvilRecipe(recipeOutput, IFWBlocks.adamantium_anvil, IFWItems.adamantium_ingot,IFWBlocks.adamantium_block);
            anvilRecipe(recipeOutput, IFWBlocks.silver_anvil, IFWItems.silver_ingot,IFWBlocks.silver_block);
            anvilRecipe(recipeOutput, IFWBlocks.copper_anvil, Items.COPPER_INGOT,Blocks.COPPER_BLOCK);
            anvilRecipe(recipeOutput, IFWBlocks.gold_anvil, Items.GOLD_INGOT,Blocks.GOLD_BLOCK);
            anvilRecipe(recipeOutput, IFWBlocks.iron_anvil, Items.IRON_INGOT,Blocks.IRON_BLOCK);
            anvilRecipe(recipeOutput, IFWBlocks.ancient_metal_anvil, IFWItems.ancient_metal_ingot,IFWBlocks.ancient_metal_block);
            anvilRecipe(recipeOutput, IFWBlocks.mithril_anvil, IFWItems.mithril_ingot,IFWBlocks.mithril_block);
        }
        {
            armorRecipe(recipeOutput, IFWItems.adamantium_helmet, IFWItems.adamantium_chestplate, IFWItems.adamantium_leggings,
                    IFWItems.adamantium_boots, IFWItems.adamantium_ingot);
            armorRecipe(recipeOutput, IFWItems.adamantium_chainmail_helmet, IFWItems.adamantium_chainmail_chestplate,
                    IFWItems.adamantium_chainmail_leggings, IFWItems.adamantium_chainmail_boots, IFWItems.adamantium_chain);
            metalToolRecipe(recipeOutput, IFWItems.adamantium_shears, IFWItems.adamantium_shovel, IFWItems.adamantium_hoe, IFWItems.adamantium_sword
                    , IFWItems.adamantium_pickaxe, IFWItems.adamantium_axe, IFWItems.adamantium_scythe, IFWItems.adamantium_mattock, IFWItems.adamantium_battle_axe
                    , IFWItems.adamantium_war_hammer, IFWItems.adamantium_dagger, IFWItems.adamantium_ingot);
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.adamantium_ingot, RecipeCategory.BUILDING_BLOCKS,
                    IFWBlocks.adamantium_block, "adamantium_block_from_ingot", "adamantium_block");
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.adamantium_nugget, RecipeCategory.MISC,
                    IFWItems.adamantium_ingot, "adamantium_ingot_from_nugget", "adamantium_ingot");
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWItems.adamantium_chain)
                    .pattern(" Y ")
                    .pattern("Y Y")
                    .pattern(" Y ")
                    .define('Y', IFWItems.adamantium_nugget)
                    .unlockedBy("has_adamantium_nugget", has(IFWItems.adamantium_nugget)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.adamantium_bars, 6)
                    .requires(IFWItems.adamantium_ingot, 6)
                    .unlockedBy("has_adamantium_ingot", has(IFWItems.adamantium_ingot)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.raw_adamantium_block, 1)
                    .requires(IFWItems.raw_adamantium, 9)
                    .unlockedBy("has_raw_adamantium", has(IFWItems.raw_adamantium)).save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.adamantium_door)
                    .pattern("YY")
                    .pattern("YY")
                    .pattern("YY")
                    .define('Y', IFWItems.adamantium_ingot)
                    .unlockedBy("has_adamantium_ingot", has(IFWItems.adamantium_ingot)).save(recipeOutput);

            armorRecipe(recipeOutput, IFWItems.mithril_helmet, IFWItems.mithril_chestplate, IFWItems.mithril_leggings,
                    IFWItems.mithril_boots, IFWItems.mithril_ingot);
            armorRecipe(recipeOutput, IFWItems.mithril_chainmail_helmet, IFWItems.mithril_chainmail_chestplate,
                    IFWItems.mithril_chainmail_leggings, IFWItems.mithril_chainmail_boots, IFWItems.mithril_chain);
            metalToolRecipe(recipeOutput, IFWItems.mithril_shears, IFWItems.mithril_shovel, IFWItems.mithril_hoe, IFWItems.mithril_sword
                    , IFWItems.mithril_pickaxe, IFWItems.mithril_axe, IFWItems.mithril_scythe, IFWItems.mithril_mattock, IFWItems.mithril_battle_axe
                    , IFWItems.mithril_war_hammer, IFWItems.mithril_dagger, IFWItems.mithril_ingot);
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.mithril_ingot, RecipeCategory.BUILDING_BLOCKS,
                    IFWBlocks.mithril_block, "mithril_block_from_ingot", "mithril_block");
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.mithril_nugget, RecipeCategory.MISC,
                    IFWItems.mithril_ingot, "mithril_ingot_from_nugget", "mithril_ingot");
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWItems.mithril_chain)
                    .pattern(" Y ")
                    .pattern("Y Y")
                    .pattern(" Y ")
                    .define('Y', IFWItems.mithril_nugget)
                    .unlockedBy("has_mithril_nugget", has(IFWItems.mithril_nugget)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.mithril_bars, 6)
                    .requires(IFWItems.mithril_ingot, 6)
                    .unlockedBy("has_mithril_ingot", has(IFWItems.mithril_ingot)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.raw_mithril_block, 1)
                    .requires(IFWItems.raw_mithril, 9)
                    .unlockedBy("has_raw_mithril", has(IFWItems.raw_mithril)).save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.mithril_door)
                    .pattern("YY")
                    .pattern("YY")
                    .pattern("YY")
                    .define('Y', IFWItems.mithril_ingot)
                    .unlockedBy("has_mithril_ingot", has(IFWItems.mithril_ingot)).save(recipeOutput);
            armorRecipe(recipeOutput, IFWItems.ancient_metal_helmet, IFWItems.ancient_metal_chestplate, IFWItems.ancient_metal_leggings,
                    IFWItems.ancient_metal_boots, IFWItems.ancient_metal_ingot);
            armorRecipe(recipeOutput, IFWItems.ancient_metal_chainmail_helmet, IFWItems.ancient_metal_chainmail_chestplate,
                    IFWItems.ancient_metal_chainmail_leggings, IFWItems.ancient_metal_chainmail_boots, IFWItems.ancient_metal_chain);
            metalToolRecipe(recipeOutput, IFWItems.ancient_metal_shears, IFWItems.ancient_metal_shovel, IFWItems.ancient_metal_hoe, IFWItems.ancient_metal_sword
                    , IFWItems.ancient_metal_pickaxe, IFWItems.ancient_metal_axe, IFWItems.ancient_metal_scythe, IFWItems.ancient_metal_mattock, IFWItems.ancient_metal_battle_axe
                    , IFWItems.ancient_metal_war_hammer, IFWItems.ancient_metal_dagger, IFWItems.ancient_metal_ingot);
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.ancient_metal_ingot, RecipeCategory.BUILDING_BLOCKS,
                    IFWBlocks.ancient_metal_block, "ancient_metal_block_from_ingot", "ancient_metal_block");
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.ancient_metal_nugget, RecipeCategory.MISC,
                    IFWItems.ancient_metal_ingot, "ancient_metal_ingot_from_nugget", "ancient_metal_ingot");
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWItems.ancient_metal_chain)
                    .pattern(" Y ")
                    .pattern("Y Y")
                    .pattern(" Y ")
                    .define('Y', IFWItems.ancient_metal_nugget)
                    .unlockedBy("has_ancient_metal_nugget", has(IFWItems.ancient_metal_nugget)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.ancient_metal_bars, 6)
                    .requires(IFWItems.ancient_metal_ingot, 6)
                    .unlockedBy("has_ancient_metal_ingot", has(IFWItems.ancient_metal_ingot)).save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.ancient_metal_door)
                    .pattern("YY")
                    .pattern("YY")
                    .pattern("YY")
                    .define('Y', IFWItems.ancient_metal_ingot)
                    .unlockedBy("has_ancient_metal_ingot", has(IFWItems.ancient_metal_ingot)).save(recipeOutput);
            armorRecipe(recipeOutput, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE,
                    Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS, IFWItems.iron_chain);
            metalToolRecipe(recipeOutput, IFWItems.iron_shears, IFWItems.iron_shovel, IFWItems.iron_hoe, IFWItems.iron_sword
                    , IFWItems.iron_pickaxe, IFWItems.iron_axe, IFWItems.iron_scythe, IFWItems.iron_mattock, IFWItems.iron_battle_axe
                    , IFWItems.iron_war_hammer, IFWItems.iron_dagger, Items.IRON_INGOT);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWItems.iron_chain)
                    .pattern(" Y ")
                    .pattern("Y Y")
                    .pattern(" Y ")
                    .define('Y', Items.IRON_NUGGET)
                    .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET)).save(recipeOutput);
            armorRecipe(recipeOutput, IFWItems.rusted_iron_chainmail_helmet, IFWItems.rusted_iron_chainmail_chestplate,
                    IFWItems.rusted_iron_chainmail_leggings, IFWItems.rusted_iron_chainmail_boots, IFWItems.rusted_iron_chain);
            armorRecipe(recipeOutput, IFWItems.golden_chainmail_helmet, IFWItems.golden_chainmail_chestplate,
                    IFWItems.golden_chainmail_leggings, IFWItems.golden_chainmail_boots, IFWItems.golden_chain);
            metalToolRecipe(recipeOutput, IFWItems.golden_shears, IFWItems.golden_shovel, IFWItems.golden_hoe, IFWItems.golden_sword
                    , IFWItems.golden_pickaxe, IFWItems.golden_axe, IFWItems.golden_scythe, IFWItems.golden_mattock, IFWItems.golden_battle_axe
                    , IFWItems.golden_war_hammer, IFWItems.golden_dagger, Items.GOLD_INGOT);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWItems.golden_chain)
                    .pattern(" Y ")
                    .pattern("Y Y")
                    .pattern(" Y ")
                    .define('Y', Items.GOLD_NUGGET)
                    .unlockedBy("has_golden_nugget", has(Items.GOLD_NUGGET)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.gold_bars, 6)
                    .requires(Items.GOLD_INGOT, 6)
                    .unlockedBy("has_golden_ingot", has(Items.GOLD_INGOT)).save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.gold_door)
                    .pattern("YY")
                    .pattern("YY")
                    .pattern("YY")
                    .define('Y', Items.GOLD_INGOT)
                    .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT)).save(recipeOutput);
            armorRecipe(recipeOutput, IFWItems.silver_helmet, IFWItems.silver_chestplate, IFWItems.silver_leggings,
                    IFWItems.silver_boots, IFWItems.silver_ingot);
            armorRecipe(recipeOutput, IFWItems.silver_chainmail_helmet, IFWItems.silver_chainmail_chestplate,
                    IFWItems.silver_chainmail_leggings, IFWItems.silver_chainmail_boots, IFWItems.silver_chain);
            metalToolRecipe(recipeOutput, IFWItems.silver_shears, IFWItems.silver_shovel, IFWItems.silver_hoe, IFWItems.silver_sword
                    , IFWItems.silver_pickaxe, IFWItems.silver_axe, IFWItems.silver_scythe, IFWItems.silver_mattock, IFWItems.silver_battle_axe
                    , IFWItems.silver_war_hammer, IFWItems.silver_dagger, IFWItems.silver_ingot);
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.silver_ingot, RecipeCategory.BUILDING_BLOCKS,
                    IFWBlocks.silver_block, "silver_block_from_ingot", "silver_block");
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.silver_nugget, RecipeCategory.MISC,
                    IFWItems.silver_ingot, "silver_ingot_from_nugget", "silver_ingot");
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWItems.silver_chain)
                    .pattern(" Y ")
                    .pattern("Y Y")
                    .pattern(" Y ")
                    .define('Y', IFWItems.silver_nugget)
                    .unlockedBy("has_silver_nugget", has(IFWItems.silver_nugget)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.silver_bars, 6)
                    .requires(IFWItems.silver_ingot, 6)
                    .unlockedBy("has_silver_ingot", has(IFWItems.silver_ingot)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.raw_silver_block, 1)
                    .requires(IFWItems.raw_silver, 9)
                    .unlockedBy("has_raw_silver", has(IFWItems.raw_silver)).save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.silver_door)
                    .pattern("YY")
                    .pattern("YY")
                    .pattern("YY")
                    .define('Y', IFWItems.silver_ingot)
                    .unlockedBy("has_silver_ingot", has(IFWItems.silver_ingot)).save(recipeOutput);
            armorRecipe(recipeOutput, IFWItems.copper_helmet, IFWItems.copper_chestplate, IFWItems.copper_leggings,
                    IFWItems.copper_boots, Items.COPPER_INGOT);
            armorRecipe(recipeOutput, IFWItems.copper_chainmail_helmet, IFWItems.copper_chainmail_chestplate,
                    IFWItems.copper_chainmail_leggings, IFWItems.copper_chainmail_boots, IFWItems.copper_chain);
            metalToolRecipe(recipeOutput, IFWItems.copper_shears, IFWItems.copper_shovel, IFWItems.copper_hoe, IFWItems.copper_sword
                    , IFWItems.copper_pickaxe, IFWItems.copper_axe, IFWItems.copper_scythe, IFWItems.copper_mattock, IFWItems.copper_battle_axe
                    , IFWItems.copper_war_hammer, IFWItems.copper_dagger, Items.COPPER_INGOT);
            nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, IFWItems.copper_nugget, RecipeCategory.MISC,
                    Items.COPPER_INGOT, "copper_ingot_from_nugget", "copper_ingot");
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWItems.copper_chain)
                    .pattern(" Y ")
                    .pattern("Y Y")
                    .pattern(" Y ")
                    .define('Y', IFWItems.copper_nugget)
                    .unlockedBy("has_copper_nugget", has(IFWItems.copper_nugget)).save(recipeOutput);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWBlocks.copper_bars, 6)
                    .requires(Items.COPPER_INGOT, 6)
                    .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        }
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_hatchet)
                    .define('X', Items.FLINT)
                    .define('Y', Items.STICK)
                    .define('Z', Tags.Items.STRINGS)
                    .pattern("YX")
                    .pattern("YZ")
                    .unlockedBy("has_flint", has(Items.FLINT))
                    .save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_axe)
                    .define('X', Items.FLINT)
                    .define('Y', Items.STICK)
                    .define('Z', Tags.Items.STRINGS)
                    .pattern("XX")
                    .pattern("YX")
                    .pattern("YZ")
                    .unlockedBy("has_flint", has(Items.FLINT))
                    .save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_shovel)
                    .define('X', Items.FLINT)
                    .define('Y', Items.STICK)
                    .define('Z', Tags.Items.STRINGS)
                    .pattern("XZ")
                    .pattern("Y ")
                    .pattern("Y ")
                    .unlockedBy("has_flint", has(Items.FLINT))
                    .save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.flint_knife)
                    .define('X', Items.FLINT)
                    .define('Y', Items.STICK)
                    .define('Z', Tags.Items.STRINGS)
                    .pattern("XZ")
                    .pattern("Y ")
                    .unlockedBy("has_flint", has(Items.FLINT))
                    .save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.wooden_club)
                    .define('X', ItemTags.PLANKS)
                    .define('Y', Items.STICK)
                    .pattern("X")
                    .pattern("X")
                    .pattern("Y")
                    .unlockedBy("has_plank", has(ItemTags.PLANKS))
                    .save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IFWItems.wooden_shovel)
                    .define('X', ItemTags.PLANKS)
                    .define('Y', Items.STICK)
                    .pattern("X")
                    .pattern("Y")
                    .pattern("Y")
                    .unlockedBy("has_plank", has(ItemTags.PLANKS))
                    .save(recipeOutput);
        }
    }

    private void bucketRecipe(RecipeOutput recipeOutput, ItemLike bucket, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, bucket)
                .pattern("C C")
                .pattern(" C ")
                .define('C', Ingredient.of(material))
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
    }
    private void fishingRodRecipe(RecipeOutput recipeOutput, ItemLike fishingRod, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, fishingRod)
                .pattern("  C")
                .pattern(" CB")
                .pattern("CAB")
                .define('A', Ingredient.of(material))
                .define('B', Tags.Items.STRINGS)
                .define('C', Tags.Items.RODS)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
    }

    private void anvilRecipe(RecipeOutput recipeOutput, ItemLike anvil, ItemLike ingot, ItemLike block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, anvil)
                .pattern("YYY")
                .pattern(" X ")
                .pattern("XXX")
                .define('X', ingot)
                .define('Y', block)
                .unlockedBy("has_block", has(block))
                .save(recipeOutput);
    }
    private void rebuildRecipe(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.PUMPKIN_SEEDS, 1)
                .requires(Items.PUMPKIN, 1)
                .unlockedBy("has_pumpkin", has(Items.PUMPKIN)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, IFWItems.sinew, 4)
                .requires(Items.LEATHER, 1)
                .unlockedBy("has_leather", has(Items.LEATHER)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.DIAMOND, 1)
                .requires(IFWItems.diamond_shard, 9)
                .unlockedBy("has_diamond_shard", has(IFWItems.diamond_shard)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EMERALD, 1)
                .requires(IFWItems.emerald_shard, 9)
                .unlockedBy("has_emerald_shard", has(IFWItems.emerald_shard)).save(recipeOutput);
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.QUARTZ, 1)
//                .requires(IFWItems.quartz_shard, 9)
//                .unlockedBy("has_quartz_shard", has(IFWItems.quartz_shard)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.FLINT, 1)
                .requires(IFWItems.flint_shard, 4)
                .unlockedBy("has_flint_shard", has(IFWItems.flint_shard)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWBlocks.clay_furnace_item)
                .pattern("YY")
                .pattern("YY")
                .define('Y', Items.CLAY)
                .unlockedBy("has_clay", has(Items.CLAY)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWBlocks.hardened_clay_furnace_item)
                .pattern("YY")
                .pattern("YY")
                .define('Y', Items.TERRACOTTA)
                .unlockedBy("has_terracotta", has(Items.TERRACOTTA)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWBlocks.sandstone_furnace_item)
                .pattern("YYY")
                .pattern("Y Y")
                .pattern("YYY")
                .define('Y', Items.SANDSTONE)
                .unlockedBy("has_sandstone", has(Items.SANDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWBlocks.stone_furnace)
                .pattern("YYY")
                .pattern("Y Y")
                .pattern("YYY")
                .define('Y', ItemTags.STONE_CRAFTING_MATERIALS)
                .unlockedBy("has_cobblestone", has(ItemTags.STONE_CRAFTING_MATERIALS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWBlocks.obsidian_furnace)
                .pattern("YYY")
                .pattern("Y Y")
                .pattern("YYY")
                .define('Y', Items.OBSIDIAN)
                .unlockedBy("has_obsidian", has(Items.OBSIDIAN)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWBlocks.netherrack_furnace)
                .pattern("YYY")
                .pattern("Y Y")
                .pattern("YYY")
                .define('Y', Items.NETHERRACK)
                .unlockedBy("has_netherrack", has(Items.NETHERRACK)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COMPASS)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('X', Items.REDSTONE)
                .define('Y', Items.IRON_NUGGET)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CLOCK)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('X', Items.REDSTONE)
                .define('Y', Items.GOLD_NUGGET)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FLINT_AND_STEEL)
                .pattern("Y ")
                .pattern(" X")
                .define('X', Items.FLINT)
                .define('Y', Items.IRON_NUGGET)
                .unlockedBy("has_flint", has(Items.FLINT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.GLASS, 1)
                .requires(IFWItems.glass_shard, 9)
                .unlockedBy("has_glass_shard", has(IFWItems.glass_shard)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.GLASS_PANE, 9)
                .requires(Blocks.GLASS, 1)
                .unlockedBy("has_glass", has(Blocks.GLASS)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.STONE, 2)
                .requires(Blocks.COBBLESTONE, 4)
                .unlockedBy("has_cobblestone", has(Blocks.COBBLESTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.BRICKS, 2)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('X', Items.SAND)
                .define('Y', Items.BRICK)
                .unlockedBy("has_brick", has(Items.BRICK)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.OBSIDIAN, 1)
                .requires(IFWItems.obsidian_shard, 9)
                .unlockedBy("has_obsidian_shard", has(IFWItems.obsidian_shard)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.LADDER, 2)
                .pattern("X X")
                .pattern("XXX")
                .pattern("X X")
                .define('X', Items.STICK)
                .unlockedBy("has_stick", has(Items.STICK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.NETHER_BRICKS, 2)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('Y', Items.NETHER_BRICK)
                .define('X', Items.SOUL_SAND)
                .unlockedBy("has_brick", has(Items.NETHER_BRICK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.SADDLE)
                .pattern("YYY")
                .pattern("Y Y")
                .pattern("X X")
                .define('Y', Items.LEATHER)
                .define('X', Items.IRON_NUGGET)
                .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET)).save(recipeOutput);
    }
    private void foodRecipe(RecipeOutput recipeOutput) {
        CookingLevelRecipeBuilder.smelting(Ingredient.of(IFWItems.cooked_worm), RecipeCategory.FOOD, IFWItems.worm,
                        1.0F, 200, 1)
                .unlockedBy("has_worm", has(IFWItems.worm)).save(recipeOutput, "worm_smelting");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.salad, 1)
            .requires(Items.BOWL, 1)
            .requires(Items.DANDELION, 3)
            .unlockedBy("has_bowl", has(Items.BOWL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.cereal, 1)
                .requires(IFWItems.milk_bowl, 1)
                .requires(Items.WHEAT, 1)
                .requires(Items.SUGAR, 1)
                .unlockedBy("has_milk_bowl", has(IFWItems.milk_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.chocolate, 1)
                .requires(Items.COCOA_BEANS, 1)
                .requires(Items.SUGAR, 1)
                .unlockedBy("has_cocoa_beans", has(Items.COCOA_BEANS)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.pumpkin_soup, 1)
                .requires(IFWItems.water_bowl, 1)
                .requires(Items.PUMPKIN, 1)
                .unlockedBy("has_water_bowl", has(IFWItems.water_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.mushroom_soup_cream, 1)
                .requires(IFWItems.milk_bowl, 1)
                .requires(Items.BROWN_MUSHROOM, 2)
                .unlockedBy("has_milk_bowl", has(IFWItems.milk_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.vegetable_soup, 1)
                .requires(IFWItems.water_bowl, 1)
                .requires(Items.POTATO, 1)
                .requires(IFWItems.onion, 1)
                .requires(Items.CARROT, 1)
                .unlockedBy("has_water_bowl", has(IFWItems.water_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.vegetable_soup_cream, 1)
                .requires(IFWItems.milk_bowl, 1)
                .requires(Items.POTATO, 1)
                .requires(IFWItems.onion, 1)
                .requires(Items.CARROT, 1)
                .unlockedBy("has_milk_bowl", has(IFWItems.milk_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.chicken_soup, 1)
                .requires(IFWItems.water_bowl, 1)
                .requires(Items.COOKED_CHICKEN, 1)
                .requires(IFWItems.onion, 1)
                .requires(Items.CARROT, 1)
                .unlockedBy("has_water_bowl", has(IFWItems.water_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.beef_stew, 1)
                .requires(IFWItems.water_bowl, 1)
                .requires(Items.COOKED_BEEF, 1)
                .requires(Items.POTATO, 1)
                .requires(Items.BROWN_MUSHROOM, 1)
                .unlockedBy("has_water_bowl", has(IFWItems.water_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.sorbet, 1)
                .requires(Items.BOWL, 1)
                .requires(Items.SNOWBALL, 1)
                .requires(Items.SUGAR, 1)
                .requires(IFWItems.orange, 1)
                .unlockedBy("has_bowl", has(Items.BOWL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.cheese, 1)
                .requires(IFWItems.milk_bowl, 4)
                .unlockedBy("has_milk_bowl", has(IFWItems.milk_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.mashed_potato, 1)
                .requires(IFWItems.milk_bowl, 1)
                .requires(Items.BAKED_POTATO, 1)
                .requires(IFWItems.cheese, 1)
                .unlockedBy("has_milk_bowl", has(IFWItems.milk_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.ice_cream, 1)
                .requires(Items.BOWL, 1)
                .requires(Items.SNOWBALL, 1)
                .requires(Items.SUGAR, 1)
                .requires(Items.COCOA_BEANS, 1)
                .unlockedBy("has_bowl", has(Items.BOWL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.dough, 1)
                .requires(IFWItems.water_bowl, 1)
                .requires(IFWItems.flour, 1)
                .unlockedBy("has_water_bowl", has(IFWItems.water_bowl)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, IFWItems.flour, 1)
                .requires(Items.WHEAT, 3)
                .unlockedBy("has_wheat", has(Items.WHEAT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.MUSHROOM_STEW, 1)
                .requires(IFWItems.water_bowl, 1)
                .requires(Items.BROWN_MUSHROOM, 1)
                .requires(Items.RED_MUSHROOM, 1)
                .unlockedBy("has_water_bowl", has(IFWItems.water_bowl)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.GOLDEN_APPLE)
                .define('X', Items.APPLE)
                .define('Z', Items.GOLD_NUGGET)
                .pattern("ZZZ")
                .pattern("ZXZ")
                .pattern("ZZZ")
                .unlockedBy("has_apple", has(Items.APPLE))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.PUMPKIN_PIE, 1)
                .requires(IFWItems.milk_bowl, 1)
                .requires(Items.EGG, 1)
                .requires(Items.SUGAR, 1)
                .requires(IFWItems.flour, 1)
                .unlockedBy("has_pumpkin", has(Items.PUMPKIN)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.CAKE, 1)
                .requires(Items.PUMPKIN, 1)
                .requires(Items.EGG, 1)
                .requires(Items.SUGAR, 1)
                .requires(IFWItems.flour, 1)
                .unlockedBy("has_milk_bowl", has(IFWItems.milk_bowl)).save(recipeOutput);
    }
    private void metalToolRecipe(RecipeOutput recipeOutput, ItemLike shears, ItemLike shovel, ItemLike hoe, ItemLike sword
            , ItemLike pickaxe, ItemLike axe, ItemLike scythe, ItemLike mattock, ItemLike battle_axe, ItemLike war_hammer, ItemLike dagger, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shears)
                .pattern("X ")
                .pattern(" X")
                .define('X', material)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .pattern("X")
                .pattern("Y")
                .pattern("Y")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .pattern("XX")
                .pattern("Y ")
                .pattern("Y ")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, sword)
                .pattern("X")
                .pattern("X")
                .pattern("Y")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .pattern("XXX")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .pattern("XX")
                .pattern("YX")
                .pattern("Y ")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, scythe)
                .pattern("YX ")
                .pattern("Y X")
                .pattern("Y  ")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, mattock)
                .pattern("XXX")
                .pattern(" YX")
                .pattern(" Y ")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, battle_axe)
                .pattern("X X")
                .pattern("XYX")
                .pattern(" Y ")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, war_hammer)
                .pattern("XXX")
                .pattern("XYX")
                .pattern(" Y ")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, dagger)
                .pattern("X")
                .pattern("Y")
                .define('X', material)
                .define('Y', Items.STICK)
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
    }
    private void armorRecipe(RecipeOutput recipeOutput, ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("CCC")
                .pattern("C C")
                .define('C', Ingredient.of(material))
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("C C")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', Ingredient.of(material))
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("CCC")
                .pattern("C C")
                .pattern("C C")
                .define('C', Ingredient.of(material))
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
                .pattern("C C")
                .pattern("C C")
                .define('C', Ingredient.of(material))
                .unlockedBy("has_material", has(material))
                .save(recipeOutput);
    }
    private void cookingRecipe(RecipeOutput recipeOutput) {
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.RAW_COPPER), RecipeCategory.MISC, Items.COPPER_INGOT,
                        10.0F, 200, 3)
                .unlockedBy("has_raw_copper", has(Items.RAW_COPPER))
                .save(recipeOutput, "raw_copper_smelting");
        CookingLevelRecipeBuilder.smelting(Ingredient.of(IFWItems.raw_silver), RecipeCategory.MISC, IFWItems.silver_ingot,
                        15.0F, 200, 3)
                .unlockedBy("has_raw_silver", has(IFWItems.raw_silver))
                .save(recipeOutput, "raw_silver_smelting");
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.RAW_GOLD), RecipeCategory.MISC, Items.GOLD_INGOT,
                        20.0F, 200, 3)
                .unlockedBy("has_raw_gold", has(Items.RAW_GOLD))
                .save(recipeOutput, "raw_gold_smelting");
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.RAW_IRON), RecipeCategory.MISC, Items.IRON_INGOT,
                        10.0F, 200, 3)
                .unlockedBy("has_raw_iron", has(Items.RAW_IRON))
                .save(recipeOutput, "raw_iron_smelting");
        CookingLevelRecipeBuilder.smelting(Ingredient.of(IFWItems.raw_mithril), RecipeCategory.MISC, IFWItems.mithril_ingot,
                        40.0F, 200, 4)
                .unlockedBy("has_raw_mithril", has(IFWItems.raw_mithril))
                .save(recipeOutput, "raw_mithril_smelting");
        CookingLevelRecipeBuilder.smelting(Ingredient.of(IFWItems.raw_adamantium), RecipeCategory.MISC, IFWItems.adamantium_ingot,
                        100.0F, 200, 5)
                .unlockedBy("has_raw_adamantium", has(IFWItems.raw_adamantium))
                .save(recipeOutput, "raw_adamantium_smelting");
        CookingLevelRecipeBuilder.smelting(Ingredient.of(IFWItems.dough), RecipeCategory.FOOD, Items.BREAD,
                        1.0F, 200, 1)
                .unlockedBy("has_dough", has(IFWItems.dough)).save(recipeOutput, "dough_smelting");

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
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.BEEF), RecipeCategory.FOOD, Items.COOKED_BEEF, 5, 200)
                .unlockedBy("has_beef", has(Items.BEEF))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.CHICKEN), RecipeCategory.FOOD, Items.COOKED_CHICKEN, 3, 200)
                .unlockedBy("has_chicken", has(Items.CHICKEN))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.COD), RecipeCategory.FOOD, Items.COOKED_COD, 2, 200)
                .unlockedBy("has_cod", has(Items.COD))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.KELP), RecipeCategory.FOOD, Items.DRIED_KELP, 1, 200)
                .unlockedBy("has_kelp", has(Blocks.KELP))
                .save(recipeOutput, getSmeltingRecipeName(Items.DRIED_KELP));
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.SALMON), RecipeCategory.FOOD, Items.COOKED_SALMON, 3, 200)
                .unlockedBy("has_salmon", has(Items.SALMON))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.MUTTON), RecipeCategory.FOOD, Items.COOKED_MUTTON, 3, 200)
                .unlockedBy("has_mutton", has(Items.MUTTON))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.PORKCHOP), RecipeCategory.FOOD, Items.COOKED_PORKCHOP, 4, 200)
                .unlockedBy("has_porkchop", has(Items.PORKCHOP))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Items.RABBIT), RecipeCategory.FOOD, Items.COOKED_RABBIT, 2, 200)
                .unlockedBy("has_rabbit", has(Items.RABBIT))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.SEA_PICKLE), RecipeCategory.MISC, Items.LIME_DYE, 1, 200)
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
                                Items.GOLDEN_HELMET,
                                Items.GOLDEN_CHESTPLATE,
                                Items.GOLDEN_LEGGINGS,
                                Items.GOLDEN_BOOTS
                        ),
                        RecipeCategory.MISC,
                        Items.GOLD_NUGGET,
                        1F,
                        200, 2
                )
                .unlockedBy("has_golden_pickaxe", has(IFWItems.golden_pickaxe))
                .unlockedBy("has_golden_shovel", has(IFWItems.golden_shovel))
                .unlockedBy("has_golden_axe", has(IFWItems.golden_axe))
                .unlockedBy("has_golden_hoe", has(IFWItems.golden_hoe))
                .unlockedBy("has_golden_sword", has(IFWItems.golden_sword))
                .unlockedBy("has_golden_helmet", has(Items.GOLDEN_HELMET))
                .unlockedBy("has_golden_chestplate", has(Items.GOLDEN_CHESTPLATE))
                .unlockedBy("has_golden_leggings", has(Items.GOLDEN_LEGGINGS))
                .unlockedBy("has_golden_boots", has(Items.GOLDEN_BOOTS))
                .save(recipeOutput, getSmeltingRecipeName(Items.GOLD_NUGGET));
        CookingLevelRecipeBuilder.smelting(
                        Ingredient.of(
                                IFWItems.iron_pickaxe,
                                IFWItems.iron_shovel,
                                IFWItems.iron_axe,
                                IFWItems.iron_hoe,
                                IFWItems.iron_sword,
                                Items.IRON_HELMET,
                                Items.IRON_CHESTPLATE,
                                Items.IRON_LEGGINGS,
                                Items.IRON_BOOTS
                        ),
                        RecipeCategory.MISC,
                        Items.IRON_NUGGET,
                        1,
                        200, 2
                )
                .unlockedBy("has_iron_pickaxe", has(IFWItems.iron_pickaxe))
                .unlockedBy("has_iron_shovel", has(IFWItems.iron_shovel))
                .unlockedBy("has_iron_axe", has(IFWItems.iron_axe))
                .unlockedBy("has_iron_hoe", has(IFWItems.iron_hoe))
                .unlockedBy("has_iron_sword", has(IFWItems.iron_sword))
                .unlockedBy("has_iron_helmet", has(Items.IRON_HELMET))
                .unlockedBy("has_iron_chestplate", has(Items.IRON_CHESTPLATE))
                .unlockedBy("has_iron_leggings", has(Items.IRON_LEGGINGS))
                .unlockedBy("has_iron_boots", has(Items.IRON_BOOTS))
                .save(recipeOutput, getSmeltingRecipeName(Items.IRON_NUGGET));
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.CLAY), RecipeCategory.BUILDING_BLOCKS, Blocks.TERRACOTTA.asItem(), 3.5F, 200)
                .unlockedBy("has_clay_block", has(Blocks.CLAY))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.NETHERRACK), RecipeCategory.MISC, Items.NETHER_BRICK, 1, 200)
                .unlockedBy("has_netherrack", has(Blocks.NETHERRACK))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.NETHER_QUARTZ_ORE), RecipeCategory.MISC, Items.QUARTZ, 0.2F, 200)
                .unlockedBy("has_nether_quartz_ore", has(Blocks.NETHER_QUARTZ_ORE))
                .save(recipeOutput);
        CookingLevelRecipeBuilder.smelting(Ingredient.of(Blocks.WET_SPONGE), RecipeCategory.BUILDING_BLOCKS, Blocks.SPONGE.asItem(), 0.15F, 200)
                .unlockedBy("has_wet_sponge", has(Blocks.WET_SPONGE))
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
    }
}
