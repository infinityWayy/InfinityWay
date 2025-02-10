package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.LivingEntityAccess;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class MoveToItemGoals extends Goal {

    protected final PathfinderMob mob;
    protected final Predicate<ItemStack> itemStackFilter;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected boolean isRunning;
    private final int maxDistance;
    public MoveToItemGoals(PathfinderMob mob, Predicate<ItemStack> itemStackFilter, int maxDistance) {
        this.mob = mob;
        this.itemStackFilter = itemStackFilter;
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof LivingEntityAccess living &&
                living.ifw_foodOrRepairItemPickupCoolDown() == 0 && !isRunning) {
            return this.findItemPosition();
        } else {
            return false;
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1);
        this.isRunning = true;
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    private boolean findItemPosition() {
        Optional<ItemEntity> itemEntity = this.mob.level().getEntitiesOfClass(ItemEntity.class,
                mob.getBoundingBox().inflate(maxDistance, (double) maxDistance / 4, maxDistance),
                entity -> itemStackFilter.test(entity.getItem()))
                .stream().min(Comparator.comparing(x -> x.distanceTo(mob)));
        if (itemEntity.isPresent()) {
            this.posX = itemEntity.get().getRandomX(0.5);
            this.posY = itemEntity.get().getBlockY();
            this.posZ = itemEntity.get().getRandomZ(0.5);
            return true;
        }
        return false;
    }
}
