package huix.infinity.extension.func;

import net.minecraft.world.item.ArmorItem;

import java.util.Map;

public interface ArmorExtension {


    default float float_defense() {
        return 0.0F;
    }

    default Map<ArmorItem.Type, Float> ifw_defense() {
        return null;
    }
}
