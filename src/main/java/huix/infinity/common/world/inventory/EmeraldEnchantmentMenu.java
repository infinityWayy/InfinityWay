package huix.infinity.common.world.inventory;

import com.mojang.datafixers.util.Pair;
import huix.infinity.common.world.block.EmeraldEnchantingTableBlock;
import huix.infinity.common.world.block.IFWBlocks;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class EmeraldEnchantmentMenu extends AbstractContainerMenu {
    static final ResourceLocation EMPTY_SLOT_LAPIS_LAZULI = ResourceLocation.withDefaultNamespace("item/empty_slot_lapis_lazuli");
    private final Container enchantSlots = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            super.setChanged();
            EmeraldEnchantmentMenu.this.slotsChanged(this);
        }
    };
    private final ContainerLevelAccess access;
    private final RandomSource random = RandomSource.create();
    private final DataSlot enchantmentSeed = DataSlot.standalone();
    public final int[] costs = new int[3];
    public final int[] enchantClue = new int[]{-1, -1, -1};
    public final int[] levelClue = new int[]{-1, -1, -1};

    public EmeraldEnchantmentMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public EmeraldEnchantmentMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(IFWMenuTypes.emerald_enchantment_menu.get(), containerId);
        this.access = access;
        this.addSlot(new Slot(this.enchantSlots, 0, 15, 47) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 1, 35, 47) {
            @Override
            public boolean mayPlace(@NotNull ItemStack p_39517_) {
                return p_39517_.is(Items.LAPIS_LAZULI); // Neo: TODO - replace with the tag once we have client tags
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EmeraldEnchantmentMenu.EMPTY_SLOT_LAPIS_LAZULI);
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(DataSlot.shared(this.costs, 0));
        this.addDataSlot(DataSlot.shared(this.costs, 1));
        this.addDataSlot(DataSlot.shared(this.costs, 2));
        this.addDataSlot(this.enchantmentSeed).set(playerInventory.player.getEnchantmentSeed());
        this.addDataSlot(DataSlot.shared(this.enchantClue, 0));
        this.addDataSlot(DataSlot.shared(this.enchantClue, 1));
        this.addDataSlot(DataSlot.shared(this.enchantClue, 2));
        this.addDataSlot(DataSlot.shared(this.levelClue, 0));
        this.addDataSlot(DataSlot.shared(this.levelClue, 1));
        this.addDataSlot(DataSlot.shared(this.levelClue, 2));
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void slotsChanged(@NotNull Container inventory) {
        if (inventory == this.enchantSlots) {
            ItemStack itemstack = inventory.getItem(0);
            if (!itemstack.isEmpty() && itemstack.isEnchantable()) {
                this.access.execute((p_344366_, p_344367_) -> {
                    IdMap<Holder<Enchantment>> idmap = p_344366_.registryAccess().registryOrThrow(Registries.ENCHANTMENT).asHolderIdMap();
                    float j = 0;

                    for (BlockPos blockpos : EmeraldEnchantingTableBlock.BOOKSHELF_OFFSETS) {
                        if (EmeraldEnchantingTableBlock.isValidBookShelf(p_344366_, p_344367_, blockpos)) {
                            j += p_344366_.getBlockState(p_344367_.offset(blockpos)).getEnchantPowerBonus(p_344366_, p_344367_.offset(blockpos));
                        }
                    }

                    this.random.setSeed(this.enchantmentSeed.get());

                    for (int k = 0; k < 3; k++) {
                        this.costs[k] = EnchantmentHelper.getEnchantmentCost(this.random, k, (int)j, itemstack);
                        this.enchantClue[k] = -1;
                        this.levelClue[k] = -1;
                        if (this.costs[k] < k + 1) {
                            this.costs[k] = 0;
                        }
                        this.costs[k] = net.neoforged.neoforge.event.EventHooks.onEnchantmentLevelSet(p_344366_, p_344367_, k, (int)j, itemstack, costs[k]);
                    }

                    for (int l = 0; l < 3; l++) {
                        if (this.costs[l] > 0) {
                            List<EnchantmentInstance> list = this.getEnchantmentList(p_344366_.registryAccess(), itemstack, l, this.costs[l]);
                            if (list != null && !list.isEmpty()) {
                                EnchantmentInstance enchantmentinstance = list.get(this.random.nextInt(list.size()));
                                this.enchantClue[l] = idmap.getId(enchantmentinstance.enchantment);
                                this.levelClue[l] = enchantmentinstance.level;
                            }
                        }
                    }

                    this.broadcastChanges();
                });
            } else {
                for (int i = 0; i < 3; i++) {
                    this.costs[i] = 0;
                    this.enchantClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }
    }

    /**
     * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
     */
    @Override
    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (id >= 0 && id < this.costs.length) {
            ItemStack itemstack = this.enchantSlots.getItem(0);
            ItemStack itemstack1 = this.enchantSlots.getItem(1);
            int i = id + 1;
            if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !player.hasInfiniteMaterials()) {
                return false;
            } else if (this.costs[id] <= 0
                    || itemstack.isEmpty()
                    || (player.experienceLevel < i || player.experienceLevel < this.costs[id]) && !player.getAbilities().instabuild) {
                return false;
            } else {
                this.access
                        .execute(
                                (p_347276_, p_347277_) -> {
                                    ItemStack itemstack2 = itemstack;
                                    List<EnchantmentInstance> list = this.getEnchantmentList(p_347276_.registryAccess(), itemstack, id, this.costs[id]);
                                    if (!list.isEmpty()) {
                                        player.onEnchantmentPerformed(itemstack, i);
                                        // Neo: Allow items to transform themselves when enchanted, instead of relying on hardcoded transformations for Items.BOOK
                                        itemstack2 = itemstack.getItem().applyEnchantments(itemstack, list);
                                        this.enchantSlots.setItem(0, itemstack2);
                                        net.neoforged.neoforge.common.CommonHooks.onPlayerEnchantItem(player, itemstack2, list);

                                        itemstack1.consume(i, player);
                                        if (itemstack1.isEmpty()) {
                                            this.enchantSlots.setItem(1, ItemStack.EMPTY);
                                        }

                                        player.awardStat(Stats.ENCHANT_ITEM);
                                        if (player instanceof ServerPlayer) {
                                            CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, itemstack2, i);
                                        }

                                        this.enchantSlots.setChanged();
                                        this.enchantmentSeed.set(player.getEnchantmentSeed());
                                        this.slotsChanged(this.enchantSlots);
                                        p_347276_.playSound(
                                                null, p_347277_, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, p_347276_.random.nextFloat() * 0.1F + 0.9F
                                        );
                                    }
                                }
                        );
                return true;
            }
        } else {
            Util.logAndPauseIfInIde(player.getName() + " pressed invalid button id: " + id);
            return false;
        }
    }

    private List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int slot, int cost) {
        this.random.setSeed(this.enchantmentSeed.get() + slot);
        Optional<HolderSet.Named<Enchantment>> optional = registryAccess.registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE);
        if (optional.isEmpty()) {
            return List.of();
        } else {
            List<EnchantmentInstance> list = EnchantmentHelper.selectEnchantment(this.random, stack, cost, optional.get().stream());
            if (stack.is(Items.BOOK) && list.size() > 1) {
                list.remove(this.random.nextInt(list.size()));
            }

            return list;
        }
    }

    public int getGoldCount() {
        ItemStack itemstack = this.enchantSlots.getItem(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }

    public int getEnchantmentSeed() {
        return this.enchantmentSeed.get();
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((p_39469_, p_39470_) -> this.clearContainer(player, this.enchantSlots));
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, IFWBlocks.emerald_enchanting_table.get());
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
     */
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.is(Items.LAPIS_LAZULI)) { // Neo: TODO - replace with the tag once we have client tags
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.getFirst().hasItem() || !this.slots.getFirst().mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1.copyWithCount(1);
                itemstack1.shrink(1);
                this.slots.getFirst().setByPlayer(itemstack2);
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}