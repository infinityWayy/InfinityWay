package huix.infinity.mixin.world.damagesource;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CombatRules.class)
public class CombatRulesMixin {

    @Overwrite
    public static float getDamageAfterAbsorb(LivingEntity entity, float damage, DamageSource source, float armor, float toughness) {
        armor /= 2;
        ItemStack itemstack = entity.getWeaponItem();
        if (itemstack != null && entity.level() instanceof ServerLevel serverlevel) {
            armor *= Mth.clamp(EnchantmentHelper.modifyArmorEffectiveness(serverlevel, itemstack, entity, source, armor), 0.0F, 1.0F);
        }

        if (armor > damage) {
            float toughEff = toughness / 10.0F;
            int delta = (int) ((int) armor - damage);

            for (int i = -1; i < delta; ++i) {
                if (entity.getRandom().nextFloat() < 0.2F + toughEff) {
                    return 0.0F;
                }
            }
        }

        return Math.max(damage - armor, 1.0F);
    }

}
