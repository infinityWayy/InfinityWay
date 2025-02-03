package huix.infinity.common.world.block.entity;

import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import huix.infinity.common.world.inventory.IFWFurnaceMenu;
import huix.infinity.common.world.item.crafting.IFWRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IFWFurnaceBlockEntity extends LevelFurnaceBlockEntity {


    public IFWFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(IFWBlockEntityTypes.ifw_furnace.value(), pos, blockState, IFWRecipeTypes.ifw_smelting.get());
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.stone_furnace");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new IFWFurnaceMenu(containerId, inventory, this, this.dataAccess);
    }
}
