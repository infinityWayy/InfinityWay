package huix.infinity.mixin.world.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class MobMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;", ordinal = 8, shift = At.Shift.AFTER),
            method = "canReplaceCurrentItem", cancellable = true)
    private void ifw_float_defence(ItemStack candidate, ItemStack existing, CallbackInfoReturnable<Boolean> cir, @Local ArmorItem armorItem){
        ArmorItem armoritem = (ArmorItem) candidate.getItem();
        if (armoritem.float_defense() != armorItem.float_defense()) {
            cir.setReturnValue(armoritem.float_defense() > armorItem.float_defense());
        }
    }

    @Redirect(method = "createMobAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;add(Lnet/minecraft/core/Holder;D)Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"))
    private static AttributeSupplier.Builder setFollowRange(AttributeSupplier.Builder builder, Holder<Attribute> attribute, double baseValue) {
        return builder.add(attribute, baseValue * 2);
    }

}
