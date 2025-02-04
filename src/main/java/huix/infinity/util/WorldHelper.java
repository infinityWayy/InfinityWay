package huix.infinity.util;

public class WorldHelper {

    public static long worldHour(long time) {
        return (time / 1000 + 6) % 24;
    }

    public static int worldMins(int time) {
        return (int) Math.round((time % 1000) * 0.06);
    }

}
