package huix.infinity.util;

import huix.infinity.common.world.block.IFWAnvilBlock;
import huix.infinity.common.world.item.tier.IFWTier;

public class DurabilityHelper {


    public static int getMultipliedDurability(int numComponents, int durability) {
        return (int) (getBaseDurability() * numComponents * durability * 100);
    }

    private static float getBaseDurability() {
        return 4.0F;
    }

    public static int getDurability(IFWTier tier) {
        return (int)((float)(getBaseDurabilityPerIngot() * 31) * tier.durability());
    }

    private static int getBaseDurabilityPerIngot() {
        return 1600;
    }

    public static int getStageDurability(int stage, IFWAnvilBlock anvilBlock) {
        if (stage == 0)
            return anvilBlock.maxDurability();
        if (stage == 1)
            return (int) (anvilBlock.maxDurability() * 0.8F);
        if (stage == 2)
            return (int) (anvilBlock.maxDurability() * 0.5F);
        throw new IllegalArgumentException("Invalid Anvil stage: " + stage + " > 2");
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
