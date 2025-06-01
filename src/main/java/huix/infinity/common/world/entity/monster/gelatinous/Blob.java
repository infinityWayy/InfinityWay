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

public class Blob extends GelatinousCube {

    public Blob(EntityType<? extends Blob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canCorodeBlocks() {
        return false;
    }

    @Override
    public boolean canDissolveItem(ItemStack item) {
        return !item.is(Items.DIAMOND) && !item.is(Items.EMERALD) &&
                !item.is(Items.NETHERITE_INGOT) && !item.is(Items.OBSIDIAN);
    }

    @Override
    public boolean damageItem(ItemEntity item) {
        ItemStack stack = item.getItem();
        if (this.canDissolveItem(stack)) {
            int damage = this.random.nextInt(3) + 1;
            stack.shrink(damage);
            if (stack.isEmpty()) {
                item.discard();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canDamageItem(ItemStack item) {
        return item.isDamageableItem() && this.random.nextFloat() < 0.25f;
    }

    @Override
    public boolean isAcidic() {
        return true;
    }

    @Override
    public int getAttackStrengthMultiplier() {
        return 3;
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

}