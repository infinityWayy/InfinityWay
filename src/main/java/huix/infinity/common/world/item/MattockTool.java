package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class MattockTool extends HoeTool {
    public MattockTool(IFWTier tier, Properties properties) {
        super(tier, 4 ,properties);
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return super.getDecayRateForBreakingBlock(state) * 0.8F;
    }


    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        return !state.is(BlockTags.MINEABLE_WITH_SHOVEL) && !state.is(BlockTags.MINEABLE_WITH_HOE) ? 1.0F : this.ifwTier().getSpeed();
    }
}
