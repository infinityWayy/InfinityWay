package huix.infinity.common.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.*;

public abstract class IFWWebProjectile extends Projectile {
    private static final EntityDataAccessor<Boolean> IS_BURNING = SynchedEntityData.defineId(IFWWebProjectile.class, EntityDataSerializers.BOOLEAN);

    public IFWWebProjectile(EntityType<? extends IFWWebProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public IFWWebProjectile(EntityType<? extends IFWWebProjectile> entityType, LivingEntity shooter, Level level) {
        super(entityType, level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

        builder.define(IS_BURNING, false);
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS) {
            this.onHit(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        // 应用水阻力和重力
        float resistance = this.isInWater() ? 0.8F : 0.99F;
        this.setDeltaMovement(vec3.scale(resistance));
        this.applyGravity();
        this.setPos(d0, d1, d2);
    }

    protected void applyGravity() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -this.getDefaultGravity(), 0.0));
        }
    }

    protected double getDefaultGravity() {
        return 0.03; // 类似箭矢的重力
    }

    protected void onHit(HitResult hitResult) {
        if (!this.level().isClientSide) {
            if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) hitResult;
                Entity target = entityHit.getEntity();

                if (target instanceof LivingEntity livingTarget) {
                    if (onImpactLivingEntity(livingTarget, isBurning())) {
                        this.discard();
                    }
                }
            } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hitResult;
                onImpactBlock(blockHit);
                this.discard();
            }
        }
    }

    /**
     * 检查是否在燃烧
     */
    public boolean isBurning() {
        return this.entityData.get(IS_BURNING);
    }

    /**
     * 设置燃烧状态
     */
    public void setBurning(boolean burning) {
        this.entityData.set(IS_BURNING, burning);
    }

    @Override
    public void setRemainingFireTicks(int ticks) {
        super.setRemainingFireTicks(ticks);
        setBurning(ticks > 0);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsBurning", isBurning());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setBurning(compound.getBoolean("IsBurning"));
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    public boolean ignoreExplosion() {
        return false;
    }

    public static boolean onImpactLivingEntity(LivingEntity target, boolean burning) {
        if (burning) {
            target.setRemainingFireTicks(100); // 5秒燃烧
        }

        Level level = target.level();
        BlockPos pos = target.blockPosition();

        // 预判位置（4tick提前量）
        int lead = 4;
        Vec3 velocity = target.getDeltaMovement();
        Vec3 predictedPos = target.position().add(velocity.scale(lead));

        BlockPos predictedBlockPos = BlockPos.containing(
                predictedPos.x,
                pos.getY(),
                predictedPos.z
        );

        // 优先在预判位置放置蛛网
        if (!predictedBlockPos.equals(pos) && setBlockToWebIfEmpty(level, predictedBlockPos, burning)) {
            return true;
        }

        // 在当前位置放置蛛网
        if (setBlockToWebIfEmpty(level, pos, burning)) {
            return true;
        }

        // 在目标的碰撞箱范围内尝试放置蛛网
        AABB boundingBox = target.getBoundingBox();
        BlockPos minPos = BlockPos.containing(
                boundingBox.minX,
                boundingBox.minY,
                boundingBox.minZ
        );
        BlockPos maxPos = BlockPos.containing(
                boundingBox.maxX,
                boundingBox.maxY,
                boundingBox.maxZ
        );

        for (BlockPos testPos : BlockPos.betweenClosed(minPos, maxPos)) {
            if (setBlockToWebIfEmpty(level, testPos, burning)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 方块命中处理
     */
    private void onImpactBlock(BlockHitResult blockHit) {
        Level level = this.level();
        BlockPos hitPos = blockHit.getBlockPos();
        BlockState hitState = level.getBlockState(hitPos);

        // 检查是否命中火焰或熔岩
        if (hitState.is(Blocks.FIRE) || hitState.is(Blocks.LAVA)) {
            // 在熔岩或火焰中销毁
            this.playSound(SoundEvents.GENERIC_BURN, 0.5F, 2.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.8F);
            return;
        }

        // 检查是否命中水
        if (hitState.is(Blocks.WATER) ||
                level.getFluidState(hitPos).is(Fluids.WATER)) {
            // 在水中销毁，播放水花音效
            this.playSound(SoundEvents.GENERIC_SPLASH, 0.5F, 2.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.8F);
            return;
        }

        // 尝试在命中位置放置蛛网
        BlockPos targetPos = hitPos;
        if (!canReplaceBlockAt(level, targetPos)) {
            // 如果命中位置不能放置，尝试邻近位置
            targetPos = hitPos.relative(blockHit.getDirection());
        }

        if (!setBlockToWebIfEmpty(level, targetPos, isBurning())) {
            // 如果还是不能放置，尝试上方一格
            setBlockToWebIfEmpty(level, targetPos.above(), isBurning());
        }
    }

    /**
     * 检查位置是否可以被替换
     */
    private static boolean canReplaceBlockAt(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        // 空气可以替换
        if (state.isAir()) {
            return true;
        }

        // 薄雪可以替换
        if (state.is(Blocks.SNOW) && state.getValue(net.minecraft.world.level.block.SnowLayerBlock.LAYERS) == 1) {
            return true;
        }

        // 可替换的方块
        return state.canBeReplaced();
    }

    /**
     * 在指定位置放置蛛网（如果可能）
     */
    private static boolean setBlockToWebIfEmpty(Level level, BlockPos pos, boolean burning) {
        if (!canReplaceBlockAt(level, pos)) {
            return false;
        }

        // 放置蛛网
        if (!level.setBlock(pos, Blocks.COBWEB.defaultBlockState(), 3)) {
            return false;
        }

        // 如果是燃烧的蛛网，处理火焰效果
        if (burning) {
            handleBurningWebEffects(level, pos);
        }

        return true;
    }

    /**
     * 处理燃烧蛛网的火焰效果
     */
    private static void handleBurningWebEffects(Level level, BlockPos webPos) {
        Direction[] directions = Direction.values();
        java.util.List<Direction> availableDirections = new java.util.ArrayList<>();

        // 找到所有可以放置火焰的相邻位置
        for (Direction direction : directions) {
            BlockPos firePos = webPos.relative(direction);
            if (level.getBlockState(firePos).isAir()) {
                availableDirections.add(direction);
            }
        }

        // 随机选择一个方向放置火焰
        if (!availableDirections.isEmpty()) {
            Direction chosenDirection = availableDirections.get(level.getRandom().nextInt(availableDirections.size()));
            BlockPos firePos = webPos.relative(chosenDirection);
            level.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 3);

            // 点燃范围内的生物
            AABB effectArea = new AABB(webPos).inflate(1.0);
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, effectArea)) {
                entity.setRemainingFireTicks(100); // 5秒燃烧
            }
        }
    }
}