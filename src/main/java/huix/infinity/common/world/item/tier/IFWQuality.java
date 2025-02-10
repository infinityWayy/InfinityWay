package huix.infinity.common.world.item.tier;

public enum IFWQuality implements Quality{

    poor("poor", 0.75F),
    average("average", 1.0F),
    fine("fine", 1.5F),
    excellent("excellent", 2.0F),
    superb("superb", 2.5F),
    masterwork("masterwork", 3.0F),
    legendary("legendary", 3.5F);

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
}
