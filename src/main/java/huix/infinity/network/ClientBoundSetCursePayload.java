package huix.infinity.network;

import huix.infinity.common.world.curse.CurseType;
import huix.infinity.init.InfinityWay;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ClientBoundSetCursePayload(int curseID, boolean known) implements CustomPacketPayload {

    public static final Type<ClientBoundSetCursePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "curse_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientBoundSetCursePayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, ClientBoundSetCursePayload::curseID,
                    ByteBufCodecs.BOOL, ClientBoundSetCursePayload::known,
                    ClientBoundSetCursePayload::new);

    @Override
    public @NotNull Type<ClientBoundSetCursePayload> type() {
        return TYPE;
    }
}