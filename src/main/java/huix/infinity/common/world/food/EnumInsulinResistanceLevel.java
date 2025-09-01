package huix.infinity.common.world.food;

public enum EnumInsulinResistanceLevel {
    NONE(0, "none", 0, true),
    MILD(1, "mild", 48000, true),
    MODERATE(2, "moderate", 96000, false),
    SEVERE(3, "severe", 144000, false);

    public final int stage;
    public final String name;
    public final int threshold;
    public final boolean canMetabolizeSugar;

    EnumInsulinResistanceLevel(int stage, String name, int threshold, boolean canMetabolizeSugar) {
        this.stage = stage;
        this.name = name;
        this.threshold = threshold;
        this.canMetabolizeSugar = canMetabolizeSugar;
    }

    public static EnumInsulinResistanceLevel fromValue(int insulinResponse) {
        if (insulinResponse > SEVERE.threshold) return SEVERE;
        if (insulinResponse > MODERATE.threshold) return MODERATE;
        if (insulinResponse > MILD.threshold) return MILD;
        return NONE;
    }
}