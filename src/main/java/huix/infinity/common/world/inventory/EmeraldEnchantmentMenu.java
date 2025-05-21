package huix.infinity.common.world.inventory;

import huix.infinity.common.world.block.EmeraldEnchantingTableBlock;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.init.event.IFWSoundEvents;
import huix.infinity.util.IFWEnchantmentHelper;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class EmeraldEnchantmentMenu extends AbstractContainerMenu {
    private final Container enchantSlots = new SimpleContainer(1) {
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
    private final int ifw_enchantingModify = 1;

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
            if (!itemstack.isEmpty() && itemstack.isEnchantable() || itemstack.ifw_hasEncRecipe()) {
                this.access.execute((level, pos) -> {
//                    IdMap<Holder<Enchantment>> idmap = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).asHolderIdMap();
                    float bookShelves = 0;

                    for (BlockPos blockpos : EmeraldEnchantingTableBlock.BOOKSHELF_OFFSETS) {
                        if (EmeraldEnchantingTableBlock.isValidBookShelf(level, pos, blockpos)) {
                            bookShelves += level.getBlockState(pos.offset(blockpos)).getEnchantPowerBonus(level, pos.offset(blockpos));
                        }
                    }

                    this.random.setSeed(this.enchantmentSeed.get());

                    for (int k = 0; k < 3; k++) {
                        this.costs[k] = IFWEnchantmentHelper.getExperienceCost(IFWEnchantmentHelper.calculateRequiredExperienceLevel(this.random, k, (int)bookShelves, itemstack, this.ifw_enchantingModify));
                        if (this.costs[k] < k + 1) {
                            this.costs[k] = 0;
                        }
                        this.costs[k] = EventHooks.onEnchantmentLevelSet(level, pos, k, (int)bookShelves, itemstack, costs[k]);
                    }

                    for (int l = 0; l < 3; l++) {
                        if (this.costs[l] > 0) {
                            this.getEnchantmentList(level.registryAccess(), itemstack, l, this.costs[l]);
                        } else if (itemstack.ifw_hasEncRecipe()) {
                            this.costs[l] = itemstack.ifw_encRecipeXP();
                        }
                    }

                    this.broadcastChanges();
                });
            } else {
                for (int i = 0; i < 3; i++) {
                    this.costs[i] = 0;
//                    this.enchantClue[i] = -1;
//                    this.levelClue[i] = -1;
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
            int i = id + 1;
            if (canClick(player, id , itemstack)) {
                this.access.execute((level, pos) -> {
                    ItemStack result = itemstack.copy();
                    if (itemstack.ifw_hasEncRecipe()) {
                        result = itemstack.ifw_encRecipeResult();
                        player.onEnchantmentPerformedPoints(itemstack, itemstack.ifw_encRecipeXP());
                        //must copy
                        this.enchantSlots.setItem(0, result.copy());
                    } else {
                        List<EnchantmentInstance> list = this.getEnchantmentList(level.registryAccess(), itemstack, id, this.costs[id]);
                        if (!list.isEmpty()) {
                            player.onEnchantmentPerformedPoints(itemstack, this.costs[id]);
                            // Neo: Allow items to transform themselves when enchanted, instead of relying on hardcoded transformations for Items.BOOK
                            result = itemstack.getItem().applyEnchantments(itemstack, list);
                            this.enchantSlots.setItem(0, result);
                            CommonHooks.onPlayerEnchantItem(player, result, list);
                        }
                    }
                    enchantEnd(level, pos, player, i, result);
                });
                return true;
            } else return false;
        } else {
            Util.logAndPauseIfInIde(player.getName() + " pressed invalid button id: " + id);
            return false;
        }
    }

    private void enchantEnd(Level level, BlockPos pos, Player player, int id, ItemStack result) {
        player.awardStat(Stats.ENCHANT_ITEM);
        this.enchantmentSeed.set(player.getEnchantmentSeed());
        if (player instanceof ServerPlayer) CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, result, id);
        this.enchantSlots.setChanged();
        this.slotsChanged(this.enchantSlots);
        level.playSound(null, pos, IFWSoundEvents.CLASSIC_HURT.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    private boolean canClick(Player player, int id, ItemStack enchantItem) {
        boolean flag = false;
        if (enchantItem.ifw_hasEncRecipe()) flag = player.totalExperience >= enchantItem.ifw_encRecipeXP();
        return this.costs[id] > 0 && !enchantItem.isEmpty() && ((player.totalExperience >= this.costs[id] || flag) || player.getAbilities().instabuild);
    }

    private List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int slot, int cost) {
        this.random.setSeed(this.enchantmentSeed.get() + slot);
        Optional<HolderSet.Named<Enchantment>> optional = registryAccess.registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE);
        if (optional.isEmpty()) {
            return List.of();
        } else {
            List<EnchantmentInstance> list = IFWEnchantmentHelper.selectEnchantment(this.random, stack, cost, optional.get().stream());
            if (stack.is(Items.BOOK) && list.size() > 1) {
                list.remove(this.random.nextInt(list.size()));
            }

            return list;
        }
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
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.getFirst().hasItem() || !this.slots.getFirst().mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                ItemStack result = itemstack1.copyWithCount(1);
                itemstack1.shrink(1);
                this.slots.getFirst().setByPlayer(result);
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