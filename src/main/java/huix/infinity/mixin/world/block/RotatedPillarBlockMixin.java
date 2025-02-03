package huix.infinity.mixin.world.block;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(RotatedPillarBlock.class)
public class RotatedPillarBlockMixin {

    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;<init>(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V"), method = "<init>")
    private static void onConstruct(Args args) {
        BlockBehaviour.Properties o = args.get(0);
        args.set(0, o.requiresCorrectToolForDrops());
    }

}
