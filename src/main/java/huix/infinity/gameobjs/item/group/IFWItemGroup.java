package huix.infinity.gameobjs.item.group;

import huix.infinity.InfinityWay;
import huix.infinity.gameobjs.block.IFWBlocks;
import huix.infinity.gameobjs.item.IFWItems;
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
                output.accept(IFWItems.ancient_metal_ingot.get());
                output.accept(IFWItems.mithril_ingot.get());
                output.accept(IFWItems.silver_ingot.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> block_tab =
            CREATIVE_TABS.register("block_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.ifw.block"))
                    .icon(() -> IFWBlocks.adamantium_block_item.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(IFWBlocks.adamantium_block_item.get());
                    }).build());


}
