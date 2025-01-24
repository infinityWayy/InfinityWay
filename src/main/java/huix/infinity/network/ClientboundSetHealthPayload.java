package huix.infinity.network;

import huix.infinity.init.InfinityWay;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public record ClientboundSetHealthPayload(int maxFood, int nutritionalStatus,int phytonutrients, int protein) implements CustomPacketPayload {

    public static final Type<ClientboundSetHealthPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "health_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSetHealthPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ClientboundSetHealthPayload::maxFood,
            ByteBufCodecs.INT, ClientboundSetHealthPayload::nutritionalStatus,
            ByteBufCodecs.INT, ClientboundSetHealthPayload::phytonutrients,
            ByteBufCodecs.INT, ClientboundSetHealthPayload::protein,
            ClientboundSetHealthPayload::new);

    @Override
    public @NotNull Type<ClientboundSetHealthPayload> type() {
        return TYPE;
    }
}
