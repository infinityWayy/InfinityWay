package huix.infinity.common.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class IFWFurnaceFuelSlot extends Slot {

    private final AbstractFurnaceMenu menu;

    public IFWFurnaceFuelSlot(AbstractFurnaceMenu furnaceMenu, Container furnaceContainer, int slot, int xPosition, int yPosition) {
        super(furnaceContainer, slot, xPosition, yPosition);
        this.menu = furnaceMenu;
    }


    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.menu.isFuel(stack) || isBucket(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }

}
