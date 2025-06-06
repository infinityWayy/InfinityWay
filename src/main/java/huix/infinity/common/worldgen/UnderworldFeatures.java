package huix.infinity.common.worldgen;

import huix.infinity.common.world.dimension.UnderworldBiomes;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * 简化版地下世界特征配置，避免复杂依赖导致崩溃
 */
public class UnderworldFeatures {

    public static final ResourceKey<BiomeModifier> ADD_UNDERWORLD_BASIC_FEATURES =
            ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                    ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "add_underworld_basic_features"));

    // Bootstrap 方法 - 配置特征（简化版）
    public static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        try {
            InfinityWay.LOGGER.info("Bootstrapping Underworld Configured Features (simplified)");
            // 暂时为空，避免复杂的特征配置导致崩溃
            // 后续可以逐步添加简单的特征
        } catch (Exception e) {
            InfinityWay.LOGGER.error("Failed to bootstrap configured features: " + e.getMessage(), e);
        }
    }

    // Bootstrap 方法 - 放置特征（简化版）
    public static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        try {
            InfinityWay.LOGGER.info("Bootstrapping Underworld Placed Features (simplified)");
            // 暂时为空，避免复杂的放置配置导致崩溃
            // 后续可以逐步添加简单的放置特征
        } catch (Exception e) {
            InfinityWay.LOGGER.error("Failed to bootstrap placed features: " + e.getMessage(), e);
        }
    }

    // Bootstrap 方法 - 生物群系修饰器（简化版）
    public static void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> bootstrap) {
        try {
            HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);

            // 简化的生物群系修饰器，不添加复杂特征
            bootstrap.register(ADD_UNDERWORLD_BASIC_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(
                            biomes.getOrThrow(UnderworldBiomes.UNDERWORLD_WASTELAND),
                            biomes.getOrThrow(UnderworldBiomes.STALAGMITE_FOREST),
                            biomes.getOrThrow(UnderworldBiomes.LUSH_CAVERNS),
                            biomes.getOrThrow(UnderworldBiomes.DEEP_DARK_REALM)
                    ),
                    HolderSet.direct(), // 暂时不添加任何特征
                    GenerationStep.Decoration.UNDERGROUND_DECORATION
            ));

            InfinityWay.LOGGER.info("Bootstrapping Underworld Biome Modifiers (simplified)");
        } catch (Exception e) {
            InfinityWay.LOGGER.error("Failed to bootstrap biome modifiers: " + e.getMessage(), e);
        }
    }

    // 简化的 RegistrySetBuilder
    public static final RegistrySetBuilder UNDERWORLD_BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, UnderworldBiomes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, UnderworldFeatures::bootstrapConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, UnderworldFeatures::bootstrapPlacedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, UnderworldFeatures::bootstrapBiomeModifiers);
}