package huix.infinity.mixin.world.item;

import huix.infinity.common.world.item.GemItem;
import huix.infinity.common.world.item.IFWDiggerItem;
import huix.infinity.common.world.item.PickaxeTool;
import huix.infinity.common.world.item.RodItem;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.init.InfinityWay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Items.class)
public abstract class ItemsInjected_1 {


    @Shadow @Final @Mutable public static Item DIAMOND;
    @Shadow @Final @Mutable public static Item EMERALD;
    @Shadow @Final @Mutable public static Item LAPIS_LAZULI;
    @Shadow @Final @Mutable public static Item QUARTZ;
    @Shadow @Final @Mutable public static Item IRON_PICKAXE;
    @Shadow @Final @Mutable public static Item STICK;
    @Shadow @Final @Mutable public static Item BONE;

    @Inject(at = @At(value = "RETURN"), method = "<clinit>")
    private static void rebuildDiamond(CallbackInfo ci) {
//        DIAMOND = registerItem("diamond", new GemItem(new Item.Properties(), 500));;
//        EMERALD = registerItem("emerald", new GemItem(new Item.Properties(), 250));;
//        QUARTZ = registerItem("quartz", new GemItem(new Item.Properties(), 50));;
//        LAPIS_LAZULI = registerItem("lapis_lazuli", new GemItem(new Item.Properties(), 25));;
//        STICK = registerItem("stick", new RodItem(new Item.Properties(), 0.4F));
//        BONE = registerItem("bone", new RodItem(new Item.Properties(), 0.6F), true);
//        IRON_PICKAXE = registerItem("iron_pickaxe", new PickaxeTool(IFWTiers.IRON,
//                new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.IRON, 2, -2.7F))));
    }

//    @Overwrite
//    public static Item registerItem(String key, Item item) {
//        return registerItem(key, item, false);
//    }

    @Unique
    private static Item registerItem(String key, Item item, boolean override) {
        if (!override && (key.equals("diamond"))) {
            item = new GemItem(new Item.Properties(), 500);
        }
        return registerItem(ResourceLocation.withDefaultNamespace(key), item);
    }

    @Shadow
    public static Item registerItem(ResourceLocation key, Item item) {
        return null;
    }

}
