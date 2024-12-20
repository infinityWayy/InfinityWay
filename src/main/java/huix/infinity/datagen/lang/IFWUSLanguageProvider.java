package huix.infinity.datagen.lang;

import huix.infinity.InfinityWay;
import huix.infinity.common.block.IFWBlocks;
import huix.infinity.common.item.IFWItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class IFWUSLanguageProvider extends LanguageProvider {
    public IFWUSLanguageProvider(PackOutput output) {
        super(output, InfinityWay.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(IFWBlocks.adamantium_block_item.get(), "Adamantium Block");
        add(IFWBlocks.adamantium_ore_item.get(), "Adamantium Ore");
        add(IFWBlocks.adamantium_bars_item.get(), "Adamantium Bars");
        add(IFWBlocks.adamantium_door_item.get(), "Adamantium Door");

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

        add(IFWItems.ancient_metal_ingot.get(), "Ancient Metal Ingot");
        add(IFWItems.mithril_ingot.get(), "Mithril Ingot");
        add(IFWItems.silver_ingot.get(), "Silver Ingot");

    }
}
