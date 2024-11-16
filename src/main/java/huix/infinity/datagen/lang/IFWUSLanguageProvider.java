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
        add(IFWItems.adamantium_ingot.get(), "Adamantium Ingot");
    }
}
