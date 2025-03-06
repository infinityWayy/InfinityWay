package huix.infinity.common.world.entity.render;

import huix.infinity.common.world.entity.animal.IFWPig;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IFWPigRenderer extends MobRenderer<IFWPig, PigModel<IFWPig>> {
    private static final ResourceLocation PIG_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/pig/pig.png");
    private static final ResourceLocation PIG_SICK = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/pig/sick.png");

    public IFWPigRenderer(EntityRendererProvider.Context p_174340_) {
        super(p_174340_, new PigModel<>(p_174340_.bakeLayer(ModelLayers.PIG)), 0.7F);
        this.addLayer(
                new SaddleLayer<>(
                        this, new PigModel<>(p_174340_.bakeLayer(ModelLayers.PIG_SADDLE)), ResourceLocation.withDefaultNamespace("textures/entity/pig/pig_saddle.png")
                )
        );
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(IFWPig pig) {
        if (pig.isWell()) {
            return PIG_LOCATION;
        } else {
            return PIG_SICK;
        }
    }
}
