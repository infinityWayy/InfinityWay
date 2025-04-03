package huix.infinity.mixin.world.block;

import huix.infinity.func_extension.BlockExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( Block.class )
public abstract class BlockMixin implements BlockExtension {
    @Redirect(method = "playerDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    public void moreFoodExhaustion(Player player, float exhaustion) {
        player.causeFoodExhaustion(exhaustion * 2);
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    public void injectPlaceHunger(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, CallbackInfo ci) {
        if (placer instanceof Player player) {
            player.causeFoodExhaustion(Math.min(state.getDestroySpeed(level, pos), 20.0f));
        }
    }
}
