package huix.infinity.common.world.item.tier;

import huix.infinity.common.world.item.crafting.mite.MITECraftingSystem;

/**
 * 材质制作难度定义
 * 基于MITE研究报告的材质递进体系
 */
public class MaterialCraftingDifficulty {

    // 基础材质制作难度（锭）
    public static final int FLINT_DIFFICULTY = 100;
    public static final int COPPER_DIFFICULTY = 400;
    public static final int SILVER_DIFFICULTY = 400;
    public static final int GOLD_DIFFICULTY = 400;
    public static final int IRON_DIFFICULTY = 800;
    public static final int ANCIENT_METAL_DIFFICULTY = 1600;
    public static final int MITHRIL_DIFFICULTY = 6400;
    public static final int ADAMANTIUM_DIFFICULTY = 25600;
    public static final int OBSIDIAN_DIFFICULTY = 240; // 特殊材质

    // 工具制作难度倍数
    public static final double TOOL_DIFFICULTY_MULTIPLIER = 1.5;
    public static final double ARMOR_DIFFICULTY_MULTIPLIER = 2.0;
    public static final double WEAPON_DIFFICULTY_MULTIPLIER = 1.25;

    // 工作台制作难度倍数
    public static final double WORKBENCH_DIFFICULTY_MULTIPLIER = 3.0;

    /**
     * 获取材质的基础制作难度
     * @param tier 材质
     * @return 基础制作难度
     */
    public static int getBaseMaterialDifficulty(IFWTier tier) {
        if (tier instanceof IFWTiers ifwTier) {
            return switch (ifwTier) {
                case FLINT -> FLINT_DIFFICULTY;
                case COPPER -> COPPER_DIFFICULTY;
                case SILVER -> SILVER_DIFFICULTY;
                case GOLD -> GOLD_DIFFICULTY;
                case IRON -> IRON_DIFFICULTY;
                case ANCIENT_METAL -> ANCIENT_METAL_DIFFICULTY;
                case MITHRIL -> MITHRIL_DIFFICULTY;
                case ADAMANTIUM -> ADAMANTIUM_DIFFICULTY;
                case OBSIDIAN -> OBSIDIAN_DIFFICULTY;
                default -> FLINT_DIFFICULTY;
            };
        }
        return FLINT_DIFFICULTY;
    }

    /**
     * 计算工具的制作难度
     * @param tier 材质等级
     * @param toolType 工具类型
     * @return 制作难度
     */
    public static int getToolCraftingDifficulty(IFWTier tier, ToolType toolType) {
        int baseDifficulty = getBaseMaterialDifficulty(tier);

        return switch (toolType) {
            case SWORD, AXE, PICKAXE, SHOVEL, HOE ->
                    (int) (baseDifficulty * WEAPON_DIFFICULTY_MULTIPLIER);
            case HELMET, CHESTPLATE, LEGGINGS, BOOTS ->
                    (int) (baseDifficulty * ARMOR_DIFFICULTY_MULTIPLIER);
            case WORKBENCH ->
                    (int) (baseDifficulty * WORKBENCH_DIFFICULTY_MULTIPLIER);
            default -> baseDifficulty;
        };
    }

    /**
     * 计算工作台的制作难度
     * @param tier 工作台材质等级
     * @return 制作难度
     */
    public static int getWorkbenchCraftingDifficulty(IFWTier tier) {
        return getToolCraftingDifficulty(tier, ToolType.WORKBENCH);
    }

    /**
     * 获取材质升级路径的总时间成本
     * @param targetTier 目标材质
     * @return 总制作时间（秒）
     */
    public static double getTotalUpgradeTime(IFWTier targetTier) {
        int level = MITETierManager.getMaterialLevel(targetTier);
        double totalTime = 0;

        for (int i = 0; i <= level; i++) {
            IFWTier tier = MITETierManager.getTierByLevel(i);
            if (tier != null) {
                int difficulty = getBaseMaterialDifficulty(tier);
                totalTime += calculateCraftingTimeSeconds(difficulty);
            }
        }

        return totalTime;
    }

    /**
     * 计算制作时间（秒）
     * @param difficulty 制作难度
     * @return 制作时间（秒）
     */
    public static double calculateCraftingTimeSeconds(int difficulty) {
        int ticks = calculateCraftingTicks(difficulty);
        return ticks / 20.0; // 20 ticks = 1 second
    }

    /**
     * 计算制作时间（ticks）
     * 基于MITE公式: crafting_ticks = ((crafting_difficulty - 100) ^ 0.8) + 100
     * @param difficulty 制作难度
     * @return 制作时间（ticks）
     */
    public static int calculateCraftingTicks(int difficulty) {
        if (difficulty <= 100) return 100;
        return (int) (Math.pow(difficulty - 100, 0.8) + 100);
    }

    /**
     * 工具类型枚举
     */
    public enum ToolType {
        SWORD,
        AXE,
        PICKAXE,
        SHOVEL,
        HOE,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        WORKBENCH,
        OTHER
    }
}