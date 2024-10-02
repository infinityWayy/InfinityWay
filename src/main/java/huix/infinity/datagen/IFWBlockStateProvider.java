package huix.infinity.datagen;


import huix.infinity.InfinityWay;
import huix.infinity.gameobjs.block.IFWBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IFWBlockStateProvider extends BlockStateProvider {

    public IFWBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, InfinityWay.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ifw_simpleBlockWithItem(IFWBlocks.adamantium_block.get());
    }

    private void ifw_simpleBlockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }
}
