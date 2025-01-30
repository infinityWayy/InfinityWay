package huix.infinity.mixin.world.entity.animal;

import huix.infinity.common.world.entity.ai.MoveToFoodItemGoals;
import huix.infinity.common.world.entity.ai.SeekFoodIfHungryGoal;
import huix.infinity.common.world.entity.animal.Livestock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( Chicken.class )
public abstract class ChickenMixin extends Animal implements Livestock {
    @Unique private int max_num_feathers;
    @Unique private int num_feathers;
    @Shadow public float oFlap;
    @Shadow public float flap;
    @Shadow public float oFlapSpeed;
    @Shadow public float flapSpeed;
    @Shadow public float flapping;
    @Shadow public abstract boolean isChickenJockey();
    @Shadow public abstract boolean isFood(@NotNull ItemStack stack);

    protected ChickenMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        if (!this.level().isClientSide) {
//            this.setManurePeriod(this.getManurePeriod() * 8 * 2);
            this.max_num_feathers = this.getRandom().nextInt(2) + 1;
            if (this.max_num_feathers > 1 && this.getRandom().nextInt(2) == 0) {
                --this.max_num_feathers;
            }
            this.num_feathers = this.max_num_feathers;
        }
        this.finalizeSpawn();
    }

    @Inject(method = "registerGoals", at = @At("RETURN"))
    protected void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new MoveToFoodItemGoals(this, getFoodTags()));
        this.goalSelector.addGoal(2, new SeekFoodIfHungryGoal(this));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    public void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.max_num_feathers = compound.getInt("max_num_feathers");
        this.num_feathers = compound.getInt("num_feathers");
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    public void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("max_num_feathers", this.max_num_feathers);
        compound.putInt("num_feathers", this.num_feathers);
    }

    /**
     * @author limingzxc
     * @reason 让坤坤会饿，会掉羽毛
     */
    @Overwrite
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
                        && !this.isChickenJockey() /*&& this.isWell()*/) {
                    this.productionAddCounter(1);
                }
                this.produceGoods();
                this.addFood(-0.01F);
                if (this.isAlive() && this.isHungry()) {
                    tryEatFoodBlock();
                }
            }
        }
    }

    @Unique
    private void tryEatFoodBlock() {
        if (isNearFood()) {
            this.addFood(0.5F);
        }
    }

    @Unique
    public void produceGoods() {
        int feather_threshold = 100;
        if (this.productionCounter() >= feather_threshold && this.random.nextInt(feather_threshold * 5) == 0) {
            this.gainFeather();
            this.productionAddCounter(-feather_threshold);
            return;
        }
        int egg_threshold = 200;
        if (this.productionCounter() >= egg_threshold && this.random.nextInt(20) == 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.EGG);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.productionAddCounter(-egg_threshold);
        }
    }

    @Unique
    protected void gainFeather() {
        if (this.num_feathers < this.max_num_feathers) {
            ++this.num_feathers;
        } else {
            this.spawnAtLocation(Items.FEATHER);
            this.gameEvent(GameEvent.ENTITY_PLACE);
        }
    }

    @Override
    public void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        Livestock.super.defineSynchedData(builder);
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public boolean wantsToPickUp(@NotNull ItemStack stack) {
        return isFood(stack);
    }

    @Unique
    public TagKey<Item> getFoodTags() {
        return Tags.Items.SEEDS_WHEAT;
    }

    @Override
    public void pickUpItem(@NotNull ItemEntity itemEntity) {
        if (this.isHungry()) {
            ItemStack itemstack = itemEntity.getItem();
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                itemEntity.discard();
            }
            this.food(1);
        }
    }

    @Overwrite
    protected int getBaseExperienceReward() {
        return 0;
    }

    @Override
    public Animal animal() {
        return this;
    }
}
