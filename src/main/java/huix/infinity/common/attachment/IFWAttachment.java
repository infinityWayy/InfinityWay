package huix.infinity.common.attachment;

import com.mojang.serialization.Codec;
import huix.infinity.init.InfinityWay;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class IFWAttachment {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, InfinityWay.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> respawn_xp = ATTACHMENT_TYPES.register(
            "respawn_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );



}
