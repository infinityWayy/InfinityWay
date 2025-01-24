package huix.infinity.common.world.item;

public class DurationHelper {


    protected static int getMultipliedDurability(int numComponents, int durability) {
        return (int) (getBaseDurability() * numComponents * durability * 100);
    }

    private static float getBaseDurability() {
        return 4.0F;
    }

}
