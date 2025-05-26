package huix.infinity.common.world.item.tier;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

/**
 * 制作难度计算器
 * 基于MITE系统计算配方的制作难度和时间
 */
public class CraftingDifficultyCalculator {

    /**
     * 计算配方的制作难度
     * @param craftingInput 制作输入
     * @return 制作难度值
     */
    public static int calculateRecipeDifficulty(CraftingInput craftingInput) {
        int totalDifficulty = 0;
        int itemCount = 0;

        // 遍历制作网格中的所有物品
        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack stack = craftingInput.getItem(i);
            if (!stack.isEmpty()) {
                int itemDifficulty = getItemDifficulty(stack);
                totalDifficulty += itemDifficulty * stack.getCount();
                itemCount++;
            }
        }

        // 如果没有物品，返回0
        if (itemCount == 0) {
            return 0;
        }

        // 基础难度计算：平均物品难度 + 复杂性加成
        int baseDifficulty = totalDifficulty / Math.max(itemCount, 1);

        // 复杂性加成：更多种类的物品增加难度
        int complexityBonus = itemCount * 10;

        return Math.max(50, baseDifficulty + complexityBonus); // 最低难度50
    }

    /**
     * 根据制作难度计算制作时间（ticks）
     * 使用MITE公式: crafting_ticks = ((crafting_difficulty - 100) ^ 0.8) + 100
     * @param difficulty 制作难度
     * @return 制作时间（ticks）
     */
    public static int calculateCraftingTicks(int difficulty) {
        return MaterialCraftingDifficulty.calculateCraftingTicks(difficulty);
    }

    /**
     * 根据制作难度计算制作时间（秒）
     * @param difficulty 制作难度
     * @return 制作时间（秒）
     */
    public static double calculateCraftingTimeSeconds(int difficulty) {
        return MaterialCraftingDifficulty.calculateCraftingTimeSeconds(difficulty);
    }

    /**
     * 获取单个物品的制作难度
     * @param stack 物品堆叠
     * @return 物品的制作难度
     */
    public static int getItemDifficulty(ItemStack stack) {
        if (stack.isEmpty()) return 0;

        // 检查是否是IFW的分层物品
        if (stack.getItem() instanceof huix.infinity.common.world.item.IFWTieredItem tieredItem) {
            IFWTier tier = tieredItem.ifwTier();
            return MaterialCraftingDifficulty.getBaseMaterialDifficulty(tier);
        }

        // 对于原版物品，根据物品类型返回基础难度
        return getVanillaItemDifficulty(stack);
    }

    /**
     * 获取物品的材质等级名称
     * @param stack 物品堆叠
     * @return 材质等级名称，如果不是分层物品则返回null
     */
    public static String getItemTier(ItemStack stack) {
        if (stack.isEmpty()) return null;

        // 检查是否是IFW的分层物品
        if (stack.getItem() instanceof huix.infinity.common.world.item.IFWTieredItem tieredItem) {
            IFWTier tier = tieredItem.ifwTier();
            return MITETierManager.getTierDisplayName(tier);
        }

        // 对于原版物品，尝试推断材质
        return getVanillaItemTier(stack);
    }

    /**
     * 获取物品的材质对象
     * @param stack 物品堆叠
     * @return 材质对象，如果不是分层物品则返回null
     */
    public static IFWTier getItemTierObject(ItemStack stack) {
        if (stack.isEmpty()) return null;

        // 检查是否是IFW的分层物品
        if (stack.getItem() instanceof huix.infinity.common.world.item.IFWTieredItem tieredItem) {
            return tieredItem.ifwTier();
        }

        // 对于原版物品，尝试映射到对应的IFW材质
        return getVanillaItemTierObject(stack);
    }

    /**
     * 获取原版物品的制作难度
     * @param stack 物品堆叠
     * @return 制作难度
     */
    private static int getVanillaItemDifficulty(ItemStack stack) {
        String itemName = stack.getItem().toString().toLowerCase();

        // 基础材料
        if (itemName.contains("planks") || itemName.contains("stick") ||
                itemName.contains("wood") || itemName.contains("log")) {
            return 50;
        }

        // 石材
        if (itemName.contains("stone") || itemName.contains("cobblestone") ||
                itemName.contains("granite") || itemName.contains("diorite") ||
                itemName.contains("andesite")) {
            return 75;
        }

        // 燧石
        if (itemName.contains("flint")) {
            return MaterialCraftingDifficulty.FLINT_DIFFICULTY;
        }

        // 铜相关
        if (itemName.contains("copper")) {
            return MaterialCraftingDifficulty.COPPER_DIFFICULTY;
        }

        // 铁相关
        if (itemName.contains("iron")) {
            return MaterialCraftingDifficulty.IRON_DIFFICULTY;
        }

        // 金相关
        if (itemName.contains("gold")) {
            return MaterialCraftingDifficulty.GOLD_DIFFICULTY;
        }

        // 钻石相关
        if (itemName.contains("diamond")) {
            return 1200;
        }

        // 绿宝石相关
        if (itemName.contains("emerald")) {
            return 1000;
        }

        // 下界相关
        if (itemName.contains("netherite")) {
            return 2000;
        }

        // 下界石英
        if (itemName.contains("quartz")) {
            return 600;
        }

        // 黑曜石
        if (itemName.contains("obsidian")) {
            return MaterialCraftingDifficulty.OBSIDIAN_DIFFICULTY;
        }

        // 红石相关
        if (itemName.contains("redstone")) {
            return 200;
        }

        // 青金石相关
        if (itemName.contains("lapis")) {
            return 300;
        }

        // 默认难度
        return 100;
    }

    /**
     * 获取原版物品的材质等级名称
     * @param stack 物品堆叠
     * @return 材质等级名称
     */
    private static String getVanillaItemTier(ItemStack stack) {
        String itemName = stack.getItem().toString().toLowerCase();

        if (itemName.contains("wood") || itemName.contains("planks") || itemName.contains("stick")) {
            return "木头";
        }
        if (itemName.contains("stone") || itemName.contains("cobblestone")) {
            return "石头";
        }
        if (itemName.contains("flint")) {
            return "燧石";
        }
        if (itemName.contains("copper")) {
            return "铜";
        }
        if (itemName.contains("iron")) {
            return "铁";
        }
        if (itemName.contains("gold")) {
            return "金";
        }
        if (itemName.contains("diamond")) {
            return "钻石";
        }
        if (itemName.contains("netherite")) {
            return "下界合金";
        }
        if (itemName.contains("obsidian")) {
            return "黑曜石";
        }

        return null;
    }

    /**
     * 获取原版物品对应的IFW材质对象
     * @param stack 物品堆叠
     * @return IFW材质对象
     */
    private static IFWTier getVanillaItemTierObject(ItemStack stack) {
        String itemName = stack.getItem().toString().toLowerCase();

        if (itemName.contains("flint")) {
            return IFWTiers.FLINT;
        }
        if (itemName.contains("copper")) {
            return IFWTiers.COPPER;
        }
        if (itemName.contains("iron")) {
            return IFWTiers.IRON;
        }
        if (itemName.contains("gold")) {
            return IFWTiers.GOLD;
        }
        if (itemName.contains("obsidian")) {
            return IFWTiers.OBSIDIAN;
        }

        return null;
    }

    /**
     * 检查配方是否需要特定的工作台等级
     * @param difficulty 制作难度
     * @return 需要的工作台等级
     */
    public static String getRequiredWorkbenchTier(int difficulty) {
        return MITETierManager.getRequiredTierForDifficulty(difficulty);
    }

    /**
     * 计算批量制作的效率加成
     * @param baseTime 基础制作时间
     * @param batchSize 批量大小
     * @return 优化后的制作时间
     */
    public static double calculateBatchEfficiency(double baseTime, int batchSize) {
        if (batchSize <= 1) return baseTime;

        // 批量制作效率：每额外的物品减少10%的时间成本
        double efficiency = 1.0 - Math.min(0.8, (batchSize - 1) * 0.1);
        return baseTime * efficiency;
    }

    /**
     * 获取配方的预估经验奖励
     * @param difficulty 制作难度
     * @return 经验值
     */
    public static int getExperienceReward(int difficulty) {
        return Math.max(1, difficulty / 100);
    }

    /**
     * 获取难度等级描述
     * @param difficulty 制作难度
     * @return 难度等级描述
     */
    public static String getDifficultyLevel(int difficulty) {
        if (difficulty <= 100) return "简单";
        if (difficulty <= 400) return "普通";
        if (difficulty <= 800) return "困难";
        if (difficulty <= 1600) return "极难";
        if (difficulty <= 6400) return "专家";
        return "大师";
    }

    /**
     * 检查物品是否是制作材料
     * @param stack 物品堆叠
     * @return 是否是制作材料
     */
    public static boolean isCraftingMaterial(ItemStack stack) {
        return getItemDifficulty(stack) > 0;
    }

    /**
     * 计算物品在配方中的重要性权重
     * @param stack 物品堆叠
     * @param totalItems 配方中的总物品数
     * @return 重要性权重 (0.0 - 1.0)
     */
    public static double getItemWeight(ItemStack stack, int totalItems) {
        int difficulty = getItemDifficulty(stack);
        if (difficulty == 0 || totalItems == 0) return 0.0;

        // 基于难度和数量计算权重
        double baseWeight = (double) difficulty / 1000.0; // 标准化到合理范围
        double countWeight = (double) stack.getCount() / totalItems;

        return Math.min(1.0, baseWeight * countWeight);
    }
}