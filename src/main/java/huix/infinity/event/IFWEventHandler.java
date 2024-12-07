package huix.infinity.event;

import huix.infinity.common.player.LevelBonusStats;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class IFWEventHandler {

    public static void init() {
        final IEventBus bus = NeoForge.EVENT_BUS;

        bus.addListener(IFWEventHandler::onBreakSpeed);
        bus.addListener(IFWEventHandler::playerAttacklHit);
        bus.addListener(IFWEventHandler::playerXPDropHit);
    }

    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getOriginalSpeed() + LevelBonusStats.HARVESTING.calcBonusFor(event.getEntity()));
    }

    public static void playerAttacklHit(CriticalHitEvent event) {
        if (!event.getEntity().getFoodData().ifw_hasAnyEnergy()) {
            event.setDamageMultiplier(0.5F);
        }
        event.setDamageMultiplier(1.0F + LevelBonusStats.MELEE_DAMAGE.calcBonusFor(event.getEntity()));
    }

    public static void playerXPDropHit(LivingExperienceDropEvent event) {
        if (event.getEntity() instanceof Player)
            event.setDroppedExperience(event.getOriginalExperience() / 3);
    }




}
