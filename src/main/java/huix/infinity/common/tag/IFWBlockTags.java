package huix.infinity.common.tag;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class IFWBlockTags {
    //flint,obsidian
    public static final TagKey<Block> NEEDS_LEVEL_0_TOOL = create("needs_level_0_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_0_TOOL = create("incorrect_for_level_0_tool");
    //copper,silver,gold, rusted_iron
    public static final TagKey<Block> NEEDS_LEVEL_1_TOOL = create("needs_level_1_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_1_TOOL = create("incorrect_for_level_1_tool");
    //iron,ancient_metal
    public static final TagKey<Block> NEEDS_LEVEL_2_TOOL = create("needs_level_2_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_2_TOOL = create("incorrect_for_level_2_tool");
    //mithril
    public static final TagKey<Block> NEEDS_LEVEL_3_TOOL = create("needs_level_3_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_3_TOOL = create("incorrect_for_level_3_tool");
    //adamantium
    public static final TagKey<Block> NEEDS_LEVEL_4_TOOL = create("needs_level_4_tool");
    public static final TagKey<Block> INCORRECT_FOR_LEVEL_4_TOOL = create("incorrect_for_level_4_tool");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace(name));
    }
}
