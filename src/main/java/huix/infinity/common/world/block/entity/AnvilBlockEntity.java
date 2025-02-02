package huix.infinity.common.world.block.entity;

import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AnvilBlockEntity extends BlockEntity {
    private int damage;

    public AnvilBlockEntity(BlockPos pos, BlockState blockState) {
        super(IFWBlockEntityTypes.ifw_anvil.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Damage")) this.damage = tag.getInt("Damage");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.damage != 0) tag.putInt("Damage", this.damage);
    }

    public void damage(int durability) {
        this.damage = durability;
    }

    public int damage() {
        return this.damage;
    }
}
