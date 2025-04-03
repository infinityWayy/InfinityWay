package huix.infinity.mixin.world.block;

import huix.infinity.extension.func.BlockStateBaseExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( BlockBehaviour.BlockStateBase.class )
public abstract class BlockStateBaseMixin implements BlockStateBaseExtension {

    @Shadow public abstract Block getBlock();

    @Shadow protected abstract BlockState asState();

    @Override
    public void ifw_onPlace(Level level, BlockPos pos, BlockState oldState, ItemStack stack, Player player, boolean movedByPiston) {
        this.getBlock().ifw_onPlace(this.asState(), level, pos, oldState, stack, player, movedByPiston);
    }
}
