package huix.infinity.mixin.world.item;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.func_extension.ItemStackExtension;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
public class ItemStackMixin implements ItemStackExtension {

    @Unique
    @Override
    public int ifw_cookingLevel() {
        return this.getItem().components().getOrDefault(IFWDataComponents.ifw_cooking_level.get(), 1);
    }

    @Unique
    @Override
    public int ifw_beCookingLevel() {
        return this.getItem().components().getOrDefault(IFWDataComponents.ifw_be_cooking_level.get(), 1);
    }

    @Shadow
    public Item getItem() {
        return null;
    }
}
