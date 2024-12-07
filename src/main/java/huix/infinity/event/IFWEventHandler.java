package huix.infinity.event;

import huix.infinity.common.player.LevelBonusStats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class IFWEventHandler {

    public static void init() {
        final IEventBus bus = NeoForge.EVENT_BUS;

        bus.addListener(IFWEventHandler::onBreakSpeed);
    }

    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getOriginalSpeed() + LevelBonusStats.HARVESTING.calcBonusFor(event.getEntity()));

    }


}
