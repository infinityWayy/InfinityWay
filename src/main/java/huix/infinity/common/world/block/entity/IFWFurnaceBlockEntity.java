package huix.infinity.common.world.block.entity;

import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import huix.infinity.common.world.inventory.IFWFurnaceMenu;
import huix.infinity.common.world.item.crafting.IFWRecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class IFWFurnaceBlockEntity extends LevelFurnaceBlockEntity {

    public IFWFurnaceBlockEntity(BlockPos pos, BlockState blockState, int furnaceLevel) {
        super(IFWBlockEntityTypes.ifw_furnace.value(), pos, blockState, IFWRecipeType.ifw_smelting.get(), furnaceLevel);
    }

    public IFWFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(IFWBlockEntityTypes.ifw_furnace.value(), pos, blockState, IFWRecipeType.ifw_smelting.get(), 1);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.stone_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new IFWFurnaceMenu(containerId, inventory, this, this.dataAccess);
    }
}
