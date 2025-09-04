package huix.infinity.datagen.tag;

import fuzs.swordblockingmechanics.init.ModRegistry;
import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class IFWItemTagsProvider extends ItemTagsProvider {
    public IFWItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, "vanilla", existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.@NotNull Provider provider) {
        tag(ItemTags.PICKAXES).add(IFWItems.adamantium_pickaxe.get(), IFWItems.mithril_pickaxe.get(), IFWItems.ancient_metal_pickaxe.get(), IFWItems.copper_pickaxe.get()
                , IFWItems.iron_pickaxe.get(), IFWItems.golden_pickaxe.get(), IFWItems.silver_pickaxe.get(), IFWItems.rusted_iron_pickaxe.get());
        tag(ItemTags.AXES).add(IFWItems.adamantium_axe.get(), IFWItems.mithril_axe.get(), IFWItems.ancient_metal_axe.get(),
                IFWItems.iron_axe.get(), IFWItems.copper_axe.get(), IFWItems.golden_axe.get(), IFWItems.silver_axe.get(), IFWItems.rusted_iron_axe.get());
        tag(ItemTags.SWORDS).add(IFWItems.adamantium_sword.get(), IFWItems.mithril_sword.get(), IFWItems.ancient_metal_sword.get(),
                IFWItems.iron_sword.get(), IFWItems.copper_sword.get(), IFWItems.golden_sword.get(), IFWItems.silver_sword.get(), IFWItems.rusted_iron_sword.get());
        tag(ItemTags.HOES).add(IFWItems.adamantium_hoe.get(), IFWItems.mithril_hoe.get(), IFWItems.ancient_metal_hoe.get(),
                IFWItems.iron_hoe.get(), IFWItems.copper_hoe.get(), IFWItems.golden_hoe.get(), IFWItems.silver_hoe.get(), IFWItems.rusted_iron_hoe.get());
        tag(ItemTags.SHOVELS).add(IFWItems.adamantium_shovel.get(), IFWItems.mithril_shovel.get(), IFWItems.ancient_metal_shovel.get(),
                IFWItems.iron_shovel.get(), IFWItems.copper_shovel.get(), IFWItems.golden_shovel.get(), IFWItems.silver_shovel.get(), IFWItems.rusted_iron_shovel.get());

        tag(IFWItemTags.BATTLE_AXE).add(IFWItems.adamantium_battle_axe.get(), IFWItems.mithril_battle_axe.get(), IFWItems.ancient_metal_battle_axe.get(),
                IFWItems.iron_battle_axe.get(), IFWItems.copper_battle_axe.get(), IFWItems.golden_battle_axe.get(), IFWItems.silver_battle_axe.get(), IFWItems.rusted_iron_battle_axe.get());
        tag(IFWItemTags.DAGGER).add(IFWItems.adamantium_dagger.get(), IFWItems.mithril_dagger.get(), IFWItems.ancient_metal_dagger.get(),
                IFWItems.iron_dagger.get(), IFWItems.copper_dagger.get(), IFWItems.golden_dagger.get(), IFWItems.silver_dagger.get(), IFWItems.rusted_iron_dagger.get());
        tag(IFWItemTags.MATTOCK).add(IFWItems.adamantium_mattock.get(), IFWItems.mithril_mattock.get(), IFWItems.ancient_metal_mattock.get(),
                IFWItems.iron_mattock.get(), IFWItems.copper_mattock.get(), IFWItems.golden_mattock.get(), IFWItems.silver_mattock.get(), IFWItems.rusted_iron_mattock.get());
        tag(IFWItemTags.SCYTHE).add(IFWItems.adamantium_scythe.get(), IFWItems.mithril_scythe.get(), IFWItems.ancient_metal_scythe.get(),
                IFWItems.iron_scythe.get(), IFWItems.copper_scythe.get(), IFWItems.golden_scythe.get(), IFWItems.silver_scythe.get(), IFWItems.rusted_iron_scythe.get());
        tag(Tags.Items.TOOLS_SHEAR).add(IFWItems.adamantium_shears.get(), IFWItems.mithril_shears.get(), IFWItems.ancient_metal_shears.get(),
                IFWItems.iron_shears.get(), IFWItems.copper_shears.get(), IFWItems.golden_shears.get(), IFWItems.silver_shears.get(), IFWItems.rusted_iron_shears.get());
        tag(IFWItemTags.WAR_HAMMER).add(IFWItems.adamantium_war_hammer.get(), IFWItems.mithril_war_hammer.get(), IFWItems.ancient_metal_war_hammer.get(),
                IFWItems.iron_war_hammer.get(), IFWItems.copper_war_hammer.get(), IFWItems.golden_war_hammer.get(), IFWItems.silver_war_hammer.get(), IFWItems.rusted_iron_war_hammer.get());

        tag(Tags.Items.MELEE_WEAPON_TOOLS).addTags(IFWItemTags.BATTLE_AXE, IFWItemTags.DAGGER, IFWItemTags.SCYTHE, IFWItemTags.WAR_HAMMER).add(IFWItems.wooden_club.get());
        tag(Tags.Items.TOOLS).addTags(IFWItemTags.MATTOCK);

        tag(IFWItemTags.NO_MINING_TOOLS).addTags(ItemTags.SWORDS, IFWItemTags.DAGGER, IFWItemTags.SCYTHE).add(IFWItems.wooden_club.get());

        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(IFWItems.copper_helmet.get(), IFWItems.copper_chainmail_helmet.get(), IFWItems.silver_helmet.get(),
                IFWItems.silver_chainmail_helmet.get(), IFWItems.golden_chainmail_helmet.get(), IFWItems.ancient_metal_helmet.get(), IFWItems.ancient_metal_chainmail_helmet.get()
                , IFWItems.mithril_helmet.get(), IFWItems.mithril_chainmail_helmet.get(), IFWItems.adamantium_helmet.get(), IFWItems.adamantium_chainmail_helmet.get());
        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(IFWItems.copper_chestplate.get(), IFWItems.copper_chainmail_chestplate.get(), IFWItems.silver_chestplate.get(),
                IFWItems.silver_chainmail_chestplate.get(), IFWItems.golden_chainmail_chestplate.get(), IFWItems.ancient_metal_chestplate.get(), IFWItems.ancient_metal_chainmail_chestplate.get()
                , IFWItems.mithril_chestplate.get(), IFWItems.mithril_chainmail_chestplate.get(), IFWItems.adamantium_chestplate.get(), IFWItems.adamantium_chainmail_chestplate.get());
        tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(IFWItems.copper_leggings.get(), IFWItems.copper_chainmail_leggings.get(), IFWItems.silver_leggings.get(),
                IFWItems.silver_chainmail_leggings.get(), IFWItems.golden_chainmail_leggings.get(), IFWItems.ancient_metal_leggings.get(), IFWItems.ancient_metal_chainmail_leggings.get()
                , IFWItems.mithril_leggings.get(), IFWItems.mithril_chainmail_leggings.get(), IFWItems.adamantium_leggings.get(), IFWItems.adamantium_chainmail_leggings.get());
        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(IFWItems.copper_boots.get(), IFWItems.copper_chainmail_boots.get(), IFWItems.silver_boots.get(),
                IFWItems.silver_chainmail_boots.get(), IFWItems.golden_chainmail_boots.get(), IFWItems.ancient_metal_boots.get(), IFWItems.ancient_metal_chainmail_boots.get()
                , IFWItems.mithril_boots.get(), IFWItems.mithril_chainmail_boots.get(), IFWItems.adamantium_boots.get(), IFWItems.adamantium_chainmail_boots.get());

        tag(ModRegistry.CAN_PERFORM_SWORD_BLOCKING_ITEM_TAG).addTags(ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, IFWItemTags.BATTLE_AXE, IFWItemTags.WAR_HAMMER);

        tag(ItemTags.FISHING_ENCHANTABLE).add(IFWItems.copper_fishing_rod.get());
        tag(ItemTags.DURABILITY_ENCHANTABLE).add(IFWItems.copper_fishing_rod.get());

        tag(Tags.Items.STRINGS).add(IFWItems.sinew.get());
        copy(BlockTags.ANVIL, IFWItemTags.ANVIL);

        tag(IFWItemTags.BUCKETS_TROPICAL_FISH).add(IFWItems.tropical_copper_bucket.get(), IFWItems.tropical_silver_bucket.get(), IFWItems.tropical_gold_bucket.get(),
                IFWItems.tropical_iron_bucket.get(), IFWItems.tropical_ancient_metal_bucket.get(), IFWItems.tropical_mithril_bucket.get(), IFWItems.tropical_adamantium_bucket.get());
        tag(Tags.Items.BUCKETS_EMPTY).add(IFWItems.copper_bucket.get(), IFWItems.silver_bucket.get(),
                IFWItems.gold_bucket.get(), IFWItems.ancient_metal_bucket.get(), IFWItems.mithril_bucket.get(), IFWItems.adamantium_bucket.get());
        tag(Tags.Items.BUCKETS_LAVA).add(IFWItems.lava_copper_bucket.get(), IFWItems.lava_silver_bucket.get(),
                IFWItems.lava_gold_bucket.get(), IFWItems.lava_ancient_metal_bucket.get(), IFWItems.lava_mithril_bucket.get(), IFWItems.lava_adamantium_bucket.get());
        tag(Tags.Items.BUCKETS_POWDER_SNOW).add(IFWItems.powder_snow_copper_bucket.get(), IFWItems.powder_snow_silver_bucket.get(),
                IFWItems.powder_snow_gold_bucket.get(), IFWItems.powder_snow_ancient_metal_bucket.get(), IFWItems.powder_snow_mithril_bucket.get(), IFWItems.powder_snow_adamantium_bucket.get());
        tag(Tags.Items.BUCKETS_WATER).add(IFWItems.water_copper_bucket.get(), IFWItems.water_silver_bucket.get(),
                IFWItems.water_gold_bucket.get(), IFWItems.water_ancient_metal_bucket.get(), IFWItems.water_mithril_bucket.get(), IFWItems.water_adamantium_bucket.get());
        tag(Tags.Items.BUCKETS_MILK).add(IFWItems.milk_copper_bucket.get(), IFWItems.milk_silver_bucket.get(),
                IFWItems.milk_gold_bucket.get(), IFWItems.milk_ancient_metal_bucket.get(), IFWItems.milk_mithril_bucket.get(), IFWItems.milk_adamantium_bucket.get());
        tag(IFWItemTags.BUCKETS_STONE).add(IFWItems.stone_copper_bucket.get(), IFWItems.stone_iron_bucket.get(), IFWItems.stone_silver_bucket.get(),
                IFWItems.stone_gold_bucket.get(), IFWItems.stone_ancient_metal_bucket.get(), IFWItems.stone_mithril_bucket.get(), IFWItems.stone_adamantium_bucket.get());
        tag(Tags.Items.BUCKETS_ENTITY_WATER).add(IFWItems.pufferfish_copper_bucket.get(), IFWItems.salmon_copper_bucket.get(), IFWItems.cod_copper_bucket.get()
                , IFWItems.axolotl_copper_bucket.get(), IFWItems.tadpole_copper_bucket.get(), IFWItems.pufferfish_iron_bucket.get(), IFWItems.salmon_iron_bucket.get(), IFWItems.cod_iron_bucket.get()
                , IFWItems.axolotl_iron_bucket.get(), IFWItems.tadpole_iron_bucket.get()).addTag(IFWItemTags.BUCKETS_TROPICAL_FISH).add(IFWItems.pufferfish_silver_bucket.get(), IFWItems.salmon_silver_bucket.get(), IFWItems.cod_silver_bucket.get()
                , IFWItems.axolotl_silver_bucket.get(), IFWItems.pufferfish_gold_bucket.get(), IFWItems.salmon_gold_bucket.get(), IFWItems.cod_gold_bucket.get()
                , IFWItems.axolotl_gold_bucket.get(), IFWItems.pufferfish_ancient_metal_bucket.get(), IFWItems.salmon_ancient_metal_bucket.get(), IFWItems.cod_ancient_metal_bucket.get()
                , IFWItems.axolotl_ancient_metal_bucket.get(), IFWItems.pufferfish_mithril_bucket.get(), IFWItems.salmon_mithril_bucket.get(), IFWItems.cod_mithril_bucket.get()
                , IFWItems.axolotl_mithril_bucket.get(), IFWItems.pufferfish_adamantium_bucket.get(), IFWItems.salmon_adamantium_bucket.get(), IFWItems.cod_adamantium_bucket.get()
                , IFWItems.axolotl_adamantium_bucket.get());
        tag(IFWItemTags.HAS_ENCHANTING_RECIPE).add(Items.GOLDEN_APPLE);
        tag(IFWItemTags.HAS_ENCHANTING_RECIPE).add(Items.POTION);

        tag(IFWItemTags.SILVER_ITEM).add(
                IFWItems.silver_sword.get(), IFWItems.silver_axe.get(), IFWItems.silver_pickaxe.get(), IFWItems.silver_shovel.get(), IFWItems.silver_hoe.get(),
                IFWItems.silver_battle_axe.get(), IFWItems.silver_dagger.get(), IFWItems.silver_mattock.get(), IFWItems.silver_scythe.get(),
                IFWItems.silver_shears.get(), IFWItems.silver_war_hammer.get(),
                IFWItems.silver_helmet.get(), IFWItems.silver_chestplate.get(), IFWItems.silver_leggings.get(), IFWItems.silver_boots.get(),
                IFWItems.silver_chainmail_helmet.get(), IFWItems.silver_chainmail_chestplate.get(), IFWItems.silver_chainmail_leggings.get(), IFWItems.silver_chainmail_boots.get(),
                IFWItems.silver_ingot.get(), IFWItems.silver_nugget.get(),
                IFWItems.silver_fishing_rod.get(),
                IFWItems.silver_bucket.get(), IFWItems.water_silver_bucket.get(), IFWItems.lava_silver_bucket.get(), IFWItems.milk_silver_bucket.get(),
                IFWItems.powder_snow_silver_bucket.get(), IFWItems.tropical_silver_bucket.get(), IFWItems.stone_silver_bucket.get(), IFWItems.pufferfish_silver_bucket.get(),
                IFWItems.salmon_silver_bucket.get(), IFWItems.cod_silver_bucket.get(), IFWItems.axolotl_silver_bucket.get()
        );

        tag(IFWItemTags.ACID_IMMUNE)
                .add(Items.LAPIS_LAZULI, Items.EMERALD, Items.DIAMOND, Items.REDSTONE, Items.QUARTZ, Items.AMETHYST_SHARD)
                .add(IFWItems.gold_bucket.get(), IFWItems.golden_chain.get(), Items.GOLD_INGOT, Items.GOLD_NUGGET)
                .add(IFWItems.mithril_bucket.get(), IFWItems.mithril_chain.get(), IFWItems.mithril_ingot.get(), IFWItems.mithril_nugget.get())
                .add(IFWItems.adamantium_bucket.get(), IFWItems.adamantium_chain.get(), IFWItems.adamantium_ingot.get(), IFWItems.adamantium_nugget.get())
                .add(Items.NETHERITE_INGOT, Items.NETHERITE_SCRAP);

        tag(IFWItemTags.ANIMAL_PRODUCTS).add(
                // Vanilla
                Items.BEEF, Items.COOKED_BEEF, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.CHICKEN, Items.COOKED_CHICKEN,Items.RABBIT_STEW,
                Items.MUTTON, Items.COOKED_MUTTON, Items.RABBIT, Items.COOKED_RABBIT, Items.COD, Items.COOKED_COD,Items.EGG,
                Items.SALMON, Items.COOKED_SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.ROTTEN_FLESH,
                Items.MILK_BUCKET, Items.HONEY_BOTTLE, Items.PUMPKIN_PIE, Items.SPIDER_EYE,
                // IFW
                IFWItems.cheese.get(), IFWItems.cooked_worm.get(), IFWItems.worm.get(), IFWItems.milk_bowl.get(), IFWItems.milk_copper_bucket.get(), IFWItems.milk_silver_bucket.get(),
                IFWItems.milk_gold_bucket.get(),IFWItems.milk_ancient_metal_bucket.get(), IFWItems.milk_mithril_bucket.get(), IFWItems.milk_adamantium_bucket.get(), IFWItems.cereal.get(),
                IFWItems.chicken_soup.get(),IFWItems.beef_stew.get(), IFWItems.mushroom_soup_cream.get(), IFWItems.vegetable_soup_cream.get(), IFWItems.mashed_potato.get(),
                IFWItems.horse_meat.get(),IFWItems.cooked_horse_meat.get()
        );

        tag(IFWItemTags.VEGETABLES).add(
                // Vanilla
                Items.CARROT, Items.POTATO, Items.BAKED_POTATO, Items.BEETROOT, Items.BEETROOT_SOUP, Items.WHEAT, Items.APPLE, Items.BREAD, Items.SUGAR,
                Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.GLOW_BERRIES, Items.SWEET_BERRIES, Items.MELON_SLICE, Items.CHORUS_FRUIT,
                Items.PUMPKIN, Items.PUMPKIN_PIE, Items.BROWN_MUSHROOM, Items.RED_MUSHROOM, Items.NETHER_WART, Items.POISONOUS_POTATO,
                Items.SUGAR_CANE, Items.COOKIE, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS,
                Items.GOLDEN_CARROT, Items.DRIED_KELP, Items.MUSHROOM_STEW, Items.RABBIT_STEW, Items.SUSPICIOUS_STEW,
                // IFW
                IFWItems.salad.get(), IFWItems.pumpkin_soup.get(), IFWItems.vegetable_soup.get(), IFWItems.vegetable_soup_cream.get(), IFWItems.chicken_soup.get(), IFWItems.beef_stew.get(),
                IFWItems.blueberry_porridge.get(), IFWItems.sorbet.get(), IFWItems.orange.get(), IFWItems.banana.get(), IFWItems.blueberry.get(), IFWItems.onion.get(), IFWItems.cereal.get(),
                IFWItems.mushroom_soup_cream.get(), IFWItems.mashed_potato.get(), IFWItems.ice_cream.get(), IFWItems.chocolate.get(), IFWItems.dough.get()
                );
    }
}
