package huix.infinity.mixin.world.entity;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import huix.infinity.extension.func.PlayerExtension;
import huix.infinity.common.world.curse.CurseType;
import huix.infinity.common.world.curse.CurseEffectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "getMaxAirSupply", at = @At("HEAD"), cancellable = true)
    private void ifw$setMaxAirSupply(CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof Player player && player instanceof PlayerExtension ext) {
            int maxAir = 300;
            if (ext.getCurse() == CurseType.cannot_hold_breath) {
                maxAir = 90;
            }
            cir.setReturnValue(maxAir);
        }
    }

    @Inject(method = "getAirSupply", at = @At("RETURN"), cancellable = true)
    private void ifw$limitAirSupply(CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof Player player && player instanceof PlayerExtension ext) {
            if (ext.getCurse() == CurseType.cannot_hold_breath && player.isEyeInFluid(FluidTags.WATER)) {
                CurseEffectHelper.learnCurseEffect(ext);
                cir.setReturnValue(90);
            }
        }
    }
}