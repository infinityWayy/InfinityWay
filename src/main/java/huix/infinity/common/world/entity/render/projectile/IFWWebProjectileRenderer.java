package huix.infinity.common.world.entity.render.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import huix.infinity.common.world.entity.projectile.IFWWebProjectileEntity;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * 蛛网投射物渲染器
 * 渲染飞行中的蛛网投射物，包含旋转和脉动效果
 */
public class IFWWebProjectileRenderer extends EntityRenderer<IFWWebProjectileEntity> {
    private static final ResourceLocation WEB_PROJECTILE_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/projectile/web_projectile.png");
    private static final ResourceLocation WEB_PROJECTILE_BURNING_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/projectile/web_projectile_burning.png");

    public IFWWebProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(IFWWebProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 基础缩放
        float scale = 0.6F;
        poseStack.scale(scale, scale, scale);

        // 旋转效果
        float rotation = (entity.tickCount + partialTick) * 20.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.mulPose(Axis.XP.rotationDegrees(rotation * 0.8F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation * 0.5F));

        // 脉动效果
        float pulse = 1.0F + 0.15F * Mth.sin((entity.tickCount + partialTick) * 0.4F);
        poseStack.scale(pulse, pulse, pulse);

        // 燃烧时的额外效果
        if (entity.isBurning()) {
            float fireScale = 1.0F + 0.3F * Mth.sin((entity.tickCount + partialTick) * 0.6F);
            poseStack.scale(fireScale, fireScale, fireScale);
        }

        // 渲染四边形投射物
        renderQuad(poseStack, buffer, entity, packedLight);

        poseStack.popPose();
    }

    /**
     * 渲染四边形投射物
     */
    private void renderQuad(PoseStack poseStack, MultiBufferSource buffer, IFWWebProjectileEntity entity, int packedLight) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();

        // 选择渲染类型
        RenderType renderType = entity.isBurning() ?
                RenderType.entityCutoutNoCull(WEB_PROJECTILE_BURNING_LOCATION) :
                RenderType.entityTranslucent(WEB_PROJECTILE_LOCATION);

        VertexConsumer vertexConsumer = buffer.getBuffer(renderType);

        // 渲染四边形
        float size = 0.5F;

        // 四个顶点
        vertex(vertexConsumer, matrix4f, pose, packedLight, -size, -size, 0.0F, 0.0F, 1.0F);
        vertex(vertexConsumer, matrix4f, pose, packedLight, size, -size, 0.0F, 1.0F, 1.0F);
        vertex(vertexConsumer, matrix4f, pose, packedLight, size, size, 0.0F, 1.0F, 0.0F);
        vertex(vertexConsumer, matrix4f, pose, packedLight, -size, size, 0.0F, 0.0F, 0.0F);
    }

    /**
     * 添加顶点
     */
    private void vertex(VertexConsumer consumer, Matrix4f matrix, PoseStack.Pose pose, int light, float x, float y, float z, float u, float v) {
        consumer.addVertex(matrix, x, y, z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(IFWWebProjectileEntity entity) {
        return entity.isBurning() ? WEB_PROJECTILE_BURNING_LOCATION : WEB_PROJECTILE_LOCATION;
    }
}