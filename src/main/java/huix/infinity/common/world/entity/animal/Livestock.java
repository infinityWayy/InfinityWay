//package huix.infinity.common.world.entity.animal;
//
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.util.Mth;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.DifficultyInstance;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.SpawnGroupData;
//import net.minecraft.world.entity.animal.Animal;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.ServerLevelAccessor;
//import org.spongepowered.asm.mixin.Unique;
//
//import javax.annotation.Nullable;
//
//public interface Livestock {
//    float food = 0;
//    float water = 0;
//    float freedom = 0;
//    int productionCounter = 0;
//    int manurePeriod = 0;
//    int manureCountdown = 0;
//    EntityDataAccessor<Boolean> IS_WELL = SynchedEntityData.defineId(Animal.class, EntityDataSerializers.BOOLEAN);
//    EntityDataAccessor<Boolean> IS_THIRSTY = SynchedEntityData.defineId(Animal.class, EntityDataSerializers.BOOLEAN);
//
//
//    default void finalizeSpawn() {
//        this.food(0.8F + this.random().nextFloat() * 0.2F);
//        this.water(0.8F + this.random().nextFloat() * 0.2F);
//        this.freedom(0.8F + this.random().nextFloat() * 0.2F);
//    }
//
//    default void defineSynchedData(SynchedEntityData.Builder builder) {
//        builder.define(IS_WELL, true);
//        builder.define(IS_THIRSTY, false);
//    }
//
//
//    default void water(float water) {
//        if (!this.level().isClientSide()) {
//            this.water = Mth.clamp(water, 0.0F, 1.0F);
//            this.setIsWell(this.isWell());
//            this.setIsThirsty(this.isThirsty());
//        }
//    }
//
//    default void food(float food) {
//        if (!this.level().isClientSide()) {
//            this.food = Mth.clamp(food, 0.0F, 1.0F);
//            this.setIsWell(this.isWell());
//        }
//    }
//
//    default void freedom(float freedom) {
//        if (!this.level().isClientSide()) {
//            this.freedom = Mth.clamp(freedom, 0.0F, 1.0F);
//            this.setIsWell(this.isWell());
//        }
//    }
//
//    default void manurePeriod(int manure_period) {
//        this.manurePeriod = manure_period;
//        this.manureCountdown = (int)(Math.random() * (double)manure_period);
//    }
//
//    default float water() {
//        return this.water;
//    }
//
//    default float food() {
//        return this.food;
//    }
//
//    default float freedom() {
//        return this.freedom;
//    }
//
//    default void addFood(float food) {
//        this.food(this.food() + food);
//    }
//
//    default void addWater(float water) {
//        this.water(this.water() + water);
//    }
//
//    default void addFreedom(float freedom) {
//        this.freedom(this.freedom() + freedom);
//    }
//
//    default void setIsWell(boolean isWell) {
//        this.getEntityData().set(IS_WELL, isWell);
//    }
//
//    default void setIsThirsty(boolean isThirsty) {
//        this.getEntityData().set(IS_THIRSTY, isThirsty);
//    }
//
//    default boolean isThirsty() {
//        if (this.level().isClientSide()) {
//            return this.getEntityData().get(IS_THIRSTY);
//        } else {
//            return this.water() < 0.5F;
//        }
//    }
//
//    default boolean isHungry() {
//        return this.food() < 0.5F;
//    }
//
//    default boolean isVeryHungry() {
//        return this.food() < 0.25F;
//    }
//
//    default boolean isDesperateForFood() {
//        return this.food() < 0.05F;
//    }
//
//    default boolean isVeryThirsty() {
//        return this.water() < 0.25F;
//    }
//
//    default boolean isDesperateForWater() {
//        return this.water() < 0.05F;
//    }
//
//    default boolean isWell() {
//        if (this.level().isClientSide()) {
//            return this.getEntityData().get(IS_WELL);
//        } else {
//            return Math.min(this.freedom(), Math.min(this.food(), this.water())) >= 0.25F;
//        }
//    }
//
//    Animal animal();
//
//    default Level level() {
//        return this.animal().level();
//    }
//
//    default SynchedEntityData getEntityData() {
//        return this.animal().getEntityData();
//    }
//
//    default RandomSource random() {
//        return this.animal().getRandom();
//    }
//}
