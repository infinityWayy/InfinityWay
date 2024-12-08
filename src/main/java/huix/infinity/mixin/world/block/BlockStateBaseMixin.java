package huix.infinity.mixin.world.block;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.MapCodec;
import huix.infinity.funextension.BlockStateBaseExtension;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( BlockStateBase.class )
public class BlockStateBaseMixin implements BlockStateBaseExtension {
//    @Unique
//    @Final
//    @Mutable
//    private boolean ifw_portable;
//
//    @Inject(at = @At(value = "RETURN"), method = "<init>")
//    private void injectInit(Block owner, Reference2ObjectArrayMap values, MapCodec propertiesCodec, CallbackInfo ci, @Local BlockBehaviour.Properties props) {
//        ifw_portable = props.;
//    }
}
