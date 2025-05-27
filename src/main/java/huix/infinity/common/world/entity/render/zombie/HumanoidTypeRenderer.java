package huix.infinity.common.world.entity.render.zombie;

import huix.infinity.common.world.entity.monster.Ghoul;
import huix.infinity.common.world.entity.monster.Wight;
import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * IFW食尸鬼和亡灵渲染器 - 具有僵尸特征的不死生物
 */
public class HumanoidTypeRenderer<T extends IFWZombie> extends MobRenderer<T, ZombieModel<T>> {
    private static final ResourceLocation GHOUL_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/zombie/ghoul.png");
    private static final ResourceLocation WIGHT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/zombie/wight.png");

    public HumanoidTypeRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
        return switch (entity) {
            case Wight wight -> WIGHT_TEXTURE;
            case Ghoul ghoul -> GHOUL_TEXTURE;
            default -> GHOUL_TEXTURE; // 默认纹理
        };
    }
}