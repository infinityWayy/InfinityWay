package huix.infinity.init;

import huix.infinity.common.core.attachment.IFWAttachment;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.entity.player.LevelBonusStats;
import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.common.world.food.IFWFoods;
import huix.infinity.util.ReplaceHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

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
            Integer respawn_experience = entity.getData(IFWAttachment.respawn_xp);

            if (entity.totalExperience < 20) {
                entity.setData(IFWAttachment.respawn_xp, entity.totalExperience - 20);

                if (respawn_experience < -800) {
                    entity.setData(IFWAttachment.respawn_xp, -800);
                }
            }
        }
    }

    public static void playerClone(final PlayerEvent.Clone event) {
        final Player cloned = event.getEntity();
        final Player original = event.getOriginal();
        cloned.giveExperiencePoints(original.getData(IFWAttachment.respawn_xp));
    }

    public static void daySleep(final CanContinueSleepingEvent event) {
        event.setContinueSleeping(true);
    }

    public static void addFoodInfo(final ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> list = event.getToolTip();
        if (stack.getItem().ifw_isFood()) {
            FoodProperties foodProperties = stack.get(DataComponents.FOOD);
            if (showFoodInfo(foodProperties)) {
                list.add(Component.translatable("foodtips.nutrition", foodProperties.nutrition()).withStyle(ChatFormatting.RED));
                list.add(Component.translatable("foodtips.saturation", foodProperties.saturation()).withStyle(ChatFormatting.RED));
                IFWFoodProperties properties = stack.get(IFWDataComponents.ifw_food_data);
                if (showMoreFoodInfo(properties)) {
                    list.add(Component.translatable("foodtips.protein", properties.protein()).withStyle(ChatFormatting.GREEN));
                    list.add(Component.translatable("foodtips.phytonutrients", properties.phytonutrients()).withStyle(ChatFormatting.GREEN));
                    if (properties.insulinResponse() != 0)
                        list.add(Component.translatable("foodtips.insulinresponse", properties.insulinResponse()).withStyle(ChatFormatting.YELLOW));
                }
            }
        }
    }

    private static boolean showFoodInfo(final FoodProperties foodProperties) {
        return Screen.hasShiftDown() && foodProperties.nutrition() >= 0 || foodProperties.saturation() >= 0.0F;
    }
    private static boolean showMoreFoodInfo(final IFWFoodProperties foodProperties) {
        return Screen.hasAltDown() && foodProperties != null && (foodProperties.protein() != 0 || foodProperties.phytonutrients() != 0.0F);
    }
}
