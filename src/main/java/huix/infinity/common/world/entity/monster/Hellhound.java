package huix.infinity.common.world.entity.monster;

import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.*;
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
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;

public class Hellhound extends Wolf {

    public Hellhound(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
        this.getNavigation().setCanFloat(true);
    }

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

            if (this.hellhound.isTame()) {
                return false;
            }

            this.temptingPlayer = this.hellhound.level().getNearestPlayer(this.hellhound, 10.0D);

            if (this.temptingPlayer == null) {
                return false;
            }

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

    public static class SafeHurtByTargetGoal extends HurtByTargetGoal {
        private final Hellhound hellhound;

        public SafeHurtByTargetGoal(Hellhound hellhound) {
            super(hellhound);
            this.hellhound = hellhound;
        }

        @Override
        public boolean canUse() {

            LivingEntity attacker = this.hellhound.getLastHurtByMob();
            if (this.hellhound.isTame() && attacker instanceof Player player && this.hellhound.isOwnedBy(player)) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public void start() {

            LivingEntity target = this.hellhound.getLastHurtByMob();
            if (this.hellhound.isTame() && target instanceof Player player && this.hellhound.isOwnedBy(player)) {
                return;
            }
            super.start();
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

        if (!this.level().isClientSide && this.isTame()) {
            LivingEntity target = this.getTarget();
            if (target instanceof Player player && this.isOwnedBy(player)) {
                this.setTarget(null);
                this.setAggressive(false);
                this.getNavigation().stop();
            }

            if (this.getLastHurtByMob() instanceof Player player && this.isOwnedBy(player)) {
                this.setLastHurtByMob(null);
            }

            if (this.getLastHurtMob() instanceof Player player && this.isOwnedBy(player)) {
                this.setLastHurtMob(null);
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new TemptedByTamingItemGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new SafeHurtByTargetGoal(this));

        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (target) -> {
            return !this.isTame() && this.isAngryAt(target);
        }));

        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, 20, true, false, (target) -> {
            return !this.isTame();
        }));
    }

    @Override
    public boolean canAttack(LivingEntity target) {

        if (this.isTame() && this.isOwnedBy(target)) {
            return false;
        }
        return super.canAttack(target);
    }

    @Override
    public void setTarget(LivingEntity target) {

        if (this.isTame() && target instanceof Player player && this.isOwnedBy(player)) {
            return;
        }
        super.setTarget(target);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {

        if (this.isTame() && damageSource.getEntity() instanceof Player player && this.isOwnedBy(player)) {

            boolean result = super.hurt(damageSource, damage);

            this.setLastHurtByMob(null);
            this.setTarget(null);
            this.setAggressive(false);
            return result;
        }
        return super.hurt(damageSource, damage);
    }

    @Override
    public boolean doHurtTarget(Entity target) {

        if (this.isTame() && target instanceof Player player && this.isOwnedBy(player)) {
            return false;
        }

        boolean result = super.doHurtTarget(target);
        if (result && this.getRandom().nextFloat() < 0.4F) {
            this.playSound(IFWSoundEvents.HELLHOUND_BREATH.get(), 1.0F, 1.0F);
            target.setRemainingFireTicks(20 * (1 + this.random.nextInt(8)));
        }
        return result;
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {

        if (this.isTame() && target instanceof Player player && this.isOwnedBy(player)) {
            return false;
        }
        return super.wantsToAttack(target, owner);
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
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isTame() && this.isOwnedBy(player)) {

            if (this.getTarget() == player) {
                this.setTarget(null);
                this.setAggressive(false);
                this.getNavigation().stop();
            }

            if (this.isFood(itemstack)) {
                return super.mobInteract(player, hand);
            }

            if (!this.level().isClientSide) {
                boolean wasSitting = this.isOrderedToSit();
                this.setOrderedToSit(!wasSitting);
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);

                if (this.isOrderedToSit()) {
                    this.playSound(SoundEvents.WOLF_WHINE, 1.0F, 1.0F);
                } else {
                    this.playSound(SoundEvents.WOLF_PANT, 1.0F, 1.0F);
                }

                return InteractionResult.SUCCESS_NO_ITEM_USED;
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame()) {
            return super.mobInteract(player, hand);
        }

        if (itemstack.is(IFWItems.blazing_wither_bone.get()) && !this.isAggressive() && this.getTarget() != player) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (this.getRandom().nextInt(3) == 0) {

                this.tame(player);
                this.setOrderedToSit(true);

                this.setTarget(null);
                this.setLastHurtByMob(null);
                this.setLastHurtMob(null);
                this.setAggressive(false);
                this.getNavigation().stop();

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
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        return false;
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public boolean isInLove() {
        return false;
    }

    @Override
    public void setInLove(Player player) {
    }

    @Override
    public void setInLoveTime(int time) {
    }

    @Override
    public int getInLoveTime() {
        return 0;
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        if (this.isTame()) {
            return stack.is(ItemTags.WOLF_FOOD);
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