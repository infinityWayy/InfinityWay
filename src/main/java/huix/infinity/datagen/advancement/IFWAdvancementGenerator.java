package huix.infinity.datagen.advancement;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.world.entity.EntityType;


import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;


public class IFWAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                        Items.GRASS_BLOCK,
                        Component.translatable("advancement.ifw.root"),
                        Component.translatable("advancement.ifw.root.desc"),
                        ResourceLocation.fromNamespaceAndPath("ifw", "textures/gui/advancements/backgrounds/xh.png"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false)
                .addCriterion(
                        "tick",
                        PlayerTrigger.TriggerInstance.tick())
                .save(saver, "ifw:root");

        AdvancementHolder openInventory = Advancement.Builder.advancement()
                .display(
                        Items.BOOK,
                        Component.translatable("advancement.ifw.open_inventory"),
                        Component.translatable("advancement.ifw.open_inventory.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(root)
                .addCriterion(
                        "has_inventory",
                        new Criterion<>(
                                CriteriaTriggers.INVENTORY_CHANGED,
                                new InventoryChangeTrigger.TriggerInstance(
                                        Optional.empty(),
                                        new InventoryChangeTrigger.TriggerInstance.Slots(
                                                MinMaxBounds.Ints.ANY,
                                                MinMaxBounds.Ints.ANY,
                                                MinMaxBounds.Ints.ANY),
                                        List.of())))
                .save(saver, "ifw:open_inventory");
//怪物杀手
        AdvancementHolder killZombie = Advancement.Builder.advancement()
                .display(
                        Items.IRON_SWORD,
                        Component.translatable("advancement.ifw.kill_monster"),
                        Component.translatable("advancement.ifw.kill_monster.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(openInventory)
                .addCriterion(
                        "kill_zombie",
                        CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
                                KilledTrigger.TriggerInstance.playerKilledEntity(
                                        EntityPredicate.Builder.entity().of(EntityType.ZOMBIE)
                                ).triggerInstance()))
                .addCriterion(
                        "kill_creeper",
                        CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
                                KilledTrigger.TriggerInstance.playerKilledEntity(
                                        EntityPredicate.Builder.entity().of(EntityType.CREEPER)
                                ).triggerInstance()))
                .addCriterion(
                        "kill_skeleton",
                        CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
                                KilledTrigger.TriggerInstance.playerKilledEntity(
                                        EntityPredicate.Builder.entity().of(EntityType.SKELETON)
                                ).triggerInstance()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(saver, "ifw:kill_one_of_three");
//粘土熔炉
        AdvancementHolder obtainClayFurnace = Advancement.Builder.advancement()
                .display(
                        IFWBlocks.clay_furnace_item,
                        Component.translatable("advancement.ifw.obtain_clay_furnace"),
                        Component.translatable("advancement.ifw.obtain_clay_furnace.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(openInventory)
                .addCriterion(
                        "has_clay_furnace",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                IFWBlocks.clay_furnace_item))
                .save(saver, "ifw:obtain_clay_furnace");
//草种子
        AdvancementHolder obtainWheatSeeds = Advancement.Builder.advancement()
                .display(
                        Items.WHEAT_SEEDS,
                        Component.translatable("advancement.ifw.obtain_wheat_seeds"),
                        Component.translatable("advancement.ifw.obtain_wheat_seeds.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(openInventory)
                .addCriterion(
                        "has_wheat_seeds",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                Items.WHEAT_SEEDS))
                .save(saver, "ifw:obtain_wheat_seeds");
//鸡蛋
        AdvancementHolder eatEgg = Advancement.Builder.advancement()
                .display(
                        Items.EGG,
                        Component.translatable("advancement.ifw.eat_egg"),
                        Component.translatable("advancement.ifw.eat_egg.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(obtainWheatSeeds)
                .addCriterion(
                        "consume_egg",
                        ConsumeItemTrigger.TriggerInstance.usedItem(
                                Items.EGG))
                .save(saver, "ifw:eat_egg");
//木棍
        AdvancementHolder getStick = Advancement.Builder.advancement()
                .display(
                        Items.STICK,
                        Component.translatable("advancement.ifw.get_stick"),
                        Component.translatable("advancement.ifw.get_stick.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(openInventory)
                .addCriterion(
                        "has_stick",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
                .save(saver, "ifw:get_stick");

//燧石
        AdvancementHolder getFlint = Advancement.Builder.advancement()
                .display(
                        Items.FLINT,
                        Component.translatable("advancement.ifw.get_flint"),
                        Component.translatable("advancement.ifw.get_flint.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(openInventory)
                .addCriterion(
                        "has_flint",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(saver, "ifw:get_flint");
//燧石短斧
        AdvancementHolder makeFlintHatchet = Advancement.Builder.advancement()
                .display(
                        IFWItems.flint_hatchet.get(),
                        Component.translatable("advancement.ifw.make_flint_hatchet"),
                        Component.translatable("advancement.ifw.make_flint_hatchet.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getStick)
                .addCriterion(
                        "has_flint_hatchet",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.flint_hatchet.get()))
                .save(saver, "ifw:make_flint_hatchet");
//木头
        AdvancementHolder getWood = Advancement.Builder.advancement()
                .display(
                        Items.OAK_LOG,
                        Component.translatable("advancement.ifw.get_wood"),
                        Component.translatable("advancement.ifw.get_wood.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeFlintHatchet)
                .addCriterion(
                        "has_wood",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.OAK_LOG))
                .save(saver, "ifw:get_wood");
//燧石工作台
        AdvancementHolder makeCraftingTable = Advancement.Builder.advancement()
                .display(
                        Items.CRAFTING_TABLE,
                        Component.translatable("advancement.ifw.make_crafting_table"),
                        Component.translatable("advancement.ifw.make_crafting_table.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getWood)
                .addCriterion(
                        "has_crafting_table",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(saver, "ifw:make_crafting_table");
//木棒
        AdvancementHolder getWoodenMallet = Advancement.Builder.advancement()
                .display(
                        IFWItems.wooden_club,
                        Component.translatable("advancement.ifw.get_wooden_mallet"),
                        Component.translatable("advancement.ifw.get_wooden_mallet.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeCraftingTable)
                .addCriterion(
                        "has_wooden_mallet",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.wooden_club))
                .save(saver, "ifw:get_wooden_mallet");
//皮革
        AdvancementHolder getLeather = Advancement.Builder.advancement()
                .display(
                        Items.LEATHER,
                        Component.translatable("advancement.ifw.get_leather"),
                        Component.translatable("advancement.ifw.get_leather.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getWoodenMallet)
                .addCriterion(
                        "has_leather",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER))
                .save(saver, "ifw:get_leather");
//皮革甲
        AdvancementHolder wearLeatherArmor = Advancement.Builder.advancement()
                .display(
                        Items.LEATHER_CHESTPLATE,
                        Component.translatable("advancement.ifw.wear_leather_armor"),
                        Component.translatable("advancement.ifw.wear_leather_armor.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getLeather)
                .addCriterion("has_leather_helmet",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER_HELMET))
                .addCriterion("has_leather_chestplate",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER_CHESTPLATE))
                .addCriterion("has_leather_leggings",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER_LEGGINGS))
                .addCriterion("has_leather_boots",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER_BOOTS))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(saver, "ifw:wear_leather_armor");
//猪会飞
        AdvancementHolder whenPigsFly = Advancement.Builder.advancement()
                .display(
                        Items.SADDLE,
                        Component.translatable("advancement.ifw.when_pigs_fly"),
                        Component.translatable("advancement.ifw.when_pigs_fly.desc"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .parent(getLeather)
                .addCriterion(
                        "when_pigs_fly",
                        PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(
                                Optional.of(EntityPredicate.Builder.entity().of(EntityType.PIG).build())))
                .save(saver, "ifw:when_pigs_fly");
//狙击手的对决
        AdvancementHolder sniperDuel = Advancement.Builder.advancement()
                .display(
                        Items.BOW,
                        Component.translatable("advancement.ifw.sniper_duel"),
                        Component.translatable("advancement.ifw.sniper_duel.desc"),
                        null,
                        AdvancementType.CHALLENGE,
                        true, true, false)
                .parent(getWoodenMallet)
                .addCriterion(
                        "sniper_duel",
                        KilledTrigger.TriggerInstance.playerKilledEntity(
                                EntityPredicate.Builder.entity()
                                        .of(EntityType.SKELETON)
                                        .distance(DistancePredicate.horizontal(MinMaxBounds.Doubles.atLeast(50.0)))
                                        .equipment(
                                                EntityEquipmentPredicate.Builder.equipment()
                                                        .mainhand(ItemPredicate.Builder.item().of(Items.BOW)))))
                .save(saver, "ifw:sniper_duel");
//火把
        AdvancementHolder obtainTorch = Advancement.Builder.advancement()
                .display(
                        Items.TORCH,
                        Component.translatable("advancement.ifw.make_torch"),
                        Component.translatable("advancement.ifw.make_torch.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeCraftingTable)
                .addCriterion(
                        "has_torch",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.TORCH))
                .save(saver, "ifw:obtain_torch");
//沙拉
        AdvancementHolder obtainSalad = Advancement.Builder.advancement()
                .display(
                        IFWItems.salad,
                        Component.translatable("advancement.ifw.make_salad"),
                        Component.translatable("advancement.ifw.make_salad.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeCraftingTable)
                .addCriterion(
                        "has_salad",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.salad))
                .save(saver, "ifw:obtain_salad");
//碎石斧
        AdvancementHolder makeStoneAxe = Advancement.Builder.advancement()
                .display(
                        IFWItems.flint_axe,
                        Component.translatable("advancement.ifw.make_stone_axe"),
                        Component.translatable("advancement.ifw.make_stone_axe.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false )
                .parent(makeCraftingTable)
                .addCriterion(
                        "has_flint_axe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.flint_axe))
                .save(saver, "ifw:make_flint_axe");

//床
        Advancement.Builder builder = Advancement.Builder.advancement()
                .display(
                        Items.RED_BED,
                        Component.translatable("advancement.ifw.sleep_on_bed"),
                        Component.translatable("advancement.ifw.sleep_on_bed.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeCraftingTable)
                .addCriterion(
                        "slept_in_bed",
                        PlayerTrigger.TriggerInstance.sleptInBed());
        builder.save(saver, "ifw:sleep_on_bed");
//木铲
        AdvancementHolder makeWoodenShovel = Advancement.Builder.advancement()
                .display(
                        IFWItems.wooden_shovel,
                        Component.translatable("advancement.ifw.make_wooden_shovel"),
                        Component.translatable("advancement.ifw.make_wooden_shovel.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeCraftingTable)
                .addCriterion(
                        "has_wooden_shovel",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.wooden_shovel))
                .save(saver, "ifw:make_wooden_shovel");
//金属粒
        AdvancementHolder getAnyMetalNugget = Advancement.Builder.advancement()
                .display(
                        IFWItems.copper_nugget,
                        Component.translatable("advancement.ifw.get_metal_nugget"),
                        Component.translatable("advancement.ifw.get_metal_nugget.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeWoodenShovel)
                .addCriterion("has_silver_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.silver_nugget))
                .addCriterion("has_copper_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.copper_nugget))
                .addCriterion("has_gold_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_NUGGET))
                .addCriterion("has_iron_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .addCriterion("has_ancient_metal_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.ancient_metal_nugget))
                .addCriterion("has_mithril_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.mithril_nugget))
                .addCriterion("has_adamantium_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.adamantium_nugget))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(saver, "ifw:get_metal_nugget");
//金属工作台
        AdvancementHolder makeBetterCraftingTable = Advancement.Builder.advancement()
                .display(
                        Items.CRAFTING_TABLE,
                        Component.translatable("advancement.ifw.make_better_crafting_table"),
                        Component.translatable("advancement.ifw.make_better_crafting_table.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getAnyMetalNugget)
                .addCriterion(
                        "has_better_crafting_table",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(saver, "ifw:make_better_crafting_table");
//锄头
        AdvancementHolder makeAnyAdvancedHoe = Advancement.Builder.advancement()
                .display(
                        IFWItems.copper_hoe.get(),
                        Component.translatable("advancement.ifw.make_any_advanced_hoe"),
                        Component.translatable("advancement.ifw.make_any_advanced_hoe.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeBetterCraftingTable)
                // 铜锄头
                .addCriterion(
                        "has_copper_hoe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.copper_hoe.get()))
                // 铁锄头
                .addCriterion(
                        "has_iron_hoe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_HOE))
                // 金锄头
                .addCriterion(
                        "has_golden_hoe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLDEN_HOE))
                // 秘银锄头
                .addCriterion(
                        "has_mithril_hoe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.mithril_hoe.get()))
                // 艾德曼锄头
                .addCriterion(
                        "has_adamant_hoe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.adamantium_hoe.get()))
                // 银锄头
                .addCriterion(
                        "has_silver_hoe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.silver_hoe.get()))
                // 设置为OR关系 - 满足任意一个条件即可
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(saver, "ifw:make_any_advanced_hoe");
// 面粉
        AdvancementHolder makeFlour = Advancement.Builder.advancement()
                .display(
                        IFWItems.flour.get(),
                        Component.translatable("advancement.ifw.make_flour"),
                        Component.translatable("advancement.ifw.make_flour.desc"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .parent(makeAnyAdvancedHoe)
                .addCriterion(
                        "has_flour",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.flour.get()))
                .save(saver, "ifw:make_flour");
//面包
        AdvancementHolder getBread = Advancement.Builder.advancement()
                .display(
                        Items.BREAD,
                        Component.translatable("advancement.ifw.get_bread"),
                        Component.translatable("advancement.ifw.get_bread.desc"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .parent(makeFlour)
                .addCriterion(
                        "has_bread",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.BREAD))
                .save(saver, "ifw:get_bread");
//蛋糕
        AdvancementHolder craftCake = Advancement.Builder.advancement()
                .display(
                        Items.CAKE,
                        Component.translatable("advancement.ifw.craft_cake"),
                        Component.translatable("advancement.ifw.craft_cake.desc"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .parent(makeFlour)
                .addCriterion(
                        "has_cake",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.CAKE))
                .save(saver, "ifw:craft_cake");
 //肥料
        AdvancementHolder fertilizeLand = Advancement.Builder.advancement()
                .display(
                        IFWItems.manure.get(),
                        Component.translatable("advancement.ifw.fertilize_land"),
                        Component.translatable("advancement.ifw.fertilize_land.desc"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .parent(makeAnyAdvancedHoe)
                .addCriterion(
                        "has_fertilizer",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.manure.get()))
                .addCriterion(
                        "used_fertilizer_on_farmland",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.location()
                                        .setBlock(BlockPredicate.Builder.block()
                                                .of(Blocks.FARMLAND)),
                                ItemPredicate.Builder.item().of(IFWItems.manure.get())))
                .addCriterion(
                        "has_bone_meal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .addCriterion(
                        "used_bone_meal_on_farmland",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.location()
                                        .setBlock(BlockPredicate.Builder.block()
                                                .of(Blocks.FARMLAND)),
                                ItemPredicate.Builder.item().of(Items.BONE_MEAL)))
                .save(saver, "ifw:fertilize_land");

//菌丝
        AdvancementHolder plantMushroomOnMycelium = Advancement.Builder.advancement()
                .display(
                        Items.MYCELIUM,
                        Component.translatable("advancement.ifw.plant_mushroom_on_mycelium"),
                        Component.translatable("advancement.ifw.plant_mushroom_on_mycelium.desc"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .parent(fertilizeLand)
                .addCriterion(
                        "has_brown_mushroom",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.BROWN_MUSHROOM))
                .addCriterion(
                        "has_mycelium",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.MYCELIUM))
                .addCriterion(
                        "used_mushroom_on_mycelium",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.location()
                                        .setBlock(BlockPredicate.Builder.block()
                                                .of(Blocks.MYCELIUM)),
                                ItemPredicate.Builder.item().of(Items.BROWN_MUSHROOM)))
                .save(saver, "ifw:plant_mushroom_on_mycelium");
//镰刀
        AdvancementHolder makeAnyAdvancedSickle = Advancement.Builder.advancement()
                .display(
                        IFWItems.copper_scythe.get(),
                        Component.translatable("advancement.ifw.make_any_advanced_scythe"),
                        Component.translatable("advancement.ifw.make_any_advanced_scythe.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeBetterCraftingTable)
                // 铜镰刀
                .addCriterion(
                        "has_copper_sickle",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.copper_scythe.get()))
                // 铁镰刀
                .addCriterion(
                        "has_iron_sickle",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.iron_scythe.get()))
                // 金镰刀
                .addCriterion(
                        "has_golden_sickle",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.golden_scythe.get()))
                // 秘银镰刀
                .addCriterion(
                        "has_mithril_sickle",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.mithril_scythe.get()))
                // 艾德曼镰刀
                .addCriterion(
                        "has_adamant_sickle",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.adamantium_scythe.get()))
                // 银镰刀
                .addCriterion(
                        "has_silver_sickle",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.silver_scythe.get()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(saver, "ifw:make_any_advanced_sickle");
//锁链装备
        AdvancementHolder makeChainArmor = Advancement.Builder.advancement()
                .display(
                        IFWItems.copper_chainmail_chestplate,
                        Component.translatable("advancement.ifw.make_chain_armor"),
                        Component.translatable("advancement.ifw.make_chain_armor.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeBetterCraftingTable)
                .addCriterion("has_copper_chainmail_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.copper_chainmail_chestplate))
                .addCriterion("has_silver_chainmail_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.silver_chainmail_chestplate))
                .addCriterion("has_mithril_chainmail_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.mithril_chainmail_chestplate))
                .addCriterion("has_adamantium_chainmail_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.adamantium_chainmail_chestplate))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(saver, "ifw:make_chain_armor");
//铁护甲
        AdvancementHolder wearFullIronArmor = Advancement.Builder.advancement()
                .display(
                        Items.IRON_CHESTPLATE,
                        Component.translatable("advancement.ifw.wear_full_iron_armor"),
                        Component.translatable("advancement.ifw.wear_full_iron_armor.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeChainArmor)
                .addCriterion(
                        "has_full_iron_armor",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                Items.IRON_HELMET,
                                Items.IRON_CHESTPLATE,
                                Items.IRON_LEGGINGS,
                                Items.IRON_BOOTS))
                .save(saver, "ifw:wear_full_iron_armor");
//艾德曼甲
        AdvancementHolder wearFullAdamantArmor = Advancement.Builder.advancement()
                .display(
                        IFWItems.adamantium_chestplate.get(),
                        Component.translatable("advancement.ifw.wear_full_adamant_armor"),
                        Component.translatable("advancement.ifw.wear_full_adamant_armor.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(wearFullIronArmor)
                .addCriterion(
                        "has_full_adamant_armor",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                IFWItems.adamantium_helmet.get(),
                                IFWItems.adamantium_chestplate.get(),
                                IFWItems.adamantium_leggings.get(),
                                IFWItems.adamantium_boots.get()))
                .save(saver, "ifw:wear_full_adamant_armor");
//铜镐
        AdvancementHolder makeCopperPickaxe = Advancement.Builder.advancement()
                .display(
                        IFWItems.copper_pickaxe,
                        Component.translatable("advancement.ifw.make_copper_pickaxe"),
                        Component.translatable("advancement.ifw.make_copper_pickaxe.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeBetterCraftingTable)
                .addCriterion(
                        "has_copper_pickaxe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.copper_pickaxe))
                .save(saver, "ifw:make_copper_pickaxe");
//熔炉
        AdvancementHolder makeFurnace = Advancement.Builder.advancement()
                .display(
                        IFWBlocks. stone_furnace,
                        Component.translatable("advancement.ifw.make_furnace"),
                        Component.translatable("advancement.ifw.make_furnace.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeCopperPickaxe)
                .addCriterion(
                        "has_stone_furnace",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWBlocks. stone_furnace))
                .save(saver, "ifw:make_stone_furnace");
//铁锭
        AdvancementHolder smeltIronIngot = Advancement.Builder.advancement()
                .display(
                        Items.IRON_INGOT,
                        Component.translatable("advancement.ifw.smelt_iron_ingot"),
                        Component.translatable("advancement.ifw.smelt_iron_ingot.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeFurnace)
                .addCriterion(
                        "has_iron_ingot",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(saver, "ifw:smelt_iron_ingot");
//铁镐
        AdvancementHolder makeIronPickaxe = Advancement.Builder.advancement()
                .display(
                        Items.IRON_PICKAXE,
                        Component.translatable("advancement.ifw.make_iron_pickaxe"),
                        Component.translatable("advancement.ifw.make_iron_pickaxe.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(smeltIronIngot)
                .addCriterion(
                        "has_iron_pickaxe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_PICKAXE))
                .save(saver, "ifw:make_iron_pickaxe");

//绿宝石
        AdvancementHolder getEmerald = Advancement.Builder.advancement()
                .display(
                        Items.EMERALD,
                        Component.translatable("advancement.ifw.get_emerald"),
                        Component.translatable("advancement.ifw.get_emerald.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeIronPickaxe)
                .addCriterion(
                        "has_emerald",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.EMERALD))
                .save(saver, "ifw:get_emerald");

//黑曜石熔炉
        AdvancementHolder makeObsidianFurnace = Advancement.Builder.advancement()
                .display(
                        IFWBlocks.obsidian_furnace_item,
                        Component.translatable("advancement.ifw.make_obsidian_furnace"),
                        Component.translatable("advancement.ifw.make_obsidian_furnace.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeIronPickaxe)
                .addCriterion(
                        "has_obsidian_furnace",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWBlocks.obsidian_furnace_item))
                .save(saver, "ifw:make_obsidian_furnace");
//秘银锭
        AdvancementHolder getMithrilIngot = Advancement.Builder.advancement()
                .display(
                        IFWItems.mithril_ingot.get(),
                        Component.translatable("advancement.ifw.get_mithril_ingot"),
                        Component.translatable("advancement.ifw.get_mithril_ingot.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeObsidianFurnace)
                .addCriterion(
                        "has_mithril_ingot",
                        InventoryChangeTrigger.TriggerInstance.hasItems(IFWItems.mithril_ingot.get()))
                .save(saver, "ifw:get_mithril_ingot");
//钻石
        AdvancementHolder getDiamond = Advancement.Builder.advancement()
                .display(
                        Items.DIAMOND,
                        Component.translatable("advancement.ifw.get_diamond"),
                        Component.translatable("advancement.ifw.get_diamond.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getMithrilIngot)
                .addCriterion(
                        "has_diamond",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(saver, "ifw:get_diamond");
//绿宝石附魔台
        AdvancementHolder makeEmeraldEnchantingTable = Advancement.Builder.advancement()
                .display(
                        IFWBlocks.emerald_enchanting_table_item,
                        Component.translatable("advancement.ifw.make_emerald_enchanting_table"),
                        Component.translatable("advancement.ifw.make_emerald_enchanting_table.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getEmerald)
                .addCriterion(
                        "has_emerald_enchanting_table",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                IFWBlocks.emerald_enchanting_table_item))
                .save(saver, "ifw:make_emerald_enchanting_table");
//书架
        AdvancementHolder makeBookshelf = Advancement.Builder.advancement()
                .display(
                        Items.BOOKSHELF,
                        Component.translatable("advancement.ifw.make_bookshelf"),
                        Component.translatable("advancement.ifw.make_bookshelf.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeEmeraldEnchantingTable)
                .addCriterion(
                        "has_bookshelf",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.BOOKSHELF))
                .save(saver, "ifw:make_bookshelf");
//黑曜石
        AdvancementHolder getObsidian = Advancement.Builder.advancement()
                .display(
                        Items.OBSIDIAN,
                        Component.translatable("advancement.ifw.get_obsidian"),
                        Component.translatable("advancement.ifw.get_obsidian.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(makeIronPickaxe)
                .addCriterion(
                        "has_obsidian",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.OBSIDIAN))
                .save(saver, "ifw:get_obsidian");
////符文传送门
//        AdvancementHolder craftRunicPortal = Advancement.Builder.advancement()
//                .display(
//                        IFWBlocks.rune_portal.get().asItem(),
//                        Component.translatable("advancement.ifw.craft_runic_portal"),
//                        Component.translatable("advancement.ifw.craft_runic_portal.desc"),
//                        null,
//                        AdvancementType.CHALLENGE,
//                        true, true, false)
//                .parent(getObsidian)
//                .addCriterion(
//                        "crafted_runic_portal",
//                        RecipeCraftedTrigger.TriggerInstance.craftedItem(
//                                ResourceLocation.parse("ifw:runic_portal")))
//                .save(saver, "ifw:craft_runic_portal");
//地幔
        AdvancementHolder touchMantle = Advancement.Builder.advancement()
                .display(
                        IFWBlocks.mantle_item,
                        Component.translatable("advancement.ifw.touch_mantle"),
                        Component.translatable("advancement.ifw.touch_mantle.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getObsidian)
                .addCriterion(
                        "stand_on_mantle",
                        EnterBlockTrigger.TriggerInstance.entersBlock(IFWBlocks.mantle.get()))
                .save(saver, "ifw:touch_mantle");
//烈焰棒
        AdvancementHolder getBlazeRod = Advancement.Builder.advancement()
                .display(
                        Items.BLAZE_ROD,
                        Component.translatable("advancement.ifw.get_blaze_rod"),
                        Component.translatable("advancement.ifw.get_blaze_rod.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(touchMantle)
                .addCriterion(
                        "has_blaze_rod",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.BLAZE_ROD))
                .save(saver, "ifw:get_blaze_rod");
//末影之眼
        AdvancementHolder obtainEnderEye = Advancement.Builder.advancement()
                .display(
                        Items.ENDER_EYE,
                        Component.translatable("advancement.ifw.obtain_ender_eye"),
                        Component.translatable("advancement.ifw.obtain_ender_eye.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getBlazeRod)
                .addCriterion(
                        "has_ender_eye",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                Items.ENDER_EYE))
                .save(saver, "ifw:obtain_ender_eye");
//恶魂
        AdvancementHolder killGhast = Advancement.Builder.advancement()
                .display(
                        Items.GHAST_TEAR,
                        Component.translatable("advancement.ifw.kill_ghast"),
                        Component.translatable("advancement.ifw.kill_ghast.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(touchMantle)
                .addCriterion(
                        "kill_ghast",
                        KilledTrigger.TriggerInstance.playerKilledEntity(
                                EntityPredicate.Builder.entity().of(EntityType.GHAST)))
                .save(saver, "ifw:kill_ghast");
//药水
        AdvancementHolder brewPotion = Advancement.Builder.advancement()
                .display(
                        Items.POTION,
                        Component.translatable("advancement.ifw.brew_potion"),
                        Component.translatable("advancement.ifw.brew_potion.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getBlazeRod)
                .addCriterion(
                        "brew_potion",
                        BrewedPotionTrigger.TriggerInstance.brewedPotion())
                .save(saver, "ifw:brew_potion");
//地狱岩熔炉
        AdvancementHolder craftNetherrackFurnace = Advancement.Builder.advancement()
                .display(
                        IFWBlocks.netherrack_furnace_item,
                        Component.translatable("advancement.ifw.craft_netherrack_furnace"),
                        Component.translatable("advancement.ifw.craft_netherrack_furnace.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(getBlazeRod)
                .addCriterion(
                        "has_netherrack_furnace",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                IFWBlocks.netherrack_furnace_item))
                .save(saver, "ifw:craft_netherrack_furnace");
//艾德曼锭
        AdvancementHolder smeltAdamantiteIngot = Advancement.Builder.advancement()
                .display(
                        IFWItems.adamantium_ingot,
                        Component.translatable("advancement.ifw.smelt_adamantium_ingot"),
                        Component.translatable("advancement.ifw.smelt_adamantium_ingot.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(craftNetherrackFurnace)
                .addCriterion(
                        "has_adamantite_ingot",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                IFWItems.adamantium_ingot))
                .save(saver, "ifw:smelt_adamantite_ingot");
// 艾德曼镐
        AdvancementHolder obtainAdamantiumPickaxe = Advancement.Builder.advancement()
                .display(
                        IFWItems.adamantium_pickaxe,
                        Component.translatable("advancement.ifw.obtain_adamantium_pickaxe"),
                        Component.translatable("advancement.ifw.obtain_adamantium_pickaxe.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                .parent(smeltAdamantiteIngot)
                .addCriterion(
                        "has_adamantium_pickaxe",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                IFWItems.adamantium_pickaxe))
                .save(saver, "ifw:obtain_adamantium_pickaxe");
    }
}

