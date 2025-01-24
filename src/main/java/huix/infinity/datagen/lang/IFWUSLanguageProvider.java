package huix.infinity.datagen.lang;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class IFWUSLanguageProvider extends LanguageProvider {
    public IFWUSLanguageProvider(PackOutput output) {
        super(output, InfinityWay.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Blocks
        add(IFWBlocks.adamantium_block_item.get(), "Adamantium Block");
        add(IFWBlocks.adamantium_ore_item.get(), "Adamantium Ore");
        add(IFWBlocks.adamantium_bars_item.get(), "Adamantium Bars");
        add(IFWBlocks.adamantium_door_item.get(), "Adamantium Door");
        add(IFWBlocks.mithril_block_item.get(), "Mithril Block");
        add(IFWBlocks.mithril_ore_item.get(), "Mithril Ore");
        add(IFWBlocks.mithril_bars_item.get(), "Mithril Bars");
        add(IFWBlocks.mithril_door_item.get(), "Mithril Door");
        add(IFWBlocks.ancient_metal_block_item.get(), "Ancient Metal Block");
        add(IFWBlocks.ancient_metal_bars_item.get(), "Ancient Metal Bars");
        add(IFWBlocks.ancient_metal_door_item.get(), "Ancient Metal Door");
        add(IFWBlocks.gold_bars_item.get(), "Gold Bars");
        add(IFWBlocks.gold_door_item.get(), "Gold Door");
        add(IFWBlocks.silver_block_item.get(), "Silver Block");
        add(IFWBlocks.silver_ore_item.get(), "Silver Ore");
        add(IFWBlocks.silver_bars_item.get(), "Silver Bars");
        add(IFWBlocks.silver_door_item.get(), "Silver Door");
        add(IFWBlocks.copper_bars_item.get(), "Copper Bars");
        add(IFWBlocks.copper_door_item.get(), "Copper Door");

        //Items
        add(IFWItems.flint_shard.get(), "Flint Shard");
        add(IFWItems.obsidian_shard.get(), "Obsidian Shard");
        add(IFWItems.glass_shard.get(), "Glass Shard");
        add(IFWItems.emerald_shard.get(), "Emerald Shard");
        add(IFWItems.diamond_shard.get(), "Diamond Shard");
        add(IFWItems.quartz_shard.get(), "Quartz Shard");
        add(IFWItems.adamantium_ingot.get(), "Adamantium Ingot");
        add(IFWItems.adamantium_pickaxe.get(), "Adamantium Pickaxe");
        add(IFWItems.adamantium_nugget.get(), "Adamantium Nugget");
        add(IFWItems.adamantium_shears.get(), "Adamantium Shears");
        add(IFWItems.adamantium_shovel.get(), "Adamantium Shovel");
        add(IFWItems.adamantium_hoe.get(), "Adamantium Hoe");
        add(IFWItems.adamantium_sword.get(), "Adamantium Sword");
        add(IFWItems.adamantium_axe.get(), "Adamantium Axe");
        add(IFWItems.adamantium_scythe.get(), "Adamantium Scythe");
        add(IFWItems.adamantium_mattock.get(), "Adamantium Mattock");
        add(IFWItems.adamantium_battle_axe.get(), "Adamantium Battle Axe");
        add(IFWItems.adamantium_war_hammer.get(), "Adamantium War Hammer");
        add(IFWItems.adamantium_dagger.get(), "Adamantium Dagger");
        add(IFWItems.mithril_ingot.get(), "Mithril Ingot");
        add(IFWItems.mithril_pickaxe.get(), "Mithril Pickaxe");
        add(IFWItems.mithril_nugget.get(), "Mithril Nugget");
        add(IFWItems.mithril_shears.get(), "Mithril Shears");
        add(IFWItems.mithril_shovel.get(), "Mithril Shovel");
        add(IFWItems.mithril_hoe.get(), "Mithril Hoe");
        add(IFWItems.mithril_sword.get(), "Mithril Sword");
        add(IFWItems.mithril_axe.get(), "Mithril Axe");
        add(IFWItems.mithril_scythe.get(), "Mithril Scythe");
        add(IFWItems.mithril_mattock.get(), "Mithril Mattock");
        add(IFWItems.mithril_battle_axe.get(), "Mithril Battle Axe");
        add(IFWItems.mithril_war_hammer.get(), "Mithril War Hammer");
        add(IFWItems.mithril_dagger.get(), "Mithril Dagger");
        add(IFWItems.ancient_metal_ingot.get(), "Ancient Metal Ingot");
        add(IFWItems.ancient_metal_pickaxe.get(), "Ancient Metal Pickaxe");
        add(IFWItems.ancient_metal_nugget.get(), "Ancient Metal Nugget");
        add(IFWItems.ancient_metal_shears.get(), "Ancient Metal Shears");
        add(IFWItems.ancient_metal_shovel.get(), "Ancient Metal Shovel");
        add(IFWItems.ancient_metal_hoe.get(), "Ancient Metal Hoe");
        add(IFWItems.ancient_metal_sword.get(), "Ancient Metal Sword");
        add(IFWItems.ancient_metal_axe.get(), "Ancient Metal Axe");
        add(IFWItems.ancient_metal_scythe.get(), "Ancient Metal Scythe");
        add(IFWItems.ancient_metal_mattock.get(), "Ancient Metal Mattock");
        add(IFWItems.ancient_metal_battle_axe.get(), "Ancient Metal Battle Axe");
        add(IFWItems.ancient_metal_war_hammer.get(), "Ancient Metal War Hammer");
        add(IFWItems.ancient_metal_dagger.get(), "Ancient Metal Dagger");
        add(IFWItems.silver_ingot.get(), "Silver Ingot");
        add(IFWItems.silver_pickaxe.get(), "Silver Pickaxe");
        add(IFWItems.silver_nugget.get(), "Silver Nugget");
        add(IFWItems.silver_shears.get(), "Silver Shears");
        add(IFWItems.silver_shovel.get(), "Silver Shovel");
        add(IFWItems.silver_hoe.get(), "Silver Hoe");
        add(IFWItems.silver_sword.get(), "Silver Sword");
        add(IFWItems.silver_axe.get(), "Silver Axe");
        add(IFWItems.silver_scythe.get(), "Silver Scythe");
        add(IFWItems.silver_mattock.get(), "Silver Mattock");
        add(IFWItems.silver_battle_axe.get(), "Silver Battle Axe");
        add(IFWItems.silver_war_hammer.get(), "Silver War Hammer");
        add(IFWItems.silver_dagger.get(), "Silver Dagger");
        add(IFWItems.copper_pickaxe.get(), "Copper Pickaxe");
        add(IFWItems.copper_nugget.get(), "Copper Nugget");
        add(IFWItems.copper_shears.get(), "Copper Shears");
        add(IFWItems.copper_shovel.get(), "Copper Shovel");
        add(IFWItems.copper_hoe.get(), "Copper Hoe");
        add(IFWItems.copper_sword.get(), "Copper Sword");
        add(IFWItems.copper_axe.get(), "Copper Axe");
        add(IFWItems.copper_scythe.get(), "Copper Scythe");
        add(IFWItems.copper_mattock.get(), "Copper Mattock");
        add(IFWItems.copper_battle_axe.get(), "Copper Battle Axe");
        add(IFWItems.copper_war_hammer.get(), "Copper War Hammer");
        add(IFWItems.copper_dagger.get(), "Copper Dagger");
        add(IFWItems.rusted_iron_pickaxe.get(), "Rusted Iron Pickaxe");
        add(IFWItems.rusted_iron_shears.get(), "Rusted Iron Shears");
        add(IFWItems.rusted_iron_shovel.get(), "Rusted Iron Shovel");
        add(IFWItems.rusted_iron_hoe.get(), "Rusted Iron Hoe");
        add(IFWItems.rusted_iron_sword.get(), "Rusted Iron Sword");
        add(IFWItems.rusted_iron_axe.get(), "Rusted Iron Axe");
        add(IFWItems.rusted_iron_scythe.get(), "Rusted Iron Scythe");
        add(IFWItems.rusted_iron_mattock.get(), "Rusted Iron Mattock");
        add(IFWItems.rusted_iron_battle_axe.get(), "Rusted Iron Battle Axe");
        add(IFWItems.rusted_iron_war_hammer.get(), "Rusted Iron War Hammer");
        add(IFWItems.rusted_iron_dagger.get(), "Rusted Iron Dagger");
        add(IFWItems.iron_scythe.get(), "Iron Scythe");
        add(IFWItems.iron_mattock.get(), "Iron Mattock");
        add(IFWItems.iron_battle_axe.get(), "Iron Battle Axe");
        add(IFWItems.iron_war_hammer.get(), "Iron War Hammer");
        add(IFWItems.iron_dagger.get(), "Iron Dagger");
        add(IFWItems.gold_scythe.get(), "Gold Scythe");
        add(IFWItems.gold_mattock.get(), "Gold Mattock");
        add(IFWItems.gold_battle_axe.get(), "Gold Battle Axe");
        add(IFWItems.gold_war_hammer.get(), "Gold War Hammer");
        add(IFWItems.gold_dagger.get(), "Gold Dagger");
    }
}
