package huix.infinity.datagen.recipe;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;
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
        for (DeferredItem<BlockItem> runestone : IFWBlocks.MITHRILRUNESTONES) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, runestone.get())
                    .pattern(" M ")
                    .pattern("MOM")
                    .pattern(" M ")
                    .define('M', IFWItems.mithril_nugget.get())
                    .define('O', Items.OBSIDIAN)
                    .unlockedBy("has_mithril_nugget", has(IFWItems.mithril_nugget.get()))
                    .save(recipeOutput,
                            ResourceLocation.fromNamespaceAndPath(
                                    InfinityWay.MOD_ID,
                                    "runestone/" + runestone.getId().getPath()
                            )
                    );
        }

        for (DeferredItem<BlockItem> runestone : IFWBlocks.ADAMANTIUMRUNESTONES) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, runestone.get())
                    .pattern(" A ")
                    .pattern("AOA")
                    .pattern(" A ")
                    .define('A', IFWItems.adamantium_nugget.get())
                    .define('O', Items.OBSIDIAN)
                    .unlockedBy("has_adamantium_nugget", has(IFWItems.adamantium_nugget.get()))
                    .save(recipeOutput,
                            ResourceLocation.fromNamespaceAndPath(
                                    InfinityWay.MOD_ID,
                                    "runestone/" + runestone.getId().getPath()
                            )
                    );
        }


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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IFWBlocks.emerald_enchanting_table)
                .pattern(" B ")
                .pattern("EOE")
                .pattern("OOO")
                .define('O', Blocks.OBSIDIAN)
                .define('B', Items.BOOK)
                .define('E', Items.EMERALD)
                .unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);

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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(IFWItems.raw_silver), RecipeCategory.MISC, IFWItems.silver_ingot,
                        15.0F, 200)
                .unlockedBy("has_raw_silver", has(IFWItems.raw_silver))
                .save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.RAW_GOLD), RecipeCategory.MISC, Items.GOLD_INGOT,
                        20.0F, 200)
                .unlockedBy("has_raw_gold", has(Items.RAW_GOLD))
                .save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(IFWItems.raw_mithril), RecipeCategory.MISC, IFWItems.mithril_ingot,
                        40.0F, 200)
                .unlockedBy("has_raw_mithril", has(IFWItems.raw_mithril))
                .save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(IFWItems.raw_adamantium), RecipeCategory.MISC, IFWItems.adamantium_ingot,
                        100.0F, 200)
                .unlockedBy("has_raw_adamantium", has(IFWItems.raw_adamantium))
                .save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(IFWItems.dough), RecipeCategory.FOOD, Items.BREAD,
                        1.0F, 200)
                .unlockedBy("has_dough", has(IFWItems.dough)).save(recipeOutput, "ifw_bread");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(IFWItems.cooked_worm), RecipeCategory.FOOD, IFWItems.worm,
                        1.0F, 200)
                .unlockedBy("has_worm", has(IFWItems.worm)).save(recipeOutput, "worm_smelting");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.BOWL, 1)
                .requires(IFWItems.water_bowl.get(),1)
                .unlockedBy("has_water_bowl", has(IFWItems.water_bowl.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "water_bowl_to_bowl"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE, 1)
                .requires(Items.EXPERIENCE_BOTTLE,1)
                .requires(Items.GOLDEN_APPLE,1)
                .unlockedBy("has_experience_bottle", has(Items.EXPERIENCE_BOTTLE))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "crafting_enchanted_golden_apple"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, IFWItems.copper_bucket.get(), 1)
                .requires(IFWItems.stone_copper_bucket.get(), 1)
                .unlockedBy("has_stone_copper_bucket", has(IFWItems.stone_copper_bucket.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "copper_bucket_filled_with_stone"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, IFWItems.silver_bucket.get(), 1)
                .requires(IFWItems.stone_silver_bucket.get(), 1)
                .unlockedBy("has_stone_silver_bucket", has(IFWItems.stone_silver_bucket.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "silver_bucket_filled_with_stone"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, IFWItems.gold_bucket.get(), 1)
                .requires(IFWItems.stone_gold_bucket.get(), 1)
                .unlockedBy("has_stone_gold_bucket", has(IFWItems.stone_gold_bucket.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "gold_bucket_filled_with_stone"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, Items.BUCKET, 1)
                .requires(IFWItems.stone_iron_bucket.get(), 1)
                .unlockedBy("has_stone_iron_bucket", has(IFWItems.stone_iron_bucket.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "iron_bucket_filled_with_stone"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, IFWItems.ancient_metal_bucket.get(), 1)
                .requires(IFWItems.stone_ancient_metal_bucket.get(), 1)
                .unlockedBy("has_stone_ancient_metal_bucket", has(IFWItems.stone_ancient_metal_bucket.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "ancient_metal_bucket_filled_with_stone"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, IFWItems.mithril_bucket.get(), 1)
                .requires(IFWItems.stone_mithril_bucket.get(), 1)
                .unlockedBy("has_stone_mithril_bucket", has(IFWItems.stone_mithril_bucket.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "mithril_bucket_filled_with_stone"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, IFWItems.adamantium_bucket.get(), 1)
                .requires(IFWItems.stone_adamantium_bucket.get(), 1)
                .unlockedBy("has_stone_adamantium_bucket", has(IFWItems.stone_adamantium_bucket.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("infinity_way", "adamantium_bucket_filled_with_stone"));

    }
}
