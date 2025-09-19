package huix.infinity.common.world.entity.render.bat;

import huix.infinity.common.world.entity.monster.bat.EntityVampireBat;
import huix.infinity.init.InfinityWay;
import huix.infinity.common.client.model.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import com.mojang.blaze3d.vertex.PoseStack;

public class RenderVampireBat extends MobRenderer<EntityVampireBat, VampireBatModel<EntityVampireBat>> {

    private static final ResourceLocation VAMPIRE_BAT_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/bat/vampire.png");
    private static final ResourceLocation NIGHTWING_BAT_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/bat/nightwing.png");
    private static final ResourceLocation VAMPIRE_BAT_GLOW_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/bat/vampire_glow.png");

    public RenderVampireBat(EntityRendererProvider.Context context) {
        super(context, new VampireBatModel<>(context.bakeLayer(ModelLayers.VAMPIRE_BAT)), 0.3F);

        this.addLayer(new EyesLayer<>(this) {

            @Override
            public @NotNull RenderType renderType() {
                return RenderType.eyes(VAMPIRE_BAT_GLOW_LOCATION);
            }

        });
    }

    @Override
    protected void scale(@NotNull EntityVampireBat entity, @NotNull PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.0F, 1.0F, 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EntityVampireBat entity) {
        if (entity.isNightwing()) return NIGHTWING_BAT_LOCATION;
        return VAMPIRE_BAT_LOCATION;
    }

}