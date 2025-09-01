package huix.infinity.util;

import huix.infinity.common.client.resources.PersistentEffectTextureManager;
import huix.infinity.init.InfinityWay;
import net.minecraft.resources.ResourceLocation;

public class IFWConstants {

    public static PersistentEffectTextureManager persistentEffectTextureManager;

    public static final ResourceLocation PERSISTENT_EFFECTS_ATLAS_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/atlas/persistent_effects");

    public static final ResourceLocation PERSISTENT_EFFECTS_ATLAS_ID =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "persistent_effects");

    public static final ResourceLocation INSULIN_RESISTANCE_ICON =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/gui/sprites/insulin_resistance.png");

    public static final ResourceLocation WITCH_CURSE_ICON =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/gui/sprites/curse.png");

}
