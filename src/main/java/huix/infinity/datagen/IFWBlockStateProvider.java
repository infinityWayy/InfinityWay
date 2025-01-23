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
        paneBlock((IronBarsBlock) IFWBlocks.adamantium_bars.get(), modLoc("block/adamantium_bars") , modLoc("block/adamantium_bars"));
        doorBlock((DoorBlock) IFWBlocks.adamantium_door.get(), "adamantium", modLoc("block/door_adamantium_upper"), modLoc("block/door_adamantium_upper"));


        ifw_simpleBlockWithItem(IFWBlocks.mithril_block.get());
        ifw_simpleBlockWithItem(IFWBlocks.mithril_ore.get());
        paneBlock((IronBarsBlock) IFWBlocks.mithril_bars.get(), modLoc("block/mithril_bars") , modLoc("block/mithril_bars"));
        doorBlock((DoorBlock) IFWBlocks.mithril_door.get(), "mithril", modLoc("block/door_mithril_upper"), modLoc("block/door_mithril_upper"));

        ifw_simpleBlockWithItem(IFWBlocks.ancient_metal_block.get());
        paneBlock((IronBarsBlock) IFWBlocks.ancient_metal_bars.get(), modLoc("block/ancient_metal_bars") , modLoc("block/ancient_metal_bars"));
        doorBlock((DoorBlock) IFWBlocks.ancient_metal_door.get(), "ancient_metal", modLoc("block/door_ancient_metal_upper"), modLoc("block/door_ancient_metal_upper"));

        paneBlock((IronBarsBlock) IFWBlocks.copper_bars.get(), modLoc("block/copper_bars") , modLoc("block/copper_bars"));
        doorBlock((DoorBlock) IFWBlocks.copper_door.get(), "copper", modLoc("block/door_copper_upper"), modLoc("block/door_copper_upper"));

        paneBlock((IronBarsBlock) IFWBlocks.gold_bars.get(), modLoc("block/gold_bars") , modLoc("block/gold_bars"));
        doorBlock((DoorBlock) IFWBlocks.gold_door.get(), "gold", modLoc("block/door_gold_upper"), modLoc("block/door_gold_upper"));

        ifw_simpleBlockWithItem(IFWBlocks.silver_block.get());
        ifw_simpleBlockWithItem(IFWBlocks.silver_ore.get());
        paneBlock((IronBarsBlock) IFWBlocks.silver_bars.get(), modLoc("block/silver_bars") , modLoc("block/silver_bars"));
        doorBlock((DoorBlock) IFWBlocks.silver_door.get(), "silver", modLoc("block/door_silver_upper"), modLoc("block/door_silver_upper"));

    }

    private void ifw_simpleBlockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }
}
