package huix.infinity.common.world.entity.animal;

import huix.infinity.common.attachment.IFWAttachment;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public interface Livestock {
    EntityDataAccessor<Boolean> IS_WELL = SynchedEntityData.defineId(Animal.class, EntityDataSerializers.BOOLEAN);
    EntityDataAccessor<Boolean> IS_THIRSTY = SynchedEntityData.defineId(Animal.class, EntityDataSerializers.BOOLEAN);


    default void finalizeSpawn() {
        this.food(0.8F + this.ifw_random().nextFloat() * 0.2F);
        this.water(0.8F + this.ifw_random().nextFloat() * 0.2F);
        this.freedom(0.8F + this.ifw_random().nextFloat() * 0.2F);
    }

    default void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(IS_WELL, true);
        builder.define(IS_THIRSTY, false);
    }


    default void water(float water) {
        if (!this.ifw_level().isClientSide()) {
            this.animal().setData(IFWAttachment.water, Mth.clamp(water, 0.0F, 1.0F));
            this.setIsWell(this.isWell());
            this.setIsThirsty(this.isThirsty());
        }
    }

    default void food(float food) {
        if (!this.ifw_level().isClientSide()) {
            this.animal().setData(IFWAttachment.food, Mth.clamp(food, 0.0F, 1.0F));
            this.setIsWell(this.isWell());
        }
    }

    default void freedom(float freedom) {
        if (!this.ifw_level().isClientSide()) {
            this.animal().setData(IFWAttachment.freedom, Mth.clamp(freedom, 0.0F, 1.0F));
            this.setIsWell(this.isWell());
        }
    }

    default void manurePeriod(int manure_period) {
        this.animal().setData(IFWAttachment.manure_period, manure_period);
        this.animal().setData(IFWAttachment.manure_countdown, (int)(Math.random() * (double)manure_period));
    }

    default float water() {
        return this.animal().getData(IFWAttachment.water);
    }

    default float food() {
        return this.animal().getData(IFWAttachment.food);
    }

    default float freedom() {
        return this.animal().getData(IFWAttachment.freedom);
    }

    default int productionCounter() {
        return this.animal().getData(IFWAttachment.production_counter);
    }

    default void addFood(float food) {
        this.food(this.food() + food);
    }

    default void addWater(float water) {
        this.water(this.water() + water);
    }

    default void addFreedom(float freedom) {
        this.freedom(this.freedom() + freedom);
    }

    default void setIsWell(boolean isWell) {
        this.ifw_getEntityData().set(IS_WELL, isWell);
    }

    default void setIsThirsty(boolean isThirsty) {
        this.ifw_getEntityData().set(IS_THIRSTY, isThirsty);
    }

    default boolean isThirsty() {
        if (this.ifw_level().isClientSide()) {
            return this.ifw_getEntityData().get(IS_THIRSTY);
        } else {
            return this.water() < 0.5F;
        }
    }

    default boolean isHungry() {
        return this.food() < 0.5F;
    }

    default boolean isVeryHungry() {
        return this.food() < 0.25F;
    }

    default boolean isDesperateForFood() {
        return this.food() < 0.05F;
    }

    default boolean isVeryThirsty() {
        return this.water() < 0.25F;
    }

    default boolean isDesperateForWater() {
        return this.water() < 0.05F;
    }

    default boolean isWell() {
        if (this.ifw_level().isClientSide()) {
            return this.ifw_getEntityData().get(IS_WELL);
        } else {
            return Math.min(this.freedom(), Math.min(this.food(), this.water())) >= 0.25F;
        }
    }



    Animal animal();

    default Level ifw_level() {
        return this.animal().level();
    }

    default SynchedEntityData ifw_getEntityData() {
        return this.animal().getEntityData();
    }

    default RandomSource ifw_random() {
        return this.animal().getRandom();
    }
}
