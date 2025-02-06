package huix.infinity.common.world.item;

import huix.infinity.common.world.food.IFWFoods;
import huix.infinity.common.world.item.tier.IFWArmorMaterials;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.init.InfinityWay;
import huix.infinity.util.DurabilityHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(InfinityWay.MOD_ID);


    public static final DeferredItem<Item> copper_bucket =
            ITEMS.register("copper_bucket", item -> new IFWBucketItem(Fluids.EMPTY, IFWTiers.COPPER, new Item.Properties()));
    public static final DeferredItem<Item> water_copper_bucket =
            ITEMS.register("water_copper_bucket", item -> new IFWBucketItem(Fluids.WATER, IFWTiers.COPPER, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> lava_copper_bucket =
            ITEMS.register("lava_copper_bucket", item -> new IFWBucketItem(Fluids.LAVA, IFWTiers.COPPER, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> powder_snow_copper_bucket =
            ITEMS.register("powder_snow_copper_bucket", item -> new IFWSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, IFWTiers.COPPER, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> milk_copper_bucket =
            ITEMS.register("milk_copper_bucket", item -> new MilkBucketItem(new Item.Properties().craftRemainder(copper_bucket.get()).stacksTo(1)));
    public static final DeferredItem<Item> stone_copper_bucket =
            ITEMS.registerSimpleItem("stone_copper_bucket", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> pufferfish_copper_bucket =
            ITEMS.register("pufferfish_copper_bucket", item -> new IFWMobBucketItem(EntityType.PUFFERFISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.COPPER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> salmon_copper_bucket =
            ITEMS.register("salmon_copper_bucket", item -> new IFWMobBucketItem(EntityType.SALMON,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.COPPER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> cod_copper_bucket =
            ITEMS.register("cod_copper_bucket", item -> new IFWMobBucketItem(EntityType.COD,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.COPPER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tropical_copper_bucket =
            ITEMS.register("tropical_copper_bucket", item -> new IFWMobBucketItem(EntityType.TROPICAL_FISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.COPPER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> axolotl_copper_bucket =
            ITEMS.register("axolotl_copper_bucket", item -> new IFWMobBucketItem(EntityType.AXOLOTL,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.COPPER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tadpole_copper_bucket =
            ITEMS.register("tadpole_copper_bucket", item -> new IFWMobBucketItem(EntityType.TADPOLE,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.COPPER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> stone_iron_bucket =
            ITEMS.registerSimpleItem("stone_iron_bucket", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> pufferfish_iron_bucket =
            ITEMS.register("pufferfish_iron_bucket", item -> new IFWMobBucketItem(EntityType.PUFFERFISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.IRON, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> salmon_iron_bucket =
            ITEMS.register("salmon_iron_bucket", item -> new IFWMobBucketItem(EntityType.SALMON,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.IRON, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> cod_iron_bucket =
            ITEMS.register("cod_iron_bucket", item -> new IFWMobBucketItem(EntityType.COD,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.IRON, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tropical_iron_bucket =
            ITEMS.register("tropical_iron_bucket", item -> new IFWMobBucketItem(EntityType.TROPICAL_FISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.IRON, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> axolotl_iron_bucket =
            ITEMS.register("axolotl_iron_bucket", item -> new IFWMobBucketItem(EntityType.AXOLOTL,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.IRON, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tadpole_iron_bucket =
            ITEMS.register("tadpole_iron_bucket", item -> new IFWMobBucketItem(EntityType.TADPOLE,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.IRON, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> silver_bucket =
            ITEMS.register("silver_bucket", item -> new IFWBucketItem(Fluids.EMPTY, IFWTiers.SILVER, new Item.Properties()));
    public static final DeferredItem<Item> water_silver_bucket =
            ITEMS.register("water_silver_bucket", item -> new IFWBucketItem(Fluids.WATER, IFWTiers.SILVER, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> lava_silver_bucket =
            ITEMS.register("lava_silver_bucket", item -> new IFWBucketItem(Fluids.LAVA, IFWTiers.SILVER, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> powder_snow_silver_bucket =
            ITEMS.register("powder_snow_silver_bucket", item -> new IFWSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, IFWTiers.SILVER, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> milk_silver_bucket =
            ITEMS.register("milk_silver_bucket", item -> new MilkBucketItem(new Item.Properties().craftRemainder(silver_bucket.get()).stacksTo(1)));
    public static final DeferredItem<Item> stone_silver_bucket =
            ITEMS.registerSimpleItem("stone_silver_bucket", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> pufferfish_silver_bucket =
            ITEMS.register("pufferfish_silver_bucket", item -> new IFWMobBucketItem(EntityType.PUFFERFISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.SILVER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> salmon_silver_bucket =
            ITEMS.register("salmon_silver_bucket", item -> new IFWMobBucketItem(EntityType.SALMON,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.SILVER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> cod_silver_bucket =
            ITEMS.register("cod_silver_bucket", item -> new IFWMobBucketItem(EntityType.COD,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.SILVER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tropical_silver_bucket =
            ITEMS.register("tropical_silver_bucket", item -> new IFWMobBucketItem(EntityType.TROPICAL_FISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.SILVER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> axolotl_silver_bucket =
            ITEMS.register("axolotl_silver_bucket", item -> new IFWMobBucketItem(EntityType.AXOLOTL,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.SILVER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tadpole_silver_bucket =
            ITEMS.register("tadpole_silver_bucket", item -> new IFWMobBucketItem(EntityType.TADPOLE,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.SILVER, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> gold_bucket =
            ITEMS.register("gold_bucket", item -> new IFWBucketItem(Fluids.EMPTY, IFWTiers.GOLD, new Item.Properties()));
    public static final DeferredItem<Item> water_gold_bucket =
            ITEMS.register("water_gold_bucket", item -> new IFWBucketItem(Fluids.WATER, IFWTiers.GOLD, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> lava_gold_bucket =
            ITEMS.register("lava_gold_bucket", item -> new IFWBucketItem(Fluids.LAVA, IFWTiers.GOLD, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> powder_snow_gold_bucket =
            ITEMS.register("powder_snow_gold_bucket", item -> new IFWSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, IFWTiers.GOLD, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> milk_gold_bucket =
            ITEMS.register("milk_gold_bucket", item -> new MilkBucketItem(new Item.Properties().craftRemainder(gold_bucket.get()).stacksTo(1)));
    public static final DeferredItem<Item> stone_gold_bucket =
            ITEMS.registerSimpleItem("stone_gold_bucket", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> pufferfish_gold_bucket =
            ITEMS.register("pufferfish_gold_bucket", item -> new IFWMobBucketItem(EntityType.PUFFERFISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.GOLD, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> salmon_gold_bucket =
            ITEMS.register("salmon_gold_bucket", item -> new IFWMobBucketItem(EntityType.SALMON,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.GOLD, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> cod_gold_bucket =
            ITEMS.register("cod_gold_bucket", item -> new IFWMobBucketItem(EntityType.COD,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.GOLD, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tropical_gold_bucket =
            ITEMS.register("tropical_gold_bucket", item -> new IFWMobBucketItem(EntityType.TROPICAL_FISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.GOLD, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> axolotl_gold_bucket =
            ITEMS.register("axolotl_gold_bucket", item -> new IFWMobBucketItem(EntityType.AXOLOTL,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.GOLD, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tadpole_gold_bucket =
            ITEMS.register("tadpole_gold_bucket", item -> new IFWMobBucketItem(EntityType.TADPOLE,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.GOLD, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> ancient_metal_bucket =
            ITEMS.register("ancient_metal_bucket", item -> new IFWBucketItem(Fluids.EMPTY, IFWTiers.ANCIENT_METAL, new Item.Properties()));
    public static final DeferredItem<Item> water_ancient_metal_bucket =
            ITEMS.register("water_ancient_metal_bucket", item -> new IFWBucketItem(Fluids.WATER, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> lava_ancient_metal_bucket =
            ITEMS.register("lava_ancient_metal_bucket", item -> new IFWBucketItem(Fluids.LAVA, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> powder_snow_ancient_metal_bucket =
            ITEMS.register("powder_snow_ancient_metal_bucket", item -> new IFWSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> milk_ancient_metal_bucket =
            ITEMS.register("milk_ancient_metal_bucket", item -> new MilkBucketItem(new Item.Properties().craftRemainder(ancient_metal_bucket.get()).stacksTo(1)));
    public static final DeferredItem<Item> stone_ancient_metal_bucket =
            ITEMS.registerSimpleItem("stone_ancient_metal_bucket", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> pufferfish_ancient_metal_bucket =
            ITEMS.register("pufferfish_ancient_metal_bucket", item -> new IFWMobBucketItem(EntityType.PUFFERFISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> salmon_ancient_metal_bucket =
            ITEMS.register("salmon_ancient_metal_bucket", item -> new IFWMobBucketItem(EntityType.SALMON,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> cod_ancient_metal_bucket =
            ITEMS.register("cod_ancient_metal_bucket", item -> new IFWMobBucketItem(EntityType.COD,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tropical_ancient_metal_bucket =
            ITEMS.register("tropical_ancient_metal_bucket", item -> new IFWMobBucketItem(EntityType.TROPICAL_FISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> axolotl_ancient_metal_bucket =
            ITEMS.register("axolotl_ancient_metal_bucket", item -> new IFWMobBucketItem(EntityType.AXOLOTL,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tadpole_ancient_metal_bucket =
            ITEMS.register("tadpole_ancient_metal_bucket", item -> new IFWMobBucketItem(EntityType.TADPOLE,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ANCIENT_METAL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> mithril_bucket =
            ITEMS.register("mithril_bucket", item -> new IFWBucketItem(Fluids.EMPTY, IFWTiers.MITHRIL, new Item.Properties()));
    public static final DeferredItem<Item> water_mithril_bucket =
            ITEMS.register("water_mithril_bucket", item -> new IFWBucketItem(Fluids.WATER, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> lava_mithril_bucket =
            ITEMS.register("lava_mithril_bucket", item -> new IFWBucketItem(Fluids.LAVA, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> powder_snow_mithril_bucket =
            ITEMS.register("powder_snow_mithril_bucket", item -> new IFWSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> milk_mithril_bucket =
            ITEMS.register("milk_mithril_bucket", item -> new MilkBucketItem(new Item.Properties().craftRemainder(mithril_bucket.get()).stacksTo(1)));
    public static final DeferredItem<Item> stone_mithril_bucket =
            ITEMS.registerSimpleItem("stone_mithril_bucket", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> pufferfish_mithril_bucket =
            ITEMS.register("pufferfish_mithril_bucket", item -> new IFWMobBucketItem(EntityType.PUFFERFISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> salmon_mithril_bucket =
            ITEMS.register("salmon_mithril_bucket", item -> new IFWMobBucketItem(EntityType.SALMON,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> cod_mithril_bucket =
            ITEMS.register("cod_mithril_bucket", item -> new IFWMobBucketItem(EntityType.COD,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tropical_mithril_bucket =
            ITEMS.register("tropical_mithril_bucket", item -> new IFWMobBucketItem(EntityType.TROPICAL_FISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> axolotl_mithril_bucket =
            ITEMS.register("axolotl_mithril_bucket", item -> new IFWMobBucketItem(EntityType.AXOLOTL,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tadpole_mithril_bucket =
            ITEMS.register("tadpole_mithril_bucket", item -> new IFWMobBucketItem(EntityType.TADPOLE,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.MITHRIL, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> adamantium_bucket =
            ITEMS.register("adamantium_bucket", item -> new IFWBucketItem(Fluids.EMPTY, IFWTiers.ADAMANTIUM, new Item.Properties()));
    public static final DeferredItem<Item> water_adamantium_bucket =
            ITEMS.register("water_adamantium_bucket", item -> new IFWBucketItem(Fluids.WATER, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> lava_adamantium_bucket =
            ITEMS.register("lava_adamantium_bucket", item -> new IFWBucketItem(Fluids.LAVA, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> powder_snow_adamantium_bucket =
            ITEMS.register("powder_snow_adamantium_bucket", item -> new IFWSolidBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> milk_adamantium_bucket =
            ITEMS.register("milk_adamantium_bucket", item -> new MilkBucketItem(new Item.Properties().craftRemainder(adamantium_bucket.get()).stacksTo(1)));
    public static final DeferredItem<Item> stone_adamantium_bucket =
            ITEMS.registerSimpleItem("stone_adamantium_bucket", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> pufferfish_adamantium_bucket =
            ITEMS.register("pufferfish_adamantium_bucket", item -> new IFWMobBucketItem(EntityType.PUFFERFISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> salmon_adamantium_bucket =
            ITEMS.register("salmon_adamantium_bucket", item -> new IFWMobBucketItem(EntityType.SALMON,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> cod_adamantium_bucket =
            ITEMS.register("cod_adamantium_bucket", item -> new IFWMobBucketItem(EntityType.COD,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tropical_adamantium_bucket =
            ITEMS.register("tropical_adamantium_bucket", item -> new IFWMobBucketItem(EntityType.TROPICAL_FISH,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> axolotl_adamantium_bucket =
            ITEMS.register("axolotl_adamantium_bucket", item -> new IFWMobBucketItem(EntityType.AXOLOTL,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
    public static final DeferredItem<Item> tadpole_adamantium_bucket =
            ITEMS.register("tadpole_adamantium_bucket", item -> new IFWMobBucketItem(EntityType.TADPOLE,
                    Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, IFWTiers.ADAMANTIUM, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));


    public static final DeferredItem<Item> manure =
            ITEMS.registerSimpleItem("manure", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> flour =
            ITEMS.registerSimpleItem("flour", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> copper_chain =
            ITEMS.registerSimpleItem("copper_chain", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> silver_chain =
            ITEMS.registerSimpleItem("silver_chain", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> golden_chain =
            ITEMS.registerSimpleItem("golden_chain", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> rusted_iron_chain =
            ITEMS.registerSimpleItem("rusted_iron_chain", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> iron_chain =
            ITEMS.registerSimpleItem("iron_chain", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> ancient_metal_chain =
            ITEMS.registerSimpleItem("ancient_metal_chain", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> mithril_chain =
            ITEMS.registerSimpleItem("mithril_chain", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> adamantium_chain =
            ITEMS.registerSimpleItem("adamantium_chain", new Item.Properties().stacksTo(16));

    public static final DeferredItem<Item> copper_helmet =
            ITEMS.register("copper_helmet", item -> new ArmorItem(IFWArmorMaterials.copper, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(4))));
    public static final DeferredItem<Item> copper_chestplate =
            ITEMS.register("copper_chestplate", item -> new ArmorItem(IFWArmorMaterials.copper, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(4))));
    public static final DeferredItem<Item> copper_leggings =
            ITEMS.register("copper_leggings", item -> new ArmorItem(IFWArmorMaterials.copper, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(4))));
    public static final DeferredItem<Item> copper_boots =
            ITEMS.register("copper_boots", item -> new ArmorItem(IFWArmorMaterials.copper, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(4))));
    public static final DeferredItem<Item> silver_helmet =
            ITEMS.register("silver_helmet", item -> new ArmorItem(IFWArmorMaterials.silver, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(4))));
    public static final DeferredItem<Item> silver_chestplate =
            ITEMS.register("silver_chestplate", item -> new ArmorItem(IFWArmorMaterials.silver, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(4))));
    public static final DeferredItem<Item> silver_leggings =
            ITEMS.register("silver_leggings", item -> new ArmorItem(IFWArmorMaterials.silver, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(4))));
    public static final DeferredItem<Item> silver_boots =
            ITEMS.register("silver_boots", item -> new ArmorItem(IFWArmorMaterials.silver, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(4))));
    public static final DeferredItem<Item> rusted_iron_helmet =
            ITEMS.register("rusted_iron_helmet", item -> new ArmorItem(IFWArmorMaterials.rusted_iron, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(3))));
    public static final DeferredItem<Item> rusted_iron_chestplate =
            ITEMS.register("rusted_iron_chestplate", item -> new ArmorItem(IFWArmorMaterials.rusted_iron, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(3))));
    public static final DeferredItem<Item> rusted_iron_leggings =
            ITEMS.register("rusted_iron_leggings", item -> new ArmorItem(IFWArmorMaterials.rusted_iron, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(3))));
    public static final DeferredItem<Item> rusted_iron_boots =
            ITEMS.register("rusted_iron_boots", item -> new ArmorItem(IFWArmorMaterials.rusted_iron, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(3))));



    public static final DeferredItem<Item> ancient_metal_helmet =
            ITEMS.register("ancient_metal_helmet", item -> new ArmorItem(IFWArmorMaterials.ancient_metal, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(16))));
    public static final DeferredItem<Item> ancient_metal_chestplate =
            ITEMS.register("ancient_metal_chestplate", item -> new ArmorItem(IFWArmorMaterials.ancient_metal, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(16))));
    public static final DeferredItem<Item> ancient_metal_leggings =
            ITEMS.register("ancient_metal_leggings", item -> new ArmorItem(IFWArmorMaterials.ancient_metal, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(16))));
    public static final DeferredItem<Item> ancient_metal_boots =
            ITEMS.register("ancient_metal_boots", item -> new ArmorItem(IFWArmorMaterials.ancient_metal, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(16))));
    public static final DeferredItem<Item> mithril_helmet =
            ITEMS.register("mithril_helmet", item -> new ArmorItem(IFWArmorMaterials.mithril, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(48))));
    public static final DeferredItem<Item> mithril_chestplate =
            ITEMS.register("mithril_chestplate", item -> new ArmorItem(IFWArmorMaterials.mithril, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(48))));
    public static final DeferredItem<Item> mithril_leggings =
            ITEMS.register("mithril_leggings", item -> new ArmorItem(IFWArmorMaterials.mithril, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(48))));
    public static final DeferredItem<Item> mithril_boots =
            ITEMS.register("mithril_boots", item -> new ArmorItem(IFWArmorMaterials.mithril, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(48))));
    public static final DeferredItem<Item> adamantium_helmet =
            ITEMS.register("adamantium_helmet", item -> new ArmorItem(IFWArmorMaterials.adamantium, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(192))));
    public static final DeferredItem<Item> adamantium_chestplate =
            ITEMS.register("adamantium_chestplate", item -> new ArmorItem(IFWArmorMaterials.adamantium, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(192))));
    public static final DeferredItem<Item> adamantium_leggings =
            ITEMS.register("adamantium_leggings", item -> new ArmorItem(IFWArmorMaterials.adamantium, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(192))));
    public static final DeferredItem<Item> adamantium_boots =
            ITEMS.register("adamantium_boots", item -> new ArmorItem(IFWArmorMaterials.adamantium, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(192))));
    public static final DeferredItem<Item> copper_chainmail_helmet =
            ITEMS.register("copper_chainmail_helmet", item -> new ArmorItem(IFWArmorMaterials.copper_chainmail, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(2))));
    public static final DeferredItem<Item> copper_chainmail_chestplate =
            ITEMS.register("copper_chainmail_chestplate", item -> new ArmorItem(IFWArmorMaterials.copper_chainmail, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(2))));
    public static final DeferredItem<Item> copper_chainmail_leggings =
            ITEMS.register("copper_chainmail_leggings", item -> new ArmorItem(IFWArmorMaterials.copper_chainmail, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(2))));
    public static final DeferredItem<Item> copper_chainmail_boots =
            ITEMS.register("copper_chainmail_boots", item -> new ArmorItem(IFWArmorMaterials.copper_chainmail, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(2))));
    public static final DeferredItem<Item> silver_chainmail_helmet =
            ITEMS.register("silver_chainmail_helmet", item -> new ArmorItem(IFWArmorMaterials.silver_chainmail, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(2))));
    public static final DeferredItem<Item> silver_chainmail_chestplate =
            ITEMS.register("silver_chainmail_chestplate", item -> new ArmorItem(IFWArmorMaterials.silver_chainmail, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(2))));
    public static final DeferredItem<Item> silver_chainmail_leggings =
            ITEMS.register("silver_chainmail_leggings", item -> new ArmorItem(IFWArmorMaterials.silver_chainmail, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(2))));
    public static final DeferredItem<Item> silver_chainmail_boots =
            ITEMS.register("silver_chainmail_boots", item -> new ArmorItem(IFWArmorMaterials.silver_chainmail, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(2))));
    public static final DeferredItem<Item> rusted_iron_chainmail_helmet =
            ITEMS.register("rusted_iron_chainmail_helmet", item -> new ArmorItem(IFWArmorMaterials.rusted_iron_chainmail, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(1.5F))));
    public static final DeferredItem<Item> rusted_iron_chainmail_chestplate =
            ITEMS.register("rusted_iron_chainmail_chestplate", item -> new ArmorItem(IFWArmorMaterials.rusted_iron_chainmail, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(1.5F))));
    public static final DeferredItem<Item> rusted_iron_chainmail_leggings =
            ITEMS.register("rusted_iron_chainmail_leggings", item -> new ArmorItem(IFWArmorMaterials.rusted_iron_chainmail, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(1.5F))));
    public static final DeferredItem<Item> rusted_iron_chainmail_boots =
            ITEMS.register("rusted_iron_chainmail_boots", item -> new ArmorItem(IFWArmorMaterials.rusted_iron_chainmail, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(1.5F))));
    public static final DeferredItem<Item> golden_chainmail_helmet =
            ITEMS.register("golden_chainmail_helmet", item -> new ArmorItem(IFWArmorMaterials.golden_chainmail, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(4))));
    public static final DeferredItem<Item> golden_chainmail_chestplate =
            ITEMS.register("golden_chainmail_chestplate", item -> new ArmorItem(IFWArmorMaterials.golden_chainmail, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(4))));
    public static final DeferredItem<Item> golden_chainmail_leggings =
            ITEMS.register("golden_chainmail_leggings", item -> new ArmorItem(IFWArmorMaterials.golden_chainmail, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(4))));
    public static final DeferredItem<Item> golden_chainmail_boots =
            ITEMS.register("golden_chainmail_boots", item -> new ArmorItem(IFWArmorMaterials.golden_chainmail, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(4))));
    public static final DeferredItem<Item> ancient_metal_chainmail_helmet =
            ITEMS.register("ancient_metal_chainmail_helmet", item -> new ArmorItem(IFWArmorMaterials.ancient_metal_chainmail, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(8))));
    public static final DeferredItem<Item> ancient_metal_chainmail_chestplate =
            ITEMS.register("ancient_metal_chainmail_chestplate", item -> new ArmorItem(IFWArmorMaterials.ancient_metal_chainmail, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(8))));
    public static final DeferredItem<Item> ancient_metal_chainmail_leggings =
            ITEMS.register("ancient_metal_chainmail_leggings", item -> new ArmorItem(IFWArmorMaterials.ancient_metal_chainmail, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(8))));
    public static final DeferredItem<Item> ancient_metal_chainmail_boots =
            ITEMS.register("ancient_metal_chainmail_boots", item -> new ArmorItem(IFWArmorMaterials.ancient_metal_chainmail, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(8))));
    public static final DeferredItem<Item> mithril_chainmail_helmet =
            ITEMS.register("mithril_chainmail_helmet", item -> new ArmorItem(IFWArmorMaterials.mithril_chainmail, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(32))));
    public static final DeferredItem<Item> mithril_chainmail_chestplate =
            ITEMS.register("mithril_chainmail_chestplate", item -> new ArmorItem(IFWArmorMaterials.mithril_chainmail, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(32))));
    public static final DeferredItem<Item> mithril_chainmail_leggings =
            ITEMS.register("mithril_chainmail_leggings", item -> new ArmorItem(IFWArmorMaterials.mithril_chainmail, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(32))));
    public static final DeferredItem<Item> mithril_chainmail_boots =
            ITEMS.register("mithril_chainmail_boots", item -> new ArmorItem(IFWArmorMaterials.mithril_chainmail, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(32))));
    public static final DeferredItem<Item> adamantium_chainmail_helmet =
            ITEMS.register("adamantium_chainmail_helmet", item -> new ArmorItem(IFWArmorMaterials.adamantium_chainmail, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(DurabilityHelper.Armor.HELMET.getDurability(96))));
    public static final DeferredItem<Item> adamantium_chainmail_chestplate =
            ITEMS.register("adamantium_chainmail_chestplate", item -> new ArmorItem(IFWArmorMaterials.adamantium_chainmail, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(DurabilityHelper.Armor.CHESTPLATE.getDurability(96))));
    public static final DeferredItem<Item> adamantium_chainmail_leggings =
            ITEMS.register("adamantium_chainmail_leggings", item -> new ArmorItem(IFWArmorMaterials.adamantium_chainmail, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(DurabilityHelper.Armor.LEGGINGS.getDurability(96))));
    public static final DeferredItem<Item> adamantium_chainmail_boots =
            ITEMS.register("adamantium_chainmail_boots", item -> new ArmorItem(IFWArmorMaterials.adamantium_chainmail, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(DurabilityHelper.Armor.BOOTS.getDurability(96))));
    public static final DeferredItem<Item> flint_shard =
            ITEMS.registerSimpleItem("flint_shard", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> obsidian_shard =
            ITEMS.registerSimpleItem("obsidian_shard", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> emerald_shard =
            ITEMS.registerSimpleItem("emerald_shard", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> diamond_shard =
            ITEMS.registerSimpleItem("diamond_shard", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> glass_shard =
            ITEMS.registerSimpleItem("glass_shard", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> quartz_shard =
            ITEMS.registerSimpleItem("quartz_shard", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> sinew =
            ITEMS.registerSimpleItem("sinew", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> wooden_shovel =
            ITEMS.register("wooden_shovel", item -> new ShovelTool(IFWTiers.WOOD, 400,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.WOOD,1.0F, -3.0F))));
    public static final DeferredItem<Item> wooden_club =
            ITEMS.register("wooden_club", item -> new ClubWeapon(IFWTiers.WOOD, 1000,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.WOOD,3.0F, -2.4F))));
    public static final DeferredItem<Item> flint_hatchet =
            ITEMS.register("flint_hatchet", item -> new HatchetTool(IFWTiers.FLINT, 360,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.FLINT,1.5F, -3.0F))));
    public static final DeferredItem<Item> flint_knife =
            ITEMS.register("flint_knife", item -> new KnifeWeapon(IFWTiers.FLINT, 400,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.FLINT,2.5F, 0.0F))));
    public static final DeferredItem<Item> flint_shovel =
            ITEMS.register("flint_shovel", item -> new ShovelTool(IFWTiers.FLINT, 400,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.FLINT,1.5F, -3.0F))));
    public static final DeferredItem<Item> flint_axe =
            ITEMS.register("flint_axe", item -> new AxeTool(IFWTiers.FLINT, 1200.0F,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.FLINT,2.0F, -3.0F))));
    public static final DeferredItem<Item> raw_adamantium =
            ITEMS.registerSimpleItem("raw_adamantium", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> raw_mithril =
            ITEMS.registerSimpleItem("raw_mithril", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> raw_silver =
            ITEMS.registerSimpleItem("raw_silver", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> adamantium_ingot =
            ITEMS.registerSimpleItem("adamantium_ingot", new Item.Properties().stacksTo(10));
    public static final DeferredItem<Item> adamantium_nugget =
            ITEMS.registerSimpleItem("adamantium_nugget", new Item.Properties());
    public static final DeferredItem<Item> adamantium_shears =
            ITEMS.register("adamantium_shears", item -> new ShearsWeapon(IFWTiers.ADAMANTIUM,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> adamantium_shovel =
            ITEMS.register("adamantium_shovel", item -> new ShovelTool(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.ADAMANTIUM,1.0F, -3.0F))));
    public static final DeferredItem<Item> adamantium_hoe =
            ITEMS.register("adamantium_hoe", item -> new HoeTool(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(HoeTool.createAttributes(IFWTiers.ADAMANTIUM,0F, -2.1F))));
    public static final DeferredItem<Item> adamantium_sword =
            ITEMS.register("adamantium_sword", item -> new SwordWeapon(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.ADAMANTIUM,4.0F, -2.4F))));
    public static final DeferredItem<Item> adamantium_pickaxe =
            ITEMS.register("adamantium_pickaxe", item -> new PickaxeTool(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.ADAMANTIUM, 2, -2.7F))));
    public static final DeferredItem<Item> adamantium_axe =
            ITEMS.register("adamantium_axe", item -> new AxeTool(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(AxeTool.createAttributes(IFWTiers.ADAMANTIUM,3.0F, -3.1F))));
    public static final DeferredItem<Item> adamantium_scythe =
            ITEMS.register("adamantium_scythe", item -> new ScytheTool(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(ScytheTool.createAttributes(IFWTiers.ADAMANTIUM,1.0F, -2.6F))));
    public static final DeferredItem<Item> adamantium_mattock =
            ITEMS.register("adamantium_mattock", item -> new MattockTool(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(MattockTool.createAttributes(IFWTiers.ADAMANTIUM,0F, -2.6F))));
    public static final DeferredItem<Item> adamantium_battle_axe =
            ITEMS.register("adamantium_battle_axe", item -> new BattleAxeTool(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(IFWTiers.ADAMANTIUM,4.0F, -2.8F))));
    public static final DeferredItem<Item> adamantium_war_hammer =
            ITEMS.register("adamantium_war_hammer", item -> new WarHammerTool(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(IFWTiers.ADAMANTIUM,4.0F, -2.8F))));
    public static final DeferredItem<Item> adamantium_dagger =
            ITEMS.register("adamantium_dagger", item -> new DaggerWeapon(IFWTiers.ADAMANTIUM,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.ADAMANTIUM,3.0F, -1.5F))));
    public static final DeferredItem<Item> ancient_metal_ingot =
            ITEMS.registerSimpleItem("ancient_metal_ingot", new Item.Properties().stacksTo(10));
    public static final DeferredItem<Item> ancient_metal_nugget =
            ITEMS.registerSimpleItem("ancient_metal_nugget", new Item.Properties());
    public static final DeferredItem<Item> ancient_metal_shears =
            ITEMS.register("ancient_metal_shears", item -> new ShearsWeapon(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> ancient_metal_shovel =
            ITEMS.register("ancient_metal_shovel", item -> new ShovelTool(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.ANCIENT_METAL,1.0F, -3.0F))));
    public static final DeferredItem<Item> ancient_metal_hoe =
            ITEMS.register("ancient_metal_hoe", item -> new HoeTool(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(HoeTool.createAttributes(IFWTiers.ANCIENT_METAL,0F, -2.1F))));
    public static final DeferredItem<Item> ancient_metal_sword =
            ITEMS.register("ancient_metal_sword", item -> new SwordWeapon(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.ANCIENT_METAL,4.0F, -2.4F))));
    public static final DeferredItem<Item> ancient_metal_pickaxe =
            ITEMS.register("ancient_metal_pickaxe", item -> new PickaxeTool(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.ANCIENT_METAL, 2, -2.7F))));
    public static final DeferredItem<Item> ancient_metal_axe =
            ITEMS.register("ancient_metal_axe", item -> new AxeTool(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(AxeTool.createAttributes(IFWTiers.ANCIENT_METAL,3.0F, -3.1F))));
    public static final DeferredItem<Item> ancient_metal_scythe =
            ITEMS.register("ancient_metal_scythe", item -> new ScytheTool(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(ScytheTool.createAttributes(IFWTiers.ANCIENT_METAL,1.0F, -2.6F))));
    public static final DeferredItem<Item> ancient_metal_mattock =
            ITEMS.register("ancient_metal_mattock", item -> new MattockTool(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(MattockTool.createAttributes(IFWTiers.ANCIENT_METAL,0F, -2.6F))));
    public static final DeferredItem<Item> ancient_metal_battle_axe =
            ITEMS.register("ancient_metal_battle_axe", item -> new BattleAxeTool(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(IFWTiers.ANCIENT_METAL,4.0F, -2.8F))));
    public static final DeferredItem<Item> ancient_metal_war_hammer =
            ITEMS.register("ancient_metal_war_hammer", item -> new WarHammerTool(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(IFWTiers.ANCIENT_METAL,4.0F, -2.8F))));
    public static final DeferredItem<Item> ancient_metal_dagger =
            ITEMS.register("ancient_metal_dagger", item -> new DaggerWeapon(IFWTiers.ANCIENT_METAL,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.ANCIENT_METAL,3.0F, -1.5F))));
    public static final DeferredItem<Item> mithril_ingot =
            ITEMS.registerSimpleItem("mithril_ingot", new Item.Properties().stacksTo(10));
    public static final DeferredItem<Item> mithril_nugget =
            ITEMS.registerSimpleItem("mithril_nugget", new Item.Properties());
    public static final DeferredItem<Item> mithril_shears =
            ITEMS.register("mithril_shears", item -> new ShearsWeapon(IFWTiers.MITHRIL,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> mithril_shovel =
            ITEMS.register("mithril_shovel", item -> new ShovelTool(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.MITHRIL,1.0F, -3.0F))));
    public static final DeferredItem<Item> mithril_hoe =
            ITEMS.register("mithril_hoe", item -> new HoeTool(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(HoeTool.createAttributes(IFWTiers.MITHRIL,0F, -2.1F))));
    public static final DeferredItem<Item> mithril_sword =
            ITEMS.register("mithril_sword", item -> new SwordWeapon(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.MITHRIL,4.0F, -2.4F))));
    public static final DeferredItem<Item> mithril_pickaxe =
            ITEMS.register("mithril_pickaxe", item -> new PickaxeTool(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.MITHRIL, 2, -2.7F))));
    public static final DeferredItem<Item> mithril_axe =
            ITEMS.register("mithril_axe", item -> new AxeTool(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(AxeTool.createAttributes(IFWTiers.MITHRIL,3.0F, -3.1F))));
    public static final DeferredItem<Item> mithril_scythe =
            ITEMS.register("mithril_scythe", item -> new ScytheTool(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(ScytheTool.createAttributes(IFWTiers.MITHRIL,1.0F, -2.6F))));
    public static final DeferredItem<Item> mithril_mattock =
            ITEMS.register("mithril_mattock", item -> new MattockTool(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(MattockTool.createAttributes(IFWTiers.MITHRIL,0F, -2.6F))));
    public static final DeferredItem<Item> mithril_battle_axe =
            ITEMS.register("mithril_battle_axe", item -> new BattleAxeTool(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(IFWTiers.MITHRIL,4.0F, -2.8F))));
    public static final DeferredItem<Item> mithril_war_hammer =
            ITEMS.register("mithril_war_hammer", item -> new WarHammerTool(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(IFWTiers.MITHRIL,4.0F, -2.8F))));
    public static final DeferredItem<Item> mithril_dagger =
            ITEMS.register("mithril_dagger", item -> new DaggerWeapon(IFWTiers.MITHRIL,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.MITHRIL,3.0F, -1.5F))));
    public static final DeferredItem<Item> silver_ingot =
            ITEMS.registerSimpleItem("silver_ingot", new Item.Properties().stacksTo(10));
    public static final DeferredItem<Item> silver_nugget =
            ITEMS.registerSimpleItem("silver_nugget", new Item.Properties());
    public static final DeferredItem<Item> silver_shears =
            ITEMS.register("silver_shears", item -> new ShearsWeapon(IFWTiers.SILVER,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> silver_shovel =
            ITEMS.register("silver_shovel", item -> new ShovelTool(IFWTiers.SILVER,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.SILVER,1.0F, -3.0F))));
    public static final DeferredItem<Item> silver_hoe =
            ITEMS.register("silver_hoe", item -> new HoeTool(IFWTiers.SILVER,
                    new Item.Properties().attributes(HoeTool.createAttributes(IFWTiers.SILVER,0F, -2.1F))));
    public static final DeferredItem<Item> silver_sword =
            ITEMS.register("silver_sword", item -> new SwordWeapon(IFWTiers.SILVER,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.SILVER,4.0F, -2.4F))));
    public static final DeferredItem<Item> silver_pickaxe =
            ITEMS.register("silver_pickaxe", item -> new PickaxeTool(IFWTiers.SILVER,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.SILVER, 2, -2.7F))));
    public static final DeferredItem<Item> silver_axe =
            ITEMS.register("silver_axe", item -> new AxeTool(IFWTiers.SILVER,
                    new Item.Properties().attributes(AxeTool.createAttributes(IFWTiers.SILVER,3.0F, -3.1F))));
    public static final DeferredItem<Item> silver_scythe =
            ITEMS.register("silver_scythe", item -> new ScytheTool(IFWTiers.SILVER,
                    new Item.Properties().attributes(ScytheTool.createAttributes(IFWTiers.SILVER,1.0F, -2.6F))));
    public static final DeferredItem<Item> silver_mattock =
            ITEMS.register("silver_mattock", item -> new MattockTool(IFWTiers.SILVER,
                    new Item.Properties().attributes(MattockTool.createAttributes(IFWTiers.SILVER,0F, -2.6F))));
    public static final DeferredItem<Item> silver_battle_axe =
            ITEMS.register("silver_battle_axe", item -> new BattleAxeTool(IFWTiers.SILVER,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(IFWTiers.SILVER,4.0F, -2.8F))));
    public static final DeferredItem<Item> silver_war_hammer =
            ITEMS.register("silver_war_hammer", item -> new WarHammerTool(IFWTiers.SILVER,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(IFWTiers.SILVER,4.0F, -2.8F))));
    public static final DeferredItem<Item> silver_dagger =
            ITEMS.register("silver_dagger", item -> new DaggerWeapon(IFWTiers.SILVER,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.SILVER,3.0F, -1.5F))));
    public static final DeferredItem<Item> copper_nugget =
            ITEMS.registerSimpleItem("copper_nugget", new Item.Properties().stacksTo(64));
    public static final DeferredItem<Item> copper_shears =
            ITEMS.register("copper_shears", item -> new ShearsWeapon(IFWTiers.COPPER,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> copper_shovel =
            ITEMS.register("copper_shovel", item -> new ShovelTool(IFWTiers.COPPER,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.COPPER,1.0F, -3.0F))));
    public static final DeferredItem<Item> copper_hoe =
            ITEMS.register("copper_hoe", item -> new HoeTool(IFWTiers.COPPER,
                    new Item.Properties().attributes(HoeTool.createAttributes(IFWTiers.COPPER,0F, -2.1F))));
    public static final DeferredItem<Item> copper_sword =
            ITEMS.register("copper_sword", item -> new SwordWeapon(IFWTiers.COPPER,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.COPPER,4.0F, -2.4F))));
    public static final DeferredItem<Item> copper_pickaxe =
            ITEMS.register("copper_pickaxe", item -> new PickaxeTool(IFWTiers.COPPER,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.COPPER, 2, -2.7F))));
    public static final DeferredItem<Item> copper_axe =
            ITEMS.register("copper_axe", item -> new AxeTool(IFWTiers.COPPER,
                    new Item.Properties().attributes(AxeTool.createAttributes(IFWTiers.COPPER,3.0F, -3.1F))));
    public static final DeferredItem<Item> copper_scythe =
            ITEMS.register("copper_scythe", item -> new ScytheTool(IFWTiers.COPPER,
                    new Item.Properties().attributes(ScytheTool.createAttributes(IFWTiers.COPPER,1.0F, -2.6F))));
    public static final DeferredItem<Item> copper_mattock =
            ITEMS.register("copper_mattock", item -> new MattockTool(IFWTiers.COPPER,
                    new Item.Properties().attributes(MattockTool.createAttributes(IFWTiers.COPPER,0F, -2.6F))));
    public static final DeferredItem<Item> copper_battle_axe =
            ITEMS.register("copper_battle_axe", item -> new BattleAxeTool(IFWTiers.COPPER,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(IFWTiers.COPPER,4.0F, -2.8F))));
    public static final DeferredItem<Item> copper_war_hammer =
            ITEMS.register("copper_war_hammer", item -> new WarHammerTool(IFWTiers.COPPER,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(IFWTiers.COPPER,4.0F, -2.8F))));
    public static final DeferredItem<Item> copper_dagger =
            ITEMS.register("copper_dagger", item -> new DaggerWeapon(IFWTiers.COPPER,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.COPPER,3.0F, -1.5F))));
    public static final DeferredItem<Item> iron_shears =
            ITEMS.register("iron_shears", item -> new ShearsWeapon(IFWTiers.IRON,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> iron_shovel =
            ITEMS.register("iron_shovel", item -> new ShovelTool(IFWTiers.IRON,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.IRON,1.0F, -3.0F))));
    public static final DeferredItem<Item> iron_hoe =
            ITEMS.register("iron_hoe", item -> new HoeTool(IFWTiers.IRON,
                    new Item.Properties().attributes(HoeTool.createAttributes(IFWTiers.IRON,0F, -2.1F))));
    public static final DeferredItem<Item> iron_pickaxe =
            ITEMS.register("iron_pickaxe", item -> new PickaxeTool(IFWTiers.IRON,
                    new Item.Properties().attributes(PickaxeTool.createAttributes(IFWTiers.IRON,2, -2.7F))));
    public static final DeferredItem<Item> iron_sword =
            ITEMS.register("iron_sword", item -> new SwordWeapon(IFWTiers.IRON,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.IRON,4.0F, -2.4F))));
    public static final DeferredItem<Item> iron_axe =
            ITEMS.register("iron_axe", item -> new AxeTool(IFWTiers.IRON,
                    new Item.Properties().attributes(AxeTool.createAttributes(IFWTiers.IRON,3.0F, -3.1F))));
    public static final DeferredItem<Item> iron_scythe =
            ITEMS.register("iron_scythe", item -> new ScytheTool(IFWTiers.IRON,
                    new Item.Properties().attributes(ScytheTool.createAttributes(IFWTiers.IRON,1.0F, -2.6F))));
    public static final DeferredItem<Item> iron_mattock =
            ITEMS.register("iron_mattock", item -> new MattockTool(IFWTiers.IRON,
                    new Item.Properties().attributes(MattockTool.createAttributes(IFWTiers.IRON,0F, -2.6F))));
    public static final DeferredItem<Item> iron_battle_axe =
            ITEMS.register("iron_battle_axe", item -> new BattleAxeTool(IFWTiers.IRON,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(IFWTiers.IRON,4.0F, -2.8F))));
    public static final DeferredItem<Item> iron_war_hammer =
            ITEMS.register("iron_war_hammer", item -> new WarHammerTool(IFWTiers.IRON,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(IFWTiers.IRON,4.0F, -2.8F))));
    public static final DeferredItem<Item> iron_dagger =
            ITEMS.register("iron_dagger", item -> new DaggerWeapon(IFWTiers.IRON,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.IRON,3.0F, -1.5F))));
    public static final DeferredItem<Item> golden_shears =
            ITEMS.register("golden_shears", item -> new ShearsWeapon(IFWTiers.GOLD,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> golden_shovel =
            ITEMS.register("golden_shovel", item -> new ShovelTool(IFWTiers.GOLD,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.GOLD,1.0F, -3.0F))));
    public static final DeferredItem<Item> golden_hoe =
            ITEMS.register("golden_hoe", item -> new HoeTool(IFWTiers.GOLD,
                    new Item.Properties().attributes(HoeTool.createAttributes(IFWTiers.GOLD,0F, -2.1F))));
    public static final DeferredItem<Item> golden_sword =
            ITEMS.register("golden_sword", item -> new SwordWeapon(IFWTiers.GOLD,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.GOLD, 4.0F, -2.4F))));
    public static final DeferredItem<Item> golden_pickaxe =
            ITEMS.register("golden_pickaxe", item -> new PickaxeTool(IFWTiers.GOLD,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.GOLD, 2, -2.7F))));
    public static final DeferredItem<Item> golden_axe =
            ITEMS.register("golden_axe", item -> new AxeTool(IFWTiers.GOLD,
                    new Item.Properties().attributes(AxeTool.createAttributes(IFWTiers.GOLD,3.0F, -3.1F))));
    public static final DeferredItem<Item> golden_scythe =
            ITEMS.register("golden_scythe", item -> new ScytheTool(IFWTiers.GOLD,
                    new Item.Properties().attributes(ScytheTool.createAttributes(IFWTiers.GOLD,1.0F, -2.6F))));
    public static final DeferredItem<Item> golden_mattock =
            ITEMS.register("golden_mattock", item -> new MattockTool(IFWTiers.GOLD,
                    new Item.Properties().attributes(MattockTool.createAttributes(IFWTiers.GOLD,0F, -2.6F))));
    public static final DeferredItem<Item> golden_battle_axe =
            ITEMS.register("golden_battle_axe", item -> new BattleAxeTool(IFWTiers.GOLD,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(IFWTiers.GOLD,4.0F, -2.8F))));
    public static final DeferredItem<Item> golden_war_hammer =
            ITEMS.register("golden_war_hammer", item -> new WarHammerTool(IFWTiers.GOLD,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(IFWTiers.GOLD,4.0F, -2.8F))));
    public static final DeferredItem<Item> golden_dagger =
            ITEMS.register("golden_dagger", item -> new DaggerWeapon(IFWTiers.GOLD,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.GOLD,3.0F, -1.5F))));
    public static final DeferredItem<Item> rusted_iron_shears =
            ITEMS.register("rusted_iron_shears", item -> new ShearsWeapon(IFWTiers.RUSTED_IRON,
                    new Item.Properties().component(DataComponents.TOOL, ShearsWeapon.createToolProperties())));
    public static final DeferredItem<Item> rusted_iron_shovel =
            ITEMS.register("rusted_iron_shovel", item -> new ShovelTool(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(ShovelTool.createAttributes(IFWTiers.RUSTED_IRON,1.0F, -3.0F))));
    public static final DeferredItem<Item> rusted_iron_hoe =
            ITEMS.register("rusted_iron_hoe", item -> new HoeTool(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(HoeTool.createAttributes(IFWTiers.RUSTED_IRON,0F, -2.1F))));
    public static final DeferredItem<Item> rusted_iron_sword =
            ITEMS.register("rusted_iron_sword", item -> new SwordWeapon(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.RUSTED_IRON,4.0F, -2.4F))));
    public static final DeferredItem<Item> rusted_iron_pickaxe =
            ITEMS.register("rusted_iron_pickaxe", item -> new PickaxeTool(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(IFWDiggerItem.createAttributes(IFWTiers.RUSTED_IRON, 2, -2.7F))));
    public static final DeferredItem<Item> rusted_iron_axe =
            ITEMS.register("rusted_iron_axe", item -> new AxeTool(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(AxeTool.createAttributes(IFWTiers.RUSTED_IRON,3.0F, -3.1F))));
    public static final DeferredItem<Item> rusted_iron_scythe =
            ITEMS.register("rusted_iron_scythe", item -> new ScytheTool(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(ScytheTool.createAttributes(IFWTiers.RUSTED_IRON,1.0F, -2.6F))));
    public static final DeferredItem<Item> rusted_iron_mattock =
            ITEMS.register("rusted_iron_mattock", item -> new MattockTool(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(MattockTool.createAttributes(IFWTiers.RUSTED_IRON,0F, -2.6F))));
    public static final DeferredItem<Item> rusted_iron_battle_axe =
            ITEMS.register("rusted_iron_battle_axe", item -> new BattleAxeTool(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(BattleAxeTool.createAttributes(IFWTiers.RUSTED_IRON,4.0F, -2.8F))));
    public static final DeferredItem<Item> rusted_iron_war_hammer =
            ITEMS.register("rusted_iron_war_hammer", item -> new WarHammerTool(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(WarHammerTool.createAttributes(IFWTiers.RUSTED_IRON,4.0F, -2.8F))));
    public static final DeferredItem<Item> rusted_iron_dagger =
            ITEMS.register("rusted_iron_dagger", item -> new DaggerWeapon(IFWTiers.RUSTED_IRON,
                    new Item.Properties().attributes(SwordWeapon.createAttributes(IFWTiers.RUSTED_IRON,3.0F, -1.5F))));

    //Food
    public static final DeferredItem<Item> salad =
            ITEMS.registerSimpleItem("salad", new Item.Properties().stacksTo(4).food(IFWFoods.salad));
    public static final DeferredItem<Item> milk_bowl =
            ITEMS.registerSimpleItem("milk_bowl", new Item.Properties().stacksTo(4).food(IFWFoods.milk_bowl));
    public static final DeferredItem<Item> water_bowl =
            ITEMS.registerSimpleItem("water_bowl", new Item.Properties().stacksTo(4));
    public static final DeferredItem<Item> cheese =
            ITEMS.registerSimpleItem("cheese", new Item.Properties().stacksTo(8).food(IFWFoods.cheese));
    public static final DeferredItem<Item> dough =
            ITEMS.registerSimpleItem("dough", new Item.Properties().stacksTo(8).food(IFWFoods.dough));
    public static final DeferredItem<Item> chocolate =
            ITEMS.registerSimpleItem("chocolate", new Item.Properties().stacksTo(16).food(IFWFoods.chocolate));
    public static final DeferredItem<Item> cereal =
            ITEMS.registerSimpleItem("cereal", new Item.Properties().stacksTo(4).food(IFWFoods.cereal));
    public static final DeferredItem<Item> pumpkin_soup =
            ITEMS.registerSimpleItem("pumpkin_soup", new Item.Properties().stacksTo(4).food(IFWFoods.pumpkin_soup));
    public static final DeferredItem<Item> mushroom_soup_cream =
            ITEMS.registerSimpleItem("mushroom_soup_cream", new Item.Properties().stacksTo(4).food(IFWFoods.mushroom_soup_cream));
    public static final DeferredItem<Item> vegetable_soup =
            ITEMS.registerSimpleItem("vegetable_soup", new Item.Properties().stacksTo(4).food(IFWFoods.vegetable_soup));
    public static final DeferredItem<Item> vegetable_soup_cream =
            ITEMS.registerSimpleItem("vegetable_soup_cream", new Item.Properties().stacksTo(4).food(IFWFoods.vegetable_soup_cream));
    public static final DeferredItem<Item> chicken_soup =
            ITEMS.registerSimpleItem("chicken_soup", new Item.Properties().stacksTo(4).food(IFWFoods.chicken_soup));
    public static final DeferredItem<Item> beef_stew =
            ITEMS.registerSimpleItem("beef_stew", new Item.Properties().stacksTo(4).food(IFWFoods.beef_stew));
    public static final DeferredItem<Item> porridge =
            ITEMS.registerSimpleItem("porridge", new Item.Properties().stacksTo(4).food(IFWFoods.porridge));
    public static final DeferredItem<Item> sorbet =
            ITEMS.registerSimpleItem("sorbet", new Item.Properties().stacksTo(4).food(IFWFoods.sorbet));
    public static final DeferredItem<Item> mashed_potato =
            ITEMS.registerSimpleItem("mashed_potato", new Item.Properties().stacksTo(4).food(IFWFoods.mashed_potato));
    public static final DeferredItem<Item> ice_cream =
            ITEMS.registerSimpleItem("ice_cream", new Item.Properties().stacksTo(4).food(IFWFoods.ice_cream));
    public static final DeferredItem<Item> orange =
            ITEMS.registerSimpleItem("orange", new Item.Properties().stacksTo(16).food(IFWFoods.orange));
    public static final DeferredItem<Item> banana =
            ITEMS.registerSimpleItem("banana", new Item.Properties().stacksTo(16).food(IFWFoods.banana));
    public static final DeferredItem<Item> blueberry =
            ITEMS.registerSimpleItem("blueberry", new Item.Properties().stacksTo(16).food(IFWFoods.blueberry));
    public static final DeferredItem<Item> cooked_worm =
            ITEMS.registerSimpleItem("cooked_worm", new Item.Properties().stacksTo(16).food(IFWFoods.cooked_worm));
    public static final DeferredItem<Item> worm =
            ITEMS.registerSimpleItem("worm", new Item.Properties().stacksTo(16).food(IFWFoods.worm));
    public static final DeferredItem<Item> onion =
            ITEMS.registerSimpleItem("onion", new Item.Properties().stacksTo(16).food(IFWFoods.onion));
}
