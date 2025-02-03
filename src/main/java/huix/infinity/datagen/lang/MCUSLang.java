package huix.infinity.datagen.lang;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MCUSLang  extends LanguageProvider {
    public MCUSLang(PackOutput output) {
        super(output, "minecraft", "en_us");
    }

    @Override
    protected void addTranslations() {
        add(Items.DIAMOND, "Diamond");
        add(Items.EMERALD, "Emerald");
        add(Items.QUARTZ, "Quartz");
        add(Items.LAPIS_LAZULI, "Lapis_lazuli");
        add(Items.STICK, "Stick");
        add(Items.BONE, "Bone");
        add(Items.IRON_PICKAXE, "Iron Pickaxe");
    }
}
