package huix.infinity.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
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

}
