package huix.infinity.common.core.tag;


import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

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
    public static final TagKey<Block> SHEARS_EFFECTIVE = create("shears_effective");
    public static final TagKey<Block> PORTABLE_BLOCK = create("portable_block");
    public static final TagKey<Block> IFW_FURNACE = create("ifw_furnace");
    public static final TagKey<Block> METAL_DOORS = create("ifw_furnace");
    public static final TagKey<Block> FALLEN_DIRT = create("fallen_dirt");

    public static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, name));
    }
}
