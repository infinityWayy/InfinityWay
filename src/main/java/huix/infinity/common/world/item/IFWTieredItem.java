package huix.infinity.common.world.item;

import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.util.DurabilityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class IFWTieredItem extends Item implements RepairableItem {
    private final IFWTier tier;
    private final float durability;

    public IFWTieredItem(IFWTier tier, int numComponents, Properties properties) {
        super(properties.durability(DurabilityHelper.getMultipliedDurability(numComponents, tier.durability())));
        this.tier = tier;
        this.durability = DurabilityHelper.getMultipliedDurability(numComponents, tier.durability());
    }

    public IFWTieredItem(IFWTier tier, float durability, Properties properties) {
        super(properties.durability((int) durability));
        this.tier = tier;
        this.durability = durability;
    }

    public IFWTier ifwTier() {
        return this.tier;
    }

    public abstract float getDecayRateForBreakingBlock(BlockState state);

    public abstract float getDecayRateForAttackingEntity(ItemStack stack);

    private int getToolDecayFromBreakingBlock(Level level, BlockState state, BlockPos pos) {
        float destroySpeed = state.getDestroySpeed(level, pos);
        if (!level.isClientSide && destroySpeed != 0.0F && !state.is(IFWBlockTags.PORTABLE_BLOCK)) {
            float decay = 100.0F * this.getDecayRateForBreakingBlock(state);
            return Math.max(Math.max((int)(destroySpeed * decay), (int)(decay / 20.0F)), 1);
        }
        return 0;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        Tool tool = stack.get(DataComponents.TOOL);
        if (tool != null && tool.damagePerBlock() > 0 && this.isDamageable(state)) {
            stack.hurtAndBreak(this.getToolDecayFromBreakingBlock(level, state, pos), miningEntity, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    public boolean isDamageable(BlockState state) {
        return false;
    }

    private int toolDecayFromAttackingEntity(ItemStack stack, LivingEntity target) {
        return Math.max((int)(100.0F * this.getDecayRateForAttackingEntity(stack)), 1);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(this.toolDecayFromAttackingEntity(stack, target), attacker, EquipmentSlot.MAINHAND);
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
        return (int) (this.durability / 5);
    }

    @Override
    public int getRepairLevel() {
        return this.ifwTier().repairLevel();
    }

    public float getBaseHarvestEfficiency(BlockState state) {
        return 4.0F;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        if (this.isCorrectToolForDrops(itemStack, state)) {
            return this.getBaseHarvestEfficiency(state) * this.ifwTier().getSpeed();
        }
        return 1.0F;
    }
}