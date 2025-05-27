package huix.infinity.common.world.entity.render.zombie;

import com.mojang.blaze3d.vertex.PoseStack;
import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.entity.monster.InvisibleStalker;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InvisibleStalkerRender extends HumanoidMobRenderer<InvisibleStalker, HumanoidModel<InvisibleStalker>> {

    private static final ResourceLocation TEXTURE_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/zombie/invisible_stalker.png");

    public InvisibleStalkerRender(EntityRendererProvider.Context context) {
        super(context, new CustomZombieModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(InvisibleStalker entity) {
        return TEXTURE_LOCATION;
    }

    @Override
    protected float getShadowRadius(InvisibleStalker entity) {
        return 0.0F;
    }

    @Override
    protected @Nullable RenderType getRenderType(InvisibleStalker entity, boolean bodyVisible,
                                                 boolean translucent, boolean glowing) {
        ResourceLocation resourcelocation = this.getTextureLocation(entity);

        if (bodyVisible) {
            return RenderType.entityTranslucent(resourcelocation);
        } else {
            return glowing ? RenderType.outline(resourcelocation) : null;
        }
    }

    protected float getAlpha(InvisibleStalker entity) {
        return 0.05f;
    }

    @Override
    protected boolean isBodyVisible(InvisibleStalker entity) {
        return true;
    }

    private static class CustomZombieModel extends HumanoidModel<InvisibleStalker> {

        public CustomZombieModel(net.minecraft.client.model.geom.ModelPart modelPart) {
            super(modelPart);
        }

        @Override
        public void setupAnim(InvisibleStalker entity, float limbSwing, float limbSwingAmount,
                              float ageInTicks, float netHeadYaw, float headPitch) {
            // 先调用父类的setupAnim
            super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            // 强制设置僵尸手臂姿态
            this.rightArm.xRot = -1.5F;
            this.leftArm.xRot = -1.5F;
            this.rightArm.yRot = 0.0F;
            this.leftArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
        }
    }
}