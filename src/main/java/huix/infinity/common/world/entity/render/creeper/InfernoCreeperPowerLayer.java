package huix.infinity.common.world.entity.render.creeper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import huix.infinity.common.world.entity.monster.InfernoCreeper;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class InfernoCreeperPowerLayer extends RenderLayer<InfernoCreeper, CreeperModel<InfernoCreeper>> {
    private static final ResourceLocation POWER_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<InfernoCreeper> model;

    public InfernoCreeperPowerLayer(RenderLayerParent<InfernoCreeper, CreeperModel<InfernoCreeper>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new CreeperModel<>(modelSet.bakeLayer(ModelLayers.CREEPER_ARMOR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, InfernoCreeper creeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (creeper.isPowered()) {
            poseStack.pushPose();

            float f = (float)creeper.tickCount + partialTicks;

            // 创建脉动的缩放效果
            float pulse = Mth.sin(f * 0.1F) * 0.05F; // 脉动幅度
            float baseScale = 1.0F;
            float scale = baseScale + pulse;
            poseStack.scale(scale, scale, scale);
            EntityModel<InfernoCreeper> entitymodel = this.model;
            entitymodel.prepareMobModel(creeper, limbSwing, limbSwingAmount, partialTicks);
            this.getParentModel().copyPropertiesTo(entitymodel);

            // 使用更快的动画速度让闪电效果更明显
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.energySwirl(this.getTextureLocation(), this.xOffset(f) % 1.0F, f * 0.02F % 1.0F));
            entitymodel.setupAnim(creeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            entitymodel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

            poseStack.popPose();
        }
    }

    protected float xOffset(float tickCount) {
        return tickCount * 0.02F;
    }

    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }
}