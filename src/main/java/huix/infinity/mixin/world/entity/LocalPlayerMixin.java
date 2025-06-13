package huix.infinity.mixin.world.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.portal.DimensionTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( LocalPlayer.class )
public class LocalPlayerMixin extends AbstractClientPlayer {

    @Overwrite
    private boolean hasEnoughFoodToStartSprinting() {
        return this.isPassenger() || this.getFoodData().ifw_hasAnyEnergy() || this.mayFly();
    }

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }
}
