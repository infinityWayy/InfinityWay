package huix.infinity.common.world.block.entity;

import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PrivateChestBlockEntity extends ChestBlockEntity {
    private String owner_name;

    public PrivateChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(IFWBlockEntityTypes.private_chest.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.owner_name != null) tag.putString("OwnerName", this.owner_name);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("OwnerName")) this.owner_name = tag.getString("OwnerName");
    }

    public String owner_name() {
        return owner_name;
    }

    public PrivateChestBlockEntity setOwner(Player player) {
        this.owner_name = player.getDisplayName().getString();
        return this;
    }

    public PrivateChestBlockEntity emptyOwner() {
        this.owner_name = "";
        return this;
    }
}
