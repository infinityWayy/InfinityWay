package huix.infinity.common.world.entity.monster.gelatinous;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.IFWTieredItem;
import huix.infinity.common.world.item.tier.IFWArmorMaterials;
import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pudding extends GelatinousCube {

    public Pudding(EntityType<? extends Pudding> entityType, Level level) {
        super(entityType, level);
        this.randomDamage = 5;
        this.baseDamage = 2;
    }

    /**
     * 伤害免疫检查 - 只有附魔武器或特定伤害类型有效
     */
    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {

        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }

        // 魔法伤害可以造成伤害
        if (damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypes.INDIRECT_MAGIC)) {
            return false;
        }

        // 火焰和熔岩伤害可以造成伤害
        if (damageSource.is(DamageTypes.ON_FIRE) || damageSource.is(DamageTypes.LAVA)) {
            return false;
        }

        // 检查是否为玩家攻击
        if (damageSource.getEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();
            // 只有附魔武器能造成伤害
            if (!weapon.isEnchanted()) {
                return true;
            }
        }

        return super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean canCorodeBlocks() {
        return true;
    }

    @Override
    public boolean isAcidic() {
        return true;
    }

    @Override
    public int getAttackStrengthMultiplier() {
        return 4;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.ON_FIRE) ||
                damageSource.is(DamageTypes.LAVA) ||
                damageSource.is(DamageTypes.MAGIC) ||
                damageSource.is(DamageTypes.INDIRECT_MAGIC)) {
            return super.hurt(damageSource, amount);
        }

        return super.hurt(damageSource, amount * 0.2f);
    }

    @Override
    public boolean canBeAffected(@NotNull MobEffectInstance effectInstance) {
        return false;
    }

    @Override
    public @NotNull MobCategory getClassification(boolean forSpawnCount) {
        return MobCategory.MONSTER;
    }

    @Override
    protected ItemStack getSlimeBallDrop() {
        return new ItemStack(IFWItems.pudding_ball.get());
    }

    @Override
    protected GelatinousCube createSmallerSlime(int size) {
        if (size < 1) return null;

        @SuppressWarnings("unchecked")
        Pudding smallPudding = new Pudding((EntityType<? extends Pudding>) this.getType(), this.level());
        smallPudding.setSize(size);
        return smallPudding;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    boolean canItemBeCorrodedByAcid(ItemStack itemStack) {
        if (itemStack.is(IFWItemTags.ACID_IMMUNE)) {
            return false;
        }
        if (itemStack.getItem() instanceof ItemNameBlockItem blockItem) {
            if (blockItem.getBlock().defaultBlockState().is(BlockTags.MINEABLE_WITH_PICKAXE)) {
                return false;
            }
        }
        if (itemStack.getItem() instanceof IFWTieredItem ifwTieredItem) {
            switch (ifwTieredItem.ifwTier()) {
                case IFWTiers.GOLD, IFWTiers.MITHRIL, IFWTiers.ADAMANTIUM -> {
                    return false;
                }
                default -> {
                    return true;
                }
            }
        }
        if (itemStack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getMaterial().is(IFWArmorMaterials.golden) ||
                    armorItem.getMaterial().is(IFWArmorMaterials.mithril) ||
                    armorItem.getMaterial().is(IFWArmorMaterials.adamantium)) {
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getExperienceReward() * 2;
    }
}