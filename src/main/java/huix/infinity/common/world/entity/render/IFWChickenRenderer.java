package huix.infinity.common.world.entity.render;

import huix.infinity.common.world.entity.animal.IFWChicken;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class IFWChickenRenderer extends MobRenderer<IFWChicken, ChickenModel<IFWChicken>> {
    private static final ResourceLocation CHICKEN_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/chicken.png");
    private static final ResourceLocation CHICKEN_SICK = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/chicken/sick.png");
    public IFWChickenRenderer(EntityRendererProvider.Context context) {
        super(context, new ChickenModel<>(context.bakeLayer(ModelLayers.CHICKEN)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull IFWChicken chicken) {
        if (chicken.isWell()) {
            return CHICKEN_LOCATION;
        } else {
            return CHICKEN_SICK;
        }
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    @Override
    protected float getBob(IFWChicken livingBase, float partialTicks) {
        float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float f1 = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }
}
