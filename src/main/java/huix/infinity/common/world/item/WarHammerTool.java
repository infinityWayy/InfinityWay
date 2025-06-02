package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class WarHammerTool extends PickaxeTool {

    public WarHammerTool(IFWTier tier, Properties properties) {
        super(tier, 5, properties.component(DataComponents.ATTRIBUTE_MODIFIERS, createWarHammerAttributes(tier)));
    }

    private static ItemAttributeModifiers createWarHammerAttributes(IFWTier tier) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID,
                                3.0F + tier.getAttackDamageBonus(),
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.8F, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return super.getDecayRateForAttackingEntity(stack) * 2.0F / 3.0F;
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return super.getDecayRateForBreakingBlock(state) * 2.0F / 3.0F;
    }

    @Override
    public float getBaseHarvestEfficiency(BlockState state) {
        return super.getBaseHarvestEfficiency(state) * 0.75F;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (target.getType().is(EntityTypeTags.SKELETONS) && target.isAlive()) {

            float baseDamage = 3.0F + this.ifwTier().getAttackDamageBonus();

            float bonusDamage = baseDamage * 0.5F;

            float armorValue = target.getArmorValue();
            float armorPenetrationDamage = Math.min(3.0F, armorValue);

            float totalExtraDamage = bonusDamage + armorPenetrationDamage;

            float newHealth = Math.max(0.0F, target.getHealth() - totalExtraDamage);
            target.setHealth(newHealth);
        }

        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("tooltip.infinity.effective_against_skeletons")
                .withStyle(ChatFormatting.GREEN));
        tooltipComponents.add(Component.translatable("tooltip.infinity.armor_penetration")
                .withStyle(ChatFormatting.YELLOW));
    }
}