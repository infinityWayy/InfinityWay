//package huix.infinity.mixin;
//
//import com.llamalad7.mixinextras.sugar.Local;
//import huix.infinity.gameobjs.item.tier.IIFWTier;
//import huix.infinity.gameobjs.item.tool.interfaces.IDamageableItem;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Tier;
//import net.minecraft.world.item.TieredItem;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin( TieredItem.class )
//public abstract class TieredItemMixin implements IDamageableItem {
//
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;durability(I)Lnet/minecraft/world/item/Item$Properties;"),
//            method = "<init>")
//    private static Item.Properties fixDurability(Item.Properties instance, int maxDamage, @Local(argsOnly = true) Tier tier) {
////        if (tier instanceof IIFWTier)
////            return instance.durability(getMultipliedDurability());
//        return instance;
//    }
//
//}
