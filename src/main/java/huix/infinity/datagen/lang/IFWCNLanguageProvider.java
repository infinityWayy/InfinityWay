package huix.infinity.datagen.lang;

import huix.infinity.InfinityWay;
import huix.infinity.common.block.IFWBlocks;
import huix.infinity.common.item.IFWItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class IFWCNLanguageProvider extends LanguageProvider {
    public IFWCNLanguageProvider(PackOutput output) {
        super(output, InfinityWay.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(IFWBlocks.adamantium_block_item.get(), "艾德曼块");
        add(IFWItems.adamantium_ingot.get(), "艾德曼锭");
    }
}
