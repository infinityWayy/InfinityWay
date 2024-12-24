package huix.infinity.mixin.world.enchantment;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.RemoveBinomial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( RemoveBinomial.class )
public class RemoveBinomialMixin {
    @Final
    @Shadow
    private LevelBasedValue chance;

    @Overwrite
    public float process(int enchantmentLevel, RandomSource random, float value) {
        float f = this.chance.calculate(enchantmentLevel) * 0.75F;
        int i = 0;

        for(int j = 0; (float)j < value; ++j) {
            if (random.nextFloat() < f) {
                ++i;
            }
        }

        return value - (float) i;
    }
}
