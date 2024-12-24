package huix.infinity.common.item.tool;

import huix.infinity.common.item.tier.IIFWTier;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class IFWMattockItem extends IFWHoeItem {
    public IFWMattockItem(IIFWTier tier, Properties properties) {
        super(tier, 4 ,properties);
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return super.getDecayRateForBreakingBlock(state) * 0.8F;
    }


    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        return !state.is(BlockTags.MINEABLE_WITH_SHOVEL) && !state.is(BlockTags.MINEABLE_WITH_HOE) ? 1.0F : this.getTier().getSpeed();
    }
}
