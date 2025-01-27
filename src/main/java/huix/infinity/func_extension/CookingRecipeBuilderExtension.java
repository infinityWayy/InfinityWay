package huix.infinity.func_extension;

import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;

public interface CookingRecipeBuilderExtension {

    default int cookingLevel() {
        return 0;
    }

    default SimpleCookingRecipeBuilder cookingLevel(int level) {
        return null;
    }
}
