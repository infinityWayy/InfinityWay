package huix.infinity.enum_extesion;

import com.google.common.base.Suppliers;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IFWEnums {

    public static final EnumProxy<RecipeBookType> cooking_recipe_enum_proxy = new EnumProxy<>(
            RecipeBookType.class, "ifw:cooking_recipe");
}
