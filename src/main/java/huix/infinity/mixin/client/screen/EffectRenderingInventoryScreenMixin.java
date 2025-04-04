package huix.infinity.mixin.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.init.InfinityWay;
import huix.infinity.init.to.IFWClient;
import huix.infinity.util.IFWConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
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
    private int yOffset = 0;

    @Inject(at = @At(value = "RETURN"), method = "render")
    private void injectCurseRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Player player = this.minecraft.player;
        if (player.hasCurse()) {
            this.yOffset = 33;
            int renderX = this.leftPos + this.imageWidth + 2;
            int j = this.width - renderX;
            if (j >= 32) {
                boolean flag = j >= 120;

                ScreenEvent.RenderInventoryMobEffects event = ClientHooks.onScreenPotionSize(this, j, !flag, renderX);
                if (event.isCanceled()) return;
                flag = !event.isCompact();
                renderX = event.getHorizontalOffset();

                this.renderExtraBackgrounds(guiGraphics, renderX, this.yOffset, flag);
                this.renderExtraIcons(guiGraphics, renderX, this.yOffset, flag);
                if (flag)
                    this.renderExtraLabels(guiGraphics, renderX, this.yOffset, player);
            }
        }
    }

    @Unique
    private void renderExtraBackgrounds(GuiGraphics guiGraphics, int renderX, int yOffset, boolean isSmall) {
        if (isSmall) guiGraphics.blitSprite(EFFECT_BACKGROUND_LARGE_SPRITE, renderX, this.topPos, 120, 32);
        else guiGraphics.blitSprite(EFFECT_BACKGROUND_SMALL_SPRITE, renderX, this.topPos, 32, 32);
    }

    @Unique
    private void renderExtraIcons(GuiGraphics guiGraphics, int renderX, int yOffset, boolean isSmall) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, IFWConstants.LOCATION_P_EFFECT_TEXTURE_ATLAS);
        TextureAtlasSprite textureatlassprite = IFWConstants.persistentEffectTextureManager.getCurse();
        guiGraphics.blit(renderX + (isSmall ? 6 : 7), this.topPos + 7, 0, 18, 18, textureatlassprite);
    }


    @Unique
    private void renderExtraLabels(GuiGraphics guiGraphics, int renderX, int yOffset, Player player) {
        Component component = Component.translatable("render.unkonwn.curse");
        if (player.knownCurse()) component = Component.translatable(player.curse().desc());

        guiGraphics.drawString(this.font, component, renderX + 10 + 18, this.topPos + 6, 16777215);
    }

    @ModifyConstant(constant = @Constant(intValue = 5), method = "renderEffects")
    private int modifyRenderSize(int constant) {
        if (this.minecraft.player.hasCurse()) constant--;
        return constant;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;topPos:I"), method = {"renderBackgrounds", "renderEffects", "renderIcons", "renderLabels"})
    private int redirectTopPos(EffectRenderingInventoryScreen instance) {
        return this.topPos + this.yOffset;
    }

    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_LARGE_SPRITE;

    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_SMALL_SPRITE;

    @Shadow protected abstract void renderBackgrounds(GuiGraphics guiGraphics, int renderX, int yOffset, Iterable<MobEffectInstance> effects, boolean isSmall);

    public EffectRenderingInventoryScreenMixin(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
