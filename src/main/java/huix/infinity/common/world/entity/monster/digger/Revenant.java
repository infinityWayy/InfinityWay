package huix.infinity.common.world.entity.monster.digger;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.util.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Revenant extends IFWZombie {

    public Revenant(EntityType<? extends Revenant> entityType, Level level) {
        super(entityType, level);
    }

    public Revenant(Level level) {
        this(IFWEntityType.REVENANT.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.26F)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public void addRandomWeapon() {
        List<WeightedEntry.Wrapper<Item>> weapons = new ArrayList<>();

        // 武器权重
        weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_sword.asItem(), 2));

        long day = WorldHelper.getDay(this.level());

        // 条件：day >= 10 && !isInTournamentMode()
        if (day >= 10 && !isInTournamentMode()) {
            // weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_battle_axe.asItem(), 1));
        }

        // 条件：day >= 20 && !isInTournamentMode()
        if (day >= 20 && !isInTournamentMode()) {
            // weapons.add(WeightedEntry.wrap(IFWItems.rusted_iron_war_hammer.asItem(), 1));
        }

        Optional<WeightedEntry.Wrapper<Item>> selected = WeightedRandom.getRandomItem(this.getRandom(), weapons);
        if (selected.isPresent()) {
            ItemStack weaponStack = new ItemStack(selected.get().data());

            weaponStack = randomizeForMob(weaponStack, true);
            this.setItemSlot(EquipmentSlot.MAINHAND, weaponStack);
        }
    }

    /**
     * 添加随机装备
     */
    private void addRandomEquipment() {
        // 对应原版的装备设置
        this.addRandomWeapon();

        // 完全对应原版的装备设置顺序和方法
        this.setItemSlot(EquipmentSlot.FEET, randomizeForMob(new ItemStack(IFWItems.rusted_iron_boots.asItem()), true));
        this.setItemSlot(EquipmentSlot.LEGS, randomizeForMob(new ItemStack(IFWItems.rusted_iron_leggings.asItem()), true));
        this.setItemSlot(EquipmentSlot.CHEST, randomizeForMob(new ItemStack(IFWItems.rusted_iron_chestplate.asItem()), true));
        this.setItemSlot(EquipmentSlot.HEAD, randomizeForMob(new ItemStack(IFWItems.rusted_iron_helmet.asItem()), true));
    }

    /**
     * 随机耐久、附魔等
     */
    private ItemStack randomizeForMob(ItemStack stack, boolean randomizeDamage) {
        if (randomizeDamage && stack.isDamageableItem()) {
            // 模拟原版的随机化逻辑
            RandomSource random = this.getRandom();
            int maxDamage = stack.getMaxDamage();
            int damage = random.nextInt(maxDamage / 4); // 大约25%的损耗
            stack.setDamageValue(damage);

            // 这里可以添加随机附魔逻辑，如果原版有的话
            // EnchantmentHelper.enchantItem(random, stack, level, false);
        }
        return stack;
    }

    /**
     * 检查是否在锦标赛模式
     */
    private boolean isInTournamentMode() {
        // 这里需要根据你的游戏逻辑来实现
        // 如果没有锦标赛模式，直接返回 false
        return false;
    }

    @Override
    protected int getBaseExperienceReward() {
        // 完全对应原版：super.getExperienceValue() * 3
        return super.getBaseExperienceReward() * 3;
    }

    public boolean isRevenant() {
        return true;
    }


    public void setVillager(boolean villager, int profession) {
        System.err.println("setVillager: why setting villager for revenant?");
        new Exception().printStackTrace();
    }

    protected boolean isVillager() {
        return false;
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource random, @NotNull DifficultyInstance difficulty) {

        this.addRandomEquipment();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {

        if (level instanceof ServerLevel serverLevel && spawnType == MobSpawnType.NATURAL) {
            if (hasRevenantInChunk(serverLevel, this.blockPosition())) {
                this.discard();
                return null;
            }
        }

        SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.setBaby(false); // Revenant不会是幼体
        this.setCanBreakDoors(true);

        return groupData;
    }

    /**
     * 区块检查方法
     */
    private boolean hasRevenantInChunk(ServerLevel level, BlockPos pos) {
        // 获取当前位置的区块坐标
        int chunkX = pos.getX() >> 4;  // 等同于 pos.getX() / 16
        int chunkZ = pos.getZ() >> 4;  // 等同于 pos.getZ() / 16

        // 创建区块边界框
        double minX = chunkX * 16.0;
        double minZ = chunkZ * 16.0;
        double maxX = minX + 16.0;
        double maxZ = minZ + 16.0;
        AABB chunkBounds = new AABB(minX, level.getMinBuildHeight(), minZ,
                maxX, level.getMaxBuildHeight(), maxZ);

        // 搜索区块内的所有Revenant实体
        List<Entity> entitiesInChunk = level.getEntitiesOfClass(Entity.class, chunkBounds);

        for (Entity entity : entitiesInChunk) {
            if (entity instanceof Revenant && entity != this) {
                return true; // 找到了其他Revenant
            }
        }

        return false; // 没有找到其他Revenant
    }

    @Override
    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    protected boolean supportsBreakDoorGoal() {
        return true;
    }
}