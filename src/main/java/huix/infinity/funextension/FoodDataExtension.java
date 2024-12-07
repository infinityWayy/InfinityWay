package huix.infinity.funextension;

import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.enchantment.Enchantment;

public interface FoodDataExtension {

    default boolean ifw_hasAnyEnergy() {
        FoodData instance = (FoodData) this;
        return instance.getSaturationLevel() > 0.0F || instance.getFoodLevel() > 0;
    }
}
