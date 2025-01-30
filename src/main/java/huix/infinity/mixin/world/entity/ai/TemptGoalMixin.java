package huix.infinity.mixin.world.entity.ai;

import huix.infinity.common.world.entity.animal.Livestock;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemptGoal.class)
public class TemptGoalMixin {

    @Shadow @Final protected PathfinderMob mob;

    @Inject(method = "canUse", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/PathfinderMob;level()Lnet/minecraft/world/level/Level;"), cancellable = true)
    public void canUse(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof Livestock livestock) {
            if (livestock.hasFullHealth() && !livestock.isWell() && !livestock.isHungry()) {
                cir.setReturnValue(false);
            }
        }
    }
}
