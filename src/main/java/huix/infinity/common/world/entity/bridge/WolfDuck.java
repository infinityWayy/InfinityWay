package huix.infinity.common.world.entity.bridge;

public interface WolfDuck {
    boolean ifw$isHostileToPlayers();
    void ifw$setHostileToPlayers(int ticks);
    void ifw$setWitchAlly(boolean flag);
    boolean ifw$isWitchAlly();
    void ifw$setIsAttacking(boolean attack);
    boolean ifw$isAttacking();
}