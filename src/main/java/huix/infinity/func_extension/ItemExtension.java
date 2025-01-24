package huix.infinity.func_extension;

import net.minecraft.world.item.ItemStack;

public interface ItemExtension {

    default float getReachBonus() {
        return 0.0F;
    }

    default float getReachBonus(final ItemStack itemStack) {
        return this.getReachBonus();
    }
}
