package huix.infinity.common.world.entity.monster.gelatinous;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Blob extends GelatinousCube {

    public Blob(EntityType<? extends Blob> entityType, Level level) {
        super(entityType, level);
        this.randomDamage = 3;
        this.baseDamage = 1;
    }

    @Override
    public boolean canCorodeBlocks() {
        return false;
    }

    @Override
    public boolean isAcidic() {
        return true;
    }

    @Override
    public int getAttackStrengthMultiplier() {
        return 3;
    }

    @Override
    public @NotNull MobCategory getClassification(boolean forSpawnCount) {
        return MobCategory.MONSTER;
    }

    @Override
    protected ItemStack getSlimeBallDrop() {
        return new ItemStack(IFWItems.blob_ball.get());
    }

    @Override
    protected GelatinousCube createSmallerSlime(int size) {
        if (size < 1) return null;

        @SuppressWarnings("unchecked")
        Blob smallBlob = new Blob((EntityType<? extends Blob>) this.getType(), this.level());
        smallBlob.setSize(size);
        return smallBlob;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    boolean canItemBeCorrodedByAcid(ItemStack itemStack) {
        if (itemStack.get(IFWDataComponents.ifw_food_data.get()) != null) {
            return itemStack.get(IFWDataComponents.ifw_food_data.get()).protein() != 0;
        } else return itemStack.is(Tags.Items.LEATHERS);
    }

}