package huix.infinity.common.world.block;

import huix.infinity.attachment.IFWAttachment;
import huix.infinity.common.world.block.entity.AnvilBlockEntity;
import huix.infinity.common.world.inventory.IFWCopperAnvilMenu;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.util.DurabilityHelper;
import huix.infinity.util.ReplaceHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class IFWAnvilBlock extends AnvilBlock implements EntityBlock {
    private final int maxDurability;
    private final int repairLevel;
    private final IFWTiers tier;
    private final int initalDurability;

    public IFWAnvilBlock(Properties properties, IFWTiers tier, int stage) {
        super(properties);
        this.tier = tier;
        this.repairLevel = tier.repairLevel();
        this.maxDurability = DurabilityHelper.getDurability(tier);
        this.initalDurability = DurabilityHelper.getStageDurability(stage, this);
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
            default -> IFWBlocks.chipped_copper_anvil.get().defaultBlockState();
        };
    }

    private BlockState getDamagedState() {
        return switch (this.tier) {
            default -> IFWBlocks.damaged_copper_anvil.get().defaultBlockState();
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
                        new IFWCopperAnvilMenu(id, inventory, ContainerLevelAccess.create(level, pos)), Component.translatable("container.repair"));
    }

    public int maxDurability() {
        return this.maxDurability;
    }

    public int initalDurability() {
        return this.initalDurability;
    }

    public IFWTiers tier() {
        return this.tier;
    }

    public int repairLevel() {
        return this.repairLevel;
    }
}
