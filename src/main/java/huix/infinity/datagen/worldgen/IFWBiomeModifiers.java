package huix.infinity.datagen.worldgen;

import huix.infinity.common.worldgen.IFWFeatures;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IFWBiomeModifiers extends DatapackBuiltinEntriesProvider {
    public IFWBiomeModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, IFWFeatures.BUILDER, Set.of(InfinityWay.MOD_ID));
    }

    @Override
    public String getName() {
        return "Biome Modifier Registries: " + InfinityWay.MOD_ID;
    }
}
