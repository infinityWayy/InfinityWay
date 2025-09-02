package huix.infinity.mixin.world.entity.monster;

import huix.infinity.common.world.curse.CurseType;
import huix.infinity.extension.func.PlayerExtension;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class EnderManMixin {

    @Inject(method = "isLookingAtMe", at = @At("HEAD"), cancellable = true)
    private void ifw$forceEndermanAggro(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof PlayerExtension ext && ext.getCurse() == CurseType.endermen_aggro) {
            cir.setReturnValue(true);
        }
    }
}