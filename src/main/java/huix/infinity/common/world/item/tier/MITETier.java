package huix.infinity.common.world.item.tier;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import huix.infinity.common.core.tag.IFWBlockTags;

/**
 * MITE材质等级枚举
 * 基于MITE的九级材质等级系统，实现IFWTier接口
 */
public enum MITETier implements IFWTier {
    FLINT("flint", 100, 1, 59, 2.0F, 0.0F, 0, () -> Ingredient.EMPTY, IFWQuality.POOR, 0.1F, 10.0F, 1),
    COPPER("copper", 400, 1, 131, 4.0F, 1.0F, 5, () -> Ingredient.EMPTY, IFWQuality.INFERIOR, 0.2F, 20.0F, 2),
    SILVER("silver", 400, 2, 180, 5.0F, 1.5F, 8, () -> Ingredient.EMPTY, IFWQuality.AVERAGE, 0.3F, 25.0F, 2),
    GOLD("gold", 400, 2, 250, 6.0F, 2.0F, 10, () -> Ingredient.EMPTY, IFWQuality.AVERAGE, 0.4F, 30.0F, 2),
    IRON("iron", 800, 2, 300, 7.0F, 2.5F, 15, () -> Ingredient.EMPTY, IFWQuality.GOOD, 0.5F, 40.0F, 3),
    ANCIENT_METAL("ancient_metal", 1600, 3, 500, 8.0F, 3.0F, 20, () -> Ingredient.EMPTY, IFWQuality.GOOD, 0.6F, 60.0F, 4),
    MITHRIL("mithril", 6400, 4, 800, 10.0F, 4.0F, 30, () -> Ingredient.EMPTY, IFWQuality.SUPERIOR, 0.8F, 100.0F, 5),
    ADAMANTIUM("adamantium", 25600, 5, 1200, 12.0F, 5.0F, 50, () -> Ingredient.EMPTY, IFWQuality.LEGENDARY, 1.0F, 200.0F, 6);

    private final String name;
    private final int craftingDifficulty;
    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final java.util.function.Supplier<Ingredient> repairIngredient;
    private final IFWQuality quality;
    private final float acidResistance;
    private final float repairDurability;
    private final int repairLevel;

    MITETier(String name, int craftingDifficulty, int level, int uses, float speed, float damage,
             int enchantmentValue, java.util.function.Supplier<Ingredient> repairIngredient,
             IFWQuality quality, float acidResistance, float repairDurability, int repairLevel) {
        this.name = name;
        this.craftingDifficulty = craftingDifficulty;
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
        this.quality = quality;
        this.acidResistance = acidResistance;
        this.repairDurability = repairDurability;
        this.repairLevel = repairLevel;
    }

    // Minecraft Tier 接口实现
    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        // 根据等级返回对应的工具标签
        return switch (this.level) {
            case 0, 1 -> IFWBlockTags.INCORRECT_FOR_LEVEL_0_TOOL;
            case 2 -> IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL;
            case 3 -> IFWBlockTags.INCORRECT_FOR_LEVEL_2_TOOL;
            case 4 -> IFWBlockTags.INCORRECT_FOR_LEVEL_3_TOOL;
            case 5 -> IFWBlockTags.INCORRECT_FOR_LEVEL_4_TOOL;
            default -> BlockTags.INCORRECT_FOR_IRON_TOOL;
        };
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    // IFWTier 接口实现
    @Override
    public int durability() {
        return this.uses;
    }

    @Override
    public IFWQuality quality() {
        return this.quality;
    }

    @Override
    public float acidResistance() {
        return this.acidResistance;
    }

    @Override
    public float repairDurability() {
        return this.repairDurability;
    }

    @Override
    public int repairLevel() {
        return this.repairLevel;
    }

    // MITE 特定方法
    public String getName() {
        return name;
    }

    public int getCraftingDifficulty() {
        return craftingDifficulty;
    }

    public int getLevel() {
        return level;
    }

    /**
     * 根据名称获取材质等级
     */
    public static MITETier fromName(String name) {
        for (MITETier tier : values()) {
            if (tier.name.equalsIgnoreCase(name)) {
                return tier;
            }
        }
        return null;
    }

    /**
     * 检查是否比另一个等级高
     */
    public boolean isHigherThan(MITETier other) {
        return this.level > other.level;
    }

    /**
     * 检查是否比另一个等级低
     */
    public boolean isLowerThan(MITETier other) {
        return this.level < other.level;
    }

    /**
     * 获取本地化键
     */
    public String getTranslationKey() {
        return "tier.infinityway.mite." + name;
    }

    /**
     * 计算制作时间（ticks）
     */
    public int calculateCraftingTicks() {
        if (this.craftingDifficulty <= 100) return 100;
        return (int) (Math.pow(this.craftingDifficulty - 100, 0.8) + 100);
    }

    /**
     * 计算制作时间（秒）
     */
    public double calculateCraftingTimeSeconds() {
        return calculateCraftingTicks() / 20.0;
    }

    /**
     * 检查是否可以制作指定材质
     */
    public boolean canCraft(MITETier target) {
        return this.level >= target.level;
    }

    /**
     * 获取下一级材质
     */
    public MITETier getNextTier() {
        MITETier[] tiers = values();
        for (int i = 0; i < tiers.length - 1; i++) {
            if (tiers[i] == this) {
                return tiers[i + 1];
            }
        }
        return null; // 已经是最高级
    }

    /**
     * 获取上一级材质
     */
    public MITETier getPreviousTier() {
        MITETier[] tiers = values();
        for (int i = 1; i < tiers.length; i++) {
            if (tiers[i] == this) {
                return tiers[i - 1];
            }
        }
        return null; // 已经是最低级
    }
}