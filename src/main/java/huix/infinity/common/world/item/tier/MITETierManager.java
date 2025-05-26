package huix.infinity.common.world.item.tier;

import huix.infinity.common.world.item.crafting.mite.MITECraftingSystem;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * MITE材质管理器
 * 管理九级材质等级系统和递进关系
 */
public class MITETierManager {

    // 材质等级到材质的映射（按照MITE研究报告的递进体系）
    private static final IFWTiers[] TIER_BY_LEVEL = {
            IFWTiers.FLINT,         // 等级 0 - 基础材质
            IFWTiers.COPPER,        // 等级 1
            IFWTiers.SILVER,        // 等级 1 (同等级)
            IFWTiers.GOLD,          // 等级 1 (同等级)
            IFWTiers.IRON,          // 等级 2
            IFWTiers.ANCIENT_METAL, // 等级 3
            IFWTiers.MITHRIL,       // 等级 4
            IFWTiers.ADAMANTIUM     // 等级 5
    };

    /**
     * 根据等级获取材质
     * @param level 材质等级
     * @return 对应的材质，如果等级无效则返回null
     */
    @Nullable
    public static IFWTier getTierByLevel(int level) {
        // 使用repairLevel()来匹配等级
        return Arrays.stream(TIER_BY_LEVEL)
                .filter(tier -> tier.repairLevel() == level)
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取材质的等级
     * @param tier 材质
     * @return 材质等级
     */
    public static int getMaterialLevel(IFWTier tier) {
        if (tier instanceof IFWTiers ifwTier) {
            return ifwTier.repairLevel();
        }
        return 0;
    }

    /**
     * 根据难度获取要求的材质等级名称
     * @param difficulty 制作难度
     * @return 要求的材质等级名称
     */
    public static String getRequiredTierForDifficulty(int difficulty) {
        if (difficulty <= 400) {
            return "flint"; // 燧石级别
        } else if (difficulty <= 800) {
            return "copper"; // 铜级别（包括银、金）
        } else if (difficulty <= 1600) {
            return "iron"; // 铁级别
        } else if (difficulty <= 3200) {
            return "ancient_metal"; // 远古金属级别
        } else if (difficulty <= 12800) {
            return "mithril"; // 秘银级别
        } else {
            return "adamantium"; // 艾德曼级别
        }
    }

    /**
     * 根据难度获取要求的材质
     * @param difficulty 制作难度
     * @return 要求的材质
     */
    @Nullable
    public static IFWTier getRequiredTierObjectForDifficulty(int difficulty) {
        String tierName = getRequiredTierForDifficulty(difficulty);
        return switch (tierName) {
            case "flint" -> IFWTiers.FLINT;
            case "copper" -> IFWTiers.COPPER;
            case "iron" -> IFWTiers.IRON;
            case "ancient_metal" -> IFWTiers.ANCIENT_METAL;
            case "mithril" -> IFWTiers.MITHRIL;
            case "adamantium" -> IFWTiers.ADAMANTIUM;
            default -> IFWTiers.FLINT;
        };
    }

    /**
     * 检查工作台是否满足材质等级要求
     * @param workbenchTier 工作台材质
     * @param requiredTier 要求的材质
     * @return 是否满足要求
     */
    public static boolean canCraftWithWorkbench(IFWTier workbenchTier, IFWTier requiredTier) {
        int workbenchLevel = getMaterialLevel(workbenchTier);
        int requiredLevel = getMaterialLevel(requiredTier);
        return workbenchLevel >= requiredLevel;
    }

    /**
     * 获取材质的显示名称
     * @param tier 材质
     * @return 显示名称
     */
    public static String getTierDisplayName(IFWTier tier) {
        if (tier instanceof IFWTiers ifwTier) {
            return switch (ifwTier) {
                case FLINT -> "燧石";
                case COPPER -> "铜";
                case SILVER -> "银";
                case GOLD -> "金";
                case IRON -> "铁";
                case ANCIENT_METAL -> "远古金属";
                case MITHRIL -> "秘银";
                case ADAMANTIUM -> "艾德曼";
                case OBSIDIAN -> "黑曜石";
                default -> tier.toString();
            };
        }
        return tier.toString();
    }

    /**
     * 获取所有可用的材质，按等级排序
     * @return 按等级排序的材质数组
     */
    public static IFWTiers[] getAllTiersSorted() {
        return Arrays.stream(TIER_BY_LEVEL)
                .sorted(Comparator.comparingInt(IFWTiers::repairLevel))
                .toArray(IFWTiers[]::new);
    }

    /**
     * 检查材质是否是基础材质（可以直接获得的）
     * @param tier 材质
     * @return 是否是基础材质
     */
    public static boolean isBasicTier(IFWTier tier) {
        return tier == IFWTiers.FLINT || tier == IFWTiers.WOOD || tier == IFWTiers.OBSIDIAN;
    }

    /**
     * 获取材质的下一级材质
     * @param tier 当前材质
     * @return 下一级材质，如果已经是最高级则返回null
     */
    @Nullable
    public static IFWTier getNextTier(IFWTier tier) {
        int currentLevel = getMaterialLevel(tier);
        return getTierByLevel(currentLevel + 1);
    }

    /**
     * 获取材质的上一级材质
     * @param tier 当前材质
     * @return 上一级材质，如果已经是最低级则返回null
     */
    @Nullable
    public static IFWTier getPreviousTier(IFWTier tier) {
        int currentLevel = getMaterialLevel(tier);
        if (currentLevel <= 0) return null;
        return getTierByLevel(currentLevel - 1);
    }
}