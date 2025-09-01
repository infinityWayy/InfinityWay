package huix.infinity.extension.func;

import huix.infinity.common.world.entity.player.NutritionalStatus;
import huix.infinity.common.world.food.IFWFoodProperties;
import net.minecraft.world.food.FoodData;

public interface FoodDataExtension {

    default boolean ifw_hasAnyEnergy() {
        FoodData instance = (FoodData) this;
        return instance.getSaturationLevel() > 0.0F || instance.getFoodLevel() > 0;
    }

    default void ifw_maxFoodLevel(int maxFoodLevel) {}
    default int ifw_maxFoodLevel() {
        return 0;
    }

    default void ifw_phytonutrients(int phytontrients) {}
    default int ifw_phytonutrients() {
        return 0;
    }

    default void ifw_protein(int protein) {}
    default int ifw_protein() {
        return 0;
    }

    default void ifw_insulinResponse(int ifw_insulinResponse) {}
    default int ifw_insulinResponse() {
        return 0;
    }

    default void ifw_nutritionalStatusByINT(int nutritionalStatus) {}
    default int ifw_nutritionalStatusByINT() {
        return 0;
    }

    default NutritionalStatus ifw_nutritionalStatus() {
        return null;
    }

    default void eat(IFWFoodProperties foodProperties){}

}
