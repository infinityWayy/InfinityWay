package huix.infinity.mixin.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;

@Mixin( EnchantmentScreen.class )
public class EnchantmentScreenMixin extends AbstractContainerScreen<EnchantmentMenu> {
    @Shadow
    @Final
    private static ResourceLocation[] ENABLED_LEVEL_SPRITES;
    @Shadow
    @Final
    private static ResourceLocation[] DISABLED_LEVEL_SPRITES;
    @Shadow
    @Final
    private static ResourceLocation ENCHANTMENT_SLOT_DISABLED_SPRITE;
    @Shadow
    @Final
    private static ResourceLocation ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE;
    @Shadow
    @Final
    private static ResourceLocation ENCHANTMENT_SLOT_SPRITE;
    @Shadow
    @Final
    private static ResourceLocation ENCHANTING_TABLE_LOCATION;

    public EnchantmentScreenMixin(EnchantmentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Overwrite
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(ENCHANTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        this.renderBook(guiGraphics, i, j, partialTick);
        EnchantmentNames.getInstance().initSeed(this.menu.getEnchantmentSeed());
//        int k = this.menu.getGoldCount();

        for (int l = 0; l < 3; l++) {
            int i1 = i + 60;
            int j1 = i1 + 20;
            int k1 = this.menu.costs[l];
            if (k1 == 0) {
                RenderSystem.enableBlend();
                guiGraphics.blitSprite(ENCHANTMENT_SLOT_DISABLED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                RenderSystem.disableBlend();
            } else {
                String s = k1 + "";
                int l1 = 86 - this.font.width(s);
                FormattedText formattedtext = EnchantmentNames.getInstance().getRandomName(this.font, l1);
                int i2 = 6839882;
                if ((this.minecraft.player.totalExperience < k1 && !this.minecraft.player.getAbilities().instabuild)
                        || this.menu.enchantClue[l] == -1) { // Forge: render buttons as disabled when enchantable but enchantability not met on lower levels
                    RenderSystem.enableBlend();
                    guiGraphics.blitSprite(ENCHANTMENT_SLOT_DISABLED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                    guiGraphics.blitSprite(DISABLED_LEVEL_SPRITES[l], i1 + 1, j + 15 + 19 * l, 16, 16);
                    RenderSystem.disableBlend();
                    guiGraphics.drawWordWrap(this.font, formattedtext, j1, j + 16 + 19 * l, l1, (i2 & 16711422) >> 1);
                    i2 = 4226832;
                } else {
                    int j2 = mouseX - (i + 60);
                    int k2 = mouseY - (j + 14 + 19 * l);
                    RenderSystem.enableBlend();
                    if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
                        guiGraphics.blitSprite(ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                        i2 = 16777088;
                    } else {
                        guiGraphics.blitSprite(ENCHANTMENT_SLOT_SPRITE, i1, j + 14 + 19 * l, 108, 19);
                    }

                    guiGraphics.blitSprite(ENABLED_LEVEL_SPRITES[l], i1 + 1, j + 15 + 19 * l, 16, 16);
                    RenderSystem.disableBlend();
                    guiGraphics.drawWordWrap(this.font, formattedtext, j1, j + 16 + 19 * l, l1, i2);
                    i2 = 8453920;
                }

                guiGraphics.drawString(this.font, s, j1 + 86 - this.font.width(s), j + 16 + 19 * l + 7, i2);
            }
        }
    }

    @Overwrite
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        boolean flag = this.minecraft.player.getAbilities().instabuild;
//        int i = this.menu.getGoldCount();

        for(int j = 0; j < 3; ++j) {
            int k = this.menu.costs[j];
            Optional<Holder.Reference<Enchantment>> optional = this.minecraft.level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolder(((EnchantmentMenu)this.menu).enchantClue[j]);
//            int l = this.menu.levelClue[j];
//            int i1 = j + 1;
            if (this.isHovering(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && k > 0) {
                List<Component> list = Lists.newArrayList();
                //list.add(Component.translatable("container.enchant.clue", optional.isEmpty() ? "" : Enchantment.getFullname(optional.get(), l)).withStyle(ChatFormatting.WHITE));
                if (optional.isEmpty()) {
                    list.add(Component.literal(""));
                    list.add(Component.translatable("neoforge.container.enchant.limitedEnchantability").withStyle(ChatFormatting.RED));
                } else if (!flag) {
//                    list.add(CommonComponents.EMPTY);
                    if (this.minecraft.player.totalExperience < k) {
                        list.add(Component.translatable("container.enchant.level.requirement", this.menu.costs[j]).withStyle(ChatFormatting.RED));
                    }
//                    else {
//                        MutableComponent mutablecomponent;
//                        if (i1 == 1) {
//                            mutablecomponent = Component.translatable("container.enchant.lapis.one");
//                        } else {
//                            mutablecomponent = Component.translatable("container.enchant.lapis.many", new Object[]{i1});
//                        }
//
//                        list.add(mutablecomponent.withStyle(i >= i1 ? ChatFormatting.GRAY : ChatFormatting.RED));
//                        MutableComponent mutablecomponent1;
//                        if (i1 == 1) {
//                            mutablecomponent1 = Component.translatable("container.enchant.level.one");
//                        } else {
//                            mutablecomponent1 = Component.translatable("container.enchant.level.many", new Object[]{i1});
//                        }
//
//                        list.add(mutablecomponent1.withStyle(ChatFormatting.GRAY));
//                    }
                }

                guiGraphics.renderComponentTooltip(this.font, list, mouseX, mouseY);
                break;
            }
        }

    }

    @Shadow
    private void renderBook(GuiGraphics guiGraphics, int x, int y, float partialTick) {
    }
}
