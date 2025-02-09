package huix.infinity.mixin.inventory;

import huix.infinity.common.world.item.crafting.EnchantmentRecipe;
import huix.infinity.common.world.item.crafting.IFWRecipeTypes;
import huix.infinity.util.IFWEnchantmentHelper;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin( EnchantmentMenu.class )
public abstract class EnchantmentMenuMixin extends AbstractContainerMenu {

    @Unique
    private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends EnchantmentRecipe> recipes = RecipeManager.createCheck(IFWRecipeTypes.enchantment.get());

    @Unique
    private Optional<ItemStack> ifw_recipeInput(Level level, ItemStack stack) {
        return this.recipes.getRecipeFor(new SingleRecipeInput(stack), level).map(holder -> holder.value().ingredient().getItems()[0]);
    }
    @Unique
    private Optional<ItemStack> ifw_recipeResult(Level level, ItemStack stack) {
        Optional<? extends RecipeHolder<? extends EnchantmentRecipe>> recipeFor = this.recipes.getRecipeFor(new SingleRecipeInput(stack), level);
        if (recipeFor.isPresent()) {
            RecipeHolder<? extends EnchantmentRecipe> holder = recipeFor.get();
            return Optional.of(holder.value().assemble(new SingleRecipeInput(stack), level.registryAccess()));
        }
        return Optional.empty();
    }
    @Unique
    private float ifw_recipeExperience(Level level, ItemStack stack) {
        return this.recipes.getRecipeFor(new SingleRecipeInput(stack), level).map(holder -> holder.value().experience()).get();
    }

    @Unique
    private int ifw_enchantingModify = 2;

    @Overwrite
    public void slotsChanged(Container inventory) {
        if (inventory == this.enchantSlots) {
            ItemStack itemstack = inventory.getItem(0);
            if (!itemstack.isEmpty()) {
                this.access.execute((level, pos) -> {
                    float bookShelves = 0.0F;
                    for (BlockPos blockpos : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
                        if (EnchantingTableBlock.isValidBookShelf(level, pos, blockpos))
                            bookShelves += level.getBlockState(pos.offset(blockpos)).getEnchantPowerBonus(level, pos.offset(blockpos));
                    }

                    this.enchantmentSeed.set(this.random.nextInt());

                    int l;
                    for(l = 0; l < 3; ++l) {
                        this.costs[l] = IFWEnchantmentHelper.getExperienceCost(IFWEnchantmentHelper.calculateRequiredExperienceLevel(random, l, (int) bookShelves, itemstack, this.ifw_enchantingModify));
                        if (this.costs[l] < l + 1) this.costs[l] = 0;

                        this.costs[l] = EventHooks.onEnchantmentLevelSet(level, pos, l, (int)bookShelves, itemstack, this.costs[l]);
                    }

                    for(l = 0; l < 3; ++l)
                        if (this.costs[l] > 0)
                            this.getEnchantmentList(level.registryAccess(), itemstack, l, this.costs[l]);
                        else if (ifw_recipeInput(level, itemstack).isPresent() && itemstack.is(ifw_recipeInput(level, itemstack).get().getItem()))
                            this.costs[l] = (int) ifw_recipeExperience(level, itemstack);
                    this.broadcastChanges();
                });
            } else {
                for(int i = 0; i < 3; ++i)
                    this.costs[i] = 0;
            }
        }

    }

    @Overwrite
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0 && id < this.costs.length) {
            ItemStack itemstack = this.enchantSlots.getItem(0);
            int i = id + 1;
            if (this.costs[id] > 0 && !itemstack.isEmpty() && (player.totalExperience >= this.costs[id] || player.getAbilities().instabuild)) {
                this.access.execute((level, pos) -> {
                    ItemStack result = itemstack;
                    Optional<ItemStack> output = ifw_recipeResult(level, itemstack);
                    if (output.isPresent()) {
                        player.onEnchantmentPerformedPoints(itemstack, (int) ifw_recipeExperience(level, itemstack));
                        this.enchantSlots.setItem(0, output.get());;
                    } else {
                        List<EnchantmentInstance> list = this.getEnchantmentList(level.registryAccess(), itemstack, id, this.costs[id]);
                        if (!list.isEmpty()) {
                            result = itemstack.getItem().applyEnchantments(itemstack, list);
                            CommonHooks.onPlayerEnchantItem(player, result, list);
                            this.enchantSlots.setItem(0, result);
                            player.onEnchantmentPerformedPoints(itemstack, this.costs[id]);
                        }
                    }

                    player.awardStat(Stats.ENCHANT_ITEM);
                    if (player instanceof ServerPlayer) CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, result, i);
                    this.enchantSlots.setChanged();
                    this.slotsChanged(this.enchantSlots);
                    level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
                });
                return true;
            } else return false;
        } else {
            Util.logAndPauseIfInIde(player.getName() + " pressed invalid button id: " + id);
            return false;
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;selectEnchantment(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/item/ItemStack;ILjava/util/stream/Stream;)Ljava/util/List;"),
            method = "getEnchantmentList")
    private List<EnchantmentInstance> redirectEnchantmentList(RandomSource randomSource, ItemStack stack, int cost, Stream<Holder<Enchantment>> stream) {
        return IFWEnchantmentHelper.selectEnchantment(randomSource, stack, cost, stream);
    }

    @Shadow
    @Final
    public int[] costs;

    @Shadow @Final private Container enchantSlots;

    @Shadow protected abstract List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int slot, int cost);

    @Shadow @Final private ContainerLevelAccess access;

    @Shadow @Final private RandomSource random;

    @Shadow @Final private DataSlot enchantmentSeed;

    protected EnchantmentMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

}
