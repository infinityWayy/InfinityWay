package huix.infinity.mixin.world.entity;

import huix.infinity.common.world.entity.LivingEntityAccess;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityAccess {
    @Unique
    public int food_or_repair_item_pickup_cooldown = 0;
    @Override
    public int getFoodOrRepairItemPickupCoolDown() {
        return food_or_repair_item_pickup_cooldown;
    }

    public void setFoodOrRepairItemPickupCoolDown(int i) {
        this.food_or_repair_item_pickup_cooldown = i;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        if (this.food_or_repair_item_pickup_cooldown > 0) {
            --this.food_or_repair_item_pickup_cooldown;
        }
    }
}
