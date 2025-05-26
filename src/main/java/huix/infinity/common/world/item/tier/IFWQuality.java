package huix.infinity.common.world.item.tier;

public enum IFWQuality implements Quality {

    POOR("poor", 0.75F),
    INFERIOR("inferior", 0.9F),
    AVERAGE("average", 1.0F),
    GOOD("good", 1.25F),
    SUPERIOR("superior", 1.5F),
    LEGENDARY("legendary", 2.0F);

    private final String name;
    private final float durability_time;

    IFWQuality(String name, float durability_time) {
        this.name = name;
        this.durability_time = durability_time;
    }

    @Override
    public float durabilityTime() {
        return this.durability_time;
    }

    /**
     * 获取品质名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取本地化键
     */
    public String getTranslationKey() {
        return "quality.infinityway." + name;
    }

    /**
     * 根据名称获取品质
     */
    public static IFWQuality fromName(String name) {
        for (IFWQuality quality : values()) {
            if (quality.name.equalsIgnoreCase(name)) {
                return quality;
            }
        }
        return AVERAGE; // 默认返回普通品质
    }
}