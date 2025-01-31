package huix.infinity.common.world.block.entity;

import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AnvilBlockEntity extends BlockEntity {
    private int durability;

    public AnvilBlockEntity(BlockPos pos, BlockState blockState) {
        super(IFWBlockEntityTypes.ifw_anvil.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Durability")) this.durability = tag.getInt("Durability");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.durability != 0) tag.putInt("Durability", this.durability);
    }

    public void durability(int durability) {
        this.durability = durability;
    }

    public int durability() {
        return this.durability;
    }
}
