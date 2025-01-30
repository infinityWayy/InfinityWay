package huix.infinity.enum_extesion;

import com.google.common.base.Suppliers;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;

public class IFWRecipeBookCategories {

    public static final EnumProxy<RecipeBookCategories> level_recipe_enum_proxy = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(IFWItems.raw_adamantium.get())));
    public static final Supplier<RecipeBookCategories> level_recipe = Suppliers.memoize(level_recipe_enum_proxy::getValue);

}
