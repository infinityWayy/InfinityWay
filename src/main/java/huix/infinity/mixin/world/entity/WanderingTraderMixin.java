package huix.infinity.mixin.world.entity;

import huix.infinity.common.world.item.IFWItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(VillagerTrades.class)
public class WanderingTraderMixin {

    @Final
    @Shadow
    public static Int2ObjectMap<VillagerTrades.ItemListing[]> WANDERING_TRADER_TRADES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addCustomWanderingTraderTrades(CallbackInfo ci) {
        VillagerTrades.ItemListing mithrilTrade = (entity, random) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 4),
                new ItemStack(IFWItems.mithril_horse_armor.get()),
                1, 1, 0.05f
        );

        VillagerTrades.ItemListing adamantiumTrade = (entity, random) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 6),
                new ItemStack(IFWItems.adamantium_horse_armor.get()),
                1, 1, 0.05f
        );

//        VillagerTrades.ItemListing[] level1Array = WANDERING_TRADER_TRADES.get(1);
//        List<VillagerTrades.ItemListing> level1List = new ArrayList<>(Arrays.asList(level1Array));
//        level1List.add(mithrilTrade);
//        level1List.add(adamantiumTrade);
//        WANDERING_TRADER_TRADES.put(1, level1List.toArray(new VillagerTrades.ItemListing[0]));

        VillagerTrades.ItemListing[] level2Array = WANDERING_TRADER_TRADES.get(2);
        List<VillagerTrades.ItemListing> level2List = new ArrayList<>(Arrays.asList(level2Array));
        level2List.add(mithrilTrade);
        level2List.add(adamantiumTrade);
        WANDERING_TRADER_TRADES.put(2, level2List.toArray(new VillagerTrades.ItemListing[0]));

    }
}