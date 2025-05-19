package huix.infinity.common.world.entity.animal;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.item.IFWBucketItem;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.util.BucketHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Set;

public class IFWCow extends Livestock {
    private static final EntityDimensions BABY_DIMENSIONS = IFWEntityType.COW.get().getDimensions().scale(0.5F).withEyeHeight(0.665F);
    private int data_object_id_milk;

    public IFWCow(EntityType<? extends IFWCow> entityType, Level level) {
        super(entityType, level);
        this.setMilk(100);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, p_335386_ -> p_335386_.is(ItemTags.COW_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public void produceGoods() {
        this.setMilk(this.getMilk() + this.getProductionCounter());
        this.setProductionCounter(0);
    }

    protected void setMilk(int milk) {
        this.data_object_id_milk = Mth.clamp(milk, 0, 100);
    }

    protected int getMilk() {
        if (this.isBaby()) {
            return 0;
        }
        return this.data_object_id_milk;
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.data_object_id_milk = compound.getInt("data_object_id_milk");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        compound.putInt("data_object_id_milk", this.data_object_id_milk);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.COW_FOOD);
    }

    @Override
    public Set<Block> getFoodBlocks() {
        return Set.of(
                Blocks.SHORT_GRASS, Blocks.TALL_GRASS,
                Blocks.FERN, Blocks.LARGE_FERN, Blocks.DANDELION
        );
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.COW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState block) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!this.isBaby() && this.getMilk() == 100 && itemstack.is(Tags.Items.BUCKETS_EMPTY)) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            IFWTier tier = IFWTiers.IRON;
            if (itemstack.getItem() instanceof IFWBucketItem ifwBucket) {
                tier = ifwBucket.tier();
            }
            ItemStack milkBucket = BucketHelper.milkBucket(tier);
            if (!milkBucket.isEmpty()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(itemstack, player, milkBucket));
                this.setMilk(0);
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
            return super.mobInteract(player, hand);
        }
        else if (!this.isBaby() && this.getMilk() >= 25 && itemstack.is(Items.BOWL)) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack milkBowl = ItemUtils.createFilledResult(itemstack, player, IFWItems.milk_bowl.get().getDefaultInstance());
            player.setItemInHand(hand, milkBowl);
            this.setMilk(this.getMilk() - 25);
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        else {
            return super.mobInteract(player, hand);
        }
    }

    @Nullable
    public IFWCow getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        return IFWEntityType.COW.get().create(level);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }
}
