package huix.infinity.common.world.entity.monster.arachnid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class IFWPhaseSpider extends IFWWoodSpider {
    private static final EntityDataAccessor<Integer> MAX_EVASIONS = SynchedEntityData.defineId(IFWPhaseSpider.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CURRENT_EVASIONS = SynchedEntityData.defineId(IFWPhaseSpider.class, EntityDataSerializers.INT);

    private static final DustParticleOptions UNDERWORLD_PORTAL_DUST = new DustParticleOptions(
            new Vector3f(0.1F, 0.5F, 0.6F), // 深湖蓝绿色 RGB(25, 128, 153)
            1.0F // 粒子大小
    );

    private int evasionRegenTimer = 0;

    public IFWPhaseSpider(EntityType<? extends IFWWoodSpider> entityType, Level level) {
        super(entityType, level);

        if (!level.isClientSide) {
            int maxEvasions = 2 + level.getRandom().nextInt(3); // 2-4次闪避
            setMaxEvasions(maxEvasions);
            setCurrentEvasions(maxEvasions);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MAX_EVASIONS, 0);
        builder.define(CURRENT_EVASIONS, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return IFWWoodSpider.createAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected float getExperienceMultiplier() {
        return 2.0f; // 2倍经验值
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide && isAlive()) {
            int ticksExisted = tickCount;

            // 闪避次数恢复 - 每100tick恢复1次
            if (getCurrentEvasions() < getMaxEvasions() && ticksExisted % 100 == 0) {
                setCurrentEvasions(getCurrentEvasions() + 1);
            }

            // 主动传送AI - 每10tick有1/3概率传送
            if (getNavigation().getPath() != null &&
                    (getTarget() != null) &&
                    ticksExisted % 10 == 0 &&
                    getRandom().nextInt(3) == 0) {
                handleActivePhasing();
            }
        }
    }

    /**
     * 处理主动传送AI行为
     */
    private void handleActivePhasing() {
        PathNavigation navigation = getNavigation();
        if (navigation.getPath() != null && !navigation.getPath().isDone()) {
            // 尝试传送到路径上的某个点
            net.minecraft.world.level.pathfinder.Path path = navigation.getPath();
            int remainingNodes = path.getNodeCount() - path.getNextNodeIndex();

            if (remainingNodes > 1) {
                int advancement = Mth.clamp(getRandom().nextInt(remainingNodes), 1, 4);
                int targetIndex = path.getNextNodeIndex() + advancement;

                if (targetIndex < path.getNodeCount()) {
                    net.minecraft.world.level.pathfinder.Node node = path.getNode(targetIndex);
                    double targetX = node.x + 0.5;
                    double targetY = node.y;
                    double targetZ = node.z + 0.5;

                    if (distanceToSqr(targetX, targetY, targetZ) > 9.0) { // 距离超过3格
                        if (tryTeleportTo(targetX, targetY, targetZ)) {
                            // 传送成功，更新路径索引
                            path.advance();
                            for (int i = 0; i < advancement - 1 && !path.isDone(); i++) {
                                path.advance();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 尝试传送到指定位置
     */
    public boolean tryTeleportTo(double posX, double posY, double posZ) {
        if (!isAlive() || getHealth() <= 0) {
            return false;
        }

        int x = Mth.floor(posX);
        int y = Mth.floor(posY);
        int z = Mth.floor(posZ);

        if (y >= 1 && level().hasChunkAt(new BlockPos(x, y, z))) {
            // 向下寻找固体方块
            while (y >= 1) {
                y--;
                if (level().getBlockState(new BlockPos(x, y, z)).isSolidRender(level(), new BlockPos(x, y, z))) {
                    y++; // 在固体方块上方
                    break;
                }
                posY--;
            }

            if (y < 1) {
                return false;
            }

            BlockPos targetPos = new BlockPos(x, y, z);

            // 检查目标位置是否安全
            if (!level().getBlockState(targetPos).isSolidRender(level(), targetPos) &&
                    !level().getFluidState(targetPos).isEmpty() == false) {

                double deltaX = posX - getX();
                double deltaY = posY - getY();
                double deltaZ = posZ - getZ();

                AABB newBB = getBoundingBox().move(deltaX, deltaY, deltaZ);

                if (level().noCollision(this, newBB) && !level().containsAnyLiquid(newBB)) {
                    // 创建传送特效
                    createTeleportEffects(posX, posY, posZ);

                    // 执行传送
                    setPos(posX, posY, posZ);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 尝试传送远离指定实体
     */
    public boolean tryTeleportAwayFrom(Entity entity, double minDistance) {
        if (!isAlive() || getHealth() <= 0) {
            return false;
        }

        double minDistanceSq = minDistance * minDistance;
        int x = getBlockX();
        int y = getBlockY();
        int z = getBlockZ();

        double threatX = entity != null ? entity.getX() : getX();
        double threatZ = entity != null ? entity.getZ() : getZ();

        // 尝试64次随机传送
        for (int attempts = 0; attempts < 64; attempts++) {
            int dx = getRandom().nextInt(11) - 5;
            int dy = getRandom().nextInt(9) - 4;
            int dz = getRandom().nextInt(11) - 5;

            // 确保传送距离足够远
            if (Math.abs(dx) >= 3 || Math.abs(dz) >= 3) {
                int tryX = x + dx;
                int tryY = y + dy;
                int tryZ = z + dz;

                double tryPosX = tryX + 0.5;
                double tryPosZ = tryZ + 0.5;

                // 检查是否足够远离威胁
                double distSq = (tryPosX - threatX) * (tryPosX - threatX) +
                        (tryPosZ - threatZ) * (tryPosZ - threatZ);

                if (distSq >= minDistanceSq && tryY >= 1 &&
                        level().hasChunkAt(new BlockPos(tryX, tryY, tryZ))) {

                    // 向下寻找地面
                    while (tryY >= 1 && !level().getBlockState(new BlockPos(tryX, tryY, tryZ))
                            .isSolidRender(level(), new BlockPos(tryX, tryY, tryZ))) {
                        tryY--;
                    }

                    if (tryY >= 1) {
                        tryY++; // 在固体方块上方
                        BlockPos targetPos = new BlockPos(tryX, tryY, tryZ);

                        if (!level().getBlockState(targetPos).isSolidRender(level(), targetPos) &&
                                level().getFluidState(targetPos).isEmpty() &&
                                tryTeleportTo(tryPosX, tryY, tryPosZ)) {

                            // 传送成功后重新寻找目标
                            Player newTarget = level().getNearestPlayer(this, Math.min(getAttributeValue(Attributes.FOLLOW_RANGE), 24.0));
                            if (newTarget != null && newTarget != getTarget()) {
                                setTarget(newTarget);
                            }

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * 创建传送特效
     */
    private void createTeleportEffects(double targetX, double targetY, double targetZ) {
        if (level() instanceof ServerLevel serverLevel) {
            // 起始位置特效 - 使用墨绿色粒子
            serverLevel.sendParticles(UNDERWORLD_PORTAL_DUST,
                    getX(), getY() + 0.5, getZ(),
                    20, 0.5, 0.5, 0.5, 0.1);

            // 目标位置特效 - 使用深湖蓝色粒子
            serverLevel.sendParticles(UNDERWORLD_PORTAL_DUST,
                    targetX, targetY + 0.5, targetZ,
                    20, 0.5, 0.5, 0.5, 0.1);

            // 传送音效
            level().playSound(null, getX(), getY(), getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, getSoundSource(), 1.0F, 1.0F);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        // 检查是否可以闪避 - 使用更兼容的方式
        boolean canEvade = !isUnevadableDamage(damageSource);

        if (canEvade && getCurrentEvasions() > 0) {
            setCurrentEvasions(getCurrentEvasions() - 1);

            Entity attacker = damageSource.getEntity();
            if (attacker == null) {
                attacker = damageSource.getDirectEntity();
            }

            if (tryTeleportAwayFrom(attacker, 3.0)) {
                return false; // 成功闪避，不受伤害
            }
        }

        return super.hurt(damageSource, amount);
    }

    /**
     * 检查是否为无法闪避的伤害类型
     */
    private boolean isUnevadableDamage(DamageSource damageSource) {
        // 使用更兼容的方式检查伤害类型
        try {
            // 优先使用DamageTypeTags（如果可用）
            if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return true;
            }
        } catch (Exception e) {
            // 如果DamageTypeTags不可用，忽略此检查
        }

        // 使用DamageTypes检查
        try {
            return damageSource.is(DamageTypes.FALL) ||
                    damageSource.is(DamageTypes.ON_FIRE) ||
                    damageSource.is(DamageTypes.LAVA) ||
                    damageSource.is(DamageTypes.WITHER) ||
                    damageSource.is(DamageTypes.DROWN) ||
                    damageSource.is(DamageTypes.STARVE);
        } catch (Exception e) {
            // 如果某些DamageTypes不存在，使用字符串匹配作为备用方案
            String damageType = damageSource.getMsgId();
            return damageType.contains("fall") ||
                    damageType.contains("fire") ||
                    damageType.contains("lava") ||
                    damageType.contains("wither") ||
                    damageType.contains("poison") ||
                    damageType.contains("drown") ||
                    damageType.contains("starve");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("MaxEvasions", getMaxEvasions());
        compound.putInt("CurrentEvasions", getCurrentEvasions());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setMaxEvasions(compound.getInt("MaxEvasions"));
        setCurrentEvasions(compound.getInt("CurrentEvasions"));
    }

    // Getter/Setter 方法
    public int getMaxEvasions() {
        return this.entityData.get(MAX_EVASIONS);
    }

    public void setMaxEvasions(int maxEvasions) {
        this.entityData.set(MAX_EVASIONS, maxEvasions);
    }

    public int getCurrentEvasions() {
        return this.entityData.get(CURRENT_EVASIONS);
    }

    public void setCurrentEvasions(int currentEvasions) {
        this.entityData.set(CURRENT_EVASIONS, Math.max(0, Math.min(currentEvasions, getMaxEvasions())));
    }
}