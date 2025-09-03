package huix.infinity.init;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.curse.CurseEffectHelper;
import huix.infinity.common.world.curse.CurseType;
import huix.infinity.common.world.effect.UnClearEffect;
import huix.infinity.common.world.entity.player.LevelBonusStats;
import huix.infinity.common.world.entity.player.NutritionalStatus;
import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.compat.farmersdelight.FDEventHandler;
import huix.infinity.compat.farmersdelight.FDFoodAdapter;
import huix.infinity.extension.func.FoodDataExtension;
import huix.infinity.init.event.IFWLoading;
import huix.infinity.util.IFWEnchantmentHelper;
import huix.infinity.util.WorldHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.CalculatePlayerTurnEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import net.neoforged.neoforge.event.level.block.CropGrowEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.*;

public class IFWEvents {

    public static void init(IEventBus bus) {
        // Register this class to receive forge bus events
        bus.register(IFWEvents.class);
        if (InfinityWay.FarmersDelightLoaded) FDEventHandler.register();
    }

    @SubscribeEvent
    public static void injectItem(final TagsUpdatedEvent event) {
        IFWLoading.rebuildStackSize();
        IFWLoading.injectCookingLevel();
        IFWLoading.injectAnvil();
    }

    @SubscribeEvent
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
        //Armor Curse Effect: Remove armor if cursed
        for (ServerLevel level : event.getServer().getAllLevels()) {
            for (ServerPlayer player : level.players()) {
                CurseEffectHelper.handleArmorCurse(player);
            }
        }
    }

    @SubscribeEvent
    public static void armorModify(final ItemAttributeModifierEvent event) {
        if (event.getItemStack().getItem() instanceof ArmorItem armorItem) {
            ArmorItem.Type type = armorItem.getType();
            double armorValue = event.getModifiers().getFirst().modifier().amount() * IFWEnchantmentHelper.getProtectionFactor(event.getItemStack());
            event.replaceModifier(Attributes.ARMOR,
                    new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()),
                            armorValue, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
        }

    }

    @SubscribeEvent
    public static void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        Player entity = event.getEntity();
        int curseOrdinal = entity.getData(IFWAttachments.player_curse);
        entity.ifw$setCurse(CurseType.values()[curseOrdinal]);
    }

    @SubscribeEvent
    public static void playerBreakSpeed(final PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getOriginalSpeed() + LevelBonusStats.HARVESTING.calcBonusFor(event.getEntity()));
    }

    @SubscribeEvent
    public static void playerAttackHit(final CriticalHitEvent event) {
        if (!event.getEntity().getFoodData().ifw_hasAnyEnergy()) {
            event.setDamageMultiplier(0.5F);
        }
        event.setDamageMultiplier(1.0F + LevelBonusStats.MELEE_DAMAGE.calcBonusFor(event.getEntity()));
    }

    @SubscribeEvent
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

    @SubscribeEvent
    public static void playerClone(final PlayerEvent.Clone event) {
        final Player cloned = event.getEntity();
        final Player original = event.getOriginal();
        cloned.giveExperiencePoints(original.getData(IFWAttachments.respawn_xp));
    }

    @SubscribeEvent
    public static void playerClone(final PlayerEvent.PlayerRespawnEvent event) {
        final Player player = event.getEntity();
        int curseOrdinal = player.getData(IFWAttachments.player_curse);
        player.ifw$setCurse(CurseType.values()[curseOrdinal]);
    }

    @SubscribeEvent
    public static void daySleep(final CanContinueSleepingEvent event) {
        event.setContinueSleeping(true);
    }

    @SubscribeEvent
    public static void addFoodInfo(final ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        boolean isFood = item.ifw_isFood();
        FoodProperties food = stack.get(DataComponents.FOOD);
        IFWFoodProperties ifwFoodData = stack.get(IFWDataComponents.ifw_food_data);
        IFWFoodProperties compatNutrition = null;

        if (isFood || food != null) {
            compatNutrition = getCompatibilityNutrition(item);
        } else if (InfinityWay.FarmersDelightLoaded) {
            compatNutrition = getCompatibilityNutrition(item);
        }
        if (!isFood && food == null && ifwFoodData == null && compatNutrition == null) {
            return;
        }
        if (food != null) {
            showFoodInfo(food, event.getToolTip());
        }
        if (ifwFoodData != null) {
            showMoreFoodInfo(ifwFoodData, event.getToolTip());
            return;
        }
        if (compatNutrition != null) {
            showMoreFoodInfo(compatNutrition, event.getToolTip());
        }
    }

    private static IFWFoodProperties getCompatibilityNutrition(Item item) {

        if (InfinityWay.FarmersDelightLoaded) {
            try {
                IFWFoodProperties nutrition = FDFoodAdapter.getNutritionProperties(item);
                if (nutrition != null) {
                    return nutrition;
                }
            } catch (Exception e) {
                InfinityWay.LOGGER.warn("Error getting Farmers Delight nutrition for item: {}", item, e);
            }
        }
        // 未来可以在这里添加其他MOD的支持
        return null;
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
        if (extraFood != null && Screen.hasAltDown()) {
            if (extraFood.protein() != 0)
                list.add(Component.translatable("foodtips.protein", extraFood.protein()).withStyle(ChatFormatting.YELLOW));
            if (extraFood.phytonutrients() != 0)
                list.add(Component.translatable("foodtips.phytonutrients", extraFood.phytonutrients()).withStyle(ChatFormatting.GREEN));
            if (extraFood.insulinResponse() != 0)
                list.add(Component.translatable("foodtips.insulinresponse", extraFood.insulinResponse()).withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }

    @SubscribeEvent
    public static void nonRemoveUnClearEffect(final MobEffectEvent.Remove event) {
        if (event.getEffect().value() instanceof UnClearEffect)
            event.setCanceled(true);
    }

    @SubscribeEvent
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

    @SubscribeEvent
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

    @SubscribeEvent
    public static void onCropGrow(final CropGrowEvent.Pre event) {
        LevelAccessor world = event.getLevel();

        // 1/8概率触发生长
        if (world.getRandom().nextInt(8) != 0) {
            event.setResult(CropGrowEvent.Pre.Result.DO_NOT_GROW);
        }
    }

    @SubscribeEvent
    public static void onCalculatePlayerTurn(CalculatePlayerTurnEvent event) {

        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null && player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {

            int amplifier = Objects.requireNonNull(player.getEffect(MobEffects.MOVEMENT_SLOWDOWN)).getAmplifier();

            double sensitivity = event.getMouseSensitivity();

            double reduction = 1.0 - ((amplifier + 1) * 0.2);

            event.setMouseSensitivity(Math.max(0, sensitivity * reduction));
        }
    }

    private static final Map<UUID, Long> playerSleepStartTime = new HashMap<>();

    @SubscribeEvent
    public static void onCanPlayerSleep(final CanPlayerSleepEvent event) {
        ServerPlayer player = event.getEntity();

        if (event.getProblem() == null) {
            if (CurseEffectHelper.shouldBlockSleep(player)) {
                player.displayClientMessage(Component.translatable("curse.ifw.cannot_sleep.desc"), true);
                for (Player other : player.level().players()) {
                    if (other != player && other.isSleeping()) {
                        other.displayClientMessage(Component.translatable("curse.ifw.someone_cannot_sleep.desc", player.getDisplayName()), true);
                    }
                }
            }
            long currentTime = player.level().getDayTime();
            playerSleepStartTime.put(player.getUUID(), currentTime);
        }
    }

    @SubscribeEvent
    public static void onSleepFinished(final SleepFinishedTimeEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        long newTime = event.getNewTime();

        // 为所有参与睡眠的玩家处理恢复
        for (ServerPlayer player : serverLevel.players()) {
            UUID playerUUID = player.getUUID();
            Long sleepStartTime = playerSleepStartTime.get(playerUUID);

            if (sleepStartTime != null) {
                // 计算实际跳过的游戏时间
                long actualSkippedTime = newTime - sleepStartTime;

                // 确保跳过时间是合理的（至少1000 ticks）
                if (actualSkippedTime > 1000) {
                    calculateSleepHealing(player, actualSkippedTime);
                }

                playerSleepStartTime.remove(playerUUID);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(final PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        // 清理可能残留的睡眠记录（处理睡眠被打断等情况）
        playerSleepStartTime.remove(player.getUUID());
    }

    private static void calculateSleepHealing(Player player, long skippedTime) {
        if (!player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
            return;
        }

        if (player.getHealth() >= player.getMaxHealth()) {
            return;
        }

        FoodDataExtension foodData = player.getFoodData();
        NutritionalStatus nutritionalStatus = foodData.ifw_nutritionalStatus();

        int baseHealInterval = 1280;
        int adjustedInterval = baseHealInterval * nutritionalStatus.naturalHealSpeedTimes();
        int sleepHealInterval = adjustedInterval / 8;
        int healCount = (int) (skippedTime / sleepHealInterval);

        if (healCount > 0) {
            float maxHealth = player.getMaxHealth();
            float currentHealth = player.getHealth();
            float maxPossibleHeal = maxHealth - currentHealth;
            float actualHeal = Math.min(healCount, maxPossibleHeal);

            if (actualHeal > 0) {
                player.heal(actualHeal);
                float exhaustionCost = actualHeal * 6.0F;
                player.getFoodData().addExhaustion(exhaustionCost);
            }
        }
    }

    // Curse Effects
    @SubscribeEvent
    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack stack = event.getItem();
        if (CurseEffectHelper.isIngestionForbiddenByCurse(player, stack)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Block block = event.getLevel().getBlockState(event.getPos()).getBlock();
        if (CurseEffectHelper.handleChestCurse(player, block)) {
            event.setCanceled(true);
        }
    }
}