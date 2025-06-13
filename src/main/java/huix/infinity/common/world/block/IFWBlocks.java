package huix.infinity.common.world.block;

import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class IFWBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(InfinityWay.MOD_ID);
    public static final DeferredRegister.Items ITEM_BLOCKS = IFWItems.ITEMS;

    public static final DeferredBlock<IFWAnvilBlock> copper_anvil = BLOCKS.registerBlock("copper_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.COPPER));
    public static final DeferredItem<BlockItem> copper_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("copper_anvil", copper_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> chipped_copper_anvil = BLOCKS.registerBlock("chipped_copper_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.COPPER));
    public static final DeferredItem<BlockItem> chipped_copper_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("chipped_copper_anvil", chipped_copper_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> damaged_copper_anvil = BLOCKS.registerBlock("damaged_copper_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.COPPER));
    public static final DeferredItem<BlockItem> damaged_copper_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("damaged_copper_anvil", damaged_copper_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> silver_anvil = BLOCKS.registerBlock("silver_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.SILVER));
    public static final DeferredItem<BlockItem> silver_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("silver_anvil", silver_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> chipped_silver_anvil = BLOCKS.registerBlock("chipped_silver_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.SILVER));
    public static final DeferredItem<BlockItem> chipped_silver_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("chipped_silver_anvil", chipped_silver_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> damaged_silver_anvil = BLOCKS.registerBlock("damaged_silver_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.SILVER));
    public static final DeferredItem<BlockItem> damaged_silver_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("damaged_silver_anvil", damaged_silver_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> gold_anvil = BLOCKS.registerBlock("gold_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.GOLD));
    public static final DeferredItem<BlockItem> gold_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("gold_anvil", gold_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> chipped_gold_anvil = BLOCKS.registerBlock("chipped_gold_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.GOLD));
    public static final DeferredItem<BlockItem> chipped_gold_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("chipped_gold_anvil", chipped_gold_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> damaged_gold_anvil = BLOCKS.registerBlock("damaged_gold_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.GOLD));
    public static final DeferredItem<BlockItem> damaged_gold_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("damaged_gold_anvil", damaged_gold_anvil,
            new Item.Properties().stacksTo(1).durability(198400));
    public static final DeferredBlock<IFWAnvilBlock> iron_anvil = BLOCKS.registerBlock("iron_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.IRON));
    public static final DeferredItem<BlockItem> iron_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("iron_anvil", iron_anvil,
            new Item.Properties().stacksTo(1).durability(396800));
    public static final DeferredBlock<IFWAnvilBlock> chipped_iron_anvil = BLOCKS.registerBlock("chipped_iron_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.IRON));
    public static final DeferredItem<BlockItem> chipped_iron_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("chipped_iron_anvil", chipped_iron_anvil,
            new Item.Properties().stacksTo(1).durability(396800));
    public static final DeferredBlock<IFWAnvilBlock> damaged_iron_anvil = BLOCKS.registerBlock("damaged_iron_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.IRON));
    public static final DeferredItem<BlockItem> damaged_iron_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("damaged_iron_anvil", damaged_iron_anvil,
            new Item.Properties().stacksTo(1).durability(396800));
    public static final DeferredBlock<IFWAnvilBlock> ancient_metal_anvil = BLOCKS.registerBlock("ancient_metal_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.ANCIENT_METAL));
    public static final DeferredItem<BlockItem> ancient_metal_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("ancient_metal_anvil", ancient_metal_anvil,
            new Item.Properties().stacksTo(1).durability(793600));
    public static final DeferredBlock<IFWAnvilBlock> chipped_ancient_metal_anvil = BLOCKS.registerBlock("chipped_ancient_metal_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.ANCIENT_METAL));
    public static final DeferredItem<BlockItem> chipped_ancient_metal_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("chipped_ancient_metal_anvil", chipped_ancient_metal_anvil,
            new Item.Properties().stacksTo(1).durability(793600));
    public static final DeferredBlock<IFWAnvilBlock> damaged_ancient_metal_anvil = BLOCKS.registerBlock("damaged_ancient_metal_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.ANCIENT_METAL));
    public static final DeferredItem<BlockItem> damaged_ancient_metal_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("damaged_ancient_metal_anvil", damaged_ancient_metal_anvil,
            new Item.Properties().stacksTo(1).durability(793600));
    public static final DeferredBlock<IFWAnvilBlock> mithril_anvil = BLOCKS.registerBlock("mithril_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.MITHRIL));
    public static final DeferredItem<BlockItem> mithril_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_anvil", mithril_anvil,
            new Item.Properties().stacksTo(1).durability(3174400));
    public static final DeferredBlock<IFWAnvilBlock> chipped_mithril_anvil = BLOCKS.registerBlock("chipped_mithril_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.MITHRIL));
    public static final DeferredItem<BlockItem> chipped_mithril_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("chipped_mithril_anvil", chipped_mithril_anvil,
            new Item.Properties().stacksTo(1).durability(3174400));
    public static final DeferredBlock<IFWAnvilBlock> damaged_mithril_anvil = BLOCKS.registerBlock("damaged_mithril_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.MITHRIL));
    public static final DeferredItem<BlockItem> damaged_mithril_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("damaged_mithril_anvil", damaged_mithril_anvil,
            new Item.Properties().stacksTo(1).durability(3174400));
    public static final DeferredBlock<IFWAnvilBlock> adamantium_anvil = BLOCKS.registerBlock("adamantium_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.ADAMANTIUM));
    public static final DeferredItem<BlockItem> adamantium_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_anvil", adamantium_anvil,
            new Item.Properties().stacksTo(1).durability(12697200));
    public static final DeferredBlock<IFWAnvilBlock> chipped_adamantium_anvil = BLOCKS.registerBlock("chipped_adamantium_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.ADAMANTIUM));
    public static final DeferredItem<BlockItem> chipped_adamantium_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("chipped_adamantium_anvil", chipped_adamantium_anvil,
            new Item.Properties().stacksTo(1).durability(12697200));
    public static final DeferredBlock<IFWAnvilBlock> damaged_adamantium_anvil = BLOCKS.registerBlock("damaged_adamantium_anvil",
            block -> new IFWAnvilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK), IFWTiers.ADAMANTIUM));
    public static final DeferredItem<BlockItem> damaged_adamantium_anvil_item = ITEM_BLOCKS.registerSimpleBlockItem("damaged_adamantium_anvil", damaged_adamantium_anvil,
            new Item.Properties().stacksTo(1).durability(12697200));

    public static final DeferredBlock<Block> emerald_enchanting_table = BLOCKS.registerBlock("emerald_enchanting_table",
            block -> new EmeraldEnchantingTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE)));
    public static final DeferredItem<BlockItem> emerald_enchanting_table_item = ITEM_BLOCKS.registerSimpleBlockItem("emerald_enchanting_table", emerald_enchanting_table, new Item.Properties().stacksTo(1));

    public static final DeferredBlock<Block> adamantium_block = BLOCKS.registerSimpleBlock("adamantium_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_block_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_block", adamantium_block, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> adamantium_ore = BLOCKS.registerBlock("adamantium_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> adamantium_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_ore", adamantium_ore, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> adamantium_bars = BLOCKS.registerBlock("adamantium_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> adamantium_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_bars", adamantium_bars, new Item.Properties().stacksTo(16));
    public static final DeferredBlock<Block> adamantium_door = BLOCKS.registerBlock("adamantium_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> adamantium_door_item = ITEM_BLOCKS.register("adamantium_door",
            item -> new DoubleHighBlockItem(adamantium_door.get(), new Item.Properties().stacksTo(1)));

    public static final DeferredBlock<Block> clay_furnace = BLOCKS.registerBlock("clay_furnace",
            block -> new IFWFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).strength(3.5F).lightLevel(Blocks.litBlockEmission(13))).furnaceLevel(1));
    public static final DeferredItem<BlockItem> clay_furnace_item = ITEM_BLOCKS.registerSimpleBlockItem("clay_furnace", clay_furnace, new Item.Properties().stacksTo(1));
    public static final DeferredBlock<Block> hardened_clay_furnace = BLOCKS.registerBlock("hardened_clay_furnace",
            block -> new IFWFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).strength(3.5F).lightLevel(Blocks.litBlockEmission(13))).furnaceLevel(2));
    public static final DeferredItem<BlockItem> hardened_clay_furnace_item = ITEM_BLOCKS.registerSimpleBlockItem("hardened_clay_furnace", hardened_clay_furnace, new Item.Properties().stacksTo(1));
    public static final DeferredBlock<Block> sandstone_furnace = BLOCKS.registerBlock("sandstone_furnace",
            block -> new IFWFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).strength(3.5F).lightLevel(Blocks.litBlockEmission(13))).furnaceLevel(2));
    public static final DeferredItem<BlockItem> sandstone_furnace_item = ITEM_BLOCKS.registerSimpleBlockItem("sandstone_furnace", sandstone_furnace, new Item.Properties().stacksTo(1));
    public static final DeferredBlock<Block> stone_furnace = BLOCKS.registerBlock("stone_furnace",
            block -> new IFWFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).strength(3.5F).lightLevel(Blocks.litBlockEmission(13))).furnaceLevel(3));
    public static final DeferredItem<BlockItem> stone_furnace_item = ITEM_BLOCKS.registerSimpleBlockItem("stone_furnace", stone_furnace, new Item.Properties().stacksTo(1));
    public static final DeferredBlock<Block> obsidian_furnace = BLOCKS.registerBlock("obsidian_furnace",
            block -> new IFWFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).strength(3.5F).lightLevel(Blocks.litBlockEmission(13))).furnaceLevel(4));
    public static final DeferredItem<BlockItem> obsidian_furnace_item = ITEM_BLOCKS.registerSimpleBlockItem("obsidian_furnace", obsidian_furnace, new Item.Properties().stacksTo(1));
    public static final DeferredBlock<Block> netherrack_furnace = BLOCKS.registerBlock("netherrack_furnace",
            block -> new IFWFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).strength(3.5F).lightLevel(Blocks.litBlockEmission(13))).furnaceLevel(5));
    public static final DeferredItem<BlockItem> netherrack_furnace_item = ITEM_BLOCKS.registerSimpleBlockItem("netherrack_furnace", netherrack_furnace, new Item.Properties().stacksTo(1));

    public static final DeferredBlock<Block> mithril_block = BLOCKS.registerSimpleBlock("mithril_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_block_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_block", mithril_block, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> mithril_ore = BLOCKS.registerBlock("mithril_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> mithril_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_ore", mithril_ore, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> mithril_bars = BLOCKS.registerBlock("mithril_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> mithril_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_bars", mithril_bars, new Item.Properties().stacksTo(16));
    public static final DeferredBlock<Block> mithril_door = BLOCKS.registerBlock("mithril_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> mithril_door_item = ITEM_BLOCKS.register("mithril_door",
            item -> new DoubleHighBlockItem(mithril_door.get(), new Item.Properties().stacksTo(1)));


    public static final DeferredBlock<Block> ancient_metal_block = BLOCKS.registerSimpleBlock("ancient_metal_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> ancient_metal_block_item = ITEM_BLOCKS.registerSimpleBlockItem("ancient_metal_block", ancient_metal_block, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> ancient_metal_bars = BLOCKS.registerBlock("ancient_metal_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> ancient_metal_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("ancient_metal_bars", ancient_metal_bars, new Item.Properties().stacksTo(16));
    public static final DeferredBlock<Block> ancient_metal_door = BLOCKS.registerBlock("ancient_metal_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> ancient_metal_door_item = ITEM_BLOCKS.register("ancient_metal_door",
            item -> new DoubleHighBlockItem(ancient_metal_door.get(), new Item.Properties().stacksTo(1)));



    public static final DeferredBlock<Block> gold_bars = BLOCKS.registerBlock("gold_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> gold_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("gold_bars", gold_bars, new Item.Properties().stacksTo(16));
    public static final DeferredBlock<Block> gold_door = BLOCKS.registerBlock("gold_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> gold_door_item = ITEM_BLOCKS.register("gold_door",
            item -> new DoubleHighBlockItem(gold_door.get(), new Item.Properties().stacksTo(1)));


    public static final DeferredBlock<Block> silver_block = BLOCKS.registerSimpleBlock("silver_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> silver_block_item = ITEM_BLOCKS.registerSimpleBlockItem("silver_block", silver_block, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> silver_ore = BLOCKS.registerBlock("silver_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> silver_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("silver_ore", silver_ore, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> silver_bars = BLOCKS.registerBlock("silver_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> silver_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("silver_bars", silver_bars, new Item.Properties().stacksTo(16));
    public static final DeferredBlock<Block> silver_door = BLOCKS.registerBlock("silver_door",
            block -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()
                    .strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> silver_door_item = ITEM_BLOCKS.register("silver_door",
            item -> new DoubleHighBlockItem(silver_door.get(), new Item.Properties().stacksTo(1)));


    public static final DeferredBlock<Block> copper_bars = BLOCKS.registerBlock("copper_bars",
            block -> new IronBarsBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F)
                    .sound(SoundType.METAL).noOcclusion()), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> copper_bars_item = ITEM_BLOCKS.registerSimpleBlockItem("copper_bars", copper_bars, new Item.Properties().stacksTo(16));

    public static final DeferredBlock<Block> raw_adamantium_block = BLOCKS.registerBlock("raw_adamantium_block",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> raw_adamantium_block_item = ITEM_BLOCKS.registerSimpleBlockItem("raw_adamantium_block", raw_adamantium_block, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> raw_mithril_block = BLOCKS.registerBlock("raw_mithril_block",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> raw_mithril_block_item = ITEM_BLOCKS.registerSimpleBlockItem("raw_mithril_block", raw_mithril_block, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> raw_silver_block = BLOCKS.registerBlock("raw_silver_block",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> raw_silver_block_item = ITEM_BLOCKS.registerSimpleBlockItem("raw_silver_block", raw_silver_block, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> deepslate_adamantium_ore = BLOCKS.registerBlock("deepslate_adamantium_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> deepslate_adamantium_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("deepslate_adamantium_ore", deepslate_adamantium_ore, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> deepslate_mithril_ore = BLOCKS.registerBlock("deepslate_mithril_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> deepslate_mithril_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("deepslate_mithril_ore", deepslate_mithril_ore, new Item.Properties().stacksTo(4));
    public static final DeferredBlock<Block> deepslate_silver_ore = BLOCKS.registerBlock("deepslate_silver_ore",
            block -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F)), BlockBehaviour.Properties.of());
    public static final DeferredItem<BlockItem> deepslate_silver_ore_item = ITEM_BLOCKS.registerSimpleBlockItem("deepslate_silver_ore", deepslate_silver_ore, new Item.Properties().stacksTo(4));

    //Runestones

    public static final DeferredBlock<Block> mithril_runestone_nul = BLOCKS.registerSimpleBlock("mithril_runestone_nul",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_nul_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_nul", mithril_runestone_nul, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_quas = BLOCKS.registerSimpleBlock("mithril_runestone_quas",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_quas_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_quas", mithril_runestone_quas, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_por = BLOCKS.registerSimpleBlock("mithril_runestone_por",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_por_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_por", mithril_runestone_por, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_an = BLOCKS.registerSimpleBlock("mithril_runestone_an",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_an_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_an", mithril_runestone_an, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_nox = BLOCKS.registerSimpleBlock("mithril_runestone_nox",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_nox_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_nox", mithril_runestone_nox, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_flam = BLOCKS.registerSimpleBlock("mithril_runestone_flam",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_flam_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_flam", mithril_runestone_flam, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_vas = BLOCKS.registerSimpleBlock("mithril_runestone_vas",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_vas_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_vas", mithril_runestone_vas, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_des = BLOCKS.registerSimpleBlock("mithril_runestone_des",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_des_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_des", mithril_runestone_des, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_ort = BLOCKS.registerSimpleBlock("mithril_runestone_ort",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_ort_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_ort", mithril_runestone_ort, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_tym = BLOCKS.registerSimpleBlock("mithril_runestone_tym",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_tym_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_tym", mithril_runestone_tym, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_corp = BLOCKS.registerSimpleBlock("mithril_runestone_corp",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_corp_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_corp", mithril_runestone_corp, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_lor = BLOCKS.registerSimpleBlock("mithril_runestone_lor",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_lor_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_lor", mithril_runestone_lor, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_mani = BLOCKS.registerSimpleBlock("mithril_runestone_mani",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_mani_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_mani", mithril_runestone_mani, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_jux = BLOCKS.registerSimpleBlock("mithril_runestone_jux",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_jux_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_jux", mithril_runestone_jux, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_ylem = BLOCKS.registerSimpleBlock("mithril_runestone_ylem",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_ylem_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_ylem", mithril_runestone_ylem, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> mithril_runestone_sanct = BLOCKS.registerSimpleBlock("mithril_runestone_sanct",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> mithril_runestone_sanct_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_runestone_sanct", mithril_runestone_sanct, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_nul = BLOCKS.registerSimpleBlock("adamantium_runestone_nul",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_nul_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_nul", adamantium_runestone_nul, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_quas = BLOCKS.registerSimpleBlock("adamantium_runestone_quas",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_quas_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_quas", adamantium_runestone_quas, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_por = BLOCKS.registerSimpleBlock("adamantium_runestone_por",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_por_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_por", adamantium_runestone_por, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_an = BLOCKS.registerSimpleBlock("adamantium_runestone_an",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_an_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_an", adamantium_runestone_an, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_nox = BLOCKS.registerSimpleBlock("adamantium_runestone_nox",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_nox_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_nox", adamantium_runestone_nox, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_flam = BLOCKS.registerSimpleBlock("adamantium_runestone_flam",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_flam_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_flam", adamantium_runestone_flam, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_vas = BLOCKS.registerSimpleBlock("adamantium_runestone_vas",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_vas_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_vas", adamantium_runestone_vas, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_des = BLOCKS.registerSimpleBlock("adamantium_runestone_des",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_des_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_des", adamantium_runestone_des, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_ort = BLOCKS.registerSimpleBlock("adamantium_runestone_ort",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_ort_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_ort", adamantium_runestone_ort, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_tym = BLOCKS.registerSimpleBlock("adamantium_runestone_tym",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_tym_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_tym", adamantium_runestone_tym, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_corp = BLOCKS.registerSimpleBlock("adamantium_runestone_corp",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_corp_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_corp", adamantium_runestone_corp, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_lor = BLOCKS.registerSimpleBlock("adamantium_runestone_lor",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_lor_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_lor", adamantium_runestone_lor, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_mani = BLOCKS.registerSimpleBlock("adamantium_runestone_mani",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_mani_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_mani", adamantium_runestone_mani, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_jux = BLOCKS.registerSimpleBlock("adamantium_runestone_jux",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_jux_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_jux", adamantium_runestone_jux, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_ylem = BLOCKS.registerSimpleBlock("adamantium_runestone_ylem",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_ylem_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_ylem", adamantium_runestone_ylem, new Item.Properties().stacksTo(4));

    public static final DeferredBlock<Block> adamantium_runestone_sanct = BLOCKS.registerSimpleBlock("adamantium_runestone_sanct",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(2.4F, 1200.0F).sound(SoundType.METAL));
    public static final DeferredItem<BlockItem> adamantium_runestone_sanct_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_runestone_sanct", adamantium_runestone_sanct, new Item.Properties().stacksTo(4));

    public static final List<DeferredItem<BlockItem>> MITHRILRUNESTONES = List.of(
            mithril_runestone_nul_item, mithril_runestone_quas_item, mithril_runestone_por_item, mithril_runestone_an_item, mithril_runestone_nox_item, mithril_runestone_flam_item,
            mithril_runestone_vas_item, mithril_runestone_des_item, mithril_runestone_ort_item, mithril_runestone_tym_item, mithril_runestone_corp_item, mithril_runestone_lor_item,
            mithril_runestone_mani_item, mithril_runestone_jux_item, mithril_runestone_ylem_item, mithril_runestone_sanct_item
    );

    public static final List<DeferredItem<BlockItem>> ADAMANTIUMRUNESTONES = List.of(
            adamantium_runestone_nul_item, adamantium_runestone_quas_item, adamantium_runestone_por_item, adamantium_runestone_an_item, adamantium_runestone_nox_item, adamantium_runestone_flam_item,
            adamantium_runestone_vas_item, adamantium_runestone_des_item, adamantium_runestone_ort_item, adamantium_runestone_tym_item, adamantium_runestone_corp_item, adamantium_runestone_lor_item,
            adamantium_runestone_mani_item, adamantium_runestone_jux_item, adamantium_runestone_ylem_item, adamantium_runestone_sanct_item
    );

    public static final DeferredBlock<Block> rune_portal = BLOCKS.registerBlock("rune_portal",
            block -> new RunePortalBlock(BlockBehaviour.Properties.of()));

//    public static final DeferredBlock<Block> cross_dimension_portal = BLOCKS.register("cross_dimension_portal",
//            () -> new CrossDimensionPortalBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<MantleBlock> mantle = BLOCKS.registerBlock("mantle",
            block -> new MantleBlock(BlockBehaviour.Properties.of()
                   .instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)
                   .lightLevel(state -> 15).isValidSpawn((state, level, pos, type) -> false).isRedstoneConductor((state, level, pos) -> true)
                   .isSuffocating((state, level, pos) -> true).isViewBlocking((state, level, pos) -> true).randomTicks()), BlockBehaviour.Properties.of());

    public static final DeferredItem<BlockItem> mantle_item = ITEM_BLOCKS.registerSimpleBlockItem("mantle", mantle,
            new Item.Properties().stacksTo(4).fireResistant());

    public static final DeferredBlock<Block> copper_private_chest = BLOCKS.registerBlock("copper_private_chest",
            block -> new PrivateChestBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(200.0F).sound(SoundType.METAL)));
    public static final DeferredItem<BlockItem> copper_private_chest_item = ITEM_BLOCKS.registerSimpleBlockItem("copper_private_chest", copper_private_chest,
            new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stacksTo(4));

    public static final DeferredBlock<Block> silver_private_chest = BLOCKS.registerBlock("silver_private_chest",
            block -> new PrivateChestBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(200.0F).sound(SoundType.METAL)));
    public static final DeferredItem<BlockItem> silver_private_chest_item = ITEM_BLOCKS.registerSimpleBlockItem("silver_private_chest", silver_private_chest,
            new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stacksTo(4));

    public static final DeferredBlock<Block> gold_private_chest = BLOCKS.registerBlock("gold_private_chest",
            block -> new PrivateChestBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(200.0F).sound(SoundType.METAL)));
    public static final DeferredItem<BlockItem> gold_private_chest_item = ITEM_BLOCKS.registerSimpleBlockItem("gold_private_chest", gold_private_chest,
            new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stacksTo(4));

    public static final DeferredBlock<Block> iron_private_chest = BLOCKS.registerBlock("iron_private_chest",
            block -> new PrivateChestBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(200.0F).sound(SoundType.METAL)));
    public static final DeferredItem<BlockItem> iron_private_chest_item = ITEM_BLOCKS.registerSimpleBlockItem("iron_private_chest", iron_private_chest,
            new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stacksTo(4));

    public static final DeferredBlock<Block> ancient_metal_private_chest = BLOCKS.registerBlock("ancient_metal_private_chest",
            block -> new PrivateChestBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(200.0F).sound(SoundType.METAL)));
    public static final DeferredItem<BlockItem> ancient_metal_private_chest_item = ITEM_BLOCKS.registerSimpleBlockItem("ancient_metal_private_chest", ancient_metal_private_chest,
            new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stacksTo(4));

    public static final DeferredBlock<Block> mithril_private_chest = BLOCKS.registerBlock("mithril_private_chest",
            block -> new PrivateChestBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(200.0F).sound(SoundType.METAL)));
    public static final DeferredItem<BlockItem> mithril_private_chest_item = ITEM_BLOCKS.registerSimpleBlockItem("mithril_private_chest", mithril_private_chest,
            new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stacksTo(4));

    public static final DeferredBlock<Block> adamantium_private_chest = BLOCKS.registerBlock("adamantium_private_chest",
            block -> new PrivateChestBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(200.0F).sound(SoundType.METAL)));
    public static final DeferredItem<BlockItem> adamantium_private_chest_item = ITEM_BLOCKS.registerSimpleBlockItem("adamantium_private_chest", adamantium_private_chest,
            new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stacksTo(4));
}

