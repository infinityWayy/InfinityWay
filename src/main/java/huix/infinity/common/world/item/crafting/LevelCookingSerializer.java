package huix.infinity.common.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public class LevelCookingSerializer<T extends LevelCookingRecipe> implements RecipeSerializer<T> {
    private final LevelCookingRecipe.Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public LevelCookingSerializer(LevelCookingRecipe.Factory<T> factory, int cookingTime) {
        this.factory  = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(LevelCookingRecipe::getGroup),
                        CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(t -> t.category),
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(p_300833_ -> p_300833_.ingredient),
                        ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter(p_300827_ -> p_300827_.result),
                        Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(LevelCookingRecipe::getExperience),
                        Codec.INT.fieldOf("cookingtime").orElse(cookingTime).forGetter(LevelCookingRecipe::getCookingTime),
                        Codec.INT.fieldOf("cookinglevel").orElse(0).forGetter(LevelCookingRecipe::cookingLevel))
                .apply(instance, factory::create)
        );

        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }


    @Override
    public @NotNull MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    private T fromNetwork(RegistryFriendlyByteBuf byteBuf) {
        String s = byteBuf.readUtf();
        CookingBookCategory cookingbookcategory = byteBuf.readEnum(CookingBookCategory.class);
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);
        ItemStack itemstack = ItemStack.STREAM_CODEC.decode(byteBuf);
        float f = byteBuf.readFloat();
        int i = byteBuf.readVarInt();
        int k = byteBuf.readVarInt();
        return this.factory.create(s, cookingbookcategory, ingredient, itemstack, f, i, k);
    }

    private void toNetwork(RegistryFriendlyByteBuf byteBuf, T t) {
        byteBuf.writeUtf(t.getGroup());
        byteBuf.writeEnum(t.category());
        Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, t.ingredient);
        ItemStack.STREAM_CODEC.encode(byteBuf, t.result);
        byteBuf.writeFloat(t.getExperience());
        byteBuf.writeVarInt(t.getCookingTime());
        byteBuf.writeVarInt(t.cookingLevel());
    }
}
