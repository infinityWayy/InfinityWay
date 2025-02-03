package huix.infinity.common.world.inventory;

import huix.infinity.common.world.item.crafting.IFWRecipeTypes;
import huix.infinity.enum_extesion.IFWRecipeBookTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;

public class IFWFurnaceMenu extends AbstractFurnaceMenu {

    protected IFWFurnaceMenu(int containerId, Inventory playerInventory) {
        super(IFWMenuTypes.ifw_furnace_menu.get(), IFWRecipeTypes.ifw_smelting.get(), IFWRecipeBookTypes.cooking_recipe_enum_proxy.getValue(), containerId, playerInventory);
    }

    public IFWFurnaceMenu(int containerId, Inventory playerInventory, Container furnaceContainer, ContainerData furnaceData) {
        super(IFWMenuTypes.ifw_furnace_menu.get(), IFWRecipeTypes.ifw_smelting.get(), IFWRecipeBookTypes.cooking_recipe_enum_proxy.getValue(), containerId, playerInventory, furnaceContainer, furnaceData);
    }
}
