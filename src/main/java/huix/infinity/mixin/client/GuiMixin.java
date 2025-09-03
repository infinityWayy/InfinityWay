package huix.infinity.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import huix.infinity.common.world.curse.CurseEffectHelper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow protected abstract boolean isExperienceBarVisible();

    @Shadow public abstract Font getFont();

    @ModifyConstant(constant = @Constant(intValue = 10), method = "renderFood")
    private int modifyFoodLevel(int constant, @Local FoodData foodData) {
        return foodData.ifw_maxFoodLevel() / 2;
    }

    @Inject(at = @At("RETURN"), method = "renderExperienceLevel")
    private void fixRenderXP(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci, @Local(ordinal = 0) int i) {
        if (this.isExperienceBarVisible() && i <= 0) {
            this.minecraft.getProfiler().push("expLevel");
            String s = "" + i;
            int j = (guiGraphics.guiWidth() - this.getFont().width(s)) / 2;
            int k = guiGraphics.guiHeight() - 31 - 4;
            guiGraphics.drawString(this.getFont(), s, j + 1, k, 0, false);
            guiGraphics.drawString(this.getFont(), s, j - 1, k, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k + 1, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k - 1, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k, i == 0 ? 8453920 : new Color(200, 50, 80).getRGB(), false);
            this.minecraft.getProfiler().pop();
        }
    }

    @Shadow protected abstract Player getCameraPlayer();
    @Final
    @Shadow
    private static ResourceLocation AIR_SPRITE;
    @Shadow
    public int rightHeight;

    @Inject(method = "renderAirLevel", at = @At("HEAD"), cancellable = true)
    private void ifw$renderAirLevel(GuiGraphics p_283143_, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            int i1 = p_283143_.guiWidth() / 2 + 91;
            int i3 = player.getMaxAirSupply();
            int j3 = Math.min(player.getAirSupply(), i3);

            if (player.isEyeInFluid(net.minecraft.tags.FluidTags.WATER) || j3 < i3) {
                int bubblesMax = CurseEffectHelper.getCursedBubbleMax(player, 10, i3);
                int bubblesCurrent = Mth.ceil((double) j3 * bubblesMax / (double) i3);
                int j2 = p_283143_.guiHeight() - this.rightHeight;
                RenderSystem.enableBlend();
                for (int j4 = 0; j4 < bubblesCurrent; ++j4) {
                    p_283143_.blitSprite(AIR_SPRITE, i1 - j4 * 8 - 9, j2, 9, 9);
                }
                RenderSystem.disableBlend();
                this.rightHeight += 10;
            }
            ci.cancel();
        }
    }
}
