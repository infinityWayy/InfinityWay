package huix.infinity.mixin.world.entity.animal;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin( Animal.class )
public abstract class LivestockInjected extends AgeableMob {
    @Unique
    private float food;
    @Unique
    private float water;
    @Unique
    private float freedom;
    @Unique
    protected int productionCounter;
    @Unique
    private int manurePeriod;
    @Unique
    private int manureCountdown;
    @Unique
    private static final EntityDataAccessor<Boolean> IS_WELL = SynchedEntityData.defineId(Animal.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private static final EntityDataAccessor<Boolean> IS_THIRSTY = SynchedEntityData.defineId(Animal.class, EntityDataSerializers.BOOLEAN);

    @Inject(at = @At("RETURN"), method = "<init>")
    private void giveValue(EntityType entityType, Level level, CallbackInfo ci) {
        if (isLivestock()) {
            this.food(0.8F + this.random.nextFloat() * 0.2F);
            this.water(0.8F + this.random.nextFloat() * 0.2F);
            this.freedom(0.8F + this.random.nextFloat() * 0.2F);
            this.manurePeriod(24000);
        }
    }

    @Unique
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (isLivestock()) {
            this.food(0.8F + this.random.nextFloat() * 0.2F);
            this.water(0.8F + this.random.nextFloat() * 0.2F);
            this.freedom(0.8F + this.random.nextFloat() * 0.2F);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Unique
    public boolean isLivestock() {
        return true;
    }

    public void illegalAnimal() {
        if (!isLivestock())
            throw new IllegalStateException("Animal is not a livestock");
    }

    @Unique
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        if (isLivestock()) {
            builder.define(IS_WELL, true);
            builder.define(IS_THIRSTY, false);
        }
    }


    @Unique
    public void water(float water) {
        illegalAnimal();
        if (!this.level().isClientSide()) {
            this.water = Mth.clamp(water, 0.0F, 1.0F);
            this.setIsWell(this.isWell());
            this.setIsThirsty(this.isThirsty());
        }
    }
    @Unique
    public void food(float food) {
        illegalAnimal();
        if (!this.level().isClientSide()) {
            this.food = Mth.clamp(food, 0.0F, 1.0F);
            this.setIsWell(this.isWell());
        }
    }
    @Unique
    public void freedom(float freedom) {
        illegalAnimal();
        if (!this.level().isClientSide()) {
            this.freedom = Mth.clamp(freedom, 0.0F, 1.0F);
            this.setIsWell(this.isWell());
        }
    }
    @Unique
    public void manurePeriod(int manure_period) {
        illegalAnimal();
        this.manurePeriod = manure_period;
        this.manureCountdown = (int)(Math.random() * (double)manure_period);
    }
    @Unique
    public float water() {
        illegalAnimal();
        return this.water;
    }
    @Unique
    public float food() {
        illegalAnimal();
        return this.food;
    }
    @Unique
    public float freedom() {
        illegalAnimal();
        return this.freedom;
    }
    @Unique
    protected void addFood(float food) {
        this.food(this.food() + food);
    }
    @Unique
    protected void addWater(float water) {
        this.water(this.water() + water);
    }
    @Unique
    protected void addFreedom(float freedom) {
        this.freedom(this.freedom() + freedom);
    }
    @Unique
    public void setIsWell(boolean isWell) {
        illegalAnimal();
        this.getEntityData().set(IS_WELL, isWell);
    }
    @Unique
    public void setIsThirsty(boolean isThirsty) {
        illegalAnimal();
        this.getEntityData().set(IS_THIRSTY, isThirsty);
    }
    @Unique
    public boolean isThirsty() {
        illegalAnimal();
        if (this.level().isClientSide()) {
            return this.getEntityData().get(IS_THIRSTY);
        } else {
            return this.water() < 0.5F;
        }
    }
    @Unique
    public boolean isHungry() {
        return this.food() < 0.5F;
    }
    @Unique
    public boolean isVeryHungry() {
        return this.food() < 0.25F;
    }
    @Unique
    public boolean isDesperateForFood() {
        return this.food() < 0.05F;
    }
    @Unique
    public boolean isVeryThirsty() {
        return this.water() < 0.25F;
    }
    @Unique
    public boolean isDesperateForWater() {
        return this.water() < 0.05F;
    }
    @Unique
    public boolean isWell() {
        illegalAnimal();
        if (this.level().isClientSide()) {
            return this.getEntityData().get(IS_WELL);
        } else {
            return Math.min(this.freedom(), Math.min(this.food(), this.water())) >= 0.25F;
        }
    }

    protected LivestockInjected(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
    }
}
