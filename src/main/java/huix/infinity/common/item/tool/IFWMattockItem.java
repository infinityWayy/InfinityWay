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
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        float destroySpeed = state.getDestroySpeed(level, pos);
        if (!level.isClientSide() && destroySpeed != 0.0F) {
            if (!state.isSolid()) {
                stack.hurtAndBreak(Math.round(destroySpeed * 100.0F), miningEntity, EquipmentSlot.MAINHAND);
            } else if (state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
                stack.hurtAndBreak(Math.round(destroySpeed * 40.0F), miningEntity, EquipmentSlot.MAINHAND);
            } else {
                stack.hurtAndBreak(Math.round(destroySpeed * 10.0F), miningEntity, EquipmentSlot.MAINHAND);
            }
        }

        return true;

    }


    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        return !state.is(BlockTags.MINEABLE_WITH_SHOVEL) && !state.is(BlockTags.MINEABLE_WITH_HOE) ? 1.0F : this.getTier().getSpeed();
    }
}
