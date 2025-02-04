package huix.infinity.common.worldgen;

import huix.infinity.datagen.worldgen.IFWOreFeatures;
import huix.infinity.datagen.worldgen.IFWOrePlacements;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;

public class IFWFeatures {

    public static final ResourceKey<BiomeModifier> ADD_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_features"));
    public static final ResourceKey<BiomeModifier> REMOVE_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_remove_features"));



    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, context -> FeatureUtils.register(context, IFWOreFeatures.ore_silver, Feature.ORE, IFWOreFeatures.ore_silver_cong))
            .add(Registries.PLACED_FEATURE, context -> context.register(IFWOrePlacements.ore_silver,
                    new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(IFWOreFeatures.ore_silver),
                            OrePlacements.commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(112))))))
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);
                HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);
                //add
                bootstrap.register(ADD_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                                biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                                HolderSet.direct(placedFeatures.getOrThrow(IFWOrePlacements.ore_silver)),
                                GenerationStep.Decoration.UNDERGROUND_ORES));
                //remove
                bootstrap.register(REMOVE_FEATURES, new BiomeModifiers.RemoveFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND)),
                        Set.of(GenerationStep.Decoration.LAKES)));
            });

}
