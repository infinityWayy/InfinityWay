package huix.infinity.common.item.tool;

import huix.infinity.common.item.tier.IIFWTier;
import huix.infinity.common.item.tool.impl.IFWDiggerItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class IFWPickaxeItem extends IFWDiggerItem {
    public IFWPickaxeItem(IIFWTier tier, Properties properties) {
        super(tier, 3, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    public IFWPickaxeItem(IIFWTier tier, int c, Properties properties) {
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
