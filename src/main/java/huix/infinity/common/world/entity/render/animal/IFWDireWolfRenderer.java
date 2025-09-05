package huix.infinity.common.world.entity.render.animal;

import huix.infinity.common.world.entity.monster.IFWDireWolf;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import huix.infinity.common.client.model.ModelLayers;

public class IFWDireWolfRenderer extends MobRenderer<IFWDireWolf, DireWolfModel<IFWDireWolf>> {
    private static final ResourceLocation DIRE_WOLF_TEXTURE = ResourceLocation.fromNamespaceAndPath("ifw", "textures/entity/dire_wolf/dire_wolf.png");
    private static final ResourceLocation DIRE_WOLF_ANGRY_TEXTURE = ResourceLocation.fromNamespaceAndPath("ifw", "textures/entity/dire_wolf/dire_wolf_angry.png");

    public IFWDireWolfRenderer(EntityRendererProvider.Context context) {
        super(context, new DireWolfModel<>(context.bakeLayer(ModelLayers.DIRE_WOLF)), 0.5F);
        this.addLayer(new DireWolfCollarLayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull IFWDireWolf entity) {
        if (entity.isAngry()) {
            return DIRE_WOLF_ANGRY_TEXTURE;
        } else {
            return DIRE_WOLF_TEXTURE;
        }
    }
}