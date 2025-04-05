package huix.infinity.network;

import huix.infinity.common.world.curse.Curses;
import huix.infinity.common.world.curse.PersistentEffectInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;


public final class ClientPayloadHandler {

    public static void handleFood(final ClientBoundSetFoodPayload payload, final IPayloadContext context) {
        @SuppressWarnings("resource")
        final LocalPlayer player = Minecraft.getInstance().player;
        player.getFoodData().ifw_maxFoodLevel(payload.maxFood());
        player.getFoodData().ifw_phytonutrients(payload.phytonutrients());
        player.getFoodData().ifw_protein(payload.protein());
        player.getFoodData().ifw_nutritionalStatusByINT(payload.nutritionalStatus());
    }

    public static void handleCurse(final ClientBoundSetCursePayload payload, final IPayloadContext context) {
        @SuppressWarnings("resource")
        final LocalPlayer player = Minecraft.getInstance().player;
        final Level level = Minecraft.getInstance().level;
        player.curse(payload.curse());
        if (!payload.curse().persistentEff().value().equals(Curses.none.value())) {
            level.addParticle(ParticleTypes.WITCH, player.getX(), player.getY(), player.getZ(), 0.0, 0.0, 0.0);
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.WITCH_AMBIENT, SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }
}
