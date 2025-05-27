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

    public static final Supplier<SoundEvent> REVENANT_AMBIENT =
            SOUND_EVENTS.register("revenant_ambient", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "revenant_ambient")
                    )
            );

    public static final Supplier<SoundEvent> GHOUL_AMBIENT =
            SOUND_EVENTS.register("ghoul_ambient", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "ghoul_ambient")
                    )
            );

    public static final Supplier<SoundEvent> GHOUL_HURT =
            SOUND_EVENTS.register("ghoul_hurt", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "ghoul_hurt")
                    )
            );

    public static final Supplier<SoundEvent> GHOUL_DEATH =
            SOUND_EVENTS.register("ghoul_death", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "ghoul_death")
                    )
            );

    public static final Supplier<SoundEvent> WIGHT_AMBIENT =
            SOUND_EVENTS.register("wight_ambient", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "wight_ambient")
                    )
            );

    public static final Supplier<SoundEvent> WIGHT_HURT =
            SOUND_EVENTS.register("wight_hurt", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "wight_hurt")
                    )
            );

    public static final Supplier<SoundEvent> WIGHT_DEATH =
            SOUND_EVENTS.register("wight_death", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "wight_death")
                    )
            );

    public static final Supplier<SoundEvent> INVISIBLE_STALKER_AMBIENT =
            SOUND_EVENTS.register("invisible_stalker_ambient", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "invisible_stalker_ambient")
                    )
            );

    public static final Supplier<SoundEvent> INVISIBLE_STALKER_HURT =
            SOUND_EVENTS.register("invisible_stalker_hurt", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "invisible_stalker_hurt")
                    )
            );

    public static final Supplier<SoundEvent> INVISIBLE_STALKER_DEATH =
            SOUND_EVENTS.register("invisible_stalker_death", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath("ifw", "invisible_stalker_death")
                    )
            );

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}