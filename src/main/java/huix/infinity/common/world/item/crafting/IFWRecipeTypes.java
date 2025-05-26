package huix.infinity.common.world.item.crafting;

import huix.infinity.common.world.item.crafting.mite.MITERecipe;
import huix.infinity.common.world.item.crafting.mite.MITERecipeType;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, InfinityWay.MOD_ID);

    // 添加MITE配方类型
    public static final DeferredHolder<RecipeType<?>, RecipeType<MITERecipe>> MITE_CRAFTING =
            RECIPES.register("mite_crafting", () -> MITERecipeType.MITE_CRAFTING);
}