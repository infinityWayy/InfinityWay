package huix.infinity.mixin.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( BlockBehaviour.class )
public class BlockBehaviourMixin {

    @Overwrite
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        float f = state.getDestroySpeed(level, pos) * 100;
        if (f != -1.0F && player.getFoodData().ifw_hasAnyEnergy()) {
            int i = net.neoforged.neoforge.event.EventHooks.doPlayerHarvestCheck(player, state, level, pos) ? 4 : -1;
            if (state.ifw_isPortable())
                i = (int)((float)i / 20.0F);

            return player.getDigSpeed(state, pos) / f / (float)i;
        }
        return 0.0F;
    }
}
