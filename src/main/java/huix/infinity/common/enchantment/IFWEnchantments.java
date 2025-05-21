package huix.infinity.common.enchantment;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

public class IFWEnchantments {
    private static final ResourceLocation ifw_speed = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "speed");

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(
            Registries.ENCHANTMENT, bootstrap -> {
                HolderGetter<Enchantment> enchantments = bootstrap.lookup(Registries.ENCHANTMENT);
                HolderGetter<Item> items = bootstrap.lookup(Registries.ITEM);
                bootstrap.register(
                        ResourceKey.create(Registries.ENCHANTMENT, ifw_speed),
                        Enchantment.enchantment(Enchantment.definition(
                                        items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                        1,
                                        5,
                                        new Enchantment.Cost(1, 8),
                                        new Enchantment.Cost(21, 8),
                                        0,
                                        EquipmentSlotGroup.FEET))
                                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                                .withEffect(EnchantmentEffectComponents.LOCATION_CHANGED,
                                        new EnchantmentAttributeEffect(
                                                ifw_speed,
                                                Attributes.MOVEMENT_SPEED,
                                                LevelBasedValue.perLevel(0.0F, 0.005F),
                                                AttributeModifier.Operation.ADD_VALUE))
                                .build(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "speed")));

            });
}
