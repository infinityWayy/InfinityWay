package huix.infinity.mixin;

import net.minecraft.world.item.Tiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( Tiers.class)
public class TiersMixin {

    @Overwrite
    public int getUses() {
        return -1;
    }

    @Overwrite
    public float getSpeed() {
        return -10;
    }

    @Overwrite
    public float getAttackDamageBonus() {
        return -10;
    }

    @Overwrite
    public int getEnchantmentValue() {
        return 0;
    }


}
