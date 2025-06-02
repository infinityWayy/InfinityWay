package huix.infinity.common.world.entity.monster.skeleton;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Longdead extends IFWSkeleton {

    public Longdead(EntityType<? extends Longdead> entityType, Level level) {
        super(entityType, level);
        // 降低装备掉落率
        for (int i = 1; i < this.armorDropChances.length; ++i) {
            this.armorDropChances[i] *= 0.25F;
        }
    }

    public Longdead(Level level) {
        this(IFWEntityType.LONGDEAD.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.29D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D);
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource random, @NotNull DifficultyInstance difficulty) {
        // 古尸守卫有特殊的武器初始化逻辑，普通古尸使用随机武器
        if (!this.isGuardian()) {
            // 普通古尸：随机给予弓或剑，一旦确定就不再改变
            if (this.getSkeletonType() == 2 || random.nextBoolean()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(IFWItems.ancient_metal_sword.get()));
            } else {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }
        }

        // 设置远古金属锁甲
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(IFWItems.ancient_metal_chainmail_helmet.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(IFWItems.ancient_metal_chainmail_chestplate.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(IFWItems.ancient_metal_chainmail_leggings.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(IFWItems.ancient_metal_chainmail_boots.get()));
    }

    @Override
    public boolean isLongdead() {
        return true;
    }

    public boolean isGuardian() {
        return this instanceof LongdeadGuardian;
    }

    @Override
    public void addRandomWeapon() {
        // 普通古尸不切换武器，保持原有武器
        // 古尸守卫有自己的武器管理逻辑
        if (!this.isGuardian()) {
            // 只有当没有武器时才添加
            if (this.getMainHandItem().isEmpty()) {
                ItemStack weapon = this.getSkeletonType() == 2 ?
                        new ItemStack(IFWItems.ancient_metal_sword.get()) :
                        new ItemStack(Items.BOW);

                // 随机损坏武器
                if (weapon.isDamageableItem()) {
                    weapon.setDamageValue((int)(weapon.getMaxDamage() * this.random.nextFloat()));
                }

                this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
            }
        }
    }

    @Override
    protected float getDamageAfterArmorAbsorb(@NotNull DamageSource damageSource, float damageAmount) {
        float result = super.getDamageAfterArmorAbsorb(damageSource, damageAmount);
        // 额外的自然防御
        if (!damageSource.is(net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR)) {
            result = Math.max(0, result - (this.isGuardian() ? 2.0F : 1.0F));
        }
        return result;
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * (this.isGuardian() ? 5 : 3);
    }
}