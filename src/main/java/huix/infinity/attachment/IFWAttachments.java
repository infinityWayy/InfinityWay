package huix.infinity.attachment;

import com.mojang.serialization.Codec;
import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.curse.PersistentEffectInstance;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.common.world.curse.Curses;
import huix.infinity.init.InfinityWay;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class IFWAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, InfinityWay.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> respawn_xp = ATTACHMENT_TYPES.register(
            "respawn_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Integer>> armor_effect = ATTACHMENT_TYPES.register(
            "armor_effect", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static final Supplier<AttachmentType<Integer>> cooking_level = ATTACHMENT_TYPES.register(
            "cooking_level", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static final Supplier<AttachmentType<Float>> food = ATTACHMENT_TYPES.register(
            "food", () -> AttachmentType.builder(() -> 0.0F).serialize(Codec.FLOAT).build()
    );
    public static final Supplier<AttachmentType<Float>> water = ATTACHMENT_TYPES.register(
            "water", () -> AttachmentType.builder(() -> 0.0F).serialize(Codec.FLOAT).build()
    );
    public static final Supplier<AttachmentType<Float>> freedom = ATTACHMENT_TYPES.register(
            "freedom", () -> AttachmentType.builder(() -> 0.0F).serialize(Codec.FLOAT).build()
    );
    public static final Supplier<AttachmentType<Integer>> production_counter = ATTACHMENT_TYPES.register(
            "production_counter", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> manure_period = ATTACHMENT_TYPES.register(
            "manure_period", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> manure_countdown = ATTACHMENT_TYPES.register(
            "manure_countdown", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Boolean>> is_panic = ATTACHMENT_TYPES.register(
            "is_panic", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build()
    );

    public static final Supplier<AttachmentType<PersistentEffectInstance>> player_curse = ATTACHMENT_TYPES.register(
            "player_curse", () -> AttachmentType.builder(() -> new PersistentEffectInstance(Curses.none)).serialize(PersistentEffectInstance.CODEC).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Boolean>> learned_curse = ATTACHMENT_TYPES.register(
            "learned_curse", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().build()
    );

}
