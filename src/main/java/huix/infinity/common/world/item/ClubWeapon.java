package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ClubWeapon extends IFWTieredItem {

    public ClubWeapon(IFWTier tier, float durability, Properties properties) {
        super(tier, durability, properties);
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return 0.25F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return 0.25F;
    }

    @Override
    public float getReachBonus() {
        return 0.5F;
    }
}
