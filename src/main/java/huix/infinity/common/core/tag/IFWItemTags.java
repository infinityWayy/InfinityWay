package huix.infinity.common.core.tag;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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

    public static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, name));
    }
}
