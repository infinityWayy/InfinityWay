package huix.infinity.common.world.entity.monster;

import huix.infinity.common.world.entity.ai.DiggerGoal;
import huix.infinity.common.world.item.ClubWeapon;
import huix.infinity.common.world.item.IFWDiggerItem;
import huix.infinity.common.world.item.ScytheTool;
import huix.infinity.common.world.item.ShovelTool;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Block.dropResources;

public class Digger extends Monster {
    public boolean isDestroyingBlock;
    public BlockPos destroyBlockPos;
    protected int destroyBlockProgress;
    public int destroyBlockCoolOff = 40;
    public int destroyPauseTicks;
    protected Digger(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.goalSelector.addGoal(1, new DiggerGoal(this));
    }

    public boolean isHoldingItemThatPreventsDigging() {
        Item handItem = this.getMainHandItem().getItem();
        return handItem instanceof SwordItem || handItem instanceof ClubWeapon || handItem instanceof ScytheTool;
    }

    public boolean isEffectiveTool(BlockState state) {
        Item handItem = this.getMainHandItem().getItem();
        if (handItem instanceof IFWDiggerItem ifwDiggerItem) {
            return ifwDiggerItem.isDamageable(state);
        }
        return false;
    }

    public boolean isDiggingEnabled() {
        return !this.isHoldingItemThatPreventsDigging();
    }

    public boolean blockWillFall(BlockPos pos) {
        Block block = this.level().getBlockState(pos).getBlock();
        return block instanceof Fallable || block == Blocks.CACTUS || block instanceof TorchBlock || block == Blocks.SNOW;
    }

    public boolean isTargetingPlayer() {
        return this.getTarget() instanceof Player;
    }

    public void partiallyDestroyBlock() {
        ItemStack heldItem = this.getMainHandItem();
        if (!this.canDestroyBlock(destroyBlockPos, true)) {
            this.cancelBlockDestruction();
            return;
        }
//        this.refreshDespawnCounter(-400);
        Level level = this.level();
        BlockState blockState = level.getBlockState(destroyBlockPos);
        Block block = blockState.getBlock();
        // 处理仙人掌伤害
//        if (block == Blocks.CACTUS && !this.isHoldingItemThatPreventsHandDamage()) {
//            DamageSource damageSource = level.damageSources().cactus();
//            this.attackEntityFrom(new DADamage(damageSource, 1.0f));
//        }
        if (++this.destroyBlockProgress < 10) {
            this.isDestroyingBlock = true;
        } else {
            this.destroyBlockProgress = -1;
            // 玻璃破碎特效
            if (blockState.is(BlockTags.IMPERMEABLE)) {
                level.levelEvent(2001, destroyBlockPos, Block.getId(blockState));
            }

            // 方块掉落
            BlockEntity blockEntity = level.getBlockEntity(destroyBlockPos);
            dropResources(blockState, level, destroyBlockPos, blockEntity, this, heldItem);

            level.removeBlock(destroyBlockPos, false);
            level.gameEvent(this, GameEvent.BLOCK_DESTROY, destroyBlockPos);

            // 处理可能下落的方块
//            if (this.blockWillFall(destroyBlockPos.above())) {
//                AABB searchArea = this.getBoundingBox().inflate(3.0, 1.0, 3.0);
//                List<PathfinderMob> entities = level.getEntitiesOfClass(PathfinderMob.class, searchArea,
//                        EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(Entity::isAlive));
//
//                for (PathfinderMob entity : entities) {
//                    entity.goalSelector.getAvailableGoals().stream()
//                            .filter(g -> g.getGoal() instanceof MeleeAttackGoal)
//                            .forEach(g -> {
//                                // 调整攻击任务计时器（示例，需根据实际 AI 实现调整）
//                                ((MeleeAttackGoal) g.getGoal()).resetAttackCooldown();
//                            });
//                }
//            }
            // 工具耐久消耗
            Item handItem = heldItem.getItem();
            if (!heldItem.isEmpty()) {
                handItem.mineBlock(heldItem, level, blockState, destroyBlockPos, this);
            }

            this.isDestroyingBlock = false;
            BlockPos abovePos = destroyBlockPos.above();
            BlockState aboveState = level.getBlockState(abovePos);

            // 处理上方方块
            if (aboveState.getBlock() instanceof Fallable) {
                this.isDestroyingBlock = true;
                this.destroyPauseTicks = 10;
            } else if (!aboveState.isAir() && !this.blockWillFall(abovePos)) {
                if (destroyBlockPos.getY() == this.getBlockY() && this.canDestroyBlock(abovePos, true)) {
                    destroyBlockPos = destroyBlockPos.above();
                } else {
                    destroyBlockPos = destroyBlockPos.below();
                }
                this.isDestroyingBlock = true;
                this.destroyPauseTicks = 10;
            } else if (destroyBlockPos.getY() == this.getBlockY() + 1 &&
                    !level.isEmptyBlock(this.blockPosition().above(2)) &&
                    this.canDestroyBlock(destroyBlockPos.below(), true)) {
                this.isDestroyingBlock = true;
                this.destroyPauseTicks = 10;
                destroyBlockPos = destroyBlockPos.below();
            }

            // 特殊方块处理
//            if (aboveState.getBlock() instanceof UnderminableBlock underminable) {
//                underminable.tryToFall(level, abovePos);
//            }
        }

        level.destroyBlockProgress(this.getId(), destroyBlockPos, destroyBlockProgress);

        // 播放方块破坏效果
        if (blockState.is(BlockTags.IMPERMEABLE)) {
            level.playSound(null, destroyBlockPos, SoundEvents.GLASS_BREAK,
                    SoundSource.BLOCKS, 1.0f, 0.8f);
        } else {
            level.levelEvent(2001, destroyBlockPos, Block.getId(blockState));
        }
    }

