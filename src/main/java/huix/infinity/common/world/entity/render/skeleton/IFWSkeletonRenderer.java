package huix.infinity.common.world.entity.render.skeleton;

import huix.infinity.common.world.entity.monster.skeleton.*;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.jetbrains.annotations.NotNull;

public class IFWSkeletonRenderer extends SkeletonRenderer {
    // 原版材质
    private static final ResourceLocation SKELETON_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/skeleton/skeleton.png");

    // 自定义材质
    private static final ResourceLocation LONGDEAD_TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/skeleton/longdead.png");
    private static final ResourceLocation LONGDEAD_GUARDIAN_TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/skeleton/longdead_guardian.png");
    private static final ResourceLocation LONGDEAD_GUARDIAN_GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/skeleton/longdead_guardian_glow.png");
    private static final ResourceLocation BONE_LORD_TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/skeleton/bone_lord.png");
    private static final ResourceLocation BONE_LORD_GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/skeleton/bone_lord_glow.png");
    private static final ResourceLocation ANCIENT_BONE_LORD_TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/skeleton/bone_lord.png");
    private static final ResourceLocation ANCIENT_BONE_LORD_GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/skeleton/bone_lord_glow.png");

    public IFWSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context);

        // 添加发光眼睛渲染层 - 修复版本
        this.addLayer(new EyesLayer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>>(this) {
            @Override
            public @NotNull RenderType renderType() {
                // 这个方法不应该在这里决定是否渲染，而是返回渲染类型
                // 实际的渲染决定应该在 render 方法中通过 shouldRender 来控制
                return RenderType.eyes(SKELETON_TEXTURE); // 默认材质，实际材质在render方法中决定
            }

            @Override
            public void render(@NotNull com.mojang.blaze3d.vertex.PoseStack poseStack,
                               @NotNull net.minecraft.client.renderer.MultiBufferSource buffer,
                               int packedLight,
                               @NotNull AbstractSkeleton entity,
                               float limbSwing, float limbSwingAmount,
                               float partialTicks, float ageInTicks,
                               float netHeadYaw, float headPitch) {

                // 首先检查是否应该渲染发光效果
                if (!shouldRenderGlow(entity)) {
                    return; // 不渲染发光效果
                }

                // 获取对应的发光材质
                ResourceLocation glowTexture = getGlowTextureForEntity(entity);
                if (glowTexture == null) {
                    return; // 没有发光材质，不渲染
                }

                // 使用正确的发光材质渲染
                RenderType renderType = RenderType.eyes(glowTexture);
                var vertexConsumer = buffer.getBuffer(renderType);
                this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight,
                        net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords(entity, 0.0F),
                        -1);
            }

            /**
             * 检查是否应该渲染发光效果
             */
            private boolean shouldRenderGlow(@NotNull AbstractSkeleton entity) {
                // 只有特定类型的骷髅才渲染发光眼睛
                return entity instanceof LongdeadGuardian ||
                        entity instanceof BoneLord ||
                        entity instanceof AncientBoneLord;
            }
        });
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractSkeleton entity) {
        // 根据实体类型返回对应的材质
        if (entity instanceof AncientBoneLord) {
            return ANCIENT_BONE_LORD_TEXTURE;
        } else if (entity instanceof BoneLord) {
            return BONE_LORD_TEXTURE;
        } else if (entity instanceof LongdeadGuardian) {
            return LONGDEAD_GUARDIAN_TEXTURE;
        } else if (entity instanceof Longdead) {
            return LONGDEAD_TEXTURE;
        } else if (entity instanceof IFWSkeleton) {
            // 根据骷髅类型返回原版材质
            return SKELETON_TEXTURE;
        }
        // 默认返回普通骷髅材质
        return SKELETON_TEXTURE;
    }

    /**
     * 根据实体类型获取对应的发光材质
     */
    private static ResourceLocation getGlowTextureForEntity(AbstractSkeleton entity) {
        if (entity instanceof AncientBoneLord) {
            return ANCIENT_BONE_LORD_GLOW_TEXTURE;
        } else if (entity instanceof BoneLord) {
            return BONE_LORD_GLOW_TEXTURE;
        } else if (entity instanceof LongdeadGuardian) {
            return LONGDEAD_GUARDIAN_GLOW_TEXTURE;
        }
        return null;
    }
}