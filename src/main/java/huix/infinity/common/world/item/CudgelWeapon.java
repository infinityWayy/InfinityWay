package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IIFWTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class CudgelWeapon extends IFWTieredItem {

    public CudgelWeapon(IIFWTier tier, Properties properties) {
        super(tier, 2, properties);
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return 0.25F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return 0.25F;
    }
}
