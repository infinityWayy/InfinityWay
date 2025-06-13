package huix.infinity.common.world.block.entity;

import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PrivateChestBlockEntity extends ChestBlockEntity {
    @Nullable
    private String owner_name;

    public PrivateChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(IFWBlockEntityTypes.private_chest.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.owner_name != null) {
            tag.putString("OwnerName", this.owner_name);
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("OwnerName")) {
            this.owner_name = tag.getString("OwnerName");
        }
    }

    @Nullable
    public String owner_name() {
        return owner_name;
    }

    @NotNull
    public PrivateChestBlockEntity setOwner(@NotNull Player player) {
        this.owner_name = player.getDisplayName().getString();
        this.setChanged();
        return this;
    }

    @NotNull
    public PrivateChestBlockEntity emptyOwner() {
        this.owner_name = null;
        this.setChanged();
        return this;
    }
}