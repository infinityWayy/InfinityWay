package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.tag.IFWBlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ScytheTool extends IFWDiggerItem {
    public ScytheTool(IFWTier tier, Properties properties) {
        super(tier, 2, IFWBlockTags.SCYTHE_EFFECTIVE, properties);
    }

    @Override
    public float getReachBonus() {
        return 1.0F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return EnchantmentHelper.has(stack, EnchantmentEffectComponents.DAMAGE) ? 2.0F : 4.0F;
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return state.is(BlockTags.CROPS) || state.is(BlockTags.TALL_FLOWERS) || state.getBlock() == Blocks.TALL_GRASS ? 2.0F : 0.5F;
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
                .add(
                        Attributes.SWEEPING_DAMAGE_RATIO,
                        new AttributeModifier(ResourceLocation.withDefaultNamespace("enchantment.sweeping_edge"), 0.5, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }


}
