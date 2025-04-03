package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SwordWeapon extends IFWTieredItem {
    public SwordWeapon(IFWTier tier, Properties properties) {
        super(tier, 2,  properties.component(DataComponents.TOOL, createToolProperties()));
    }

    public SwordWeapon(IFWTier tier, int c, Properties properties) {
        super(tier, c,  properties.component(DataComponents.TOOL, createToolProperties()));
    }

    public SwordWeapon(IFWTier tier, float durability, Properties properties) {
        super(tier, durability,  properties.component(DataComponents.TOOL, createToolProperties()));
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return 2.0F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return 0.5F;
    }

    public SwordWeapon(IFWTier tier, Properties properties, Tool toolComponentData) {
        super(tier, 2, properties.component(DataComponents.TOOL, toolComponentData));
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    public static ItemAttributeModifiers createAttributes(IFWTier tier, int damage, float speed) {
        return createAttributes(tier, (float)damage, speed);
    }

    public static ItemAttributeModifiers createAttributes(IFWTier tier, float damage, float speed) {
        return ItemAttributeModifiers.builder()
            .add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                    BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
                ),
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
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }

//    @Override
//    public float getReachBonus() {
//        return 0.75F;
//    }
}
