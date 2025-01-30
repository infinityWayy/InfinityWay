package huix.infinity.network;

import huix.infinity.init.InfinityWay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public record ClientBoundSetHealthPayload(int maxFood, int nutritionalStatus, int phytonutrients, int protein) implements CustomPacketPayload {

    public static final Type<ClientBoundSetHealthPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "health_packet"));

    public static final StreamCodec<FriendlyByteBuf, ClientBoundSetHealthPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ClientBoundSetHealthPayload::maxFood,
            ByteBufCodecs.VAR_INT, ClientBoundSetHealthPayload::nutritionalStatus,
            ByteBufCodecs.VAR_INT, ClientBoundSetHealthPayload::phytonutrients,
            ByteBufCodecs.VAR_INT, ClientBoundSetHealthPayload::protein,
            ClientBoundSetHealthPayload::new);

    @Override
    public @NotNull Type<ClientBoundSetHealthPayload> type() {
        return TYPE;
    }

}
