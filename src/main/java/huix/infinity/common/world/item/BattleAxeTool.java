package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IIFWTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class BattleAxeTool extends AxeTool {
    public BattleAxeTool(IIFWTier tier, Properties properties) {
        super(tier, 5, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        return super.getDestroySpeed(itemStack, state) * 0.75F;
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return super.getDecayRateForBreakingBlock(state) * 1.25F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return super.getDecayRateForAttackingEntity(stack) * 0.75F;
    }
}
