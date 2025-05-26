package huix.infinity.common.world.block.workbench;

import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 燧石工作台
 * 最基础的MITE工作台，可以制作低级物品
 */
public class FlintWorkbenchBlock extends MITEWorkbenchBlock {

    public FlintWorkbenchBlock(Properties properties) {
        super(properties, IFWTiers.FLINT);
    }

    @Override
    protected MenuProvider createMITECraftingMenu(BlockState state, Level level, BlockPos pos) {
        return new MenuProvider() {
            @Override
            public @NotNull Component getDisplayName() {
                return Component.translatable("block.infinityway.flint_workbench");
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
                // 暂时使用默认的合成菜单，后续会创建专门的MITE合成菜单
                return new CraftingMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos));
            }
        };
    }

    @Override
    protected String getTierName() {
        return "Flint";
    }
}