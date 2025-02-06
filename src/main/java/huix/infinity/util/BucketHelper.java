package huix.infinity.util;

import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BucketHelper {

    public static ItemStack emptyBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.copper_bucket.get());
            case IRON -> new ItemStack(Items.BUCKET);
            case SILVER -> new ItemStack(IFWItems.silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack waterBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.water_copper_bucket.get());
            case IRON -> new ItemStack(Items.WATER_BUCKET);
            case SILVER -> new ItemStack(IFWItems.water_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.water_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.water_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.water_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.water_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack lavaBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.lava_copper_bucket.get());
            case IRON -> new ItemStack(Items.LAVA_BUCKET);
            case SILVER -> new ItemStack(IFWItems.lava_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.lava_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.lava_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.lava_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.lava_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack milkBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.milk_copper_bucket.get());
            case IRON -> new ItemStack(Items.MILK_BUCKET);
            case SILVER -> new ItemStack(IFWItems.milk_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.milk_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.milk_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.milk_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.milk_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack powderSnowBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.powder_snow_copper_bucket.get());
            case IRON -> new ItemStack(Items.POWDER_SNOW_BUCKET);
            case SILVER -> new ItemStack(IFWItems.powder_snow_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.powder_snow_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.powder_snow_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.powder_snow_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.powder_snow_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack stoneBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.stone_copper_bucket.get());
            case IRON -> new ItemStack(IFWItems.stone_iron_bucket.get());
            case SILVER -> new ItemStack(IFWItems.stone_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.stone_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.stone_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.stone_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.stone_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack pufferFishBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.pufferfish_copper_bucket.get());
            case IRON -> new ItemStack(IFWItems.pufferfish_iron_bucket.get());
            case SILVER -> new ItemStack(IFWItems.pufferfish_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.pufferfish_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.pufferfish_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.pufferfish_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.pufferfish_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack salmonBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.salmon_copper_bucket.get());
            case IRON -> new ItemStack(IFWItems.salmon_iron_bucket.get());
            case SILVER -> new ItemStack(IFWItems.salmon_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.salmon_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.salmon_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.salmon_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.salmon_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack codBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.cod_copper_bucket.get());
            case IRON -> new ItemStack(IFWItems.cod_iron_bucket.get());
            case SILVER -> new ItemStack(IFWItems.cod_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.cod_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.cod_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.cod_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.cod_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack tropicalBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.tropical_copper_bucket.get());
            case IRON -> new ItemStack(IFWItems.tropical_iron_bucket.get());
            case SILVER -> new ItemStack(IFWItems.tropical_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.tropical_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.tropical_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.tropical_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.tropical_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack axolotlBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.axolotl_copper_bucket.get());
            case IRON -> new ItemStack(IFWItems.axolotl_iron_bucket.get());
            case SILVER -> new ItemStack(IFWItems.axolotl_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.axolotl_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.axolotl_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.axolotl_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.axolotl_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static ItemStack tadpoleBucket(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER -> new ItemStack(IFWItems.tadpole_copper_bucket.get());
            case IRON -> new ItemStack(IFWItems.tadpole_iron_bucket.get());
            case SILVER -> new ItemStack(IFWItems.tadpole_silver_bucket.get());
            case GOLD -> new ItemStack(IFWItems.tadpole_gold_bucket.get());
            case ANCIENT_METAL -> new ItemStack(IFWItems.tadpole_ancient_metal_bucket.get());
            case MITHRIL -> new ItemStack(IFWItems.tadpole_mithril_bucket.get());
            case ADAMANTIUM -> new ItemStack(IFWItems.tadpole_adamantium_bucket.get());
            default -> ItemStack.EMPTY;
        };
    }

    public static float chanceOfMeltingWhenFilledWithLava(IFWTiers tiers) {
        return switch (tiers) {
            case COPPER, SILVER -> 0.16F;
            case GOLD -> 0.2F;
            case IRON -> 0.08F;
            case ANCIENT_METAL -> 0.04F;
            case MITHRIL -> 0.01F;
            case ADAMANTIUM -> 0.0F;
            default -> 1.0F;
        };
    }

}
