package huix.infinity.util;

import huix.infinity.common.client.resources.PersistentEffectTextureManager;
import huix.infinity.init.InfinityWay;
import net.minecraft.resources.ResourceLocation;

public class IFWConstants {
    public static PersistentEffectTextureManager persistentEffectTextureManager;
    public static final ResourceLocation LOCATION_P_EFFECT_TEXTURE_ATLAS = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/atlas/persistent_effects.png");
}
