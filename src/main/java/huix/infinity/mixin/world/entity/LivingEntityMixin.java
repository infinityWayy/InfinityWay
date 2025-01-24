package huix.infinity.mixin.world.entity;

import huix.infinity.util.ReflectHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( LivingEntity.class )
public class LivingEntityMixin {



    @Shadow
    public double getAttributeValue(Holder<Attribute> attribute) {
        return 0.0D;
    }
}
