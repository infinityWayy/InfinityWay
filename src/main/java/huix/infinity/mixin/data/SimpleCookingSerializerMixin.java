package huix.infinity.mixin.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( SimpleCookingSerializer.class )
public class SimpleCookingSerializerMixin<T extends AbstractCookingRecipe> {
    @Shadow
    @Final
    @Mutable
    private MapCodec<T> codec;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/codec/StreamCodec;of(Lnet/minecraft/network/codec/StreamEncoder;Lnet/minecraft/network/codec/StreamDecoder;)Lnet/minecraft/network/codec/StreamCodec;")
            , method = "<init>")
    private void ifw_cookingLevel(AbstractCookingRecipe.Factory<T> factory, int cookingTime, CallbackInfo ci) {
        this.codec = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(AbstractCookingRecipe::getGroup),
                                CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(t -> t.category),
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(t -> t.ingredient),
                                ItemStack.CODEC.fieldOf("result").forGetter(t -> t.result),
                                Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AbstractCookingRecipe::getExperience),
                                Codec.INT.fieldOf("cookingTime").orElse(cookingTime).forGetter(AbstractCookingRecipe::getCookingTime),
                                Codec.INT.fieldOf("cookingLevel").orElse(cookingTime).forGetter(AbstractCookingRecipe::cookingLevel)
                        ).apply(instance, factory::create)
        );
    }
}
