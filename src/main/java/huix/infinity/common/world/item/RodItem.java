package huix.infinity.common.world.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RodItem extends Item {
    private final float chance;

    public RodItem(Properties properties, float change) {
        super(properties);
        this.chance = change;
    }

    @Override
    public float getReachBonus() {
        return 0.5F;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getRandom().nextFloat() < chance) {
            stack.shrink(1);
        }
    }
}
