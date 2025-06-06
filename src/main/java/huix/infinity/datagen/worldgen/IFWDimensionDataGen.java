package huix.infinity.datagen.worldgen;

import huix.infinity.common.world.dimension.IFWDimensionTypes;
import huix.infinity.common.world.dimension.UnderworldBiomes;
import huix.infinity.common.world.dimension.UnderworldBiomeSource;
import huix.infinity.common.world.dimension.UnderworldChunkGenerator;
import huix.infinity.common.worldgen.UnderworldFeatures;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IFWDimensionDataGen extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, IFWDimensionDataGen::bootstrapDimensionTypes)
            .add(Registries.BIOME, UnderworldBiomes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, UnderworldFeatures::bootstrapConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, UnderworldFeatures::bootstrapPlacedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, UnderworldFeatures::bootstrapBiomeModifiers)
            .add(Registries.LEVEL_STEM, IFWDimensionDataGen::bootstrapDimensions);

    public IFWDimensionDataGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(InfinityWay.MOD_ID));
    }

    private static void bootstrapDimensionTypes(net.minecraft.data.worldgen.BootstrapContext<DimensionType> context) {
        try {
            DimensionType underworldDimensionType = IFWDimensionTypes.UNDER_WORLD;
            context.register(ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "underworld")), underworldDimensionType);

        } catch (Exception e) {
            InfinityWay.LOGGER.error("Failed to register Underworld dimension type: " + e.getMessage(), e);
            throw new RuntimeException("Dimension type registration failed", e);
        }
    }

    private static void bootstrapDimensions(net.minecraft.data.worldgen.BootstrapContext<LevelStem> context) {
        try {
            // 获取必要的注册表
            var biomeRegistry = context.lookup(Registries.BIOME);
            var dimensionTypeRegistry = context.lookup(Registries.DIMENSION_TYPE);

            // 确保生物群系注册表不为空
            if (biomeRegistry == null) {
                throw new IllegalStateException("Biome registry is null during dimension bootstrap!");
            }

            BiomeSource biomeSource = new UnderworldBiomeSource(biomeRegistry);
            ChunkGenerator chunkGenerator = new UnderworldChunkGenerator(biomeSource);

            // 获取地下世界维度类型
            var underworldDimensionType = dimensionTypeRegistry.getOrThrow(
                    ResourceKey.create(Registries.DIMENSION_TYPE,
                            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "underworld"))
            );

            // 创建并注册地下世界维度
            LevelStem underworldStem = new LevelStem(underworldDimensionType, chunkGenerator);

            context.register(
                    ResourceKey.create(Registries.LEVEL_STEM,
                            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "underworld")),
                    underworldStem
            );

        } catch (Exception e) {
            throw new RuntimeException("Dimension registration failed", e);
        }
    }
}