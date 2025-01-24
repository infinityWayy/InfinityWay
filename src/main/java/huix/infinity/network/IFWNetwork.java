package huix.infinity.network;

import huix.infinity.init.InfinityWay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IFWNetwork {

    @SubscribeEvent
    private static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").optional();
        registrar.
                playToClient(
                        ClientboundSetHealthPayload.TYPE,
                        ClientboundSetHealthPayload.STREAM_CODEC,
                        ClientPayloadHandler::handle);
    }
}
