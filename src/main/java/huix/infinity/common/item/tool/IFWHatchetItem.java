package huix.infinity.common.item.tool;

import huix.infinity.common.item.tier.IIFWTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class IFWHatchetItem extends IFWAxeItem {
    public IFWHatchetItem(IIFWTier tier, Properties properties) {
        super(tier, 1, properties);
    }

    @Override
    public float getReachBonus() {
        return 0.5F;
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return super.getDecayRateForBreakingBlock(state) * 4.0F / 3.0F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return super.getDecayRateForAttackingEntity(stack) * 4.0F / 3.0F;
    }
}
