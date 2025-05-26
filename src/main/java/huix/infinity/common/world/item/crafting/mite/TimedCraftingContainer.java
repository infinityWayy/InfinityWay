package huix.infinity.common.world.item.crafting.mite;

import huix.infinity.common.world.item.tier.CraftingDifficultyCalculator;
import huix.infinity.common.world.item.crafting.mite.MITERecipe;
import huix.infinity.common.world.item.tier.MITETierManager;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TimedCraftingContainer extends CraftingMenu {
    private final ContainerLevelAccess access;
    private final ContainerData craftingData;

    // MITE 制作相关数据
    private int craftingTicks = 0;           // 当前制作已用时间（tick）
    private int totalCraftingTicks = 0;      // 总制作时间（tick）
    private int currentDifficulty = 0;       // 当前配方难度
    private boolean isCrafting = false;      // 是否正在制作
    private String requiredTier = null;      // 要求的材质等级

    public TimedCraftingContainer(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public TimedCraftingContainer(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
        this.access = access;

        // 初始化制作数据容器
        this.craftingData = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> TimedCraftingContainer.this.craftingTicks;
                    case 1 -> TimedCraftingContainer.this.totalCraftingTicks;
                    case 2 -> TimedCraftingContainer.this.currentDifficulty;
                    case 3 -> TimedCraftingContainer.this.isCrafting ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> TimedCraftingContainer.this.craftingTicks = value;
                    case 1 -> TimedCraftingContainer.this.totalCraftingTicks = value;
                    case 2 -> TimedCraftingContainer.this.currentDifficulty = value;
                    case 3 -> TimedCraftingContainer.this.isCrafting = value != 0;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

        this.addDataSlots(this.craftingData);
    }

    @Override
    public void slotsChanged(@NotNull Container inventory) {
        this.access.execute((level, pos) -> {
            if (!level.isClientSide()) {
                updateCraftingCalculations(level);
            }
        });
        super.slotsChanged(inventory);
    }

    private void updateCraftingCalculations(Level level) {
        // 获取制作网格中的物品
        CraftingInput craftingInput = this.getCraftingInput();

        // 检查是否有有效的配方
        var recipeHolder = level.getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, craftingInput, level);

        if (recipeHolder.isPresent()) {
            CraftingRecipe recipe = recipeHolder.get().value();

            // 计算制作难度
            this.currentDifficulty = CraftingDifficultyCalculator.calculateRecipeDifficulty(craftingInput);

            // 计算制作时间
            this.totalCraftingTicks = CraftingDifficultyCalculator.calculateCraftingTicks(this.currentDifficulty);

            // 获取要求的材质等级
            this.requiredTier = MITETierManager.getRequiredTierForDifficulty(this.currentDifficulty);

            // 检查当前工作台是否满足要求
            boolean canCraft = checkWorkbenchRequirement(level);

            if (canCraft && this.currentDifficulty > 0) {
                // 如果可以制作且有难度，开始制作过程
                if (!this.isCrafting) {
                    startCrafting();
                }
            } else {
                // 停止制作
                stopCrafting();
            }
        } else {
            // 没有有效配方，停止制作
            stopCrafting();
        }

        this.broadcastChanges();
    }

    private boolean checkWorkbenchRequirement(Level level) {
        // 检查当前工作台是否满足材质等级要求
        // 这里需要根据实际的工作台方块来判断
        // 暂时返回 true，后续会在具体的工作台方块中实现
        return true;
    }

    private void startCrafting() {
        this.isCrafting = true;
        this.craftingTicks = 0;
    }

    private void stopCrafting() {
        this.isCrafting = false;
        this.craftingTicks = 0;
        this.totalCraftingTicks = 0;
        this.currentDifficulty = 0;
        this.requiredTier = null;
    }

    /**
     * 服务端tick更新，用于制作进度
     */
    public void tick() {
        if (this.isCrafting && this.totalCraftingTicks > 0) {
            this.craftingTicks++;

            if (this.craftingTicks >= this.totalCraftingTicks) {
                // 制作完成
                completeCrafting();
            }

            this.broadcastChanges();
        }
    }

    private void completeCrafting() {
        // 制作完成，执行正常的合成逻辑
        this.access.execute((level, pos) -> {
            if (!level.isClientSide()) {
                CraftingInput craftingInput = this.getCraftingInput();
                var recipeHolder = level.getRecipeManager()
                        .getRecipeFor(RecipeType.CRAFTING, craftingInput, level);

                if (recipeHolder.isPresent()) {
                    ItemStack result = recipeHolder.get().value().assemble(craftingInput, level.registryAccess());
                    this.getResultSlot().set(result);
                }
            }
        });

        stopCrafting();
    }

    /**
     * 获取结果槽位
     */
    private Slot getResultSlot() {
        // CraftingMenu 的结果槽位通常是索引 0
        return this.slots.get(0);
    }

    /**
     * 检查玩家是否可以拿取物品
     */
    protected boolean mayPickup(Player player, boolean hasStack) {
        // 只有制作完成后才能取出物品
        return !this.isCrafting && hasStack;
    }

    // Getter 方法供 UI 使用
    public boolean isCrafting() {
        return this.isCrafting;
    }

    public float getCraftingProgress() {
        if (this.totalCraftingTicks == 0) return 0.0f;
        return (float) this.craftingTicks / this.totalCraftingTicks;
    }

    public int getCurrentDifficulty() {
        return this.currentDifficulty;
    }

    public double getEstimatedTimeSeconds() {
        if (this.totalCraftingTicks == 0) return 0.0;
        return this.totalCraftingTicks / 20.0; // 20 ticks = 1 second
    }

    public double getRemainingTimeSeconds() {
        if (this.totalCraftingTicks == 0) return 0.0;
        int remainingTicks = this.totalCraftingTicks - this.craftingTicks;
        return remainingTicks / 20.0;
    }

    public String getRequiredTier() {
        return this.requiredTier;
    }

    public boolean hasValidRecipe() {
        return this.currentDifficulty > 0;
    }

    private CraftingInput getCraftingInput() {
        // 获取制作网格的输入
        NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
        for (int i = 0; i < 9; i++) {
            items.set(i, this.getSlot(i + 1).getItem()); // 槽位1-9是制作网格
        }
        return CraftingInput.of(3, 3, items);
    }
}