package huix.infinity.common.world.entity.monster.skeleton;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BoneLord extends IFWSkeleton {
    private int numTroopsSummoned;

    public BoneLord(EntityType<? extends BoneLord> entityType, Level level) {
        super(entityType, level);
    }

    public BoneLord(Level level) {
        this(IFWEntityType.BONE_LORD.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D);
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource random, @NotNull DifficultyInstance difficulty) {
        // 骨王使用锈铁武器
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(IFWItems.rusted_iron_sword.get()));

        // 设置锈铁装备
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(IFWItems.rusted_iron_boots.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(IFWItems.rusted_iron_leggings.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(IFWItems.rusted_iron_chestplate.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(IFWItems.rusted_iron_helmet.get()));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.numTroopsSummoned = compound.getByte("num_troops_summoned");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.numTroopsSummoned > 0) {
            compound.putByte("num_troops_summoned", (byte) this.numTroopsSummoned);
        }
    }

    @Override
    protected int getBaseExperienceReward() {
        return super.getBaseExperienceReward() * 3;
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    public Class<? extends IFWSkeleton> getTroopClass() {
        return this.isAncientBoneLord() ? Longdead.class : IFWSkeleton.class;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide) {
            // 如果有玩家目标，设为不消失
            if (!this.isPersistenceRequired() && this.getTarget() instanceof Player) {
                this.setPersistenceRequired();
            }

            if (this.tickCount % 20 == 0) {
                LivingEntity target = this.getTarget();
                if (target != null) {
                    if (target.isDeadOrDying() || this.distanceTo(target) > 16.0F || !this.hasLineOfSight(target)) {
                        target = null;
                    }

                    // 尝试召唤部队
                    if (this.numTroopsSummoned < 6 && target instanceof Player && this.random.nextInt(8) < 7 - this.numTroopsSummoned) {
                        this.numTroopsSummoned += this.trySummonTroop(target);
                        if (this.numTroopsSummoned < 6 && this.random.nextBoolean()) {
                            this.numTroopsSummoned += this.trySummonTroop(target);
                        }
                    }
                }

                // 治疗和激励附近的骷髅
                List<IFWSkeleton> nearbySkeletons = this.level().getEntitiesOfClass(IFWSkeleton.class,
                        this.getBoundingBox().inflate(16.0F, 8.0F, 16.0F));

                for (IFWSkeleton skeleton : nearbySkeletons) {
                    if (skeleton == this || skeleton.hasLineOfSight(this)) {
                        // 治疗
                        if (skeleton.getHealth() < skeleton.getMaxHealth()) {
                            skeleton.heal(1.0F);
                        }

                        // 激励非骨王
                        if (!skeleton.isBoneLord()) {
                            skeleton.setFrenziedByBoneLord(target);
                        }
                    }
                }
            }
        }
    }

    private int trySummonTroop(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return 0;
        }

        // 简化的召唤逻辑 - 在附近生成骷髅
        for (int i = 0; i < 10; i++) {
            double x = this.getX() + (this.random.nextDouble() - 0.5) * 24.0;
            double z = this.getZ() + (this.random.nextDouble() - 0.5) * 24.0;
            // 修复：getHeightmapPos需要传入BlockPos对象
            BlockPos heightPos = this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos((int) x, 0, (int) z));
            double y = heightPos.getY();

            IFWSkeleton skeleton;
            if (this.getTroopClass() == Longdead.class) {
                skeleton = new Longdead(this.level());
            } else {
                skeleton = new IFWSkeleton(this.level());
            }

            skeleton.setPos(x, y, z);
            skeleton.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(skeleton.blockPosition()),
                    MobSpawnType.REINFORCEMENT, null);
            skeleton.setTarget(target);

            if (serverLevel.addFreshEntity(skeleton)) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void addRandomWeapon() {
        List<ItemStack> weapons = new ArrayList<>();
        weapons.add(new ItemStack(IFWItems.rusted_iron_sword.get()));

        // 根据世界天数添加更多武器选择
        long dayOfWorld = this.level().getDayTime() / 24000;
        if (dayOfWorld >= 10) {
            // 可以添加更多武器选择
            weapons.add(new ItemStack(IFWItems.rusted_iron_battle_axe.get())); // 增加权重
        }
        if (dayOfWorld >= 20) {
            // 更高级武器
            weapons.add(new ItemStack(IFWItems.rusted_iron_war_hammer.get())); // 增加权重
        }

        ItemStack selectedWeapon = weapons.get(this.random.nextInt(weapons.size()));

        // 随机损坏武器
        if (selectedWeapon.isDamageableItem()) {
            selectedWeapon.setDamageValue((int)(selectedWeapon.getMaxDamage() * this.random.nextFloat()));
        }

        this.setItemSlot(EquipmentSlot.MAINHAND, selectedWeapon);
    }

    @Override
    public boolean isBoneLord() {
        return true;
    }
}