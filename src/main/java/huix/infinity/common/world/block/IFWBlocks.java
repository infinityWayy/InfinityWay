package huix.infinity.common.world.block;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(InfinityWay.MOD_ID);
    public static final DeferredRegister.Items ITEM_BLOCKS = IFWItems.ITEMS;

    public static final DeferredBlock<Block> adamantium_block = BLOCKS.registerSimpleBlock("adamantium_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_block_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_block", adamantium_block);
    public static final DeferredBlock<Block> adamantium_ore = BLOCKS.registerBlock("adamantium_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> adamantium_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_ore", adamantium_ore);
    public static final DeferredBlock<Block> adamantium_bars = BLOCKS.registerBlock("adamantium_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> adamantium_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_bars", adamantium_bars);
    public static final DeferredBlock<Block> adamantium_door = BLOCKS.registerBlock("adamantium_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> adamantium_door_item = ITEM_BLOCKS.register("adamantium_door",
                    item -> new DoubleHighBlockItem(adamantium_door.get(), new Item.Properties()));


    public static final DeferredBlock<Block> mithril_block = BLOCKS.registerSimpleBlock("mithril_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_block_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_block", mithril_block);
    public static final DeferredBlock<Block> mithril_ore = BLOCKS.registerBlock("mithril_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> mithril_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_ore", mithril_ore);
    public static final DeferredBlock<Block> mithril_bars = BLOCKS.registerBlock("mithril_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> mithril_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_bars", mithril_bars);
    public static final DeferredBlock<Block> mithril_door = BLOCKS.registerBlock("mithril_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> mithril_door_item = ITEM_BLOCKS.register("mithril_door",
            item -> new DoubleHighBlockItem(mithril_door.get(), new Item.Properties()));


    public static final DeferredBlock<Block> ancient_metal_block = BLOCKS.registerSimpleBlock("ancient_metal_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> ancient_metal_block_item = ITEM_BLOCKS.registerSimpleBlockItem("ancient_metal_block", ancient_metal_block);
    public static final DeferredBlock<Block> ancient_metal_bars = BLOCKS.registerBlock("ancient_metal_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> ancient_metal_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("ancient_metal_bars", ancient_metal_bars);
    public static final DeferredBlock<Block> ancient_metal_door = BLOCKS.registerBlock("ancient_metal_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> ancient_metal_door_item = ITEM_BLOCKS.register("ancient_metal_door",
            item -> new DoubleHighBlockItem(ancient_metal_door.get(), new Item.Properties()));



    public static final DeferredBlock<Block> gold_bars = BLOCKS.registerBlock("gold_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> gold_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("gold_bars", gold_bars);
    public static final DeferredBlock<Block> gold_door = BLOCKS.registerBlock("gold_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> gold_door_item = ITEM_BLOCKS.register("gold_door",
            item -> new DoubleHighBlockItem(gold_door.get(), new Item.Properties()));


    public static final DeferredBlock<Block> silver_block = BLOCKS.registerSimpleBlock("silver_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> silver_block_item = ITEM_BLOCKS.registerSimpleBlockItem("silver_block", silver_block);
    public static final DeferredBlock<Block> silver_ore = BLOCKS.registerBlock("silver_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> silver_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("silver_ore", silver_ore);
    public static final DeferredBlock<Block> silver_bars = BLOCKS.registerBlock("silver_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> silver_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("silver_bars", silver_bars);
    public static final DeferredBlock<Block> silver_door = BLOCKS.registerBlock("silver_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> silver_door_item = ITEM_BLOCKS.register("silver_door",
            item -> new DoubleHighBlockItem(silver_door.get(), new Item.Properties()));


    public static final DeferredBlock<Block> copper_bars = BLOCKS.registerBlock("copper_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> copper_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("copper_bars", copper_bars);
    public static final DeferredBlock<Block> copper_door = BLOCKS.registerBlock("copper_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> copper_door_item = ITEM_BLOCKS.register("copper_door",
            item -> new DoubleHighBlockItem(copper_door.get(), new Item.Properties()));

    public static final DeferredBlock<Block> raw_adamantium_block = BLOCKS.registerBlock("raw_adamantium_block",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> raw_adamantium_block_item = ITEM_BLOCKS.registerSimpleBlockItem("raw_adamantium_block", raw_adamantium_block);
    public static final DeferredBlock<Block> raw_mithril_block = BLOCKS.registerBlock("raw_mithril_block",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> raw_mithril_block_item = ITEM_BLOCKS.registerSimpleBlockItem("raw_mithril_block", raw_adamantium_block);
    public static final DeferredBlock<Block> raw_silver_block = BLOCKS.registerBlock("raw_silver_block",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> raw_silver_block_item = ITEM_BLOCKS.registerSimpleBlockItem("raw_silver_block", raw_silver_block);

    public static final DeferredBlock<Block> deepslate_adamantium_ore = BLOCKS.registerBlock("deepslate_adamantium_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> deepslate_adamantium_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("deepslate_adamantium_ore", deepslate_adamantium_ore);
    public static final DeferredBlock<Block> deepslate_mithril_ore = BLOCKS.registerBlock("deepslate_mithril_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> deepslate_mithril_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("deepslate_mithril_ore", deepslate_mithril_ore);
    public static final DeferredBlock<Block> deepslate_silver_ore = BLOCKS.registerBlock("deepslate_silver_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> deepslate_silver_ore_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("deepslate_silver_ore", deepslate_silver_ore);

}

