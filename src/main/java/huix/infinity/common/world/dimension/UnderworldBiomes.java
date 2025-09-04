package huix.infinity.common.world.dimension;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import huix.infinity.init.InfinityWay;

public class UnderworldBiomes {

    public static final ResourceKey<Biome> UNDERWORLD_WASTELAND = ResourceKey.create(
            Registries.BIOME, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "underworld_wasteland"));

    public static final ResourceKey<Biome> STALAGMITE_FOREST = ResourceKey.create(
            Registries.BIOME, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "stalagmite_forest"));

    public static final ResourceKey<Biome> LUSH_CAVERNS = ResourceKey.create(
            Registries.BIOME, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "lush_caverns"));

    public static final ResourceKey<Biome> DEEP_DARK_REALM = ResourceKey.create(
            Registries.BIOME, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "deep_dark_realm"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        try {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
            HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);

            // 注册地下荒地 - 类似下界荒地的设定
            context.register(UNDERWORLD_WASTELAND, createUnderworldWasteland(placedFeatures, worldCarvers));
            InfinityWay.LOGGER.info("Registered Underworld Wasteland biome");

            // 注册钟乳石森林 - 石质环境
            context.register(STALAGMITE_FOREST, createStalagmiteForest(placedFeatures, worldCarvers));
            InfinityWay.LOGGER.info("Registered Stalagmite Forest biome");

            // 注册繁茂洞穴 - 生机勃勃的环境
            context.register(LUSH_CAVERNS, createLushCaverns(placedFeatures, worldCarvers));
            InfinityWay.LOGGER.info("Registered Lush Caverns biome");

            // 注册深暗领域 - 危险的深层区域
            context.register(DEEP_DARK_REALM, createDeepDarkRealm(placedFeatures, worldCarvers));
            InfinityWay.LOGGER.info("Registered Deep Dark Realm biome");

        } catch (Exception e) {
            InfinityWay.LOGGER.error("Failed to bootstrap Underworld biomes: " + e.getMessage(), e);
            throw e;
        }
    }

    private static Biome createUnderworldWasteland(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        // 类似下界荒地的生物群系设定
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 1, 4));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CAVE_SPIDER, 80, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 60, 1, 3));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.8F)
                .downfall(0.0F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0x3F1A1A)
                        .waterFogColor(0x330808)
                        .fogColor(0x4A3B3B)
                        .skyColor(0x5A4A4A)
                        .ambientLoopSound(SoundEvents.AMBIENT_CAVE)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES))
                        .build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).build())
                .build();
    }

    private static Biome createStalagmiteForest(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SILVERFISH, 80, 1, 3));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CAVE_SPIDER, 60, 1, 2));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BAT, 40, 1, 2));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.5F)
                .downfall(0.0F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0x2A2A3F)
                        .waterFogColor(0x1A1A2E)
                        .fogColor(0x3A3A4A)
                        .skyColor(0x4A4A5A)
                        .ambientLoopSound(SoundEvents.AMBIENT_CAVE)
                        .build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).build())
                .build();
    }

    private static Biome createLushCaverns(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.BAT, 60, 1, 3));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 40, 1, 2));
        spawns.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 20, 1, 2));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7F)
                .downfall(0.8F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0x1F4A3F)
                        .waterFogColor(0x0F2A1F)
                        .fogColor(0x2F5A4F)
                        .skyColor(0x3F6A5F)
                        .ambientLoopSound(SoundEvents.AMBIENT_CAVE)
                        .build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).build())
                .build();
    }

    private static Biome createDeepDarkRealm(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WARDEN, 5, 1, 1));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 80, 1, 3));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CAVE_SPIDER, 100, 1, 4));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.2F)
                .downfall(0.0F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0x0A0A1A)
                        .waterFogColor(0x050510)
                        .fogColor(0x0F0F1F)
                        .skyColor(0x1A1A2A)
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                        .build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).build())
                .build();
    }
}