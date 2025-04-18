package huix.infinity.common.world.block.entity;

import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import huix.infinity.common.world.item.crafting.IFWRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IFWFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public IFWFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(IFWBlockEntityTypes.ifw_furnace.value(), pos, blockState, RecipeType.SMELTING);
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.furnace");
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new FurnaceMenu(id, player, this, this.dataAccess);
    }
}
