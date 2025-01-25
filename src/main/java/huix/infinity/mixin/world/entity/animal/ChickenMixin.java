package huix.infinity.mixin.world.entity.animal;

import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( Chicken.class )
public class ChickenMixin {

    @Overwrite
    protected int getBaseExperienceReward() {
        return 0;
    }

}