    public Vec3 getLegPosition() {
        return this.position().add(0, -1, 0);
    }

//    protected Vec3 getEyePosForBlockDestroying() {
//        return this.getPrimaryPointOfAttack();
//    }

    private boolean hasDownwardsDiggingTool() {
        ItemStack held_item = this.getMainHandItem();
        return held_item != null && held_item.getItem() instanceof ShovelTool;
    }

    private boolean isBlockClaimedByAnother(BlockPos pos) {
        // 使用现代 AABB 构造方式
        AABB searchArea = new AABB(
                this.getX() - 4.0,
                this.getY() - 4.0,
                this.getZ() - 4.0,
                this.getX() + 4.0,
                this.getY() + 4.0,
                this.getZ() + 4.0
        );

        // 使用类型安全的实体查询
        return this.level().getEntitiesOfClass(Digger.class, searchArea, entity ->
                entity != this &&
                        entity.isDestroyingBlock &&
                        entity.destroyBlockPos.equals(pos)
        ).stream().findAny().isPresent();
    }

    public boolean canDestroyBlock(BlockPos pos, boolean checkClipping) {

        if (this.isHoldingItemThatPreventsDigging()) {
            return false;
        }

        final int footY = this.getBlockY();
        final int targetY = pos.getY();

        // 纵向位置检查
        if (targetY < footY && !this.hasDownwardsDiggingTool()) {
            return false;
        }
        if (targetY > footY + 1) {
            return false;
        }

        // 距离检查（使用现代向量计算）
        final Vec3 entityCenter = this.position()
                .add(0, this.getBbHeight() / 2, 0); // 实体中心位置
        final Vec3 blockCenter = Vec3.atCenterOf(pos);
        if (entityCenter.distanceTo(blockCenter) > 3.25) {
            return false;
        }

        // 是否可以看到挖掘方块
        if (checkClipping) {
            ClipContext context = new ClipContext(
                    this.getEyePosition(1.0f),
                    blockCenter,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this
            );
            BlockHitResult result = this.level().clip(context);

            if (result.getType() != HitResult.Type.MISS &&
                    (!pos.equals(result.getBlockPos()))) {
                return false;
            }
        }

        // 获取方块状态
        final BlockState state = this.level().getBlockState(pos);
        if (state.isAir() || state.getFluidState().isSource()) {
            return false;
        }

        // 特殊实体类型处理（土元素）
//        if (this instanceof EntityEarthElemental elemental) {
//            int harvestLevel = state.getHarvestLevel();
//            return elemental.getBlockHarvestLevel() >= harvestLevel;
//        }

        // 工具有效性检查
        ItemStack tool = this.getMainHandItem();
        boolean hasEffectiveTool = !tool.isEmpty() &&
                /* state.canHarvestBlock(this.level(), pos, this) && */
                tool.isCorrectToolForDrops(state);

        // 白名单材质检查（使用现代标签系统）
        boolean isSoftMaterial = state.is(BlockTags.SAND) ||
                state.is(BlockTags.DIRT) ||
                state.is(BlockTags.LEAVES) ||
                state.is(BlockTags.WOOL) ||
                state.is(BlockTags.IMPERMEABLE) ||
                state.getBlock() instanceof CropBlock;


        // 最终判断逻辑
        return (hasEffectiveTool ||
                !state.requiresCorrectToolForDrops() ||
//                (this.isFrenzied() && state.getHarvestLevel() < 2) ||
                isSoftMaterial) &&
                !this.isBlockClaimedByAnother(pos);
    }

