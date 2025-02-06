package huix.infinity.common.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class IFWChicken extends Livestock {
    private int max_num_feathers;
    private int num_feathers;
    private static final EntityDimensions BABY_DIMENSIONS = EntityType.CHICKEN.getDimensions().scale(0.5F).withEyeHeight(0.2975F);
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private float nextFlap = 1.0F;
    public boolean isChickenJockey;

    public IFWChicken(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        if (!level.isClientSide) {
            this.max_num_feathers = this.getRandom().nextInt(2) + 1;
            if (this.max_num_feathers > 1 && this.getRandom().nextInt(2) == 0) {
                --this.max_num_feathers;
            }
            this.num_feathers = this.max_num_feathers;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, p_335679_ -> p_335679_.is(ItemTags.CHICKEN_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0).add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = this.flapSpeed + (this.onGround() ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
        }

        this.flap = this.flap + this.flapping * 2.0F;

        if (!this.level().isClientSide) {
            if (this.tickCount % 100 == 0) {
                if (this.random.nextInt(10) > 0 && this.isAlive() && !this.isBaby()
                        && !this.isChickenJockey() && this.updateWellness()) {
                    this.addProductionCounter(1);
                }
                this.produceGoods();
            }
        }
    }

    @Override
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap() {
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState block) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Nullable
    public Chicken getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        return EntityType.CHICKEN.create(level);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.CHICKEN_FOOD);
    }

    @Override
    protected int getBaseExperienceReward() {
        return this.isChickenJockey() ? 10 : super.getBaseExperienceReward();
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.isChickenJockey = compound.getBoolean("IsChickenJockey");
        this.max_num_feathers = compound.getInt("max_num_feathers");
        this.num_feathers = compound.getInt("num_feathers");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        compound.putBoolean("IsChickenJockey", this.isChickenJockey);
        compound.putInt("max_num_feathers", this.max_num_feathers);
        compound.putInt("num_feathers", this.num_feathers);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return this.isChickenJockey();
    }

    @Override
    protected void positionRider(@NotNull Entity passenger, Entity.@NotNull MoveFunction callback) {
        super.positionRider(passenger, callback);
        if (passenger instanceof LivingEntity) {
            ((LivingEntity)passenger).yBodyRot = this.yBodyRot;
        }
    }

    public boolean isChickenJockey() {
        return this.isChickenJockey;
    }

    /**
     * Sets whether this chicken is a jockey or not.
     */
    public void setChickenJockey(boolean isChickenJockey) {
        this.isChickenJockey = isChickenJockey;
    }

    public void produceGoods() {
        int feather_threshold = 100;
        if (this.productionCounter() >= feather_threshold && this.random.nextInt(feather_threshold * 5) == 0) {
            this.gainFeather();
            this.addProductionCounter(-feather_threshold);
            return;
        }
        int egg_threshold = 200;
        if (this.productionCounter() >= egg_threshold && this.random.nextInt(20) == 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.EGG);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.addProductionCounter(-egg_threshold);
        }
    }

    protected void gainFeather() {
        if (this.num_feathers < this.max_num_feathers) {
            ++this.num_feathers;
        } else {
            this.spawnAtLocation(Items.FEATHER);
            this.gameEvent(GameEvent.ENTITY_PLACE);
        }
    }

    @Override
    public void jumpFromGround() {
        super.jumpFromGround();
        if (!this.isBaby() && this.random.nextInt(40) == 0 && !this.isInWater()) {
            this.tryDropFeather(true);
        }
    }

    protected boolean tryDropFeather(boolean retain_at_least_one) {
        if (this.num_feathers < (retain_at_least_one ? 2 : 1) || this.isBaby()) {
            return false;
        }
        this.spawnAtLocation(Items.FEATHER);
        this.gameEvent(GameEvent.ENTITY_PLACE);
        --this.num_feathers;
        return true;
    }

}
