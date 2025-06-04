package huix.infinity.datagen.tag;

import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class IFWBlockTagsProvider extends BlockTagsProvider {
    public IFWBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, InfinityWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.@NotNull Provider provider) {
        tag(IFWBlockTags.HARVEST_LEVEL_0_TOOL).addTag(BlockTags.LOGS);
        tag(IFWBlockTags.HARVEST_LEVEL_1_TOOL).add(Blocks.STONE, Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.COPPER_ORE
                , Blocks.DEEPSLATE_COPPER_ORE, Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE,
                IFWBlocks.deepslate_silver_ore.get(), IFWBlocks.silver_ore.get());
        tag(IFWBlockTags.HARVEST_LEVEL_2_TOOL).add(
                Blocks.COPPER_BLOCK, Blocks.CUT_COPPER_STAIRS, Blocks.CUT_COPPER, Blocks.WEATHERED_COPPER, Blocks.WEATHERED_CUT_COPPER_SLAB,
                Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_COPPER, Blocks.OXIDIZED_CUT_COPPER_SLAB,
                Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER, Blocks.EXPOSED_COPPER, Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_STAIRS,
                Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_COPPER_BLOCK, Blocks.WAXED_CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.WAXED_OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.LIGHTNING_ROD, Blocks.CRAFTER, Blocks.CHISELED_COPPER, Blocks.EXPOSED_CHISELED_COPPER, Blocks.WEATHERED_CHISELED_COPPER,
                Blocks.OXIDIZED_CHISELED_COPPER, Blocks.WAXED_CHISELED_COPPER, Blocks.WAXED_EXPOSED_CHISELED_COPPER, Blocks.WAXED_WEATHERED_CHISELED_COPPER,
                Blocks.WAXED_OXIDIZED_CHISELED_COPPER, Blocks.COPPER_GRATE, Blocks.EXPOSED_COPPER_GRATE, Blocks.WEATHERED_COPPER_GRATE,
                Blocks.OXIDIZED_COPPER_GRATE, Blocks.WAXED_COPPER_GRATE, Blocks.WAXED_EXPOSED_COPPER_GRATE, Blocks.WAXED_WEATHERED_COPPER_GRATE,
                Blocks.WAXED_OXIDIZED_COPPER_GRATE, Blocks.COPPER_BULB, Blocks.EXPOSED_COPPER_BULB, Blocks.WEATHERED_COPPER_BULB, Blocks.OXIDIZED_COPPER_BULB,
                Blocks.WAXED_COPPER_BULB, Blocks.WAXED_EXPOSED_COPPER_BULB, Blocks.WAXED_WEATHERED_COPPER_BULB, Blocks.WAXED_OXIDIZED_COPPER_BULB,
                Blocks.COPPER_TRAPDOOR, Blocks.EXPOSED_COPPER_TRAPDOOR, Blocks.WEATHERED_COPPER_TRAPDOOR, Blocks.OXIDIZED_COPPER_TRAPDOOR, Blocks.WAXED_COPPER_TRAPDOOR,
                Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR, Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR, Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR, Blocks.COPPER_DOOR, Blocks.EXPOSED_COPPER_DOOR,
                Blocks.WEATHERED_COPPER_DOOR, Blocks.OXIDIZED_COPPER_DOOR, Blocks.WAXED_COPPER_DOOR, Blocks.WAXED_EXPOSED_COPPER_DOOR, Blocks.WAXED_WEATHERED_COPPER_DOOR, Blocks.WAXED_OXIDIZED_COPPER_DOOR, Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.RAW_GOLD_BLOCK, Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.RAW_IRON_BLOCK, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE, Blocks.RAW_COPPER_BLOCK, Blocks.CUT_COPPER_SLAB, Blocks.RESPAWN_ANCHOR, Blocks.GOLD_BLOCK,
                IFWBlocks.deepslate_mithril_ore.get(), IFWBlocks.mithril_ore.get(), IFWBlocks.copper_bars.get(), IFWBlocks.silver_block.get(), IFWBlocks.silver_bars.get(),
                IFWBlocks.silver_door.get(), IFWBlocks.gold_door.get(),
                IFWBlocks.mithril_runestone_nul.get(), IFWBlocks.mithril_runestone_quas.get(), IFWBlocks.mithril_runestone_por.get(), IFWBlocks.mithril_runestone_an.get(), IFWBlocks.mithril_runestone_nox.get(),
                IFWBlocks.mithril_runestone_flam.get(), IFWBlocks.mithril_runestone_vas.get(), IFWBlocks.mithril_runestone_des.get(), IFWBlocks.mithril_runestone_ort.get(), IFWBlocks.mithril_runestone_tym.get(),
                IFWBlocks.mithril_runestone_corp.get(), IFWBlocks.mithril_runestone_lor.get(), IFWBlocks.mithril_runestone_mani.get(), IFWBlocks.mithril_runestone_jux.get(), IFWBlocks.mithril_runestone_ylem.get(),
                IFWBlocks.mithril_runestone_sanct.get(),
                IFWBlocks.adamantium_runestone_nul.get(), IFWBlocks.adamantium_runestone_quas.get(), IFWBlocks.adamantium_runestone_por.get(), IFWBlocks.adamantium_runestone_an.get(), IFWBlocks.adamantium_runestone_nox.get(),
                IFWBlocks.adamantium_runestone_flam.get(), IFWBlocks.adamantium_runestone_vas.get(), IFWBlocks.adamantium_runestone_des.get(), IFWBlocks.adamantium_runestone_ort.get(), IFWBlocks.adamantium_runestone_tym.get(),
                IFWBlocks.adamantium_runestone_corp.get(), IFWBlocks.adamantium_runestone_lor.get(), IFWBlocks.adamantium_runestone_mani.get(), IFWBlocks.adamantium_runestone_jux.get(), IFWBlocks.adamantium_runestone_ylem.get(),
                IFWBlocks.adamantium_runestone_sanct.get()
        );
        tag(IFWBlockTags.HARVEST_LEVEL_3_TOOL).add(
                Blocks.IRON_BLOCK, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.EMERALD_BLOCK, Blocks.ANCIENT_DEBRIS, Blocks.IRON_BARS,
                IFWBlocks.adamantium_ore.get(), IFWBlocks.deepslate_adamantium_ore.get(), Blocks.DIAMOND_BLOCK, Blocks.IRON_DOOR,
                IFWBlocks.ancient_metal_block.get(), IFWBlocks.ancient_metal_bars.get(), IFWBlocks.ancient_metal_door.get()
                );
        tag(IFWBlockTags.HARVEST_LEVEL_4_TOOL).add(
                IFWBlocks.mithril_bars.get(), IFWBlocks.mithril_door.get(), IFWBlocks.mithril_block.get()
        );

        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_4_TOOL);
        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_3_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_4_TOOL);
        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_2_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_3_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_4_TOOL);
        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_2_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_3_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_4_TOOL);
        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_0_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_1_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_2_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_3_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_4_TOOL);

        tag(IFWBlockTags.METAL_DOORS).add(IFWBlocks.adamantium_door.get(), IFWBlocks.ancient_metal_door.get(), Blocks.COPPER_DOOR, IFWBlocks.gold_door.get(),
                IFWBlocks.mithril_door.get(), IFWBlocks.silver_door.get(), Blocks.IRON_DOOR);
        tag(BlockTags.DOORS).addTag(IFWBlockTags.METAL_DOORS);

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(IFWBlocks.adamantium_ore.get(), IFWBlocks.deepslate_adamantium_ore.get(),
                IFWBlocks.mithril_bars.get(), IFWBlocks.mithril_door.get(), IFWBlocks.mithril_block.get(), IFWBlocks.deepslate_mithril_ore.get(), IFWBlocks.mithril_ore.get(),
                IFWBlocks.deepslate_silver_ore.get(), IFWBlocks.silver_ore.get(), IFWBlocks.copper_bars.get(), IFWBlocks.silver_block.get(), IFWBlocks.silver_bars.get(),
                IFWBlocks.silver_door.get(), IFWBlocks.gold_door.get(), IFWBlocks.ancient_metal_block.get(), IFWBlocks.ancient_metal_bars.get(), IFWBlocks.ancient_metal_door.get(),
                IFWBlocks.mithril_runestone_nul.get(), IFWBlocks.mithril_runestone_quas.get(), IFWBlocks.mithril_runestone_por.get(), IFWBlocks.mithril_runestone_an.get(), IFWBlocks.mithril_runestone_nox.get(),
                IFWBlocks.mithril_runestone_flam.get(), IFWBlocks.mithril_runestone_vas.get(), IFWBlocks.mithril_runestone_des.get(), IFWBlocks.mithril_runestone_ort.get(), IFWBlocks.mithril_runestone_tym.get(),
                IFWBlocks.mithril_runestone_corp.get(), IFWBlocks.mithril_runestone_lor.get(), IFWBlocks.mithril_runestone_mani.get(), IFWBlocks.mithril_runestone_jux.get(), IFWBlocks.mithril_runestone_ylem.get(),
                IFWBlocks.mithril_runestone_sanct.get(),
                IFWBlocks.adamantium_runestone_nul.get(), IFWBlocks.adamantium_runestone_quas.get(), IFWBlocks.adamantium_runestone_por.get(), IFWBlocks.adamantium_runestone_an.get(), IFWBlocks.adamantium_runestone_nox.get(),
                IFWBlocks.adamantium_runestone_flam.get(), IFWBlocks.adamantium_runestone_vas.get(), IFWBlocks.adamantium_runestone_des.get(), IFWBlocks.adamantium_runestone_ort.get(), IFWBlocks.adamantium_runestone_tym.get(),
                IFWBlocks.adamantium_runestone_corp.get(), IFWBlocks.adamantium_runestone_lor.get(), IFWBlocks.adamantium_runestone_mani.get(), IFWBlocks.adamantium_runestone_jux.get(), IFWBlocks.adamantium_runestone_ylem.get(),
                IFWBlocks.adamantium_runestone_sanct.get()
        ).addTag(BlockTags.DOORS);

        tag(BlockTags.ANVIL).add(IFWBlocks.copper_anvil.get(), IFWBlocks.chipped_copper_anvil.get(), IFWBlocks.damaged_copper_anvil.get(),
                IFWBlocks.silver_anvil.get(), IFWBlocks.chipped_silver_anvil.get(), IFWBlocks.damaged_silver_anvil.get(),
                IFWBlocks.gold_anvil.get(), IFWBlocks.chipped_gold_anvil.get(), IFWBlocks.damaged_gold_anvil.get(),
                IFWBlocks.iron_anvil.get(), IFWBlocks.chipped_iron_anvil.get(), IFWBlocks.damaged_iron_anvil.get(),
                IFWBlocks.ancient_metal_anvil.get(), IFWBlocks.chipped_ancient_metal_anvil.get(), IFWBlocks.damaged_ancient_metal_anvil.get(),
                IFWBlocks.mithril_anvil.get(), IFWBlocks.chipped_mithril_anvil.get(), IFWBlocks.damaged_mithril_anvil.get(),
                IFWBlocks.adamantium_anvil.get(), IFWBlocks.chipped_adamantium_anvil.get(), IFWBlocks.damaged_adamantium_anvil.get());
        tag(IFWBlockTags.IFW_FURNACE).add(IFWBlocks.clay_furnace.get(), IFWBlocks.hardened_clay_furnace.get(), IFWBlocks.netherrack_furnace.get(), IFWBlocks.obsidian_furnace.get()
                                    , IFWBlocks.sandstone_furnace.get(), IFWBlocks.stone_furnace.get());
        tag(IFWBlockTags.PORTABLE_BLOCK).addTags(BlockTags.ANVIL, IFWBlockTags.IFW_FURNACE)
                .add(Blocks.CRAFTING_TABLE, Blocks.CHEST);
        tag(IFWBlockTags.SCYTHE_EFFECTIVE).add(Blocks.GLASS);
        tag(IFWBlockTags.SHEARS_EFFECTIVE).addTags(BlockTags.LEAVES, BlockTags.WOOL)
                .add(Blocks.COBWEB, Blocks.SHORT_GRASS, Blocks.FERN, Blocks.DEAD_BUSH, Blocks.HANGING_ROOTS, Blocks.VINE,Blocks.TRIPWIRE)
                ;
        tag(IFWBlockTags.FALLEN_DIRT).add(Blocks.DIRT, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.FARMLAND, Blocks.MUD);

        tag(IFWBlockTags.RUNESTONE)
                .add(
                        IFWBlocks.mithril_runestone_nul.get(), IFWBlocks.mithril_runestone_quas.get(), IFWBlocks.mithril_runestone_por.get(), IFWBlocks.mithril_runestone_an.get(), IFWBlocks.mithril_runestone_nox.get(),
                        IFWBlocks.mithril_runestone_flam.get(), IFWBlocks.mithril_runestone_vas.get(), IFWBlocks.mithril_runestone_des.get(), IFWBlocks.mithril_runestone_ort.get(), IFWBlocks.mithril_runestone_tym.get(),
                        IFWBlocks.mithril_runestone_corp.get(), IFWBlocks.mithril_runestone_lor.get(), IFWBlocks.mithril_runestone_mani.get(), IFWBlocks.mithril_runestone_jux.get(), IFWBlocks.mithril_runestone_ylem.get(),
                        IFWBlocks.mithril_runestone_sanct.get(),
                        IFWBlocks.adamantium_runestone_nul.get(), IFWBlocks.adamantium_runestone_quas.get(), IFWBlocks.adamantium_runestone_por.get(), IFWBlocks.adamantium_runestone_an.get(), IFWBlocks.adamantium_runestone_nox.get(),
                        IFWBlocks.adamantium_runestone_flam.get(), IFWBlocks.adamantium_runestone_vas.get(), IFWBlocks.adamantium_runestone_des.get(), IFWBlocks.adamantium_runestone_ort.get(), IFWBlocks.adamantium_runestone_tym.get(),
                        IFWBlocks.adamantium_runestone_corp.get(), IFWBlocks.adamantium_runestone_lor.get(), IFWBlocks.adamantium_runestone_mani.get(), IFWBlocks.adamantium_runestone_jux.get(), IFWBlocks.adamantium_runestone_ylem.get(),
                        IFWBlocks.adamantium_runestone_sanct.get()
                );

        tag(IFWBlockTags.CORROSIVE_DIRT).add(Blocks.GRASS_BLOCK, Blocks.ROOTED_DIRT, Blocks.MOSS_BLOCK, Blocks.MUDDY_MANGROVE_ROOTS);

        tag(IFWBlockTags.SLOW_CORROSION)
                .addTags(BlockTags.WOODEN_FENCES, BlockTags.FENCE_GATES, BlockTags.BEDS, BlockTags.WOODEN_PRESSURE_PLATES)
                .add(Blocks.HAY_BLOCK, Blocks.MELON, Blocks.PUMPKIN, Blocks.CACTUS, Blocks.LADDER, Blocks.SCAFFOLDING);

        tag(IFWBlockTags.ACID_DEGRADABLE)
                .addTags(IFWBlockTags.CORROSIVE_DIRT, BlockTags.REPLACEABLE_BY_TREES,
                        BlockTags.CROPS, BlockTags.FLOWERS, BlockTags.WOOL, BlockTags.WOOL_CARPETS, BlockTags.CAVE_VINES)
                .add(Blocks.KELP, Blocks.KELP_PLANT, Blocks.LILY_PAD, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM,
                        Blocks.CRIMSON_FUNGUS, Blocks.WARPED_FUNGUS, Blocks.CRIMSON_ROOTS,  Blocks.WARPED_ROOTS,
                        Blocks.NETHER_SPROUTS, Blocks.NETHER_WART, Blocks.COCOA, Blocks.SWEET_BERRY_BUSH,
                        Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT, Blocks.TWISTING_VINES, Blocks.TWISTING_VINES_PLANT)
                .remove(Blocks.WATER);
    }
}
