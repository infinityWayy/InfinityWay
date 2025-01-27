package huix.infinity.mixin.data;

import huix.infinity.common.world.item.crafting.AbstractCookingLevelRecipe;
import huix.infinity.func_extension.CookingRecipeBuilderExtension;
import huix.infinity.util.ReflectHelper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

@Mixin(SimpleCookingRecipeBuilder.class)
public class SimpleCookingRecipeBuilderMixin implements CookingRecipeBuilderExtension {

    @Unique
    private int cookingLevel = 0;

    @Override
    public int cookingLevel() {
        return this.cookingLevel;
    }

    @Override
    public SimpleCookingRecipeBuilder cookingLevel(int level) {
        this.cookingLevel  = level;
        return ReflectHelper.dyCast(this);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/RecipeOutput;accept(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/item/crafting/Recipe;Lnet/minecraft/advancements/AdvancementHolder;)V")
            , method = "save")
    private void ifw_cookingLevel(RecipeOutput instance, ResourceLocation location, Recipe<?> recipe, AdvancementHolder advancement) {
        if (this.cookingLevel != 0) {
            AbstractCookingRecipe abstractcookingrecipe = ((AbstractCookingLevelRecipe.Factory<?>) this.factory)
                    .create(Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, new ItemStack(this.result)
                            , this.experience, this.cookingTime, this.cookingLevel);
            instance.accept(location, abstractcookingrecipe, advancement);
        } else
            instance.accept(location, recipe, advancement);
    }

    @Shadow
    @Final
    private RecipeCategory category;
    @Shadow
    @Final
    private CookingBookCategory bookCategory;
    @Shadow
    @Final
    private Item result;
    @Shadow
    @Final
    private Ingredient ingredient;
    @Shadow
    @Final
    private float experience;
    @Shadow
    @Final
    private int cookingTime;
    @Shadow
    @Final
    private Map<String, Criterion<?>> criteria;
    @Nullable
    private String group;
    @Shadow
    @Final
    private AbstractCookingRecipe.Factory<?> factory;
}
