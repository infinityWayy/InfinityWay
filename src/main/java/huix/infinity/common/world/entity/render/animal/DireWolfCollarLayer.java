package huix.infinity.common.world.entity.render.animal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import huix.infinity.common.world.entity.monster.IFWDireWolf;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DireWolfCollarLayer<T extends IFWDireWolf, M extends DireWolfModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation COLLAR_TEXTURE = ResourceLocation.fromNamespaceAndPath("ifw", "textures/entity/dire_wolf/dire_wolf_collar.png");

    public DireWolfCollarLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, T entity,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isTame() && !entity.isInvisible()) {
            int color = entity.getCollarColor().getTextureDiffuseColor();
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(COLLAR_TEXTURE));
            this.getParentModel().renderToBuffer(
                    poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, color
            );
        }
    }
}