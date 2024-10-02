package huix.infinity.datagen;

import huix.infinity.InfinityWay;
import huix.infinity.gameobjs.item.IFWItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IFWItemModelProvider extends ItemModelProvider {
    public IFWItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, InfinityWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(IFWItems.adamantium_ingot.get());
        basicItem(IFWItems.ancient_metal_ingot.get());
        basicItem(IFWItems.mithril_ingot.get());
        basicItem(IFWItems.silver_ingot.get());
    }
}
