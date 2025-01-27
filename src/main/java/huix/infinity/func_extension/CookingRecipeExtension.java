package huix.infinity.func_extension;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import org.spongepowered.asm.mixin.Unique;

public interface CookingRecipeExtension<T extends AbstractCookingRecipe> {

    default int cookingLevel() {
        return 0;
    }

    default AbstractCookingRecipe cookingLevel(int cookingLevel) {
        return null;
    }
}
