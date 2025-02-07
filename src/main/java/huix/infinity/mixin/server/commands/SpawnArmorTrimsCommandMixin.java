package huix.infinity.mixin.server.commands;

import huix.infinity.common.world.item.tier.IFWArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.server.commands.SpawnArmorTrimsCommand;
import net.minecraft.world.item.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpawnArmorTrimsCommand.class)
public class SpawnArmorTrimsCommandMixin {

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/ArmorMaterials;LEATHER:Lnet/minecraft/core/Holder;"), method = "spawnArmorTrims")
    private static Holder<ArmorMaterial> fixleather() {
        return IFWArmorMaterials.leather;
    }

}
