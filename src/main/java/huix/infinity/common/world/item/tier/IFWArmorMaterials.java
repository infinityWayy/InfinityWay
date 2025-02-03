package huix.infinity.common.world.item.tier;

import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.InfinityWay;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class IFWArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, InfinityWay.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> leather = ARMOR_MATERIALS.register("leather", () ->
            registerByMC(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 0.33F);map.put(ArmorItem.Type.LEGGINGS, 0.58F);
                map.put(ArmorItem.Type.CHESTPLATE, 0.67F);map.put(ArmorItem.Type.HELMET, 0.42F);
                map.put(ArmorItem.Type.BODY, 0.67F);}),
                    10, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0,5, 0, 0,
                    () -> Ingredient.of(IFWItems.sinew), "leather"));


    public static final Holder<ArmorMaterial> copper = ARMOR_MATERIALS.register("copper", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1.2F);map.put(ArmorItem.Type.LEGGINGS, 2.0F);
                map.put(ArmorItem.Type.CHESTPLATE, 2.3F);map.put(ArmorItem.Type.HELMET, 1.5F);
                map.put(ArmorItem.Type.BODY, 2.3F);}),
                    30, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0, 20, 0, 1,
                    () -> Ingredient.of(IFWItems.copper_nugget), "copper"));
    public static final Holder<ArmorMaterial> silver = ARMOR_MATERIALS.register("silver", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.2F);map.put(ArmorItem.Type.LEGGINGS, 2.0F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.3F);map.put(ArmorItem.Type.HELMET, 1.5F);
                        map.put(ArmorItem.Type.BODY, 2.3F);}),
                    30, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0, 20, 0, 1,
                    () -> Ingredient.of(IFWItems.silver_nugget), "silver"));
    public static final Holder<ArmorMaterial> rusted_iron = ARMOR_MATERIALS.register("rusted_iron", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.0F);map.put(ArmorItem.Type.LEGGINGS, 1.8F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.0F);map.put(ArmorItem.Type.HELMET, 1.3F);
                        map.put(ArmorItem.Type.BODY, 2.0F);}),
                    0, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0, 5, 0, 2,
                    () -> Ingredient.of(Items.IRON_NUGGET), "rusted_iron"));
    public static final Holder<ArmorMaterial> iron = ARMOR_MATERIALS.register("iron", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.3F);map.put(ArmorItem.Type.LEGGINGS, 2.3F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.7F);map.put(ArmorItem.Type.HELMET, 1.7F);
                        map.put(ArmorItem.Type.BODY, 2.7F);}),
                    30, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0, 60, 0, 2,
                    () -> Ingredient.of(Items.IRON_NUGGET), "iron"));
    public static final Holder<ArmorMaterial> golden = ARMOR_MATERIALS.register("golden", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.0F);map.put(ArmorItem.Type.LEGGINGS, 1.8F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.0F);map.put(ArmorItem.Type.HELMET, 1.3F);
                        map.put(ArmorItem.Type.BODY, 2.0F);}),
                    50, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0, 999999999, 0, 1,
                    () -> Ingredient.of(Items.GOLD_NUGGET), "golden"));
    public static final Holder<ArmorMaterial> ancient_metal = ARMOR_MATERIALS.register("ancient_metal", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.3F);map.put(ArmorItem.Type.LEGGINGS, 2.3F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.7F);map.put(ArmorItem.Type.HELMET, 1.7F);
                        map.put(ArmorItem.Type.BODY, 2.7F);}),
                    40, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0, 90, 0, 3,
                    () -> Ingredient.of(IFWItems.ancient_metal_nugget), "ancient_metal"));
    public static final Holder<ArmorMaterial> mithril = ARMOR_MATERIALS.register("mithril", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.5F);map.put(ArmorItem.Type.LEGGINGS, 2.6F);
                        map.put(ArmorItem.Type.CHESTPLATE, 3.0F);map.put(ArmorItem.Type.HELMET, 1.9F);
                        map.put(ArmorItem.Type.BODY, 3.0F);}),
                    100, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0, 100, 0, 4,
                    () -> Ingredient.of(IFWItems.mithril_nugget), "mithril"));
    public static final Holder<ArmorMaterial> adamantium = ARMOR_MATERIALS.register("adamantium", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.7F);map.put(ArmorItem.Type.LEGGINGS, 2.9F);
                        map.put(ArmorItem.Type.CHESTPLATE, 3.3F);map.put(ArmorItem.Type.HELMET, 2.1F);
                        map.put(ArmorItem.Type.BODY, 3.3F);}),
                    70, SoundEvents.ARMOR_EQUIP_IRON, 3.0F, 0.1F, 1000, 0, 5,
                    () -> Ingredient.of(IFWItems.adamantium_nugget), "adamantium"));
    public static final Holder<ArmorMaterial> copper_chainmail = ARMOR_MATERIALS.register("copper_chainmail", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 0.83F);map.put(ArmorItem.Type.LEGGINGS, 1.5F);
                        map.put(ArmorItem.Type.CHESTPLATE, 1.7F);map.put(ArmorItem.Type.HELMET, 1.0F);
                        map.put(ArmorItem.Type.BODY, 1.7F);}),
                    15, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0, 10, 0, 1,
                    () -> Ingredient.of(IFWItems.copper_nugget), "copper_chainmail"));
    public static final Holder<ArmorMaterial> silver_chainmail = ARMOR_MATERIALS.register("silver_chainmail", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 0.83F);map.put(ArmorItem.Type.LEGGINGS, 1.5F);
                        map.put(ArmorItem.Type.CHESTPLATE, 1.7F);map.put(ArmorItem.Type.HELMET, 1.0F);
                        map.put(ArmorItem.Type.BODY, 1.7F);}),
                    15, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0, 10, 0, 1,
                    () -> Ingredient.of(IFWItems.silver_nugget), "silver_chainmail"));
    public static final Holder<ArmorMaterial> iron_chainmail = ARMOR_MATERIALS.register("iron_chainmail", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.3F);map.put(ArmorItem.Type.LEGGINGS, 1.8F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.0F);map.put(ArmorItem.Type.HELMET, 1.7F);
                        map.put(ArmorItem.Type.BODY, 2.0F);}),
                    15, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0, 15, 0, 2,
                    () -> Ingredient.of(Items.IRON_NUGGET), "iron_chainmail"));
    public static final Holder<ArmorMaterial> rusted_iron_chainmail = ARMOR_MATERIALS.register("rusted_iron_chainmail", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 0.67F);map.put(ArmorItem.Type.LEGGINGS, 1.2F);
                        map.put(ArmorItem.Type.CHESTPLATE, 1.3F);map.put(ArmorItem.Type.HELMET, 0.83F);
                        map.put(ArmorItem.Type.BODY, 1.3F);}),
                    0, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0, 4, 0, 2,
                    () -> Ingredient.of(Items.IRON_NUGGET), "rusted_iron_chainmail"));
    public static final Holder<ArmorMaterial> golden_chainmail = ARMOR_MATERIALS.register("golden_chainmail", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.0F);map.put(ArmorItem.Type.LEGGINGS, 1.8F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.0F);map.put(ArmorItem.Type.HELMET, 1.3F);
                        map.put(ArmorItem.Type.BODY, 2.0F);}),
                    25, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0, 999999, 0, 1,
                    () -> Ingredient.of(Items.GOLD_NUGGET), "golden_chainmail"));
    public static final Holder<ArmorMaterial> ancient_metal_chainmail = ARMOR_MATERIALS.register("ancient_metal_chainmail", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.0F);map.put(ArmorItem.Type.LEGGINGS, 1.8F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.0F);map.put(ArmorItem.Type.HELMET, 1.3F);
                        map.put(ArmorItem.Type.BODY, 2.0F);}),
                    20, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0, 25, 0, 3,
                    () -> Ingredient.of(IFWItems.ancient_metal_nugget), "ancient_metal_chainmail"));
    public static final Holder<ArmorMaterial> mithril_chainmail = ARMOR_MATERIALS.register("mithril_chainmail", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.2F);map.put(ArmorItem.Type.LEGGINGS, 2.0F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.3F);map.put(ArmorItem.Type.HELMET, 1.5F);
                        map.put(ArmorItem.Type.BODY, 2.3F);}),
                    50, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0, 100, 0, 4,
                    () -> Ingredient.of(IFWItems.mithril_nugget), "mithril_chainmail"));
    public static final Holder<ArmorMaterial> adamantium_chainmail = ARMOR_MATERIALS.register("adamantium_chainmail", () ->
            register(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1.3F);map.put(ArmorItem.Type.LEGGINGS, 2.3F);
                        map.put(ArmorItem.Type.CHESTPLATE, 2.7F);map.put(ArmorItem.Type.HELMET, 1.7F);
                        map.put(ArmorItem.Type.BODY, 2.7F);}),
                    20, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0, 1000, 0, 5,
                    () -> Ingredient.of(IFWItems.adamantium_nugget), "adamantium_chainmail"));


    private static ArmorMaterial register(EnumMap<ArmorItem.Type, Float> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, int acidResistance, float magicResistance, int repairLevel, Supplier<Ingredient> repairIngridient, String name) {
        EnumMap<ArmorItem.Type, Float> enummap = new EnumMap<>(ArmorItem.Type.class);
        for(ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, defense.get(armoritem$type));
        }
        return new ArmorMaterial(new EnumMap<>(ArmorItem.Type.class), enchantmentValue, equipSound, repairIngridient,
                List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, name))),
                toughness, knockbackResistance).ifw_defense(enummap).acidResistance(acidResistance).magicResistance(magicResistance).repairLevel(repairLevel);
    }

    private static ArmorMaterial registerByMC(EnumMap<ArmorItem.Type, Float> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, int acidResistance, float magicResistance, int repairLevel, Supplier<Ingredient> repairIngridient, String name) {
        EnumMap<ArmorItem.Type, Float> enummap = new EnumMap<>(ArmorItem.Type.class);
        for(ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, defense.get(armoritem$type));
        }
        return new ArmorMaterial(new EnumMap<>(ArmorItem.Type.class), enchantmentValue, equipSound, repairIngridient,
                List.of(new ArmorMaterial.Layer(ResourceLocation.withDefaultNamespace(name))),
                toughness, knockbackResistance).ifw_defense(enummap).acidResistance(acidResistance).magicResistance(magicResistance).repairLevel(repairLevel);
    }
}
