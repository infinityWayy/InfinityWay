package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class WarHammerTool extends PickaxeTool {
    public WarHammerTool(IFWTier tier, Properties properties) {
        super(tier, 5, properties);
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return super.getDecayRateForAttackingEntity(stack) * 2.0F / 3.0F;
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return super.getDecayRateForBreakingBlock(state) * 2.0F / 3.0F;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        return super.getDestroySpeed(itemStack, state) * 0.75F;
    }
}
