package huix.infinity.common.world.curse;

import huix.infinity.common.world.effect.PersistentEffect;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.util.DataComponentUtil;

import java.util.Objects;

public class Curse extends PersistentEffect {

    public static final StreamCodec<FriendlyByteBuf, Curse> CURSE_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, Curse::desc,
            Curse::new);

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
}
