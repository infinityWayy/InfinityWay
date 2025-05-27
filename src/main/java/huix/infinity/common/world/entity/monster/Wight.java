package huix.infinity.common.world.entity.monster;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * 亡灵 - 吸取玩家经验值的不死生物
 * 只攻击玩家，只能被附魔武器或银质武器伤害
 */
public class Wight extends IFWZombie {

    public Wight(EntityType<? extends Wight> entityType, Level level) {
        super(entityType, level);
    }

    public Wight(Level level) {
        this(IFWEntityType.WIGHT.get(), level);
    }

    @Override
    protected void addBehaviourGoals() {
        // 基础行为AI - 只攻击玩家
        this.goalSelector.addGoal(0, new FloatGoal(this)); // 游泳
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false)); // 攻击玩家
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // 目标选择AI - 只攻击玩家
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return IFWZombie.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.MAX_HEALTH, 20.0);
    }

    /**
     * 检查武器是否能对亡灵造成伤害
     * 只有附魔武器或银质武器才能伤害亡灵
     */
    private boolean canDamageWight(ItemStack weapon) {
        if (weapon.isEmpty()) {
            return false; // 空手无法伤害
        }
        // 检查是否在银质物品TAG中
        boolean isSilverItem = weapon.is(IFWItemTags.SILVER_ITEM);
        if (isSilverItem) {
            return true;
        }

        // 检查是否有附魔
        return weapon.isEnchanted();
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float damage) {
        // 检查伤害来源是否为玩家
        if (damageSource.getEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();
            // 如果武器不能伤害亡灵，则免疫伤害
            if (!canDamageWight(weapon)) {
                // 播放免疫音效
                this.playSound(SoundEvents.ZOMBIE_INFECT, 0.5F, 2.0F);
                return false;
            }
        }

        return super.hurt(damageSource, damage);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        boolean result = super.doHurtTarget(target);

        // 特性：对玩家进行经验值吸取
        if (result && target instanceof Player player) {
            // 40% 概率吸取玩家经验值
            if (this.random.nextFloat() < 0.4F) {
                int playerLevel = player.experienceLevel;
                int drainAmount = Math.max((playerLevel + 1) * 10, 20);

                // 计算实际吸取的经验值
                int actualDrain = getDrainAfterResistance(player, drainAmount);

                // 吸取经验值
                player.giveExperiencePoints(-actualDrain);

                // 播放音效反馈 - 让附近的玩家都能听到
                this.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        IFWSoundEvents.CLASSIC_HURT.get(),
                        net.minecraft.sounds.SoundSource.HOSTILE,
                        1.0F, 0.8F);
            }
        }

        return result;
    }

    /**
     * 计算经验值吸取抗性后的实际值
     */
    private int getDrainAfterResistance(Player player, int drainAmount) {
        return drainAmount;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return IFWSoundEvents.WIGHT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return IFWSoundEvents.WIGHT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IFWSoundEvents.WIGHT_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState block) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull net.minecraft.server.level.ServerLevel level,
                                       @NotNull DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 2;
    }

    @Override
    public void addRandomWeapon() {
    }

    @Override
    protected boolean supportsBreakDoorGoal() {
        return false;
    }

    @Override
    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        return false;
    }
}