package huix.infinity.init.event;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

import java.util.Set;

/**
 * 处理基于高度的生物生成限制
 * 根据MITE规则实现三层生物分布：地表、浅层洞穴、深板岩层
 */
@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class HeightBasedSpawnHandler {

    // 深板岩层专属生物ID（Y=-64到Y=0生成）
    private static final Set<String> DEEPSLATE_LAYER_MOB_IDS = Set.of(
            "ifw:revenant",
            "ifw:demon_spider",
            "ifw:wight",
            "ifw:invisible_stalker",
            "ifw:hellhound",
            "ifw:shadow",
            "ifw:phase_spider",
            "ifw:inferno_creeper"
    );

    // 浅层洞穴生物ID（Y=0到Y=63生成）
    private static final Set<String> SHALLOW_CAVE_MOB_IDS = Set.of(
            "ifw:ghoul",
            "ifw:jelly"
    );

    // 地表生物ID（Y=64以上优先生成）
    private static final Set<String> SURFACE_LAYER_MOB_IDS = Set.of(
            "ifw:chicken",
            "ifw:sheep",
            "ifw:pig",
            "ifw:cow",
            "ifw:wood_spider",
            "ifw:black_widow_spider"
    );

    // 通用生物ID（可在多个层级生成，但有不同权重）
    private static final Set<String> UNIVERSAL_MOB_IDS = Set.of(
            "ifw:spider",
            "ifw:zombie"
    );

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onMobSpawn(FinalizeSpawnEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }

        ServerLevelAccessor levelAccessor = event.getLevel();
        BlockPos pos = mob.blockPosition();
        int y = pos.getY();
        EntityType<?> entityType = mob.getType();
        String entityId = EntityType.getKey(entityType).toString();

        // 深板岩层生物限制：只能在Y=-64到Y=0生成
        if (DEEPSLATE_LAYER_MOB_IDS.contains(entityId)) {
            if (y > 0 || y < -64) {
                InfinityWay.LOGGER.debug("Cancelled deepslate mob {} spawn at Y={} (outside deepslate layer Y=-64 to Y=0)",
                        entityId, y);
                event.setSpawnCancelled(true);
                return;
            }
        }

        // 浅层洞穴生物限制：优先在Y=0到Y=63生成
        if (SHALLOW_CAVE_MOB_IDS.contains(entityId)) {
            if (y < 0) {
                // 在深板岩层减少生成概率
                if (levelAccessor.getRandom().nextFloat() < 0.2f) { // 20% 概率在深层生成
                    InfinityWay.LOGGER.debug("Allowed shallow cave mob {} to spawn at Y={} with reduced chance",
                            entityId, y);
                } else {
                    InfinityWay.LOGGER.debug("Cancelled shallow cave mob {} spawn at Y={} (below shallow cave layer)",
                            entityId, y);
                    event.setSpawnCancelled(true);
                    return;
                }
            } else if (y > 63) {
                // 在地表减少生成概率
                if (levelAccessor.getRandom().nextFloat() < 0.0f) { // 0% 概率在地表生成
                    InfinityWay.LOGGER.debug("Allowed shallow cave mob {} to spawn at Y={} with reduced chance",
                            entityId, y);
                } else {
                    InfinityWay.LOGGER.debug("Cancelled shallow cave mob {} spawn at Y={} (above shallow cave layer)",
                            entityId, y);
                    event.setSpawnCancelled(true);
                    return;
                }
            }
        }

        // 地表生物限制：优先在Y=64以上生成
        if (SURFACE_LAYER_MOB_IDS.contains(entityId)) {
            if (y < 0) {
                // 在深层大幅减少地表生物的生成概率
                if (levelAccessor.getRandom().nextFloat() < 0.05f) { // 5% 概率在深层生成
                    InfinityWay.LOGGER.debug("Allowed surface mob {} to spawn at Y={} with very reduced chance",
                            entityId, y);
                } else {
                    InfinityWay.LOGGER.debug("Cancelled surface mob {} spawn at Y={} (too deep for surface mob)",
                            entityId, y);
                    event.setSpawnCancelled(true);
                    return;
                }
            } else if (y < 64) {
                // 在浅层洞穴减少地表生物生成概率
                if (levelAccessor.getRandom().nextFloat() < 0.3f) { // 30% 概率在浅层生成
                    InfinityWay.LOGGER.debug("Allowed surface mob {} to spawn at Y={} with reduced chance",
                            entityId, y);
                } else {
                    InfinityWay.LOGGER.debug("Cancelled surface mob {} spawn at Y={} (below preferred surface layer)",
                            entityId, y);
                    event.setSpawnCancelled(true);
                    return;
                }
            }
        }

        // 通用生物的高度调整
        if (UNIVERSAL_MOB_IDS.contains(entityId)) {
            // 蜘蛛和僵尸可以在任何层级生成，但根据深度调整概率
            if (y < -32) {
                // 非常深的地方稍微减少通用生物生成
                if (levelAccessor.getRandom().nextFloat() < 0.7f) {
                    InfinityWay.LOGGER.debug("Reduced universal mob {} spawn chance at very deep Y={}",
                            entityId, y);
                } else {
                    event.setSpawnCancelled(true);
                    return;
                }
            }
        }

        // 特殊高度限制
        if (!checkSpecialHeightRestrictions(entityType, entityId, y, levelAccessor)) {
            InfinityWay.LOGGER.debug("Cancelled mob {} spawn due to special height restrictions at Y={}",
                    entityId, y);
            event.setSpawnCancelled(true);
        }
    }

    /**
     * 检查特殊的高度限制
     */
    private static boolean checkSpecialHeightRestrictions(EntityType<?> entityType, String entityId, int y, ServerLevelAccessor levelAccessor) {
        // 末影人限制：不在太高的地方生成
        if (entityType == EntityType.ENDERMAN) {
            return y <= 100;
        }

        // 爬行者限制：在深层更稀少
        if (entityType == EntityType.CREEPER) {
            if (y < -32) {
                return levelAccessor.getRandom().nextFloat() < 0.3f; // 30% 概率在深层生成
            }
        }

        return true; // 默认允许生成
    }

    /**
     * 获取实体类型的理想生成高度范围
     */
    public static HeightRange getIdealSpawnHeight(String entityId) {
        if (DEEPSLATE_LAYER_MOB_IDS.contains(entityId)) {
            return new HeightRange(-64, 0);
        }
        if (SHALLOW_CAVE_MOB_IDS.contains(entityId)) {
            return new HeightRange(0, 63);
        }
        if (SURFACE_LAYER_MOB_IDS.contains(entityId)) {
            return new HeightRange(64, 256);
        }
        if (UNIVERSAL_MOB_IDS.contains(entityId)) {
            return new HeightRange(-64, 256); // 可在任何地方生成
        }
        return new HeightRange(-64, 256); // 默认范围
    }

    /**
     * 高度范围记录类
     */
    public record HeightRange(int minHeight, int maxHeight) {
        public boolean contains(int y) {
            return y >= minHeight && y <= maxHeight;
        }
    }

    /**
     * 检查生物是否为深板岩层生物
     */
    public static boolean isDeepslateLayerMob(String entityId) {
        return DEEPSLATE_LAYER_MOB_IDS.contains(entityId);
    }

    /**
     * 检查生物是否为浅层洞穴生物
     */
    public static boolean isShallowCaveMob(String entityId) {
        return SHALLOW_CAVE_MOB_IDS.contains(entityId);
    }

    /**
     * 检查生物是否为地表生物
     */
    public static boolean isSurfaceLayerMob(String entityId) {
        return SURFACE_LAYER_MOB_IDS.contains(entityId);
    }

    /**
     * 检查生物是否为通用生物（可在多层级生成）
     */
    public static boolean isUniversalMob(String entityId) {
        return UNIVERSAL_MOB_IDS.contains(entityId);
    }

    /**
     * 获取生物在指定高度的生成概率修饰符
     */
    public static float getSpawnChanceModifier(String entityId, int y) {
        if (DEEPSLATE_LAYER_MOB_IDS.contains(entityId)) {
            return (y >= -64 && y <= 0) ? 1.0f : 0.0f;
        }
        if (SHALLOW_CAVE_MOB_IDS.contains(entityId)) {
            if (y >= 0 && y <= 63) return 1.0f;
            if (y < 0) return 0.2f;
            return 0.0f;
        }
        if (SURFACE_LAYER_MOB_IDS.contains(entityId)) {
            if (y >= 64) return 1.0f;
            if (y >= 0) return 0.3f;
            return 0.05f;
        }
        if (UNIVERSAL_MOB_IDS.contains(entityId)) {
            return y < -32 ? 0.7f : 1.0f;
        }
        return 1.0f;
    }
}