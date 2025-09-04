package huix.infinity.mixin.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import huix.infinity.extension.func.PlayerExtension;
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
import net.minecraft.world.food.FoodData;
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
 */
@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin extends AbstractContainerScreen <AbstractContainerMenu> {

    /* Offsets (relative stacking order) */
    @Unique private int ifw$curseOffset = -1;
    @Unique private int ifw$insulinOffset = -1;
    @Unique private int ifw$malOffset = -1;

    /* Total custom vertical shift applied to vanilla effects via redirect */
    @Unique private int ifw$customTotalHeight = 0;

    @Unique private int ifw$insulinStage = 0;

    @Unique private static final int PANEL_HEIGHT = 33;
    @Unique private static final int LARGE_PANEL_WIDTH = 120;
    @Unique private static final int ICON_X_INSET = 6;
    @Unique private static final int ICON_Y_INSET = 7;
    @Unique private static final int TEXT_LEFT_OFFSET = 28;
    @Unique private static final int MAL_THRESHOLD = 0;

    public EffectRenderingInventoryScreenMixin(AbstractContainerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    /* Compute which custom panels will be drawn; done before vanilla renders effects. */
    @Inject(method = "render", at = @At("HEAD"))
    private void ifw$computePanels(GuiGraphics g, int mouseX, int mouseY, float partial, CallbackInfo ci) {

        if (this.minecraft == null) return;
        Player player = this.minecraft.player;
        if (player == null) return;
        ifw$curseOffset = ifw$insulinOffset = ifw$malOffset = -1;
        ifw$customTotalHeight = 0;
        ifw$insulinStage = 0;

        int stack = 0;

        if (player.hasCurse()) {
            ifw$curseOffset = stack;
            stack += PANEL_HEIGHT;
        }

        if (player instanceof PlayerExtension px) {
            if (px.suffering_insulinResistance_mild()) ifw$insulinStage = 1;
            else if (px.suffering_insulinResistance_moderate()) ifw$insulinStage = 2;
            else if (px.suffering_insulinResistance_severe()) ifw$insulinStage = 3;
        } else {
            ifw$insulinStage = 0;
        }

        if (ifw$insulinStage > 0) {
            ifw$insulinOffset = stack;
            stack += PANEL_HEIGHT;
        }

        if (player.sufferingMalnutrition()) {
            ifw$malOffset = stack;
            stack += PANEL_HEIGHT;
        }

        ifw$customTotalHeight = stack;
    }

    /* After vanilla screen render, draw custom panels (they occupy the vertical space we pushed). */
    @Inject(method = "render", at = @At("RETURN"))
    private void ifw$drawPanels(GuiGraphics g, int mouseX, int mouseY, float partial, CallbackInfo ci) {

        if (this.minecraft == null) return;
        Player p = this.minecraft.player;
        if (p == null) return;

        int panelBaseX = this.leftPos + this.imageWidth + 2;

        // Require the layout to have enough room for large panel width. If too narrow, skip entirely.
        if (this.width - panelBaseX < LARGE_PANEL_WIDTH) return;

        // Curse
        if (ifw$curseOffset >= 0) {
            int baseY = this.topPos + ifw$curseOffset;
            ifw$drawLargeBackground(g, panelBaseX, baseY);
            ifw$drawCursePanel(g, p, panelBaseX, baseY, mouseX, mouseY);
        }

        // Insulin
        if (ifw$insulinOffset >= 0 && ifw$insulinStage > 0) {
            int baseY = this.topPos + ifw$insulinOffset;
            ifw$drawLargeBackground(g, panelBaseX, baseY);
            ifw$drawInsulinPanel(g, ifw$insulinStage, panelBaseX, baseY, mouseX, mouseY);
        }

        // Malnourished
        if (ifw$malOffset >= 0) {
            int baseY = this.topPos + ifw$malOffset;
            ifw$drawLargeBackground(g, panelBaseX, baseY);
            ifw$drawMalnourishedPanel(g, p, panelBaseX, baseY, mouseX, mouseY);
        }
    }

    /* Push vanilla potion/effect list down so it does not overlap our custom panels. */
    @Redirect(method = {"renderBackgrounds","renderEffects","renderIcons","renderLabels"},
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;topPos:I"))
    private int ifw$redirectTop(EffectRenderingInventoryScreen <AbstractContainerMenu> self) {
        return this.topPos + this.ifw$customTotalHeight;
    }

    /* Reduce vanilla row capacity proportionally */
    @ModifyConstant(method = "renderEffects", constant = @Constant(intValue = 5))
    private int ifw$shrinkVanilla(int original) {
        if (this.minecraft == null) return original;
        Player p = this.minecraft.player;
        if (p == null) return original;
        int panels = 0;
        if (p.hasCurse()) panels++;
        if (ifw$insulinStage > 0) panels++;
        if (p.sufferingMalnutrition()) panels++;
        int result = original - panels;
        return Math.max(result, 1);
    }

    /* ===================== Panel Drawing ===================== */

    @Unique
    private void ifw$drawLargeBackground(GuiGraphics g, int x, int y) {

        g.blitSprite(EFFECT_BACKGROUND_LARGE_SPRITE, x, y, LARGE_PANEL_WIDTH, 32);
    }

    /* Curse panel */
    @Unique
    private void ifw$drawCursePanel(GuiGraphics g, Player p, int baseX, int baseY, int mouseX, int mouseY) {

        int ix = baseX + ICON_X_INSET;
        int iy = baseY + ICON_Y_INSET;
        ifw$blitIcon(g, IFWConstants.WITCH_CURSE_ICON, ix, iy);

        int tx = baseX + TEXT_LEFT_OFFSET;
        int ty = baseY + 6;
        g.drawString(this.font, Component.translatable("effect.ifw.curse"), tx, ty, 0xFFFFFF);
        Component line = p.knownCurse()
                ? Component.translatable("curse.ifw." + p.getCurse().name())
                : Component.translatable("curse.ifw.unknown.curse");
        g.drawString(this.font, line, tx, ty + this.font.lineHeight, 0xAA00FF);

        if (ifw$iconHovered(mouseX, mouseY, ix, iy)) {
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
    private void ifw$drawInsulinPanel(GuiGraphics g, int stage, int baseX, int baseY, int mouseX, int mouseY) {

        int ix = baseX + ICON_X_INSET;
        int iy = baseY + ICON_Y_INSET;

        // Sugar icon alpha varies with stage
        float[] sugarAlpha = {0.33f, 0.66f, 1.0f}; // mild, moderate, severe
        float sugarAlphaValue = sugarAlpha[Math.max(0, Math.min(stage-1, 2))];

        ifw$blitIconWithAlpha(g, ix, iy, sugarAlphaValue);

        int tx = baseX + TEXT_LEFT_OFFSET;
        int ty = baseY + 6;
        Component title = switch (stage) {
            case 1 -> Component.translatable("effect.ifw.insulin_resistance.stage1");
            case 2 -> Component.translatable("effect.ifw.insulin_resistance.stage2");
            case 3 -> Component.translatable("effect.ifw.insulin_resistance.stage3");
            default -> Component.empty();
        };
        Component desc  = switch (stage) {
            case 1 -> Component.translatable("effect.ifw.insulin_resistance.stage1.desc");
            case 2 -> Component.translatable("effect.ifw.insulin_resistance.stage2.desc");
            case 3 -> Component.translatable("effect.ifw.insulin_resistance.stage3.desc");
            default -> Component.empty();
        };
        g.drawString(this.font, title, tx, ty, 0xFFFFFF);

        String timeString = "";
        if (this.minecraft == null) return;
        Player player = this.minecraft.player;
        if (player == null) return;
        FoodData foodData = player.getFoodData();
        int insulinResponse = foodData.ifw_insulinResponse();
        float start = 0f, times = 0f, now = 0f;
        switch (stage) {
            case 1: // mild
                start = 600.0F; times = 5400.0F; now = 48000; break;
            case 2: // moderate
                start = 5400.0F; times = 3600.0F; now = 96000; break;
            case 3: // severe
                start = 9000.0F; times = 5400.0F; now = 144000; break;
            default:
        }
        if (stage >= 1 && stage <= 3) {
            int duration = (int)(start + times * (1.0F - 1.0E-5F * (now - insulinResponse)));
            duration = Math.max(duration, 0);
            timeString = String.format("%d:%02d", duration / 60, duration % 60);
        }
        g.drawString(this.font, Component.literal(timeString), tx, ty + this.font.lineHeight, 0xFFFFFF);

        if (ifw$iconHovered(mouseX, mouseY, ix, iy)) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(desc.plainCopy().withStyle(style -> style.withColor(0xFFAAFF)));
            g.renderTooltip(this.font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    @Unique
    private void ifw$blitIconWithAlpha(GuiGraphics g, int x, int y, float sugarAlpha) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Sugar image: white, alpha varies with insulin resistance level
        RenderSystem.setShaderColor(1f, 1f, 1f, sugarAlpha);
        RenderSystem.setShaderTexture(0, IFWConstants.SUGAR_ICON);
        g.blit(IFWConstants.SUGAR_ICON, x, y, 0, 0, 18, 18, 18, 18);

        // Frame image: bright yellow, fully opaque
        RenderSystem.setShaderColor(1f, 1f, 0.4f, 1f);
        RenderSystem.setShaderTexture(0, IFWConstants.INSULIN_RESISTANCE_ICON);
        g.blit(IFWConstants.INSULIN_RESISTANCE_ICON, x, y, 0, 0, 18, 18, 18, 18);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    /* Malnourished panel */
    @Unique
    private void ifw$drawMalnourishedPanel(GuiGraphics g, Player p, int baseX, int baseY, int mouseX, int mouseY) {

        int ix = baseX + ICON_X_INSET;
        int iy = baseY + ICON_Y_INSET;
        ifw$blitIcon(g, IFWConstants.MALNOURISHED_ICON, ix, iy);

        int tx = baseX + TEXT_LEFT_OFFSET;
        int ty = baseY + 6;
        g.drawString(this.font, Component.translatable("effect.ifw.malnourished"), tx, ty, 0xFFFFFF);

        long millis = System.currentTimeMillis();
        int interval = 5000;
        int which = (int)((millis / interval) % 2) + 1;
        Component desc = Component.translatable("effect.ifw.malnourished.desc" + which);
        g.drawString(this.font, desc, tx, ty + this.font.lineHeight, 0xFFC040);

        if (ifw$iconHovered(mouseX, mouseY, ix, iy)) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.translatable("effect.ifw.malnourished.general"));
            int protein = p.getFoodData().ifw_protein();
            int phyto   = p.getFoodData().ifw_phytonutrients();
            boolean proteinLow = protein <= MAL_THRESHOLD;
            boolean phytoLow = phyto <= MAL_THRESHOLD;

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
    private boolean ifw$iconHovered(int mx, int my, int ix, int iy) {
        return mx >= ix && mx <= ix + 18 && my >= iy && my <= iy + 18;
    }

    @Unique
    private void ifw$blitIcon(GuiGraphics g, ResourceLocation tex, int x, int y) {
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