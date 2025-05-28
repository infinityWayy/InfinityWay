package huix.infinity.common.world.entity.monster;

import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;

public class Hellhound extends Wolf {

    private boolean aiNeedsUpdate = false;

    public Hellhound(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
        this.getNavigation().setCanFloat(true);
    }

    // 自定义AI目标：被驯服物品吸引
    public static class TemptedByTamingItemGoal extends Goal {
        private static final double TEMP_SPEED_MODIFIER = 1.0D;
        private static final int COOLDOWN_TIME = 100;

        private final Hellhound hellhound;
        private Player temptingPlayer;
        private int calmDown;

        public TemptedByTamingItemGoal(Hellhound hellhound) {
            this.hellhound = hellhound;
            this.setFlags(java.util.EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.calmDown > 0) {
                --this.calmDown;
                return false;
            }

            // 只有未驯服的地狱犬才会被吸引
            if (this.hellhound.isTame()) {
                return false;
            }

            this.temptingPlayer = this.hellhound.level().getNearestPlayer(this.hellhound, 10.0D);

            if (this.temptingPlayer == null) {
                return false;
            }

            // 检查玩家是否手持驯服物品
            return this.isTemptingItem(this.temptingPlayer.getMainHandItem()) ||
                    this.isTemptingItem(this.temptingPlayer.getOffhandItem());
        }

        private boolean isTemptingItem(ItemStack stack) {
            return stack.is(IFWItems.blazing_wither_bone.get());
        }

        @Override
        public boolean canContinueToUse() {
            if (this.hellhound.isTame()) {
                return false;
            }

            if (this.temptingPlayer != null && this.temptingPlayer.isAlive()) {
                if (this.hellhound.distanceToSqr(this.temptingPlayer) < 100.0D) {
                    return this.isTemptingItem(this.temptingPlayer.getMainHandItem()) ||
                            this.isTemptingItem(this.temptingPlayer.getOffhandItem());
                }
            }
            return false;
        }

        @Override
        public void start() {
            // 清除当前目标，停止攻击
            this.hellhound.setTarget(null);
        }

        @Override
        public void stop() {
            this.temptingPlayer = null;
            this.hellhound.getNavigation().stop();
            this.calmDown = COOLDOWN_TIME;
        }

        @Override
        public void tick() {
            this.hellhound.getLookControl().setLookAt(this.temptingPlayer,
                    (float)(this.hellhound.getMaxHeadYRot() + 20), (float)this.hellhound.getMaxHeadXRot());

            if (this.hellhound.distanceToSqr(this.temptingPlayer) < 6.25D) {
                this.hellhound.getNavigation().stop();
            } else {
                this.hellhound.getNavigation().moveTo(this.temptingPlayer, TEMP_SPEED_MODIFIER);
            }
        }
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public void setBaby(boolean baby) {
    }

    @Override
    public void tick() {
        super.tick();

        // 检查是否需要更新AI
        if (this.aiNeedsUpdate && !this.level().isClientSide) {
            this.updateAI();
            this.aiNeedsUpdate = false;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.removeAllGoals(goal -> true);
        this.targetSelector.removeAllGoals(goal -> true);

        this.goalSelector.addGoal(1, new FloatGoal(this));

        // 根据驯服状态注册不同的AI
        if (this.isTame()) {
            // 驯服后的AI
            this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
            this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
            this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
            this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
            this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
            this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

            this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
            this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        } else {
            // 未驯服时的AI - 添加被驯服物品吸引的目标作为最高优先级
            this.goalSelector.addGoal(2, new TemptedByTamingItemGoal(this));
            this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
            this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
            this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
            this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
            this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, 20, true, false, null));
        }
    }

