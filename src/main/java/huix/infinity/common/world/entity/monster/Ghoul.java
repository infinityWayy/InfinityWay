package huix.infinity.common.world.entity.monster;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.mob.Livestock;
import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class Ghoul extends IFWZombie {

    private int pickupCooldown = 0;
    private static final int PICKUP_COOLDOWN_TICKS = 400; // 20秒 = 20 * 20 ticks

    public Ghoul(EntityType<? extends Ghoul> entityType, Level level) {
        super(entityType, level);
    }

    public Ghoul(Level level) {
        this(IFWEntityType.GHOUL.get(), level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.pickupCooldown > 0) {
            this.pickupCooldown--;
        }
    }

    @Override
    protected void addBehaviourGoals() {

        // 基础行为AI
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, true)); // 攻击村民
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // 寻找食物的行为
        this.goalSelector.addGoal(1, new MoveToItemGoal(this, 1.0F, true));

        // 目标选择AI
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Livestock.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return IFWZombie.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28F)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result && target instanceof LivingEntity livingTarget) {
            // 造成缓慢效果
            livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 5));
        }
        return result;
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        // 如果在冷却中，不拾取任何物品
        if (this.pickupCooldown > 0) {
            return false;
        }

        // 只允许拾取食物和腐肉
        return isFoodItem(stack) || stack.is(Items.ROTTEN_FLESH);
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public void pickUpItem(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();

        // 只处理食物和腐肉，并且不在冷却中
        if ((isFoodItem(stack) || stack.is(Items.ROTTEN_FLESH)) && this.pickupCooldown <= 0) {
            // 恢复10点生命值
            this.heal(10.0F);

            // 播放吃东西的音效
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

            // 设置冷却时间（20秒）
            this.pickupCooldown = PICKUP_COOLDOWN_TICKS;

            // 移除物品（消耗掉）
            itemEntity.discard();
        }
        // 对于其他物品或在冷却中的情况，不做任何处理
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return IFWSoundEvents.GHOUL_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return IFWSoundEvents.GHOUL_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IFWSoundEvents.GHOUL_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState block) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    // 食物检测功能
    public boolean isFoodItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return false;
        }
        // 检测肉类物品
        return itemStack.is(Items.BEEF) ||
                itemStack.is(Items.PORKCHOP) ||
                itemStack.is(Items.CHICKEN) ||
                itemStack.is(Items.MUTTON) ||
                itemStack.is(Items.RABBIT) ||
                itemStack.is(Items.COOKED_BEEF) ||
                itemStack.is(Items.COOKED_PORKCHOP) ||
                itemStack.is(Items.COOKED_CHICKEN) ||
                itemStack.is(Items.COOKED_MUTTON) ||
                itemStack.is(Items.COOKED_RABBIT);
    }

    // 检测是否捕食某个实体
    protected boolean preysUpon(Entity entity) {
        return entity instanceof Animal || entity instanceof Livestock;
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 2;
    }

    @Override
    public void addRandomWeapon() {
        // 食尸鬼不需要武器，空实现
    }

    @Override
    protected boolean supportsBreakDoorGoal() {
        return true;
    }

    // 保存和读取冷却时间到NBT
    @Override
    public void addAdditionalSaveData(@NotNull net.minecraft.nbt.CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("PickupCooldown", this.pickupCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull net.minecraft.nbt.CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.pickupCooldown = compound.getInt("PickupCooldown");
    }

    // 自定义的移动到物品目标类
    public static class MoveToItemGoal extends Goal {
        private final Ghoul ghoul;
        private final double speedModifier;
        private final boolean requireLineOfSight;

        public MoveToItemGoal(Ghoul ghoul, double speedModifier, boolean requireLineOfSight) {
            this.ghoul = ghoul;
            this.speedModifier = speedModifier;
            this.requireLineOfSight = requireLineOfSight;
        }

        @Override
        public boolean canUse() {
            // 检查附近是否有食物物品
            return !this.ghoul.level().getEntitiesOfClass(
                    net.minecraft.world.entity.item.ItemEntity.class,
                    this.ghoul.getBoundingBox().inflate(10.0),
                    itemEntity -> this.ghoul.isFoodItem(itemEntity.getItem())
            ).isEmpty();
        }

        @Override
        public void tick() {
            // 寻找最近的食物物品并移动过去
            var nearbyItems = this.ghoul.level().getEntitiesOfClass(
                    net.minecraft.world.entity.item.ItemEntity.class,
                    this.ghoul.getBoundingBox().inflate(10.0),
                    itemEntity -> this.ghoul.isFoodItem(itemEntity.getItem())
            );

            if (!nearbyItems.isEmpty()) {
                var target = nearbyItems.getFirst();
                this.ghoul.getNavigation().moveTo(target, this.speedModifier);
            }
        }
    }
}