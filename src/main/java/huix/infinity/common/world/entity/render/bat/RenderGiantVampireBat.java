package huix.infinity.common.world.entity.render.bat;

import com.mojang.blaze3d.vertex.PoseStack;
import huix.infinity.common.client.model.ModelLayers;
import huix.infinity.common.world.entity.monster.bat.EntityGiantVampireBat;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderGiantVampireBat extends MobRenderer<EntityGiantVampireBat, VampireBatModel<EntityGiantVampireBat>> {
    private static final ResourceLocation VAMPIRE_BAT_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/bat/vampire.png");

    public RenderGiantVampireBat(EntityRendererProvider.Context context) {
        super(context, new VampireBatModel<>(context.bakeLayer(ModelLayers.GIANT_VAMPIRE_BAT)), 0.3F);
    }

    @Override
    protected void scale(@NotNull EntityGiantVampireBat entity, @NotNull PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.5F, 1.5F, 1.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EntityGiantVampireBat entity) {
        return VAMPIRE_BAT_LOCATION;
    }

}