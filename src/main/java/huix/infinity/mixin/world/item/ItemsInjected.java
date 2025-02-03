package huix.infinity.mixin.world.item;

import huix.infinity.common.world.item.tier.IFWArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class ItemsInjected {

    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
                , ordinal = 1), method = "<clinit>")
    private static ArmorItem ifw_rebuildLeather_0(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.leather, ArmorItem.Type.HELMET, (new Item.Properties()).durability(ArmorItem.Type.HELMET.getDurability(1)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 2), method = "<clinit>")
    private static ArmorItem ifw_rebuildLeather_1(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.leather, ArmorItem.Type.CHESTPLATE, (new Item.Properties()).durability(ArmorItem.Type.CHESTPLATE.getDurability(1)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 3), method = "<clinit>")
    private static ArmorItem ifw_rebuildLeather_2(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.leather, ArmorItem.Type.LEGGINGS, (new Item.Properties()).durability(ArmorItem.Type.LEGGINGS.getDurability(1)));
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ArmorItem$Type;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/ArmorItem;"
            , ordinal = 4), method = "<clinit>")
    private static ArmorItem ifw_rebuildLeather_3(Holder<ArmorMaterials> material, ArmorItem.Type type, Item.Properties properties) {
        return new ArmorItem(IFWArmorMaterials.leather, ArmorItem.Type.BOOTS, (new Item.Properties()).durability(ArmorItem.Type.BOOTS.getDurability(1)));
    }
}
