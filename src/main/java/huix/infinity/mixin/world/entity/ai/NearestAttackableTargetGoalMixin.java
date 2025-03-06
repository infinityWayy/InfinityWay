package huix.infinity.mixin.world.entity.ai;

import huix.infinity.common.world.entity.monster.Digger;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NearestAttackableTargetGoal.class)
public class NearestAttackableTargetGoalMixin {
    @Shadow protected TargetingConditions targetConditions;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;Z)V", at = @At("TAIL"))
    public void init (Mob mob, Class targetType, boolean mustSee, CallbackInfo ci) {
        if (mob instanceof Digger && !mustSee) this.targetConditions.ignoreLineOfSight();
    }
}
