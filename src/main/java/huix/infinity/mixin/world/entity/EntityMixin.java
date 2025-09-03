package huix.infinity.mixin.world.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import huix.infinity.common.world.curse.CurseEffectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "getMaxAirSupply", at = @At("HEAD"), cancellable = true)
    private void ifw$setMaxAirSupply(CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof Player player) {
            int vanillaMaxAir = 300;
            cir.setReturnValue(CurseEffectHelper.getCursedMaxAirSupply(player, vanillaMaxAir));
        }
    }

    @Inject(method = "getAirSupply", at = @At("RETURN"), cancellable = true)
    private void ifw$limitAirSupply(CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof Player player) {
            int vanillaAir = cir.getReturnValue();
            cir.setReturnValue(CurseEffectHelper.getCursedAirSupply(player, vanillaAir));
        }
    }

    @Invoker("setSharedFlag")
    protected abstract void invokeSetSharedFlag(int flag, boolean value);

    @Redirect(method = "setSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSharedFlag(IZ)V"))
    private void ifw$blockSetSprintFlag(Entity entity, int flag, boolean value) {
        if (entity instanceof Player player && flag == 3 && value && CurseEffectHelper.shouldBlockSprint(player)) {
            ((EntityMixin)(Object)entity).invokeSetSharedFlag(flag, false);
        } else {
            ((EntityMixin)(Object)entity).invokeSetSharedFlag(flag, value);
        }
    }
}