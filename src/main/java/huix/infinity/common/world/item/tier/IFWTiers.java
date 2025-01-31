package huix.infinity.common.world.item.tier;

import com.google.common.base.Suppliers;
import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public enum IFWTiers implements IFWTier {
    WOOD(IFWBlockTags.INCORRECT_FOR_LEVEL_0_TOOL, 1, 2.0F, 0.0F, 0,
            2, -1, 0, () -> Ingredient.of(ItemTags.PLANKS)),
    FLINT(IFWBlockTags.INCORRECT_FOR_LEVEL_0_TOOL, 1, 2.0F, 0.5F, 0,
            2, -1, 0, () -> Ingredient.of(IFWItems.flint_shard)),
    OBSIDIAN(IFWBlockTags.INCORRECT_FOR_LEVEL_0_TOOL, 2, 2.0F, 0.5F, 0,
            2, -1, 0, () -> Ingredient.of(IFWItems.obsidian_shard)),
    RUSTED_IRON(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL, 4, 3.5F, 2.0F, 0,
            4, 600, 2, () -> Ingredient.of(Tags.Items.NUGGETS_IRON)),
    COPPER(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL, 4, 3.5F, 3.0F, 30,
            8, 800, 1, () -> Ingredient.of(IFWItems.copper_nugget)),
    SILVER(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL, 4, 3.5F, 3.0F, 30,
            8, 800, 1, () -> Ingredient.of(IFWItems.silver_nugget)),
    GOLD(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL, 4, 12.0F, 3.0F, 50
            , 99999999, 800, 1, () -> Ingredient.of(Tags.Items.NUGGETS_GOLD)),
    IRON(IFWBlockTags.INCORRECT_FOR_LEVEL_2_TOOL, 8, 6.0F, 4.0F, 30,
            20, 1600, 2, () -> Ingredient.of(Tags.Items.NUGGETS_IRON)),
    ANCIENT_METAL(IFWBlockTags.INCORRECT_FOR_LEVEL_2_TOOL, 16, 6.0F, 4.0F, 40,
            30, 3200, 3, () -> Ingredient.of(IFWItems.ancient_metal_nugget)),
    MITHRIL(IFWBlockTags.INCORRECT_FOR_LEVEL_3_TOOL, 64, 7.5F, 5.0F, 100,
            40, 12800, 4, () -> Ingredient.of(IFWItems.mithril_nugget)),
    ADAMANTIUM(IFWBlockTags.INCORRECT_FOR_LEVEL_4_TOOL, 256, 10.0F, 6.0F, 40,
            4000, 51200, 5, () -> Ingredient.of(IFWItems.adamantium_nugget));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int durability;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final int acidResistance;
    private final int repairDurability;
    private final int repairLevel;
    private final Supplier<Ingredient> repairIngredient;

    IFWTiers(TagKey<Block> incorrectBlockForDrops, int durability, float speed, float damage, int enchantmentValue, int acidResistance
            , int repairDurability, int repairLevel, Supplier<Ingredient> repairIngredient) {
        this.incorrectBlocksForDrops = incorrectBlockForDrops;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.acidResistance = acidResistance;
        this.repairDurability = repairDurability;
        this.repairLevel = repairLevel;
        Objects.requireNonNull(repairIngredient);
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
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
    public int getUses() {
        return -1;
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
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public EnumQuality getQuality() {
        return null;
    }

    @Override
    public int repairLevel() {
        return this.repairLevel;
    }
}
