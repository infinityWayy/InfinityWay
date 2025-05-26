package huix.infinity.common.client.screen;

import huix.infinity.common.world.inventory.TimedCraftingContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * MITE合成系统的GUI界面
 */
public class MITECraftingScreen extends AbstractContainerScreen<TimedCraftingContainer> {

    private static final ResourceLocation CRAFTING_TABLE_LOCATION =
            new ResourceLocation("textures/gui/container/crafting_table.png");

    private final DifficultyTooltip difficultyTooltip;
    private final CraftingProgressBar progressBar;
    private final TimeEstimateWidget timeWidget;

    public MITECraftingScreen(TimedCraftingContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.difficultyTooltip = new DifficultyTooltip(this);
        this.progressBar = new CraftingProgressBar(this);
        this.timeWidget = new TimeEstimateWidget(this);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // 渲染制作进度条
        this.progressBar.render(guiGraphics, mouseX, mouseY);

        // 渲染时间估算组件
        this.timeWidget.render(guiGraphics, mouseX, mouseY);

        // 渲染物品提示（最后渲染，确保在最上层）
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.difficultyTooltip.render(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CRAFTING_TABLE_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        // 显示制作难度信息
        if (this.menu.hasValidRecipe()) {
            String difficultyText = "难度: " + this.menu.getCurrentDifficulty();
            guiGraphics.drawString(this.font, difficultyText, 8, 73, 0x404040, false);

            String tierText = "需要: " + this.menu.getRequiredTier();
            guiGraphics.drawString(this.font, tierText, 8, 83, 0x404040, false);
        }
    }

    /**
     * 检查鼠标是否悬停在指定区域
     */
    public boolean isHoveringSlot(int x, int y, int width, int height, int mouseX, int mouseY) {
        int screenX = this.leftPos + x;
        int screenY = this.topPos + y;
        return mouseX >= screenX && mouseX < screenX + width &&
                mouseY >= screenY && mouseY < screenY + height;
    }

    /**
     * 获取字体对象
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * 获取左上角位置
     */
    public int getLeftPos() {
        return this.leftPos;
    }

    /**
     * 获取左上角位置
     */
    public int getTopPos() {
        return this.topPos;
    }
}