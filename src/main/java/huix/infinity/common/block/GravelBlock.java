package huix.infinity.common.block;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GravelBlock extends ColoredFallingBlock {
    public GravelBlock(ColorRGBA dustColor, Properties properties) {
        super(dustColor, properties);
    }

//    @Override
//    protected @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
//        if (params.getParameter(LootContextParams.THIS_ENTITY) == null) {
//            return Collections.singletonList(Items.GRAVEL.getDefaultInstance());
//        } else {
//            ServerLevel worldIn = params.getLevel();
//            if (worldIn.random.nextInt(7) == 0) {
//                return this.getli(Items.FLINT_SHARD);
//            } else if (worldIn.random.nextInt(17) == 0) {
//                return this.asList(Items.COPPER_NUGGET);
//            } else if (worldIn.random.nextInt(53) == 0) {
//                return this.asList(Items.SILVER_NUGGET);
//            } else if (worldIn.random.nextInt(95) == 0) {
//                return this.asList(Items.FLINT);
//            } else if (worldIn.random.nextInt(161) == 0) {
//                return this.asList(Items.GOLD_NUGGET);
//            } else if (worldIn.random.nextInt(495) == 0) {
//                return this.asList(Items.OBSIDIAN_SHARD);
//            } else if (worldIn.random.nextInt(1457) == 0) {
//                return this.asList(Items.EMERALD_SHARD);
//            } else if (worldIn.random.nextInt(4373) == 0) {
//                return this.asList(Items.DIAMOND_SHARD);
//            } else if (worldIn.random.nextInt(13121) == 0) {
//                return this.asList(Items.MITHRIL_NUGGET);
//            } else {
//                return worldIn.random.nextInt(26243) == 0 ? this.asList(Items.ADAMANTIUM_NUGGET) : this.asList(Items.GRAVEL);
//            }
//        }
//    }
}
