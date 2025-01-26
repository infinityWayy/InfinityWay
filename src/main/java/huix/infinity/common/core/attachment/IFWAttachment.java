package huix.infinity.common.core.attachment;

import com.mojang.serialization.Codec;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class IFWAttachment {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, InfinityWay.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> respawn_xp = ATTACHMENT_TYPES.register(
            "respawn_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
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
    public static final Supplier<AttachmentType<Integer>> productionCounter = ATTACHMENT_TYPES.register(
            "productionCounter", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> manurePeriod = ATTACHMENT_TYPES.register(
            "manurePeriod", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> manureCountdown = ATTACHMENT_TYPES.register(
            "manureCountdown", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );



}
