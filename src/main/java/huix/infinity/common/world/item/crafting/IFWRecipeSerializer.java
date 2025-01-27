package huix.infinity.common.world.item.crafting;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, InfinityWay.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> level_smelting = RECIPE_SERIALIZERS.register("level_smelting",
            location -> new LevelCookingSerializer<>(LevelSmeltingRecipe::new, 200));


}
