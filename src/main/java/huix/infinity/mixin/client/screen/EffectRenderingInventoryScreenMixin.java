package huix.infinity.mixin.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import huix.infinity.util.IFWConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin extends AbstractContainerScreen {

    @Unique
    private int curse_yOffset = 0;
    @Unique
    private int insulin_response_yOffset = 0;

    @Inject(at = @At(value = "RETURN"), method = "render")
    private void injectCurseRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Player player = this.minecraft.player;
        if (player.hasCurse()) {
            this.curse_yOffset = 33;
            int renderX = this.leftPos + this.imageWidth + 2;
            int j = this.width - renderX;
            if (j >= 32) {
                boolean flag = j >= 120;
                ScreenEvent.RenderInventoryMobEffects event = ClientHooks.onScreenPotionSize(this, j, !flag, renderX);
                if (event.isCanceled()) return;
                flag = !event.isCompact();
                renderX = event.getHorizontalOffset();

                // 先渲染背景和图标
                this.renderCurseBackgrounds(guiGraphics, renderX, this.curse_yOffset, flag);
                this.renderCurseIcons(guiGraphics, renderX, this.curse_yOffset, flag);
                if (flag) this.renderCurseLabels(guiGraphics, renderX, this.curse_yOffset, player);

                // 渲染 Tooltip，已识别只显示真实内容，未识别只显示未知内容
                int iconX = renderX + (flag ? 6 : 7);
                int iconY = this.topPos + 7;
                int iconWidth = 18, iconHeight = 18;
                if (mouseX >= iconX && mouseX <= iconX + iconWidth && mouseY >= iconY && mouseY <= iconY + iconHeight) {
                    var curse = player.getCurse();
                    java.util.List<Component> tooltip = new java.util.ArrayList<>();
                    if (player.knownCurse()) {
                        tooltip.add(Component.translatable("curse.ifw." + curse.name()));
                        tooltip.add(Component.translatable("curse.ifw." + curse.name() + ".desc"));
                    } else {
                        tooltip.add(Component.translatable("effect.unkonwn.curse"));
                        tooltip.add(Component.translatable("curse.ifw.unknown.desc"));
                    }
                    guiGraphics.renderTooltip(this.font, tooltip, java.util.Optional.empty(), mouseX, mouseY);
                }
            }
        }

        if (player.suffering_insulinResistance_mild()) {
            this.insulin_response_yOffset = 33; // 同级偏移量
            int renderX = this.leftPos + this.imageWidth + 2;
            int j = this.width - renderX;
            if (j >= 32) {
                boolean flag = j >= 120;
                ScreenEvent.RenderInventoryMobEffects event = ClientHooks.onScreenPotionSize(this, j, !flag, renderX);
                if (event.isCanceled()) return;
                flag = !event.isCompact();
                renderX = event.getHorizontalOffset();

                this.renderInsulinResponseBackgrounds(guiGraphics, renderX, this.insulin_response_yOffset, flag);
                this.renderInsulinResponseIcons(guiGraphics, renderX, this.insulin_response_yOffset, flag);
                if (flag)
                    this.renderInsulinResponseLabels(guiGraphics, renderX, this.insulin_response_yOffset, player);
            }
        }
    }

    //Curse
    @Unique
    private void renderCurseBackgrounds(GuiGraphics guiGraphics, int renderX, int yOffset, boolean isSmall) {
        if (isSmall) guiGraphics.blitSprite(EFFECT_BACKGROUND_LARGE_SPRITE, renderX, this.topPos, 120, 32);
        else guiGraphics.blitSprite(EFFECT_BACKGROUND_SMALL_SPRITE, renderX, this.topPos, 32, 32);
    }

    @Unique
    private void renderCurseIcons(GuiGraphics guiGraphics, int renderX, int yOffset, boolean isSmall) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, IFWConstants.LOCATION_P_EFFECT_TEXTURE_ATLAS);
        TextureAtlasSprite textureatlassprite = IFWConstants.persistentEffectTextureManager.getCurse();
        guiGraphics.blit(renderX + (isSmall ? 6 : 7), this.topPos + 7, 0, 18, 18, textureatlassprite);
    }

    @Unique
    private void renderCurseLabels(GuiGraphics guiGraphics, int renderX, int yOffset, Player player) {
        Component component = Component.translatable("effect.unkonwn.curse");
        Component curseDescription = Component.translatable("curse.ifw.unknown.desc");
        if (player.knownCurse()) {
            component = Component.translatable("curse.ifw." + player.getCurse().name());
            curseDescription = Component.translatable("curse.ifw." + player.getCurse().name() + ".desc");
        }
        guiGraphics.drawString(this.font, component, renderX + 10 + 18, this.topPos + 6, 16777215);
        guiGraphics.drawString(this.font, curseDescription, renderX + 10 + 18, this.topPos + 6 + this.font.lineHeight, 16711680);
    }

    //InsulinResponse
    @Unique
    private void renderInsulinResponseBackgrounds(GuiGraphics guiGraphics, int renderX, int yOffset, boolean isSmall) {
        if (isSmall)
            guiGraphics.blitSprite(EFFECT_BACKGROUND_LARGE_SPRITE, renderX, this.topPos + yOffset, 120, 32);
        else
            guiGraphics.blitSprite(EFFECT_BACKGROUND_SMALL_SPRITE, renderX, this.topPos + yOffset, 32, 32);
    }

    @Unique
    private void renderInsulinResponseIcons(GuiGraphics guiGraphics, int renderX, int yOffset, boolean isSmall) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, IFWConstants.LOCATION_P_EFFECT_TEXTURE_ATLAS);
        TextureAtlasSprite textureatlassprite = IFWConstants.persistentEffectTextureManager.getInsulinResistance();
        guiGraphics.blit(renderX + (isSmall ? 6 : 7), this.topPos + yOffset + 7, 0, 18, 18, textureatlassprite);
    }

    @Unique
    private void renderInsulinResponseLabels(GuiGraphics guiGraphics, int renderX, int yOffset, Player player) {
        Component component = Component.translatable("effect.insulin_resistance_mild");
        guiGraphics.drawString(this.font, component, renderX + 10 + 18, this.topPos + yOffset + 6, 0xFF55FF);
        // Component description = UnClearEffect.getDescriptionComponent();
        // guiGraphics.drawString(this.font, description, renderX + 10 + 18, this.topPos + yOffset + 6 + this.font.lineHeight, 0xCC5500);
    }

    @ModifyConstant(constant = @Constant(intValue = 5), method = "renderEffects")
    private int modifyRenderSize(int constant) {
        if (this.minecraft.player.hasCurse()) constant--;
        return constant;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;topPos:I"),
            method = {"renderBackgrounds", "renderEffects", "renderIcons", "renderLabels"})
    private int redirectTopPos(EffectRenderingInventoryScreen instance) {
        return this.topPos + this.curse_yOffset + this.insulin_response_yOffset; // 叠加偏移
    }

    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_LARGE_SPRITE;

    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_SMALL_SPRITE;

    @Shadow protected abstract void renderBackgrounds(GuiGraphics guiGraphics, int renderX, int yOffset, Iterable<MobEffectInstance> effects, boolean isSmall);

    public EffectRenderingInventoryScreenMixin(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}