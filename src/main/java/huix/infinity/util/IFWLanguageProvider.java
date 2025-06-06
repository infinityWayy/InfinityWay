package huix.infinity.util;

import huix.infinity.common.world.curse.CurseType;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class IFWLanguageProvider extends LanguageProvider {

    public IFWLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    public void add(CurseType instance, String translation) {
        this.add(instance.name(), translation);
    }
}
