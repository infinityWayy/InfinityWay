package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.bridge.WolfDuck;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

/**
 * 兼容原版狼与恐狼（IFWDireWolf）的玩家目标追击AI。
 * 只要实体实现了WolfDuck接口（通过Mixin桥接），即可自动兼容。
 */
public class HostileToPlayersTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final WolfDuck duck;

    public HostileToPlayersTargetGoal(LivingEntity wolf, Class<T> targetClass, boolean mustSee) {
        super((Mob) wolf, targetClass, mustSee);
        this.duck = (WolfDuck)wolf;
    }

    @Override
    public boolean canUse() {
        return (duck.ifw$isHostileToPlayers() || duck.ifw$isWitchAlly()) && super.canUse();
    }

    @Override
    public void start() {
        duck.ifw$setIsAttacking(true);
        super.start();
    }
}