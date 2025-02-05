package huix.infinity.mixin.world.loot;

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

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCount$OreDropsMixin {


    @Shadow @Final private ApplyBonusCount.Formula formula;

    @Shadow @Final private Holder<Enchantment> enchantment;

    @Overwrite
    public ItemStack run(ItemStack stack, LootContext context) {
        ItemStack itemstack = context.getParamOrNull(LootContextParams.TOOL);
        if (itemstack != null) {
            int i = EnchantmentHelper.getItemEnchantmentLevel(this.enchantment, itemstack);
            if (this.enchantment.is(Enchantments.FORTUNE))
                stack.setCount(this.ifw_calculateNewCount(context.getRandom(), stack.getCount(), i));
            else
                stack.setCount(this.formula.calculateNewCount(context.getRandom(), stack.getCount(), i));
        }
        return stack;
    }

    @Unique
    public int ifw_calculateNewCount(RandomSource random, int originalCount, int enchantmentLevel) {
        return random.nextFloat() < enchantmentLevel * 0.1F ? originalCount * 2 : originalCount;
    }
}
