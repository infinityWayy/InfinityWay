package huix.infinity.mixin.world.entity.ai;

import huix.infinity.common.world.entity.animal.Livestock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(PanicGoal.class)
public abstract class PanicGoalMixin {
    @Unique
    private static final double PANIC_SPEED_BONUS = 0.2D;
    @Unique
    private static final int PANIC_DURATION_TICKS = 10;
    @Unique
    private static final float PANIC_SPREAD_RADIUS = 5.0F;
    @Unique
    private static final int WATER_SEARCH_RANGE = 5;
    @Unique
    private static final double MIN_DISTANCE_SQUARED = 2.0D;
    @Unique
    private static final double MAX_DISTANCE_SQUARED = 10.0D * 10.0D;
    @Unique
    private static final int PATHFINDING_RANGE = 10;
    @Unique
    private static final int PATHFINDING_RETREAT_DISTANCE = 7;
    @Unique
    public int infinityWay$damageTick = 0;
    @Unique
    public Vec3 infinityWay$sourcePosition;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/PathfinderMob;D)V", at = @At("TAIL"))
    public void init(PathfinderMob mob, double speedModifier, CallbackInfo ci) {
        // Apply speed bonus when initializing the goal
        this.speedModifier = speedModifier + PANIC_SPEED_BONUS;
    }

    /**
     * Overrides the canUse method to extend panic duration and spread panic to nearby livestock.
     *
     * @author limingzxc
     * @reason Enhances panic behavior with duration, spread, and improved pathfinding
     */
    @Overwrite
    public boolean canUse() {

        // Check if the mob should start panicking
        if (this.shouldPanic() || (this.mob instanceof Livestock livestock && livestock.isPanic())) {
            this.infinityWay$damageTick = PANIC_DURATION_TICKS;
            if (this.mob instanceof Livestock) {
                ((Livestock) this.mob).setPanic(false);
            }
            // Determine the panic source position
            DamageSource lastDamageSource = this.mob.getLastDamageSource();
            this.infinityWay$sourcePosition = lastDamageSource != null ? lastDamageSource.getSourcePosition() : this.mob.position();
        }

        if (this.infinityWay$damageTick > 0) {
            this.infinityWay$damageTick--;

            // Spread panic to nearby livestock
            this.infinityWay$spreadPanicToNearbyLivestock();

            // If on fire, prioritize finding water
            if (this.mob.isOnFire()) {
                BlockPos waterPos = this.lookForWater(this.mob.level(), this.mob, WATER_SEARCH_RANGE);
                if (waterPos != null) {
                    this.infinityWay$setTargetPosition(waterPos);
                    return true;
                }
            }

            // Recalculate path if too far or too close to current target
            Vec3 currentTarget = new Vec3(this.posX, this.posY, this.posZ);
            double distanceSq = this.mob.position().distanceToSqr(currentTarget);
            if (distanceSq > MAX_DISTANCE_SQUARED || distanceSq < MIN_DISTANCE_SQUARED) {
                return this.infinityWay$findNewAwayPosition();
            }
            return true;
        }

        return false;
    }

    /**
     * Spreads panic status to nearby livestock within a defined radius.
     */
    @Unique
    private void infinityWay$spreadPanicToNearbyLivestock() {
        List<Animal> nearbyAnimals = this.mob.level().getEntitiesOfClass(Animal.class,
                this.mob.getBoundingBox().inflate(PANIC_SPREAD_RADIUS),
                entity -> entity instanceof Livestock livestock && !livestock.isPanic());

        for (Animal animal : nearbyAnimals) {
            ((Livestock) animal).setPanic(true);
        }
    }

    /**
     * Finds a new position away from the panic source using improved pathfinding logic.
     *
     * @return true if a valid position was found
     */
    @Unique
    private boolean infinityWay$findNewAwayPosition() {
        Vec3 newPosition = DefaultRandomPos.getPosAway(
                this.mob, PATHFINDING_RANGE, PATHFINDING_RETREAT_DISTANCE, this.infinityWay$sourcePosition
        );
        if (newPosition != null) {
            this.infinityWay$setTargetPosition(newPosition);
            return true;
        }
        return false;
    }

    /**
     * Sets the target position coordinates.
     *
     * @param pos BlockPos containing target coordinates
     */
    @Unique
    private void infinityWay$setTargetPosition(BlockPos pos) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
    }

    /**
     * Sets the target position using a Vec3.
     *
     * @param pos Vec3 containing target coordinates
     */
    @Unique
    private void infinityWay$setTargetPosition(Vec3 pos) {
        this.posX = pos.x;
        this.posY = pos.y;
        this.posZ = pos.z;
    }

    @Shadow @Final protected PathfinderMob mob;
    @Shadow protected double posX;
    @Shadow protected double posY;
    @Shadow protected double posZ;
    @Shadow protected abstract boolean shouldPanic();
    @Shadow @Nullable protected abstract BlockPos lookForWater(BlockGetter level, Entity entity, int range);
    @Mutable
    @Shadow @Final protected double speedModifier;
}
