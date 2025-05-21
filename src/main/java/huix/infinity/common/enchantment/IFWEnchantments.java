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
    //Common
//    private static final ResourceLocation ifw_unbreaking = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "unbreaking");
    //Weapon
//    private static final ResourceLocation ifw_disarming = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "disarming");
//    private static final ResourceLocation ifw_quickness = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "quickness");
//    private static final ResourceLocation ifw_accuracy = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "accuracy");
//    private static final ResourceLocation ifw_poison = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "poison");
//    private static final ResourceLocation ifw_butchering = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "butchering");
//    private static final ResourceLocation ifw_stun = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "stun");
//    private static final ResourceLocation ifw_vampiric = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "vampiric");
//    private static final ResourceLocation ifw_recovery = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "recovery");
//    private static final ResourceLocation ifw_slaying = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "slaying");
//    private static final ResourceLocation ifw_cleaving = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "cleaving");
    //Tool
//    private static final ResourceLocation ifw_harvesting = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "harvesting");
//    private static final ResourceLocation ifw_piercing = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "piercing");
//    private static final ResourceLocation ifw_luring = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "luring");
//    private static final ResourceLocation ifw_fertility = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "fertility");
//    private static final ResourceLocation ifw_treeFelling = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "treeFelling");
//    private static final ResourceLocation ifw_fortune = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "fortune");
    //Armor
//    private static final ResourceLocation ifw_freeAction = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "freeAction");
//    private static final ResourceLocation ifw_regeneration = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "regeneration");
      private static final ResourceLocation ifw_speed = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "speed");
//    private static final ResourceLocation ifw_endurance = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "endurance");
//    private static final ResourceLocation ifw_protection = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "protection");

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
