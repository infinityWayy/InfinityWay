package huix.infinity.datagen.model;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.InfinityWay;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IFWItemModelProvider extends ItemModelProvider {
    public IFWItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, InfinityWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("obsidian_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/obsidian_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("flint_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/flint_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("copper_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/copper_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("silver_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/silver_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("copper_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/copper_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("silver_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/silver_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("gold_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/gold_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("iron_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/iron_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("ancient_metal_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/ancient_metal_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("mithril_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/mithril_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();
        withExistingParent("adamantium_fishing_rod", "handheld_rod")
                .texture("layer0", modLoc("item/adamantium_fishing_rod"))
                .override()
                .predicate(mcLoc("cast"), 1)
                .model(getExistingFile(mcLoc("item/fishing_rod_cast")))
                .end();


        simpleBlockItem(IFWBlocks.silver_block.get());
        simpleBlockItem(IFWBlocks.silver_ore.get());
        simpleBlockItem(IFWBlocks.raw_adamantium_block.get());
        simpleBlockItem(IFWBlocks.raw_mithril_block.get());
        simpleBlockItem(IFWBlocks.raw_silver_block.get());
        simpleBlockItem(IFWBlocks.deepslate_adamantium_ore.get());
        simpleBlockItem(IFWBlocks.deepslate_mithril_ore.get());
        simpleBlockItem(IFWBlocks.deepslate_silver_ore.get());
        simpleBlockItem(IFWBlocks.mithril_block.get());
        simpleBlockItem(IFWBlocks.mithril_ore.get());
        simpleBlockItem(IFWBlocks.ancient_metal_block.get());
        simpleBlockItem(IFWBlocks.adamantium_ore.get());
        simpleBlockItem(IFWBlocks.adamantium_block.get());
        basicItem(IFWItems.flint_shard.get());
        basicItem(IFWItems.diamond_shard.get());
        basicItem(IFWItems.emerald_shard.get());
        basicItem(IFWItems.obsidian_shard.get());
        basicItem(IFWItems.quartz_shard.get());
        basicItem(IFWItems.glass_shard.get());
        basicItem(IFWItems.sinew.get());
        basicItem(IFWBlocks.adamantium_door_item.get());
        basicItem(IFWBlocks.mithril_door_item.get());
        basicItem(IFWBlocks.ancient_metal_door_item.get());
        basicItem(IFWBlocks.silver_door_item.get());
        basicItem(IFWBlocks.gold_door_item.get());
        basicItem(IFWItems.adamantium_ingot.get());
        basicItem(IFWItems.adamantium_nugget.get());
        basicItem(IFWItems.ancient_metal_ingot.get());
        basicItem(IFWItems.ancient_metal_nugget.get());
        basicItem(IFWItems.mithril_ingot.get());
        basicItem(IFWItems.mithril_nugget.get());
        basicItem(IFWItems.copper_nugget.get());
        basicItem(IFWItems.silver_ingot.get());
        basicItem(IFWItems.silver_nugget.get());
        basicItem(IFWItems.raw_adamantium.get());
        basicItem(IFWItems.raw_mithril.get());
        basicItem(IFWItems.raw_silver.get());
        handheldItem(IFWItems.flint_axe.get());
        handheldItem(IFWItems.flint_shovel.get());
        handheldItem(IFWItems.flint_knife.get());
        handheldItem(IFWItems.adamantium_hoe.get());
        handheldItem(IFWItems.flint_hatchet.get());
        handheldItem(IFWItems.wooden_club.get());
        handheldItem(IFWItems.wooden_shovel.get());
        handheldItem(IFWItems.adamantium_pickaxe.get());
        handheldItem(IFWItems.adamantium_shears.get());
        handheldItem(IFWItems.adamantium_shovel.get());
        handheldItem(IFWItems.adamantium_hoe.get());
        handheldItem(IFWItems.adamantium_sword.get());
        handheldItem(IFWItems.adamantium_axe.get());
        handheldItem(IFWItems.adamantium_scythe.get());
        handheldItem(IFWItems.adamantium_mattock.get());
        handheldItem(IFWItems.adamantium_battle_axe.get());
        handheldItem(IFWItems.adamantium_war_hammer.get());
        handheldItem(IFWItems.adamantium_dagger.get());
        handheldItem(IFWItems.ancient_metal_pickaxe.get());
        handheldItem(IFWItems.ancient_metal_shears.get());
        handheldItem(IFWItems.ancient_metal_shovel.get());
        handheldItem(IFWItems.ancient_metal_hoe.get());
        handheldItem(IFWItems.ancient_metal_sword.get());
        handheldItem(IFWItems.ancient_metal_axe.get());
        handheldItem(IFWItems.ancient_metal_scythe.get());
        handheldItem(IFWItems.ancient_metal_mattock.get());
        handheldItem(IFWItems.ancient_metal_battle_axe.get());
        handheldItem(IFWItems.ancient_metal_war_hammer.get());
        handheldItem(IFWItems.ancient_metal_dagger.get());
        handheldItem(IFWItems.mithril_pickaxe.get());
        handheldItem(IFWItems.mithril_shears.get());
        handheldItem(IFWItems.mithril_shovel.get());
        handheldItem(IFWItems.mithril_hoe.get());
        handheldItem(IFWItems.mithril_sword.get());
        handheldItem(IFWItems.mithril_axe.get());
        handheldItem(IFWItems.mithril_scythe.get());
        handheldItem(IFWItems.mithril_mattock.get());
        handheldItem(IFWItems.mithril_battle_axe.get());
        handheldItem(IFWItems.mithril_war_hammer.get());
        handheldItem(IFWItems.mithril_dagger.get());
        handheldItem(IFWItems.silver_pickaxe.get());
        handheldItem(IFWItems.silver_shears.get());
        handheldItem(IFWItems.silver_shovel.get());
        handheldItem(IFWItems.silver_hoe.get());
        handheldItem(IFWItems.silver_sword.get());
        handheldItem(IFWItems.silver_axe.get());
        handheldItem(IFWItems.silver_scythe.get());
        handheldItem(IFWItems.silver_mattock.get());
        handheldItem(IFWItems.silver_battle_axe.get());
        handheldItem(IFWItems.silver_war_hammer.get());
        handheldItem(IFWItems.silver_dagger.get());
        handheldItem(IFWItems.copper_pickaxe.get());
        handheldItem(IFWItems.copper_shears.get());
        handheldItem(IFWItems.copper_shovel.get());
        handheldItem(IFWItems.copper_hoe.get());
        handheldItem(IFWItems.copper_sword.get());
        handheldItem(IFWItems.copper_axe.get());
        handheldItem(IFWItems.copper_scythe.get());
        handheldItem(IFWItems.copper_mattock.get());
        handheldItem(IFWItems.copper_battle_axe.get());
        handheldItem(IFWItems.copper_war_hammer.get());
        handheldItem(IFWItems.copper_dagger.get());
        handheldItem(IFWItems.iron_shears.get());
        handheldItem(IFWItems.iron_shovel.get());
        handheldItem(IFWItems.iron_hoe.get());
        handheldItem(IFWItems.iron_pickaxe.get());
        handheldItem(IFWItems.iron_sword.get());
        handheldItem(IFWItems.iron_axe.get());
        handheldItem(IFWItems.iron_scythe.get());
        handheldItem(IFWItems.iron_mattock.get());
        handheldItem(IFWItems.iron_battle_axe.get());
        handheldItem(IFWItems.iron_war_hammer.get());
        handheldItem(IFWItems.iron_dagger.get());
        handheldItem(IFWItems.rusted_iron_pickaxe.get());
        handheldItem(IFWItems.rusted_iron_shears.get());
        handheldItem(IFWItems.rusted_iron_shovel.get());
        handheldItem(IFWItems.rusted_iron_hoe.get());
        handheldItem(IFWItems.rusted_iron_sword.get());
        handheldItem(IFWItems.rusted_iron_axe.get());
        handheldItem(IFWItems.rusted_iron_scythe.get());
        handheldItem(IFWItems.rusted_iron_mattock.get());
        handheldItem(IFWItems.rusted_iron_battle_axe.get());
        handheldItem(IFWItems.rusted_iron_war_hammer.get());
        handheldItem(IFWItems.rusted_iron_dagger.get());
        handheldItem(IFWItems.golden_pickaxe.get());
        handheldItem(IFWItems.golden_shears.get());
        handheldItem(IFWItems.golden_shovel.get());
        handheldItem(IFWItems.golden_hoe.get());
        handheldItem(IFWItems.golden_sword.get());
        handheldItem(IFWItems.golden_axe.get());
        handheldItem(IFWItems.golden_scythe.get());
        handheldItem(IFWItems.golden_mattock.get());
        handheldItem(IFWItems.golden_battle_axe.get());
        handheldItem(IFWItems.golden_war_hammer.get());
        handheldItem(IFWItems.golden_dagger.get());
        //FOOD
        basicItem(IFWItems.salad.get());
        basicItem(IFWItems.milk_bowl.get());
        basicItem(IFWItems.water_bowl.get());
        basicItem(IFWItems.cheese.get());
        basicItem(IFWItems.dough.get());
        basicItem(IFWItems.chocolate.get());
        basicItem(IFWItems.cereal.get());
        basicItem(IFWItems.pumpkin_soup.get());
        basicItem(IFWItems.mushroom_soup_cream.get());
        basicItem(IFWItems.vegetable_soup.get());
        basicItem(IFWItems.vegetable_soup_cream.get());
        basicItem(IFWItems.chicken_soup.get());
        basicItem(IFWItems.beef_stew.get());
        basicItem(IFWItems.porridge.get());
        basicItem(IFWItems.sorbet.get());
        basicItem(IFWItems.mashed_potato.get());
        basicItem(IFWItems.ice_cream.get());
        basicItem(IFWItems.orange.get());
        basicItem(IFWItems.blueberry.get());
        basicItem(IFWItems.banana.get());
        basicItem(IFWItems.cooked_worm.get());
        basicItem(IFWItems.worm.get());
        basicItem(IFWItems.onion.get());
        basicItem(IFWItems.flour.get());
        basicItem(IFWItems.manure.get());
        basicItem(IFWItems.copper_bucket.get());
        basicItem(IFWItems.water_copper_bucket.get());
        basicItem(IFWItems.lava_copper_bucket.get());
        basicItem(IFWItems.powder_snow_copper_bucket.get());
        basicItem(IFWItems.milk_copper_bucket.get());
        basicItem(IFWItems.stone_copper_bucket.get());
        basicItem(IFWItems.pufferfish_copper_bucket.get());
        basicItem(IFWItems.salmon_copper_bucket.get());
        basicItem(IFWItems.cod_copper_bucket.get());
        basicItem(IFWItems.tropical_copper_bucket.get());
        basicItem(IFWItems.axolotl_copper_bucket.get());
        basicItem(IFWItems.tadpole_copper_bucket.get());
        basicItem(IFWItems.stone_iron_bucket.get());
        basicItem(IFWItems.pufferfish_iron_bucket.get());
        basicItem(IFWItems.salmon_iron_bucket.get());
        basicItem(IFWItems.cod_iron_bucket.get());
        basicItem(IFWItems.tropical_iron_bucket.get());
        basicItem(IFWItems.axolotl_iron_bucket.get());
        basicItem(IFWItems.tadpole_iron_bucket.get());
        basicItem(IFWItems.silver_bucket.get());
        basicItem(IFWItems.water_silver_bucket.get());
        basicItem(IFWItems.lava_silver_bucket.get());
        basicItem(IFWItems.powder_snow_silver_bucket.get());
        basicItem(IFWItems.milk_silver_bucket.get());
        basicItem(IFWItems.stone_silver_bucket.get());
        basicItem(IFWItems.pufferfish_silver_bucket.get());
        basicItem(IFWItems.salmon_silver_bucket.get());
        basicItem(IFWItems.cod_silver_bucket.get());
        basicItem(IFWItems.tropical_silver_bucket.get());
        basicItem(IFWItems.axolotl_silver_bucket.get());
        basicItem(IFWItems.tadpole_silver_bucket.get());
        basicItem(IFWItems.gold_bucket.get());
        basicItem(IFWItems.water_gold_bucket.get());
        basicItem(IFWItems.lava_gold_bucket.get());
        basicItem(IFWItems.powder_snow_gold_bucket.get());
        basicItem(IFWItems.milk_gold_bucket.get());
        basicItem(IFWItems.stone_gold_bucket.get());
        basicItem(IFWItems.pufferfish_gold_bucket.get());
        basicItem(IFWItems.salmon_gold_bucket.get());
        basicItem(IFWItems.cod_gold_bucket.get());
        basicItem(IFWItems.tropical_gold_bucket.get());
        basicItem(IFWItems.axolotl_gold_bucket.get());
        basicItem(IFWItems.tadpole_gold_bucket.get());
        basicItem(IFWItems.ancient_metal_bucket.get());
        basicItem(IFWItems.water_ancient_metal_bucket.get());
        basicItem(IFWItems.lava_ancient_metal_bucket.get());
        basicItem(IFWItems.powder_snow_ancient_metal_bucket.get());
        basicItem(IFWItems.milk_ancient_metal_bucket.get());
        basicItem(IFWItems.stone_ancient_metal_bucket.get());
        basicItem(IFWItems.pufferfish_ancient_metal_bucket.get());
        basicItem(IFWItems.salmon_ancient_metal_bucket.get());
        basicItem(IFWItems.copper_horse_armor.get());
        basicItem(IFWItems.silver_horse_armor.get());
        basicItem(IFWItems.ancient_metal_horse_armor.get());
        basicItem(IFWItems.mithril_horse_armor.get());
        basicItem(IFWItems.adamantium_horse_armor.get());
        basicItem(IFWItems.cod_ancient_metal_bucket.get());
        basicItem(IFWItems.tropical_ancient_metal_bucket.get());
        basicItem(IFWItems.axolotl_ancient_metal_bucket.get());
        basicItem(IFWItems.tadpole_ancient_metal_bucket.get());
        basicItem(IFWItems.mithril_bucket.get());
        basicItem(IFWItems.water_mithril_bucket.get());
        basicItem(IFWItems.lava_mithril_bucket.get());
        basicItem(IFWItems.powder_snow_mithril_bucket.get());
        basicItem(IFWItems.milk_mithril_bucket.get());
        basicItem(IFWItems.stone_mithril_bucket.get());
        basicItem(IFWItems.pufferfish_mithril_bucket.get());
        basicItem(IFWItems.salmon_mithril_bucket.get());
        basicItem(IFWItems.cod_mithril_bucket.get());
        basicItem(IFWItems.tropical_mithril_bucket.get());
        basicItem(IFWItems.axolotl_mithril_bucket.get());
        basicItem(IFWItems.tadpole_mithril_bucket.get());
        basicItem(IFWItems.adamantium_bucket.get());
        basicItem(IFWItems.water_adamantium_bucket.get());
        basicItem(IFWItems.lava_adamantium_bucket.get());
        basicItem(IFWItems.powder_snow_adamantium_bucket.get());
        basicItem(IFWItems.milk_adamantium_bucket.get());
        basicItem(IFWItems.stone_adamantium_bucket.get());
        basicItem(IFWItems.pufferfish_adamantium_bucket.get());
        basicItem(IFWItems.salmon_adamantium_bucket.get());
        basicItem(IFWItems.cod_adamantium_bucket.get());
        basicItem(IFWItems.tropical_adamantium_bucket.get());
        basicItem(IFWItems.axolotl_adamantium_bucket.get());
        basicItem(IFWItems.tadpole_adamantium_bucket.get());
        //armor
        basicItem(IFWItems.copper_helmet.get());
        basicItem(IFWItems.copper_chestplate.get());
        basicItem(IFWItems.copper_leggings.get());
        basicItem(IFWItems.copper_boots.get());
        basicItem(IFWItems.silver_helmet.get());
        basicItem(IFWItems.silver_chestplate.get());
        basicItem(IFWItems.silver_leggings.get());
        basicItem(IFWItems.silver_boots.get());
        basicItem(IFWItems.rusted_iron_helmet.get());
        basicItem(IFWItems.rusted_iron_chestplate.get());
        basicItem(IFWItems.rusted_iron_leggings.get());
        basicItem(IFWItems.rusted_iron_boots.get());
        basicItem(IFWItems.ancient_metal_helmet.get());
        basicItem(IFWItems.ancient_metal_chestplate.get());
        basicItem(IFWItems.ancient_metal_leggings.get());
        basicItem(IFWItems.ancient_metal_boots.get());
        basicItem(IFWItems.mithril_helmet.get());
        basicItem(IFWItems.mithril_chestplate.get());
        basicItem(IFWItems.mithril_leggings.get());
        basicItem(IFWItems.mithril_boots.get());
        basicItem(IFWItems.adamantium_helmet.get());
        basicItem(IFWItems.adamantium_chestplate.get());
        basicItem(IFWItems.adamantium_leggings.get());
        basicItem(IFWItems.adamantium_boots.get());
        basicItem(IFWItems.copper_chainmail_helmet.get());
        basicItem(IFWItems.copper_chainmail_chestplate.get());
        basicItem(IFWItems.copper_chainmail_leggings.get());
        basicItem(IFWItems.copper_chainmail_boots.get());
        basicItem(IFWItems.silver_chainmail_helmet.get());
        basicItem(IFWItems.silver_chainmail_chestplate.get());
        basicItem(IFWItems.silver_chainmail_leggings.get());
        basicItem(IFWItems.silver_chainmail_boots.get());
        basicItem(IFWItems.rusted_iron_chainmail_helmet.get());
        basicItem(IFWItems.rusted_iron_chainmail_chestplate.get());
        basicItem(IFWItems.rusted_iron_chainmail_leggings.get());
        basicItem(IFWItems.rusted_iron_chainmail_boots.get());
        basicItem(IFWItems.golden_chainmail_helmet.get());
        basicItem(IFWItems.golden_chainmail_chestplate.get());
        basicItem(IFWItems.golden_chainmail_leggings.get());
        basicItem(IFWItems.golden_chainmail_boots.get());
        basicItem(IFWItems.ancient_metal_chainmail_helmet.get());
        basicItem(IFWItems.ancient_metal_chainmail_chestplate.get());
        basicItem(IFWItems.ancient_metal_chainmail_leggings.get());
        basicItem(IFWItems.ancient_metal_chainmail_boots.get());
        basicItem(IFWItems.mithril_chainmail_helmet.get());
        basicItem(IFWItems.mithril_chainmail_chestplate.get());
        basicItem(IFWItems.mithril_chainmail_leggings.get());
        basicItem(IFWItems.mithril_chainmail_boots.get());
        basicItem(IFWItems.adamantium_chainmail_helmet.get());
        basicItem(IFWItems.adamantium_chainmail_chestplate.get());
        basicItem(IFWItems.adamantium_chainmail_leggings.get());
        basicItem(IFWItems.adamantium_chainmail_boots.get());
        //chain
        basicItem(IFWItems.copper_chain.get());
        basicItem(IFWItems.silver_chain.get());
        basicItem(IFWItems.golden_chain.get());
        basicItem(IFWItems.iron_chain.get());
        basicItem(IFWItems.rusted_iron_chain.get());
        basicItem(IFWItems.ancient_metal_chain.get());
        basicItem(IFWItems.mithril_chain.get());
        basicItem(IFWItems.adamantium_chain.get());
        spawnEggItem(IFWItems.chicken_spawn_egg.get());
        spawnEggItem(IFWItems.cow_spawn_egg.get());
        spawnEggItem(IFWItems.pig_spawn_egg.get());
        spawnEggItem(IFWItems.sheep_spawn_egg.get());
        spawnEggItem(IFWItems.zombie_spawn_egg.get());
    }
}
