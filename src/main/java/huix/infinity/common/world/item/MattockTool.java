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
    public float getBaseHarvestEfficiency(BlockState state) {
        return super.getBaseHarvestEfficiency(state) * 0.75F;
    }
}
