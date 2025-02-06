package huix.infinity.mixin.world.entity.animal;

import huix.infinity.func_extension.BucketableExtension;
import huix.infinity.util.ReflectHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.lang.ref.Reference;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin extends WaterAnimal implements BucketableExtension {

    @Overwrite
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        return BucketableExtension.ifw_bucketMobPickup(player, hand, ReflectHelper.dyCast(this)).orElse(super.mobInteract(player, hand));
    }

    protected AbstractFishMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
    }
}
