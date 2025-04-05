package huix.infinity.common.world.curse;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.common.util.DataComponentUtil;

import java.util.Objects;

public class Curse extends PersistentEffect {
    private final String desc;

    public Curse(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return this.desc;
    }

    public String displayDesc() {
        return this.getDescriptionId() + ".desc";
    }

    @Override
    public String toString() {
        return "Curse [desc=" + desc + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return desc.equals(((Curse) obj).desc());
    }
}
