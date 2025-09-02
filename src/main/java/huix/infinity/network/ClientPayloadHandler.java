package huix.infinity.network;

import huix.infinity.common.world.curse.CurseType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ClientPayloadHandler {

    public static void handleFood(final ClientBoundSetFoodPayload payload, final IPayloadContext context) {
        final LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.getFoodData().ifw_maxFoodLevel(payload.maxFood());
            player.getFoodData().ifw_phytonutrients(payload.phytonutrients());
            player.getFoodData().ifw_protein(payload.protein());
            player.getFoodData().ifw_nutritionalStatusByINT(payload.nutritionalStatus());
            player.getFoodData().ifw_insulinResponse(payload.insulinResponse());
        }
    }

    public static void handleCurse(final ClientBoundSetCursePayload payload, final IPayloadContext context) {
        final LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            CurseType curseType = CurseType.values()[payload.curseID()];
            player.ifw$setCurse(curseType);
        }
    }
}