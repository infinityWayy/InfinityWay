package huix.infinity.common.world.block;

import huix.infinity.common.world.item.IFWItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GravelBlock extends ColoredFallingBlock {
    public GravelBlock(ColorRGBA dustColor, Properties properties) {
        super(dustColor, properties);
    }

    @Override
    protected @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.Builder params) {
        params.getParameter(LootContextParams.THIS_ENTITY);
        ServerLevel level = params.getLevel();
        if (level.random.nextInt(7) == 0) {
            return this.asListToStack(IFWItems.flint_shard.get());
        } else if (level.random.nextInt(17) == 0) {
            return this.asListToStack(IFWItems.copper_nugget.get());
        } else if (level.random.nextInt(53) == 0) {
            return this.asListToStack(IFWItems.silver_nugget.get());
        } else if (level.random.nextInt(95) == 0) {
            return this.asListToStack(Items.FLINT);
        } else if (level.random.nextInt(161) == 0) {
            return this.asListToStack(Items.GOLD_NUGGET);
        } else if (level.random.nextInt(495) == 0) {
            return this.asListToStack(IFWItems.obsidian_shard.get());
        } else if (level.random.nextInt(1457) == 0) {
            return this.asListToStack(IFWItems.emerald_shard.get());
        } else if (level.random.nextInt(4373) == 0) {
            return this.asListToStack(IFWItems.diamond_shard.get());
        } else if (level.random.nextInt(13121) == 0) {
            return this.asListToStack(IFWItems.mithril_nugget.get());
        } else {
            return level.random.nextInt(26243) == 0 ? this.asListToStack(IFWItems.adamantium_nugget.get()) : this.asListToStack(Items.GRAVEL);
        }
    }
}
