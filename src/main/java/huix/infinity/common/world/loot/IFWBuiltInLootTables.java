package huix.infinity.common.world.loot;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IFWBuiltInLootTables {
    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet<>();
    private static final Set<ResourceKey<LootTable>> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);
    public static final ResourceKey<LootTable> SHEEP_WHITE = register("entities/sheep/white");
    public static final ResourceKey<LootTable> SHEEP_ORANGE = register("entities/sheep/orange");
    public static final ResourceKey<LootTable> SHEEP_MAGENTA = register("entities/sheep/magenta");
    public static final ResourceKey<LootTable> SHEEP_LIGHT_BLUE = register("entities/sheep/light_blue");
    public static final ResourceKey<LootTable> SHEEP_YELLOW = register("entities/sheep/yellow");
    public static final ResourceKey<LootTable> SHEEP_LIME = register("entities/sheep/lime");
    public static final ResourceKey<LootTable> SHEEP_PINK = register("entities/sheep/pink");
    public static final ResourceKey<LootTable> SHEEP_GRAY = register("entities/sheep/gray");
    public static final ResourceKey<LootTable> SHEEP_LIGHT_GRAY = register("entities/sheep/light_gray");
    public static final ResourceKey<LootTable> SHEEP_CYAN = register("entities/sheep/cyan");
    public static final ResourceKey<LootTable> SHEEP_PURPLE = register("entities/sheep/purple");
    public static final ResourceKey<LootTable> SHEEP_BLUE = register("entities/sheep/blue");
    public static final ResourceKey<LootTable> SHEEP_BROWN = register("entities/sheep/brown");
    public static final ResourceKey<LootTable> SHEEP_GREEN = register("entities/sheep/green");
    public static final ResourceKey<LootTable> SHEEP_RED = register("entities/sheep/red");
    public static final ResourceKey<LootTable> SHEEP_BLACK = register("entities/sheep/black");

    private static ResourceKey<LootTable> register(String name) {
        return register(ResourceKey.create(Registries.LOOT_TABLE,
                ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, name)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> name) {
        if (LOCATIONS.add(name)) {
            return name;
        } else {
            throw new IllegalArgumentException(name.location() + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceKey<LootTable>> all() {
        return IMMUTABLE_LOCATIONS;
    }
}
