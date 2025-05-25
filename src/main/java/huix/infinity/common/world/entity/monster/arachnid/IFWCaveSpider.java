package huix.infinity.common.world.entity.monster.arachnid;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

public class IFWCaveSpider extends IFWArachnid {

    public IFWCaveSpider(EntityType<? extends IFWArachnid> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createArachnidAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected boolean isSpecialSpiderType() {
        return true; // 洞穴蜘蛛是特殊类型
    }

    protected int getWebShootInterval() {
        return 200; // 更快的蛛网射击间隔
    }

    @Override
    protected boolean peacefulDuringDay() {
        return false; // 洞穴蜘蛛不会在白天变友善
    }

    @Override
    protected float getExperienceMultiplier() {
        return 2.0f; // 2倍经验值
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean result = super.doHurtTarget(target);

        if (result && target instanceof LivingEntity livingTarget) {
            // 添加中毒效果 24秒 (480 tick / 20 = 24秒)
            livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 480, 0));
        }

        return result;
    }
}