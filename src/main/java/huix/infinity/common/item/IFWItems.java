package huix.infinity.common.item;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.item.tier.EnumTier;
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
            ITEMS.register("adamantium_shears", item -> new ShearsWeapon(EnumTier.ADAMANTIUM,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> adamantium_shovel =
            ITEMS.register("adamantium_shovel", item -> new ShovelTool(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(ShovelTool.createAttributes(EnumTier.ADAMANTIUM,1.0F, -3.0F))));
    public static final DeferredItem<Item> adamantium_hoe =
            ITEMS.register("adamantium_hoe", item -> new HoeTool(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(HoeTool.createAttributes(EnumTier.ADAMANTIUM,0F, -2.1F))));
    public static final DeferredItem<Item> adamantium_sword =
            ITEMS.register("adamantium_sword", item -> new SwordWeapon(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(EnumTier.ADAMANTIUM,4.0F, -2.4F))));
    public static final DeferredItem<Item> adamantium_pickaxe =
            ITEMS.register("adamantium_pickaxe", item -> new PickaxeTool(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(EnumTier.ADAMANTIUM, 2, -2.7F))));
    public static final DeferredItem<Item> adamantium_axe =
            ITEMS.register("adamantium_axe", item -> new AxeTool(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(AxeTool.createAttributes(EnumTier.ADAMANTIUM,3.0F, -3.1F))));
    public static final DeferredItem<Item> adamantium_scythe =
            ITEMS.register("adamantium_scythe", item -> new ScytheTool(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(ScytheTool.createAttributes(EnumTier.ADAMANTIUM,1.0F, -2.6F))));
    public static final DeferredItem<Item> adamantium_mattock =
            ITEMS.register("adamantium_mattock", item -> new MattockTool(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(MattockTool.createAttributes(EnumTier.ADAMANTIUM,0F, -2.6F))));
    public static final DeferredItem<Item> adamantium_battle_axe =
            ITEMS.register("adamantium_battle_axe", item -> new BattleAxeTool(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(EnumTier.ADAMANTIUM,4.0F, -2.8F))));
    public static final DeferredItem<Item> adamantium_war_hammer =
            ITEMS.register("adamantium_war_hammer", item -> new WarHammerTool(EnumTier.ADAMANTIUM,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(EnumTier.ADAMANTIUM,4.0F, -2.8F))));
    public static final DeferredItem<Item> adamantium_dagger =
            ITEMS.register("adamantium_dagger", item -> new DaggerWeapon(EnumTier.ADAMANTIUM,
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
