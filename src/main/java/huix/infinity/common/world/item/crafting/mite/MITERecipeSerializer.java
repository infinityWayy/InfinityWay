package huix.infinity.common.world.item.crafting.mite;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * MITE配方序列化器
 */
public class MITERecipeSerializer implements RecipeSerializer<MITERecipe> {

    public static final MITERecipeSerializer INSTANCE = new MITERecipeSerializer();

    private MITERecipeSerializer() {}

    @Override
    public @NotNull MapCodec<MITERecipe> codec() {
        return MITERecipe.CODEC.fieldOf("mite_recipe");
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, MITERecipe> streamCodec() {
        return MITERecipe.STREAM_CODEC;
    }
}