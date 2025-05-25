package huix.infinity.common.world.entity.monster.arachnid;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

public class IFWSpider extends IFWArachnid {

    public IFWSpider(EntityType<? extends IFWArachnid> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createArachnidAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH, 16.0D)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    protected boolean isSpecialSpiderType() {
        return false; // 普通蜘蛛
    }

    protected int getWebShootInterval() {
        return 500; // 普通蜘蛛发射蛛网冷却较长
    }
}