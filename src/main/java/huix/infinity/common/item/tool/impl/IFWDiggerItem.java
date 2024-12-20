package huix.infinity.common.item.tool.impl;

import huix.infinity.common.item.tier.IIFWTier;
import huix.infinity.funextension.BlockStateBaseExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class IFWDiggerItem extends IFWTieredItem {
    protected final TagKey<Block> effectiveBlocks;

    public IFWDiggerItem(IIFWTier tier, int numComponents, TagKey<Block> blocks,Properties properties) {
        super(tier, numComponents, properties.component(DataComponents.TOOL, tier.createToolProperties(blocks)));
        this.effectiveBlocks = blocks;
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, float damage, float speed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        Tool tool = stack.get(DataComponents.TOOL);
        if (tool == null) {
            return false;
        } else {
            float destroySpeed = state.getDestroySpeed(level, pos);
            //!state.isPortable()
            if (!level.isClientSide && tool.damagePerBlock() > 0 && destroySpeed != 0.0F && !((BlockStateBaseExtension) state).ifw_isPortable()) {
                if (state.is(this.effectiveBlocks)) {
                    stack.hurtAndBreak(Math.round(destroySpeed * 15.0F), miningEntity, EquipmentSlot.MAINHAND);
                } else if (!state.requiresCorrectToolForDrops()) {
                    stack.hurtAndBreak(Math.round(destroySpeed * 5.0F), miningEntity, EquipmentSlot.MAINHAND);
                } else {
                    stack.hurtAndBreak(Math.round(destroySpeed * 50.0F), miningEntity, EquipmentSlot.MAINHAND);
                }
            }

            return true;
        }
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(40, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public float getReachBonus() {
        return 0.75F;
    }
}
