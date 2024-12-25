package huix.infinity.common.item;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.item.tier.EnumTier;
import huix.infinity.common.item.tool.*;
import huix.infinity.common.item.tool.impl.IFWDiggerItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(InfinityWay.MOD_ID);


    public static final DeferredItem<Item> adamantium_ingot =
            ITEMS.registerSimpleItem("adamantium_ingot", new Item.Properties());
    public static final DeferredItem<Item> adamantium_nugget =
            ITEMS.registerSimpleItem("adamantium_nugget", new Item.Properties());
    public static final DeferredItem<Item> adamantium_shears =
            ITEMS.register("adamantium_shears", item -> new IFWShearsItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().component(DataComponents.TOOL, IFWShearsItem.createToolProperties())));
    public static final DeferredItem<Item> adamantium_shovel =
            ITEMS.register("adamantium_shovel", item -> new IFWShovelItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWShovelItem.createAttributes(EnumTier.ADAMANTIUM,1.0F, -3.0F))));
    public static final DeferredItem<Item> adamantium_hoe =
            ITEMS.register("adamantium_hoe", item -> new IFWHoeItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWHoeItem.createAttributes(EnumTier.ADAMANTIUM,0F, -2.1F))));
    public static final DeferredItem<Item> adamantium_sword =
            ITEMS.register("adamantium_sword", item -> new IFWSwordItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWSwordItem.createAttributes(EnumTier.ADAMANTIUM,4.0F, -2.4F))));
    public static final DeferredItem<Item> adamantium_pickaxe =
            ITEMS.register("adamantium_pickaxe", item -> new IFWPickaxeItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(EnumTier.ADAMANTIUM, 2, -2.7F))));
    public static final DeferredItem<Item> adamantium_axe =
            ITEMS.register("adamantium_axe", item -> new IFWAxeItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWAxeItem.createAttributes(EnumTier.ADAMANTIUM,3.0F, -3.1F))));
    public static final DeferredItem<Item> adamantium_scythe =
            ITEMS.register("adamantium_scythe", item -> new IFWScytheItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWScytheItem.createAttributes(EnumTier.ADAMANTIUM,1.0F, -2.6F))));
    public static final DeferredItem<Item> adamantium_mattock =
            ITEMS.register("adamantium_mattock", item -> new IFWMattockItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWMattockItem.createAttributes(EnumTier.ADAMANTIUM,0F, -2.6F))));
    public static final DeferredItem<Item> adamantium_battle_axe =
            ITEMS.register("adamantium_battle_axe", item -> new IFWBattleAxeItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWBattleAxeItem.createAttributes(EnumTier.ADAMANTIUM,4.0F, -2.8F))));
    public static final DeferredItem<Item> adamantium_war_hammer =
            ITEMS.register("adamantium_war_hammer", item -> new IFWWarHammerItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWWarHammerItem.createAttributes(EnumTier.ADAMANTIUM,4.0F, -2.8F))));
    public static final DeferredItem<Item> adamantium_dagger =
            ITEMS.register("adamantium_dagger", item -> new IFWDaggerItem(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(EnumTier.ADAMANTIUM,4.0F, -1.5F))));



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
