package huix.infinity.common.world.entity.monster;

import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

public class Hellhound extends Wolf {

    public Hellhound(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
        this.getNavigation().setCanFloat(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.removeAllGoals(goal -> true);
        this.targetSelector.removeAllGoals(goal -> true);

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, 20, true, false, null));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.MAX_HEALTH, 20.0F)
                .add(Attributes.ATTACK_DAMAGE, 4.0F);
    }

    @Override
    public boolean isAngry() {
        return false;
    }

    @Override
    public float getTailAngle() {
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
        return InteractionResult.PASS;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        return false;
    }

    @Override
    public boolean isTame() {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 3;
    }

    @Override
    public boolean isOrderedToSit() {
        return false;
    }

    @Override
    public void setOrderedToSit(boolean sitting) {
    }

    @Override
    public boolean isAggressive() {
        return true;
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