package huix.infinity.common.item.tool;

import huix.infinity.common.item.tier.IIFWTier;
import huix.infinity.common.item.tool.impl.IFWTieredItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class IFWCudgelItem extends IFWTieredItem {

    public IFWCudgelItem(IIFWTier tier, Properties properties) {
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
