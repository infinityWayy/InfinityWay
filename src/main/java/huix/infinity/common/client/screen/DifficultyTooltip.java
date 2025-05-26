package huix.infinity.common.client.screen;

import com.google.common.collect.Lists;
import huix.infinity.common.world.item.tier.CraftingDifficultyCalculator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DifficultyTooltip {
    private final MITECraftingScreen parent;

    public DifficultyTooltip(MITECraftingScreen parent) {
        this.parent = parent;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // 检查鼠标是否悬停在制作网格上
        for (int slot = 1; slot <= 9; slot++) {
            if (isHoveringSlot(slot, mouseX, mouseY)) {
                renderSlotDifficultyTooltip(guiGraphics, slot, mouseX, mouseY);
                break;
            }
        }
    }

    private boolean isHoveringSlot(int slot, int mouseX, int mouseY) {
        int slotX = getSlotX(slot);
        int slotY = getSlotY(slot);
        // 通过 parent 调用 isHovering 方法
        return parent.isHoveringSlot(slotX, slotY, 16, 16, mouseX, mouseY);
    }

    private int getSlotX(int slot) {
        int col = (slot - 1) % 3;
        return 30 + col * 18;
    }

    private int getSlotY(int slot) {
        int row = (slot - 1) / 3;
        return 17 + row * 18;
    }

    private void renderSlotDifficultyTooltip(GuiGraphics guiGraphics, int slot, int mouseX, int mouseY) {
        ItemStack stack = parent.getMenu().getSlot(slot).getItem();
        if (stack.isEmpty()) return;

        List<Component> tooltip = Lists.newArrayList();

        // 显示物品的制作难度贡献
        int itemDifficulty = CraftingDifficultyCalculator.getItemDifficulty(stack);
        if (itemDifficulty > 0) {
            ChatFormatting color = getDifficultyColor(itemDifficulty);
            tooltip.add(Component.translatable("gui.infinityway.mite.item.difficulty", itemDifficulty)
                    .withStyle(color));

            // 显示材质等级
            String tier = CraftingDifficultyCalculator.getItemTier(stack);
            if (tier != null) {
                tooltip.add(Component.translatable("gui.infinityway.mite.item.tier", tier)
                        .withStyle(ChatFormatting.GRAY));
            }

            // 显示制作时间贡献
            double timeContribution = CraftingDifficultyCalculator.calculateCraftingTimeSeconds(itemDifficulty);
            tooltip.add(Component.translatable("gui.infinityway.mite.item.time_contribution",
                    formatTime(timeContribution)).withStyle(ChatFormatting.DARK_GRAY));
        }

        if (!tooltip.isEmpty()) {
            guiGraphics.renderComponentTooltip(parent.getFont(), tooltip, mouseX, mouseY);
        }
    }

    private ChatFormatting getDifficultyColor(int difficulty) {
        if (difficulty <= 100) return ChatFormatting.GREEN;
        if (difficulty <= 400) return ChatFormatting.YELLOW;
        if (difficulty <= 1600) return ChatFormatting.GOLD;
        if (difficulty <= 6400) return ChatFormatting.RED;
        return ChatFormatting.DARK_RED;
    }

    private String formatTime(double seconds) {
        if (seconds < 1) return String.format("%.1fs", seconds);
        if (seconds < 60) return String.format("%.0fs", seconds);
        if (seconds < 3600) return String.format("%.1fm", seconds / 60);
        return String.format("%.1fh", seconds / 3600);
    }
}