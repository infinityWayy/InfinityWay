package huix.infinity.common.item.group;

import huix.infinity.init.InfinityWay;
import huix.infinity.init.InfinityWayData;
import huix.infinity.common.block.IFWBlocks;
import huix.infinity.common.item.IFWItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWItemGroup {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfinityWay.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> item_tab =
            CREATIVE_TABS.register("item_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.ifw.item"))
            .icon(() -> IFWItems.adamantium_ingot.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(IFWItems.adamantium_ingot.get());
                output.accept(IFWItems.adamantium_nugget.get());
                output.accept(IFWItems.ancient_metal_ingot.get());
                output.accept(IFWItems.mithril_ingot.get());
                output.accept(IFWItems.silver_ingot.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> tool_tab =
            CREATIVE_TABS.register("tool_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.ifw.tool_tab"))
                    .icon(() -> IFWItems.adamantium_pickaxe.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(IFWItems.adamantium_pickaxe.get());
                        output.accept(IFWItems.adamantium_shears.get());
                        output.accept(IFWItems.adamantium_shovel.get());
                        output.accept(IFWItems.adamantium_hoe.get());
                        output.accept(IFWItems.adamantium_sword.get());
                        output.accept(IFWItems.adamantium_axe.get());
                        output.accept(IFWItems.adamantium_scythe.get());
                        output.accept(IFWItems.adamantium_mattock.get());
                        output.accept(IFWItems.adamantium_battle_axe.get());
                        output.accept(IFWItems.adamantium_war_hammer.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> block_tab =
            CREATIVE_TABS.register("block_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.ifw.block"))
                    .icon(() -> IFWBlocks.adamantium_block_item.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(IFWBlocks.adamantium_block_item.get());
                        output.accept(IFWBlocks.adamantium_ore_item.get());
                        output.accept(IFWBlocks.adamantium_bars_item.get());
                        output.accept(IFWBlocks.adamantium_door_item.get());
                    }).build());


}
