package huix.infinity.mixin.world.item.enchantment;

import huix.infinity.extension.func.EnchantmentExtension;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( Enchantment.class )
public class EnchantmentMixin implements EnchantmentExtension {
}
