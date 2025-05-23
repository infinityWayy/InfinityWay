package huix.infinity.network;

import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.curse.PersistentEffectInstance;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public record ClientBoundSetCursePayload(PersistentEffectInstance curse) implements CustomPacketPayload {

    public static final Type<ClientBoundSetCursePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "curse_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientBoundSetCursePayload> STREAM_CODEC = StreamCodec.composite(
            PersistentEffectInstance.STREAM_CODEC, ClientBoundSetCursePayload::curse,
            ClientBoundSetCursePayload::new);

    @Override
    public @NotNull Type<ClientBoundSetCursePayload> type() {
        return TYPE;
    }

}
