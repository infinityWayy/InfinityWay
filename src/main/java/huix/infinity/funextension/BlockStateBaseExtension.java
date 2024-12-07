package huix.infinity.funextension;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface BlockStateBaseExtension {

    default boolean ifw_isPortable() {
        return false;
    }
}
