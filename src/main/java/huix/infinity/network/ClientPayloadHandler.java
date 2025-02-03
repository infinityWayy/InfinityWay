package huix.infinity.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public final class ClientPayloadHandler {

    public static void handle(final ClientBoundSetHealthPayload payload, final IPayloadContext context) {
        @SuppressWarnings("resource")
        final LocalPlayer player = Minecraft.getInstance().player;
        player.getFoodData().ifw_maxFoodLevel(payload.maxFood());
        player.getFoodData().ifw_phytonutrients(payload.phytonutrients());
        player.getFoodData().ifw_protein(payload.protein());
        player.getFoodData().ifw_nutritionalStatusByINT(payload.nutritionalStatus());
    }
}
