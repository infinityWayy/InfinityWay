package huix.infinity.extension.func;

import net.minecraft.world.item.enchantment.Enchantment;

public interface EnchantmentExtension {

    default boolean ifw_hasLevels() {
        Enchantment instance = (Enchantment) this;
        return instance.getMaxLevel() > 1;
    }
}
