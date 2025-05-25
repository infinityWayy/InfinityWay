package huix.infinity.common.world.entity.render.zombie;

import huix.infinity.common.world.entity.monster.digger.Revenant;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RevenantRenderer extends AbstractZombieRenderer<Revenant, ZombieModel<Revenant>> {
    // Revenant基础纹理
    private static final ResourceLocation REVENANT_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/zombie/revenant.png");
    // 发光眼睛纹理
    private static final ResourceLocation REVENANT_EYES_LOCATION =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/zombie/revenant_glow.png");

    public RevenantRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public RevenantRenderer(EntityRendererProvider.Context context,
                            ModelLayerLocation zombieLayer,
                            ModelLayerLocation innerArmor,
                            ModelLayerLocation outerArmor) {
        super(
                context,
                new ZombieModel<>(context.bakeLayer(zombieLayer)),
                new ZombieModel<>(context.bakeLayer(innerArmor)),
                new ZombieModel<>(context.bakeLayer(outerArmor))
        );

        // 添加发光眼睛渲染层
        this.addLayer(new EyesLayer<Revenant, ZombieModel<Revenant>>(this) {
            @Override
            public @NotNull RenderType renderType() {
                return RenderType.eyes(REVENANT_EYES_LOCATION);
            }
        });
    }

    /**
     * 重写此方法以使用自定义Revenant纹理而不是原版僵尸纹理
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Revenant entity) {
        return REVENANT_LOCATION;
    }

    @Override
    protected boolean isShaking(@NotNull Revenant entity) {
        return super.isShaking(entity) || entity.isUnderWaterConverting();
    }
}