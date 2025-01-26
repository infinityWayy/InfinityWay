package huix.infinity.func_extension;

import huix.infinity.common.world.food.IFWFoodProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public interface ItemPropertiesExtension {

    default Item.Properties ifw_food(IFWFoodProperties food) {
        return null;
    }
}
