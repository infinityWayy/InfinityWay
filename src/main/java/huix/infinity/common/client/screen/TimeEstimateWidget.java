package huix.infinity.common.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

/**
 * 时间估算显示组件
 */
public class TimeEstimateWidget {
    private final MITECraftingScreen parent;

    public TimeEstimateWidget(MITECraftingScreen parent) {
        this.parent = parent;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (!parent.getMenu().hasValidRecipe()) return;

        int x = parent.getLeftPos() + 8;
        int y = parent.getTopPos() + 93;

        // 显示预估制作时间
        double estimatedTime = parent.getMenu().getEstimatedTimeSeconds();
        String timeText = formatTime(estimatedTime);

        ChatFormatting color = getTimeColor(estimatedTime);
        Component timeComponent = Component.literal("预估时间: " + timeText).withStyle(color);

        guiGraphics.drawString(parent.getFont(), timeComponent, x, y, 0xFFFFFF, false);
    }

    private String formatTime(double seconds) {
        if (seconds < 1) return String.format("%.1fs", seconds);
        if (seconds < 60) return String.format("%.0fs", seconds);
        if (seconds < 3600) return String.format("%.1fm", seconds / 60);
        return String.format("%.1fh", seconds / 3600);
    }

    private ChatFormatting getTimeColor(double seconds) {
        if (seconds <= 5) return ChatFormatting.GREEN;
        if (seconds <= 30) return ChatFormatting.YELLOW;
        if (seconds <= 120) return ChatFormatting.GOLD;
        if (seconds <= 600) return ChatFormatting.RED;
        return ChatFormatting.DARK_RED;
    }
}