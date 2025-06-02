package huix.infinity.common.world.entity.monster.skeleton;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LongdeadGuardian extends Longdead {
    private ItemStack stowedItemStack;

    public LongdeadGuardian(EntityType<? extends LongdeadGuardian> entityType, Level level) {
        super(entityType, level);
    }

    public LongdeadGuardian(Level level) {
        this(IFWEntityType.LONGDEAD_GUARDIAN.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.29D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D);
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource random, @NotNull DifficultyInstance difficulty) {
        // 古尸守卫特殊的武器初始化：总是持弓，剑作为备用
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));

        // 创建备用剑
        ItemStack sword = new ItemStack(IFWItems.ancient_metal_sword.get());
        if (sword.isDamageableItem()) {
            sword.setDamageValue((int)(sword.getMaxDamage() * random.nextFloat()));
        }
        this.stowedItemStack = sword;

        // 设置远古金属锁甲
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(IFWItems.ancient_metal_chainmail_boots.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(IFWItems.ancient_metal_chainmail_leggings.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(IFWItems.ancient_metal_chainmail_chestplate.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(IFWItems.ancient_metal_chainmail_helmet.get()));
    }

    @Override
    public void addRandomWeapon() {
        // 古尸守卫不使用随机武器逻辑，在 populateDefaultEquipmentSlots 中已经设置
        // 保持为空方法，避免覆盖已设置的武器配置
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("stowed_item_stack")) {
            this.stowedItemStack = ItemStack.parseOptional(this.registryAccess(), compound.getCompound("stowed_item_stack"));
        } else {
            this.stowedItemStack = null;
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.stowedItemStack != null) {
            CompoundTag itemCompound = new CompoundTag();
            this.stowedItemStack.save(this.registryAccess(), itemCompound);
            compound.put("stowed_item_stack", itemCompound);
        }
    }

    public ItemStack getStowedItemStack() {
        return this.stowedItemStack;
    }

    public void swapHeldItemStackWithStowed() {
        if (this.stowedItemStack != null) {
            ItemStack itemStack = this.stowedItemStack;
            this.stowedItemStack = this.getMainHandItem();
            this.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
            this.reassessWeaponGoal();
        }
    }

    public boolean canStowItem(Item item) {
        if (this.getSkeletonType() != 0) {
            return false;
        }
        // 检查是否为剑或弓
        return item == IFWItems.ancient_metal_sword.get() || item instanceof BowItem;
    }

    @Override
    public boolean canPickUpLoot() {
        ItemStack mainHand = this.getMainHandItem();
        return super.canPickUpLoot() && (mainHand.isEmpty() || this.canStowItem(mainHand.getItem()));
    }

    public boolean isHoldingRangedWeapon() {
        return this.getMainHandItem().getItem() instanceof BowItem;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // 武器切换逻辑：只有当有备用武器时才执行
        if (this.stowedItemStack != null && !this.stowedItemStack.isEmpty()) {
            Entity target = this.getTarget();
            if (target != null && this.hasLineOfSight(target)) {
                double distance = this.distanceTo(target);

                if (this.isHoldingRangedWeapon()) {
                    // 当前持弓，玩家靠近时切换到剑
                    if (distance < 5.0D) {
                        this.swapHeldItemStackWithStowed();
                    }
                } else {
                    // 当前持剑，玩家远离时切换到弓
                    if (distance > 6.0D) {
                        this.swapHeldItemStackWithStowed();
                    }
                }
            }
        }
    }

    @Override
    public boolean isGuardian() {
        return true;
    }
}