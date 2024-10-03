package huix.infinity.datagen.loot;

import huix.infinity.gameobjs.block.IFWBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class IFWBlockLootSubProvider extends BlockLootSubProvider {

    protected IFWBlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return IFWBlocks.BLOCKS.getEntries().stream().
                map(e -> (Block) e.value()).toList();
    }

    @Override
    protected void generate() {
        dropSelf(IFWBlocks.adamantium_block.get());;
        dropSelf(IFWBlocks.adamantium_ore.get());;
        dropSelf(IFWBlocks.adamantium_bars.get());;

        add(IFWBlocks.adamantium_door.get(), this::createDoorTable);
    }
}
