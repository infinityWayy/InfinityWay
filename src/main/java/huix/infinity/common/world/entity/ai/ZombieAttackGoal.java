package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.monster.IFWZombie;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ZombieAttackGoal extends MeleeAttackGoal {
    private final IFWZombie zombie;
    private int raiseArmTicks;

    public ZombieAttackGoal(IFWZombie zombie, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(zombie, speedModifier, followingTargetEvenIfNotSeen);
        this.zombie = zombie;
    }

    @Override
    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.zombie.setAggressive(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.raiseArmTicks++;
        if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
            this.zombie.setAggressive(true);
        } else {
            this.zombie.setAggressive(false);
        }
    }
}
