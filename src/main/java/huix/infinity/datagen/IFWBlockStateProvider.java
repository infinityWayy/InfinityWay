package huix.infinity.datagen;


import huix.infinity.init.InfinityWay;
import huix.infinity.common.block.IFWBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IFWBlockStateProvider extends BlockStateProvider {

    public IFWBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, InfinityWay.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ifw_simpleBlockWithItem(IFWBlocks.adamantium_block.get());
        ifw_simpleBlockWithItem(IFWBlocks.adamantium_ore.get());

        ResourceLocation bar_0_pane = modLoc("block/adamantium_bars");
        ResourceLocation bar_0_edge = modLoc("block/adamantium_bars");
        paneBlock((IronBarsBlock) IFWBlocks.adamantium_bars.get(), bar_0_pane, bar_0_edge);


        ResourceLocation door_0_bottom = modLoc("block/door_adamantium_lower");
        ResourceLocation door_0_top = modLoc("block/door_adamantium_upper");
        doorBlock((DoorBlock) IFWBlocks.adamantium_door.get(), "adamantium", door_0_bottom, door_0_top);
    }

    private void ifw_simpleBlockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }
}
