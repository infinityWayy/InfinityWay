package huix.infinity.datagen.model;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
        getBuilder("copper_private_chest")
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", modLoc("block/copper_private_chest"));

        getBuilder("silver_private_chest")
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", modLoc("block/silver_private_chest"));

        getBuilder("gold_private_chest")
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", modLoc("block/gold_private_chest"));

        getBuilder("iron_private_chest")
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", modLoc("block/iron_private_chest"));

        getBuilder("ancient_metal_private_chest")
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", modLoc("block/ancient_metal_private_chest"));

        getBuilder("mithril_private_chest")
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", modLoc("block/mithril_private_chest"));

        getBuilder("adamantium_private_chest")
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", modLoc("block/adamantium_private_chest"));
        generateGoldPanModels();
        simpleBlockItem(IFWBlocks.mantle.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_nul.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_quas.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_por.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_an.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_nox.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_flam.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_vas.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_des.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_ort.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_tym.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_corp.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_lor.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_mani.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_jux.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_ylem.get());
        simpleBlockItem(IFWBlocks.mithril_runestone_sanct.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_nul.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_quas.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_por.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_an.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_nox.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_flam.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_vas.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_des.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_ort.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_tym.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_corp.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_lor.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_mani.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_jux.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_ylem.get());
        simpleBlockItem(IFWBlocks.adamantium_runestone_sanct.get());
        simpleBlockItem(IFWBlocks.emerald_enchanting_table.get());
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
        basicItem(IFWItems.jelly_ball.get());
        basicItem(IFWItems.blob_ball.get());
        basicItem(IFWItems.ooze_ball.get());
        basicItem(IFWItems.pudding_ball.get());
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
        basicItem(IFWItems.blueberry_porridge.get());
        basicItem(IFWItems.pumpkin_soup.get());
        basicItem(IFWItems.mushroom_soup_cream.get());
        basicItem(IFWItems.vegetable_soup.get());
        basicItem(IFWItems.vegetable_soup_cream.get());
        basicItem(IFWItems.chicken_soup.get());
        basicItem(IFWItems.beef_stew.get());
        basicItem(IFWItems.sorbet.get());
        basicItem(IFWItems.mashed_potato.get());
        basicItem(IFWItems.ice_cream.get());
        basicItem(IFWItems.orange.get());
        basicItem(IFWItems.blueberry.get());
        basicItem(IFWItems.banana.get());
        basicItem(IFWItems.cooked_worm.get());
        basicItem(IFWItems.worm.get());
        basicItem(IFWItems.horse_meat.get());
        basicItem(IFWItems.cooked_horse_meat.get());
        basicItem(IFWItems.onion.get());
        basicItem(IFWItems.flour.get());
        basicItem(IFWItems.manure.get());
        basicItem(IFWItems.wither_bone.get());
        basicItem(IFWItems.wither_bone_handle.get());
        basicItem(IFWItems.blazing_wither_bone.get());
        basicItem(IFWItems.bottle_of_disenchanting.get());
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
        // Chain
        basicItem(IFWItems.copper_chain.get());
        basicItem(IFWItems.silver_chain.get());
        basicItem(IFWItems.golden_chain.get());
        basicItem(IFWItems.iron_chain.get());
        basicItem(IFWItems.rusted_iron_chain.get());
        basicItem(IFWItems.ancient_metal_chain.get());
        basicItem(IFWItems.mithril_chain.get());
        basicItem(IFWItems.adamantium_chain.get());
        // Spawn Egg
        spawnEggItem(IFWItems.chicken_spawn_egg.get());
        spawnEggItem(IFWItems.cow_spawn_egg.get());
        spawnEggItem(IFWItems.pig_spawn_egg.get());
        spawnEggItem(IFWItems.sheep_spawn_egg.get());
        spawnEggItem(IFWItems.zombie_spawn_egg.get());
        spawnEggItem(IFWItems.revenant_spawn_egg.get());
        spawnEggItem(IFWItems.ghoul_spawn_egg.get());
        spawnEggItem(IFWItems.wight_spawn_egg.get());
        spawnEggItem(IFWItems.invisible_stalker_spawn_egg.get());
        spawnEggItem(IFWItems.shadow_spawn_egg.get());
        spawnEggItem(IFWItems.skeleton_spawn_egg.get());
        spawnEggItem(IFWItems.longdead_spawn_egg.get());
        spawnEggItem(IFWItems.longdead_guardian_spawn_egg.get());
        spawnEggItem(IFWItems.bone_lord_spawn_egg.get());
        spawnEggItem(IFWItems.ancient_bone_lord_spawn_egg.get());
        spawnEggItem(IFWItems.inferno_creeper_spawn_egg.get());
        spawnEggItem(IFWItems.dire_wolf_spawn_egg.get());
        spawnEggItem(IFWItems.hellhound_spawn_egg.get());
        spawnEggItem(IFWItems.spider_spawn_egg.get());
        spawnEggItem(IFWItems.wood_spider_spawn_egg.get());
        spawnEggItem(IFWItems.black_widow_spider_spawn_egg.get());
        spawnEggItem(IFWItems.phase_spider_spawn_egg.get());
        spawnEggItem(IFWItems.cave_spider_spawn_egg.get());
        spawnEggItem(IFWItems.demon_spider_spawn_egg.get());
        spawnEggItem(IFWItems.demon_spider_spawn_egg.get());
        spawnEggItem(IFWItems.slime_spawn_egg.get());
        spawnEggItem(IFWItems.jelly_spawn_egg.get());
        spawnEggItem(IFWItems.blob_spawn_egg.get());
        spawnEggItem(IFWItems.pudding_spawn_egg.get());
        spawnEggItem(IFWItems.ooze_spawn_egg.get());
        spawnEggItem(IFWItems.magma_cube_spawn_egg.get());
    }

    /**
     * 生成完整的淘金盘模型系统
     */
    private void generateGoldPanModels() {
        // 1. 先生成并注册基础模型
        generateEmptyGoldPan();
        generateFullGoldPan();
        generateHalfGoldPan();
        generateResultGoldPan();

        // 2. 生成主要的金盘物品模型（包括空的和装砂砾的）
        generateMainGoldPan();
    }

    private void generateEmptyGoldPan() {
        ItemModelBuilder builder = withExistingParent("gold_pan_empty", "block/block")
                .texture("0", modLoc("item/interior"))
                .texture("1", modLoc("item/grid"))
                .texture("particle", modLoc("item/interior"));

        addPanTransforms(builder);
        addGoldPanBaseElements(builder);
    }

    /**
     * 生成满砂砾模型
     */
    private void generateFullGoldPan() {
        ItemModelBuilder builder = withExistingParent("gold_pan_full", "block/block")
                .texture("0", modLoc("item/interior"))
                .texture("1", modLoc("item/grid"))
                .texture("2", mcLoc("block/gravel"))
                .texture("particle", modLoc("item/interior"));

        addPanTransforms(builder);
        addGoldPanBaseElements(builder);
        addFullGravelLayer(builder);
    }

    /**
     * 生成半满砂砾模型
     */
    private void generateHalfGoldPan() {
        ItemModelBuilder builder = withExistingParent("gold_pan_half", "block/block")
                .texture("0", modLoc("item/interior"))
                .texture("1", modLoc("item/grid"))
                .texture("2", mcLoc("block/gravel"))
                .texture("particle", modLoc("item/interior"));

        addPanTransforms(builder);
        addGoldPanBaseElements(builder);
        addHalfGravelLayer(builder);
    }

    /**
     * 生成结果模型（散落的砂砾颗粒）
     */
    private void generateResultGoldPan() {
        ItemModelBuilder builder = withExistingParent("gold_pan_result", "block/block")
                .texture("0", modLoc("item/interior"))
                .texture("1", modLoc("item/grid"))
                .texture("2", mcLoc("block/gravel"))
                .texture("particle", modLoc("item/interior"));

        addPanTransforms(builder);
        addGoldPanBaseElements(builder);
        addScatteredGravelParticles(builder);
    }

    /**
     * 生成主要的金盘物品模型（这个是实际注册给物品的模型）
     */
    private void generateMainGoldPan() {
        // 为空淘金盘生成基础模型
        ItemModelBuilder builder = withExistingParent("gold_pan", "block/block")
                .texture("0", modLoc("item/interior"))
                .texture("1", modLoc("item/grid"))
                .texture("particle", modLoc("item/interior"));

        addPanTransforms(builder);
        addGoldPanBaseElements(builder);

        // 为装砂砾的淘金盘生成带动态切换的模型
        ItemModelBuilder gravelBuilder = withExistingParent("gold_pan_gravel", "block/block")
                .texture("0", modLoc("item/interior"))
                .texture("1", modLoc("item/grid"))
                .texture("2", mcLoc("block/gravel"))
                .texture("particle", modLoc("item/interior"));

        addPanTransforms(gravelBuilder);
        addGoldPanBaseElements(gravelBuilder);
        addFullGravelLayer(gravelBuilder); // 默认满砂砾状态

        // 添加动态模型切换
        gravelBuilder.override()
                .predicate(modLoc("gravel_amount"), 0.25f)
                .model(getExistingFile(modLoc("item/gold_pan_result"))) // 散落颗粒
                .end()
                .override()
                .predicate(modLoc("gravel_amount"), 0.5f)
                .model(getExistingFile(modLoc("item/gold_pan_half"))) // 半满
                .end()
                .override()
                .predicate(modLoc("gravel_amount"), 0.75f)
                .model(getExistingFile(modLoc("item/gold_pan_full"))) // 满砂砾
                .end();
        // 默认状态（1.0f）使用基础模型，即满砂砾
    }

    /**
     * 添加淘金盘的display transforms
     */
    private ItemModelBuilder addPanTransforms(ItemModelBuilder builder) {
        return builder.transforms()
                // 第一人称右手 - 关键的调整
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 45, 0)      // 改为TFC的标准角度
                .translation(0, 0, 0)    // 重置位置偏移
                .scale(1.00f, 1.00f, 1.00f) // 重置缩放
                .end()
                // 第一人称左手
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 45, 0)      // 改为TFC的标准角度
                .translation(0, 0, 0)    // 重置位置偏移
                .scale(1.00f, 1.00f, 1.00f) // 重置缩放
                .end()
                // 第三人称保持你的设置
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5f, 0)
                .scale(0.375f, 0.375f, 0.375f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5f, 0)
                .scale(0.375f, 0.375f, 0.375f)
                .end()
                // GUI显示 - 调整为更好的显示角度
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 315, 0)    // 调整GUI显示角度
                .scale(0.625f, 0.625f, 0.625f)
                .end()
                // 地面显示
                .transform(ItemDisplayContext.GROUND)
                .translation(0, 3, 0)
                .scale(0.25f, 0.25f, 0.25f)
                .end()
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 225, 0)
                .scale(0.625f, 0.625f, 0.625f)
                .end()
                .end();
    }

    /**
     * 添加淘金盘的基础几何结构
     */
    private void addGoldPanBaseElements(ItemModelBuilder builder) {
        // 底部网格层 [2,5,2] to [14,6,14]
        builder.element()
                .from(2, 5, 2).to(14, 6, 14)
                .face(Direction.NORTH).uvs(0, 0, 12, 1).texture("#0").end()
                .face(Direction.EAST).uvs(0, 0, 12, 1).texture("#0").end()
                .face(Direction.SOUTH).uvs(0, 0, 12, 1).texture("#0").end()
                .face(Direction.WEST).uvs(0, 0, 12, 1).texture("#0").end()
                .face(Direction.UP).uvs(0, 0, 12, 12).texture("#1").end()
                .face(Direction.DOWN).uvs(2, 1, 14, 13).texture("#1").end()
                .end();

        // 前边框 [1,6,1] to [15,8,2]
        builder.element()
                .from(1, 6, 1).to(15, 8, 2)
                .face(Direction.NORTH).uvs(0, 0, 14, 2).texture("#0").end()
                .face(Direction.EAST).uvs(0, 0, 1, 2).texture("#0").end()
                .face(Direction.SOUTH).uvs(0, 0, 14, 2).texture("#0").end()
                .face(Direction.WEST).uvs(0, 0, 1, 2).texture("#0").end()
                .face(Direction.UP).uvs(0, 0, 14, 1).texture("#0").end()
                .face(Direction.DOWN).uvs(0, 0, 14, 1).texture("#0").end()
                .end();

        // 后边框 [1,6,14] to [15,8,15]
        builder.element()
                .from(1, 6, 14).to(15, 8, 15)
                .face(Direction.NORTH).uvs(0, 0, 14, 2).texture("#0").end()
                .face(Direction.EAST).uvs(0, 0, 1, 2).texture("#0").end()
                .face(Direction.SOUTH).uvs(0, 0, 14, 2).texture("#0").end()
                .face(Direction.WEST).uvs(0, 0, 1, 2).texture("#0").end()
                .face(Direction.UP).uvs(0, 0, 14, 1).texture("#0").end()
                .face(Direction.DOWN).uvs(0, 0, 14, 1).texture("#0").end()
                .end();

        // 左边框 [1,6,2] to [2,8,14]
        builder.element()
                .from(1, 6, 2).to(2, 8, 14)
                .face(Direction.EAST).uvs(0, 0, 12, 2).texture("#0").end()
                .face(Direction.WEST).uvs(0, 0, 12, 2).texture("#0").end()
                .face(Direction.UP).uvs(0, 0, 1, 12).texture("#0").end()
                .face(Direction.DOWN).uvs(0, 0, 1, 12).texture("#0").end()
                .end();

        // 右边框 [14,6,2] to [15,8,14]
        builder.element()
                .from(14, 6, 2).to(15, 8, 14)
                .face(Direction.EAST).uvs(0, 0, 12, 2).texture("#0").end()
                .face(Direction.WEST).uvs(0, 0, 12, 2).texture("#0").end()
                .face(Direction.UP).uvs(0, 0, 1, 12).texture("#0").end()
                .face(Direction.DOWN).uvs(0, 0, 1, 12).texture("#0").end()
                .end();
    }

    /**
     * 添加满砂砾层
     */
    private void addFullGravelLayer(ItemModelBuilder builder) {
        builder.element()
                .from(2, 7, 2).to(14, 8, 14)
                .face(Direction.NORTH).uvs(0, 0, 12, 1).texture("#2").end()
                .face(Direction.EAST).uvs(0, 0, 12, 1).texture("#2").end()
                .face(Direction.SOUTH).uvs(0, 0, 12, 1).texture("#2").end()
                .face(Direction.WEST).uvs(0, 0, 12, 1).texture("#2").end()
                .face(Direction.UP).uvs(0, 0, 12, 12).texture("#2").end()
                .face(Direction.DOWN).uvs(0, 0, 12, 12).texture("#2").end()
                .end();
    }

    /**
     * 添加半满砂砾层
     */
    private void addHalfGravelLayer(ItemModelBuilder builder) {
        builder.element()
                .from(2, 7, 2).to(14, 7, 14)
                .face(Direction.NORTH).uvs(0, 0, 12, 0).texture("#2").end()
                .face(Direction.EAST).uvs(0, 0, 12, 0).texture("#2").end()
                .face(Direction.SOUTH).uvs(0, 0, 12, 0).texture("#2").end()
                .face(Direction.WEST).uvs(0, 0, 12, 0).texture("#2").end()
                .face(Direction.UP).uvs(0, 0, 12, 12).texture("#2").end()
                .face(Direction.DOWN).uvs(0, 0, 12, 12).texture("#2").end()
                .end();
    }

    /**
     * 添加散落的砂砾颗粒
     */
    private void addScatteredGravelParticles(ItemModelBuilder builder) {
        int[][] particlePositions = {
                {5, 6, 10, 6, 7, 11}, {3, 6, 4, 4, 7, 5}, {10, 6, 4, 11, 7, 5},
                {8, 6, 6, 9, 7, 7}, {8, 6, 11, 9, 7, 12}, {10, 6, 7, 11, 7, 8}, {3, 6, 12, 4, 7, 13}
        };

        for (int[] pos : particlePositions) {
            builder.element()
                    .from(pos[0], pos[1], pos[2]).to(pos[3], pos[4], pos[5])
                    .face(Direction.NORTH).uvs(0, 0, 1, 1).texture("#2").end()
                    .face(Direction.EAST).uvs(0, 0, 1, 1).texture("#2").end()
                    .face(Direction.SOUTH).uvs(0, 0, 1, 1).texture("#2").end()
                    .face(Direction.WEST).uvs(0, 0, 1, 1).texture("#2").end()
                    .face(Direction.UP).uvs(0, 0, 1, 1).texture("#2").end()
                    .face(Direction.DOWN).uvs(0, 0, 1, 1).texture("#2").end()
                    .end();
        }
    }
}
