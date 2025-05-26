package huix.infinity.common.world.item.crafting;

import huix.infinity.common.world.item.crafting.mite.MITERecipe;
import huix.infinity.common.world.item.crafting.mite.MITERecipeSerializer;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, InfinityWay.MOD_ID);

    // 添加MITE配方序列化器
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MITERecipe>> MITE_CRAFTING =
            RECIPE_SERIALIZERS.register("mite_crafting", () -> MITERecipeSerializer.INSTANCE);
}