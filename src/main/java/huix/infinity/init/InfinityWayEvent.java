package huix.infinity.init;

import huix.infinity.common.attachment.IFWAttachment;
import huix.infinity.common.player.LevelBonusStats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class InfinityWayEvent {

    public static void init() {
        final IEventBus bus = NeoForge.EVENT_BUS;

        bus.addListener(InfinityWayEvent::onBreakSpeed);
        bus.addListener(InfinityWayEvent::playerAttacklHit);
        bus.addListener(InfinityWayEvent::playerDie);
        bus.addListener(InfinityWayEvent::playerClone);
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


}
