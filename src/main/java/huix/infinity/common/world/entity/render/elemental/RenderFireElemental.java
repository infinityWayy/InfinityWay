package huix.infinity.common.world.entity.render.elemental;

import huix.infinity.common.world.entity.monster.elemental.EntityFireElemental;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenderFireElemental extends HumanoidMobRenderer<EntityFireElemental, RenderFireElemental.CustomModel> {

    private static final ResourceLocation TEXTURE_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/fire_elemental/fire_elemental.png");

    public RenderFireElemental(EntityRendererProvider.Context context) {
        super(context, new CustomModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EntityFireElemental entity) {
        return TEXTURE_LOCATION;
    }

    @Override
    protected float getShadowRadius(@NotNull EntityFireElemental entity) {
        return 0.0F;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull EntityFireElemental entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        ResourceLocation resourcelocation = this.getTextureLocation(entity);

        if (bodyVisible) {
            return RenderType.entityTranslucent(resourcelocation);
        } else {
            return glowing ? RenderType.outline(resourcelocation) : null;
        }
    }

    protected float getAlpha(EntityFireElemental entity) {
        return 0.05f;
    }

    @Override
    protected boolean isBodyVisible(@NotNull EntityFireElemental entity) {
        return true;
    }

    public static class CustomModel extends HumanoidModel<EntityFireElemental> {
        public CustomModel(net.minecraft.client.model.geom.ModelPart modelPart) {
            super(modelPart);
        }

        @Override
        public void setupAnim(@NotNull EntityFireElemental entity, float limbSwing, float limbSwingAmount,
                              float ageInTicks, float netHeadYaw, float headPitch) {
            super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        }
    }
}