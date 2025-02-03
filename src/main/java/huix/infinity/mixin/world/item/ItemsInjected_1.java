package huix.infinity.mixin.world.item;

import huix.infinity.common.world.item.GemItem;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Items.class)
public class ItemsInjected_1 {

    @Shadow @Final @Mutable public static Item DIAMOND;
    @Shadow @Final @Mutable public static Item EMERALD;
    @Shadow @Final @Mutable public static Item LAPIS_LAZULI;
    @Shadow @Final @Mutable public static Item QUARTZ;

    @Inject(at = @At(value = "RETURN"), method = "<clinit>")
    private static void rebuildDiamond(CallbackInfo ci) {
        DIAMOND = registerItem("diamond", new GemItem(new Item.Properties(), 500));;
        EMERALD = registerItem("emerald", new GemItem(new Item.Properties(), 250));;
        QUARTZ = registerItem("quartz", new GemItem(new Item.Properties(), 50));;
        LAPIS_LAZULI = registerItem("lapis_lazuli", new GemItem(new Item.Properties(), 25));;
    }

    @Shadow
    public static Item registerItem(String key, Item item) {
        return null;
    }

}
