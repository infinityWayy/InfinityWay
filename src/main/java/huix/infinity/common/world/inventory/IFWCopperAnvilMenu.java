package huix.infinity.common.world.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.InventoryMenu;

public class IFWCopperAnvilMenu extends IFWAnvilMenu {
    public IFWCopperAnvilMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(IFWMenuType.copper_anvil_menu.get(), containerId, playerInventory, access, 1);
    }

    public IFWCopperAnvilMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }
}
