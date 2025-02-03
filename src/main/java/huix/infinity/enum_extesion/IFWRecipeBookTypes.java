package huix.infinity.enum_extesion;

import net.minecraft.world.inventory.RecipeBookType;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class IFWRecipeBookTypes {

    public static final EnumProxy<RecipeBookType> cooking_recipe_enum_proxy = new EnumProxy<>(
            RecipeBookType.class, "ifw:cooking_recipe");
}
