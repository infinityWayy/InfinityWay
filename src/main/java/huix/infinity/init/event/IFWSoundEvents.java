package huix.infinity.init.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class IFWSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, "ifw");

    public static final Supplier<SoundEvent> CLASSIC_HURT =
            SOUND_EVENTS.register("classic_hurt", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "classic_hurt")
                    )
            );

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}