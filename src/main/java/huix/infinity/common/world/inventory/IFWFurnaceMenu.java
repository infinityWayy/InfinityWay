package huix.infinity.common.world.inventory;

import huix.infinity.common.world.item.crafting.IFWRecipeType;
import huix.infinity.enum_extesion.IFWEnums;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;

public class IFWFurnaceMenu extends AbstractFurnaceMenu {

    protected IFWFurnaceMenu(int containerId, Inventory playerInventory) {
        super(IFWMenuType.ifw_furnace_menu.get(), IFWRecipeType.ifw_smelting.get(), IFWEnums.cooking_recipe_enum_proxy.getValue(), containerId, playerInventory);
    }

    public IFWFurnaceMenu(int containerId, Inventory playerInventory, Container furnaceContainer, ContainerData furnaceData) {
        super(IFWMenuType.ifw_furnace_menu.get(), IFWRecipeType.ifw_smelting.get(), IFWEnums.cooking_recipe_enum_proxy.getValue(), containerId, playerInventory, furnaceContainer, furnaceData);
    }
}
