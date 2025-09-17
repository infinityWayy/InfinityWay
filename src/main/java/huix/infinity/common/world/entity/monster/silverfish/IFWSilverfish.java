package huix.infinity.common.world.entity.monster.silverfish;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.mob.Livestock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IFWSilverfish extends Monster {
    protected int allySummonCooldown = 0;

    public IFWSilverfish(EntityType<? extends IFWSilverfish> type, Level level) {
        super(type, level);
        this.xpReward = isNormalSilverfish() ? 5 : 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Livestock.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Villager.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (!this.level().isClientSide && result) {
            if (this.allySummonCooldown <= 0 && (source.getEntity() != null)) {
                this.allySummonCooldown = 20;
            }
        }
        return result;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && allySummonCooldown > 0) {
            allySummonCooldown--;
            if (allySummonCooldown == 0)
                trySummonAllies();
        }
        this.yBodyRot = this.getYRot();
    }

    protected boolean isInfestedBlock(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof InfestedBlock;
    }

    protected Block getOriginalBlockFromInfested(BlockState state) {
        if (state.getBlock() instanceof InfestedBlock infested) {
            return infested.getHostBlock();
        }
        return Blocks.STONE;
    }

    protected void trySummonAllies() {
        BlockPos origin = this.blockPosition();
        Level level = this.level();
        for (int dy = -5; dy <= 5; dy++) {
            for (int dx = -10; dx <= 10; dx++) {
                for (int dz = -10; dz <= 10; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (isInfestedBlock(level, pos)) {
                        BlockState state = level.getBlockState(pos);
                        if (!level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                            Block original = getOriginalBlockFromInfested(state);
                            level.setBlock(pos, original.defaultBlockState(), 3);
                        } else {
                            level.destroyBlock(pos, false, this);
                        }
                        spawnSilverfishFromBlock(level, pos, state);
                        if (this.random.nextInt(4) == 0) return;
                    }
                }
            }
        }
    }

    protected void spawnSilverfishFromBlock(Level level, BlockPos pos, BlockState state) {
        Entity silverfish;
        Block host = (state.getBlock() instanceof InfestedBlock infested) ? infested.getHostBlock() : Blocks.STONE;

        if (host == Blocks.COPPER_ORE) {
            silverfish = IFWEntityType.COPPERSPINE.get().create(level);
        }

        else {
            silverfish = EntityType.SILVERFISH.create(level);
        }

        if (silverfish != null) {
            silverfish.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(silverfish);
        }

    }

    @Override
    public boolean canSpawnSprintParticle() {
        return false; }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource ds) {
        return SoundEvents.SILVERFISH_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, net.minecraft.world.level.block.state.@NotNull BlockState state) {
        this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
    }

    public boolean isNormalSilverfish() {
        return this.getClass() == IFWSilverfish.class;
    }

    public boolean isNetherspawn() {
        return this instanceof NetherSilverFish;
    }

    public boolean isCopperspine() {
        return this instanceof Copperspine;
    }

    public boolean isHoarySilverfish() {
        return this.getClass() == HoarySilverfish.class;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        boolean flag = super.doHurtTarget(target);
        if (flag && target instanceof LivingEntity living) {
            if (this.isHoarySilverfish()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 5));
            } else if (this.isCopperspine()) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 480, 0));
            }
        }
        return flag;
    }

}