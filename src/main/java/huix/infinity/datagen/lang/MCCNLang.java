package huix.infinity.datagen.lang;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MCCNLang extends LanguageProvider {
    public MCCNLang(PackOutput output) {
        super(output, "minecraft", "zh_cn");
    }

    @Deprecated
    @Override
    protected void addTranslations() {
        add(Items.DIAMOND, "钻石");
        add(Items.EMERALD, "绿宝石");
        add(Items.QUARTZ, "石英");
        add(Items.LAPIS_LAZULI, "青金石");
        add(Items.STICK, "木棍");
        add(Items.BONE, "骨头");
        add(Items.IRON_PICKAXE, "铁镐");
    }
}
