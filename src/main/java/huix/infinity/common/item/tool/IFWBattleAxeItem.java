package huix.infinity.common.item.tool;

import huix.infinity.common.item.tier.IIFWTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class IFWBattleAxeItem extends IFWAxeItem{
    public IFWBattleAxeItem(IIFWTier tier, Properties properties) {
        super(tier, 5, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        return super.getDestroySpeed(itemStack, state) * 0.75F;
    }
}
