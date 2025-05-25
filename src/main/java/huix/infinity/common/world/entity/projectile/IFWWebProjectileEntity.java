package huix.infinity.common.world.entity.projectile;

import huix.infinity.common.world.entity.IFWEntityType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/**
 * 具体的蛛网投射物实现类
 */
public class IFWWebProjectileEntity extends IFWWebProjectile {

    public IFWWebProjectileEntity(EntityType<? extends IFWWebProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IFWWebProjectileEntity(Level level, LivingEntity shooter) {
        super(IFWEntityType.WEB_PROJECTILE.get(), shooter, level);
    }

    public IFWWebProjectileEntity(EntityType<? extends IFWWebProjectileEntity> entityType, LivingEntity shooter, Level level) {
        super(entityType, shooter, level);
    }
}