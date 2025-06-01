package huix.infinity.common.world.entity.monster.gelatinous;

import huix.infinity.common.world.item.IFWItems;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Jelly extends GelatinousCube {

    public Jelly(EntityType<? extends Jelly> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canCorodeBlocks() {
        return false;
    }

    @Override
    public boolean canDissolveItem(ItemStack item) {
        return item.is(Items.BREAD) || item.is(Items.APPLE) || item.is(Items.WHEAT) ||
                item.is(Items.CARROT) || item.is(Items.POTATO) || item.is(Items.LEATHER) ||
                item.is(Items.STRING) || item.is(Items.PAPER);
    }

    @Override
    public boolean damageItem(ItemEntity item) {
        ItemStack stack = item.getItem();
        if (this.canDissolveItem(stack)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                item.discard();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canDamageItem(ItemStack item) {
        return item.isDamageableItem() && this.random.nextFloat() < 0.15f;
    }

    @Override
    public boolean isAcidic() {
        return false;
    }

    @Override
    public int getAttackStrengthMultiplier() {
        return 2;
    }

    protected boolean isDealsDamage() {
        return true;
    }

    @Override
    public MobCategory getClassification(boolean forSpawnCount) {
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

}