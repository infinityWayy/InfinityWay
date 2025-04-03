package huix.infinity.extension.func;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockStateBaseExtension {

    default void ifw_onPlace(Level level, BlockPos pos, BlockState oldState, ItemStack stack , Player player, boolean movedByPiston) {
    }
}
