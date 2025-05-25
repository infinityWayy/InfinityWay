package huix.infinity.common.world.entity.render.zombie;

import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.geom.ModelPart;

public class ZombieModel<T extends IFWZombie> extends AbstractZombieModel<T> {
    public ZombieModel(ModelPart root) {
        super(root);
    }

    public boolean isAggressive(T entity) {
        return entity.isAggressive();
    }
}
