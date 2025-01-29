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
        ModelFile stoneFurnaceModel = models().orientable("stone_furnace", modLoc("block/stone_furnace_side"),
                 modLoc("block/stone_furnace_front"), modLoc("block/stone_furnace_top"));
        ModelFile stoneFurnaceOnModel = models().orientable("stone_furnace_on", modLoc("block/stone_furnace_side"),
                modLoc("block/stone_furnace_front_on"), modLoc("block/stone_furnace_top"));
        horizontalBlock(stoneFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? stoneFurnaceOnModel : stoneFurnaceModel, 180);
        simpleBlockItem(stoneFurnaceBlock, stoneFurnaceModel);

        Block clayFurnaceBlock = IFWBlocks.clay_furnace.get();
        ModelFile clayFurnaceModel = models().orientable("clay_furnace", modLoc("block/clay_furnace_side"),
                modLoc("block/clay_furnace_front"), modLoc("block/clay_furnace_top"));
        ModelFile clayFurnaceOnModel = models().orientable("clay_furnace_on", modLoc("block/clay_furnace_side"),
                modLoc("block/clay_furnace_front_on"), modLoc("block/clay_furnace_top"));
        horizontalBlock(clayFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? clayFurnaceOnModel : clayFurnaceModel, 180);
        simpleBlockItem(clayFurnaceBlock, clayFurnaceModel);

        Block hardenedClayFurnaceBlock = IFWBlocks.hardened_clay_furnace.get();
        ModelFile hardenedClayFurnaceModel = models().orientable("hardened_clay_furnace", modLoc("block/hardened_clay_furnace_side"),
                modLoc("block/hardened_clay_furnace_front"), modLoc("block/hardened_clay_furnace_top"));
        ModelFile hardenedClayFurnaceOnModel = models().orientable("hardened_clay_furnace_on", modLoc("block/hardened_clay_furnace_side"),
                modLoc("block/hardened_clay_furnace_front_on"), modLoc("block/hardened_clay_furnace_top"));
        horizontalBlock(hardenedClayFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? hardenedClayFurnaceModel : hardenedClayFurnaceOnModel, 180);
        simpleBlockItem(hardenedClayFurnaceBlock, hardenedClayFurnaceModel);

        Block sandstoneFurnaceBlock = IFWBlocks.sandstone_furnace.get();
        ModelFile sandstoneFurnaceModel = models().orientable("sandstone_furnace", modLoc("block/sandstone_furnace_side"),
                modLoc("block/sandstone_furnace_front"), modLoc("block/sandstone_furnace_top"));
        ModelFile sandstoneFurnaceOnModel = models().orientable("sandstone_furnace_on", modLoc("block/sandstone_furnace_side"),
                modLoc("block/sandstone_furnace_front_on"), modLoc("block/sandstone_furnace_top"));
        horizontalBlock(sandstoneFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? sandstoneFurnaceOnModel : sandstoneFurnaceModel, 180);
        simpleBlockItem(sandstoneFurnaceBlock, sandstoneFurnaceModel);

        Block obsidianFurnaceBlock = IFWBlocks.obsidian_furnace.get();
        ModelFile obsidianFurnaceModel = models().orientable("obsidian_furnace", modLoc("block/obsidian_furnace_side"),
                modLoc("block/obsidian_furnace_front"), modLoc("block/obsidian_furnace_top"));
        ModelFile obsidianFurnaceOnModel = models().orientable("obsidian_furnace_on", modLoc("block/obsidian_furnace_side"),
                modLoc("block/obsidian_furnace_front_on"), modLoc("block/obsidian_furnace_top"));
        horizontalBlock(obsidianFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? obsidianFurnaceOnModel : obsidianFurnaceModel, 180);
        simpleBlockItem(obsidianFurnaceBlock, obsidianFurnaceModel);

        Block netherrackFurnaceBlock = IFWBlocks.netherrack_furnace.get();
        ModelFile netherrackFurnaceModel = models().orientable("netherrack_furnace", modLoc("block/netherrack_furnace_side"),
                modLoc("block/netherrack_furnace_front"), modLoc("block/netherrack_furnace_top"));
        ModelFile netherrackFurnaceOnModel = models().orientable("netherrack_furnace_on", modLoc("block/netherrack_furnace_side"),
                modLoc("block/netherrack_furnace_front_on"), modLoc("block/netherrack_furnace_top"));
        horizontalBlock(netherrackFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? netherrackFurnaceOnModel : netherrackFurnaceModel, 180);
        simpleBlockItem(netherrackFurnaceBlock, netherrackFurnaceModel);

    }
}
