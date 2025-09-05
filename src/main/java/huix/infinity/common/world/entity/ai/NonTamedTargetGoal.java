package huix.infinity.common.world.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class NonTamedTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final TamableAnimal tameable;

    public NonTamedTargetGoal(TamableAnimal tameable, Class<T> targetClass, boolean mustSee) {
        super(tameable, targetClass, mustSee);
        this.tameable = tameable;
    }

    @Override
    public boolean canUse() {
        return !tameable.isTame() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !tameable.isTame() && super.canContinueToUse();
    }
}