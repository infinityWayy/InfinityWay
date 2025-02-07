package huix.infinity.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class WorldHelper {

    public static final Map<ResourceKey<Level>, Map<BlockPos, Integer>> DELAYED_BLOCKS = new HashMap<>();

    public static long worldHour(long time) {
        return (time / 1000 + 6) % 24;
    }

    public static int worldMins(int time) {
        return (int) Math.round((time % 1000) * 0.06);
    }

    public static long getDay(Level level) {
        return level.getGameTime() / 24000L;
    }

    public static ClientLevel setDay(ClientLevel level, long time) {
        level.setGameTime(time);
        return level;
    }
}
