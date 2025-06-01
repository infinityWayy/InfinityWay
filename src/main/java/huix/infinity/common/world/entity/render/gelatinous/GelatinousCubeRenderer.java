package huix.infinity.common.world.entity.render.gelatinous;

import com.mojang.blaze3d.vertex.PoseStack;
import huix.infinity.common.world.entity.monster.gelatinous.GelatinousCube;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class GelatinousCubeRenderer<T extends GelatinousCube> extends MobRenderer<T, SlimeModel<T>> {

    public GelatinousCubeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(ModelLayers.SLIME)), 0.25F);

        this.addLayer(new SlimeOuterLayer<>(this, context.getModelSet()));
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffer, int packedLight) {

        this.shadowRadius = 0.25F * (float)entity.getSize();

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void scale(@NotNull T entity, @NotNull PoseStack poseStack, float partialTickTime) {

        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0F, 0.001F, 0.0F);

        float size = (float)entity.getSize();
        float squish = Mth.lerp(partialTickTime, entity.oSquish, entity.squish) / (size * 0.5F + 1.0F);
        float inverseSquish = 1.0F / (squish + 1.0F);

        poseStack.scale(inverseSquish * size, 1.0F / inverseSquish * size, inverseSquish * size);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
        String entityName = getEntityTextureName(entity);
        return ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID,
                "textures/entity/gelatinous/" + entityName + ".png");
    }

    private String getEntityTextureName(T entity) {
        String className = entity.getClass().getSimpleName().toLowerCase();
        return switch (className) {
            case "jelly" -> "jelly";
            case "blob" -> "blob";
            case "pudding" -> "pudding";
            case "ooze" -> "ooze";
            case "magmacube" -> "magmacube";
            default -> "slime";
        };
    }
}