package huix.infinity.common.block;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.item.IFWItems;
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



}

