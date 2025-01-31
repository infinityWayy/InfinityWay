package huix.infinity.datagen.tag;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.core.tag.IFWBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
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
        tag(IFWBlockTags.HARVEST_LEVEL_0_TOOL).addTags(BlockTags.LOGS);
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
                Blocks.DEEPSLATE_LAPIS_ORE, Blocks.RAW_COPPER_BLOCK, Blocks.CUT_COPPER_SLAB, Blocks.RESPAWN_ANCHOR,
                IFWBlocks.deepslate_mithril_ore.get(), IFWBlocks.mithril_ore.get()
        );
        tag(IFWBlockTags.HARVEST_LEVEL_3_TOOL).add(
                Blocks.IRON_BLOCK, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.EMERALD_BLOCK, Blocks.GOLD_BLOCK, Blocks.ANCIENT_DEBRIS,
                IFWBlocks.adamantium_ore.get(), IFWBlocks.deepslate_adamantium_ore.get()
        );
        tag(IFWBlockTags.HARVEST_LEVEL_4_TOOL).add(
                Blocks.DIAMOND_BLOCK, IFWBlocks.mithril_bars.get(), IFWBlocks.mithril_door.get(), IFWBlocks.mithril_block.get()
        );

        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_4_TOOL);
        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_3_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_3_TOOL);
        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_2_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_3_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_4_TOOL);
        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_2_TOOL)
                .addTag(IFWBlockTags.HARVEST_LEVEL_3_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_4_TOOL);
        tag(IFWBlockTags.INCORRECT_FOR_LEVEL_0_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_1_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_2_TOOL)
                .addTag(IFWBlockTags.HARVEST_LEVEL_3_TOOL).addTag(IFWBlockTags.HARVEST_LEVEL_4_TOOL);


        tag(BlockTags.DOORS).add(IFWBlocks.adamantium_door.get(), IFWBlocks.ancient_metal_door.get(), IFWBlocks.copper_door.get(), IFWBlocks.gold_door.get(),
                IFWBlocks.mithril_door.get(), IFWBlocks.silver_door.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(IFWBlocks.adamantium_door.get());
        tag(BlockTags.ANVIL).add(IFWBlocks.copper_anvil.get(), IFWBlocks.chipped_copper_anvil.get(), IFWBlocks.damaged_copper_anvil.get());
    }
}
