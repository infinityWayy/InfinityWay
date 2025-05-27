package huix.infinity.common.world.entity.monster;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.ai.SeekTorchGoal;
import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * 暗影
 * 特殊能力：
 * - 受到阳光照射立刻死亡
 * - 黑暗中治疗
 * - 攻击造成视觉变暗和虚弱效果
 * - 免疫火焰和熔岩伤害
 * - 寻找并破坏光源
 * - 只能被银质武器、附魔武器或阳光伤害
 */
public class Shadow extends IFWZombie {

    public Shadow(EntityType<? extends Shadow> entityType, Level level) {
        super(entityType, level);
    }

    public Shadow(Level level) {
        this(IFWEntityType.SHADOW.get(), level);
    }

    @Override
    protected void addBehaviourGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new SeekTorchGoal(this, 1.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return IFWZombie.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    /**
     * 伤害免疫检查 - 只有银质、附魔或阳光伤害有效
     */
    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {

        if (damageSource.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }

        // 火焰和熔岩伤害免疫
        if (damageSource.is(net.minecraft.tags.DamageTypeTags.IS_FIRE)) {
            return true;
        }

        // 检查是否为玩家攻击
        if (damageSource.getEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();
            // 只有银质武器或附魔武器能造成伤害
            if (!canDamageShadow(weapon)) {
                return true;
            }
        }

        return super.isInvulnerableTo(damageSource);
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide) {
            // 阳光检测 - 立刻死亡
            if (this.isSunBurnTick()) {
                this.hurt(this.damageSources().generic(), 1000.0F);
            }
            // 黑暗治疗 - 每 40 tick检查一次
            else if (this.tickCount % 40 == 0) {
                float brightness = this.level().getBrightness(LightLayer.BLOCK, this.blockPosition());
                int healAmount = (int)((0.4F - brightness) * 10.0F);
                if (healAmount > 0) {
                    this.heal((float)healAmount);
                }
            }
        }

        // 尝试破坏光源
        this.tryDisableNearbyLightSource();
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        boolean result = super.doHurtTarget(target);

        if (result) {
            // 对玩家造成视觉变暗效果
            if (target instanceof Player player) {
                // 添加失明效果模拟视觉变暗
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0)); // 如果有黑暗效果
            }

            // 对所有生物造成虚弱效果
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 0));
            }
        }

        return result;
    }

    /**
     * 破坏光源
     */
    public boolean tryDisableNearbyLightSource() {
        if (this.level().isClientSide || this.hurtTime > 0 || this.distanceToNearestPlayer() <= 4.0) {
            return false;
        }

        Level level = this.level();
        int x = Mth.floor(this.getX());
        int y = Mth.floor(this.getY());
        int z = Mth.floor(this.getZ());

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1 + (int)this.getBbHeight(); dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = new BlockPos(x + dx, y + dy, z + dz);
                    BlockState blockState = level.getBlockState(pos);
                    Block block = blockState.getBlock();

                    if (block == Blocks.TORCH || block == Blocks.WALL_TORCH) {
                        if (level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())) {
                            Block.dropResources(blockState, level, pos, null, this, ItemStack.EMPTY);
                            playBreakSound(level, pos, block);
                            return true;
                        }
                    }
                    else if (block == Blocks.JACK_O_LANTERN) {
                        if (level.setBlockAndUpdate(pos, Blocks.PUMPKIN.defaultBlockState())) {
                            spawnLightSourceDrop(level, pos, Items.TORCH);
                            playBreakSound(level, pos, block);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * 检查武器是否能对暗影造成伤害
     */
    private boolean canDamageShadow(ItemStack weapon) {
        if (weapon.isEmpty()) {
            return false;
        }

        // 检查是否在银质物品TAG中
        if (weapon.is(IFWItemTags.SILVER_ITEM)) {
            return true;
        }

        // 检查是否有附魔
        return weapon.isEnchanted();
    }

    /**
     * 计算到最近玩家的距离
     */
    private double distanceToNearestPlayer() {
        Player nearestPlayer = this.level().getNearestPlayer(this, 64.0);
        if (nearestPlayer != null) {
            return this.distanceTo(nearestPlayer);
        }
        return Double.MAX_VALUE;
    }

    /**
     * 生成掉落物
     */
    private void spawnLightSourceDrop(Level level, BlockPos pos, net.minecraft.world.item.Item item) {
        ItemEntity itemEntity = new ItemEntity(level,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                new ItemStack(item));
        itemEntity.setPickUpDelay(10);
        level.addFreshEntity(itemEntity);
    }

    /**
     * 播放破坏音效
     */
    private void playBreakSound(Level level, BlockPos pos, Block brokenBlock) {
        SoundEvent sound;
        float volume = 1.0F;
        float pitch = 0.8F + this.random.nextFloat() * 0.4F;

        if (brokenBlock == Blocks.TORCH || brokenBlock == Blocks.WALL_TORCH) {
            sound = SoundEvents.WOOD_BREAK;
        } else if (brokenBlock == Blocks.JACK_O_LANTERN) {
            sound = SoundEvents.PUMPKIN_CARVE;
        } else {
            return;
        }

        level.playSound(null, pos, sound, SoundSource.BLOCKS, volume, pitch);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return IFWSoundEvents.SHADOW_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return IFWSoundEvents.SHADOW_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IFWSoundEvents.SHADOW_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState block) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 2;
    }

    @Override
    public void addRandomWeapon() {
        // 暗影不使用武器
    }

    @Override
    protected boolean supportsBreakDoorGoal() {
        return false;
    }

    @Override
    public boolean canBreakDoors() {
        return false;
    }

    @Override
    public void setCanBreakDoors(boolean canBreakDoors) {
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