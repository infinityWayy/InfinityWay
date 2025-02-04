package huix.infinity.mixin.world.item;

import huix.infinity.common.world.item.IFWDiggerItem;
import huix.infinity.common.world.item.PickaxeTool;
import huix.infinity.common.world.item.tier.IFWArmorMaterials;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.util.DurabilityHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class EquipsInjected {
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 1), method = "<clinit>")
    private static ArmorItem ifw_rebuildLeather_0(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.leather, ArmorItem.Type.HELMET, (new Item.Properties()).durability(DurabilityHelper.Armor.HELMET.getDurability(1)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 2), method = "<clinit>")
    private static ArmorItem ifw_rebuildLeather_1(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.leather, ArmorItem.Type.CHESTPLATE, (new Item.Properties()).durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(1)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 3), method = "<clinit>")
    private static ArmorItem ifw_rebuildLeather_2(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.leather, ArmorItem.Type.LEGGINGS, (new Item.Properties()).durability(DurabilityHelper.Armor.LEGGINGS.getDurability(1)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 4), method = "<clinit>")
    private static ArmorItem ifw_rebuildLeather_3(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.leather, ArmorItem.Type.BOOTS, (new Item.Properties()).durability(DurabilityHelper.Armor.BOOTS.getDurability(1)));
    }

    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 5), method = "<clinit>")
    private static ArmorItem ifw_rebuildIronChainmail_0(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.iron_chainmail, ArmorItem.Type.HELMET, (new Item.Properties()).durability(DurabilityHelper.Armor.HELMET.getDurability(4)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 6), method = "<clinit>")
    private static ArmorItem ifw_rebuildIronChainmail_1(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.iron_chainmail, ArmorItem.Type.CHESTPLATE, (new Item.Properties()).durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(4)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 7), method = "<clinit>")
    private static ArmorItem ifw_rebuildIronChainmail_2(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.iron_chainmail, ArmorItem.Type.LEGGINGS, (new Item.Properties()).durability(DurabilityHelper.Armor.LEGGINGS.getDurability(4)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 8), method = "<clinit>")
    private static ArmorItem ifw_rebuildIronChainmail_3(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.iron_chainmail, ArmorItem.Type.BOOTS, (new Item.Properties()).durability(DurabilityHelper.Armor.BOOTS.getDurability(4)));
    }

    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 9), method = "<clinit>")
    private static ArmorItem ifw_rebuildIron_0(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.iron, ArmorItem.Type.HELMET, (new Item.Properties()).durability(DurabilityHelper.Armor.HELMET.getDurability(8)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 10), method = "<clinit>")
    private static ArmorItem ifw_rebuildIron_1(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.iron, ArmorItem.Type.CHESTPLATE, (new Item.Properties()).durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(8)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 11), method = "<clinit>")
    private static ArmorItem ifw_rebuildIron_2(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.iron, ArmorItem.Type.LEGGINGS, (new Item.Properties()).durability(DurabilityHelper.Armor.LEGGINGS.getDurability(8)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 12), method = "<clinit>")
    private static ArmorItem ifw_rebuildIron_3(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.iron, ArmorItem.Type.BOOTS, (new Item.Properties()).durability(DurabilityHelper.Armor.BOOTS.getDurability(8)));
    }

    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 17), method = "<clinit>")
    private static ArmorItem ifw_rebuildGolden_0(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.golden, ArmorItem.Type.HELMET, (new Item.Properties()).durability(DurabilityHelper.Armor.HELMET.getDurability(4)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 18), method = "<clinit>")
    private static ArmorItem ifw_rebuildGolden_1(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.golden, ArmorItem.Type.CHESTPLATE, (new Item.Properties()).durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(4)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 19), method = "<clinit>")
    private static ArmorItem ifw_rebuildGolden_2(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.golden, ArmorItem.Type.LEGGINGS, (new Item.Properties()).durability(DurabilityHelper.Armor.LEGGINGS.getDurability(4)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 20), method = "<clinit>")
    private static ArmorItem ifw_rebuildGolden_3(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.golden, ArmorItem.Type.BOOTS, (new Item.Properties()).durability(DurabilityHelper.Armor.BOOTS.getDurability(4)));
    }

}
