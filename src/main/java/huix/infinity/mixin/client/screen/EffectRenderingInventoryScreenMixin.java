package huix.infinity.mixin.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import huix.infinity.common.world.effect.IFWMobEffects;
import huix.infinity.util.IFWConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Custom large (120x32) panels stacked above vanilla effects:
 * Order: Curse -> Insulin Resistance -> Malnourished.
 * Each panel height = 33. We push vanilla effect list downward by total custom height.
 * Insulin stage now derived FIRST from active MobEffectInstance amplifier (0..2) -> stage(1..3).
 * If no active effect present, fallback to FoodData thresholds (mild/moderate/severe) so panel
 * still appears during same tick a new stage starts (optional).
 * Tooltip for insulin shows only the stage description, panel shows title + description.
 * Malnourished panel tooltip shows solutions only.
 * All backgrounds are forced to the LARGE sprite; no small 32x32 panels are used.
 */
@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin extends AbstractContainerScreen {

    /* Offsets (relative stacking order) */
    @Unique private int curseOffset = -1;
    @Unique private int insulinOffset = -1;
    @Unique private int malOffset = -1;

    /* Total custom vertical shift applied to vanilla effects via redirect */
    @Unique private int customTotalHeight = 0;

    /* Cached insulin stage for this frame (0 = none, 1..3 = stage) */
    @Unique private int insulinStage = 0;

    @Unique private static final int PANEL_HEIGHT = 33;
    @Unique private static final int LARGE_PANEL_WIDTH = 120;
    @Unique private static final int ICON_X_INSET = 6;
    @Unique private static final int ICON_Y_INSET = 7;
    @Unique private static final int TEXT_LEFT_OFFSET = 28;
    @Unique private static final int MAL_THRESHOLD = 8000;

    public EffectRenderingInventoryScreenMixin(AbstractContainerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    /* Compute which custom panels will be drawn; done before vanilla renders effects. */
    @Inject(method = "render", at = @At("HEAD"))
    private void ifw$computePanels(GuiGraphics g, int mouseX, int mouseY, float partial, CallbackInfo ci) {
        Player p = this.minecraft.player;
        curseOffset = insulinOffset = malOffset = -1;
        customTotalHeight = 0;
        insulinStage = 0;

        if (p == null) return;

        int stack = 0;

        if (p.hasCurse()) {
            curseOffset = stack;
            stack += PANEL_HEIGHT;
        }

        // Determine insulin stage primarily from active effect amplifier.
        MobEffectInstance insulinInst = p.getEffect(IFWMobEffects.insulin_resistance);
        if (insulinInst != null) {
            insulinStage = insulinInst.getAmplifier() + 1; // amplifier 0..2 -> stage 1..3
            if (insulinStage < 1 || insulinStage > 3) insulinStage = 1;
        } else {
            // Fallback to FoodData stage detection (PlayerExtension thresholds) if effect missing.
            if (p.suffering_insulinResistance_severe()) insulinStage = 3;
            else if (p.suffering_insulinResistance_moderate()) insulinStage = 2;
            else if (p.suffering_insulinResistance_mild()) insulinStage = 1;
        }

        if (insulinStage > 0) {
            insulinOffset = stack;
            stack += PANEL_HEIGHT;
        }

        if (p.sufferingMalnutrition()) {
            malOffset = stack;
            stack += PANEL_HEIGHT;
        }

        customTotalHeight = stack;
    }

    /* After vanilla screen render, draw custom panels (they occupy the vertical space we pushed). */
    @Inject(method = "render", at = @At("RETURN"))
    private void ifw$drawPanels(GuiGraphics g, int mouseX, int mouseY, float partial, CallbackInfo ci) {
        Player p = this.minecraft.player;
        if (p == null) return;

        int panelBaseX = this.leftPos + this.imageWidth + 2;

        // Require the layout to have enough room for large panel width. If too narrow, skip entirely.
        if (this.width - panelBaseX < LARGE_PANEL_WIDTH) return;

        // Curse
        if (curseOffset >= 0) {
            int baseY = this.topPos + curseOffset;
            drawLargeBackground(g, panelBaseX, baseY);
            drawCursePanel(g, p, panelBaseX, baseY, mouseX, mouseY);
        }

        // Insulin
        if (insulinOffset >= 0 && insulinStage > 0) {
            int baseY = this.topPos + insulinOffset;
            drawLargeBackground(g, panelBaseX, baseY);
            drawInsulinPanel(g, insulinStage, panelBaseX, baseY, mouseX, mouseY);
        }

        // Malnourished
        if (malOffset >= 0) {
            int baseY = this.topPos + malOffset;
            drawLargeBackground(g, panelBaseX, baseY);
            drawMalnourishedPanel(g, p, panelBaseX, baseY, mouseX, mouseY);
        }
    }

    /* Push vanilla potion/effect list down so it does not overlap our custom panels. */
    @Redirect(method = {"renderBackgrounds","renderEffects","renderIcons","renderLabels"},
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;topPos:I"))
    private int ifw$redirectTop(EffectRenderingInventoryScreen self) {
        return this.topPos + this.customTotalHeight;
    }

    /* Reduce vanilla row capacity proportionally (optional – you requested previous behavior).
       If you do NOT want this reduction anymore, remove this method. */
    @ModifyConstant(method = "renderEffects", constant = @Constant(intValue = 5))
    private int ifw$shrinkVanilla(int original) {
        Player p = this.minecraft.player;
        if (p == null) return original;
        int panels = 0;
        if (p.hasCurse()) panels++;
        if (insulinStage > 0) panels++;
        if (p.sufferingMalnutrition()) panels++;
        int result = original - panels;
        return Math.max(result, 1);
    }

    /* ===================== Panel Drawing ===================== */

    @Unique
    private void drawLargeBackground(GuiGraphics g, int x, int y) {
        g.blitSprite(EFFECT_BACKGROUND_LARGE_SPRITE, x, y, LARGE_PANEL_WIDTH, 32);
    }

    /* Curse panel */
    @Unique
    private void drawCursePanel(GuiGraphics g, Player p, int baseX, int baseY, int mouseX, int mouseY) {
        int ix = baseX + ICON_X_INSET;
        int iy = baseY + ICON_Y_INSET;
        blitIcon(g, IFWConstants.WITCH_CURSE_ICON, ix, iy);

        int tx = baseX + TEXT_LEFT_OFFSET;
        int ty = baseY + 6;
        g.drawString(this.font, Component.translatable("effect.ifw.curse"), tx, ty, 0xFFFFFF);
        Component line = p.knownCurse()
                ? Component.translatable("curse.ifw." + p.getCurse().name())
                : Component.translatable("effect.ifw.unknown.curse");
        g.drawString(this.font, line, tx, ty + this.font.lineHeight, 0xAA00FF);

        if (iconHovered(mouseX, mouseY, ix, iy)) {
            List<Component> tooltip = new ArrayList<>();
            if (p.knownCurse())
                tooltip.add(Component.translatable("curse.ifw." + p.getCurse().name() + ".desc"));
            else
                tooltip.add(Component.translatable("curse.ifw.unknown.desc"));
            g.renderTooltip(this.font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    /* Insulin panel (single icon; stage controls text). */
    @Unique
    private void drawInsulinPanel(GuiGraphics g, int stage, int baseX, int baseY, int mouseX, int mouseY) {
        int ix = baseX + ICON_X_INSET;
        int iy = baseY + ICON_Y_INSET;
        blitIcon(g, IFWConstants.INSULIN_RESISTANCE_ICON, ix, iy);

        int tx = baseX + TEXT_LEFT_OFFSET;
        int ty = baseY + (32 - this.font.lineHeight) / 2;
        Component title = Component.translatable("effect.ifw.insulin_resistance.stage" + stage);
        Component desc  = Component.translatable("effect.ifw.insulin_resistance.stage" + stage + ".desc");
        g.drawString(this.font, title, tx, ty, 0xFFFFFF);

        if (iconHovered(mouseX, mouseY, ix, iy)) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(title);
            tooltip.add(desc.plainCopy().withStyle(style -> style.withColor(0xFFAAFF)));
            g.renderTooltip(this.font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    /* Malnourished panel */
    @Unique
    private void drawMalnourishedPanel(GuiGraphics g, Player p, int baseX, int baseY, int mouseX, int mouseY) {
        int ix = baseX + ICON_X_INSET;
        int iy = baseY + ICON_Y_INSET;
        blitIcon(g, IFWConstants.MALNOURISHED_ICON, ix, iy);

        int tx = baseX + TEXT_LEFT_OFFSET;
        int ty = baseY + 6;
        g.drawString(this.font, Component.translatable("effect.ifw.malnourished"), tx, ty, 0xFFFFFF);
        g.drawString(this.font, Component.translatable("effect.ifw.malnourished.desc"),
                tx, ty + this.font.lineHeight, 0xFFC040);

        if (iconHovered(mouseX, mouseY, ix, iy)) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.translatable("effect.ifw.malnourished.general"));
            int protein = p.getFoodData().ifw_protein();
            int phyto   = p.getFoodData().ifw_phytonutrients();
            boolean proteinLow = protein <= 0 || protein < MAL_THRESHOLD;
            boolean phytoLow = phyto <= 0 || phyto < MAL_THRESHOLD;

            if (proteinLow && phytoLow) {
                tooltip.add(Component.translatable("effect.ifw.malnourished.protein").plainCopy().withStyle(style -> style.withColor(0xFF8090)));
                tooltip.add(Component.translatable("effect.ifw.malnourished.phytonutrients").plainCopy().withStyle(style -> style.withColor(0xFF8090)));
            } else {
                if (proteinLow)
                    tooltip.add(Component.translatable("effect.ifw.malnourished.protein").plainCopy().withStyle(style -> style.withColor(0xFFD700)));
                if (phytoLow)
                    tooltip.add(Component.translatable("effect.ifw.malnourished.phytonutrients").plainCopy().withStyle(style -> style.withColor(0x60FF60)));
            }
            g.renderTooltip(this.font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    /* ===================== Helpers ===================== */

    @Unique
    private boolean iconHovered(int mx, int my, int ix, int iy) {
        return mx >= ix && mx <= ix + 18 && my >= iy && my <= iy + 18;
    }

    @Unique
    private void blitIcon(GuiGraphics g, ResourceLocation tex, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, tex);
        g.blit(tex, x, y, 0,0,18,18,18,18);
    }

    /* Vanilla resources */
    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_LARGE_SPRITE;
    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_SMALL_SPRITE; // unused but must exist
    @Shadow protected abstract void renderBackgrounds(GuiGraphics g, int renderX, int yOffset, Iterable<MobEffectInstance> effects, boolean isSmall);
}