package huix.infinity.mixin.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( Gui.class )
public class GuiMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private boolean isExperienceBarVisible() {
        return false;
    }
    @Shadow
    public Font getFont() {
        return null;
    }


    @Overwrite
    private void renderExperienceLevel(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        int i = this.minecraft.player.experienceLevel;
        if (this.isExperienceBarVisible()) {
            this.minecraft.getProfiler().push("expLevel");
            String s = "" + i;
            int j = (guiGraphics.guiWidth() - this.getFont().width(s)) / 2;
            int k = guiGraphics.guiHeight() - 31 - 4;
            guiGraphics.drawString(this.getFont(), s, j + 1, k, 0, false);
            guiGraphics.drawString(this.getFont(), s, j - 1, k, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k + 1, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k - 1, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k, 8453920, false);
            this.minecraft.getProfiler().pop();
        }

    }
}
