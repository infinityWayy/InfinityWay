package huix.infinity.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.ApiStatus;


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
