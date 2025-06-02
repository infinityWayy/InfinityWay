package huix.infinity.common.world.entity.monster.skeleton;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AncientBoneLord extends BoneLord {

    public AncientBoneLord(EntityType<? extends AncientBoneLord> entityType, Level level) {
        super(entityType, level);
    }

    public AncientBoneLord(Level level) {
        this(IFWEntityType.ANCIENT_BONE_LORD.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D);
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource random, @NotNull DifficultyInstance difficulty) {
        // 远古骨王使用远古金属武器
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(IFWItems.ancient_metal_sword.get()));

        // 设置远古金属装备
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(IFWItems.ancient_metal_boots.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(IFWItems.ancient_metal_leggings.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(IFWItems.ancient_metal_chestplate.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(IFWItems.ancient_metal_helmet.get()));
    }

    @Override
    public Class<? extends IFWSkeleton> getTroopClass() {
        return Longdead.class;
    }

    @Override
    public void addRandomWeapon() {
        List<ItemStack> weapons = new ArrayList<>();
        weapons.add(new ItemStack(IFWItems.ancient_metal_sword.get()));
        weapons.add(new ItemStack(IFWItems.ancient_metal_battle_axe.get()));
        weapons.add(new ItemStack(IFWItems.ancient_metal_war_hammer.get()));
        // 可以添加更多古代金属武器

        ItemStack selectedWeapon = weapons.get(this.random.nextInt(weapons.size()));

        // 随机损坏武器
        if (selectedWeapon.isDamageableItem()) {
            selectedWeapon.setDamageValue((int)(selectedWeapon.getMaxDamage() * this.random.nextFloat()));
        }

        this.setItemSlot(EquipmentSlot.MAINHAND, selectedWeapon);
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 2;
    }

    @Override
    public boolean isAncientBoneLord() {
        return true;
    }
}