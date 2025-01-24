package huix.infinity.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handle(final ClientboundSetHealthPayload payload, final IPayloadContext context) {
        final Player player = Minecraft.getInstance().player;
        assert player != null;
        player.getFoodData().ifw_maxFoodLevel(payload.maxFood());
        player.getFoodData().ifw_phytonutrients(payload.phytonutrients());
        player.getFoodData().ifw_protein(payload.protein());
        player.getFoodData().ifw_nutritionalStatusByINT(payload.nutritionalStatus());
    }
}
