package huix.infinity.common.worldgen;

import huix.infinity.common.core.tag.IFWEntityTypeTags;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.datagen.worldgen.IFWBiomeModifiers.ModifySpawnsBiomeModifier;
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
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import huix.infinity.common.worldgen.HeightBasedAddSpawnsBiomeModifier;


import java.util.List;
import java.util.Map;
import java.util.Set;

public class IFWFeatures {

    // 主世界修饰器
    public static final ResourceKey<BiomeModifier> ADD_OVERWORLD_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_overworld_features"));
    public static final ResourceKey<BiomeModifier> ADD_OVERWORLD_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_overworld_spawns"));
    public static final ResourceKey<BiomeModifier> REMOVE_OVERWORLD_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_remove_overworld_features"));
    public static final ResourceKey<BiomeModifier> REMOVE_OVERWORLD_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_remove_overworld_spawns"));

    // 自定义生物生成修饰器
    public static final ResourceKey<BiomeModifier> MODIFY_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_modify_spawns"));

    // 特定生物群系生物修饰器
    public static final ResourceKey<BiomeModifier> ADD_FOREST_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_forest_spawns"));
    public static final ResourceKey<BiomeModifier> ADD_JUNGLE_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_jungle_spawns"));

    // 地下世界维度修饰器
    /*
    public static final ResourceKey<BiomeModifier> ADD_UNDERWORLD_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_underworld_features"));
    public static final ResourceKey<BiomeModifier> ADD_UNDERWORLD_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_underworld_spawns"));
    */

    // 浅层洞穴修饰器
    public static final ResourceKey<BiomeModifier> ADD_CAVE_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_cave_spawns"));

    // 深板岩层修饰器(Y=0以下，深板岩层)
    public static final ResourceKey<BiomeModifier> ADD_DEEPSLATE_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_deepslate_spawns"));

    // 蠹虫方块生成
    public static final ResourceKey<BiomeModifier> ADD_SILVERFISH_BLOCKS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_silverfish_blocks"));

    // 地下世界菌丝体生成
    /*
    public static final ResourceKey<BiomeModifier> ADD_MYCELIUM_GENERATION = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "ifw_add_mycelium_generation"));
    */

