package huix.infinity.common.world.effect;

import com.mojang.serialization.Codec;
import huix.infinity.common.core.registries.IFWRegistries;
import huix.infinity.init.InfinityWay;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public abstract class PersistentEffect {
    public static final Codec<PersistentEffect> CODEC = IFWRegistries.persistent_eff_registry.byNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PersistentEffect>> STREAM_HOLDER_CODEC = ByteBufCodecs.holderRegistry(IFWRegistries.persistent_eff_registry_key);

    public Component getDisplayName() {
        return Component.translatable(this.getDescriptionId());
    }

    public Tag save() {
        return CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow();
    }


    public static PersistentEffect load(CompoundTag tag) {
        return CODEC.parse(NbtOps.INSTANCE, tag).resultOrPartial(InfinityWay.LOGGER::error).orElse(null);
    }

    public String getDescriptionId() {
        return Util.makeDescriptionId("persistent_eff", IFWRegistries.persistent_eff_registry.getKey(this));
    }

    public String key() {
        return IFWRegistries.persistent_eff_registry.getKey(this).getPath();
    }
}
