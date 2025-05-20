package huix.infinity.common.world.block.entity;

import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EmeraldEnchantingTableBlockEntity extends BlockEntity implements Nameable {
    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    public float rot;
    public float oRot;
    public float tRot;
    private static final RandomSource RANDOM = RandomSource.create();
    @Nullable
    private Component name;

    public EmeraldEnchantingTableBlockEntity(BlockPos pos, BlockState state) {
        super(IFWBlockEntityTypes.emerald_enchanting_table.get(), pos, state);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.hasCustomName()) {
            if (this.name != null) {
                tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
            }
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("CustomName", 8)) {
            this.name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }
    }

    public static void bookAnimationTick(Level level, BlockPos pos, BlockState state, EmeraldEnchantingTableBlockEntity enchantingTable) {
        enchantingTable.oOpen = enchantingTable.open;
        enchantingTable.oRot = enchantingTable.rot;
        Player player = level.getNearestPlayer((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 3.0, false);
        if (player != null) {
            double d0 = player.getX() - ((double)pos.getX() + 0.5);
            double d1 = player.getZ() - ((double)pos.getZ() + 0.5);
            enchantingTable.tRot = (float) Mth.atan2(d1, d0);
            enchantingTable.open += 0.1F;
            if (enchantingTable.open < 0.5F || RANDOM.nextInt(40) == 0) {
                float f1 = enchantingTable.flipT;

                do {
                    enchantingTable.flipT = enchantingTable.flipT + (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
                } while (f1 == enchantingTable.flipT);
            }
        } else {
            enchantingTable.tRot += 0.02F;
            enchantingTable.open -= 0.1F;
        }

        while (enchantingTable.rot >= (float) Math.PI) {
            enchantingTable.rot -= (float) (Math.PI * 2);
        }

        while (enchantingTable.rot < (float) -Math.PI) {
            enchantingTable.rot += (float) (Math.PI * 2);
        }

        while (enchantingTable.tRot >= (float) Math.PI) {
            enchantingTable.tRot -= (float) (Math.PI * 2);
        }

        while (enchantingTable.tRot < (float) -Math.PI) {
            enchantingTable.tRot += (float) (Math.PI * 2);
        }

        float f2 = enchantingTable.tRot - enchantingTable.rot;

        while (f2 >= (float) Math.PI) {
            f2 -= (float) (Math.PI * 2);
        }

        while (f2 < (float) -Math.PI) {
            f2 += (float) (Math.PI * 2);
        }

        enchantingTable.rot += f2 * 0.4F;
        enchantingTable.open = Mth.clamp(enchantingTable.open, 0.0F, 1.0F);
        enchantingTable.time++;
        enchantingTable.oFlip = enchantingTable.flip;
        float f = (enchantingTable.flipT - enchantingTable.flip) * 0.4F;
        float f3 = 0.2F;
        f = Mth.clamp(f, -0.2F, 0.2F);
        enchantingTable.flipA = enchantingTable.flipA + (f - enchantingTable.flipA) * 0.9F;
        enchantingTable.flip = enchantingTable.flip + enchantingTable.flipA;
    }

    @Override
    public @NotNull Component getName() {
        return this.name != null ? this.name : Component.translatable("container.enchant");
    }

    public void setCustomName(@Nullable Component customName) {
        this.name = customName;
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.@NotNull DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.name = componentInput.get(DataComponents.CUSTOM_NAME);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CUSTOM_NAME, this.name);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("CustomName");
    }
}
