package huix.infinity.common.world.block;

import huix.infinity.common.world.block.entity.AnvilBlockEntity;
import huix.infinity.common.world.block.entity.IFWFurnaceBlockEntity;
import huix.infinity.common.world.inventory.IFWAnvilMenu;
import huix.infinity.common.world.inventory.IFWCopperAnvilMenu;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.util.DurabilityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

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
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (canPlace(state, level, pos, oldState)) {
            ServerLevel worldIn = (ServerLevel) level;
            int durability = ((AnvilBlockEntity)worldIn.getBlockEntity(pos)).durability();
            if (durability < this.maxDurability()) {
                ItemStack result = new ItemStack(this.asItem());
                result.set(DataComponents.DAMAGE, durability);
                popResource(worldIn, pos, result);
            }
        }
        super.onPlace(state, level, pos, oldState, isMoving);
    }

    private boolean canPlace(BlockState state, Level level, BlockPos pos, BlockState oldState) {
        return !level.isClientSide() && !isFree(level.getBlockState(pos.below()))
                && !(state.getBlock() instanceof AnvilBlock);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            CompoundTag nbtCompound = new CompoundTag();
            nbtCompound.putInt("Durability", ((AnvilBlockEntity)level.getBlockEntity(pos)).durability());
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
            fallingblockentity.blockData = nbtCompound;
            this.configureFallingBlockEntity(fallingblockentity);
        }

        super.tick(state, level, pos, random);
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtsEntities(2.0F, 40);
    }

    @Nullable
    public BlockState damage(int value, BlockEntity tileEntityAnvil) {
        if (tileEntityAnvil instanceof AnvilBlockEntity anvilBlockEntity) {
            anvilBlockEntity.durability(anvilBlockEntity.durability() + value);
            return this.getBrokeState(anvilBlockEntity.durability());
        } else {
            return null;
        }
    }

    @Nullable
    public BlockState getBrokeState(int currentDamage) {
        if (currentDamage > this.maxDurability())
            throw new IllegalArgumentException("IFWAnvilBlock.GetBrokeState error");

        if (currentDamage <= this.maxDurability() * 0.5F) return this.getDamagedState();
        else if (currentDamage >= this.maxDurability() * 0.8F) return this.defaultBlockState();
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
