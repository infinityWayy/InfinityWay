package huix.infinity.mixin.world.block;

import huix.infinity.func_extension.BlockExtension;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( Block.class )
public abstract class BlockMixin implements BlockExtension {

}
