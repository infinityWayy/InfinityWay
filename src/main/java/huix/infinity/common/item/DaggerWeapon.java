package huix.infinity.common.item;

import huix.infinity.common.item.tier.IIFWTier;

public class DaggerWeapon extends SwordWeapon {
    public DaggerWeapon(IIFWTier tier, Properties properties) {
        super(tier,  1, properties);
    }

    public DaggerWeapon(IIFWTier tier, int c, Properties properties) {
        super(tier,  c, properties);
    }


    @Override
    public float getReachBonus() {
        return 0.5F;
    }
}
