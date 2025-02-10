package huix.infinity.common.world.item.tier;

import net.minecraft.world.item.Tier;

public interface IFWTier extends Tier {

    int durability();

    IFWQuality quality();

    public float acidResistance();

    float repairDurability();

    int repairLevel();
}
