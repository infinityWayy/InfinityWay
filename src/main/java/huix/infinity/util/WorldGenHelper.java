package huix.infinity.util;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WorldGenHelper {

    public static ResourceKey<PlacedFeature> createKeyToPlace(String key) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, key));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKeyToCong(String key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, key));
    }
}
