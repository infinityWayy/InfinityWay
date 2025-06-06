package huix.infinity.common.client.resources;

import huix.infinity.init.InfinityWay;
import huix.infinity.util.IFWConstants;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PersistentEffectTextureManager extends TextureAtlasHolder {

    public PersistentEffectTextureManager(TextureManager textureManager) {
        super(textureManager, IFWConstants.LOCATION_P_EFFECT_TEXTURE_ATLAS, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "atlases/persistent_effects"));
    }

    public TextureAtlasSprite getCurse() {
        return this.getSprite(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "gui/sprites/curse"));
    }

    public TextureAtlasSprite getInsulinResistance() {
        return this.getSprite(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "gui/sprites/insulin_resistance"));
    }

}
