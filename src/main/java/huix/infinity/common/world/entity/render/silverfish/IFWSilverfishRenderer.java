package huix.infinity.common.world.entity.render.silverfish;

import huix.infinity.common.world.entity.monster.silverfish.*;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IFWSilverfishRenderer extends MobRenderer<IFWSilverfish, SilverfishModel<IFWSilverfish>> {

    private static final ResourceLocation SILVERFISH_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/silverfish.png");
    private static final ResourceLocation COPPERSPINE_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/silverfish/copperspine.png");
    private static final ResourceLocation HOARY_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/silverfish/hoary.png");
    private static final ResourceLocation NETHERSPAWN_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/silverfish/netherspawn.png");

    public IFWSilverfishRenderer(EntityRendererProvider.Context context) {
        super(context, new SilverfishModel<>(context.bakeLayer(ModelLayers.SILVERFISH)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull IFWSilverfish entity) {
        if (entity instanceof Copperspine) return COPPERSPINE_LOCATION;
        if (entity instanceof HoarySilverfish) return HOARY_LOCATION;
        if (entity instanceof NetherSilverFish) return NETHERSPAWN_LOCATION;
        return SILVERFISH_LOCATION;
    }

    @Override
    protected float getFlipDegrees(@NotNull IFWSilverfish entity) {
        return 180.0F;
    }
}