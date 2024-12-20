package huix.infinity.datagen;

import huix.infinity.InfinityWay;
import huix.infinity.common.block.IFWBlocks;
import huix.infinity.common.item.IFWItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IFWItemModelProvider extends ItemModelProvider {
    public IFWItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, InfinityWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(IFWBlocks.adamantium_door_item.get());

        basicItem(IFWItems.adamantium_ingot.get());
        basicItem(IFWItems.adamantium_pickaxe.get());
        basicItem(IFWItems.adamantium_nugget.get());
        basicItem(IFWItems.adamantium_shears.get());
        basicItem(IFWItems.adamantium_shovel.get());
        basicItem(IFWItems.adamantium_hoe.get());
        basicItem(IFWItems.adamantium_sword.get());
        basicItem(IFWItems.adamantium_axe.get());
        basicItem(IFWItems.adamantium_scythe.get());
        basicItem(IFWItems.adamantium_mattock.get());
        basicItem(IFWItems.adamantium_battle_axe.get());
        basicItem(IFWItems.adamantium_war_hammer.get());
        basicItem(IFWItems.adamantium_dagger.get());

        basicItem(IFWItems.ancient_metal_ingot.get());
        basicItem(IFWItems.mithril_ingot.get());
        basicItem(IFWItems.silver_ingot.get());

    }
}
