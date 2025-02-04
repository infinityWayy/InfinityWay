package huix.infinity.datagen.worldgen;


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
}
