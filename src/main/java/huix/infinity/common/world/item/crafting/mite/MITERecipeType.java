package huix.infinity.common.world.item.crafting.mite;

import net.minecraft.world.item.crafting.RecipeType;

/**
 * MITE配方类型
 */
public class MITERecipeType implements RecipeType<MITERecipe> {

    public static final MITERecipeType MITE_CRAFTING = new MITERecipeType();

    private MITERecipeType() {}

    @Override
    public String toString() {
        return "mite_crafting";
    }
}