package huix.infinity.common.worldgen;


import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.util.WorldGenHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class IFWOreFeatures {
    private static final RuleTest stone = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    private static final RuleTest deepslate = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);


    public static final ResourceKey<ConfiguredFeature<?, ?>> ore_silver = WorldGenHelper.createKeyToCong("ore_silver");
    public static final OreConfiguration ore_silver_cong = new OreConfiguration(

            List.of(OreConfiguration.target(stone, IFWBlocks.silver_ore.get().defaultBlockState()),
                    OreConfiguration.target(deepslate, IFWBlocks.deepslate_silver_ore.get().defaultBlockState())), 10);


    public static final ResourceKey<ConfiguredFeature<?, ?>> ore_mithril = WorldGenHelper.createKeyToCong("ore_mithril");
    public static final OreConfiguration mithril_ore_cong = new OreConfiguration(

            List.of(OreConfiguration.target(stone, IFWBlocks.mithril_ore.get().defaultBlockState()),
                    OreConfiguration.target(deepslate, IFWBlocks.deepslate_mithril_ore.get().defaultBlockState())), 5);


    public static final ResourceKey<ConfiguredFeature<?, ?>> ore_adamantium = WorldGenHelper.createKeyToCong("ore_adamantium");
    public static final OreConfiguration adamantium_ore_cong = new OreConfiguration(

            List.of(OreConfiguration.target(stone, IFWBlocks.adamantium_ore.get().defaultBlockState()),
                    OreConfiguration.target(deepslate, IFWBlocks.deepslate_adamantium_ore.get().defaultBlockState())), 4);
}