    // 新方法：更新AI
    private void updateAI() {
        this.registerGoals();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.MAX_HEALTH, 20.0F)
                .add(Attributes.ATTACK_DAMAGE, 4.0F);
    }

    @Override
    public boolean isAngry() {
        return !this.isTame() && super.isAngry();
    }

    @Override
    public float getTailAngle() {
        if (this.isTame()) {
            return this.isInSittingPose() ? 1.5F : 1.3F;
        }
        return 1.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return IFWSoundEvents.HELLHOUND_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return IFWSoundEvents.HELLHOUND_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IFWSoundEvents.HELLHOUND_DEATH.get();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result && this.getRandom().nextFloat() < 0.4F) {
            this.playSound(IFWSoundEvents.HELLHOUND_BREATH.get(), 1.0F, 1.0F);
            target.setRemainingFireTicks(20 * (1 + this.random.nextInt(8)));
        }
        return result;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // 如果已经驯服且玩家是主人
        if (this.isTame() && this.isOwnedBy(player)) {
            // 如果手里有食物，处理喂食
            if (this.isFood(itemstack)) {
                return super.mobInteract(player, hand);
            }

            // 否则切换坐下状态
            if (!this.level().isClientSide) {
                boolean wasSitting = this.isOrderedToSit();
                this.setOrderedToSit(!wasSitting);

                // 播放音效
                if (this.isOrderedToSit()) {
                    this.playSound(SoundEvents.WOLF_WHINE, 1.0F, 1.0F);
                } else {
                    this.playSound(SoundEvents.WOLF_PANT, 1.0F, 1.0F);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // 如果已经驯服但不是主人，使用原版逻辑
        if (this.isTame()) {
            return super.mobInteract(player, hand);
        }

        // 未驯服的情况下的驯服逻辑
        if (itemstack.is(IFWItems.blazing_wither_bone.get()) && !this.isAggressive() && this.getTarget() != player) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            // 33%的驯服成功率
            if (this.getRandom().nextInt(3) == 0) {
                // 驯服成功
                this.tame(player);
                this.setOrderedToSit(true);

                // 立即清除当前目标并停止攻击
                this.setTarget(null);
                this.setLastHurtByMob(null);
                this.setLastHurtMob(null);

                // 立即更新AI
                this.updateAI();

                // 播放成功音效和粒子效果
                if (this.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 7; ++i) {
                        double d0 = this.random.nextGaussian() * 0.02D;
                        double d1 = this.random.nextGaussian() * 0.02D;
                        double d2 = this.random.nextGaussian() * 0.02D;
                        serverLevel.sendParticles(ParticleTypes.HEART,
                                this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D),
                                1, d0, d1, d2, 0.0D);
                    }
                }
                this.playSound(SoundEvents.WOLF_WHINE, 1.0F, 1.0F);

                return InteractionResult.SUCCESS;
            } else {
                // 驯服失败，播放失败音效和粒子效果
                if (this.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 7; ++i) {
                        double d0 = this.random.nextGaussian() * 0.02D;
                        double d1 = this.random.nextGaussian() * 0.02D;
                        double d2 = this.random.nextGaussian() * 0.02D;
                        serverLevel.sendParticles(ParticleTypes.SMOKE,
                                this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D),
                                1, d0, d1, d2, 0.0D);
                    }
                }
                this.playSound(SoundEvents.WOLF_GROWL, 1.0F, 1.0F);

                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        // 读取数据后标记需要更新AI
        this.aiNeedsUpdate = true;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        // 驯服后可以喂食来恢复血量
        if (this.isTame()) {
            return stack.is(IFWItems.cooked_worm.get()) ||
                    stack.is(net.minecraft.world.item.Items.COOKED_BEEF) ||
                    stack.is(net.minecraft.world.item.Items.COOKED_PORKCHOP);
        }
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected int getBaseExperienceReward() {
        return this.isTame() ? super.getBaseExperienceReward() : super.getBaseExperienceReward() * 3;
    }

    @Override
    public boolean isAggressive() {
        return !this.isTame() && super.isAggressive();
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, net.minecraft.world.entity.MobSpawnType spawnType) {
        return this.isDarkEnoughToSpawn(level, this.blockPosition(), this.getRandom()) && super.checkSpawnRules(level, spawnType);
    }

    protected boolean isDarkEnoughToSpawn(LevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int lightLevel = level.getMaxLocalRawBrightness(pos);
            if (level instanceof Level serverLevel && serverLevel.isThundering()) {
                // Thunder logic would go here if needed
            }
            return lightLevel <= random.nextInt(8);
        }
    }

    public static boolean canSpawn(EntityType<Hellhound> entityType, LevelAccessor level,
                                   net.minecraft.world.entity.MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkMonsterSpawnRules(entityType, (ServerLevelAccessor) level, spawnType, pos, random);
    }

    public static boolean checkMonsterSpawnRules(EntityType<? extends Mob> entityType, ServerLevelAccessor level,
                                                 net.minecraft.world.entity.MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL &&
                isDarkEnoughToSpawnStatic(level, pos, random) &&
                Mob.checkMobSpawnRules(entityType, level, spawnType, pos, random);
    }

    private static boolean isDarkEnoughToSpawnStatic(LevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int lightLevel = level.getMaxLocalRawBrightness(pos);
            return lightLevel <= random.nextInt(8);
        }
    }
}