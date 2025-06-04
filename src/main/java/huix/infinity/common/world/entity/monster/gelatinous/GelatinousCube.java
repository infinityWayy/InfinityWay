package huix.infinity.common.world.entity.monster.gelatinous;

import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.common.world.item.IFWTieredItem;
import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class GelatinousCube extends Monster {
    private static final EntityDataAccessor<Boolean> DATA_IS_FEEDING =
            SynchedEntityData.defineId(GelatinousCube.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_SIZE =
            SynchedEntityData.defineId(GelatinousCube.class, EntityDataSerializers.INT);

    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    // 添加原版史莱姆的 squish 属性
    public float oSquish;
    public float squish;

    private boolean wasOnGround;
    private int blockFeedingCountdown;
    private int itemFeedingCountdown;
    private int ticksUntilNextFizzSound;
    protected int randomDamage = 0;
    protected int baseDamage = 0;

    // 修改：支持多个方块同时腐蚀，但有优化
    private final Map<BlockPos, Integer> corrosionProgress = new HashMap<>();
    private static final int BASE_CORROSION_TIME = 60; // 基础3秒（60 ticks）

    public GelatinousCube(EntityType<? extends GelatinousCube> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SlimeMoveControl(this);
        // 修复默认尺寸设置
        this.fixupDimensions();
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        RandomSource randomsource = level.getRandom();

        // 随机设置史莱姆尺寸，就像原版一样
        int size = randomsource.nextInt(3);
        if (size < 2 && randomsource.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) {
            size++;
        }

        int actualSize = 1 << size; // 1, 2, 4
        this.setSize(actualSize);
        this.refreshDimensions();

        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_FEEDING, false);
        builder.define(DATA_SIZE, 1);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SlimeAttackGoal(this));
        this.goalSelector.addGoal(3, new SlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new SlimeKeepOnJumpingGoal(this));

        this.targetSelector
                .addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, p_352812_ -> Math.abs(p_352812_.getY() - this.getY()) <= 4.0));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    public int getSize() {
        return this.entityData.get(DATA_SIZE);
    }

    public void setSize(int size) {
        if (size != 1 && size != 2 && size != 4) {
            size = 1;
        }

        this.entityData.set(DATA_SIZE, size);
        this.reapplyPosition();
        this.refreshDimensions();

        // 根据尺寸调整属性
        AttributeInstance maxHealthAttribute = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttribute != null) {
            maxHealthAttribute.setBaseValue(size * size);
        }

        AttributeInstance movementSpeedAttribute = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeedAttribute != null) {
            movementSpeedAttribute.setBaseValue(0.2 + 0.1 * Math.max(0, size - 1));
        }

        AttributeInstance attackDamageAttribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamageAttribute != null) {
            attackDamageAttribute.setBaseValue(size);
        }

        if (maxHealthAttribute != null) {
            this.setHealth(this.getMaxHealth());
        }

        this.xpReward = size;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_SIZE.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        return super.getDefaultDimensions(pose).scale((float)this.getSize());
    }

    @Override
    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    // ===== 喂食状态管理 =====
    protected void setIsFeeding(boolean isFeeding) {
        this.entityData.set(DATA_IS_FEEDING, isFeeding);
    }

    public boolean isFeeding() {
        return this.entityData.get(DATA_IS_FEEDING);
    }

    private void updateIsFeedingFlag() {
        this.setIsFeeding(this.blockFeedingCountdown > 0 || this.itemFeedingCountdown > 0);
    }

    public void setBlockFeedingCountdown(int countdown) {
        this.blockFeedingCountdown = Math.max(0, Math.min(countdown, 20));
        this.updateIsFeedingFlag();
    }

    public void setItemFeedingCountdown(int countdown) {
        this.itemFeedingCountdown = Math.max(0, Math.min(countdown, 20));
        this.updateIsFeedingFlag();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Size", this.getSize());
        compound.putBoolean("wasOnGround", this.wasOnGround);

        // 保存腐蚀进度
        CompoundTag corrosionData = new CompoundTag();
        for (Map.Entry<BlockPos, Integer> entry : this.corrosionProgress.entrySet()) {
            corrosionData.putInt(entry.getKey().toShortString(), entry.getValue());
        }
        compound.put("corrosionProgress", corrosionData);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        int size = compound.getInt("Size");
        if (size < 1) size = 1;
        this.setSize(size);
        this.refreshDimensions();

        this.wasOnGround = compound.getBoolean("wasOnGround");

        // 读取腐蚀进度
        this.corrosionProgress.clear();
        if (compound.contains("corrosionProgress")) {
            CompoundTag corrosionData = compound.getCompound("corrosionProgress");
            for (String key : corrosionData.getAllKeys()) {
                try {
                    String[] parts = key.split(",");
                    if (parts.length == 3) {
                        int x = Integer.parseInt(parts[0]);
                        int y = Integer.parseInt(parts[1]);
                        int z = Integer.parseInt(parts[2]);
                        BlockPos pos = new BlockPos(x, y, z);
                        int progress = corrosionData.getInt(key);
                        this.corrosionProgress.put(pos, progress);
                    }
                } catch (NumberFormatException e) {
                    // 忽略无效数据
                }
            }
        }
    }

    @Override
    public void tick() {
        // 更新挤压效果（类似原版史莱姆）
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;

        // 原版史莱姆的 squish 逻辑
        this.oSquish = this.squish;

        boolean isOnGround = this.onGround();
        if (this.isFeeding()) {
            this.squishAmount = -0.5F + (float) Math.sin((double) ((float) this.tickCount / 5.0F)) * 0.1F;
        } else {
            if (isOnGround && !this.wasOnGround) {
                this.squishAmount = -0.5F;
                this.squish = -0.5F;
                this.playSound(this.getSquishSound(), this.getSoundVolume(),
                        ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            } else if (!isOnGround && this.wasOnGround) {
                this.squishAmount = 1.0F;
                this.squish = 1.0F;
            }

            this.squishAmount *= 0.6F;
            this.squish *= 0.6F;
        }

        this.wasOnGround = isOnGround;

        super.tick();

        if (!this.level().isClientSide) {
            if (this.blockFeedingCountdown > 0) {
                --this.blockFeedingCountdown;
                this.updateIsFeedingFlag();
            }

            if (this.itemFeedingCountdown > 0) {
                --this.itemFeedingCountdown;
                this.updateIsFeedingFlag();
            }

            // 每5tick检测一次，保持合理的检测频率
            if (this.tickCount % 5 == 0) {
                if (this.canCorodeBlocks()) {
                    this.updateBlockCorrosionSystem();
                }
                this.updateItemDissolvingSystem();
            }
        }

        if (this.isInLava()) {
            this.handleLavaEffects();
        }
    }

    protected net.minecraft.sounds.SoundEvent getSquishSound() {
        return this.getSize() > 1 ? SoundEvents.SLIME_SQUISH : SoundEvents.SLIME_SQUISH_SMALL;
    }

    protected net.minecraft.sounds.SoundEvent getJumpSound() {
        return this.getSize() > 1 ? SoundEvents.SLIME_JUMP : SoundEvents.SLIME_JUMP_SMALL;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return this.getSize() > 1 ? SoundEvents.SLIME_HURT : SoundEvents.SLIME_HURT_SMALL;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getDeathSound() {
        return this.getSize() > 1 ? SoundEvents.SLIME_DEATH : SoundEvents.SLIME_DEATH_SMALL;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F * this.getSize();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean success = super.doHurtTarget(entity);

        if (success && entity instanceof LivingEntity) {
            if (this.isAcidic() && entity instanceof Player player) {
                this.applyAcidicDamage(player);
            }
        }

        return success;
    }

    private void applyAcidicDamage(Player player) {
        this.corrodeInventoryItems(player);
        this.corrodePlayerEquipment(player);
    }

    private void corrodeInventoryItems(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if (!stack.isEmpty() && this.canItemBeCorrodedByAcid(stack)) {
                float baseChance = 0.03F;
                float sizeBonus = this.getSize() * 0.02F;
                float attackBonus = this.getAttackStrengthMultiplier() * 0.01F;
                float totalChance = baseChance + sizeBonus + attackBonus;

                if (this.random.nextFloat() < totalChance) {
                    int baseDamage = 1;
                    int sizeDamage = Math.max(0, this.getSize() - 1);
                    int attackDamage = this.getAttackStrengthMultiplier();
                    int totalDamage = baseDamage + sizeDamage + attackDamage;

                    int actualDamage = Math.min(stack.getCount(), totalDamage);
                    stack.shrink(actualDamage);

                    this.playCorrosionEffects(player, actualDamage);
                    return;
                }
            }
        }
    }

    private void corrodePlayerEquipment(Player player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack item = player.getItemBySlot(slot);
            if (!item.isEmpty() && this.canEquipmentBeCorrodedByAcid(item)) {
                int baseDamage = 2;
                int sizeDamage = this.getSize();
                int attackDamage = this.getAttackStrengthMultiplier();
                int vulnerabilityMultiplier = this.calculateMaterialVulnerability(item);

                int totalDamage = (baseDamage + sizeDamage + attackDamage) * vulnerabilityMultiplier;
                item.hurtAndBreak(totalDamage, player, slot);
            }
        }
    }

    abstract boolean canItemBeCorrodedByAcid(ItemStack itemStack);

    private boolean canEquipmentBeCorrodedByAcid(ItemStack itemStack) {
        if (itemStack.getItem() instanceof IFWTieredItem) {
            return true;
        }
        return itemStack.is(Tags.Items.ARMORS);
    }

    private int calculateMaterialVulnerability(ItemStack itemStack) {
        if (itemStack.getItem() instanceof TieredItem tieredItem) {
            Tier tier = tieredItem.getTier();
            if (tier instanceof IFWTier ifwTier) {
                float acidResistance = ifwTier.acidResistance();

                if (acidResistance >= 4000) {
                    return 0;
                } else if (acidResistance >= 40) {
                    return 1;
                } else if (acidResistance >= 20) {
                    return 2;
                } else if (acidResistance >= 8) {
                    return 3;
                } else if (acidResistance >= 4) {
                    return 4;
                } else {
                    return 5;
                }
            }
        }

        return 3;
    }

    private void playCorrosionEffects(Player player, int damageAmount) {
        float volume = Math.min(1.0F, 0.3F + damageAmount * 0.1F);
        player.playSound(SoundEvents.FIRE_EXTINGUISH, volume, 1.5F);

        if (this.level() instanceof ServerLevel serverLevel) {
            int particleCount = Math.min(10, 3 + damageAmount);
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    particleCount, 0.3, 0.3, 0.3, 0.1);
        }
    }

    // ===== 方块腐蚀系统（优化多方块处理） =====

    private void updateBlockCorrosionSystem() {
        if (!this.canCorodeBlocks()) {
            return;
        }

        // 获取史莱姆的精确碰撞箱
        AABB slimeBoundingBox = this.getBoundingBox();

        // 使用稍微扩展的检测范围，但仍然基于碰撞箱
        double tolerance = 0.1;
        int minX = Mth.floor(slimeBoundingBox.minX - tolerance);
        int maxX = Mth.ceil(slimeBoundingBox.maxX + tolerance);
        int minY = Mth.floor(slimeBoundingBox.minY - tolerance);
        int maxY = Mth.ceil(slimeBoundingBox.maxY + tolerance);
        int minZ = Mth.floor(slimeBoundingBox.minZ - tolerance);
        int maxZ = Mth.ceil(slimeBoundingBox.maxZ + tolerance);

        List<BlockPos> immediateCorrosionBlocks = new ArrayList<>();
        List<BlockPos> timedCorrosionBlocks = new ArrayList<>();

        // 检查碰撞箱覆盖范围内的方块
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = this.level().getBlockState(pos);

                    if (!blockState.isAir() &&
                            (blockState.is(IFWBlockTags.ACID_DEGRADABLE) || blockState.is(IFWBlockTags.SLOW_CORROSION)))
                    {
                        // 创建方块的碰撞箱
                        AABB blockAABB = new AABB(pos);

                        // 检查史莱姆碰撞箱是否与方块碰撞箱相交或接触
                        if (this.isCollisionBoxTouching(slimeBoundingBox, blockAABB)) {
                            if (blockState.is(IFWBlockTags.SLOW_CORROSION)) {
                                timedCorrosionBlocks.add(pos);
                            } else {
                                immediateCorrosionBlocks.add(pos);
                            }
                        }
                    }
                }
            }
        }

        // 处理立即腐蚀的方块
        for (BlockPos pos : immediateCorrosionBlocks) {
            BlockState blockState = this.level().getBlockState(pos);
            this.corrodeBlock(pos, blockState);
        }

        // 处理需要时间腐蚀的方块（优化版本）
        boolean anyTimedCorrosion = false;
        if (!timedCorrosionBlocks.isEmpty()) {
            anyTimedCorrosion = this.handleTimedCorrosionBlocksOptimized(timedCorrosionBlocks);
        }

        // 清理不再接触的方块的腐蚀进度
        this.cleanupCorrosionProgress(timedCorrosionBlocks);

        // 设置喂食状态
        if (!immediateCorrosionBlocks.isEmpty() || anyTimedCorrosion) {
            this.setBlockFeedingCountdown(20);
        } else {
            this.setBlockFeedingCountdown(0);
        }
    }

    /**
     * 优化的时间腐蚀处理：当接触方块太多时，增加腐蚀速度
     */
    private boolean handleTimedCorrosionBlocksOptimized(List<BlockPos> timedCorrosionBlocks) {
        boolean anyProgress = false;

        // 计算腐蚀速度加成：接触的方块越多，每个方块腐蚀得越快
        int blockCount = timedCorrosionBlocks.size();
        int progressIncrement;

        if (blockCount <= 3) {
            progressIncrement = 1; // 正常速度
        } else if (blockCount <= 6) {
            progressIncrement = 2; // 2倍速度
        } else if (blockCount <= 10) {
            progressIncrement = 3; // 3倍速度
        } else {
            progressIncrement = 4; // 4倍速度，最多接触很多方块时
        }

        // 调试信息
        if (this.tickCount % 20 == 0 && blockCount > 0) {
            System.out.println("Corroding " + blockCount + " blocks simultaneously with " + progressIncrement + "x speed");
        }

        for (BlockPos pos : timedCorrosionBlocks) {
            BlockState blockState = this.level().getBlockState(pos);
            if (!blockState.isAir() && blockState.is(IFWBlockTags.SLOW_CORROSION)) {

                // 获取或初始化腐蚀进度
                int progress = this.corrosionProgress.getOrDefault(pos, 0);
                progress += progressIncrement; // 使用加速的进度增量
                this.corrosionProgress.put(pos, progress);
                anyProgress = true;

                // 计算实际需要的腐蚀时间（根据史莱姆大小调整）
                int actualCorrosionTime = Math.max(20, BASE_CORROSION_TIME / this.getSize());

                // 调试信息
                if (progress % 20 == 0) {
                    System.out.println("Corroding block " + blockState.getBlock().getName().getString() +
                            " at " + pos + ", progress: " + progress + "/" + actualCorrosionTime +
                            " (speed: " + progressIncrement + "x)");
                }

                // 播放腐蚀进行中的粒子效果
                if (progress % 10 == 0) {
                    this.spawnCorrosionProgressParticles(pos);
                }

                // 达到腐蚀时间后开始腐蚀
                if (progress >= actualCorrosionTime) {
                    System.out.println("Block " + blockState.getBlock().getName().getString() +
                            " at " + pos + " fully corroded!");
                    this.corrodeBlock(pos, blockState);
                    this.corrosionProgress.remove(pos);
                }
            }
        }

        return anyProgress;
    }

    /**
     * 改进的碰撞箱接触检测，包括边缘接触
     */
    private boolean isCollisionBoxTouching(AABB slimeBox, AABB blockBox) {
        // 使用更宽松的检测，允许边缘接触
        double tolerance = 0.001; // 很小的容差值

        return slimeBox.minX <= blockBox.maxX + tolerance &&
                slimeBox.maxX >= blockBox.minX - tolerance &&
                slimeBox.minY <= blockBox.maxY + tolerance &&
                slimeBox.maxY >= blockBox.minY - tolerance &&
                slimeBox.minZ <= blockBox.maxZ + tolerance &&
                slimeBox.maxZ >= blockBox.minZ - tolerance;
    }

    private void cleanupCorrosionProgress(List<BlockPos> currentTimedBlocks) {
        // 移除不再接触的方块的腐蚀进度
        List<BlockPos> toRemove = new ArrayList<>();
        for (BlockPos pos : this.corrosionProgress.keySet()) {
            if (!currentTimedBlocks.contains(pos)) {
                toRemove.add(pos);
            }
        }
        for (BlockPos pos : toRemove) {
            this.corrosionProgress.remove(pos);
        }
    }

    private void corrodeBlock(BlockPos pos, BlockState originalState) {
        if (!this.level().isClientSide) {

            if (originalState.is(IFWBlockTags.CORROSIVE_DIRT)) {
                // 转化成泥土
                this.level().setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);

                this.level().playSound(null, pos, SoundEvents.LAVA_EXTINGUISH,
                        this.getSoundSource(), 0.5F, 0.8F + this.random.nextFloat() * 0.4F);

                this.spawnCorrosionParticles(pos);
            } else {
                // 其他方块直接消失
                this.level().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                this.level().playSound(null, pos, SoundEvents.FIRE_EXTINGUISH,
                        this.getSoundSource(), 0.3F, 1.2F + this.random.nextFloat() * 0.4F);

                this.spawnDissolveParticles(pos);
            }
        }
    }

    private void spawnCorrosionParticles(BlockPos pos) {
        if (this.level() instanceof ServerLevel serverLevel) {
            SimpleParticleType particleType = this.isAcidic() ? ParticleTypes.SMOKE : ParticleTypes.CLOUD;

            serverLevel.sendParticles(particleType,
                    pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                    5, 0.3, 0.1, 0.3, 0.02);
        }
    }

    private void spawnCorrosionProgressParticles(BlockPos pos) {
        if (this.level() instanceof ServerLevel serverLevel) {
            SimpleParticleType particleType = this.isAcidic() ? ParticleTypes.SMOKE : ParticleTypes.POOF;

            serverLevel.sendParticles(particleType,
                    pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                    2, 0.2, 0.1, 0.2, 0.01);
        }
    }

    private void spawnDissolveParticles(BlockPos pos) {
        if (this.level() instanceof ServerLevel serverLevel) {
            SimpleParticleType particleType = this.isAcidic() ? ParticleTypes.SMOKE : ParticleTypes.POOF;

            // 较少的粒子效果
            serverLevel.sendParticles(particleType,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    3, 0.2, 0.2, 0.2, 0.05);
        }
    }

    private void updateItemDissolvingSystem() {
        List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox());
        boolean refreshCounter = false;

        for (ItemEntity item : items) {
            if (!item.isRemoved() && this.canItemBeCorrodedByAcid(item.getItem())) {
                if (item.hasPickUpDelay() && item.getAge() < 60) {
                    item.setPickUpDelay(60);
                }

                this.handleSpecialItems(item);

                if (this.damageItem(item)) {
                    refreshCounter = true;
                }
            }
        }

        this.setItemFeedingCountdown(refreshCounter ? 20 : 0);
    }

    private void handleLavaEffects() {
        if (this.level().isClientSide) {
            for (int i = 0; i < 5; ++i) {
                this.level().addParticle(ParticleTypes.LARGE_SMOKE,
                        this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0),
                        0.0, 0.0, 0.0);
            }
        } else if (--this.ticksUntilNextFizzSound <= 0) {
            this.playSound(SoundEvents.LAVA_EXTINGUISH, 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            this.ticksUntilNextFizzSound = this.random.nextInt(7) + 2;
        }
    }

    protected void handleSpecialItems(ItemEntity item) {
        // 子类可以重写此方法来处理特殊物品
    }

    // ===== AI 类 =====

    static class SlimeAttackGoal extends Goal {
        private final GelatinousCube slime;
        private int growTiredTimer;

        public SlimeAttackGoal(GelatinousCube slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.slime.getTarget();
            return target != null && target.isAlive() && this.slime.canAttack(target);
        }

        @Override
        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            super.start();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.slime.getTarget();
            return target != null && target.isAlive() && --this.growTiredTimer > 0;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.slime.getTarget();
            if (target != null) {
                this.slime.lookAt(target, 10.0F, 10.0F);
            }

            ((SlimeMoveControl) this.slime.getMoveControl()).setDirection(this.slime.getYRot(), true);
        }
    }

    static class SlimeMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final GelatinousCube slime;
        private boolean isAggressive;

        public SlimeMoveControl(GelatinousCube slime) {
            super(slime);
            this.slime = slime;
            this.yRot = 180.0F * slime.getYRot() / (float) Math.PI;
        }

        public void setDirection(float yRot, boolean aggressive) {
            this.yRot = yRot;
            this.isAggressive = aggressive;
        }

        public void setWantedMovement(double speed) {
            this.speedModifier = speed;
            this.operation = Operation.MOVE_TO;
        }

        @Override
        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();

            if (this.operation != Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.slime.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.slime.getJumpControl().jump();
                        this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(),
                                this.slime.getVoicePitch());
                    } else {
                        this.slime.xxa = 0.0F;
                        this.slime.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }

    static class SlimeRandomDirectionGoal extends Goal {
        private final GelatinousCube slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public SlimeRandomDirectionGoal(GelatinousCube slime) {
            this.slime = slime;
            this.setFlags(java.util.EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.slime.getTarget() == null &&
                    (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava());
        }

        @Override
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.slime.getRandom().nextInt(60);
                this.chosenDegrees = this.slime.getRandom().nextFloat() * 360.0F;
            }

            ((SlimeMoveControl) this.slime.getMoveControl()).setDirection(this.chosenDegrees, false);
        }
    }

    static class SlimeKeepOnJumpingGoal extends Goal {
        private final GelatinousCube slime;

        public SlimeKeepOnJumpingGoal(GelatinousCube slime) {
            this.slime = slime;
            this.setFlags(java.util.EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.slime.isPassenger();
        }

        @Override
        public void tick() {
            ((SlimeMoveControl) this.slime.getMoveControl()).setWantedMovement(1.0);
        }
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    public boolean damageItem(ItemEntity item) {
        if (baseDamage + randomDamage == 0) return false;
        ItemStack stack = item.getItem();
        int random = randomDamage == 0? 0: this.random.nextInt(randomDamage);
        int damage = random + baseDamage;
        stack.shrink(damage);
        if (stack.isEmpty()) {
            item.discard();
        }
        return true;
    }

    // ===== 抽象方法 =====

    public abstract boolean canCorodeBlocks();
    public abstract boolean isAcidic();
    public abstract int getAttackStrengthMultiplier();

    public int getExperienceReward() {
        return this.getSize() * (this.getAttackStrengthMultiplier() + (this.isAcidic() ? 1 : 0));
    }

    @Override
    public boolean fireImmune() {
        return false;
    }

    @Override
    public boolean canBeAffected(@NotNull MobEffectInstance effectInstance) {
        return super.canBeAffected(effectInstance);
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        if (!this.level().isClientSide && this.getSize() > 1) {
            this.spawnSmallerSlimes();
        }
        super.die(damageSource);
    }

    private void spawnSmallerSlimes() {
        int currentSize = this.getSize();
        if (currentSize <= 1) return;

        int newSize = currentSize == 4 ? 2 : 1;
        int count = 2 + this.random.nextInt(3);

        for (int i = 0; i < count; i++) {
            GelatinousCube smallSlime = this.createSmallerSlime(newSize);
            if (smallSlime != null) {
                double x = this.getX() + (this.random.nextDouble() - 0.5) * 2.0;
                double z = this.getZ() + (this.random.nextDouble() - 0.5) * 2.0;
                smallSlime.moveTo(x, this.getY(), z, this.random.nextFloat() * 360.0F, 0.0F);
                this.level().addFreshEntity(smallSlime);
            }
        }
    }

    protected abstract GelatinousCube createSmallerSlime(int size);

    protected void dropCustomDeathLoot(@NotNull ServerLevel serverLevel, @NotNull DamageSource damageSource,
                                       boolean wasRecentlyHit) {
        super.dropCustomDeathLoot(serverLevel, damageSource, wasRecentlyHit);

        int ballCount = this.getSize() + this.random.nextInt(this.getSize() + 1);

        ItemStack ballDrop = this.getSlimeBallDrop();
        if (!ballDrop.isEmpty()) {
            this.spawnAtLocation(ballDrop.getItem(), ballCount);
        }
    }

    protected abstract ItemStack getSlimeBallDrop();
}