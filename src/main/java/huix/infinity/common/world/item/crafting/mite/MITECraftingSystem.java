package huix.infinity.common.world.item.crafting.mite;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * MITE合成系统核心管理器
 * 负责管理基于时间的合成系统、难度计算和材质递进验证
 */
public class MITECraftingSystem {

    /**
     * 获取配方的制作难度
     * @param recipe MITE配方
     * @return 制作难度值
     */
    public static int getCraftingDifficulty(MITERecipe recipe) {
        return recipe.getCraftingDifficulty();
    }

    /**
     * 计算制作时间（以ticks为单位）
     * 核心公式: crafting_ticks = ((crafting_difficulty - 100) ^ 0.8) + 100
     * @param difficulty 制作难度
     * @return 制作时间（ticks）
     */
    public static int calculateCraftingTicks(int difficulty) {
        if (difficulty <= 100) {
            return 100;
        }
        return (int) (Math.pow(difficulty - 100, 0.8) + 100);
    }

    /**
     * 计算制作时间（以秒为单位）
     * @param difficulty 制作难度
     * @return 制作时间（秒）
     */
    public static double calculateCraftingTimeSeconds(int difficulty) {
        return calculateCraftingTicks(difficulty) / 20.0;
    }

    /**
     * 验证工作台是否满足配方要求
     * @param workbenchTier 工作台材质等级
     * @param recipeTier 配方要求的材质等级
     * @return 是否满足要求
     */
    public static boolean isWorkbenchSuitable(IFWTier workbenchTier, IFWTier recipeTier) {
        return getMaterialLevel(workbenchTier) >= getMaterialLevel(recipeTier);
    }

    /**
     * 获取材质等级（用于比较）
     * @param tier 材质等级
     * @return 材质等级数值
     */
    public static int getMaterialLevel(IFWTier tier) {
        if (tier == IFWTiers.FLINT) return 1;
        if (tier == IFWTiers.COPPER) return 2;
        if (tier == IFWTiers.SILVER) return 3;
        if (tier == IFWTiers.GOLD) return 4;
        if (tier == IFWTiers.IRON) return 5;
        if (tier == IFWTiers.ANCIENT_METAL) return 6;
        if (tier == IFWTiers.MITHRIL) return 7;
        if (tier == IFWTiers.ADAMANTIUM) return 8;
        if (tier == IFWTiers.OBSIDIAN) return 9; // 特殊材质
        return 0;
    }

    /**
     * 获取材质的基础制作难度
     * @param tier 材质等级
     * @return 基础制作难度
     */
    public static int getBaseMaterialDifficulty(IFWTier tier) {
        return switch (getMaterialLevel(tier)) {
            case 1 -> 100; // 燧石
            case 2 -> 400; // 铜
            case 3 -> 400; // 银
            case 4 -> 400; // 金
            case 5 -> 800; // 铁
            case 6 -> 1600; // 远古金属
            case 7 -> 6400; // 秘银
            case 8 -> 25600; // 艾德曼
            case 9 -> 240; // 黑曜石（特殊）
            default -> 50;
        };
    }

    /**
     * 查找适合的MITE配方
     * @param level 世界
     * @param input 合成输入
     * @param workbenchTier 工作台材质等级
     * @return 匹配的配方
     */
    @Nullable
    public static RecipeHolder<MITERecipe> findRecipe(Level level, MITERecipeInput input, IFWTier workbenchTier) {
        Optional<RecipeHolder<MITERecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(MITERecipeType.MITE_CRAFTING, input, level);

        if (recipe.isPresent()) {
            MITERecipe miteRecipe = recipe.get().value();
            if (isWorkbenchSuitable(workbenchTier, miteRecipe.getRequiredTier())) {
                return recipe.get();
            }
        }
        return null;
    }

    /**
     * 计算批量制作的效率加成
     * @param baseCount 基础数量
     * @param actualCount 实际制作数量
     * @return 效率倍数
     */
    public static double calculateBatchEfficiency(int baseCount, int actualCount) {
        if (actualCount <= baseCount) {
            return 1.0;
        }

        // 实现规模经济：数量越多，单位时间越短
        double batchMultiplier = actualCount / (double) baseCount;
        return Math.min(batchMultiplier, 16.0); // 最大16倍效率
    }
}