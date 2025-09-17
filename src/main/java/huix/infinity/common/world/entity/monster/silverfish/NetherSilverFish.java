package huix.infinity.common.world.entity.monster.silverfish;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class NetherSilverFish extends IFWSilverfish {
    private int ticksUntilNextFizzSound = 0;

    public NetherSilverFish(EntityType<? extends NetherSilverFish> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        Level level = this.level();
        if (level.isClientSide) {

        } else if (this.isInWater() && --ticksUntilNextFizzSound <= 0) {
            this.playSound(SoundEvents.GENERIC_BURN, 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            this.ticksUntilNextFizzSound = this.random.nextInt(7) + 2;

            if (this.random.nextInt(this.isInWater() ? 1 : 4) == 0) {
                this.hurt(level.damageSources().drown(), 1.0F);
            }
        }
        super.tick();
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

}