    // 蠹虫特征定义
    public static final ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> SILVERFISH_GENERATION =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "silverfish_generation"));
    public static final ResourceKey<PlacedFeature> SILVERFISH_PLACEMENT =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "silverfish_placement"));

    // 地下世界菌丝体特征
    /*
    public static final ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> UNDERWORLD_MYCELIUM =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "underworld_mycelium"));
    public static final ResourceKey<PlacedFeature> UNDERWORLD_MYCELIUM_PLACEMENT =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "underworld_mycelium_placement"));
    */

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, context -> {
                // 银矿在主世界生成
                FeatureUtils.register(context, IFWOreFeatures.ore_silver, Feature.ORE, IFWOreFeatures.ore_silver_cong);

                // 秘银和艾德曼只在地下世界生成 - 暂时注释
                // FeatureUtils.register(context, IFWOreFeatures.ore_mithril, Feature.ORE, IFWOreFeatures.mithril_ore_cong);
                // FeatureUtils.register(context, IFWOreFeatures.ore_adamantium, Feature.ORE, IFWOreFeatures.adamantium_ore_cong);

                // 蠹虫方块生成特征 - 用于主世界山地
                FeatureUtils.register(context, SILVERFISH_GENERATION, Feature.ORE,
                        new OreConfiguration(
                                List.of(
                                        OreConfiguration.target(new BlockMatchTest(Blocks.STONE), Blocks.INFESTED_STONE.defaultBlockState()),
                                        OreConfiguration.target(new BlockMatchTest(Blocks.COBBLESTONE), Blocks.INFESTED_COBBLESTONE.defaultBlockState()),
                                        OreConfiguration.target(new BlockMatchTest(Blocks.STONE_BRICKS), Blocks.INFESTED_STONE_BRICKS.defaultBlockState())
                                ),
                                9 // 矿脉大小
                        )
                );

                // 地下世界菌丝体生成特征
                /*
                FeatureUtils.register(context, UNDERWORLD_MYCELIUM, Feature.ORE,
                        new OreConfiguration(
                                List.of(
                                        OreConfiguration.target(new BlockMatchTest(Blocks.STONE), Blocks.MYCELIUM.defaultBlockState()),
                                        OreConfiguration.target(new BlockMatchTest(Blocks.COBBLESTONE), Blocks.MYCELIUM.defaultBlockState())
                                ),
                                5 // 菌丝体补丁大小
                        )
                );
                */
            })

            .add(Registries.PLACED_FEATURE, context -> {
                // 银矿放置
                context.register(IFWOrePlacements.ore_silver, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(IFWOreFeatures.ore_silver),
                        OrePlacements.commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.absolute(112)))));

                // 秘银和艾德曼放置
                /*
                context.register(IFWOrePlacements.ore_mithril, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(IFWOreFeatures.ore_mithril),
                        OrePlacements.rareOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
                context.register(IFWOrePlacements.ore_adamantium, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(IFWOreFeatures.ore_adamantium),
                        OrePlacements.rareOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
                */

                // 蠹虫方块放置 - 主世界山地生物群系
                context.register(SILVERFISH_PLACEMENT, new PlacedFeature(
                        context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(SILVERFISH_GENERATION),
                        List.of(
                                CountPlacement.of(UniformInt.of(2, 6)), // 每区块2-6次尝试
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)),
                                BiomeFilter.biome()
                        )
                ));

                // 地下世界菌丝体放置 - 注释掉
                /*
                context.register(UNDERWORLD_MYCELIUM_PLACEMENT, new PlacedFeature(
                        context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(UNDERWORLD_MYCELIUM),
                        List.of(
                                CountPlacement.of(UniformInt.of(1, 3)), // 每区块1-3次尝试
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(24), VerticalAnchor.absolute(96)), // Y=24-96 模拟MITE
                                BiomeFilter.biome()
                        )
                ));
                */
            })

            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);
                HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);
                HolderGetter<EntityType<?>> entities = bootstrap.lookup(Registries.ENTITY_TYPE);

                // ===== 主世界配置 =====

                // 主世界银矿
                bootstrap.register(ADD_OVERWORLD_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(IFWOrePlacements.ore_silver)
                        ),
                        GenerationStep.Decoration.UNDERGROUND_ORES));

                // 主世界蠹虫方块生成 - 仅在山地生物群系
                bootstrap.register(ADD_SILVERFISH_BLOCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_MOUNTAIN), // 仅在山地生成，模拟MITE
                        HolderSet.direct(placedFeatures.getOrThrow(SILVERFISH_PLACEMENT)),
                        GenerationStep.Decoration.UNDERGROUND_ORES));

                // 主世界地表生物
                bootstrap.register(ADD_OVERWORLD_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        List.of(
                                // 基础动物
                                new MobSpawnSettings.SpawnerData(IFWEntityType.CHICKEN.get(), 10, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.SHEEP.get(), 10, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.PIG.get(), 10, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.COW.get(), 10, 1, 1),

                                // 基础怪物
                                new MobSpawnSettings.SpawnerData(IFWEntityType.SPIDER.get(), 80, 1, 2),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.ZOMBIE.get(), 100, 1, 4),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.SKELETON.get(), 100, 1, 4),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.SLIME.get(), 100, 1, 4),
                                new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4),
                                new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 1, 2)
                        )
                ));

                // 自定义其他生物生成
                bootstrap.register(MODIFY_SPAWNS, new ModifySpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        Map.of(
                                EntityType.getKey(EntityType.SALMON), new MobSpawnSettings.SpawnerData(
                                        EntityType.SALMON, 15, 1, 2),
                                EntityType.getKey(EntityType.COD), new MobSpawnSettings.SpawnerData(
                                        EntityType.COD, 15, 1, 2)
                        )
                ));

                // 森林群系生物
                bootstrap.register(ADD_FOREST_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_FOREST),
                        List.of(
                                new MobSpawnSettings.SpawnerData(IFWEntityType.WOOD_SPIDER.get(), 20, 1, 1)
                        )
                ));

                // 丛林群系生物
                bootstrap.register(ADD_JUNGLE_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_JUNGLE),
                        List.of(
                                new MobSpawnSettings.SpawnerData(IFWEntityType.BLACK_WIDOW_SPIDER.get(), 25, 1, 1)
                        )
                ));

