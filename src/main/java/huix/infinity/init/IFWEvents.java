package huix.infinity.init;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.effect.UnClearEffect;
import huix.infinity.common.world.entity.player.LevelBonusStats;
import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.event.IFWLoading;
import huix.infinity.util.IFWEnchantmentHelper;
import huix.infinity.util.WorldHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapsUpdatedEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IFWEvents {

    public static void init(IEventBus bus) {
        bus.addListener(IFWEvents::armorModify);
        bus.addListener(IFWEvents::playerBreakSpeed);
        bus.addListener(IFWEvents::playerAttacklHit);
        bus.addListener(IFWEvents::playerDie);
        bus.addListener(IFWEvents::playerClone);
        bus.addListener(IFWEvents::daySleep);
        bus.addListener(IFWEvents::addFoodInfo);
        bus.addListener(IFWEvents::nonRemoveUnClearEffect);
        bus.addListener(IFWEvents::injectFuel);
        bus.addListener(IFWEvents::injectItem);
        bus.addListener(IFWEvents::serverTick);
        bus.addListener(IFWEvents::playerLoggedIn);
        bus.addListener(IFWEvents::onLootTableLoad);
    }

    public static void injectItem(final DataMapsUpdatedEvent event) {
        IFWLoading.rebuildStackSize();
        IFWLoading.injectCookingLevel();
        IFWLoading.injectAnvil();
    }


    public static void serverTick(final ServerTickEvent.Post event) {
        for (Map.Entry<ResourceKey<Level>, Map<BlockPos, Integer>> entry : WorldHelper.DELAYED_BLOCKS.entrySet()) {
            ResourceKey<Level> dimension = entry.getKey();
            Map<BlockPos, Integer> blocks = entry.getValue();

            ServerLevel level = event.getServer().getLevel(dimension);
            if (level == null) continue;

            Iterator<Map.Entry<BlockPos, Integer>> iterator = blocks.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<BlockPos, Integer> blockEntry = iterator.next();
                BlockPos pos = blockEntry.getKey();
                int timeLeft = blockEntry.getValue() - 1;

                if (timeLeft <= 0) {
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    iterator.remove();
                } else {
                    blockEntry.setValue(timeLeft);
                }
            }
        }
    }

    public static void armorModify(final ItemAttributeModifierEvent event) {
        if (event.getItemStack().getItem() instanceof ArmorItem armorItem) {
            ArmorItem.Type type = armorItem.getType();
            double armorValue = event.getModifiers().getFirst().modifier().amount() * IFWEnchantmentHelper.getProtectionFactor(event.getItemStack());
            event.replaceModifier(Attributes.ARMOR,
                    new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()),
                            armorValue, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
        }

    }
    public static void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        Player entity = event.getEntity();
    }


    public static void playerBreakSpeed(final PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getOriginalSpeed() + LevelBonusStats.HARVESTING.calcBonusFor(event.getEntity()));
    }

    public static void playerAttacklHit(final CriticalHitEvent event) {
        if (!event.getEntity().getFoodData().ifw_hasAnyEnergy()) {
            event.setDamageMultiplier(0.5F);
        }
        event.setDamageMultiplier(1.0F + LevelBonusStats.MELEE_DAMAGE.calcBonusFor(event.getEntity()));
    }

    public static void playerDie(final LivingDeathEvent event) {
        if (event.getEntity() instanceof Player entity) {
            int respawn_experience = entity.getData(IFWAttachments.respawn_xp);
            if (entity.totalExperience < 20) {
                entity.setData(IFWAttachments.respawn_xp, entity.totalExperience - 20);

                if (respawn_experience < -800) {
                    entity.setData(IFWAttachments.respawn_xp, -800);
                }
            }
        }
    }

    public static void playerClone(final PlayerEvent.Clone event) {
        final Player cloned = event.getEntity();
        cloned.giveExperiencePoints(event.getOriginal().getData(IFWAttachments.respawn_xp));
    }

    public static void daySleep(final CanContinueSleepingEvent event) {
        event.setContinueSleeping(true);
    }

    public static void addFoodInfo(final ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem().ifw_isFood()) {
            showFoodInfo(stack.get(DataComponents.FOOD), event.getToolTip());
            showMoreFoodInfo(stack.get(IFWDataComponents.ifw_food_data), event.getToolTip());
        }
    }
    private static void showFoodInfo(final FoodProperties food, final List<Component> list) {
        if (food != null && Screen.hasShiftDown()) {
            if (food.nutrition() != 0)
                list.add(Component.translatable("foodtips.nutrition", food.nutrition()).withStyle(ChatFormatting.AQUA));
            if (food.saturation() != 0)
                list.add(Component.translatable("foodtips.saturation", food.saturation()).withStyle(ChatFormatting.AQUA));
        }
    }
    private static void showMoreFoodInfo(final IFWFoodProperties extraFood, final List<Component> list) {
        if (extraFood != null && Screen.hasAltDown())  {
            if (extraFood.protein() != 0)
                list.add(Component.translatable("foodtips.protein", extraFood.protein()).withStyle(ChatFormatting.GOLD));
            if (extraFood.phytonutrients() != 0)
                list.add(Component.translatable("foodtips.phytonutrients", extraFood.phytonutrients()).withStyle(ChatFormatting.GREEN));
            if (extraFood.insulinResponse() != 0)
                list.add(Component.translatable("foodtips.insulinresponse", extraFood.insulinResponse()).withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }

    public static void nonRemoveUnClearEffect(final MobEffectEvent.Remove event) {
        if (event.getEffect().value() instanceof UnClearEffect)
            event.setCanceled(true);
    }

    public static void injectFuel(final FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(Items.TORCH))
            event.setBurnTime(800);
        if (event.getItemStack().is(ItemTags.ARROWS))
            event.setBurnTime(100);
        if (event.getItemStack().is(IFWItems.manure))
            event.setBurnTime(100);
        if (event.getItemStack().is(IFWItems.wooden_shovel))
            event.setBurnTime(200);
        if (event.getItemStack().is(IFWItems.wooden_club))
            event.setBurnTime(200);
        if (event.getItemStack().is(Tags.Items.BUCKETS_LAVA))
            event.setBurnTime(3200);
    }

    public static void onLootTableLoad(final LootTableLoadEvent event) {
        if (event.getName().equals(BuiltInLootTables.SPAWN_BONUS_CHEST.location())) {
            event.setTable(LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(3))
                            .add(LootItem.lootTableItem(IFWItems.worm))
                            .add(LootItem.lootTableItem(IFWItems.manure))
                    )
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(3))
                            .add(LootItem.lootTableItem(Items.APPLE))
                            .add(LootItem.lootTableItem(Items.WHEAT_SEEDS))
                            .add(LootItem.lootTableItem(IFWItems.banana))
                    )
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(3))
                            .add(LootItem.lootTableItem(Items.STICK).setWeight(20))
                            .add(LootItem.lootTableItem(IFWItems.flint_shard).setWeight(10))
                    )
                    .build());
        }
    }

}
