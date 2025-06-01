package huix.infinity.common.world.entity.projectile;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.item.SlimeBallItem;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownSlimeBall extends ThrowableItemProjectile {
    private float throwDamage = 2.0F;
    private float corrosionDamage = 0.0F;
    private boolean isAcidic = false;

    public ThrownSlimeBall(EntityType<? extends ThrownSlimeBall> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSlimeBall(Level level, LivingEntity shooter) {
        super(IFWEntityType.THROWN_SLIME_BALL.get(), shooter, level);
    }

    public ThrownSlimeBall(Level level, double x, double y, double z) {
        super(IFWEntityType.THROWN_SLIME_BALL.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SLIME_BALL;
    }

    public void setDamageValues(float throwDamage, float corrosionDamage, boolean isAcidic) {
        this.throwDamage = throwDamage;
        this.corrosionDamage = corrosionDamage;
        this.isAcidic = isAcidic;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        // 造成投掷伤害
        DamageSource damageSource = this.damageSources().thrown(this, this.getOwner());
        entity.hurt(damageSource, throwDamage);

        // 如果击中玩家且具有腐蚀性，应用腐蚀效果
        if (entity instanceof Player player && (isAcidic || corrosionDamage > 0)) {
            this.applyCorrosionEffect(player);
        }
    }

    private void applyCorrosionEffect(Player player) {
        // 模拟史莱姆的腐蚀效果
        if (isAcidic) {
            // 应用类似GelatinousCube的腐蚀逻辑
            this.corrodePlayerEquipment(player);
            this.corrodeInventoryItems(player);
        }
    }

    private void corrodePlayerEquipment(Player player) {
        // 简化版的装备腐蚀逻辑
        // 可以参考GelatinousCube的corrodePlayerEquipment方法
        // 这里简化处理，造成少量耐久损伤
        int damageAmount = Math.max(1, (int) corrosionDamage);

        for (net.minecraft.world.entity.EquipmentSlot slot : net.minecraft.world.entity.EquipmentSlot.values()) {
            ItemStack item = player.getItemBySlot(slot);
            if (!item.isEmpty() && item.isDamageableItem()) {
                if (player.getRandom().nextFloat() < 0.3F) { // 30%几率腐蚀
                    item.hurtAndBreak(damageAmount, player, slot);
                }
            }
        }
    }

    private void corrodeInventoryItems(Player player) {
        // 简化版的物品腐蚀逻辑
        if (player.getRandom().nextFloat() < 0.2F) { // 20%几率腐蚀物品栏
            // 随机选择一个物品进行腐蚀
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (!stack.isEmpty() && player.getRandom().nextFloat() < 0.1F) {
                    int corrodeAmount = Math.min(stack.getCount(), Math.max(1, (int) (corrosionDamage / 2)));
                    stack.shrink(corrodeAmount);
                    break; // 只腐蚀一个物品
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!this.level().isClientSide) {
            // 生成粒子效果
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public void handleEntityEvent(byte eventId) {
        if (eventId == 3) {
            // 粒子效果
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                        this.getX(), this.getY(), this.getZ(),
                        ((double) this.random.nextFloat() - 0.5) * 0.08,
                        ((double) this.random.nextFloat() - 0.5) * 0.08,
                        ((double) this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }
}