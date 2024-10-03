package huix.infinity.gameobjs.item.tier;

import com.google.common.base.Suppliers;
import huix.infinity.gameobjs.tag.IFWBlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public enum EnumTier implements Tier, IIFWTier {
    FLINT(IFWBlockTags.INCORRECT_FOR_LEVEL_0_TOOL, 1, 1.25F, 1.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    OBSIDIAN(IFWBlockTags.INCORRECT_FOR_LEVEL_0_TOOL, 2, 1.5F, 2.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    RUSTED_IRON(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL, 4, 1.25F, 2.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    COPPER(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL, 4, 1.75F, 3.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    SILVER(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL, 4, 1.75F, 3.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    GOLD(IFWBlockTags.INCORRECT_FOR_LEVEL_1_TOOL, 4, 1.75F, 2.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    IRON(IFWBlockTags.INCORRECT_FOR_LEVEL_2_TOOL, 8, 2.0F, 4.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    ANCIENT_METAL(IFWBlockTags.INCORRECT_FOR_LEVEL_2_TOOL, 16, 2.0F, 4.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    MITHRIL(IFWBlockTags.INCORRECT_FOR_LEVEL_3_TOOL, 64, 2.5F, 5.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    ADAMANTIUM(IFWBlockTags.INCORRECT_FOR_LEVEL_4_TOOL, 256, 3.0F, 6.0F, 15, () -> Ingredient.of(ItemTags.PLANKS));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int durability;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    EnumTier(TagKey<Block> incorrectBlockForDrops, int durability, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.incorrectBlocksForDrops = incorrectBlockForDrops;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        Objects.requireNonNull(repairIngredient);
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
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
}
