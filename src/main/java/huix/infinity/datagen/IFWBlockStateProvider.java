package huix.infinity.datagen;


import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.block.IFWBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IFWBlockStateProvider extends BlockStateProvider {

    public IFWBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, InfinityWay.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(IFWBlocks.adamantium_block.get());
        simpleBlock(IFWBlocks.adamantium_ore.get());
        paneBlock((IronBarsBlock) IFWBlocks.adamantium_bars.get(), modLoc("block/adamantium_bars") , modLoc("block/adamantium_bars"));
        doorBlock((DoorBlock) IFWBlocks.adamantium_door.get(), "adamantium", modLoc("block/door_adamantium_upper"), modLoc("block/door_adamantium_upper"));


        simpleBlock(IFWBlocks.mithril_block.get());
        simpleBlock(IFWBlocks.mithril_ore.get());
        paneBlock((IronBarsBlock) IFWBlocks.mithril_bars.get(), modLoc("block/mithril_bars") , modLoc("block/mithril_bars"));
        doorBlock((DoorBlock) IFWBlocks.mithril_door.get(), "mithril", modLoc("block/door_mithril_upper"), modLoc("block/door_mithril_upper"));

        simpleBlock(IFWBlocks.ancient_metal_block.get());
        paneBlock((IronBarsBlock) IFWBlocks.ancient_metal_bars.get(), modLoc("block/ancient_metal_bars") , modLoc("block/ancient_metal_bars"));
        doorBlock((DoorBlock) IFWBlocks.ancient_metal_door.get(), "ancient_metal", modLoc("block/door_ancient_metal_upper"), modLoc("block/door_ancient_metal_upper"));

        paneBlock((IronBarsBlock) IFWBlocks.copper_bars.get(), modLoc("block/copper_bars") , modLoc("block/copper_bars"));
        doorBlock((DoorBlock) IFWBlocks.copper_door.get(), "copper", modLoc("block/door_copper_upper"), modLoc("block/door_copper_upper"));

        paneBlock((IronBarsBlock) IFWBlocks.gold_bars.get(), modLoc("block/gold_bars") , modLoc("block/gold_bars"));
        doorBlock((DoorBlock) IFWBlocks.gold_door.get(), "gold", modLoc("block/door_gold_upper"), modLoc("block/door_gold_upper"));

        simpleBlock(IFWBlocks.silver_block.get());
        simpleBlock(IFWBlocks.silver_ore.get());
        paneBlock((IronBarsBlock) IFWBlocks.silver_bars.get(), modLoc("block/silver_bars") , modLoc("block/silver_bars"));
        doorBlock((DoorBlock) IFWBlocks.silver_door.get(), "silver", modLoc("block/door_silver_upper"), modLoc("block/door_silver_upper"));

        simpleBlock(IFWBlocks.raw_adamantium_block.get());
        simpleBlock(IFWBlocks.raw_mithril_block.get());
        simpleBlock(IFWBlocks.raw_silver_block.get());
        simpleBlock(IFWBlocks.deepslate_adamantium_ore.get());
        simpleBlock(IFWBlocks.deepslate_mithril_ore.get());
        simpleBlock(IFWBlocks.deepslate_silver_ore.get());


        Block stoneFurnaceBlock = IFWBlocks.stone_furnace.get();
        ModelFile stoneFurnaceModel = models().orientable(
                "stone_furnace", // 模型名称
                modLoc("block/stone_furnace_side"), // 侧面纹理
                 modLoc("block/stone_furnace_front"), // 正面纹理（未燃烧）
                modLoc("block/stone_furnace_top") // 顶部纹理
        );
        ModelFile stoneFurnaceOnModel = models().orientable(
                "stone_furnace_on", // 燃烧状态模型名称
                modLoc("block/stone_furnace_side"), // 侧面纹理（相同）
                modLoc("block/stone_furnace_front_on"), // 正面纹理（燃烧状态）
                modLoc("block/stone_furnace_top") // 顶部纹理（相同）
        );
        horizontalBlock(stoneFurnaceBlock, state -> {
            boolean isLit = state.getValue(BlockStateProperties.LIT);
            return isLit ? stoneFurnaceOnModel : stoneFurnaceModel;}, 180); // 调整旋转角度（可选）
        simpleBlockItem(stoneFurnaceBlock, stoneFurnaceModel);
    }
}
