package huix.infinity.init;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.food.IFWFoods;
import huix.infinity.common.world.food.RebuildFoods;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.util.ReplaceHelper;
import huix.infinity.util.StackSizeHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IFWLoad {

    @SubscribeEvent
    public static void injectItem(final FMLLoadCompleteEvent event) {
        injectCookingLevel();
        rebuildFood();

    }



    private static void injectCookingLevel() {
        ReplaceHelper.itemCookingLevel(Items.TORCH, 1);
        ReplaceHelper.itemCookingLevel(Items.COAL, 2);
        ReplaceHelper.itemCookingLevel(Items.BLAZE_ROD, 4);
        ReplaceHelper.itemCookingLevel(Items.LAVA_BUCKET, 3);
        ReplaceHelper.itemCookingLevel(Items.COAL_BLOCK, 2);
        ReplaceHelper.itemCookingLevel(Items.CHARCOAL, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.LOGS, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.BAMBOO_BLOCKS, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.PLANKS, 1);
        ReplaceHelper.itemCookingLevel(Items.BAMBOO_MOSAIC, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOODEN_STAIRS, 1);
        ReplaceHelper.itemCookingLevel(Items.BAMBOO_MOSAIC_STAIRS, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOODEN_SLABS, 1);
        ReplaceHelper.itemCookingLevel(Items.BAMBOO_MOSAIC_SLAB, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOODEN_TRAPDOORS, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOODEN_PRESSURE_PLATES, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOODEN_FENCES, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.FENCE_GATES, 1);
        ReplaceHelper.itemCookingLevel(Items.NOTE_BLOCK, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOODEN_SLABS, 1);
        ReplaceHelper.itemCookingLevel(Items.BOOKSHELF, 1);
        ReplaceHelper.itemCookingLevel(Items.CHISELED_BOOKSHELF, 1);
        ReplaceHelper.itemCookingLevel(Items.LECTERN, 1);
        ReplaceHelper.itemCookingLevel(Items.JUKEBOX, 1);
        ReplaceHelper.itemCookingLevel(Items.CHEST, 1);
        ReplaceHelper.itemCookingLevel(Items.TRAPPED_CHEST, 1);
        ReplaceHelper.itemCookingLevel(Items.CRAFTING_TABLE, 1);
        ReplaceHelper.itemCookingLevel(Items.DAYLIGHT_DETECTOR, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.BANNERS, 1);
        ReplaceHelper.itemCookingLevel(Items.BOW, 1);
        ReplaceHelper.itemCookingLevel(Items.FISHING_ROD, 1);
        ReplaceHelper.itemCookingLevel(Items.LADDER, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.SIGNS, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.HANGING_SIGNS, 1);
        ReplaceHelper.itemCookingLevel(IFWItems.wooden_cudgel.get(), 1);
        ReplaceHelper.itemCookingLevel(IFWItems.wooden_shovel.get(), 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOODEN_DOORS, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.BOATS, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOOL, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOODEN_BUTTONS, 1);
        ReplaceHelper.itemCookingLevel(Items.STICK, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.SAPLINGS, 1);
        ReplaceHelper.itemCookingLevel(Items.BOWL, 1);
        ReplaceHelper.itemCookingLevel(ItemTags.WOOL_CARPETS, 1);
        ReplaceHelper.itemCookingLevel(Items.DRIED_KELP_BLOCK, 1);
        ReplaceHelper.itemCookingLevel(Items.CROSSBOW, 1);
        ReplaceHelper.itemCookingLevel(Items.BAMBOO, 1);
        ReplaceHelper.itemCookingLevel(Items.DEAD_BUSH, 1);
        ReplaceHelper.itemCookingLevel(Items.SCAFFOLDING, 1);
        ReplaceHelper.itemCookingLevel(Items.LOOM, 1);
        ReplaceHelper.itemCookingLevel(Items.BARREL, 1);
        ReplaceHelper.itemCookingLevel(Items.CARTOGRAPHY_TABLE, 1);
        ReplaceHelper.itemCookingLevel(Items.FLETCHING_TABLE, 1);
        ReplaceHelper.itemCookingLevel(Items.SMITHING_TABLE, 1);
        ReplaceHelper.itemCookingLevel(Items.COMPOSTER, 1);
        ReplaceHelper.itemCookingLevel(Items.AZALEA, 1);
        ReplaceHelper.itemCookingLevel(Items.FLOWERING_AZALEA, 1);
        ReplaceHelper.itemCookingLevel(Items.MANGROVE_ROOTS, 1);

    }


    public static void rebuildStackSize() {
        ReplaceHelper.stackSize(IFWItemTags.string, StackSizeHelper.ingot);

        //misc
        ReplaceHelper.stackSize(Items.BOOK, 16);
        //ingot
        ReplaceHelper.stackSize(Tags.Items.INGOTS, StackSizeHelper.ingot);
//        ReplaceHelper.stackSize(Items.IRON_INGOT, StackSizeHelper.ingot);
//        ReplaceHelper.stackSize(Items.GOLD_INGOT, StackSizeHelper.ingot);
//        ReplaceHelper.stackSize(Items.COPPER_INGOT, StackSizeHelper.ingot);
//        ReplaceHelper.stackSize(Items.NETHERITE_INGOT, StackSizeHelper.ingot);
        //slab
        ReplaceHelper.stackSize(Items.END_STONE_BRICK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.SANDSTONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.SMOOTH_QUARTZ_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.ACACIA_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.ANDESITE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.SMOOTH_RED_SANDSTONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.SMOOTH_STONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.BAMBOO_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.SPRUCE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.BLACKSTONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.BRICK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.BAMBOO_MOSAIC_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.CHERRY_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.COBBLED_DEEPSLATE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.COBBLESTONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.CRIMSON_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.CUT_COPPER_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.CUT_RED_SANDSTONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.CUT_STANDSTONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.DARK_OAK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.DARK_PRISMARINE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.DEEPSLATE_TILE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.DIORITE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.EXPOSED_CUT_COPPER_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.GRANITE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.JUNGLE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.MANGROVE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.MOSSY_COBBLESTONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.MOSSY_STONE_BRICK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.MUD_BRICK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.NETHER_BRICK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.OAK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.OXIDIZED_CUT_COPPER_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.PETRIFIED_OAK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.POLISHED_BLACKSTONE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.POLISHED_DEEPSLATE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.POLISHED_DIORITE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.POLISHED_GRANITE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.POLISHED_TUFF_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.PRISMARINE_BRICK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.PRISMARINE_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.TUFF_BRICK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.TUFF_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.WARPED_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.WAXED_CUT_COPPER_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.WAXED_EXPOSED_CUT_COPPER_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.WAXED_WEATHERED_CUT_COPPER_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.WEATHERED_CUT_COPPER_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.CRACKED_DEEPSLATE_BRICKS, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.DEEPSLATE_BRICK_SLAB, StackSizeHelper.slab);
        ReplaceHelper.stackSize(Items.STONE_SLAB, StackSizeHelper.slab);
        //glass_pane
        ReplaceHelper.stackSize(Items.LIME_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.BLACK_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.BLUE_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.PINK_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.BROWN_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.PURPLE_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.CYAN_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.GRAY_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.GREEN_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.LIGHT_BLUE_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.LIGHT_GRAY_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.MAGENTA_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.ORANGE_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.RED_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.WHITE_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.YELLOW_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        ReplaceHelper.stackSize(Items.BROWN_STAINED_GLASS_PANE, StackSizeHelper.glass_pane);
        //normal Block
        ReplaceHelper.stackSize(Items.STONE, StackSizeHelper.normal);
        ReplaceHelper.stackSize(Items.GRANITE, StackSizeHelper.normal);
        ReplaceHelper.stackSize(Items.GRAVEL, StackSizeHelper.normal);
        ReplaceHelper.stackSize(Items.POLISHED_GRANITE, StackSizeHelper.normal);
        ReplaceHelper.stackSize(Items.DIORITE, StackSizeHelper.normal);
        ReplaceHelper.stackSize(Items.POLISHED_DIORITE, StackSizeHelper.normal);
        ReplaceHelper.stackSize(Items.BOOKSHELF, StackSizeHelper.normal);
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
        ReplaceHelper.foodAdd(Items.COOKED_SALMON, IFWFoods.salmon);
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
        ReplaceHelper.foodAdd(Items.COOKED_CHICKEN, IFWFoods.chicken);
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
        ReplaceHelper.foodAdd(IFWItems.cooked_worm.get(), IFWFoods.ifw_cooked_worm);
        ReplaceHelper.foodAdd(IFWItems.worm.get(), IFWFoods.ifw_worm);
    }
}
