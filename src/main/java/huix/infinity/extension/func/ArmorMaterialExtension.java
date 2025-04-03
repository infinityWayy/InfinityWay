package huix.infinity.extension.func;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import java.util.Map;

public interface ArmorMaterialExtension {

    default Map<ArmorItem.Type, Float> ifw_defense() {
        return null;
    }

    default ArmorMaterial ifw_defense(Map<ArmorItem.Type, Float> defense) {
        return null;
    }

    default int acidResistance() {
        return 0;
    }

    default float magicResistance() {
        return 0.0F;
    }

    default ArmorMaterial acidResistance(int acidResistance) {
        return null;
    }

    default ArmorMaterial magicResistance(float magicResistance) {
        return null;
    }

    default int repairLevel() {
        return 0;
    }

    default ArmorMaterial repairLevel(int repairLevel) {
        return null;
    }

    default float float_defense(ArmorItem.Type type) {
        return 0.0F;
    }
}
