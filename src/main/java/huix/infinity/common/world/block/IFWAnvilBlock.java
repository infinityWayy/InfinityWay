package huix.infinity.common.world.block;

import huix.infinity.common.world.block.entity.AnvilBlockEntity;
import huix.infinity.common.world.inventory.IFWAnvilMenu;
import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.util.DurabilityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class IFWAnvilBlock extends AnvilBlock implements EntityBlock {
    private final int maxDurability;
    private final int repairLevel;
    private final IFWTier tier;

    public IFWAnvilBlock(Properties properties, IFWTier tier) {
        super(properties);
        this.tier = tier;
        this.repairLevel = tier.repairLevel();
        this.maxDurability = DurabilityHelper.getDurability(tier);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallen = FallingBlockEntity.fall(level, pos, state);
            this.falling(fallen);
        }
    }
    @Override
    protected void falling(FallingBlockEntity entity) {
        entity.setHurtsEntities(2.0F, 40);
    }


    @Override
    public void ifw_onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, ItemStack stack, Player player, boolean movedByPiston) {
        super.ifw_onPlace(state, level, pos, oldState, stack, player, movedByPiston);
        AnvilBlockEntity entity = (AnvilBlockEntity) level.getBlockEntity(pos);
        entity.damage(stack.getDamageValue());
    }

    private boolean canRemove(Level level, BlockPos pos, BlockState newState) {
        return !level.isClientSide() && !isFree(level.getBlockState(pos.below())) && !(newState.getBlock() instanceof AnvilBlock);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (canRemove(level, pos, newState)) {
            int damage = ((AnvilBlockEntity) level.getBlockEntity(pos)).damage();
            ItemStack result = new ItemStack(this);
            result.setDamageValue(damage);
            popResource(level, pos, result);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return Collections.emptyList();
    }

    @Nullable
    public BlockState ifw_damage(int value, BlockEntity tileEntityAnvil) {
        if (tileEntityAnvil instanceof AnvilBlockEntity anvilBlockEntity) {
            anvilBlockEntity.damage(anvilBlockEntity.damage() + value);
            return this.getBrokeState(anvilBlockEntity.damage());
        } else {
            return null;
        }
    }

    @Nullable
    public BlockState getBrokeState(int currentDamage) {
        if (currentDamage > this.maxDurability())
            throw new IllegalArgumentException("IFWAnvilBlock.GetBrokeState error");
        int currentDurability = this.maxDurability() - currentDamage;
        if (currentDurability <= this.maxDurability() * 0.5F) return this.getDamagedState();
        else if (currentDurability >= this.maxDurability() * 0.8F) return this.defaultBlockState();
        else return this.getChippedState();
    }

    private BlockState getChippedState() {
        return switch (this.tier) {
            case IFWTiers.COPPER -> IFWBlocks.chipped_copper_anvil.get().defaultBlockState();
            case IFWTiers.SILVER -> IFWBlocks.chipped_silver_anvil.get().defaultBlockState();
            case IFWTiers.GOLD -> IFWBlocks.chipped_gold_anvil.get().defaultBlockState();
            case IFWTiers.IRON -> IFWBlocks.chipped_iron_anvil.get().defaultBlockState();
            case IFWTiers.ANCIENT_METAL -> IFWBlocks.chipped_ancient_metal_anvil.get().defaultBlockState();
            case IFWTiers.MITHRIL -> IFWBlocks.chipped_mithril_anvil.get().defaultBlockState();
            case IFWTiers.ADAMANTIUM -> IFWBlocks.chipped_adamantium_anvil.get().defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };
    }

    private BlockState getDamagedState() {
        return switch (this.tier) {
            case IFWTiers.COPPER -> IFWBlocks.damaged_copper_anvil.get().defaultBlockState();
            case IFWTiers.SILVER -> IFWBlocks.damaged_silver_anvil.get().defaultBlockState();
            case IFWTiers.GOLD -> IFWBlocks.damaged_gold_anvil.get().defaultBlockState();
            case IFWTiers.IRON -> IFWBlocks.damaged_iron_anvil.get().defaultBlockState();
            case IFWTiers.ANCIENT_METAL -> IFWBlocks.damaged_ancient_metal_anvil.get().defaultBlockState();
            case IFWTiers.MITHRIL -> IFWBlocks.damaged_mithril_anvil.get().defaultBlockState();
            case IFWTiers.ADAMANTIUM -> IFWBlocks.damaged_adamantium_anvil.get().defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AnvilBlockEntity(pos, state);
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) ->
                        new IFWAnvilMenu(id, inventory, ContainerLevelAccess.create(level, pos)), Component.translatable("container.repair"));
    }

    public int maxDurability() {
        return this.maxDurability;
    }

    public IFWTier tier() {
        return this.tier;
    }

    public int repairLevel() {
        return this.repairLevel;
    }
}