    public boolean setBlockToDig(BlockPos targetPos, boolean checkClipping) {
        if (!this.canDestroyBlock(targetPos, checkClipping)) {
            return false;
        }

        this.isDestroyingBlock = true;

        // 相同目标位置快速返回
        if (targetPos.equals(this.destroyBlockPos)) {
            return true;
        }

        // 仙人掌特殊处理（使用现代坐标方法）
        final BlockPos footPos = this.blockPosition();
        if (targetPos.getY() == footPos.getY() + 1) {
            BlockState currentState = this.level().getBlockState(targetPos);
            BlockPos belowPos = targetPos.below();

            if (currentState.is(Blocks.CACTUS) &&
                    this.canDestroyBlock(belowPos, checkClipping)) {
                targetPos = belowPos; // 更新目标位置到下方方块
            }
        }

        // 重置破坏状态
        this.destroyBlockProgress = -1;
        this.destroyBlockPos = targetPos;
        return true;
    }

    public void cancelBlockDestruction() {
        if (!this.isDestroyingBlock) {
            return;
        }
        this.level().destroyBlockProgress(this.getId(), destroyBlockPos, -1);
        this.isDestroyingBlock = false;
        this.destroyBlockProgress = -1;
        this.destroyBlockCoolOff = 40;
    }

    public int getCoolOffForBlock() {
        // 使用 BlockPos 替代离散坐标
        final BlockPos destroyPos = this.destroyBlockPos;
        final Level level = this.level();

        // 获取方块状态（替代旧版 blockID 获取方式）
        final BlockState state = level.getBlockState(destroyPos);
        if (state.isAir()) {
            return 40; // 默认冷却时间
        }

        // 获取方块硬度（现代 API）
        float hardness = state.getDestroySpeed(level, destroyPos);
        int coolOff = (int)(300.0f * hardness);


        // 狂暴状态加速
//        if (this.isFrenzied()) {
//            cooloff /= 2;
//        }

        // 土元素特殊处理（使用模式匹配）
//        if (this instanceof EntityEarthElemental elemental) {
//            cooloff = switch (elemental.getClayType()) {
//                case NORMAL -> cooloff / 4;
//                case HARDENED -> cooloff / 6;
//                case SPECIAL -> cooloff / 8;
//                default -> cooloff;
//            };
//        }

        // 工具加速计算
        ItemStack toolStack = this.getMainHandItem();
        if (!toolStack.isEmpty()) {
            float efficiency = toolStack.getDestroySpeed(state);
            if (efficiency > 1.0f) {
                coolOff = (int) (coolOff / (1.0f + efficiency * 0.5f));
            }
        }

        return Math.max(coolOff, 5); // 确保最小冷却时间
    }

    @Override
    public void aiStep() {
        if (this.isDestroyingBlock) {
            if (this.destroyPauseTicks == 0) {
                // 使用 BlockPos 并调用 lookAt 方法
                BlockPos targetPos = this.destroyBlockPos;
                this.lookAt(
                        EntityAnchorArgument.Anchor.EYES,
                        new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5)
                );
                if (!this.canDestroyBlock(destroyBlockPos, true)) {
                    this.cancelBlockDestruction();
                }
            }
        } else {
            this.destroyBlockCoolOff = 40;
            this.destroyBlockProgress = -1;
        }
        super.aiStep();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.isDestroyingBlock) {
            compound.putBoolean("isDestroyingBlock", true);
            compound.putInt("destroy_block_x", this.destroyBlockPos.getX());
            compound.putInt("destroy_block_y", this.destroyBlockPos.getY());
            compound.putInt("destroy_block_z", this.destroyBlockPos.getZ());
            compound.putInt("destroyBlockProgress", this.destroyBlockProgress);
            compound.putInt("destroyBlockCoolOff", this.destroyBlockCoolOff);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("isDestroyingBlock")) {
            this.isDestroyingBlock = compound.getBoolean("isDestroyingBlock");
            int destroy_block_x = compound.getInt("destroy_block_x");
            int destroy_block_y = compound.getInt("destroy_block_y");
            int destroy_block_z = compound.getInt("destroy_block_z");

            this.destroyBlockPos = new BlockPos(destroy_block_x, destroy_block_y, destroy_block_z);

            this.destroyBlockProgress = compound.getInt("destroyBlockProgress");
            this.destroyBlockCoolOff = compound.getInt("destroyBlockCoolOff");
        }
    }

    @Override
    public void die(@NotNull DamageSource pDamageSource) {
        this.cancelBlockDestruction();
        super.die(pDamageSource);
    }
}
