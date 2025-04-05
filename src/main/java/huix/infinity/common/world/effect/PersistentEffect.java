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
import org.checkerframework.checker.units.qual.A;

public abstract class PersistentEffect {
    public static final Codec<Holder<PersistentEffect>> CODEC = IFWRegistries.persistent_eff_registry.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PersistentEffect>> STREAM_HOLDER_CODEC = ByteBufCodecs.holderRegistry(IFWRegistries.persistent_eff_registry_key);

    public Component getDisplayName() {
        return Component.translatable(this.getDescriptionId());
    }

    public abstract String desc();

    public String getDescriptionId() {
        return Util.makeDescriptionId("persistent_eff", IFWRegistries.persistent_eff_registry.getKey(this));
    }

    public String key() {
        return IFWRegistries.persistent_eff_registry.getKey(this).getPath();
    }
}
