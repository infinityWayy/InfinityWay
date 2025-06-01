package huix.infinity.common.world.entity.monster.digger;

import huix.infinity.common.world.entity.ai.DiggerGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Digger extends Monster {
    // 用于存储目标方块位置的 NBT 标签名
    private static final String TARGET_X = "DigTargetX";
    private static final String TARGET_Y = "DigTargetY";
    private static final String TARGET_Z = "DigTargetZ";
    private static final String DIG_PROGRESS = "DigProgress";
    private static final String HAS_TARGET = "HasDigTarget";

    // 存储当前挖掘目标和进度的字段
    private BlockPos digTarget;
    private float digProgress = 0;

    protected Digger(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.goalSelector.addGoal(1, new DiggerGoal(this));
    }

    // Getter 和 Setter 方法
    public void setDigTarget(BlockPos target) {
        this.digTarget = target;
    }

    public BlockPos getDigTarget() {
        return this.digTarget;
    }

    public void setDigProgress(float progress) {
        this.digProgress = progress;
    }

    public float getDigProgress() {
        return this.digProgress;
    }

    public void clearDigging() {
        this.digTarget = null;
        this.digProgress = 0;
    }

    @Override
    public void aiStep() {
        // 如果有挖掘目标但目标方块不再存在或变成了空气，清除挖掘状态
        if (digTarget != null && (level().isOutsideBuildHeight(digTarget) ||
                level().getBlockState(digTarget).isAir())) {
            clearDigging();
        }
        super.aiStep();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        // 保存是否有挖掘目标
        compound.putBoolean(HAS_TARGET, digTarget != null);

        // 如果有挖掘目标，保存目标位置和进度
        if (digTarget != null) {
            compound.putInt(TARGET_X, digTarget.getX());
            compound.putInt(TARGET_Y, digTarget.getY());
            compound.putInt(TARGET_Z, digTarget.getZ());
            compound.putFloat(DIG_PROGRESS, digProgress);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        // 读取挖掘目标和进度
        if (compound.getBoolean(HAS_TARGET)) {
            int x = compound.getInt(TARGET_X);
            int y = compound.getInt(TARGET_Y);
            int z = compound.getInt(TARGET_Z);
            digTarget = new BlockPos(x, y, z);
            digProgress = compound.getFloat(DIG_PROGRESS);
        }
    }

    @Override
    public void die(@NotNull DamageSource pDamageSource) {
        // 死亡时清除挖掘进度显示
        if (digTarget != null) {
            level().destroyBlockProgress(getId(), digTarget, -1);
        }
        super.die(pDamageSource);
    }
}
