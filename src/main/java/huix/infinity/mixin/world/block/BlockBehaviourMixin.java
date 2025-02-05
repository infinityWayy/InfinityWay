package huix.infinity.mixin.world.block;

import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.func_extension.BlockBehaviourExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( BlockBehaviour.class )
public abstract class BlockBehaviourMixin implements BlockBehaviourExtension {

    @Overwrite
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        float f = state.getDestroySpeed(level, pos) * 100;
        if (f != -1.0F && player.getFoodData().ifw_hasAnyEnergy()) {
            int i = net.neoforged.neoforge.event.EventHooks.doPlayerHarvestCheck(player, state, level, pos) ? 4 : -1;
            if (state.is(IFWBlockTags.PORTABLE_BLOCK))
                i = (int)((float)i / 5.0F);

            return player.getDigSpeed(state, pos) / f / (float)i;
        }
        return 0.0F;
    }
}
