package huix.infinity.common.core.tag;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class IFWItemTags {

    public static final TagKey<Item> SCYTHE = bind("scythe");
    public static final TagKey<Item> MATTOCK = bind("mattock");
    public static final TagKey<Item> BATTLE_AXE = bind("battle_axe");
    public static final TagKey<Item> WAR_HAMMER = bind("war_hammer");
    public static final TagKey<Item> DAGGER = bind("dagger");
    public static final TagKey<Item> ANVIL = bind("anvil");
    public static final TagKey<Item> BUCKETS_TROPICAL_FISH = bind("buckets_tropical_fish");
    public static final TagKey<Item> BUCKETS_STONE = bind("buckets_stone");
    public static final TagKey<Item> HAS_ENCHANTING_RECIPE = bind("has_enchanting_recipe");
    public static final TagKey<Item> SILVER_ITEM = bind("silver_item");
    public static final TagKey<Item> NO_MINING_TOOLS = bind("no_mining_tools");
    public static final TagKey<Item> ACID_IMMUNE = bind("acid_immune");

    public static final TagKey<Item> ANIMAL_PRODUCTS = bind("animal_products");
    public static final TagKey<Item> VEGETABLES = bind("vegetables");
    public static final TagKey<Item> DRINKS = bind("drinks");
    public static final TagKey<Item> ARMORS = bind("armors");

    public static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, name));
    }
}
