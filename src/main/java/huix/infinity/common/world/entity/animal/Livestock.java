package huix.infinity.common.world.entity.animal;

import huix.infinity.attachment.IFWAttachment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Set;

public interface Livestock {

    default void ifw_FinalizeSpawn() {
        this.food(0.8F + this.ifw_random().nextFloat() * 0.2F);
        this.water(0.8F + this.ifw_random().nextFloat() * 0.2F);
        this.freedom(0.8F + this.ifw_random().nextFloat() * 0.2F);
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

    default void productionAddCounter(int i) {
        this.animal().setData(IFWAttachment.production_counter,
                this.animal().getData(IFWAttachment.production_counter) + i);
    }

    default boolean isPanic() {return this.animal().getData(IFWAttachment.is_panic);};

    default void setPanic(boolean isPanic) {this.animal().setData(IFWAttachment.is_panic, isPanic);};

    default void addFood(float food) {
        this.food(this.food() + food);
    }

    default void addWater(float water) {
        this.water(this.water() + water);
    }

    default void addFreedom(float freedom) {
        this.freedom(this.freedom() + freedom);
    }

    void setIsWell(boolean isWell);
    void setIsThirsty(boolean isThirsty);
    boolean isThirsty();
    boolean isWell();

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


    default Set<Block> getFoodBlocks() {
        return Set.of(
            Blocks.GRASS_BLOCK,
            Blocks.SHORT_GRASS, Blocks.TALL_GRASS,
            Blocks.FERN, Blocks.LARGE_FERN
        );
    }

    default boolean isNearFood() {
        return isNearFood(this.animal().blockPosition());
    }

    default boolean isNearFood(BlockPos pos) {
        return isNearFood(pos.getX(), pos.getY(), pos.getZ());
    }

    default boolean isNearFood(int x, int y, int z) {
        int height = Mth.floor(this.animal().getBbHeight());
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= height; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    if (getFoodBlocks().contains(this.ifw_level().getBlockState(
                            new BlockPos(x + dx, y + dy, z + dz)).getBlock())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    default boolean hasFullHealth() {
        return this.animal().getHealth() == this.animal().getMaxHealth();
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
