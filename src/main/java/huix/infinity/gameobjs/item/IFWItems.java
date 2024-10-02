package huix.infinity.gameobjs.item;

import huix.infinity.InfinityWay;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(InfinityWay.MOD_ID);

    public static final DeferredItem<Item> adamantium_ingot =
            ITEMS.registerSimpleItem("adamantium_ingot", new Item.Properties());
    public static final DeferredItem<Item> ancient_metal_ingot =
            ITEMS.registerSimpleItem("ancient_metal_ingot", new Item.Properties());
    public static final DeferredItem<Item> mithril_ingot =
            ITEMS.registerSimpleItem("mithril_ingot", new Item.Properties());
    public static final DeferredItem<Item> silver_ingot =
            ITEMS.registerSimpleItem("silver_ingot", new Item.Properties());


}
