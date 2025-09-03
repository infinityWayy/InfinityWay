package huix.infinity.mixin.world.entity.monster;

import huix.infinity.common.world.curse.CurseEffectHelper;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderMan.class)
public class EnderManMixin {

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void ifw$forceEndermanAttack(CallbackInfo ci) {
        CurseEffectHelper.handleEndermanAggro((EnderMan)(Object)this);
    }
}