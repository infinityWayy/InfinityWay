package huix.infinity.common.core.component;

import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.common.world.item.crafting.EnchantingRecipe;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, InfinityWay.MOD_ID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<IFWFoodProperties>> ifw_food_data = DATA_COMPONENTS.registerComponentType("ifw_food_data",
            builder -> builder.persistent(IFWFoodProperties.DIRECT_CODEC).networkSynchronized(IFWFoodProperties.DIRECT_STREAM_CODEC).cacheEncoding());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ifw_cooking_level = DATA_COMPONENTS.registerComponentType("ifw_cooking_level",
            builder -> builder.persistent(ExtraCodecs.intRange(1, 99)).networkSynchronized(ByteBufCodecs.VAR_INT).cacheEncoding());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ifw_be_cooking_level = DATA_COMPONENTS.registerComponentType("ifw_be_cooking_level",
            builder -> builder.persistent(ExtraCodecs.intRange(1, 99)).networkSynchronized(ByteBufCodecs.VAR_INT).cacheEncoding());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<EnchantingRecipe>> enchantment_recipe = DATA_COMPONENTS.registerComponentType("enchantment_recipe",
            builder -> builder.persistent(EnchantingRecipe.DIRECT_CODEC).networkSynchronized(EnchantingRecipe.DIRECT_STREAM_CODEC).cacheEncoding());

}
