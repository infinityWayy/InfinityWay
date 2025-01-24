package huix.infinity.datagen;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IFWItemModelProvider extends ItemModelProvider {
    public IFWItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, InfinityWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
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
        basicItem(IFWBlocks.copper_door_item.get());
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
        handheldItem(IFWItems.wooden_cudgel.get());
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

        handheldItem(IFWItems.iron_pickaxe.get());
        handheldItem(IFWItems.iron_shears.get());
        handheldItem(IFWItems.iron_shovel.get());
        handheldItem(IFWItems.iron_hoe.get());
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

        handheldItem(IFWItems.gold_pickaxe.get());
        handheldItem(IFWItems.gold_shears.get());
        handheldItem(IFWItems.gold_shovel.get());
        handheldItem(IFWItems.gold_hoe.get());
        handheldItem(IFWItems.gold_sword.get());
        handheldItem(IFWItems.gold_axe.get());
        handheldItem(IFWItems.gold_scythe.get());
        handheldItem(IFWItems.gold_mattock.get());
        handheldItem(IFWItems.gold_battle_axe.get());
        handheldItem(IFWItems.gold_war_hammer.get());
        handheldItem(IFWItems.gold_dagger.get());

    }
}
