package huix.infinity.common.worldgen;

import huix.infinity.common.core.tag.IFWEntityTypeTags;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
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
    public static final ResourceKey<BiomeModifier> ADD_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_spawns"));
    public static final ResourceKey<BiomeModifier> REMOVE_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_remove_spawns"));



    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, context -> {
                FeatureUtils.register(context, IFWOreFeatures.ore_silver, Feature.ORE, IFWOreFeatures.ore_silver_cong);
                FeatureUtils.register(context, IFWOreFeatures.ore_mithril, Feature.ORE, IFWOreFeatures.mithril_ore_cong);
                FeatureUtils.register(context, IFWOreFeatures.ore_adamantium, Feature.ORE, IFWOreFeatures.adamantium_ore_cong);
            })

            .add(Registries.PLACED_FEATURE, context -> {
                context.register(IFWOrePlacements.ore_silver, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(IFWOreFeatures.ore_silver),
                                OrePlacements.commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(112)))));
                context.register(IFWOrePlacements.ore_mithril, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(IFWOreFeatures.ore_mithril),
                                OrePlacements.rareOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
                context.register(IFWOrePlacements.ore_adamantium, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(IFWOreFeatures.ore_adamantium),
                                OrePlacements.rareOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
            })

            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);
                HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);
                HolderGetter<EntityType<?>> entities = bootstrap.lookup(Registries.ENTITY_TYPE);

                //add
                bootstrap.register(ADD_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                                biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                                HolderSet.direct(placedFeatures.getOrThrow(IFWOrePlacements.ore_silver), placedFeatures.getOrThrow(IFWOrePlacements.ore_mithril),
                                        placedFeatures.getOrThrow(IFWOrePlacements.ore_adamantium)),
                                GenerationStep.Decoration.UNDERGROUND_ORES));

                //add animal
                bootstrap.register(ADD_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        List.of(
                                new MobSpawnSettings.SpawnerData(IFWEntityType.CHICKEN.get(), 10, 4, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.SHEEP.get(), 12, 4, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.PIG.get(), 10, 4, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.COW.get(), 8, 4, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.ZOMBIE.get(), 100, 4, 4)
                        )
                ));

                //remove
                bootstrap.register(REMOVE_FEATURES, new BiomeModifiers.RemoveFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND),
                                placedFeatures.getOrThrow(VegetationPlacements.PATCH_MELON), placedFeatures.getOrThrow(VegetationPlacements.PATCH_MELON_SPARSE)),
                        Set.of(GenerationStep.Decoration.LAKES, GenerationStep.Decoration.VEGETAL_DECORATION)));

                //remove animal
                bootstrap.register(REMOVE_SPAWNS, new BiomeModifiers.RemoveSpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        entities.getOrThrow(IFWEntityTypeTags.REPLACE)
                ));
            });

}
