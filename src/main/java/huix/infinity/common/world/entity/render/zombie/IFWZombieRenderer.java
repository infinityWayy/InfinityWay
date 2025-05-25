package huix.infinity.common.world.entity.render.zombie;

import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class IFWZombieRenderer extends AbstractZombieRenderer<IFWZombie, ZombieModel<IFWZombie>> {
    public IFWZombieRenderer(EntityRendererProvider.Context p_174456_) {
        this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public IFWZombieRenderer(EntityRendererProvider.Context context, ModelLayerLocation zombieLayer, ModelLayerLocation innerArmor, ModelLayerLocation outerArmor) {
        super(
                context,
                new ZombieModel<>(context.bakeLayer(zombieLayer)),
                new ZombieModel<>(context.bakeLayer(innerArmor)),
                new ZombieModel<>(context.bakeLayer(outerArmor))
        );
    }
}
