package huix.infinity.common.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public record EnchantmentRecipe(ItemStack ingredient, ItemStack result, int experience) {

    public static final Codec<EnchantmentRecipe> DIRECT_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ItemStack.CODEC.fieldOf("ingredient").forGetter(EnchantmentRecipe::ingredient),
                            ItemStack.CODEC.fieldOf("result").orElse(new ItemStack(Items.BAMBOO)).forGetter(EnchantmentRecipe::result),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("experience").forGetter(EnchantmentRecipe::experience)
                    )
                    .apply(instance, EnchantmentRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentRecipe> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, EnchantmentRecipe::ingredient,
            ItemStack.STREAM_CODEC, EnchantmentRecipe::result,
            ByteBufCodecs.VAR_INT, EnchantmentRecipe::experience,
            EnchantmentRecipe::new
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

        public EnchantmentRecipe build() {
            return new EnchantmentRecipe(this.ingredient(), this.result(), this.experience());
        }
    }
}
