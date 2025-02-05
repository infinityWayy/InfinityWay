package huix.infinity.datagen.enchantment;

import huix.infinity.common.enchantment.IFWEnchantments;
import huix.infinity.common.worldgen.IFWFeatures;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IFWDataEnchantments extends DatapackBuiltinEntriesProvider {
    public IFWDataEnchantments(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, IFWEnchantments.BUILDER, Set.of(InfinityWay.MOD_ID));
    }

    @Override
    public String getName() {
        return "IFWEnchantments Registries: " + InfinityWay.MOD_ID;
    }
}
