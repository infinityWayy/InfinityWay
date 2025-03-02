package huix.infinity.mixin.world.loot;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ApplyBonusCount.class, priority = 2000)
public class ApplyBonusCount$OreDropsMixin {


    @Shadow @Final private ApplyBonusCount.Formula formula;

    @Shadow @Final private Holder<Enchantment> enchantment;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setCount(I)V"), method = "run")
    private void applyIFWFortune(ItemStack stack, int count, @Local(ordinal = 1) int enchLevel, @Local(argsOnly = true) LootContext context) {
        if (this.enchantment.is(Enchantments.FORTUNE))
            stack.setCount(this.ifw_calculateNewCount(context.getRandom(), 1, enchLevel));
        else stack.setCount(count);
    }

    @Unique
    public int ifw_calculateNewCount(RandomSource random, int originalCount, int enchantmentLevel) {
        return random.nextFloat() < enchantmentLevel * 0.1F ? originalCount * 2 : originalCount;
    }
}
