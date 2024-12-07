package huix.infinity.mixin.world.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( Blocks.class )
public class BlocksMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "log(Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;)Lnet/minecraft/world/level/block/Block;")
    private static BlockBehaviour.Properties ifw_logWithTool_0(BlockBehaviour.Properties instance, float strength) {
        return instance.strength(strength).requiresCorrectToolForDrops();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "log(Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/block/SoundType;)Lnet/minecraft/world/level/block/Block;")
    private static BlockBehaviour.Properties ifw_logWithTool_1(BlockBehaviour.Properties instance, float strength) {
        return instance.strength(strength).requiresCorrectToolForDrops();
    }
}
