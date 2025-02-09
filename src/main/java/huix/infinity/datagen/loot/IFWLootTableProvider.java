package huix.infinity.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IFWLootTableProvider extends LootTableProvider {
    public IFWLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(new SubProviderEntry(IFWBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(IFWEntityLootSubProvider::new, LootContextParamSets.ENTITY)
        ), registries);
    }
}
