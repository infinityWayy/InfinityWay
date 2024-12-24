//package huix.infinity.mixin.world.enchantment;
//
//import com.llamalad7.mixinextras.sugar.Local;
//import net.minecraft.advancements.critereon.ItemPredicate;
//import net.minecraft.core.HolderGetter;
//import net.minecraft.data.worldgen.BootstrapContext;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.tags.ItemTags;
//import net.minecraft.world.entity.EquipmentSlotGroup;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.item.enchantment.LevelBasedValue;
//import net.minecraft.world.item.enchantment.effects.RemoveBinomial;
//import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
//import net.minecraft.world.level.storage.loot.predicates.MatchTool;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin( Enchantments.class )
//public class EnchantmentsMixin {
//
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantments;register(Lnet/minecraft/data/worldgen/BootstrapContext;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/item/enchantment/Enchantment$Builder;)V"
//            , ordinal = 22)
//            , method = "bootstrap")
//    private static void ifw_UnbreakingFix(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key
//            , Enchantment.Builder builder, @Local(ordinal = 2) HolderGetter<Item> holderGetter) {
//        var unbreaking = Enchantment.enchantment(
//                        Enchantment.definition(
//                                holderGetter.getOrThrow(ItemTags.DURABILITY_ENCHANTABLE),
//                                5, 5,
//                                Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(55, 8),
//                                0, EquipmentSlotGroup.ANY
//                        )
//                )
//                .withEffect(
//                        EnchantmentEffectComponents.ITEM_DAMAGE,
//                        new RemoveBinomial(new LevelBasedValue.Fraction(LevelBasedValue.perLevel(2.0F), LevelBasedValue.perLevel(10.0F, 5.0F))),
//                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.ARMOR_ENCHANTABLE))
//                )
//                .withEffect(
//                        EnchantmentEffectComponents.ITEM_DAMAGE,
//                        new RemoveBinomial(new LevelBasedValue.Fraction(LevelBasedValue.perLevel(1.0F), LevelBasedValue.constant(5))),
//                        InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.ARMOR_ENCHANTABLE)))
//                );
//        register(context, key, unbreaking);
//    }
//
//    @Shadow
//    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
//    }
//}
