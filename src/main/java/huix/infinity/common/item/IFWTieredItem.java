package huix.infinity.common.item;

import huix.infinity.common.item.tier.IIFWTier;
import huix.infinity.common.tag.IFWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class IFWTieredItem extends Item implements IRepairableItem {
    private final IIFWTier tier;
    private final int numComponents;

    public IFWTieredItem(IIFWTier tier, int numComponents, Properties properties) {
        super(properties.durability(DurationHelper.getMultipliedDurability(numComponents, tier.getDurability())));
        this.tier = tier;
        this.numComponents = numComponents;
    }

    public Tier getTier() {
        return this.tier;
    }

    public abstract float getDecayRateForBreakingBlock(BlockState state);

    public abstract float getDecayRateForAttackingEntity(ItemStack stack);

    public final int getToolDecayFromBreakingBlock(Level level, BlockState state, BlockPos pos) {
        float destroySpeed = state.getDestroySpeed(level, pos);

        if (!level.isClientSide && destroySpeed != 0.0F && !state.is(IFWBlockTags.PORTABLE_BLOCK)) {
            float decay = 100.0F * this.getDecayRateForBreakingBlock(state);
            return Math.max(Math.max((int)(destroySpeed * decay), (int)(decay / 20.0F)), 1);
        }
        return 0;
    }

    public final int getToolDecayFromAttackingEntity(ItemStack stack, LivingEntity livingEntity) {
        return Math.max((int)(100.0F * this.getDecayRateForAttackingEntity(stack)), 1);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        Tool tool = stack.get(DataComponents.TOOL);
        if (tool == null) {
            return false;
        } else {
            if (tool.damagePerBlock() > 0 && this.isDamageable(state)) {
                stack.hurtAndBreak(this.getToolDecayFromBreakingBlock(level, state, pos), miningEntity, EquipmentSlot.MAINHAND);
            }

            return true;
        }
    }

    public boolean isDamageable(BlockState state) {
        return false;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(getToolDecayFromAttackingEntity(stack, target), attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public int getRepairCost() {
        return this.numComponents * 2;
    }

}
