package huix.infinity.common.world.curse;

import com.google.common.collect.ComparisonChain;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.vehicle.Minecart;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class PersistentEffectInstance implements Comparable<PersistentEffectInstance>, INBTSerializable<CompoundTag> {

    private Holder<PersistentEffect> persistentEff;
    private String desc;

    public static final Codec<PersistentEffectInstance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            PersistentEffect.CODEC.fieldOf("id").forGetter(PersistentEffectInstance::persistentEff),
                            Codec.STRING.fieldOf("desc").forGetter(PersistentEffectInstance::desc)
                    )
                    .apply(instance, PersistentEffectInstance::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PersistentEffectInstance> STREAM_CODEC = StreamCodec.composite(
            PersistentEffect.STREAM_HOLDER_CODEC, PersistentEffectInstance::persistentEff,
            ByteBufCodecs.STRING_UTF8, PersistentEffectInstance::desc,
            PersistentEffectInstance::new);

    public static PersistentEffectInstance of(final Holder<PersistentEffect> curse) {
        return new PersistentEffectInstance(curse);
    }

    public PersistentEffectInstance(Holder<PersistentEffect> persistentEff) {
        this.persistentEff = persistentEff;
        this.desc = persistentEff.value().desc();
    }

    public PersistentEffectInstance(Holder<PersistentEffect> persistentEff, String desc) {
        this.persistentEff = persistentEff;
        this.desc = desc;
    }

    public Holder<PersistentEffect> persistentEff() {
        return persistentEff;
    }

    public String desc() {
        return desc;
    }


    public Tag save() {
        return CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow();
    }

    public static PersistentEffectInstance load(CompoundTag tag) {
        return CODEC.parse(NbtOps.INSTANCE, tag).resultOrPartial(InfinityWay.LOGGER::error).orElse(null);
    }

    @Override
    public int compareTo(@NotNull PersistentEffectInstance o) {
        return ComparisonChain.start().compare(this.desc(), o.desc()).result();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.put("curse", this.save());
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        this.persistentEff = load(compoundTag.getCompound("curse")).persistentEff();
        this.desc = load(compoundTag.getCompound("curse")).desc();
    }

    @Override
    public String toString() {
        return "Curse [desc=" + desc + ", persistentEff=" + persistentEff + "]";
    }
}
