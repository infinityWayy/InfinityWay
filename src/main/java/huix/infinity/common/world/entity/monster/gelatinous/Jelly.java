package huix.infinity.common.world.entity.monster.gelatinous;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Jelly extends GelatinousCube {

    public Jelly(EntityType<? extends Jelly> entityType, Level level) {
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
        return 2;
    }

    @Override
    public @NotNull MobCategory getClassification(boolean forSpawnCount) {
        return MobCategory.MONSTER;
    }

    @Override
    protected ItemStack getSlimeBallDrop() {
        return new ItemStack(IFWItems.jelly_ball.get());
    }

    @Override
    protected GelatinousCube createSmallerSlime(int size) {
        if (size < 1) return null;

        @SuppressWarnings("unchecked")
        Jelly smallJelly = new Jelly((EntityType<? extends Jelly>) this.getType(), this.level());
        smallJelly.setSize(size);
        return smallJelly;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    boolean canItemBeCorrodedByAcid(ItemStack itemStack) {
        if (itemStack.get(IFWDataComponents.ifw_food_data.get()) != null) {
            return itemStack.get(IFWDataComponents.ifw_food_data.get()).phytonutrients() != 0;
        } else return itemStack.is(Items.PAPER);
    }

}