package huix.infinity.common.world.curse;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Collection;

@EventBusSubscriber
public class CurseManagerTickHandler {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        Collection<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();
        CurseManager.INSTANCE.tick(players);
        CurseManager.INSTANCE.processPendingCurseClear(players);
    }
}