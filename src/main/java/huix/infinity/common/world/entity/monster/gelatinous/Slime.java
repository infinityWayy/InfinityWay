package huix.infinity.common.world.entity.monster.gelatinous;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Slime extends GelatinousCube {

    public Slime(EntityType<? extends Slime> entityType, Level level) {
        super(entityType, level);
        this.baseDamage = 1;
    }

    @Override
    public boolean canCorodeBlocks() {
        return false;
    }

    @Override
    public boolean isAcidic() {
        return false;
    }

    @Override
    public int getAttackStrengthMultiplier() {
        return 1;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    boolean canItemBeCorrodedByAcid(ItemStack itemStack) {
        return false;
    }

    @Override
    protected ItemStack getSlimeBallDrop() {
        return new ItemStack(Items.SLIME_BALL);
    }

    @Override
    protected GelatinousCube createSmallerSlime(int size) {
        if (size < 1) return null;

        @SuppressWarnings("unchecked")
        Slime smallSlime = new Slime((EntityType<? extends Slime>) this.getType(), this.level());
        smallSlime.setSize(size);
        return smallSlime;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

}