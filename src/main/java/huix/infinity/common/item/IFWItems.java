package huix.infinity.common.item;

import huix.infinity.InfinityWay;
import huix.infinity.common.item.tier.EnumTier;
import huix.infinity.common.item.tool.IFWAxeItem;
import huix.infinity.common.item.tool.IFWPickaxeItem;
import huix.infinity.common.item.tool.IFWShearsItem;
import huix.infinity.common.item.tool.impl.IFWDiggerItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(InfinityWay.MOD_ID);


    public static final DeferredItem<Item> adamantium_ingot =
            ITEMS.registerSimpleItem("adamantium_ingot", new Item.Properties());
    public static final DeferredItem<Item> adamantium_nugget =
            ITEMS.registerSimpleItem("adamantium_nugget", new Item.Properties());
    public static final DeferredItem<Item> adamantium_pickaxe =
            ITEMS.register("adamantium_pickaxe", item -> new IFWPickaxeItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(EnumTier.ADAMANTIUM, 1.0F, -2.8F))));
    public static final DeferredItem<Item> adamantium_axe =
            ITEMS.register("adamantium_axe", item -> new IFWAxeItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(EnumTier.ADAMANTIUM, 3.0F, -3F))));
    public static final DeferredItem<Item> adamantium_shears =
            ITEMS.register("adamantium_shears", item -> new IFWShearsItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().component(DataComponents.TOOL, IFWShearsItem.createToolProperties())));


    public static final DeferredItem<Item> ancient_metal_ingot =
            ITEMS.registerSimpleItem("ancient_metal_ingot", new Item.Properties());
    public static final DeferredItem<Item> ancient_metal_nugget =
            ITEMS.registerSimpleItem("ancient_metal_nugget", new Item.Properties());

    public static final DeferredItem<Item> mithril_ingot =
            ITEMS.registerSimpleItem("mithril_ingot", new Item.Properties());
    public static final DeferredItem<Item> mithril_nugget =
            ITEMS.registerSimpleItem("mithril_nugget", new Item.Properties());

    public static final DeferredItem<Item> silver_ingot =
            ITEMS.registerSimpleItem("silver_ingot", new Item.Properties());
    public static final DeferredItem<Item> silver_nugget =
            ITEMS.registerSimpleItem("silver_nugget", new Item.Properties());

    public static final DeferredItem<Item> copper_nugget =
            ITEMS.registerSimpleItem("copper_nugget", new Item.Properties());


}
