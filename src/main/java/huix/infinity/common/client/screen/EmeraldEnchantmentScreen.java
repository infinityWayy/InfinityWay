package huix.infinity.common.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import huix.infinity.common.world.inventory.EmeraldEnchantmentMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class EmeraldEnchantmentScreen extends AbstractContainerScreen<EmeraldEnchantmentMenu> {
    private static final ResourceLocation[] ENABLED_LEVEL_SPRITES = new ResourceLocation[]{
            ResourceLocation.withDefaultNamespace("container/enchanting_table/level_1"),
            ResourceLocation.withDefaultNamespace("container/enchanting_table/level_2"),
            ResourceLocation.withDefaultNamespace("container/enchanting_table/level_3")
    };
    private static final ResourceLocation[] DISABLED_LEVEL_SPRITES = new ResourceLocation[]{
            ResourceLocation.withDefaultNamespace("container/enchanting_table/level_1_disabled"),
            ResourceLocation.withDefaultNamespace("container/enchanting_table/level_2_disabled"),
            ResourceLocation.withDefaultNamespace("container/enchanting_table/level_3_disabled")
    };
    private static final ResourceLocation ENCHANTMENT_SLOT_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace(
            "container/enchanting_table/enchantment_slot_disabled"
    );
    private static final ResourceLocation ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace(
            "container/enchanting_table/enchantment_slot_highlighted"
    );
    private static final ResourceLocation ENCHANTMENT_SLOT_SPRITE = ResourceLocation.withDefaultNamespace("container/enchanting_table/enchantment_slot");
    /**
     * The ResourceLocation containing the Enchantment GUI texture location
     */
    private static final ResourceLocation ENCHANTING_TABLE_LOCATION = ResourceLocation.fromNamespaceAndPath("ifw", "textures/gui/container/enchanting_table.png");
    /**
     * The ResourceLocation containing the texture for the Book rendered above the enchantment table
     */
    private static final ResourceLocation ENCHANTING_BOOK_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/enchanting_table_book.png");
    /**
     * A Random instance for use with the enchantment gui
     */
    private final RandomSource random = RandomSource.create();
    private BookModel bookModel;
    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    private ItemStack last = ItemStack.EMPTY;

    public EmeraldEnchantmentScreen(EmeraldEnchantmentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.bookModel = new BookModel(this.minecraft.getEntityModels().bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.tickBook();
    }

    /**
     * Called when a mouse button is clicked within the GUI element.
     * <p>
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     *
     * @param mouseX the X coordinate of the mouse.
     * @param mouseY the Y coordinate of the mouse.
     * @param button the button that was clicked.
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        for (int k = 0; k < 3; k++) {
            double d0 = mouseX - (double)(i + 60);
            double d1 = mouseY - (double)(j + 14 + 19 * k);
            if (d0 >= 0.0 && d1 >= 0.0 && d0 < 108.0 && d1 < 19.0 && this.menu.clickMenuButton(this.minecraft.player, k)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, k);
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
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
                if (((/* k < l + 1 || */this.minecraft.player.totalExperience < k1) && !this.minecraft.player.getAbilities().instabuild)/* || this.menu.enchantClue[l] == -1 */) { // Forge: render buttons as disabled when enchantable but enchantability not met on lower levels
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

    private void renderBook(GuiGraphics guiGraphics, int x, int y, float partialTick) {
        float f = Mth.lerp(partialTick, this.oOpen, this.open);
        float f1 = Mth.lerp(partialTick, this.oFlip, this.flip);
        Lighting.setupForEntityInInventory();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((float)x + 33.0F, (float)y + 31.0F, 100.0F);
        float f2 = 40.0F;
        guiGraphics.pose().scale(-40.0F, 40.0F, 40.0F);
        guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(25.0F));
        guiGraphics.pose().translate((1.0F - f) * 0.2F, (1.0F - f) * 0.1F, (1.0F - f) * 0.25F);
        float f3 = -(1.0F - f) * 90.0F - 90.0F;
        guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(f3));
        guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(180.0F));
        float f4 = Mth.clamp(Mth.frac(f1 + 0.25F) * 1.6F - 0.3F, 0.0F, 1.0F);
        float f5 = Mth.clamp(Mth.frac(f1 + 0.75F) * 1.6F - 0.3F, 0.0F, 1.0F);
        this.bookModel.setupAnim(0.0F, f4, f5, f);
        VertexConsumer vertexconsumer = guiGraphics.bufferSource().getBuffer(this.bookModel.renderType(ENCHANTING_BOOK_LOCATION));
        this.bookModel.renderToBuffer(guiGraphics.pose(), vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY);
        guiGraphics.flush();
        guiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }

    /**
     * Renders the graphical user interface (GUI) element.
     *
     * @param guiGraphics the GuiGraphics object used for rendering.
     * @param mouseX      the x-coordinate of the mouse cursor.
     * @param mouseY      the y-coordinate of the mouse cursor.
     * @param partialTick the partial tick time.
     */
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        boolean flag = this.minecraft.player.getAbilities().instabuild;
//        int i = this.menu.getGoldCount();

        for (int j = 0; j < 3; j++) {
            int k = this.menu.costs[j];
//            Optional<Holder.Reference<Enchantment>> optional = this.minecraft
//                    .level
//                    .registryAccess()
//                    .registryOrThrow(Registries.ENCHANTMENT)
//                    .getHolder(this.menu.enchantClue[j]);
            int l = this.menu.levelClue[j];
//            int i1 = j + 1;
            if (this.isHovering(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && k > 0) {
                List<Component> list = Lists.newArrayList();
//                list.add(Component.translatable("container.enchant.clue", optional.isEmpty() ? "" : Enchantment.getFullname(optional.get(), l)).withStyle(ChatFormatting.WHITE));
//                if (optional.isEmpty()) {
//                    list.add(Component.literal(""));
//                    list.add(Component.translatable("neoforge.container.enchant.limitedEnchantability").withStyle(ChatFormatting.RED));
//                } else
                if (!flag) {
                    list.add(CommonComponents.EMPTY);
                    if (this.minecraft.player.totalExperience < k) {
                        list.add(Component.translatable("container.enchant.experience.requirement", this.menu.costs[j]).withStyle(ChatFormatting.RED));
                    }
//                    if (this.minecraft.player.experienceLevel < k) {
//                        list.add(Component.translatable("container.enchant.level.requirement", this.menu.costs[j]).withStyle(ChatFormatting.RED));
//                    } else {
//                        MutableComponent mutablecomponent;
//                        if (i1 == 1) {
//                            mutablecomponent = Component.translatable("container.enchant.lapis.one");
//                        } else {
//                            mutablecomponent = Component.translatable("container.enchant.lapis.many", i1);
//                        }
//
//                        list.add(mutablecomponent.withStyle(i >= i1 ? ChatFormatting.GRAY : ChatFormatting.RED));
//                        MutableComponent mutablecomponent1;
//                        if (i1 == 1) {
//                            mutablecomponent1 = Component.translatable("container.enchant.level.one");
//                        } else {
//                            mutablecomponent1 = Component.translatable("container.enchant.level.many", i1);
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

    public void tickBook() {
        ItemStack itemstack = this.menu.getSlot(0).getItem();
        if (!ItemStack.matches(itemstack, this.last)) {
            this.last = itemstack;

            do {
                this.flipT = this.flipT + (float)(this.random.nextInt(4) - this.random.nextInt(4));
            } while (this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
        }

        this.time++;
        this.oFlip = this.flip;
        this.oOpen = this.open;
        boolean flag = false;

        for (int i = 0; i < 3; i++) {
            if (this.menu.costs[i] != 0) {
                flag = true;
                break;
            }
        }

        if (flag) {
            this.open += 0.2F;
        } else {
            this.open -= 0.2F;
        }

        this.open = Mth.clamp(this.open, 0.0F, 1.0F);
        float f1 = (this.flipT - this.flip) * 0.4F;
        float f = 0.2F;
        f1 = Mth.clamp(f1, -0.2F, 0.2F);
        this.flipA = this.flipA + (f1 - this.flipA) * 0.9F;
        this.flip = this.flip + this.flipA;
    }
}
