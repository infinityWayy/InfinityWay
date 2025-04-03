package huix.infinity.mixin.world.item;

import com.llamalad7.mixinextras.sugar.Local;
import huix.infinity.common.world.item.RepairableItem;
import huix.infinity.extension.func.ArmorExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.Supplier;

@Mixin( ArmorItem.class )
public class ArmorItemMixin extends Item implements ArmorExtension, RepairableItem {
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/resources/ResourceLocation;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"
            , ordinal = 0), method = "lambda$new$0")
    private static AttributeModifier ifw_rebuildDefense(ResourceLocation id, double amount, AttributeModifier.Operation operation, @Local(argsOnly = true) Holder<ArmorMaterial> material, @Local(argsOnly = true) ArmorItem.Type type) {
        double i = material.value().ifw_defense() != null ? (material.value()).float_defense(type) : amount;
        return new AttributeModifier(id, i, AttributeModifier.Operation.ADD_VALUE);
    }


    @Shadow
    @Final
    private Supplier<ItemAttributeModifiers> defaultModifiers;
    @Shadow
    @Final
    protected Holder<ArmorMaterial> material;
    @Shadow
    @Final
    protected ArmorItem.Type type;

    @Unique
    public float float_defense() {
        return this.material.value().float_defense(this.type);
    }

    @Override
    public Map<ArmorItem.Type, Float> ifw_defense() {
        return this.material.value().ifw_defense();
    }

    @Override
    public int getRepairCost() {
        return this.components().get(DataComponents.MAX_DAMAGE) / 16;
    }

    @Override
    public int getRepairLevel() {
        return 0;
    }

    public ArmorItemMixin(Properties properties) {
        super(properties);
    }
}
