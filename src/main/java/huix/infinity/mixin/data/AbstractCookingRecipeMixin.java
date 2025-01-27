package huix.infinity.mixin.data;

import huix.infinity.func_extension.CookingRecipeExtension;
import huix.infinity.util.ReflectHelper;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractCookingRecipe.class)
public class AbstractCookingRecipeMixin implements CookingRecipeExtension {

    @Unique
    private int cookingLevel = 0;

    @Unique
    @Override
    public int cookingLevel() {
        return this.cookingLevel;
    }

    @Unique
    @Override
    public AbstractCookingRecipe cookingLevel(int cookingLevel) {
        this.cookingLevel = cookingLevel;
        return ReflectHelper.dyCast(this);
    }
}
