package huix.infinity.common.world.block;

import huix.infinity.attachment.IFWAttachment;
import huix.infinity.common.world.block.entity.AnvilBlockEntity;
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
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.EntityBlock;
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
            AnvilBlockEntity blockEntity = (AnvilBlockEntity) level.getBlockEntity(pos);
            int damage = blockEntity.getData(IFWAttachment.anvil_damage.get());
            if (damage < this.maxDurability()) {
                ItemStack result = new ItemStack(this.asItem());
                result.set(DataComponents.DAMAGE, damage);
                popResource(level, pos, result);
            }
        }
        super.onPlace(state, level, pos, oldState, isMoving);
    }

    private boolean canPlace(BlockState state, Level level, BlockPos pos, BlockState oldState) {
        return !level.isClientSide() && !isFree(level.getBlockState(pos.below()))
                && !(state.getBlock() instanceof AnvilBlock);
    }

    @Override
    public ItemStack ifw_defaultInstance() {
        ItemStack itemstack = super.ifw_defaultInstance();
        itemstack.set(DataComponents.MAX_DAMAGE, this.maxDurability());
        itemstack.set(DataComponents.DAMAGE, this.initalDurability());
        return itemstack;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            AnvilBlockEntity blockEntity = (AnvilBlockEntity) level.getBlockEntity(pos);
            blockEntity.setData(IFWAttachment.anvil_damage.get(), blockEntity.damage());
        }

        super.tick(state, level, pos, random);
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtsEntities(2.0F, 40);
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
