package huix.infinity.common.world.curse;

import huix.infinity.extension.func.PlayerExtension;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber
public class CurseManagerTickHandler {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        Collection<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();
        CurseManager.INSTANCE.tick(players);

        Set<UUID> pending = CurseManager.pendingCurseClear;
        Iterator<UUID> iter = pending.iterator();
        while (iter.hasNext()) {
            UUID uuid = iter.next();
            for (ServerPlayer player : players) {
                if (player.getUUID().equals(uuid) && player instanceof PlayerExtension ext) {
                    ext.ifw$setCurse(CurseType.none);
                    CurseManager.INSTANCE.syncCurseRemoved(player);
                    iter.remove();
                    break;
                }
            }
        }
    }
}