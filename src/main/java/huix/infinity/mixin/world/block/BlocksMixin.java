package huix.infinity.mixin.world.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( Blocks.class )
public class BlocksMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 0), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_stoneHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.4F, 6.0F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 33), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_obsidianHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.4F, 1200.0F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 172), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_cryingObsidianHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.4F, 1200.0F).lightLevel((p_152651_) -> 10);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "log(Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;)Lnet/minecraft/world/level/block/Block;")
    private static BlockBehaviour.Properties ifw_logWithTool_0(BlockBehaviour.Properties instance, float strength) {
        return instance.strength(1.2F).requiresCorrectToolForDrops();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "log(Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/block/SoundType;)Lnet/minecraft/world/level/block/Block;")
    private static BlockBehaviour.Properties ifw_logWithTool_1(BlockBehaviour.Properties instance, float strength) {
        return instance.strength(1.2F).requiresCorrectToolForDrops();
    }
}
