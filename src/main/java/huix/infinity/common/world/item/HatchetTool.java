package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IIFWTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class HatchetTool extends AxeTool {
    public HatchetTool(IIFWTier tier, Properties properties) {
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
