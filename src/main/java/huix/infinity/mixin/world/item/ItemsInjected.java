package huix.infinity.mixin.world.item;

import huix.infinity.common.world.item.GemItem;
import huix.infinity.common.world.item.RodItem;
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

    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;"
            , ordinal = 6), method = "<clinit>")
    private static Item ifw_rebuildGem_0(Item.Properties properties) {
        return new GemItem(new Item.Properties(), 500);
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;"
            , ordinal = 7), method = "<clinit>")
    private static Item ifw_rebuildGem_1(Item.Properties properties) {
        return new GemItem(new Item.Properties(), 250);
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;"
            , ordinal = 8), method = "<clinit>")
    private static Item ifw_rebuildGem_2(Item.Properties properties) {
        return new GemItem(new Item.Properties(), 25);
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;"
            , ordinal = 9), method = "<clinit>")
    private static Item ifw_rebuildGem_3(Item.Properties properties) {
        return new GemItem(new Item.Properties(), 50);
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;"
            , ordinal = 44), method = "<clinit>")
    private static Item ifw_rebuildBone(Item.Properties properties) {
        return new RodItem(new Item.Properties(), 0.6F);
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;"
            , ordinal = 19), method = "<clinit>")
    private static Item ifw_rebuildStick(Item.Properties properties) {
        return new RodItem(new Item.Properties(), 0.4F);
    }

}
