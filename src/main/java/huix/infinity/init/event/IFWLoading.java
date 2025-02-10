package huix.infinity.init.event;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.enchantment.Recipes;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.animal.Livestock;
import huix.infinity.common.world.food.IFWFoods;
import huix.infinity.common.world.food.RebuildFoods;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.InfinityWay;
import huix.infinity.util.ReplaceHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IFWLoading {

    @SubscribeEvent
    public static void injectItem(final FMLLoadCompleteEvent event) {
        rebuildFood();
        buildEnchantmentRecipe();
    }


    private static void buildEnchantmentRecipe() {
        ReplaceHelper.enchantmentRecipe(Items.GOLDEN_APPLE, Recipes.enchanted_golden_apple);
    }

    public static void injectCookingLevel() {
        ReplaceHelper.cookingLevel(Items.TORCH, 2);
        ReplaceHelper.cookingLevel(Items.COAL, 3);
        ReplaceHelper.cookingLevel(Items.BLAZE_ROD, 5);
        ReplaceHelper.cookingLevel(Tags.Items.BUCKETS_LAVA, 4);
        ReplaceHelper.cookingLevel(Items.COAL_BLOCK, 3);
        ReplaceHelper.cookingLevel(Items.CHARCOAL, 2);
        ReplaceHelper.cookingLevel(ItemTags.LOGS, 1);
        ReplaceHelper.cookingLevel(ItemTags.BAMBOO_BLOCKS, 1);
        ReplaceHelper.cookingLevel(ItemTags.PLANKS, 1);
        ReplaceHelper.cookingLevel(Items.BAMBOO_MOSAIC, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOODEN_STAIRS, 1);
        ReplaceHelper.cookingLevel(Items.BAMBOO_MOSAIC_STAIRS, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOODEN_SLABS, 1);
        ReplaceHelper.cookingLevel(Items.BAMBOO_MOSAIC_SLAB, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOODEN_TRAPDOORS, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOODEN_PRESSURE_PLATES, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOODEN_FENCES, 1);
        ReplaceHelper.cookingLevel(ItemTags.FENCE_GATES, 1);
        ReplaceHelper.cookingLevel(Items.NOTE_BLOCK, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOODEN_SLABS, 1);
        ReplaceHelper.cookingLevel(Items.BOOKSHELF, 1);
        ReplaceHelper.cookingLevel(Items.CHISELED_BOOKSHELF, 1);
        ReplaceHelper.cookingLevel(Items.LECTERN, 1);
        ReplaceHelper.cookingLevel(Items.JUKEBOX, 1);
        ReplaceHelper.cookingLevel(Items.CHEST, 1);
        ReplaceHelper.cookingLevel(Items.TRAPPED_CHEST, 1);
        ReplaceHelper.cookingLevel(Items.CRAFTING_TABLE, 1);
        ReplaceHelper.cookingLevel(Items.DAYLIGHT_DETECTOR, 1);
        ReplaceHelper.cookingLevel(ItemTags.BANNERS, 1);
        ReplaceHelper.cookingLevel(Items.BOW, 1);
        ReplaceHelper.cookingLevel(Items.FISHING_ROD, 1);
        ReplaceHelper.cookingLevel(Items.LADDER, 1);
        ReplaceHelper.cookingLevel(ItemTags.SIGNS, 1);
        ReplaceHelper.cookingLevel(ItemTags.HANGING_SIGNS, 1);
        ReplaceHelper.cookingLevel(IFWItems.wooden_club.get(), 1);
        ReplaceHelper.cookingLevel(IFWItems.wooden_shovel.get(), 1);
        ReplaceHelper.cookingLevel(IFWItems.manure.get(), 1);
        ReplaceHelper.cookingLevel(ItemTags.WOODEN_DOORS, 1);
        ReplaceHelper.cookingLevel(ItemTags.BOATS, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOOL, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOODEN_BUTTONS, 1);
        ReplaceHelper.cookingLevel(Items.STICK, 1);
        ReplaceHelper.cookingLevel(ItemTags.SAPLINGS, 1);
        ReplaceHelper.cookingLevel(Items.BOWL, 1);
        ReplaceHelper.cookingLevel(ItemTags.WOOL_CARPETS, 1);
        ReplaceHelper.cookingLevel(Items.DRIED_KELP_BLOCK, 1);
        ReplaceHelper.cookingLevel(Items.CROSSBOW, 1);
        ReplaceHelper.cookingLevel(Items.BAMBOO, 1);
        ReplaceHelper.cookingLevel(Items.DEAD_BUSH, 1);
        ReplaceHelper.cookingLevel(Items.SCAFFOLDING, 1);
        ReplaceHelper.cookingLevel(Items.LOOM, 1);
        ReplaceHelper.cookingLevel(Items.BARREL, 1);
        ReplaceHelper.cookingLevel(Items.CARTOGRAPHY_TABLE, 1);
        ReplaceHelper.cookingLevel(Items.FLETCHING_TABLE, 1);
        ReplaceHelper.cookingLevel(Items.SMITHING_TABLE, 1);
        ReplaceHelper.cookingLevel(Items.COMPOSTER, 1);
        ReplaceHelper.cookingLevel(Items.AZALEA, 1);
        ReplaceHelper.cookingLevel(Items.FLOWERING_AZALEA, 1);
        ReplaceHelper.cookingLevel(Items.MANGROVE_ROOTS, 1);
        ReplaceHelper.cookingLevel(IFWItems.manure.get(), 1);

        ReplaceHelper.beCookLevel(Items.RAW_COPPER, 3);
        ReplaceHelper.beCookLevel(IFWItems.raw_silver.get(), 3);
        ReplaceHelper.beCookLevel(Items.RAW_GOLD, 3);
        ReplaceHelper.beCookLevel(Items.RAW_IRON, 3);
        ReplaceHelper.beCookLevel(IFWItems.raw_mithril.get(), 4);
        ReplaceHelper.beCookLevel(IFWItems.raw_adamantium.get(), 5);

    }

    public static void injectAnvil() {
        ReplaceHelper.anvilDamage(IFWBlocks.chipped_copper_anvil_item.get(), 39680);
        ReplaceHelper.anvilDamage(IFWBlocks.damaged_copper_anvil_item.get(), 99200);
        ReplaceHelper.anvilDamage(IFWBlocks.chipped_silver_anvil_item.get(), 39680);
        ReplaceHelper.anvilDamage(IFWBlocks.damaged_silver_anvil_item.get(), 99200);
        ReplaceHelper.anvilDamage(IFWBlocks.chipped_gold_anvil_item.get(), 39680);
        ReplaceHelper.anvilDamage(IFWBlocks.damaged_gold_anvil_item.get(), 99200);
        ReplaceHelper.anvilDamage(IFWBlocks.chipped_iron_anvil_item.get(), 198400);
        ReplaceHelper.anvilDamage(IFWBlocks.damaged_iron_anvil_item.get(), 79360);
        ReplaceHelper.anvilDamage(IFWBlocks.chipped_ancient_metal_anvil_item.get(), 396800);
        ReplaceHelper.anvilDamage(IFWBlocks.damaged_ancient_metal_anvil_item.get(), 158720);
        ReplaceHelper.anvilDamage(IFWBlocks.chipped_mithril_anvil_item.get(), 1587200);
        ReplaceHelper.anvilDamage(IFWBlocks.damaged_mithril_anvil_item.get(), 634880);
        ReplaceHelper.anvilDamage(IFWBlocks.chipped_adamantium_anvil_item.get(), 6348800);
        ReplaceHelper.anvilDamage(IFWBlocks.damaged_adamantium_anvil_item.get(), 2539520);
    }

    public static void rebuildStackSize() {
        ReplaceHelper.stackSize(Tags.Items.STRINGS, 16);
        ReplaceHelper.stackSize(Items.BOOK, 16);
        ReplaceHelper.stackSize(Items.APPLE, 16);
        ReplaceHelper.stackSize(Items.ENCHANTED_GOLDEN_APPLE, 16);
        ReplaceHelper.stackSize(Items.GOLDEN_APPLE, 16);
        ReplaceHelper.stackSize(Items.STICK, 32);
        ReplaceHelper.stackSize(Items.FLINT, 16);
        ReplaceHelper.stackSize(Items.WHEAT, 16);
        ReplaceHelper.stackSize(Items.BONE, 16);
        ReplaceHelper.stackSize(Tags.Items.INGOTS, 10);
        ReplaceHelper.stackSize(ItemTags.ARROWS, 16);
        ReplaceHelper.stackSizeByBlock(BlockTags.MINEABLE_WITH_SHOVEL, 8);
        ReplaceHelper.stackSizeByBlock(BlockTags.DIRT, 4);
        ReplaceHelper.stackSizeByBlock(BlockTags.MINEABLE_WITH_AXE, 16);
        ReplaceHelper.stackSizeByBlock(BlockTags.MINEABLE_WITH_HOE, 16);
        ReplaceHelper.stackSizeByBlock(BlockTags.LEAVES, 4);
        ReplaceHelper.stackSizeByBlock(BlockTags.CANDLES, 32);
        ReplaceHelper.stackSizeByBlock(BlockTags.CROPS, 16);
        ReplaceHelper.stackSize(Items.TURTLE_EGG, 16);
        ReplaceHelper.stackSize(Items.FROGSPAWN, 16);
        ReplaceHelper.stackSize(Items.SNIFFER_EGG, 16);
        ReplaceHelper.stackSize(Items.SLIME_BLOCK, 16);
        ReplaceHelper.stackSize(Items.HONEY_BLOCK, 16);
        ReplaceHelper.stackSize(Items.PEARLESCENT_FROGLIGHT, 16);
        ReplaceHelper.stackSize(Items.VERDANT_FROGLIGHT, 16);
        ReplaceHelper.stackSize(Items.OCHRE_FROGLIGHT, 16);
        ReplaceHelper.stackSize(Items.COCOA_BEANS, 16);
        ReplaceHelper.stackSize(Items.TORCH, 16);
        ReplaceHelper.stackSize(Items.REDSTONE_TORCH, 16);
        ReplaceHelper.stackSize(Items.SOUL_TORCH, 16);
        ReplaceHelper.stackSize(Items.NETHER_WART, 64);
        ReplaceHelper.stackSize(ItemTags.COALS, 16);
        ReplaceHelper.stackSize(Items.RAW_COPPER, 16);
        ReplaceHelper.stackSize(Items.RAW_IRON, 16);
        ReplaceHelper.stackSize(Items.RAW_GOLD, 16);
        ReplaceHelper.stackSize(Tags.Items.GEMS, 32);
        ReplaceHelper.stackSize(Tags.Items.BUCKETS_EMPTY, 8);
        ReplaceHelper.stackSize(Items.BLAZE_ROD, 16);
        ReplaceHelper.stackSize(Items.BREEZE_ROD, 16);
        ReplaceHelper.stackSize(Items.GLOWSTONE, 4);
        ReplaceHelper.stackSize(ItemTags.WOOL, 8);
        ReplaceHelper.stackSize(ItemTags.PLANKS, 8);
        ReplaceHelper.stackSize(ItemTags.STONE_BRICKS, 4);
        ReplaceHelper.stackSize(ItemTags.BUTTONS, 16);
        ReplaceHelper.stackSize(ItemTags.WOOL_CARPETS, 16);
        ReplaceHelper.stackSize(ItemTags.WOODEN_STAIRS, 8);
        ReplaceHelper.stackSize(ItemTags.WOODEN_SLABS, 8);
        ReplaceHelper.stackSize(ItemTags.WOODEN_FENCES, 8);
        ReplaceHelper.stackSize(ItemTags.FENCE_GATES, 8);
        ReplaceHelper.stackSize(ItemTags.WOODEN_PRESSURE_PLATES, 16);
        ReplaceHelper.stackSize(ItemTags.WOODEN_TRAPDOORS, 8);
        ReplaceHelper.stackSize(ItemTags.WOODEN_DOORS, 1);
        ReplaceHelper.stackSize(ItemTags.SAPLINGS, 16);
        ReplaceHelper.stackSize(ItemTags.WOOL_CARPETS, 16);
        ReplaceHelper.stackSize(ItemTags.LOGS_THAT_BURN, 4);
        ReplaceHelper.stackSize(ItemTags.LOGS, 4);
        ReplaceHelper.stackSize(ItemTags.CRIMSON_STEMS, 8);
        ReplaceHelper.stackSize(ItemTags.WARPED_STEMS, 8);
        ReplaceHelper.stackSize(ItemTags.BAMBOO_BLOCKS, 4);
        ReplaceHelper.stackSize(ItemTags.WART_BLOCKS, 4);
        ReplaceHelper.stackSize(ItemTags.STAIRS, 8);
        ReplaceHelper.stackSize(ItemTags.SLABS, 8);
        ReplaceHelper.stackSize(ItemTags.WALLS, 8);
        ReplaceHelper.stackSize(ItemTags.WART_BLOCKS, 4);
        ReplaceHelper.stackSize(ItemTags.ANVIL, 1);
        ReplaceHelper.stackSize(ItemTags.SMALL_FLOWERS, 32);
        ReplaceHelper.stackSize(ItemTags.TALL_FLOWERS, 16);
        ReplaceHelper.stackSize(ItemTags.MEAT, 16);
        ReplaceHelper.stackSize(ItemTags.STAIRS, 8);
        ReplaceHelper.stackSize(ItemTags.SLABS, 8);
        ReplaceHelper.stackSize(ItemTags.WALLS, 8);
        ReplaceHelper.stackSize(ItemTags.WART_BLOCKS, 4);
        //glass_pane
        ReplaceHelper.stackSize(Items.LIME_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.BLACK_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.BLUE_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.PINK_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.BROWN_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.PURPLE_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.CYAN_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.GRAY_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.GREEN_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.LIGHT_BLUE_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.LIGHT_GRAY_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.MAGENTA_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.ORANGE_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.RED_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.WHITE_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.YELLOW_STAINED_GLASS_PANE, 16);
        ReplaceHelper.stackSize(Items.BROWN_STAINED_GLASS_PANE, 16);
        //normal Block
        ReplaceHelper.stackSize(Items.STONE, 4);
        ReplaceHelper.stackSize(Items.GRANITE, 4);
        ReplaceHelper.stackSize(Items.GRAVEL, 4);
        ReplaceHelper.stackSize(Items.POLISHED_GRANITE, 4);
        ReplaceHelper.stackSize(Items.DIORITE, 4);
        ReplaceHelper.stackSize(Items.POLISHED_DIORITE, 4);
        ReplaceHelper.stackSize(Items.BOOKSHELF, 4);
        ReplaceHelper.stackSize(Items.CHISELED_DEEPSLATE, 4);
        ReplaceHelper.stackSize(Items.MOSSY_COBBLESTONE, 4);
        ReplaceHelper.stackSize(Items.DEEPSLATE, 4);
        ReplaceHelper.stackSize(Items.COBBLED_DEEPSLATE, 4);
        ReplaceHelper.stackSize(Items.REINFORCED_DEEPSLATE, 4);
        ReplaceHelper.stackSize(Items.TUFF, 4);
        ReplaceHelper.stackSize(Items.CHISELED_TUFF, 4);
        ReplaceHelper.stackSize(Items.SANDSTONE, 4);
        ReplaceHelper.stackSize(Items.SMOOTH_SANDSTONE, 4);
        ReplaceHelper.stackSize(Items.CHISELED_SANDSTONE, 4);
        ReplaceHelper.stackSize(Items.SANDSTONE, 4);
        ReplaceHelper.stackSize(Items.PRISMARINE, 4);
        ReplaceHelper.stackSize(Items.NETHERRACK, 4);
        ReplaceHelper.stackSize(Items.NETHER_BRICK, 4);
        ReplaceHelper.stackSize(Items.CRACKED_NETHER_BRICKS, 4);
        ReplaceHelper.stackSize(Items.COAL_BLOCK, 4);
        ReplaceHelper.stackSize(Items.IRON_BLOCK, 4);
        ReplaceHelper.stackSize(Items.GOLD_BLOCK, 4);
        ReplaceHelper.stackSize(Items.CHISELED_TUFF, 4);
        ReplaceHelper.stackSize(Items.SANDSTONE, 4);
        ReplaceHelper.stackSize(Items.FEATHER, 16);
        ReplaceHelper.stackSize(Items.CLAY_BALL, 16);
        ReplaceHelper.stackSize(Items.SLIME_BALL, 16);
        ReplaceHelper.stackSize(Items.ARMADILLO_SCUTE, 16);
        ReplaceHelper.stackSize(Items.TURTLE_SCUTE, 16);
        ReplaceHelper.stackSize(Items.GLOW_INK_SAC, 16);
        ReplaceHelper.stackSize(Items.INK_SAC, 16);
        ReplaceHelper.stackSize(Items.HONEYCOMB, 16);
        ReplaceHelper.stackSize(Items.RABBIT, 32);
        ReplaceHelper.stackSize(Items.LEATHER, 16);
        ReplaceHelper.stackSize(Items.PRISMARINE_SHARD, 16);
        ReplaceHelper.stackSize(Items.NAUTILUS_SHELL, 16);
        ReplaceHelper.stackSize(Items.FIRE_CHARGE, 16);
        ReplaceHelper.stackSize(Items.BLAZE_ROD, 16);
        ReplaceHelper.stackSize(Items.BREEZE_ROD, 16);
        ReplaceHelper.stackSize(Items.HEAVY_CORE, 4);
        ReplaceHelper.stackSize(Items.NETHER_STAR, 16);
        ReplaceHelper.stackSize(Items.ENDER_EYE, 16);
        ReplaceHelper.stackSize(Items.REDSTONE, 16);
        ReplaceHelper.stackSize(Items.CHEST, 8);
        ReplaceHelper.stackSize(Items.SHULKER_SHELL, 16);
        ReplaceHelper.stackSize(Items.POPPED_CHORUS_FRUIT, 16);
        ReplaceHelper.stackSize(Items.ECHO_SHARD, 16);
        ReplaceHelper.stackSize(Items.DISC_FRAGMENT_5, 32);
        ReplaceHelper.stackSize(Items.BOWL, 16);
        ReplaceHelper.stackSize(Items.BRICK, 16);
        ReplaceHelper.stackSize(Items.NETHER_BRICK, 16);
        ReplaceHelper.stackSize(Items.FIREWORK_STAR, 16);
        ReplaceHelper.stackSize(Items.GLOWSTONE_DUST, 16);
        ReplaceHelper.stackSize(Items.GUNPOWDER, 16);
        ReplaceHelper.stackSize(Items.DRAGON_BREATH, 16);
        ReplaceHelper.stackSize(Items.FERMENTED_SPIDER_EYE, 16);
        ReplaceHelper.stackSize(Items.BLAZE_POWDER, 32);
        ReplaceHelper.stackSize(Items.SUGAR, 16);
        ReplaceHelper.stackSize(Items.RABBIT_FOOT, 16);
        ReplaceHelper.stackSize(Items.GLISTERING_MELON_SLICE, 16);
        ReplaceHelper.stackSize(Items.SPIDER_EYE, 16);
        ReplaceHelper.stackSize(Items.PUFFERFISH, 16);
        ReplaceHelper.stackSize(Items.MAGMA_CREAM, 32);
        ReplaceHelper.stackSize(Items.GOLDEN_CARROT, 16);
        ReplaceHelper.stackSize(Items.GHAST_TEAR, 16);
        ReplaceHelper.stackSize(Items.PHANTOM_MEMBRANE, 16);
        ReplaceHelper.stackSize(Items.EXPERIENCE_BOTTLE, 16);
        ReplaceHelper.stackSize(Items.TRIAL_KEY, 16);
        ReplaceHelper.stackSize(Items.OMINOUS_TRIAL_KEY, 16);
        ReplaceHelper.stackSize(Items.MELON_SLICE, 16);
        ReplaceHelper.stackSize(Items.CHORUS_FRUIT, 16);
        ReplaceHelper.stackSize(Items.BAKED_POTATO, 16);
        ReplaceHelper.stackSize(Items.POISONOUS_POTATO, 16);
        ReplaceHelper.stackSize(Items.BEETROOT, 16);
        ReplaceHelper.stackSize(Items.DRIED_KELP, 16);
        ReplaceHelper.stackSize(Items.RABBIT, 16);
        ReplaceHelper.stackSize(Items.COOKED_RABBIT, 16);
        ReplaceHelper.stackSize(Items.COD, 16);
        ReplaceHelper.stackSize(Items.COOKED_COD, 16);
        ReplaceHelper.stackSize(Items.SALMON, 16);
        ReplaceHelper.stackSize(Items.COOKED_SALMON, 16);
        ReplaceHelper.stackSize(Items.TROPICAL_FISH, 16);
        ReplaceHelper.stackSize(Items.BREAD, 16);
        ReplaceHelper.stackSize(Items.COOKIE, 16);
        ReplaceHelper.stackSize(Items.CAKE, 8);
        ReplaceHelper.stackSize(Items.PUMPKIN_PIE, 16);
        ReplaceHelper.stackSize(Items.MELON, 8);
        ReplaceHelper.stackSize(Items.PUMPKIN, 8);
        ReplaceHelper.stackSize(Items.CARVED_PUMPKIN, 8);
        ReplaceHelper.stackSize(Items.JACK_O_LANTERN, 8);
        ReplaceHelper.stackSize(Items.HAY_BLOCK, 8);
        ReplaceHelper.stackSize(Items.BEE_NEST, 1);
        ReplaceHelper.stackSize(Items.COBWEB, 16);
        ReplaceHelper.stackSize(Items.BEDROCK, 4);
        ReplaceHelper.stackSize(Items.TNT, 4);
        ReplaceHelper.stackSize(Items.FIREWORK_ROCKET, 16);
        ReplaceHelper.stackSizeByBlock(BlockTags.IMPERMEABLE, 8);
        ReplaceHelper.stackSizeByBlock(BlockTags.FENCES, 8);
        ReplaceHelper.stackSizeByBlock(BlockTags.TRAPDOORS, 16);
        ReplaceHelper.stackSize(Items.COBBLESTONE, 4);
        ReplaceHelper.stackSize(Items.SMOOTH_STONE, 4);
        ReplaceHelper.stackSize(Items.ANDESITE, 4);
        ReplaceHelper.stackSize(Items.POLISHED_ANDESITE, 4);
        ReplaceHelper.stackSize(Items.POLISHED_DEEPSLATE, 4);
        ReplaceHelper.stackSize(Items.DEEPSLATE_BRICKS, 4);
        ReplaceHelper.stackSize(Items.CRACKED_DEEPSLATE_BRICKS, 4);
        ReplaceHelper.stackSize(Items.DEEPSLATE_TILES, 4);
        ReplaceHelper.stackSize(Items.CRACKED_DEEPSLATE_TILES, 4);
        ReplaceHelper.stackSize(Items.POLISHED_TUFF, 4);
        ReplaceHelper.stackSize(Items.TUFF_BRICKS, 4);
        ReplaceHelper.stackSize(Items.CHISELED_TUFF, 4);
        ReplaceHelper.stackSize(Items.CUT_SANDSTONE, 4);
        ReplaceHelper.stackSize(Items.RED_SANDSTONE, 4);
        ReplaceHelper.stackSize(Items.SEA_LANTERN, 4);
        ReplaceHelper.stackSize(Items.PRISMARINE_BRICKS, 4);
        ReplaceHelper.stackSize(Items.DARK_PRISMARINE, 4);
        ReplaceHelper.stackSize(Items.NETHER_BRICKS, 4);
        ReplaceHelper.stackSize(Items.CHISELED_NETHER_BRICKS, 4);
        ReplaceHelper.stackSize(Items.RED_NETHER_BRICKS, 4);
        ReplaceHelper.stackSize(Items.BASALT, 4);
        ReplaceHelper.stackSize(Items.SMOOTH_BASALT, 4);
        ReplaceHelper.stackSize(Items.POLISHED_BASALT, 4);
        ReplaceHelper.stackSize(Items.BLACKSTONE, 4);
        ReplaceHelper.stackSize(Items.GILDED_BLACKSTONE, 4);
        ReplaceHelper.stackSize(Items.CHISELED_POLISHED_BLACKSTONE, 4);
        ReplaceHelper.stackSize(Items.POLISHED_BLACKSTONE, 4);
        ReplaceHelper.stackSize(Items.POLISHED_BLACKSTONE_BRICKS, 4);
        ReplaceHelper.stackSize(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS, 4);
        ReplaceHelper.stackSize(Items.END_STONE, 4);
        ReplaceHelper.stackSize(Items.END_STONE_BRICKS, 4);
        ReplaceHelper.stackSize(Items.PURPUR_BLOCK, 4);
        ReplaceHelper.stackSize(Items.PURPUR_PILLAR, 4);
        ReplaceHelper.stackSize(Items.IRON_BARS, 16);
        ReplaceHelper.stackSize(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, 4);
        ReplaceHelper.stackSize(Items.CHAIN, 16);
        ReplaceHelper.stackSize(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, 4);
        ReplaceHelper.stackSize(Items.REDSTONE_BLOCK, 4);
        ReplaceHelper.stackSize(Items.EMERALD_BLOCK, 4);
        ReplaceHelper.stackSize(Items.LAPIS_BLOCK, 4);
        ReplaceHelper.stackSize(Items.DIAMOND_BLOCK, 4);
        ReplaceHelper.stackSize(Items.NETHERITE_BLOCK, 4);
        ReplaceHelper.stackSize(Items.QUARTZ_BLOCK, 4);
        ReplaceHelper.stackSize(Items.QUARTZ_BRICKS, 4);
        ReplaceHelper.stackSize(Items.CHISELED_QUARTZ_BLOCK, 4);
        ReplaceHelper.stackSize(Items.QUARTZ_PILLAR, 4);
        ReplaceHelper.stackSize(Items.SMOOTH_QUARTZ, 4);
        ReplaceHelper.stackSize(Items.AMETHYST_BLOCK, 4);
        ReplaceHelper.stackSize(Items.COPPER_BLOCK, 4);
        ReplaceHelper.stackSize(Items.BONE_BLOCK, 4);
        ReplaceHelper.stackSize(Items.PISTON, 4);
        ReplaceHelper.stackSize(Items.STICKY_PISTON, 4);
        ReplaceHelper.stackSize(Items.LEVER, 16);
        ReplaceHelper.stackSize(Items.REPEATER, 16);
        ReplaceHelper.stackSize(Items.COMPARATOR, 16);
        ReplaceHelper.stackSize(Items.TARGET, 4);
        ReplaceHelper.stackSize(Items.COPPER_BULB, 4);
        ReplaceHelper.stackSize(Items.EXPOSED_COPPER_BULB, 4);
        ReplaceHelper.stackSize(Items.OXIDIZED_COPPER_BULB, 4);
        ReplaceHelper.stackSize(Items.WAXED_COPPER_BULB, 4);
        ReplaceHelper.stackSize(Items.WAXED_EXPOSED_COPPER_BULB, 4);
        ReplaceHelper.stackSize(Items.WAXED_WEATHERED_COPPER_BULB, 4);
        ReplaceHelper.stackSize(Items.WEATHERED_COPPER_BULB, 4);
        ReplaceHelper.stackSize(Items.TRIPWIRE_HOOK, 4);
        ReplaceHelper.stackSize(Items.LIGHTNING_ROD, 4);
        ReplaceHelper.stackSize(Items.DISPENSER, 4);
        ReplaceHelper.stackSize(Items.DROPPER, 4);
        ReplaceHelper.stackSize(Items.CRAFTER, 4);
        ReplaceHelper.stackSize(Items.HOPPER, 8);
        ReplaceHelper.stackSize(Items.CHISELED_BOOKSHELF, 4);
        ReplaceHelper.stackSize(Items.FURNACE, 1);
        ReplaceHelper.stackSize(Items.DECORATED_POT, 4);
        ReplaceHelper.stackSize(Items.OBSERVER, 4);
        ReplaceHelper.stackSize(Items.CAULDRON, 4);
        ReplaceHelper.stackSize(Items.RAIL, 16);
        ReplaceHelper.stackSize(Items.ACTIVATOR_RAIL, 16);
        ReplaceHelper.stackSize(Items.DETECTOR_RAIL, 16);
        ReplaceHelper.stackSize(Items.POWERED_RAIL, 16);
        ReplaceHelper.stackSize(Items.REDSTONE_LAMP, 4);
        ReplaceHelper.stackSize(Items.BELL, 4);
        ReplaceHelper.stackSize(Items.WIND_CHARGE, 16);
        ReplaceHelper.stackSize(Items.END_CRYSTAL, 16);
        ReplaceHelper.stackSizeByBlock(BlockTags.TERRACOTTA, 8);

    }

    private static void rebuildFood() {
        ReplaceHelper.foodOverride(Items.APPLE, RebuildFoods.APPLE);
        ReplaceHelper.foodAdd(Items.APPLE, IFWFoods.apple);
        ReplaceHelper.foodOverride(Items.BREAD, RebuildFoods.BREAD);
        ReplaceHelper.foodOverride(Items.PORKCHOP, RebuildFoods.PORKCHOP);
        ReplaceHelper.foodAdd(Items.PORKCHOP, IFWFoods.porkchop);
        ReplaceHelper.foodOverride(Items.COOKED_PORKCHOP, RebuildFoods.COOKED_PORKCHOP);
        ReplaceHelper.foodAdd(Items.COOKED_PORKCHOP, IFWFoods.cooked_porkchop);
        ReplaceHelper.foodOverride(Items.ENCHANTED_GOLDEN_APPLE, RebuildFoods.ENCHANTED_GOLDEN_APPLE);
        ReplaceHelper.foodAdd(Items.ENCHANTED_GOLDEN_APPLE, IFWFoods.enchanted_golden_apple);
        ReplaceHelper.foodAdd(Items.COD, IFWFoods.cod);
        ReplaceHelper.foodAdd(Items.SALMON, IFWFoods.salmon);
        ReplaceHelper.foodOverride(Items.PUFFERFISH, RebuildFoods.PUFFERFISH);
        ReplaceHelper.foodAdd(Items.COOKED_COD, IFWFoods.cooked_cod);
        ReplaceHelper.foodOverride(Items.COOKED_SALMON, RebuildFoods.COOKED_SALMON);
        ReplaceHelper.foodAdd(Items.COOKED_SALMON, IFWFoods.cooked_salmon);
        ReplaceHelper.foodOverride(Items.COOKIE, RebuildFoods.COOKIE);
        ReplaceHelper.foodAdd(Items.COOKIE, IFWFoods.cookie);
        ReplaceHelper.foodOverride(Items.MELON_SLICE, RebuildFoods.MELON_SLICE);
        ReplaceHelper.foodAdd(Items.MELON_SLICE, IFWFoods.melon_slice);
        ReplaceHelper.foodOverride(Items.DRIED_KELP, RebuildFoods.DRIED_KELP);
        ReplaceHelper.foodAdd(Items.DRIED_KELP, IFWFoods.dried_kelp);
        ReplaceHelper.foodOverride(Items.BEEF, RebuildFoods.BEEF);
        ReplaceHelper.foodAdd(Items.BEEF, IFWFoods.beef);
        ReplaceHelper.foodOverride(Items.COOKED_BEEF, RebuildFoods.COOKED_BEEF);
        ReplaceHelper.foodAdd(Items.COOKED_BEEF, IFWFoods.cooked_beef);
        ReplaceHelper.foodOverride(Items.CHICKEN, RebuildFoods.CHICKEN);
        ReplaceHelper.foodAdd(Items.CHICKEN, IFWFoods.chicken);
        ReplaceHelper.foodAdd(Items.COOKED_CHICKEN, IFWFoods.cooked_chicken);
        ReplaceHelper.foodOverride(Items.ROTTEN_FLESH, RebuildFoods.ROTTEN_FLESH);
        ReplaceHelper.foodAdd(Items.ROTTEN_FLESH, IFWFoods.rotten_flesh);
        ReplaceHelper.foodOverride(Items.SPIDER_EYE, RebuildFoods.SPIDER_EYE);
        ReplaceHelper.foodAdd(Items.SPIDER_EYE, IFWFoods.spider_eye);
        ReplaceHelper.foodOverride(Items.CARROT, RebuildFoods.CARROT);
        ReplaceHelper.foodAdd(Items.CARROT, IFWFoods.carrot);
        ReplaceHelper.foodOverride(Items.BAKED_POTATO, RebuildFoods.BAKED_POTATO);
        ReplaceHelper.foodOverride(Items.POISONOUS_POTATO, RebuildFoods.POISONOUS_POTATO);
        ReplaceHelper.foodOverride(Items.GOLDEN_CARROT, RebuildFoods.GOLDEN_CARROT);
        ReplaceHelper.foodAdd(Items.GOLDEN_CARROT, IFWFoods.golden_carror);
        ReplaceHelper.foodOverride(Items.PUMPKIN_PIE, RebuildFoods.PUMPKIN_PIE);
        ReplaceHelper.foodAdd(Items.PUMPKIN_PIE, IFWFoods.pumpkin_pie);
        ReplaceHelper.foodOverride(Items.RABBIT, RebuildFoods.RABBIT);
        ReplaceHelper.foodAdd(Items.RABBIT, IFWFoods.rabbit);
        ReplaceHelper.foodOverride(Items.COOKED_RABBIT, RebuildFoods.COOKED_RABBIT);
        ReplaceHelper.foodAdd(Items.COOKED_RABBIT, IFWFoods.cooked_rabbit);
        ReplaceHelper.foodAdd(Items.RABBIT_STEW, IFWFoods.rabbit_stew);
        ReplaceHelper.foodOverride(Items.MUTTON, RebuildFoods.MUTTON);
        ReplaceHelper.foodAdd(Items.MUTTON, IFWFoods.mutton);
        ReplaceHelper.foodOverride(Items.COOKED_MUTTON, RebuildFoods.COOKED_MUTTON);
        ReplaceHelper.foodAdd(Items.COOKED_MUTTON, IFWFoods.cooked_mutton);
        ReplaceHelper.foodOverride(Items.BEETROOT, RebuildFoods.BEETROOT);
        ReplaceHelper.foodAdd(Items.BEETROOT, IFWFoods.beefroot);
        ReplaceHelper.foodAdd(Items.BEETROOT_SOUP, IFWFoods.beefroot_soup);
        ReplaceHelper.foodOverride(Items.SWEET_BERRIES, RebuildFoods.SWEET_BERRIES);
        ReplaceHelper.foodAdd(Items.SWEET_BERRIES, IFWFoods.sweet_berries);
        ReplaceHelper.foodOverride(Items.HONEY_BOTTLE, RebuildFoods.HONEY_BOTTLE);
        ReplaceHelper.foodAdd(Items.HONEY_BOTTLE, IFWFoods.honey_bottle);
        ReplaceHelper.foodOverride(Items.POTATO, RebuildFoods.POTATO);
        ReplaceHelper.foodOverride(Items.GOLDEN_APPLE, RebuildFoods.GOLDEN_APPLE);
        ReplaceHelper.foodAdd(Items.GOLDEN_APPLE, IFWFoods.golden_apple);
        ReplaceHelper.foodOverride(Items.GLOW_BERRIES, RebuildFoods.GLOW_BERRIES);
        ReplaceHelper.foodOverride(Items.WHEAT_SEEDS, IFWFoods.wheat_seeds);
        ReplaceHelper.foodAdd(Items.MILK_BUCKET, IFWFoods.ifw_milk_bucket);
        ReplaceHelper.foodOverride(Items.MILK_BUCKET, IFWFoods.milk_bucket);
        ReplaceHelper.foodOverride(Items.BROWN_MUSHROOM, IFWFoods.brown_mushroom);
        ReplaceHelper.foodOverride(Items.RED_MUSHROOM, IFWFoods.red_mushroom);
        ReplaceHelper.foodOverride(Items.PUMPKIN_SEEDS, IFWFoods.pumpkin_seed);
        ReplaceHelper.foodAdd(Items.SUGAR, IFWFoods.ifw_sugar);
        ReplaceHelper.foodOverride(Items.SUGAR, IFWFoods.sugar);
        ReplaceHelper.foodOverride(Items.EGG, IFWFoods.egg);
        ReplaceHelper.foodAdd(Items.EGG, IFWFoods.ifw_egg);
        ReplaceHelper.foodOverride(Items.WHEAT_SEEDS, IFWFoods.wheat_seeds);
        ReplaceHelper.foodAdd(Items.MILK_BUCKET, IFWFoods.ifw_milk_bucket);
        ReplaceHelper.foodOverride(Items.WHEAT_SEEDS, IFWFoods.wheat_seeds);
        ReplaceHelper.foodAdd(Items.MILK_BUCKET, IFWFoods.ifw_milk_bucket);
        ReplaceHelper.foodOverride(Items.NETHER_WART, IFWFoods.nether_wart);
        ReplaceHelper.foodOverride(Items.MELON_SEEDS, IFWFoods.melon_seed);
        ReplaceHelper.foodOverride(Items.BEETROOT_SEEDS, IFWFoods.beetroot_seeds);
        ReplaceHelper.foodAdd(IFWItems.salad.get(), IFWFoods.ifw_salad);
        ReplaceHelper.foodAdd(IFWItems.milk_bowl.get(), IFWFoods.ifw_milk_bowl);
        ReplaceHelper.foodAdd(IFWItems.water_bowl.get(), IFWFoods.ifw_bowl_water);
        ReplaceHelper.foodAdd(IFWItems.cheese.get(), IFWFoods.ifw_cheese);
        ReplaceHelper.foodAdd(IFWItems.chocolate.get(), IFWFoods.ifw_chocolate);
        ReplaceHelper.foodAdd(IFWItems.cereal.get(), IFWFoods.ifw_cereal);
        ReplaceHelper.foodAdd(IFWItems.pumpkin_soup.get(), IFWFoods.ifw_pumpkin_soup);
        ReplaceHelper.foodAdd(IFWItems.mushroom_soup_cream.get(), IFWFoods.ifw_mushroom_soup_cream);
        ReplaceHelper.foodAdd(IFWItems.vegetable_soup.get(), IFWFoods.ifw_vegetable_soup);
        ReplaceHelper.foodAdd(IFWItems.vegetable_soup_cream.get(), IFWFoods.ifw_vegetable_soup_cream);
        ReplaceHelper.foodAdd(IFWItems.chicken_soup.get(), IFWFoods.ifw_chicken_soup);
        ReplaceHelper.foodAdd(IFWItems.beef_stew.get(), IFWFoods.ifw_beef_stew);
        ReplaceHelper.foodAdd(IFWItems.porridge.get(), IFWFoods.ifw_porridge);
        ReplaceHelper.foodAdd(IFWItems.sorbet.get(), IFWFoods.ifw_sorbet);
        ReplaceHelper.foodAdd(IFWItems.mashed_potato.get(), IFWFoods.ifw_mashed_potato);
        ReplaceHelper.foodAdd(IFWItems.ice_cream.get(), IFWFoods.ifw_ice_cream);
        ReplaceHelper.foodAdd(IFWItems.orange.get(), IFWFoods.ifw_orange);
        ReplaceHelper.foodAdd(IFWItems.banana.get(), IFWFoods.ifw_banana);
        ReplaceHelper.foodAdd(IFWItems.blueberry.get(), IFWFoods.ifw_blueberry);
        ReplaceHelper.foodAdd(IFWItems.cooked_worm.get(), IFWFoods.ifw_cooked_worm);
        ReplaceHelper.foodAdd(IFWItems.worm.get(), IFWFoods.ifw_worm);
        ReplaceHelper.foodAdd(IFWItems.onion.get(), IFWFoods.ifw_onion);
        ReplaceHelper.foodOverride(Items.CHORUS_FRUIT, RebuildFoods.CHORUS_FRUIT);
        ReplaceHelper.foodOverride(Items.COOKED_CHICKEN, RebuildFoods.COOKED_CHICKEN);
        ReplaceHelper.foodOverride(Items.COD, RebuildFoods.COD);
        ReplaceHelper.foodOverride(Items.COOKED_COD, RebuildFoods.COOKED_COD);
        ReplaceHelper.foodOverride(Items.SALMON, RebuildFoods.SALMON);
        ReplaceHelper.foodOverride(Items.TROPICAL_FISH, RebuildFoods.TROPICAL_FISH);
        ReplaceHelper.foodOverride(Items.OMINOUS_BOTTLE, RebuildFoods.OMINOUS_BOTTLE);
    }

    @SubscribeEvent
    public static void registerSpawnPlacementsEvent(RegisterSpawnPlacementsEvent event) {
        event.register(IFWEntityType.CHICKEN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Livestock::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(IFWEntityType.SHEEP.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Livestock::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(IFWEntityType.PIG.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Livestock::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(IFWEntityType.COW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Livestock::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
