package huix.infinity.common.world.entity.render.zombie;

import huix.infinity.common.world.entity.monster.IFWZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractZombieRenderer<T extends IFWZombie, M extends ZombieModel<T>> extends HumanoidMobRenderer<T, M> {
    private static final ResourceLocation ZOMBIE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/zombie/zombie.png");

    protected AbstractZombieRenderer(EntityRendererProvider.Context context, M model, M innerModel, M outerModel) {
        super(context, model, 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, innerModel, outerModel, context.getModelManager()));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull IFWZombie entity) {
        return ZOMBIE_LOCATION;
    }

    protected boolean isShaking(@NotNull T entity) {
        return super.isShaking(entity) || entity.isUnderWaterConverting();
    }
}