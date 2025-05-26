package huix.infinity.common.world.entity.monster.digger;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.animal.Livestock;
import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.event.IFWSoundEvents;
import huix.infinity.util.WorldHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Revenant extends IFWZombie {
    private int eatAnimationTick;

    public Revenant(EntityType<? extends Revenant> entityType, Level level) {
        super(entityType, level);
        this.setCanBreakDoors(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.26)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                .add(Attributes.MAX_HEALTH, 30.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new RevenantAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::canBreakDoors));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(IFWZombie.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Livestock.class, 10, true, false, (entity) -> !(entity instanceof IFWZombie)));
    }

    /**
     * 亡魂永远不能是婴儿
     */
    @Override
    public void setBaby(boolean baby) {
        // 强制设置为成年，忽略传入的参数
        super.setBaby(false);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // 客户端暗影粒子光环特效
        if (this.level().isClientSide && this.isAlive()) {
            spawnShadowAura();
        }
    }

    /**
     * 生成围绕亡魂的暗影粒子光环
     */
    private void spawnShadowAura() {
        if (this.tickCount % 3 != 0) return;

        double radius = 1.2;
        int particleCount = 3;

        for (int i = 0; i < particleCount; i++) {
            double angle = (this.tickCount * 0.1 + i * (2 * Math.PI / particleCount));
            double x = this.getX() + Math.cos(angle) * radius;
            double z = this.getZ() + Math.sin(angle) * radius;
            double y = this.getY() + 0.5 + Math.sin(this.tickCount * 0.05 + i) * 0.3;

            this.level().addParticle(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    x, y, z,
                    0, 0.02, 0
            );

            if (this.random.nextInt(8) == 0) {
                this.level().addParticle(
                        ParticleTypes.SMOKE,
                        x + (this.random.nextDouble() - 0.5) * 0.5,
                        y - 0.2,
                        z + (this.random.nextDouble() - 0.5) * 0.5,
                        0, 0.01, 0
                );
            }
        }

        if (this.random.nextInt(15) == 0) {
            this.level().addParticle(
                    ParticleTypes.SOUL,
                    this.getX() + (this.random.nextDouble() - 0.5) * 0.8,
                    this.getY() + 0.1,
                    this.getZ() + (this.random.nextDouble() - 0.5) * 0.8,
                    (this.random.nextDouble() - 0.5) * 0.02,
                    0.05,
                    (this.random.nextDouble() - 0.5) * 0.02
            );
        }
    }

    /**
     * 重写addRandomWeapon方法，实现渐进式武器解锁
     */
    @Override
    public void addRandomWeapon() {
        // 亡魂有更高的武器装备概率
        float chance = this.getRandom().nextFloat();
        if (chance < 0.85f) { // 85%概率装备武器（比普通僵尸高）
            List<WeightedEntry.Wrapper<Item>> weapons = new ArrayList<>();

            weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_sword.asItem(), 1));

            long day = WorldHelper.getDay(this.level());

            // 第10天解锁：战斧类武器
            if (day >= 10) {
                weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_battle_axe.asItem(), 1));
            }

            // 第20天解锁：重型武器
            if (day >= 20) {
                weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_war_hammer.asItem(), 1));
            }

            Optional<WeightedEntry.Wrapper<Item>> selected = WeightedRandom.getRandomItem(this.getRandom(), weapons);
            if (selected.isPresent()) {
                ItemStack stack = new ItemStack(selected.get().data());

                // 添加随机损坏度，体现"锈蚀"效果
                if (stack.isDamageableItem()) {
                    stack.setDamageValue((int)(stack.getMaxDamage() * this.getRandom().nextFloat() * 0.25f));
                }

                this.setItemSlot(EquipmentSlot.MAINHAND, stack);
            }
        }
    }

    /**
     * 装备完整的锈铁套装
     */
    protected void addRandomArmor() {
        RandomSource random = this.getRandom();

        // 锈铁头盔 - 100%概率
        ItemStack helmet = new ItemStack(IFWItems.rusted_iron_helmet.asItem());
        if (helmet.isDamageableItem()) {
            helmet.setDamageValue((int)(helmet.getMaxDamage() * random.nextFloat() * 0.3f));
        }
        this.setItemSlot(EquipmentSlot.HEAD, helmet);

        // 锈铁胸甲 - 100%概率
        ItemStack chestplate = new ItemStack(IFWItems.rusted_iron_chestplate.asItem());
        if (chestplate.isDamageableItem()) {
            chestplate.setDamageValue((int)(chestplate.getMaxDamage() * random.nextFloat() * 0.3f));
        }
        this.setItemSlot(EquipmentSlot.CHEST, chestplate);

        // 锈铁护腿 - 100%概率
        ItemStack leggings = new ItemStack(IFWItems.rusted_iron_leggings.asItem());
        if (leggings.isDamageableItem()) {
            leggings.setDamageValue((int)(leggings.getMaxDamage() * random.nextFloat() * 0.3f));
        }
        this.setItemSlot(EquipmentSlot.LEGS, leggings);

        // 锈铁靴子 - 100%概率
        ItemStack boots = new ItemStack(IFWItems.rusted_iron_boots.asItem());
        if (boots.isDamageableItem()) {
            boots.setDamageValue((int)(boots.getMaxDamage() * random.nextFloat() * 0.3f));
        }
        this.setItemSlot(EquipmentSlot.FEET, boots);
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource random, @NotNull DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);

        this.addRandomWeapon();
        this.addRandomArmor();
    }

    /**
     * 重写经验值，亡魂给予3倍经验
     */
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 3;
    }

    /**
     * 亡魂确认方法
     */
    public boolean isRevenant() {
        return true;
    }

    /**
     * 亡魂不能是村民
     */
    public boolean isVillager() {
        return false;
    }

    @Override
    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return IFWSoundEvents.REVENANT_AMBIENT.get();
    }

    @Override
    protected float getSoundVolume() {
        return 1.5F;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level,
                                                  @NotNull DifficultyInstance difficulty,
                                                  @NotNull MobSpawnType spawnType,
                                                  @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        this.populateDefaultEquipmentEnchantments(level, randomsource, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.eatAnimationTick = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    public boolean canBreakDoors() {
        return true;
    }

    @Override
    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

    /**
     * 自定义亡魂攻击目标
     */
    static class RevenantAttackGoal extends MeleeAttackGoal {
        private final Revenant revenant;
        private int raiseArmTicks;

        public RevenantAttackGoal(Revenant revenant, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(revenant, speedModifier, followingTargetEvenIfNotSeen);
            this.revenant = revenant;
        }

        @Override
        public void start() {
            super.start();
            this.raiseArmTicks = 0;
        }

        @Override
        public void tick() {
            super.tick();
            this.raiseArmTicks++;

            if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
                this.revenant.setAggressive(true);

                if (this.revenant.level().isClientSide && this.revenant.random.nextInt(5) == 0) {
                    this.revenant.level().addParticle(
                            ParticleTypes.SOUL_FIRE_FLAME,
                            this.revenant.getX() + (this.revenant.random.nextDouble() - 0.5),
                            this.revenant.getY() + 1.0,
                            this.revenant.getZ() + (this.revenant.random.nextDouble() - 0.5),
                            0, 0.1, 0
                    );
                }
            } else {
                this.revenant.setAggressive(false);
            }
        }

        @Override
        public void stop() {
            super.stop();
            this.revenant.setAggressive(false);
        }

        @Override
        protected void checkAndPerformAttack(@NotNull LivingEntity target) {
            if (this.canPerformAttack(target)) {
                this.resetAttackCooldown();
                this.revenant.swing(this.revenant.getUsedItemHand());
                this.revenant.doHurtTarget(target);

                if (this.revenant.level().isClientSide) {
                    for (int i = 0; i < 5; i++) {
                        this.revenant.level().addParticle(
                                ParticleTypes.SOUL,
                                target.getX() + (this.revenant.random.nextDouble() - 0.5),
                                target.getY() + this.revenant.random.nextDouble() * 2,
                                target.getZ() + (this.revenant.random.nextDouble() - 0.5),
                                (this.revenant.random.nextDouble() - 0.5) * 0.1,
                                0.1,
                                (this.revenant.random.nextDouble() - 0.5) * 0.1
                        );
                    }
                }
            }
        }
    }
}