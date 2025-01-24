package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class PickaxeTool extends IFWDiggerItem {
    public PickaxeTool(IFWTier tier, Properties properties) {
        super(tier, 3, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    public PickaxeTool(IFWTier tier, int c, Properties properties) {
        super(tier, c, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return 1.0F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return 1.0F;
    }

}
