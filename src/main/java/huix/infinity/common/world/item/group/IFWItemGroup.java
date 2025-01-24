package huix.infinity.common.world.item.group;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
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
                output.accept(IFWItems.silver_nugget.get());
                output.accept(IFWItems.adamantium_nugget.get());
                output.accept(IFWItems.ancient_metal_nugget.get());
                output.accept(IFWItems.mithril_nugget.get());
                output.accept(IFWItems.copper_nugget.get());
                output.accept(IFWItems.diamond_shard.get());
                output.accept(IFWItems.emerald_shard.get());
                output.accept(IFWItems.flint_shard.get());
                output.accept(IFWItems.obsidian_shard.get());
                output.accept(IFWItems.glass_shard.get());
                output.accept(IFWItems.quartz_shard.get());
                output.accept(IFWItems.raw_adamantium.get());
                output.accept(IFWItems.raw_mithril.get());
                output.accept(IFWItems.raw_silver.get());
                output.accept(IFWItems.sinew.get());
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
                        output.accept(IFWItems.ancient_metal_pickaxe.get());
                        output.accept(IFWItems.ancient_metal_shears.get());
                        output.accept(IFWItems.ancient_metal_shovel.get());
                        output.accept(IFWItems.ancient_metal_hoe.get());
                        output.accept(IFWItems.ancient_metal_sword.get());
                        output.accept(IFWItems.ancient_metal_axe.get());
                        output.accept(IFWItems.ancient_metal_scythe.get());
                        output.accept(IFWItems.ancient_metal_mattock.get());
                        output.accept(IFWItems.ancient_metal_battle_axe.get());
                        output.accept(IFWItems.ancient_metal_war_hammer.get());
                        output.accept(IFWItems.mithril_pickaxe.get());
                        output.accept(IFWItems.mithril_shears.get());
                        output.accept(IFWItems.mithril_shovel.get());
                        output.accept(IFWItems.mithril_hoe.get());
                        output.accept(IFWItems.mithril_sword.get());
                        output.accept(IFWItems.mithril_axe.get());
                        output.accept(IFWItems.mithril_scythe.get());
                        output.accept(IFWItems.mithril_mattock.get());
                        output.accept(IFWItems.mithril_battle_axe.get());
                        output.accept(IFWItems.mithril_war_hammer.get());
                        output.accept(IFWItems.silver_pickaxe.get());
                        output.accept(IFWItems.silver_shears.get());
                        output.accept(IFWItems.silver_shovel.get());
                        output.accept(IFWItems.silver_hoe.get());
                        output.accept(IFWItems.silver_sword.get());
                        output.accept(IFWItems.silver_axe.get());
                        output.accept(IFWItems.silver_scythe.get());
                        output.accept(IFWItems.silver_mattock.get());
                        output.accept(IFWItems.silver_battle_axe.get());
                        output.accept(IFWItems.silver_war_hammer.get());
                        output.accept(IFWItems.silver_pickaxe.get());
                        output.accept(IFWItems.silver_shears.get());
                        output.accept(IFWItems.silver_shovel.get());
                        output.accept(IFWItems.silver_hoe.get());
                        output.accept(IFWItems.silver_sword.get());
                        output.accept(IFWItems.silver_axe.get());
                        output.accept(IFWItems.silver_scythe.get());
                        output.accept(IFWItems.silver_mattock.get());
                        output.accept(IFWItems.silver_battle_axe.get());
                        output.accept(IFWItems.silver_war_hammer.get());
                        output.accept(IFWItems.copper_pickaxe.get());
                        output.accept(IFWItems.copper_shears.get());
                        output.accept(IFWItems.copper_shovel.get());
                        output.accept(IFWItems.copper_hoe.get());
                        output.accept(IFWItems.copper_sword.get());
                        output.accept(IFWItems.copper_axe.get());
                        output.accept(IFWItems.copper_scythe.get());
                        output.accept(IFWItems.copper_mattock.get());
                        output.accept(IFWItems.copper_battle_axe.get());
                        output.accept(IFWItems.copper_war_hammer.get());
                        output.accept(IFWItems.gold_pickaxe.get());
                        output.accept(IFWItems.gold_shears.get());
                        output.accept(IFWItems.gold_shovel.get());
                        output.accept(IFWItems.gold_hoe.get());
                        output.accept(IFWItems.gold_sword.get());
                        output.accept(IFWItems.gold_axe.get());
                        output.accept(IFWItems.gold_scythe.get());
                        output.accept(IFWItems.gold_mattock.get());
                        output.accept(IFWItems.gold_battle_axe.get());
                        output.accept(IFWItems.gold_war_hammer.get());
                        output.accept(IFWItems.iron_pickaxe.get());
                        output.accept(IFWItems.iron_shears.get());
                        output.accept(IFWItems.iron_shovel.get());
                        output.accept(IFWItems.iron_hoe.get());
                        output.accept(IFWItems.iron_sword.get());
                        output.accept(IFWItems.iron_axe.get());
                        output.accept(IFWItems.iron_scythe.get());
                        output.accept(IFWItems.iron_mattock.get());
                        output.accept(IFWItems.iron_battle_axe.get());
                        output.accept(IFWItems.iron_war_hammer.get());
                        output.accept(IFWItems.rusted_iron_pickaxe.get());
                        output.accept(IFWItems.rusted_iron_shears.get());
                        output.accept(IFWItems.rusted_iron_shovel.get());
                        output.accept(IFWItems.rusted_iron_hoe.get());
                        output.accept(IFWItems.rusted_iron_sword.get());
                        output.accept(IFWItems.rusted_iron_axe.get());
                        output.accept(IFWItems.rusted_iron_scythe.get());
                        output.accept(IFWItems.rusted_iron_mattock.get());
                        output.accept(IFWItems.rusted_iron_battle_axe.get());
                        output.accept(IFWItems.rusted_iron_war_hammer.get());

                        output.accept(IFWItems.flint_hatchet.get());
                        output.accept(IFWItems.flint_knife.get());
                        output.accept(IFWItems.flint_shovel.get());
                        output.accept(IFWItems.flint_axe.get());
                        output.accept(IFWItems.wooden_shovel.get());
                        output.accept(IFWItems.wooden_cudgel.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> block_tab =
            CREATIVE_TABS.register("block_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.ifw.block"))
                    .icon(() -> IFWBlocks.adamantium_block_item.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(IFWBlocks.adamantium_block_item.get());
                        output.accept(IFWBlocks.adamantium_ore_item.get());
                        output.accept(IFWBlocks.adamantium_bars_item.get());
                        output.accept(IFWBlocks.adamantium_door_item.get());
                        output.accept(IFWBlocks.mithril_block_item.get());
                        output.accept(IFWBlocks.mithril_ore_item.get());
                        output.accept(IFWBlocks.mithril_bars_item.get());
                        output.accept(IFWBlocks.mithril_door_item.get());
                        output.accept(IFWBlocks.ancient_metal_block_item.get());
                        output.accept(IFWBlocks.ancient_metal_bars_item.get());
                        output.accept(IFWBlocks.ancient_metal_door_item.get());
                        output.accept(IFWBlocks.gold_bars_item.get());
                        output.accept(IFWBlocks.gold_door_item.get());
                        output.accept(IFWBlocks.silver_block_item.get());
                        output.accept(IFWBlocks.silver_ore_item.get());
                        output.accept(IFWBlocks.silver_bars_item.get());
                        output.accept(IFWBlocks.silver_door_item.get());
                        output.accept(IFWBlocks.copper_bars_item.get());
                        output.accept(IFWBlocks.copper_door_item.get());

                        output.accept(IFWBlocks.raw_adamantium_block_item.get());
                        output.accept(IFWBlocks.raw_mithril_block_item.get());
                        output.accept(IFWBlocks.raw_silver_block_item.get());
                        output.accept(IFWBlocks.deepslate_adamantium_ore_item.get());
                        output.accept(IFWBlocks.deepslate_mithril_ore_item.get());
                        output.accept(IFWBlocks.deepslate_silver_ore_ore_item.get());
                    }).build());


}
