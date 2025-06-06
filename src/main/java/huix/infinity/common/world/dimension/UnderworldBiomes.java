package huix.infinity.common.world.dimension;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import huix.infinity.init.InfinityWay;

import java.awt.*;

public class UnderworldBiomes {
    public static final ResourceKey<Biome> UNDER_WORLD = ResourceKey.create(
            Registries.BIOME, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "under_world"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        try {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
            HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);

            context.register(UNDER_WORLD, createUnderworld(placedFeatures, worldCarvers));
        } catch (Exception e) {
            throw e;
        }
    }

    public static Biome createUnderworld(HolderGetter<PlacedFeature> placedFeatureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder biomeGenSettings = new BiomeGenerationSettings.Builder(placedFeatureGetter,carverGetter);
        addDefaultCarvers(biomeGenSettings);

        int color = new Color(100, 100,120).getRGB();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.7F)
                .downfall(0.1F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(color).waterFogColor(color)
                        .fogColor(color).skyColor(color)
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                .generationSettings(biomeGenSettings.build())
                .build();
    }

    private static void addDefaultCarvers(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR , Carvers.NETHER_CAVE);
        builder.addCarver(GenerationStep.Carving.AIR , Carvers.CAVE);
        builder.addCarver(GenerationStep.Carving.AIR , Carvers.CANYON);
    }
}