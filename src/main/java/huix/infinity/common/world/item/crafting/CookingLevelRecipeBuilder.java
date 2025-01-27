package huix.infinity.common.world.item.crafting;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class CookingLevelRecipeBuilder {

//    private AbstractCookingLevelRecipe.Factory<?> level_factory;
//
//    public CookingLevelRecipeBuilder(RecipeCategory category, CookingBookCategory bookCategory, ItemLike result,
//                                     Ingredient ingredient, float experience, int cookingTime, AbstractCookingLevelRecipe.Factory<?> level_factory) {
//        super(category, bookCategory, result, ingredient, experience, cookingTime, null);
//
//        this.level_factory = level_factory;
//    }
//
//    @Override
//    public void save(RecipeOutput p_301266_, ResourceLocation p_126264_) {
//        this.ensureValid(p_126264_);
//        Advancement.Builder advancement$builder = p_301266_.advancement()
//                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_126264_))
//                .rewards(AdvancementRewards.Builder.recipe(p_126264_))
//                .requirements(AdvancementRequirements.Strategy.OR);
//        this.criteria.forEach(advancement$builder::addCriterion);
//        AbstractCookingRecipe abstractcookingrecipe = this.level_factory
//                .create(
//                        Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.stackResult, this.experience, this.cookingTime
//                );
//        p_301266_.accept(p_126264_, abstractcookingrecipe, advancement$builder.build(p_126264_.withPrefix("recipes/" + this.category.getFolderName() + "/")));
//    }


}
