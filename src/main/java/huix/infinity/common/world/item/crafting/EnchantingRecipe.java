package huix.infinity.common.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public record EnchantingRecipe(ItemStack ingredient, ItemStack result, int experience) {

    public static final Codec<EnchantingRecipe> DIRECT_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ItemStack.CODEC.fieldOf("ingredient").forGetter(EnchantingRecipe::ingredient),
                            ItemStack.CODEC.fieldOf("result").orElse(new ItemStack(Items.BAMBOO)).forGetter(EnchantingRecipe::result),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("experience").forGetter(EnchantingRecipe::experience)
                    )
                    .apply(instance, EnchantingRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantingRecipe> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, EnchantingRecipe::ingredient,
            ItemStack.STREAM_CODEC, EnchantingRecipe::result,
            ByteBufCodecs.VAR_INT, EnchantingRecipe::experience,
            EnchantingRecipe::new
    );

    public static class Builder {
        private ItemStack ingredient;
        private ItemStack result;
        private int experience;

        public ItemStack ingredient() {
            return this.ingredient;
        }

        public ItemStack result() {
            return this.result;
        }

        public int experience() {
            return this.experience;
        }

        public Builder ingredient(ItemStack ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        public Builder result(ItemStack result) {
            this.result = result;
            return this;
        }

        public Builder experience(int experience) {
            this.experience = experience;
            return this;
        }

        public EnchantingRecipe build() {
            return new EnchantingRecipe(this.ingredient(), this.result(), this.experience());
        }
    }
}
