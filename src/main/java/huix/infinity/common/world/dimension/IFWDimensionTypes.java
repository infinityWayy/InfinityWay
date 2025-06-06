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
            OptionalLong.empty(), // fixedTime - 如果为空则有正常时间循环
                    false, // hasSkyLight - 是否有天空光照
                            true, // hasCeiling - 是否有基岩天花板（如下界）
                            false, // ultraWarm - 是否超热（影响水的蒸发等）
                            true, // natural - 是否为自然维度
                            1.0F, // coordinateScale - 坐标缩放比例 1.0 = 与主世界同步
                            false, // bedWorks - 床是否能工作
                            true, // respawnAnchorWorks - 重生锚是否工作
                            -64, // minY - 最小Y坐标
                            384, // height - 世界高度
                            384, // logicalHeight - 逻辑高度
                            // 使用一个空的TagKey来取消永续燃烧效果
                            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "empty")),
                            ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"), // effectsLocation - 效果位置
                            0.0F, // ambientLight - 环境光等级
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