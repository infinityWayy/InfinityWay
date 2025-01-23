package huix.infinity.common.item;

import huix.infinity.common.item.tier.IIFWTier;
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
    public SwordWeapon(IIFWTier p_43269_, Properties p_43272_) {
        super(p_43269_, 2,  p_43272_.component(DataComponents.TOOL, createToolProperties()));
    }

    public SwordWeapon(IIFWTier p_43269_, int c, Properties p_43272_) {
        super(p_43269_, c,  p_43272_.component(DataComponents.TOOL, createToolProperties()));
    }

    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        return 2.0F;
    }

    @Override
    public float getDecayRateForAttackingEntity(ItemStack stack) {
        return 0.5F;
    }

    /**
     * Neo: Allow modded Swords to set exactly what Tool data component to use for their sword.
     */
    public SwordWeapon(IIFWTier tier, Properties properties, Tool toolComponentData) {
        super(tier, 2, properties.component(DataComponents.TOOL, toolComponentData));
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    public static ItemAttributeModifiers createAttributes(IIFWTier p_330371_, int p_331976_, float p_332104_) {
        return createAttributes(p_330371_, (float)p_331976_, p_332104_);
    }

    /**
     * Neo: Method overload to allow giving a float for damage instead of an int.
     */
    public static ItemAttributeModifiers createAttributes(IIFWTier p_330371_, float p_331976_, float p_332104_) {
        return ItemAttributeModifiers.builder()
            .add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                    BASE_ATTACK_DAMAGE_ID, (double)((float)p_331976_ + p_330371_.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
            )
            .add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)p_332104_, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
            )
            .build();
    }

    @Override
    public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
        return !p_43294_.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }
}
