package huix.infinity.mixin.world.item;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class ItemsInjected {

    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/item/Item$Properties;")
            , method = "registerBlock(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/item/Item;")
    private static Item.Properties ifw_blockStackSize_0() {
        return new Item.Properties().stacksTo(4);
    }

    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/item/Item$Properties;")
            , method = "registerBlock(Lnet/minecraft/world/level/block/Block;Ljava/util/function/UnaryOperator;)Lnet/minecraft/world/item/Item;")
    private static Item.Properties ifw_blockStackSize_1() {
        return new Item.Properties().stacksTo(4);
    }

    @Redirect(at = @At(value = "NEW", target = "()Lnet/minecraft/world/item/Item$Properties;")
            , method = "registerBlock(Lnet/minecraft/world/level/block/Block;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/item/Item;")
    private static Item.Properties ifw_blockStackSize_2() {
        return new Item.Properties().stacksTo(4);
    }
}
