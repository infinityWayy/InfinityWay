package huix.infinity.common.world.entity.monster;

import huix.infinity.common.world.entity.ai.SeekTorchGoal;
import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class InvisibleStalker extends Monster {

    public InvisibleStalker(EntityType<? extends InvisibleStalker> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, true)); // 攻击村民（原版行为）
        this.goalSelector.addGoal(4, new SeekTorchGoal(this, 1.0F)); // 寻找火把
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true)); // 攻击村民目标
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.MAX_HEALTH, 20.0);
    }

    @Override
    public void aiStep() {
        this.tryDisableNearbyLightSource();
        super.aiStep();
    }

    // 光源破坏方法
    public boolean tryDisableNearbyLightSource() {
        // 最近没有受到伤害，距离最近玩家超过4格
        if (this.level().isClientSide || this.hurtTime > 0 || this.distanceToNearestPlayer() <= 4.0) {
            return false;
        }

        Level level = this.level();
        int x = Mth.floor(this.getX());
        int y = Mth.floor(this.getY());
        int z = Mth.floor(this.getZ());

        // 搜索周围1格范围内的方块，高度包括实体高度
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1 + (int)this.getBbHeight(); dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = new BlockPos(x + dx, y + dy, z + dz);
                    BlockState blockState = level.getBlockState(pos);
                    Block block = blockState.getBlock();

                    // 检查是否是火把
                    if (block == Blocks.TORCH || block == Blocks.WALL_TORCH) {
                        if (level.removeBlock(pos, false)) {
                            // 掉落火把物品
                            ItemEntity itemEntity = new ItemEntity(level,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                    new ItemStack(Items.TORCH));
                            itemEntity.setPickUpDelay(10);
                            level.addFreshEntity(itemEntity);

                            // 播放音效
                            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,
                                    0.05F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            return true;
                        }
                    }
                    // 检查是否是点亮的南瓜灯，将其变为普通南瓜
                    else if (block == Blocks.JACK_O_LANTERN) {
                        if (level.setBlock(pos, Blocks.PUMPKIN.defaultBlockState(), 3)) {
                            // 掉落火把
                            ItemEntity itemEntity = new ItemEntity(level,
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                    new ItemStack(Items.TORCH));
                            itemEntity.setPickUpDelay(10);
                            level.addFreshEntity(itemEntity);

                            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,
                                    0.05F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    // 计算到最近玩家的距离
    private double distanceToNearestPlayer() {
        Player nearestPlayer = this.level().getNearestPlayer(this, 64.0);
        if (nearestPlayer != null) {
            return this.distanceTo(nearestPlayer);
        }
        return Double.MAX_VALUE;
    }

    protected boolean isAffectedByFluidPush() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return IFWSoundEvents.INVISIBLE_STALKER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return IFWSoundEvents.INVISIBLE_STALKER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IFWSoundEvents.INVISIBLE_STALKER_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 2;
    }
}