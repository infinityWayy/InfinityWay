package huix.infinity.common.world.item;

public class DurabilityHelper {


    protected static int getMultipliedDurability(int numComponents, int durability) {
        return (int) (getBaseDurability() * numComponents * durability * 100);
    }

    private static float getBaseDurability() {
        return 4.0F;
    }



    public enum Armor {
        HELMET(10),
        CHESTPLATE(16),
        LEGGINGS( 14),
        BOOTS(8),
        BODY( 16);
        
        private final int durability;

        Armor(int durability) {
            this.durability = durability;
        }

        public int getDurability(float durabilityFactor) {
            return (int) (this.durability * durabilityFactor);
        }
    }

}
