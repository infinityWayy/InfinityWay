package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;

public class DaggerWeapon extends SwordWeapon {
    public DaggerWeapon(IFWTier tier, Properties properties) {
        super(tier,  1, properties);
    }

    public DaggerWeapon(IFWTier tier, int c, Properties properties) {
        super(tier,  c, properties);
    }

    public DaggerWeapon(IFWTier tier, float durability, Properties properties) {
        super(tier, durability,  properties);
    }


    @Override
    public float getReachBonus() {
        return 0.5F;
    }
}
