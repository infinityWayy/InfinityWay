package huix.infinity.common.world.entity.monster.arachnid;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class IFWWoodSpider extends IFWArachnid {

    public IFWWoodSpider(EntityType<? extends IFWArachnid> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createArachnidAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    protected boolean isSpecialSpiderType() {
        return false; // 木蜘蛛不是特殊类型
    }

    @Override
    protected boolean peacefulDuringDay() {
        return false; // 木蜘蛛不会在白天变友善
    }

    @Override
    protected float getExperienceMultiplier() {
        return 1.0f; // 1倍经验值（默认）
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean result = super.doHurtTarget(target);

        if (result && target instanceof LivingEntity livingTarget) {
            // 检查是否是基础木蜘蛛类（不是子类）
            if (this.getClass() == IFWWoodSpider.class) {
                // 木蜘蛛: 中毒效果 12秒 (240 tick)
                livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 240, 0));
            } else if (this.isBlackWidowSpider()) {
                // 黑寡妇蜘蛛: 中毒效果 48秒 (960 tick)
                livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 960, 0));
            }
        }

        return result;
    }

    /**
     * 检查是否是黑寡妇蜘蛛（需要根据您的实现调整）
     */
    protected boolean isBlackWidowSpider() {
        // 如果您有黑寡妇蜘蛛子类，在这里检查
        // return this instanceof IFWBlackWidowSpider;
        return false;
    }


    protected double getFollowRange() {
        // 根据亮度调整跟随距离
        float brightness = getLightLevelDependentMagicValue();
        double baseRange = this.getAttributeValue(Attributes.FOLLOW_RANGE);

        if (brightness < 0.65F) {
            return baseRange; // 暗处正常距离
        } else {
            return baseRange * 0.5; // 亮处减半距离
        }
    }

    // 可以被雪球伤害
    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.getDirectEntity() instanceof Snowball) {
            // 雪球造成1点伤害
            return super.hurt(damageSource, 1.0F);
        }
        return super.hurt(damageSource, amount);
    }

    // MITE原版的音效调整
    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 0.6F; // 音量降低40%
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 1.2F; // 音调提高20%
    }

}
