package huix.infinity.mixin.world.block;


import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Blocks.class)
public class BlocksInjected_1 {
    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SnowLayerBlock;"), method = "<clinit>")
    private static SnowLayerBlock ifw_snowWithoutTools(BlockBehaviour.Properties properties) {
        return new SnowLayerBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).sound(SoundType.SNOW)
                        .isViewBlocking((state, getter, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8)
                        .pushReaction(PushReaction.DESTROY));
    }


}
