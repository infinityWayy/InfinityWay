package huix.infinity.common.world.food;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record IFWFoodProperties(int phytonutrients, int protein, int insulinResponse, boolean meat, boolean snack, boolean soup) {

    public static final Codec<IFWFoodProperties> DIRECT_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("phytonutrients").forGetter(IFWFoodProperties::phytonutrients),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("protein").forGetter(IFWFoodProperties::protein),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("insulinResponse").forGetter(IFWFoodProperties::insulinResponse),
                            Codec.BOOL.fieldOf("meat").forGetter(IFWFoodProperties::meat),
                            Codec.BOOL.fieldOf("snack").forGetter(IFWFoodProperties::snack),
                            Codec.BOOL.fieldOf("soup").forGetter(IFWFoodProperties::soup)
                    )
                    .apply(instance, IFWFoodProperties::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, IFWFoodProperties> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, IFWFoodProperties::phytonutrients,
            ByteBufCodecs.VAR_INT, IFWFoodProperties::protein,
            ByteBufCodecs.VAR_INT, IFWFoodProperties::insulinResponse,
            ByteBufCodecs.BOOL, IFWFoodProperties::meat,
            ByteBufCodecs.BOOL, IFWFoodProperties::snack,
            ByteBufCodecs.BOOL, IFWFoodProperties::soup,
            IFWFoodProperties::new
    );

    public static class Builder {
        private int phytonutrients;
        private int protein;
        private int insulinResponse;
        private boolean meat;
        private boolean snack;
        private boolean soup;

        public IFWFoodProperties.Builder phytonutrients(int i) {
            this.phytonutrients = i;
            return this;
        }

        public IFWFoodProperties.Builder protein(int i) {
            this.protein = i;
            return this;
        }

        public IFWFoodProperties.Builder insulinResponse(int i) {
            this.insulinResponse = i;
            return this;
        }

        public Builder meat() {
            this.meat = true;
            return this;
        }

        public Builder snack() {
            this.snack = true;
            return this;
        }

        public Builder soup() {
            this.soup = true;
            return this;
        }

        public IFWFoodProperties build() {
            return new IFWFoodProperties(this.phytonutrients, this.protein, this.insulinResponse, this.meat, this.snack, this.soup);
        }
    }
}
