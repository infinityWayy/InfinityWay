package huix.infinity.common.world.item.crafting.mite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * MITE配方基类
 * 包含制作难度、时间计算、材质要求等MITE特有功能
 */
public class MITERecipe implements Recipe<MITERecipeInput> {

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int craftingDifficulty;
    private final IFWTier requiredTier;
    private final boolean allowBatchCrafting;
    private final int baseCount;

    public MITERecipe(NonNullList<Ingredient> ingredients, ItemStack result,
                      int craftingDifficulty, IFWTier requiredTier,
                      boolean allowBatchCrafting, int baseCount) {
        this.ingredients = ingredients;
        this.result = result;
        this.craftingDifficulty = craftingDifficulty;
        this.requiredTier = requiredTier;
        this.allowBatchCrafting = allowBatchCrafting;
        this.baseCount = baseCount;
    }

    @Override
    public boolean matches(@NotNull MITERecipeInput input, @NotNull Level level) {
        // 检查配方是否匹配输入中的物品
        if (input.size() < ingredients.size()) {
            return false;
        }

        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(input.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MITERecipeInput input, HolderLookup.@NotNull Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= ingredients.size();
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return MITERecipeSerializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return MITERecipeType.MITE_CRAFTING;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    // MITE特有方法
    public int getCraftingDifficulty() {
        return craftingDifficulty;
    }

    public IFWTier getRequiredTier() {
        return requiredTier;
    }

    public boolean allowsBatchCrafting() {
        return allowBatchCrafting;
    }

    public int getBaseCount() {
        return baseCount;
    }

    public int getCraftingTicks() {
        return MITECraftingSystem.calculateCraftingTicks(craftingDifficulty);
    }

    public double getCraftingTimeSeconds() {
        return MITECraftingSystem.calculateCraftingTimeSeconds(craftingDifficulty);
    }

    /**
     * 计算批量制作的时间
     * @param count 制作数量
     * @return 总制作时间（ticks）
     */
    public int getBatchCraftingTicks(int count) {
        if (!allowBatchCrafting || count <= baseCount) {
            return getCraftingTicks() * count;
        }

        double efficiency = MITECraftingSystem.calculateBatchEfficiency(baseCount, count);
        return (int) (getCraftingTicks() * count / efficiency);
    }

    // Codec用于序列化
    public static final Codec<MITERecipe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients")
                            .xmap(NonNullList::copyOf, java.util.List::copyOf)
                            .forGetter(recipe -> recipe.ingredients),
                    ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("difficulty").forGetter(recipe -> recipe.craftingDifficulty),
                    Codec.STRING.fieldOf("required_tier")
                            .xmap(MITERecipe::parseTier, MITERecipe::tierToString)
                            .forGetter(recipe -> recipe.requiredTier),
                    Codec.BOOL.optionalFieldOf("batch_crafting", false).forGetter(recipe -> recipe.allowBatchCrafting),
                    ExtraCodecs.POSITIVE_INT.optionalFieldOf("base_count", 1).forGetter(recipe -> recipe.baseCount)
            ).apply(instance, MITERecipe::new)
    );

    // StreamCodec用于网络传输
    public static final StreamCodec<RegistryFriendlyByteBuf, MITERecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).map(NonNullList::copyOf, java.util.List::copyOf),
            recipe -> recipe.ingredients,
            ItemStack.STREAM_CODEC, recipe -> recipe.result,
            ByteBufCodecs.VAR_INT, recipe -> recipe.craftingDifficulty,
            ByteBufCodecs.STRING_UTF8.map(MITERecipe::parseTier, MITERecipe::tierToString), recipe -> recipe.requiredTier,
            ByteBufCodecs.BOOL, recipe -> recipe.allowBatchCrafting,
            ByteBufCodecs.VAR_INT, recipe -> recipe.baseCount,
            MITERecipe::new
    );

    private static IFWTier parseTier(String tierName) {
        return switch (tierName.toLowerCase()) {
            case "flint" -> IFWTiers.FLINT;
            case "copper" -> IFWTiers.COPPER;
            case "silver" -> IFWTiers.SILVER;
            case "gold" -> IFWTiers.GOLD;
            case "iron" -> IFWTiers.IRON;
            case "ancient_metal" -> IFWTiers.ANCIENT_METAL;
            case "mithril" -> IFWTiers.MITHRIL;
            case "adamantium" -> IFWTiers.ADAMANTIUM;
            case "obsidian" -> IFWTiers.OBSIDIAN;
            default -> IFWTiers.FLINT;
        };
    }

    private static String tierToString(IFWTier tier) {
        if (tier == IFWTiers.FLINT) return "flint";
        if (tier == IFWTiers.COPPER) return "copper";
        if (tier == IFWTiers.SILVER) return "silver";
        if (tier == IFWTiers.GOLD) return "gold";
        if (tier == IFWTiers.IRON) return "iron";
        if (tier == IFWTiers.ANCIENT_METAL) return "ancient_metal";
        if (tier == IFWTiers.MITHRIL) return "mithril";
        if (tier == IFWTiers.ADAMANTIUM) return "adamantium";
        if (tier == IFWTiers.OBSIDIAN) return "obsidian";
        return "flint";
    }
}