package huix.infinity.mixin.world.block;

import huix.infinity.common.world.block.GravelBlock;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( Blocks.class )
public class BlocksInjected {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 0), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_stoneHardness(BlockBehaviour.Properties properties, float destroyTime, float explosionResistance) {
        return properties.strength(2.4F, 6.0F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 33), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_obsidianHardness(BlockBehaviour.Properties properties, float destroyTime, float explosionResistance) {
        return properties.strength(2.4F, 1200.0F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 8), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_shortGrassHardness(BlockBehaviour.Properties properties) {
        return properties.strength(0.1F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 54), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_tallGrassHardness(BlockBehaviour.Properties properties) {
        return properties.strength(0.1F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 9), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_fernHardness(BlockBehaviour.Properties properties) {
        return properties.strength(0.1F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 55), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_largeFernHardness(BlockBehaviour.Properties properties) {
        return properties.strength(0.1F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 172), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_cryingObsidianHardness(BlockBehaviour.Properties properties, float destroyTime, float explosionResistance) {
        return properties.strength(2.4F, 1200.0F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "log(Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;)Lnet/minecraft/world/level/block/Block;")
    private static BlockBehaviour.Properties ifw_logWithTool_0(BlockBehaviour.Properties properties, float strength) {
        return properties.strength(1.2F).requiresCorrectToolForDrops();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "log(Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/block/SoundType;)Lnet/minecraft/world/level/block/Block;")
    private static BlockBehaviour.Properties ifw_logWithTool_1(BlockBehaviour.Properties properties, float strength) {
        return properties.strength(1.2F).requiresCorrectToolForDrops();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "leaves")
    private static BlockBehaviour.Properties ifw_leavesHardness(BlockBehaviour.Properties properties, float strength) {
        return properties.strength(0.6F);
    }

    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/util/ColorRGBA;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/ColoredFallingBlock;"
            , ordinal = 2), method = "<clinit>")
    private static ColoredFallingBlock ifw_rebuildGravel(ColorRGBA dustColor, BlockBehaviour.Properties properties) {
        return new GravelBlock(dustColor, properties);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;", ordinal = 0), method = "<clinit>")
    private static UniformInt ifw_noCoalDropXp_0(int minInclusive, int maxInclusive) {
        return UniformInt.of(0, 0);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;", ordinal = 1), method = "<clinit>")
    private static UniformInt ifw_noCoalDropXp_1(int minInclusive, int maxInclusive) {
        return UniformInt.of(0, 0);
    }
}
