package huix.infinity.common.world.entity.render;

import huix.infinity.common.world.entity.animal.IFWSheep;
import huix.infinity.common.world.entity.render.sheep.SheepFurLayer;
import huix.infinity.common.world.entity.render.sheep.SheepModel;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IFWSheepRenderer extends MobRenderer<IFWSheep, SheepModel<IFWSheep>> {
    private static final ResourceLocation SHEEP_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/sheep/sheep.png");
    private static final ResourceLocation SHEEP_SICK = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/sheep/sick.png");
    public IFWSheepRenderer(EntityRendererProvider.Context context) {
        super(context, new SheepModel<>(context.bakeLayer(ModelLayers.SHEEP)), 0.7F);
        this.addLayer(new SheepFurLayer(this, context.getModelSet()));
    }

    // Returns the location of an entity's texture.
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull IFWSheep sheep) {
        if (sheep.isWell()) {
            return SHEEP_LOCATION;
        } else {
            return SHEEP_SICK;
        }
    }
}
