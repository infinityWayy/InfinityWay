package huix.infinity.common.world.item.crafting;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class EnchantmentRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final float experience;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final EnchantmentRecipe.Factory<?> factory;

    public EnchantmentRecipeBuilder(Ingredient ingredient, ItemStack result, float experience) {
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.factory = EnchantmentRecipe::new;
    }

    public static EnchantmentRecipeBuilder basic(Ingredient ingredient, ItemLike result, float experience) {
        return new EnchantmentRecipeBuilder(ingredient, new ItemStack(result), experience);
    }

    @Override
    public EnchantmentRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public EnchantmentRecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation location) {
        Advancement.Builder advancement$builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location))
                .rewards(AdvancementRewards.Builder.recipe(location))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        EnchantmentRecipe recipe = this.factory.create(this.ingredient, this.result, this.experience);
        output.accept(location, recipe, advancement$builder.build(location.withPrefix("recipes/enchantment/")));
    }
}
