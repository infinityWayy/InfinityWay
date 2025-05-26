package huix.infinity.mixin.world.block;

import huix.infinity.common.world.block.GravelBlock;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Blocks.class)
public class BlocksInjected {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 0), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_stoneHardness(BlockBehaviour.Properties properties, float destroyTime, float explosionResistance) {
        return properties.strength(2.4F, 6.0F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "log(Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;)Lnet/minecraft/world/level/block/Block;")
    private static BlockBehaviour.Properties ifw_logHardness(BlockBehaviour.Properties properties, float strength) {
        return properties.strength(1.2F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;")
            , method = "log(Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/block/SoundType;)Lnet/minecraft/world/level/block/Block;")
    private static BlockBehaviour.Properties ifw_log1Hardness(BlockBehaviour.Properties properties, float strength) {
        return properties.strength(1.2F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 8), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_oak_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 9), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_spruce_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 10), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_birch_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 11), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_jungle_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 12), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_acacia_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 13), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_cherry_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 14), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_dark_oak_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 15), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_mangrove_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 16), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_bamboo_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 160), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_crimson_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 161), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_warped_plankHardness(BlockBehaviour.Properties instance, float destroyTime, float explosionResistance) {
        return instance.strength(0.8F, explosionResistance);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 23), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_coalOreHardness(BlockBehaviour.Properties properties, float destroyTime, float explosionResistance) {
        return properties.strength(1.2F, 3.0F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 33), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_obsidianHardness(BlockBehaviour.Properties properties, float destroyTime, float explosionResistance) {
        return properties.strength(2.4F, 12.0F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 30), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_torchHardness(BlockBehaviour.Properties properties) {
        return properties.instabreak().replaceable();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 31), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_wallTorchHardness(BlockBehaviour.Properties properties) {
        return properties.instabreak().replaceable();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 39), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_soulTorchHardness(BlockBehaviour.Properties properties) {
        return properties.instabreak().replaceable();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 40), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_soulWallTorchHardness(BlockBehaviour.Properties properties) {
        return properties.instabreak().replaceable();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 8), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_shortGrassHardness(BlockBehaviour.Properties properties) {
        return properties.strength(0.02F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 54), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_tallGrassHardness(BlockBehaviour.Properties properties) {
        return properties.strength(0.02F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 9), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_fernHardness(BlockBehaviour.Properties properties) {
        return properties.strength(0.02F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;instabreak()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 55), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_largeFernHardness(BlockBehaviour.Properties properties) {
        return properties.strength(0.02F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(FF)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            , ordinal = 172), method = "<clinit>")
    private static BlockBehaviour.Properties ifw_cryingObsidianHardness(BlockBehaviour.Properties properties, float destroyTime, float explosionResistance) {
        return properties.strength(2.4F, 12.0F);
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

    @Redirect(at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SnowLayerBlock;"), method = "<clinit>")
    private static SnowLayerBlock ifw_snowWithoutTools(BlockBehaviour.Properties properties) {
        return new SnowLayerBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).sound(SoundType.SNOW)
                        .isViewBlocking((state, getter, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8)
                        .pushReaction(PushReaction.DESTROY));
    }
}
