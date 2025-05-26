package huix.infinity.common.world.block.workbench;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.MaterialCraftingDifficulty;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

/**
 * MITE工作台基类
 * 每个材质等级对应专门的工作台
 */
public abstract class MITEWorkbenchBlock extends CraftingTableBlock {

    private final IFWTier tier;
    private final int craftingDifficulty;

    public MITEWorkbenchBlock(Properties properties, IFWTier tier) {
        super(properties);
        this.tier = tier;
        this.craftingDifficulty = MaterialCraftingDifficulty.getWorkbenchCraftingDifficulty(tier);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                     @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // 打开MITE合成界面
        player.openMenu(createMITECraftingMenu(state, level, pos), pos);
        return InteractionResult.CONSUME;
    }

    /**
     * 创建MITE合成菜单
     * @param state 方块状态
     * @param level 世界
     * @param pos 位置
     * @return 菜单提供者
     */
    protected abstract net.minecraft.world.MenuProvider createMITECraftingMenu(BlockState state, Level level, BlockPos pos);

    /**
     * 获取工作台的材质等级
     * @return 材质等级
     */
    public IFWTier getTier() {
        return tier;
    }

    /**
     * 获取工作台的制作难度
     * @return 制作难度
     */
    public int getCraftingDifficulty() {
        return craftingDifficulty;
    }

    /**
     * 获取工作台的显示名称
     * @return 组件
     */
    public Component getDisplayName() {
        return Component.translatable("block.infinityway." + getTierName().toLowerCase() + "_workbench");
    }

    /**
     * 获取材质名称（用于本地化）
     * @return 材质名称
     */
    protected abstract String getTierName();
}