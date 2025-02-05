package huix.infinity.common.core.tag;


import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.minecraft.tags.BlockTags.create;

public class IFWBlockTags {
    //flint,obsidian
    public static final TagKey<Block> HARVEST_LEVEL_0_TOOL = create("harvest_level_0_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_0_TOOL = create("incorrect_for_level_0_tool");
    //copper,silver,gold,rusted_iron
    public static final TagKey<Block> HARVEST_LEVEL_1_TOOL = create("harvest_level_1_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_1_TOOL = create("incorrect_for_level_1_tool");
    //iron,ancient_metal
    public static final TagKey<Block> HARVEST_LEVEL_2_TOOL = create("harvest_level_2_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_2_TOOL = create("incorrect_for_level_2_tool");
    //mithril
    public static final TagKey<Block> HARVEST_LEVEL_3_TOOL = create("harvest_level_3_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_3_TOOL = create("incorrect_for_level_3_tool");
    //adamantium
    public static final TagKey<Block> HARVEST_LEVEL_4_TOOL = create("harvest_level_4_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_4_TOOL = create("incorrect_for_level_4_tool");

    public static final TagKey<Block> SCYTHE_EFFECTIVE = create("scythe_effective");
    public static final TagKey<Block> PORTABLE_BLOCK = create("portable_block");
    public static final TagKey<Block> IFW_FURNACE = create("ifw_furnace");
    public static final TagKey<Block> METAL_DOORS = create("ifw_furnace");
    public static final TagKey<Block> FALLEN_DIRT = create("fallen_dirt");
}
