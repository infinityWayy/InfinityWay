package huix.infinity.datagen.worldgen;

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
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.OptionalLong;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IFWDimensionDataGen extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            // 维度类型注册
            .add(Registries.DIMENSION_TYPE, IFWDimensionDataGen::bootstrapDimensionTypes)
            // 生物群系注册
            .add(Registries.BIOME, UnderworldBiomes::bootstrap)
            // 特征注册
            .add(Registries.CONFIGURED_FEATURE, UnderworldFeatures::bootstrapConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, UnderworldFeatures::bootstrapPlacedFeatures)
            // 生物群系修饰器注册
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, UnderworldFeatures::bootstrapBiomeModifiers)
            // 维度注册
            .add(Registries.LEVEL_STEM, IFWDimensionDataGen::bootstrapDimensions);

    public IFWDimensionDataGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(InfinityWay.MOD_ID));
    }

    /**
     * 注册维度类型 - 直接创建DimensionType对象
     */
    private static void bootstrapDimensionTypes(net.minecraft.data.worldgen.BootstrapContext<DimensionType> context) {
        try {
            // 创建地下世界维度类型
            DimensionType underworldDimensionType = new DimensionType(
                    OptionalLong.empty(), // fixedTime - 如果为空则有正常时间循环
                    false, // hasSkyLight - 是否有天空光照
                    false, // hasCeiling - 是否有基岩天花板（如下界）
                    false, // ultraWarm - 是否超热（影响水的蒸发等）
                    true, // natural - 是否为自然维度
                    1.0, // coordinateScale - 坐标缩放比例 1.0 = 与主世界同步
                    false, // bedWorks - 床是否能工作
                    false, // respawnAnchorWorks - 重生锚是否工作
                    -64, // minY - 最小Y坐标
                    384, // height - 世界高度
                    384, // logicalHeight - 逻辑高度
                    // 使用主世界的燃烧方块标签
                    TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "infiniburn_overworld")),
                    ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"), // effectsLocation - 效果位置
                    0.0f, // ambientLight - 环境光等级
                    new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)
            );

            // 注册维度类型
            context.register(
                    ResourceKey.create(Registries.DIMENSION_TYPE,
                            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "underworld")),
                    underworldDimensionType
            );

            InfinityWay.LOGGER.info("Successfully registered Underworld dimension type");
        } catch (Exception e) {
            InfinityWay.LOGGER.error("Failed to register Underworld dimension type: " + e.getMessage(), e);
            throw new RuntimeException("Dimension type registration failed", e);
        }
    }

    /**
     * 注册维度本身
     */
    private static void bootstrapDimensions(net.minecraft.data.worldgen.BootstrapContext<LevelStem> context) {
        try {
            // 获取必要的注册表
            var biomeRegistry = context.lookup(Registries.BIOME);
            var dimensionTypeRegistry = context.lookup(Registries.DIMENSION_TYPE);

            // 确保生物群系注册表不为空
            if (biomeRegistry == null) {
                throw new IllegalStateException("Biome registry is null during dimension bootstrap!");
            }

            InfinityWay.LOGGER.info("Creating UnderworldBiomeSource with biome registry...");

            // 创建地下世界生物群系源 - 确保传入正确的参数
            BiomeSource biomeSource = new UnderworldBiomeSource(biomeRegistry);
            InfinityWay.LOGGER.info("Created UnderworldBiomeSource successfully");

            // 创建地下世界区块生成器
            ChunkGenerator chunkGenerator = new UnderworldChunkGenerator(biomeSource);
            InfinityWay.LOGGER.info("Created UnderworldChunkGenerator successfully");

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

            InfinityWay.LOGGER.info("Successfully registered Underworld dimension");
        } catch (Exception e) {
            InfinityWay.LOGGER.error("Failed to register Underworld dimension: " + e.getMessage(), e);
            // 抛出异常让问题被发现，而不是静默失败
            throw new RuntimeException("Dimension registration failed", e);
        }
    }
}