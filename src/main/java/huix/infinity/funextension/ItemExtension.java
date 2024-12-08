package huix.infinity.funextension;

import net.minecraft.world.item.ItemStack;

public interface ItemExtension {

    default float getReachBonus() {
        return 0.0F;
    }

    default float getReachBonus(ItemStack selectedItem) {
        return this.getReachBonus();
    }
}
