package huix.infinity.common.world.entity.monster.arachnid;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

public class IFWBlackWidowSpider extends IFWWoodSpider {

    public IFWBlackWidowSpider(EntityType<? extends IFWWoodSpider> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {

        return IFWWoodSpider.createAttributes();
    }

    @Override
    protected float getExperienceMultiplier() {
        return 1.6f;
    }

    @Override
    protected boolean isBlackWidowSpider() {
        return true;
    }

}