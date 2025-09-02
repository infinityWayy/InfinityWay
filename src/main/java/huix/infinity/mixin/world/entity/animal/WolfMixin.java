package huix.infinity.mixin.world.entity.animal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

/**
 * Prevent "witch ally" wolves from attacking their summoning witch.
 * Wolves with a "witch_ally" UUID in their persistent data will not attack the matching Witch entity.
 */
@Mixin(Wolf.class)
public abstract class WolfMixin {

    @Inject(method = "wantsToAttack", at = @At("HEAD"), cancellable = true)
    private void ifw$blockAttackWitch(LivingEntity target, LivingEntity owner, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof Witch witch) {
            Wolf wolf = (Wolf)(Object)this;
            CompoundTag data = wolf.getPersistentData();
            if (data.contains("witch_ally")) {
                UUID witchId = data.getUUID("witch_ally");
                if (witch.getUUID().equals(witchId)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}