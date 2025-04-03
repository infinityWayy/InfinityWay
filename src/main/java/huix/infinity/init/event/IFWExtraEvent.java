package huix.infinity.init.event;

import huix.infinity.common.core.registries.IFWRegistries;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.animal.IFWChicken;
import huix.infinity.common.world.entity.animal.IFWCow;
import huix.infinity.common.world.entity.animal.IFWPig;
import huix.infinity.common.world.entity.animal.IFWSheep;
import huix.infinity.common.world.entity.monster.IFWZombie;
import huix.infinity.init.InfinityWay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IFWExtraEvent {
    @SubscribeEvent
    public static void onRegisterAttributes(final EntityAttributeCreationEvent event) {
        event.put(IFWEntityType.CHICKEN.get(), IFWChicken.createAttributes().build());
        event.put(IFWEntityType.SHEEP.get(), IFWSheep.createAttributes().build());
        event.put(IFWEntityType.PIG.get(), IFWPig.createAttributes().build());
        event.put(IFWEntityType.COW.get(), IFWCow.createAttributes().build());
        event.put(IFWEntityType.ZOMBIE.get(), IFWZombie.createAttributes().build());
    }

    @SubscribeEvent
    static void registerRegistries(NewRegistryEvent event) {
        event.register(IFWRegistries.CURSE_REGISTRY);
    }
}
