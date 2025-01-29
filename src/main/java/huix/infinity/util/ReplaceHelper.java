package huix.infinity.util;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.food.IFWFoodProperties;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.Optional;

public class ReplaceHelper {

    public static void foodOverride(Item patchItem, FoodProperties patch) {
        patchItem.ifw_modifyDefaultComponentsFrom(DataComponentPatch.builder().set(DataComponents.FOOD, patch).build());
    }

    public static void foodAdd(Item patchItem, IFWFoodProperties add) {
        patchItem.ifw_modifyDefaultComponentsFrom(DataComponentPatch.builder().set(IFWDataComponents.ifw_food_data.get(), add).build());
    }

    public static void stackSize(Item patchItem, int size) {
        patchItem.ifw_modifyDefaultComponentsFrom(DataComponentPatch.builder().set(DataComponents.MAX_STACK_SIZE, size).build());
    }

    public static void itemCookingLevel(Item patchItem, int level) {
        patchItem.ifw_modifyDefaultComponentsFrom(DataComponentPatch.builder().set(IFWDataComponents.ifw_cooking_level.get(), level).build());
    }

    public static void itemCookingLevel(TagKey<Item> itemTagKey, int level) {
        for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(itemTagKey)) {
            holder.value().ifw_modifyDefaultComponentsFrom(DataComponentPatch.builder().set(IFWDataComponents.ifw_cooking_level.get(), level).build());
        }

    }
}
