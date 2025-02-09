package huix.infinity.common.world.item.crafting;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, InfinityWay.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<EnchantmentRecipe>> enchantment = RECIPE_SERIALIZERS.register("enchantment",
            location -> new EnchantmentSerializer<>(EnchantmentRecipe::new));
}
