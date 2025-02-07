package huix.infinity.init.event;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.animal.IFWChicken;
import huix.infinity.init.InfinityWay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IFWExtraEvent {
    @SubscribeEvent
    public static void onRegisterAttributes(final EntityAttributeCreationEvent event) {
        event.put(IFWEntityType.CHICKEN.get(), IFWChicken.createAttributes().build());
    }
}
