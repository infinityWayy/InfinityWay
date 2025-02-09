package huix.infinity.common.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class EnchantmentSerializer<T extends EnchantmentRecipe> implements RecipeSerializer<T> {
    private final EnchantmentRecipe.Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public EnchantmentSerializer(EnchantmentRecipe.Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(EnchantmentRecipe::ingredient),
                                ItemStack.CODEC.fieldOf("result").forGetter(EnchantmentRecipe::result),
                                Codec.FLOAT.fieldOf("experience").forGetter(EnchantmentRecipe::experience)
                        )
                        .apply(instance, factory::create)
        );
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    private T fromNetwork(RegistryFriendlyByteBuf byteBuf) {
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);
        ItemStack itemstack = ItemStack.STREAM_CODEC.decode(byteBuf);
        float experience = byteBuf.readFloat();
        return this.factory.create(ingredient, itemstack, experience);
    }

    private void toNetwork(RegistryFriendlyByteBuf byteBuf, T instance) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, instance.ingredient());
        ItemStack.STREAM_CODEC.encode(byteBuf, instance.result());
        byteBuf.writeFloat(instance.experience());
    }

    public EnchantmentRecipe create(Ingredient ingredient, ItemStack result, float experience) {
        return this.factory.create(ingredient, result, experience);
    }
}
