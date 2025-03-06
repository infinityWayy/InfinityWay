package huix.infinity.common.world.entity.animal;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.world.entity.LivingEntityAccess;
import huix.infinity.common.world.entity.ai.MoveToItemGoals;
import huix.infinity.common.world.entity.ai.SeekFoodIfHungryGoal;
import huix.infinity.common.world.entity.ai.SeekOpenSpaceIfCrowdedGoal;
import huix.infinity.common.world.entity.ai.SeekWaterIfThirstyGoal;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.util.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class Livestock extends Animal {
    private final static EntityDataAccessor<Boolean> IS_WELL = SynchedEntityData.defineId(Livestock.class, EntityDataSerializers.BOOLEAN);
    private final static EntityDataAccessor<Boolean> IS_THIRSTY = SynchedEntityData.defineId(Livestock.class, EntityDataSerializers.BOOLEAN);


    protected Livestock(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        finalizeSpawn();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MoveToItemGoals(this, this::isFood, 16));
        this.goalSelector.addGoal(2, new SeekFoodIfHungryGoal(this));
        this.goalSelector.addGoal(2, new SeekWaterIfThirstyGoal(this));
        this.goalSelector.addGoal(3, new SeekOpenSpaceIfCrowdedGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide) {
            if (this.tickCount % 100 == 0) {
                if (this.random.nextInt(10) > 0 && this.isAlive() && !this.isBaby()
                        && !this.isSpecialCase() && this.updateWellness()) {
                    this.addProductionCounter(1);
                }
                this.produceGoods();
            }

            if (!this.isBaby() && !this.isDesperateForFood()) {
                addManureCountdown(-1);
                if (this.getManureCountdown() <= 0) {
                    this.spawnAtLocation(IFWItems.manure);
                    this.gameEvent(GameEvent.ENTITY_PLACE);
                    this.setManureCountdown(this.getManurePeriod() / 2 + this.random.nextInt(this.getManurePeriod()));
                }
            }
        }

    }

    public boolean isSpecialCase() {
        return false;
    }

    public abstract void produceGoods();

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_WELL, true);
        builder.define(IS_THIRSTY, false);
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public boolean wantsToPickUp(@NotNull ItemStack stack) {
        return this.isFood(stack);
    }

    @Override
    public void pickUpItem(@NotNull ItemEntity itemEntity) {
        if (isFood(itemEntity.getItem()) &&
                ((LivingEntityAccess) this).ifw_foodOrRepairItemPickupCoolDown() == 0 &&
                (this.canFallInLove() || this.isBaby())) {
            ItemStack itemstack = itemEntity.getItem();
            this.onItemPickup(itemEntity);
            this.take(itemEntity, 1);
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                itemEntity.discard();
            }
            this.addFood(1);
            int age = this.getAge();
            if (!this.level().isClientSide && age == 0 && this.canFallInLove()) {
                this.setInLove(null);
            }

            if (this.isBaby()) {
                this.ageUp(getSpeedUpSecondsWhenFeeding(-age), true);
            }
            ((LivingEntityAccess) this).ifw_foodOrRepairItemPickupCoolDown(400);
        }
    }

    @Override
    protected int getBaseExperienceReward() {
        return 0;
    }

    protected void finalizeSpawn() {
        this.food(0.8F + this.random.nextFloat() * 0.2F);
        this.water(0.8F + this.random.nextFloat() * 0.2F);
        this.freedom(0.8F + this.random.nextFloat() * 0.2F);
        this.setManurePeriodAddManureCountdown(24000);
    }

    private void water(float water) {
        if (!this.level().isClientSide()) {
            this.setData(IFWAttachments.water, Mth.clamp(water, 0.0F, 1.0F));
            this.setIsWell(this.isWell());
            this.setIsThirsty(this.isThirsty());
        }
    }

    private void food(float food) {
        if (!this.level().isClientSide()) {
            this.setData(IFWAttachments.food, Mth.clamp(food, 0.0F, 1.0F));
            this.setIsWell(this.isWell());
        }
    }

    private void freedom(float freedom) {
        if (!this.level().isClientSide()) {
            this.setData(IFWAttachments.freedom, Mth.clamp(freedom, 0.0F, 1.0F));
            this.setIsWell(this.isWell());
        }
    }

    public int getManurePeriod() {
        return this.getData(IFWAttachments.manure_period);
    }

    protected void setManurePeriod(int manure_period) {
        if (!this.level().isClientSide()) {
            this.setData(IFWAttachments.manure_period, manure_period);
        }
    }

    public int getManureCountdown() {
        return this.getData(IFWAttachments.manure_countdown);
    }

    protected void setManureCountdown(int manure_countdown) {
        if (!this.level().isClientSide()) {
            this.setData(IFWAttachments.manure_countdown, manure_countdown);
        }
    }

    protected void setManurePeriodAddManureCountdown(int manure_period) {
        this.setManurePeriod(manure_period);
        this.setManureCountdown((int) (Math.random() * manure_period));
    }

    protected void addManureCountdown(int i) {
        this.setData(IFWAttachments.manure_countdown,
                this.getData(IFWAttachments.manure_countdown) + i);
    }

    public float water() {
        return this.getData(IFWAttachments.water);
    }

    public float food() {
        return this.getData(IFWAttachments.food);
    }

    public float freedom() {
        return this.getData(IFWAttachments.freedom);
    }

    public int getProductionCounter() {
        return this.getData(IFWAttachments.production_counter);
    }

    protected void setProductionCounter(int production_counter) {
        if (!this.level().isClientSide()) {
            this.setData(IFWAttachments.production_counter, production_counter);
        }
    }

    protected void addProductionCounter(int i) {
        this.setProductionCounter(this.getData(IFWAttachments.production_counter) + i);
    }

    public boolean isPanic() {return this.getData(IFWAttachments.is_panic);}

    public void setPanic(boolean isPanic) {this.setData(IFWAttachments.is_panic, isPanic);}

    protected void addFood(float food) {
        this.food(this.food() + food);
    }

    protected void addWater(float water) {
        this.water(this.water() + water);
    }

    protected void addFreedom(float freedom) {
        this.freedom(this.freedom() + freedom);
    }

    public void setIsWell(boolean isWell) {
        this.getEntityData().set(IS_WELL, isWell);
    }

    public void setIsThirsty(boolean isThirsty) {
        this.getEntityData().set(IS_THIRSTY, isThirsty);
    }

    public boolean isThirsty() {
        if (this.level().isClientSide()) {
            return this.getEntityData().get(IS_THIRSTY);
        } else {
            return this.water() < 0.5F;
        }
    }

    public boolean isWell() {
        if (this.level().isClientSide()) {
            return this.getEntityData().get(IS_WELL);
        } else {
            return Math.min(this.freedom(), Math.min(this.food(), this.water())) >= 0.25F;
        }
    }

    public boolean isHungry() {
        return this.food() < 0.5F;
    }

    public boolean isVeryHungry() {
        return this.food() < 0.25F;
    }

    public boolean isDesperateForFood() {
        return this.food() < 0.05F;
    }

    public boolean isVeryThirsty() {
        return this.water() < 0.25F;
    }

    public boolean isDesperateForWater() {
        return this.water() < 0.05F;
    }


    public Set<Block> getFoodBlocks() {
        return Set.of(
            Blocks.GRASS_BLOCK,
            Blocks.SHORT_GRASS, Blocks.TALL_GRASS,
            Blocks.FERN, Blocks.LARGE_FERN
        );
    }

    public boolean isNearFood() {
        return isNearFood(this.blockPosition());
    }

    protected boolean isNearFood(BlockPos pos) {
        return isNearFood(pos.getX(), pos.getY(), pos.getZ());
    }

    protected boolean isNearFood(int x, int y, int z) {
        int height = Mth.floor(this.getBbHeight());
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= height; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    BlockPos blockpos = new BlockPos(x + dx, y + dy, z + dz);
                    if (getFoodBlocks().contains(this.level().getBlockState(blockpos).getBlock())) {
                        if (this instanceof IFWCow && this.random.nextInt(100) == 0) {
                            if (net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level(), this)) {
                                this.level().destroyBlock(blockpos, false);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCrowded() {
        return this.getLight() < 7 && this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().expandTowards(2.0, 0.5, 2.0)).size() > 2;
    }

    public boolean isCrowded(double x, double y, double z) {
        if (this.getLight() >= 7) {
            return false;
        }
        AABB bounding_box = new AABB(x - 2, y - 0.5f, z - 2, x + 2, y + 0.5f, z + 2);
        return this.level().getEntitiesOfClass(LivingEntity.class, bounding_box).size() > 2;
    }

    public int getLight() {
        return this.level().getBrightness(LightLayer.SKY, this.blockPosition());
    }

    protected boolean updateWellness() {
        float benefit = 0.1f;
        float penalty = -0.005f;

        if (isNearFood()) {
            this.addFood(benefit);
        } else {
            this.addFood(penalty);
        }

        if (this.isInWater()) {
            this.addWater(benefit);
        } else if (this.isInRain()) {
            this.addWater(benefit / 10.0F);
        } else {
            this.addWater(penalty);
        }

        if (!this.isCrowded()) {
            this.addFreedom(benefit);
        } else {
            this.addFreedom(penalty);
        }
        return this.isWell();
    }

    public boolean hasFullHealth() {
        return this.getHealth() == this.getMaxHealth();
    }

    @Override
    protected void dropFromLootTable(@NotNull DamageSource damageSource, boolean attackedRecently) {
        if (this.isWell() && !damageSource.is(DamageTypes.FALL)) {
            super.dropFromLootTable(damageSource, attackedRecently);
        }
    }

    public static boolean checkAnimalSpawnRules(
            @NotNull EntityType<? extends Animal> type, @NotNull LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
    ) {
        if (spawnType == MobSpawnType.NATURAL && !WorldHelper.isBlueMoon(level)) {
            return false;
        }
        return Animal.checkAnimalSpawnRules(type, level, spawnType, pos, random);
    }
}
