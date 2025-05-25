package huix.infinity.common.world.entity.render.animal;

import huix.infinity.common.world.entity.animal.IFWCow;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IFWCowRenderer extends MobRenderer<IFWCow, CowModel<IFWCow>> {
    private static final ResourceLocation COW_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/cow/cow.png");
    private static final ResourceLocation COW_SICK = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/cow/sick.png");

    public IFWCowRenderer(EntityRendererProvider.Context p_173956_) {
        super(p_173956_, new CowModel<>(p_173956_.bakeLayer(ModelLayers.COW)), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull IFWCow cow) {
        if (cow.isWell()) {
            return COW_LOCATION;
        } else {
            return COW_SICK;
        }
    }
}
