package huix.infinity.common.world.entity.monster.skeleton;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.ai.StationaryRangedBowAttackGoal;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class IFWSkeleton extends AbstractSkeleton implements RangedAttackMob {
    private static final EntityDataAccessor<Integer> DATA_SKELETON_TYPE_ID = SynchedEntityData.defineId(IFWSkeleton.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FRENZIED_BY_BONE_LORD = SynchedEntityData.defineId(IFWSkeleton.class, EntityDataSerializers.BOOLEAN);

    private final StationaryRangedBowAttackGoal<IFWSkeleton> bowGoal = new StationaryRangedBowAttackGoal<>(this, 1.0D, 20, 60, 15.0F);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
        @Override
        public void stop() {
            super.stop();
            // 不再重置攻击姿态，保持举起武器
            // IFWSkeleton.this.setAggressive(false);
        }

        @Override
        public void start() {
            super.start();
            IFWSkeleton.this.setAggressive(true);
        }
    };

    private int frenziedByBoneLordCountdown;
    public int forcedSkeletonType = -1;

    public IFWSkeleton(EntityType<? extends IFWSkeleton> entityType, Level level) {
        super(entityType, level);
        this.reassessWeaponGoal();
        // 默认设置为攻击姿态
        this.setAggressive(true);
    }

    public IFWSkeleton(Level level) {
        this(IFWEntityType.SKELETON.get(), level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SKELETON_TYPE_ID, 0);
        builder.define(DATA_FRENZIED_BY_BONE_LORD, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RestrictSunGoal(this));
        this.goalSelector.addGoal(2, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    public void reassessWeaponGoal() {
        if (this.level() != null && !this.level().isClientSide) {
            // 安全地移除现有的 goals
            if (this.meleeGoal != null) {
                this.goalSelector.removeGoal(this.meleeGoal);
            }
            if (this.bowGoal != null) {
                this.goalSelector.removeGoal(this.bowGoal);
            }

            ItemStack itemstack = this.getItemInHand(InteractionHand.MAIN_HAND);
            if (itemstack.getItem() instanceof BowItem) {
                // 复刻原版的射击间隔逻辑
                int i = 20; // 原版最小攻击间隔
                if (this.level().getDifficulty() != Difficulty.HARD) {
                    i = 40; // 原版在非困难模式下的间隔
                }

                if (this.bowGoal != null) {
                    this.bowGoal.setMinAttackInterval(i);
                    this.goalSelector.addGoal(4, this.bowGoal);
                }
            } else {
                if (this.meleeGoal != null) {
                    this.goalSelector.addGoal(4, this.meleeGoal);
                }
            }
        }
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity target, float velocity) {
        ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW)));
        AbstractArrow abstractarrow = this.getArrow(itemstack, velocity, this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW)));

        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333D) - abstractarrow.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);

        // 完全复刻原版的射击参数
        abstractarrow.shoot(d0, d1 + d3 * (double) 0.2F, d2,
                1.6F,  // 原版箭矢速度
                (float) (14 - this.level().getDifficulty().getId() * 4)); // 原版精准度公式

        // 复刻原版的伤害计算
        double damage = (double)(velocity * 2.0F) + this.random.nextGaussian() * 0.25D + (double)((float)this.level().getDifficulty().getId() * 0.11F);
        abstractarrow.setBaseDamage(damage);

        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(abstractarrow);
    }

    @Override
    protected AbstractArrow getArrow(@NotNull ItemStack arrow, float velocity, @Nullable ItemStack weapon) {
        return ProjectileUtil.getMobArrow(this, arrow, velocity, weapon);
    }

    @Override
    public boolean canFireProjectileWeapon(@NotNull ProjectileWeaponItem weapon) {
        return weapon == Items.BOW;
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkeletonType(compound.getInt("SkeletonType"));
        this.reassessWeaponGoal();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SkeletonType", this.getSkeletonType());
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
        super.setItemSlot(slot, stack);
        if (!this.level().isClientSide) {
            this.reassessWeaponGoal();
            // 设置装备后确保保持攻击姿态
            if (slot == EquipmentSlot.MAINHAND && !stack.isEmpty()) {
                this.setAggressive(true);
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        // Drop arrows for all skeleton types
        this.spawnAtLocation(Items.ARROW, this.random.nextInt(3));
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource random, @NotNull DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData);

        // Set skeleton type - 只使用默认类型 0 或特殊类型 2
        int skeletonType = this.forcedSkeletonType >= 0 ? this.forcedSkeletonType : 0;
        this.setSkeletonType(skeletonType);

        this.populateDefaultEquipmentSlots(random, difficulty);
        this.populateDefaultEquipmentEnchantments(level, random, difficulty);
        this.reassessWeaponGoal();
        this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficulty.getSpecialMultiplier());

        // Halloween pumpkin
        if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            LocalDate localdate = LocalDate.now();
            int i = localdate.get(ChronoField.DAY_OF_MONTH);
            int j = localdate.get(ChronoField.MONTH_OF_YEAR);
            if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
                this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.PUMPKIN));
                this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }

        // 确保生成后保持攻击姿态
        this.setAggressive(true);

        return spawnData;
    }

    public void addRandomWeapon() {
        if (this.getSkeletonType() == 2 && this.random.nextInt(20) == 0) {
            // 特殊类型骷髅使用近战武器
            int dayOfWorld = Math.toIntExact(this.level().getDayTime() / 24000); // 简化的天数计算
            if (dayOfWorld >= 10) {
                ItemStack weapon = dayOfWorld >= 20 && !this.random.nextBoolean() ?
                        new ItemStack(IFWItems.rusted_iron_sword.get()) :
                        new ItemStack(IFWItems.rusted_iron_dagger.get());
                // 随机损坏武器
                if (weapon.isDamageableItem()) {
                    weapon.setDamageValue((int)(weapon.getMaxDamage() * this.random.nextFloat()));
                }
                this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
                return;
            }
        }

        // 默认武器
        ItemStack defaultWeapon = this.getSkeletonType() == 2 ?
                new ItemStack(IFWItems.wooden_club.get()) :
                new ItemStack(Items.BOW);

        // 随机损坏武器
        if (defaultWeapon.isDamageableItem()) {
            defaultWeapon.setDamageValue((int)(defaultWeapon.getMaxDamage() * this.random.nextFloat()));
        }

        this.setItemSlot(EquipmentSlot.MAINHAND, defaultWeapon);
    }

    public int getSkeletonType() {
        return this.entityData.get(DATA_SKELETON_TYPE_ID);
    }

    public void setSkeletonType(int type) {
        this.entityData.set(DATA_SKELETON_TYPE_ID, type);
        // 设置对应的生命值
        var healthAttribute = this.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null) {
            double maxHealth = this.getMaxHealthForType();
            healthAttribute.setBaseValue(maxHealth);
        }
        // 默认装备弓
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        this.reassessWeaponGoal();
        // 确保设置类型后保持攻击姿态
        this.setAggressive(true);
    }

    /**
     * 根据实体类型返回对应的最大生命值
     */
    protected double getMaxHealthForType() {
        if (this instanceof AncientBoneLord) {
            return 24.0D;
        } else if (this instanceof LongdeadGuardian) {
            return 24.0D;
        } else if (this instanceof BoneLord) {
            return 20.0D;
        } else if (this instanceof Longdead) {
            return 12.0D;
        } else {
            return 6.0D;
        }
    }

    public boolean isFrenziedByBoneLord() {
        return this.entityData.get(DATA_FRENZIED_BY_BONE_LORD);
    }

    public void setFrenziedByBoneLord(LivingEntity target) {
        this.frenziedByBoneLordCountdown = 20;
        this.entityData.set(DATA_FRENZIED_BY_BONE_LORD, true);
        if (this.getTarget() == null) {
            this.setTarget(target);
        }
    }

    @Override
    public void aiStep() {
        if (this.frenziedByBoneLordCountdown > 0) {
            this.frenziedByBoneLordCountdown--;
            if (this.frenziedByBoneLordCountdown <= 0) {
                this.entityData.set(DATA_FRENZIED_BY_BONE_LORD, false);
            }
        }
        super.aiStep();

        // 确保始终保持攻击姿态（持有武器时）
        ItemStack mainHandItem = this.getItemInHand(InteractionHand.MAIN_HAND);
        if (!mainHandItem.isEmpty() && !this.isAggressive()) {
            this.setAggressive(true);
        }
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        // Immune to arrow damage from other skeletons
        if (damageSource.getDirectEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof IFWSkeleton) {
            return true;
        }
        return super.isInvulnerableTo(damageSource);
    }

    public boolean isLongdead() {
        return false;
    }

    public boolean isBoneLord() {
        return false;
    }

    public boolean isAncientBoneLord() {
        return false;
    }

    @Override
    protected int getBaseExperienceReward() {
        return 5;
    }
}