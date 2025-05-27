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
        this.goalSelector.addGoal(3, new SeekTorchGoal(this, 1.0F));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
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
        // 尝试破坏光源
        this.tryDisableNearbyLightSource();
        super.aiStep();
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

        // 1格范围 + 实体高度
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
     * 生成掉落物
     */
    private void spawnLightSourceDrop(Level level, BlockPos pos, net.minecraft.world.item.Item item) {
        ItemEntity itemEntity = new ItemEntity(level,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                new ItemStack(item));
        itemEntity.setPickUpDelay(10); // 对应原版的 delayBeforeCanPickup = 10
        level.addFreshEntity(itemEntity);
    }

    /**
     * 播放破坏音效 - 只针对火把和南瓜灯
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