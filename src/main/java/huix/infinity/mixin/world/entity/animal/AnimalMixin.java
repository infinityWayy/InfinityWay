package huix.infinity.mixin.world.entity.animal;

import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( Animal.class )
public class AnimalMixin {

    @Overwrite
    protected int getBaseExperienceReward() {
        return 0;
    }
}
