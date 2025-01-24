package huix.infinity.common.world.entity.player;

public enum NutritionalStatus implements INutritionalStatus{
    NONE(1, 1.0F),
    LIGHT(1, 1.25F),
    SERIOUS(4, 1.25F);

    final int healSpeed;
    final float exhaustion;

    NutritionalStatus(int healSpeed, float exhaustion) {
        this.healSpeed = healSpeed;
        this.exhaustion = exhaustion;
    }

    @Override
    public int naturalHealSpeedTimes() {
        return this.healSpeed;
    }

    @Override
    public float exhaustionTimes() {
        return this.exhaustion;
    }
}
