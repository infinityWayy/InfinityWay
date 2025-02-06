package huix.infinity.mixin.world.item;

import huix.infinity.common.world.item.*;
import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
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

    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/BucketItem;"
            , ordinal = 0), method = "<clinit>")
    private static BucketItem ifw_rebuildBucket_0(Fluid content, Item.Properties properties) {
        return new IFWBucketItem(content, IFWTiers.IRON, properties);
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/BucketItem;"
            , ordinal = 1), method = "<clinit>")
    private static BucketItem ifw_rebuildBucket_1(Fluid content, Item.Properties properties) {
        return new IFWBucketItem(content, IFWTiers.IRON, properties);
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/BucketItem;"
            , ordinal = 2), method = "<clinit>")
    private static BucketItem ifw_rebuildBucket_2(Fluid content, Item.Properties properties) {
        return new IFWBucketItem(content, IFWTiers.IRON, properties);
    }
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/SolidBucketItem;"
            ), method = "<clinit>")
    private static SolidBucketItem ifw_rebuildBucket_3(Block block, SoundEvent placeSound, Item.Properties properties) {
        return new IFWSolidBucketItem(block, placeSound, IFWTiers.IRON, properties);
    }

}
