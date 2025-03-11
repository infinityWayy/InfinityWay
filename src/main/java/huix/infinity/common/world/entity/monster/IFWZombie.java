package huix.infinity.common.world.entity.monster;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.ai.ZombieAttackGoal;
import huix.infinity.common.world.entity.animal.Livestock;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.util.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class IFWZombie extends Digger {
    private static final ResourceLocation SPEED_MODIFIER_BABY_ID = ResourceLocation.withDefaultNamespace("baby");
    private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(
            SPEED_MODIFIER_BABY_ID, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final ResourceLocation REINFORCEMENT_CALLER_CHARGE_ID = ResourceLocation.withDefaultNamespace("reinforcement_caller_charge");
    private static final AttributeModifier ZOMBIE_REINFORCEMENT_CALLEE_CHARGE = new AttributeModifier(
            ResourceLocation.withDefaultNamespace("reinforcement_callee_charge"), -0.05F, AttributeModifier.Operation.ADD_VALUE
    );
    private static final ResourceLocation LEADER_ZOMBIE_BONUS_ID = ResourceLocation.withDefaultNamespace("leader_zombie_bonus");
    private static final ResourceLocation ZOMBIE_RANDOM_SPAWN_BONUS_ID = ResourceLocation.withDefaultNamespace("zombie_random_spawn_bonus");
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(IFWZombie.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_SPECIAL_TYPE_ID = SynchedEntityData.defineId(IFWZombie.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_DROWNED_CONVERSION_ID = SynchedEntityData.defineId(IFWZombie.class, EntityDataSerializers.BOOLEAN);
    public static final float ZOMBIE_LEADER_CHANCE = 0.05F;
    public static final int REINFORCEMENT_ATTEMPTS = 50;
    public static final int REINFORCEMENT_RANGE_MAX = 40;
    public static final int REINFORCEMENT_RANGE_MIN = 7;
    private static final EntityDimensions BABY_DIMENSIONS = IFWEntityType.ZOMBIE.get().getDimensions().scale(0.5F).withEyeHeight(0.93F);
    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = p_34284_ -> p_34284_ == Difficulty.HARD;
    private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
    private boolean canBreakDoors;
    private int inWaterTime;
    protected int conversionTime;

    public IFWZombie(EntityType<? extends IFWZombie> entityType, Level level) {
        super(entityType, level);
    }
    public IFWZombie(Level level) {
        this(IFWEntityType.ZOMBIE.get(), level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new ZombieAttackTurtleEggGoal(this, 1.0, 3));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::canBreakDoors));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(ZombifiedPiglin.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Livestock.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_BABY_ID, false);
        builder.define(DATA_SPECIAL_TYPE_ID, 0);
        builder.define(DATA_DROWNED_CONVERSION_ID, false);
    }

    public boolean isUnderWaterConverting() {
        return this.getEntityData().get(DATA_DROWNED_CONVERSION_ID);
    }

    public boolean canBreakDoors() {
        return this.canBreakDoors;
    }

    /**
     * Sets or removes EntityAIBreakDoor task
     */
    public void setCanBreakDoors(boolean canBreakDoors) {
        if (this.supportsBreakDoorGoal() && GoalUtils.hasGroundPathNavigation(this)) {
            if (this.canBreakDoors != canBreakDoors) {
                this.canBreakDoors = canBreakDoors;
                ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(canBreakDoors);
                if (canBreakDoors) {
                    this.goalSelector.addGoal(1, this.breakDoorGoal);
                } else {
                    this.goalSelector.removeGoal(this.breakDoorGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.removeGoal(this.breakDoorGoal);
            this.canBreakDoors = false;
        }
    }

    protected boolean supportsBreakDoorGoal() {
        return true;
    }

    @Override
    public boolean isBaby() {
        return this.getEntityData().get(DATA_BABY_ID);
    }

    @Override
    protected int getBaseExperienceReward() {
        if (this.isBaby()) {
            this.xpReward = (int) ((double) this.xpReward * 2.5);
        }

        return super.getBaseExperienceReward();
    }

    /**
     * Set whether this zombie is a child.
     */
    @Override
    public void setBaby(boolean childZombie) {
        this.getEntityData().set(DATA_BABY_ID, childZombie);
        if (this.level() != null && !this.level().isClientSide) {
            AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            attributeinstance.removeModifier(SPEED_MODIFIER_BABY_ID);
            if (childZombie) {
                attributeinstance.addTransientModifier(SPEED_MODIFIER_BABY);
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_BABY_ID.equals(key)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
    }

    // 暂时不会转换
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.isAlive() && !this.isNoAi()) {
            if (this.isUnderWaterConverting()) {
                this.conversionTime--;
                if (this.conversionTime < 0) {
                    this.doUnderWaterConversion();
                }
            } else if (this.convertsInWater()) {
                if (this.isEyeInFluid(FluidTags.WATER)) {
                    this.inWaterTime++;
                    if (this.inWaterTime >= 600) {
                        this.startUnderWaterConversion(300);
                    }
                } else {
                    this.inWaterTime = -1;
                }
            }
        }

        super.tick();
    }

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        Item item = itemstack.getItem();
                        itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            this.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.igniteForSeconds(8.0F);
                }
            }
        }

        super.aiStep();
    }

    private void startUnderWaterConversion(int conversionTime) {
        this.conversionTime = conversionTime;
        this.getEntityData().set(DATA_DROWNED_CONVERSION_ID, true);
    }

    protected void doUnderWaterConversion() {
        if (!net.neoforged.neoforge.event.EventHooks.canLivingConvert(this, EntityType.DROWNED, (timer) -> this.conversionTime = timer))
            return;
//        this.convertToZombieType(EntityType.DROWNED);
        if (!this.isSilent()) {
            this.level().levelEvent(null, 1040, this.blockPosition(), 0);
        }
    }

    protected void convertToZombieType(EntityType<? extends IFWZombie> entityType) {
        IFWZombie zombie = this.convertTo(entityType, true);
        if (zombie != null) {
            zombie.handleAttributes(zombie.level().getCurrentDifficultyAt(zombie.blockPosition()).getSpecialMultiplier());
            zombie.setCanBreakDoors(zombie.supportsBreakDoorGoal() && this.canBreakDoors());
            net.neoforged.neoforge.event.EventHooks.onLivingConvert(this, zombie);
        }
    }

    protected boolean isSunSensitive() {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (!super.hurt(source, amount)) {
            return false;
        } else if (!(this.level() instanceof ServerLevel serverlevel)) {
            return false;
        } else {
            LivingEntity livingentity = this.getTarget();
            if (livingentity == null && source.getEntity() instanceof LivingEntity) {
                livingentity = (LivingEntity) source.getEntity();
            }

            if (livingentity != null
                    && this.level().getDifficulty() == Difficulty.HARD
                    && (double) this.random.nextFloat() < this.getAttributeValue(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                    && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                int i = Mth.floor(this.getX());
                int j = Mth.floor(this.getY());
                int k = Mth.floor(this.getZ());
                net.minecraft.world.entity.monster.Zombie zombie = new net.minecraft.world.entity.monster.Zombie(this.level());

                for (int l = 0; l < 50; l++) {
                    int i1 = i + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    int j1 = j + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    int k1 = k + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    BlockPos blockpos = new BlockPos(i1, j1, k1);
                    EntityType<?> entitytype = zombie.getType();
                    if (SpawnPlacements.isSpawnPositionOk(entitytype, this.level(), blockpos)
                            && SpawnPlacements.checkSpawnRules(entitytype, serverlevel, MobSpawnType.REINFORCEMENT, blockpos, this.level().random)) {
                        zombie.setPos(i1, j1, k1);
                        if (!this.level().hasNearbyAlivePlayer(i1, j1, k1, 7.0)
                                && this.level().isUnobstructed(zombie)
                                && this.level().noCollision(zombie)
                                && !this.level().containsAnyLiquid(zombie.getBoundingBox())) {
                            zombie.setTarget(livingentity);
                            zombie.finalizeSpawn(serverlevel, this.level().getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.REINFORCEMENT, null);
                            serverlevel.addFreshEntityWithPassengers(zombie);
                            AttributeInstance attributeinstance = this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
                            AttributeModifier attributemodifier = attributeinstance.getModifier(REINFORCEMENT_CALLER_CHARGE_ID);
                            double d0 = attributemodifier != null ? attributemodifier.amount() : 0.0;
                            attributeinstance.removeModifier(REINFORCEMENT_CALLER_CHARGE_ID);
                            attributeinstance.addPermanentModifier(
                                    new AttributeModifier(REINFORCEMENT_CALLER_CHARGE_ID, d0 - 0.05, AttributeModifier.Operation.ADD_VALUE)
                            );
                            zombie.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(ZOMBIE_REINFORCEMENT_CALLEE_CHARGE);
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
                entity.igniteForSeconds((float) (2 * (int) f));
            }
        }

        return flag;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState block) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource random, @NotNull DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.addRandomWeapon();
    }

    public void addRandomWeapon() {
        float chance = this.getRandom().nextFloat();
        float threshold = this.isVillager() ? 0.2f : 0.05f;
        if (chance < threshold) {
            List<WeightedEntry.Wrapper<Item>> weapons = new ArrayList<>();

            weapons.add(WeightedEntry.wrap(IFWItems.wooden_shovel.asItem(), 1));
            weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_shovel.asItem(), 2));

            long day = WorldHelper.getDay(this.level());
            if (day >= 10) {
//                weapons.add(WeightedEntry.wrap(IFWItems.hatchetRustedIron, 1));
            }
            if (this.isVillager()) {
                weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_shears.asItem(), 1));
                weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_scythe.asItem(), 1));
                if (day >= 10) {
                    weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_hoe.asItem(), 1));
                    weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_mattock.asItem(), 1));
                }
                if (day >= 20) {
                    weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_pickaxe.asItem(), 1));
                }
            } else {
//                items.add(new RandomItemListEntry(Item.cudgelWood, 1));
                weapons.add(WeightedEntry.wrap(IFWItems.wooden_club.asItem(), 1));
                weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_sword.asItem(), 1));
                weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_dagger.get(), 1));
            }
            Optional<WeightedEntry.Wrapper<Item>> selected = WeightedRandom.getRandomItem(this.getRandom(), weapons);
            if (selected.isPresent()) {
                ItemStack stack = new ItemStack(selected.get().data());

                // 随机附魔

                stack.setDamageValue((int)(stack.getMaxDamage() * random.nextFloat()));

                this.setItemSlot(EquipmentSlot.MAINHAND, stack);
            }
        }
    }

    private boolean isVillager() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsBaby", this.isBaby());
        compound.putBoolean("CanBreakDoors", this.canBreakDoors());
        compound.putInt("InWaterTime", this.isInWater() ? this.inWaterTime : -1);
        compound.putInt("DrownedConversionTime", this.isUnderWaterConverting() ? this.conversionTime : -1);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBaby(compound.getBoolean("IsBaby"));
        this.setCanBreakDoors(compound.getBoolean("CanBreakDoors"));
        this.inWaterTime = compound.getInt("InWaterTime");
        if (compound.contains("DrownedConversionTime", 99) && compound.getInt("DrownedConversionTime") > -1) {
            this.startUnderWaterConversion(compound.getInt("DrownedConversionTime"));
        }
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity entity) {
        boolean flag = super.killedEntity(level, entity);
        if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) && entity instanceof Villager villager && net.neoforged.neoforge.event.EventHooks.canLivingConvert(entity, EntityType.ZOMBIE_VILLAGER, (timer) -> {
        })) {
            if (level.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
                return flag;
            }

            ZombieVillager zombievillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            if (zombievillager != null) {
                zombievillager.finalizeSpawn(
                        level,
                        level.getCurrentDifficultyAt(zombievillager.blockPosition()),
                        MobSpawnType.CONVERSION,
                        new net.minecraft.world.entity.monster.Zombie.ZombieGroupData(false, true)
                );
                zombievillager.setVillagerData(villager.getVillagerData());
                zombievillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
                zombievillager.setTradeOffers(villager.getOffers().copy());
                zombievillager.setVillagerXp(villager.getVillagerXp());
                net.neoforged.neoforge.event.EventHooks.onLivingConvert(entity, zombievillager);
                if (!this.isSilent()) {
                    level.levelEvent(null, 1026, this.blockPosition(), 0);
                }

                flag = false;
            }
        }

        return flag;
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        return (!stack.is(Items.EGG) || !this.isBaby() || !this.isPassenger()) && super.canHoldItem(stack);
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        return !stack.is(Items.GLOW_INK_SAC) && super.wantsToPickUp(stack);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        float f = difficulty.getSpecialMultiplier();
        this.setCanPickUpLoot(randomsource.nextFloat() < 0.55F * f);
        if (spawnGroupData == null) {
            spawnGroupData = new net.minecraft.world.entity.monster.Zombie.ZombieGroupData(getSpawnAsBabyOdds(randomsource), true);
        }

        if (spawnGroupData instanceof net.minecraft.world.entity.monster.Zombie.ZombieGroupData zombie$zombiegroupdata) {
            if (zombie$zombiegroupdata.isBaby) {
                this.setBaby(true);
                if (zombie$zombiegroupdata.canSpawnJockey) {
                    if ((double) randomsource.nextFloat() < 0.05) {
                        List<Chicken> list = level.getEntitiesOfClass(
                                Chicken.class, this.getBoundingBox().inflate(5.0, 3.0, 5.0), EntitySelector.ENTITY_NOT_BEING_RIDDEN
                        );
                        if (!list.isEmpty()) {
                            Chicken chicken = list.getFirst();
                            chicken.setChickenJockey(true);
                            this.startRiding(chicken);
                        }
                    } else if ((double) randomsource.nextFloat() < 0.05) {
                        Chicken chicken1 = EntityType.CHICKEN.create(this.level());
                        if (chicken1 != null) {
                            chicken1.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                            chicken1.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null);
                            chicken1.setChickenJockey(true);
                            this.startRiding(chicken1);
                            level.addFreshEntity(chicken1);
                        }
                    }
                }
            }

            this.setCanBreakDoors(this.supportsBreakDoorGoal() && randomsource.nextFloat() < f * 0.1F);
            this.populateDefaultEquipmentSlots(randomsource, difficulty);
            this.populateDefaultEquipmentEnchantments(level, randomsource, difficulty);
        }

        if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            LocalDate localdate = LocalDate.now();
            int i = localdate.getDayOfMonth();
            int j = localdate.getMonth().getValue();
            if (j == 10 && i == 31 && randomsource.nextFloat() < 0.25F) {
                this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(randomsource.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }

        this.handleAttributes(f);
        return spawnGroupData;
    }

    public static boolean getSpawnAsBabyOdds(RandomSource random) {
        return random.nextFloat() < 0.05F;
    }

    protected void handleAttributes(float difficulty) {
        this.randomizeReinforcementsChance();
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
                .addOrReplacePermanentModifier(
                        new AttributeModifier(RANDOM_SPAWN_BONUS_ID, this.random.nextDouble() * 0.05F, AttributeModifier.Operation.ADD_VALUE)
                );
        double d0 = this.random.nextDouble() * 1.5 * (double) difficulty;
        if (d0 > 1.0) {
            this.getAttribute(Attributes.FOLLOW_RANGE)
                    .addOrReplacePermanentModifier(new AttributeModifier(ZOMBIE_RANDOM_SPAWN_BONUS_ID, d0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        if (this.random.nextFloat() < difficulty * 0.05F) {
            this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                    .addOrReplacePermanentModifier(
                            new AttributeModifier(LEADER_ZOMBIE_BONUS_ID, this.random.nextDouble() * 0.25 + 0.5, AttributeModifier.Operation.ADD_VALUE)
                    );
            this.getAttribute(Attributes.MAX_HEALTH)
                    .addOrReplacePermanentModifier(
                            new AttributeModifier(LEADER_ZOMBIE_BONUS_ID, this.random.nextDouble() * 3.0 + 1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    );
            this.setCanBreakDoors(this.supportsBreakDoorGoal());
        }
    }

    protected void randomizeReinforcementsChance() {
        this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(this.random.nextDouble() + 0.1F);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        if (damageSource.getEntity() instanceof Creeper creeper && creeper.canDropMobsSkull()) {
            ItemStack itemstack = this.getSkull();
            if (!itemstack.isEmpty()) {
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(itemstack);
            }
        }
    }

    protected ItemStack getSkull() {
        return new ItemStack(Items.ZOMBIE_HEAD);
    }

    class ZombieAttackTurtleEggGoal extends RemoveBlockGoal {
        ZombieAttackTurtleEggGoal(PathfinderMob mob, double speedModifier, int verticalSearchRange) {
            super(Blocks.TURTLE_EGG, mob, speedModifier, verticalSearchRange);
        }

        @Override
        public void playDestroyProgressSound(LevelAccessor level, @NotNull BlockPos pos) {
            level.playSound(null, pos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + IFWZombie.this.random.nextFloat() * 0.2F);
        }

        @Override
        public void playBreakSound(Level level, @NotNull BlockPos pos) {
            level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        @Override
        public double acceptedDistance() {
            return 1.14;
        }
    }

    public static class ZombieGroupData implements SpawnGroupData {
        public final boolean isBaby;
        public final boolean canSpawnJockey;

        public ZombieGroupData(boolean isBaby, boolean canSpawnJockey) {
            this.isBaby = isBaby;
            this.canSpawnJockey = canSpawnJockey;
        }
    }
}
