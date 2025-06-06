package huix.infinity.common.world.dimension;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.OptionalLong;
import java.util.function.Supplier;

public class IFWDimensionTypes {
    public static final ResourceKey<Level> UNDERWORLD_LEVEL = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "underworld"));

    public static final DimensionType UNDER_WORLD = new DimensionType(
            OptionalLong.of(3000L),
                    false,
                            true,
                            false,
                            false,
                            4.0F,
                            false,
                            true,
                            -32,
                            256,
                            144,
                            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "empty")),
                            ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"),
                            0.0F,
                            new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)
    );

    public static ResourceKey<Level> getNetherPortalDestination(ResourceKey<Level> currentDimension) {
        if (currentDimension == Level.OVERWORLD) {
            // 主世界 -> 地下世界
            return UNDERWORLD_LEVEL;
        } else if (currentDimension == UNDERWORLD_LEVEL) {
            // 地下世界 -> 地狱
            return Level.NETHER;
        } else if (currentDimension == Level.NETHER) {
            // 地狱 -> 地下世界
            return UNDERWORLD_LEVEL;
        }

        // 其他维度保持原有逻辑（地狱 ↔ 主世界）
        return currentDimension == Level.NETHER ? Level.OVERWORLD : Level.NETHER;
    }
}