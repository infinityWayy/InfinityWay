package huix.infinity.common.world.curse;

import com.mojang.serialization.Codec;
import huix.infinity.common.core.registries.IFWRegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class Curse {

    public static final Codec<Holder<Curse>> CODEC = IFWRegistries.CURSE_REGISTRY.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Curse>> STREAM_CODEC = ByteBufCodecs.holderRegistry(IFWRegistries.CURSE_REGISTRY_KEY);


}