//                // 浅层洞穴生物
//                bootstrap.register(ADD_CAVE_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
//                        biomes.getOrThrow(Tags.Biomes.IS_CAVE),
//                        List.of(
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.GHOUL.get(), 5, 1, 1),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.JELLY.get(), 10, 1, 2)
//
//                        )
//                ));

                // 深板岩层生物（Y=0以下）
                bootstrap.register(ADD_DEEPSLATE_SPAWNS, new HeightBasedAddSpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        List.of(
                                // 基础深层怪物
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.SKELETON.get(), 100, 1, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.SPIDER.get(), 80, 1, 2),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.ZOMBIE.get(), 100, 1, 4),
                                new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4),
                                new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 1, 2),

                                // 特殊怪物
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.GHOUL.get(), 10, 1, 1),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.WIGHT.get(), 10, 1, 1),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.INVISIBLE_STALKER.get(), 10, 1, 1),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.HELLHOUND.get(), 10, 1, 2),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.SHADOW.get(), 10, 1, 1),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.REVENANT.get(), 10, 1, 1),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.EARTH_ELEMENTAL.get(), 10, 1, 1),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.CLAY_GOLEM.get(), 50, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.DEMON_SPIDER.get(), 10, 1, 1),

                                // 软泥类生物
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.JELLY.get(), 30, 1, 4),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.BLOB.get(), 30, 1, 4),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.OOZE.get(), 20, 1, 4),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.PUDDING.get(), 30, 1, 4),

                                // 高级怪物
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.BONE_LORD.get(), 5, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.PHASE_SPIDER.get(), 5, 1, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.INFERNO_CREEPER.get(), 30, 1, 1)

                                // 深层洞穴飞行生物
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.VAMPIRE_BAT.get(), 20, 6, 8),
//                                new MobSpawnSettings.SpawnerData(IFWEntityType.NIGHTWING.get(), 4, 1, 4),
                        ),
                        -64, 0 // Y=-64到Y=0 (深板岩层)
                ));

                // ===== 地下世界维度配置 - 全部注释掉 =====
                /*
                // 地下世界特征生成
                bootstrap.register(ADD_UNDERWORLD_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_NETHER), // 临时使用下界标签，后续替换为地下世界
                        HolderSet.direct(
                                placedFeatures.getOrThrow(UNDERWORLD_MYCELIUM_PLACEMENT) // 菌丝体生成
                        ),
                        GenerationStep.Decoration.VEGETAL_DECORATION));

                // 地下世界生物生成
                bootstrap.register(ADD_UNDERWORLD_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_NETHER), // 临时使用下界标签
                        List.of(
                                // 地下世界主要生物 - 与MITE完全一致的权重
                                new MobSpawnSettings.SpawnerData(IFWEntityType.CAVE_SPIDER.get(), 40, 1, 2),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.LONGDEAD.get(), 40, 1, 2),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.ANCIENT_BONE_LORD.get(), 5, 1, 1),

                                // 地下世界环境生物
                                new MobSpawnSettings.SpawnerData(IFWEntityType.OOZE.get(), 30, 1, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.BLOB.get(), 30, 1, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.JELLY.get(), 30, 1, 4),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.PUDDING.get(), 30, 1, 4),

                                // 地下世界精英生物
                                new MobSpawnSettings.SpawnerData(IFWEntityType.WIGHT.get(), 10, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.INVISIBLE_STALKER.get(), 10, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.HELLHOUND.get(), 10, 1, 2),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.SHADOW.get(), 10, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.EARTH_ELEMENTAL.get(), 10, 1, 1),

                                // 地下世界稀有生物
                                new MobSpawnSettings.SpawnerData(IFWEntityType.DEMON_SPIDER.get(), 10, 1, 1),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.PHASE_SPIDER.get(), 5, 1, 4), // 在地下世界更常见一些

                                // 地下世界洞穴生物
                                new MobSpawnSettings.SpawnerData(IFWEntityType.VAMPIRE_BAT.get(), 20, 8, 8),
                                new MobSpawnSettings.SpawnerData(IFWEntityType.NIGHTWING.get(), 4, 1, 4)
                        )
                ));

                // 地下世界菌丝体生成
                bootstrap.register(ADD_MYCELIUM_GENERATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_NETHER), // 临时使用下界标签
                        HolderSet.direct(placedFeatures.getOrThrow(UNDERWORLD_MYCELIUM_PLACEMENT)),
                        GenerationStep.Decoration.VEGETAL_DECORATION));
                */

                // ===== 移除配置 =====

                // 移除主世界不需要的特征
                bootstrap.register(REMOVE_OVERWORLD_FEATURES, new BiomeModifiers.RemoveFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND),
                                placedFeatures.getOrThrow(VegetationPlacements.PATCH_MELON),
                                placedFeatures.getOrThrow(VegetationPlacements.PATCH_MELON_SPARSE)
                        ),
                        Set.of(GenerationStep.Decoration.LAKES, GenerationStep.Decoration.VEGETAL_DECORATION)));

                // 移除原版生物
                bootstrap.register(REMOVE_OVERWORLD_SPAWNS, new BiomeModifiers.RemoveSpawnsBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                        entities.getOrThrow(IFWEntityTypeTags.REPLACE)
                ));
            });

    /**
     * MITE环境系统
     * 地下世界环境参数：温度1.0F，降雨量0.0F，禁用降雨 - 注释掉地下世界相关
     */
    public static class EnvironmentSystem {

        // 地下世界环境常量 - 注释掉
        /*
        public static final float UNDERWORLD_TEMPERATURE = 1.0F;
        public static final float UNDERWORLD_RAINFALL = 0.0F;
        public static final boolean UNDERWORLD_ENABLE_RAIN = false;
        */

        /**
         * 检查温度是否在有效范围内
         * MITE规则：避免0.1-0.2范围内的温度值（会导致雪的问题）
         */
        public static boolean isValidTemperature(float temperature) {
            if (temperature > 0.1F && temperature < 0.2F) {
                InfinityWay.LOGGER.warn("Temperature {} is in invalid range 0.1-0.2, this may cause snow issues!", temperature);
                return false;
            }
            return true;
        }

        /**
         * 检查生物群系是否为冰冻环境
         */
        public static boolean isFreezing(float temperature) {
            return temperature <= 0.15F;
        }

        /**
         * 检查生物群系是否为高湿度环境
         */
        public static boolean isHighHumidity(float rainfall) {
            return rainfall > 0.85F;
        }

        /**
         * 根据环境条件调整生成概率
         */
        public static float getSpawningChance(float temperature) {
            return isFreezing(temperature) ? 0.05F : 0.1F;
        }

        /**
         * 检查是否可以产生闪电
         */
        public static boolean canSpawnLightningBolt(boolean enableSnow, boolean enableRain, boolean isBloodMoon) {
            return enableSnow ? false : enableRain || isBloodMoon;
        }

        // 地下世界环境检查 - 注释掉
        /*
        public static boolean isUnderworldEnvironment(float temperature, float rainfall) {
            return Math.abs(temperature - UNDERWORLD_TEMPERATURE) < 0.01F &&
                    Math.abs(rainfall - UNDERWORLD_RAINFALL) < 0.01F;
        }
        */
    }

    /**
     * 蠹虫方块生成工具类 - 模拟MITE的generateSilverfishBlocks
     * 仅在主世界山地生物群系生成
     */
    public static class SilverfishBlockGenerator {

        public static int calculateVeinSize(int averageHeight) {
            return Math.max((averageHeight - 512) / 16 / 8, 2);
        }

        public static int calculateAttempts(int veinSize) {
            return Math.max(veinSize - 1, 1);
        }
    }

    /**
     * 地下世界菌丝体生成工具类 - 注释掉
     */
    /*
    public static class UnderworldMyceliumGenerator {

        // 菌丝体生成参数
        public static final int MIN_Y = 24;
        public static final int MAX_Y = 96;
        public static final int MUSHROOM_CHANCE = 16; // 1/16概率生成蘑菇

        public static int calculateMyceliumHeight(int baseY, boolean useExtendedRange) {
            return baseY + (useExtendedRange ? 72 : 16);
        }
    }
    */
}