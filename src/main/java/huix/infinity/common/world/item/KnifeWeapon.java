package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;

public class KnifeWeapon extends DaggerWeapon {
    public KnifeWeapon(IFWTier tier, Properties properties) {
        super(tier, properties);
    }

    public KnifeWeapon(IFWTier tier, float durability, Properties properties) {
        super(tier, durability,  properties);
    }

    @Override
    public float getReachBonus() {
        return 0.25F;
    }

}
