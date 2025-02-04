package huix.infinity.init.event;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.effect.UnClearEffect;
import huix.infinity.common.world.entity.player.LevelBonusStats;
import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapsUpdatedEvent;

import java.util.List;

public class IFWEvent {

    public static void init() {
        final IEventBus bus = NeoForge.EVENT_BUS;

        bus.addListener(IFWEvent::onBreakSpeed);
        bus.addListener(IFWEvent::playerAttacklHit);
        bus.addListener(IFWEvent::playerDie);
        bus.addListener(IFWEvent::playerClone);
        bus.addListener(IFWEvent::daySleep);
        bus.addListener(IFWEvent::addFoodInfo);
        bus.addListener(IFWEvent::nonRemoveUnClearEffect);
        bus.addListener(IFWEvent::injectFuel);
        bus.addListener(IFWEvent::injectItem);
    }

    public static void injectItem(final DataMapsUpdatedEvent event) {
        IFWLoad.rebuildStackSize();
        IFWLoad.injectAnvil();
    }

    public static void onBreakSpeed(final PlayerEvent.BreakSpeed event) {
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
            Integer respawn_experience = entity.getData(IFWAttachments.respawn_xp);

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
        final Player original = event.getOriginal();
        cloned.giveExperiencePoints(original.getData(IFWAttachments.respawn_xp));
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
                list.add(Component.translatable("foodtips.nutrition", food.nutrition()).withStyle(ChatFormatting.RED));
            if (food.saturation() != 0)
                list.add(Component.translatable("foodtips.saturation", food.saturation()).withStyle(ChatFormatting.RED));
        }
    }
    private static void showMoreFoodInfo(final IFWFoodProperties extraFood, final List<Component> list) {
        if (extraFood != null && Screen.hasAltDown())  {
            if (extraFood.protein() != 0)
                list.add(Component.translatable("foodtips.protein", extraFood.protein()).withStyle(ChatFormatting.GREEN));
            if (extraFood.phytonutrients() != 0)
                list.add(Component.translatable("foodtips.phytonutrients", extraFood.phytonutrients()).withStyle(ChatFormatting.GREEN));
            if (extraFood.insulinResponse() != 0)
                list.add(Component.translatable("foodtips.insulinresponse", extraFood.insulinResponse()).withStyle(ChatFormatting.YELLOW));
        }
    }

    public static void nonRemoveUnClearEffect(final MobEffectEvent.Remove event) {
        if (event.getEffect().value() instanceof UnClearEffect)
            event.setCanceled(true);
    }

    public static void injectFuel(final FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(Items.TORCH))
            event.setBurnTime(800);
        if (event.getItemStack().is(Items.LAVA_BUCKET))
            event.setBurnTime(3200);
        if (event.getItemStack().is(ItemTags.ARROWS))
            event.setBurnTime(100);
        if (event.getItemStack().is(IFWItems.manure))
            event.setBurnTime(100);
        if (event.getItemStack().is(IFWItems.wooden_shovel))
            event.setBurnTime(200);
        if (event.getItemStack().is(IFWItems.wooden_club))
            event.setBurnTime(200);
    }



}
