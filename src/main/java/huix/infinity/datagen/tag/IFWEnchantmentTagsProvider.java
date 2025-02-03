package huix.infinity.datagen.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class IFWEnchantmentTagsProvider extends EnchantmentTagsProvider {

    public IFWEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(final HolderLookup.@NotNull Provider provider) {
//        this.tooltipOrder(provider,
//                Enchantments.UNBREAKING);
    }
}
