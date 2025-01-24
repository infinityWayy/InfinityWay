package huix.infinity.common.world.item.tier;

public enum EnumQuality {

    poor("poor", 0.75F),
    average("average", 1.0F),
    fine("fine", 1.5F),
    excellent("excellent", 2.0F),
    superb("superb", 2.5F),
    masterwork("masterwork", 3.0F),
    legendary("legendary", 3.5F);

    private final String name;
    private final float durability_modifier;

    EnumQuality(String name, float durability_modifier) {
        this.name = name;
        this.durability_modifier = durability_modifier;
    }
}
