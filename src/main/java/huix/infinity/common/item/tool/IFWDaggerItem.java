package huix.infinity.common.item.tool;

import huix.infinity.common.item.tier.IIFWTier;

public class IFWDaggerItem extends IFWSwordItem{
    public IFWDaggerItem(IIFWTier p_43269_, Properties p_43272_) {
        super(p_43269_,  1, p_43272_);
    }

    @Override
    public float getReachBonus() {
        return 0.5F;
    }
}
