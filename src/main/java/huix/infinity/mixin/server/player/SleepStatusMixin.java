package huix.infinity.mixin.server.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.stream.Stream;

@Mixin( SleepStatus.class )
public class SleepStatusMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Ljava/util/List;stream()Ljava/util/stream/Stream;")
            , method = "areEnoughDeepSleeping")
    private Stream<ServerPlayer> ifw_fixSleep(List<ServerPlayer> instance) {
        return instance.stream().filter(Player::canResetTimeBySleeping);
    }
}
