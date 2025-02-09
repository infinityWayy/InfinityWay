package huix.infinity.mixin.world.item;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.Properties.class)
public class Item$PropertiesMixin {

    @Shadow
    public <T> Item.Properties component(DataComponentType<T> component, T value) {
        return null;
    }
}
