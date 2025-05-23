package huix.infinity.extension.func;

import huix.infinity.common.world.item.crafting.EnchantingRecipe;
import net.minecraft.world.item.ItemStack;

public interface ItemStackExtension {

    default int ifw_cookingLevel() {
        return 1;
    }

    default int ifw_beCookingLevel() {
        return 1;
    }


    default boolean ifw_hasEncRecipe() {
        return false;
    }

    default EnchantingRecipe ifw_encRecipe() {
        return null;
    }

    default ItemStack ifw_encRecipeResult() {
        return ItemStack.EMPTY;
    }

    default int ifw_encRecipeXP() {
        return 1;
    }

}
