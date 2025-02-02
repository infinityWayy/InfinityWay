package huix.infinity.mixin.world.item;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.func_extension.ItemPropertiesExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.Properties.class)
public class Item$PropertiesMixin implements ItemPropertiesExtension {

    @Shadow
    public <T> Item.Properties component(DataComponentType<T> component, T value) {
        return null;
    }
}
