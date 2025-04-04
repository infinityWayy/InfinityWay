package huix.infinity.util;

import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.effect.PersistentEffect;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class IFWLanguageProvider extends LanguageProvider {

    public IFWLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    public void add(PersistentEffect key, String name) {
        this.add(key.getDescriptionId(), name);
    }
}
