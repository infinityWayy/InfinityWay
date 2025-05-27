package huix.infinity.common.world.entity.render.creeper;

import huix.infinity.common.world.entity.monster.InfernoCreeper;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class InfernoCreeperRenderer extends MobRenderer<InfernoCreeper, CreeperModel<InfernoCreeper>> {
    private static final ResourceLocation CREEPER_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/creeper/inferno_creeper.png");

    public InfernoCreeperRenderer(EntityRendererProvider.Context context) {
        super(context, new CreeperModel<>(context.bakeLayer(ModelLayers.CREEPER)), 0.5F);
        this.addLayer(new InfernoCreeperPowerLayer(this, context.getModelSet()));
    }

    @Override
    protected void scale(InfernoCreeper creeper, com.mojang.blaze3d.vertex.PoseStack poseStack, float partialTickTime) {
        float f = creeper.getSwelling(partialTickTime);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f *= f;
        f *= f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        poseStack.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(InfernoCreeper creeper, float partialTicks) {
        float f = creeper.getSwelling(partialTicks);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(InfernoCreeper entity) {
        return CREEPER_LOCATION;
    }
}