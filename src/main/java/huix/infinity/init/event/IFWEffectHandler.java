package huix.infinity.init.event;

import huix.infinity.init.InfinityWay;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = InfinityWay.MOD_ID)
public class IFWEffectHandler {
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity living) {
            if (!living.level().isClientSide() && living.tickCount % 20 == 0) {
                if (living.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                    MobEffectInstance effect = living.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    if (effect != null && effect.getAmplifier() >= 2) {
                        if (living.isInWater() || living.isInLava()) {
                            if (living.getDeltaMovement().y > 0) {
                                living.setDeltaMovement(
                                        living.getDeltaMovement().x,
                                        Math.min(living.getDeltaMovement().y * 0.1, 0.01),
                                        living.getDeltaMovement().z
                                );
                            }
                        }
                    }
                }
            }
        }
    }
}