package huix.infinity.common.item.tool;

import huix.infinity.common.item.tier.IIFWTier;

public class IFWDaggerItem extends IFWSwordItem {
    public IFWDaggerItem(IIFWTier tier, Properties properties) {
        super(tier,  1, properties);
    }

    public IFWDaggerItem(IIFWTier tier, int c, Properties properties) {
        super(tier,  c, properties);
    }


    @Override
    public float getReachBonus() {
        return 0.5F;
    }
}
