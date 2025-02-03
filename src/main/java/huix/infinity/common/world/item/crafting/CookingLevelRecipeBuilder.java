package huix.infinity.common.world.item.crafting;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class CookingLevelRecipeBuilder extends SimpleCookingRecipeBuilder {

    private final LevelCookingRecipe.Factory<?> level_factory;
    public final int cookingLevel;

    public CookingLevelRecipeBuilder(RecipeCategory category, ItemLike result,
                                     Ingredient ingredient, float experience, int cookingTime, int cookingLevel, LevelCookingRecipe.Factory<?> level_factory) {
        super(category, CookingBookCategory.MISC, result, ingredient, experience, cookingTime, null);

        this.level_factory = level_factory;
        this.cookingLevel = cookingLevel;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation location) {
        this.ensureValid(location);
        Advancement.Builder advancement$builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location))
                .rewards(AdvancementRewards.Builder.recipe(location))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        LevelCookingRecipe abstractcookingrecipe = this.level_factory
                .create(Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.stackResult,
                        this.experience, this.cookingTime, this.cookingLevel);
        output.accept(location, abstractcookingrecipe, advancement$builder.build(location.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }


    public static CookingLevelRecipeBuilder smelting(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime, int cookingLevel) {
        return new CookingLevelRecipeBuilder(
                category, result, ingredient, experience, cookingTime, cookingLevel, LevelSmeltingRecipe::new
        );
    }

    public static CookingLevelRecipeBuilder smelting(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime) {
        return new CookingLevelRecipeBuilder(
                category, result, ingredient, experience, cookingTime, 1, LevelSmeltingRecipe::new
        );
    }




}
