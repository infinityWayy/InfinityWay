package huix.infinity.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.food.FoodData;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

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

    @ModifyConstant(constant = @Constant(intValue = 10), method = "renderFood")
    private int modifyFoodLevel(int constant, @Local FoodData foodData) {
        return foodData.ifw_maxFoodLevel() / 2;
    }

    @ModifyConstant(constant = @Constant(intValue = 0), method = "renderExperienceLevel")
    private int fixRenderXP(int constant) {
        return -50;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"
                , ordinal = 4), method = "renderExperienceLevel")
    private int fixRenderXPRed(GuiGraphics instance, Font font, String text, int x, int y, int color, boolean dropShadow, @Local(ordinal = 0) int xp) {
        return instance.drawString(font, text, x, y, xp >= 0 ? color : new Color(200, 50, 80).getRGB(), dropShadow);
    }

}
