package huix.infinity.network;

import huix.infinity.init.InfinityWay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public record ClientBoundSetFoodPayload(int maxFood, int nutritionalStatus, int phytonutrients, int protein, int insulinResponse) implements CustomPacketPayload {

    public static final Type<ClientBoundSetFoodPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "health_packet"));

    public static final StreamCodec<FriendlyByteBuf, ClientBoundSetFoodPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ClientBoundSetFoodPayload::maxFood,
            ByteBufCodecs.VAR_INT, ClientBoundSetFoodPayload::nutritionalStatus,
            ByteBufCodecs.VAR_INT, ClientBoundSetFoodPayload::phytonutrients,
            ByteBufCodecs.VAR_INT, ClientBoundSetFoodPayload::protein,
            ByteBufCodecs.VAR_INT, ClientBoundSetFoodPayload::insulinResponse,
            ClientBoundSetFoodPayload::new);

    @Override
    public @NotNull Type<ClientBoundSetFoodPayload> type() {
        return TYPE;
    }

}
