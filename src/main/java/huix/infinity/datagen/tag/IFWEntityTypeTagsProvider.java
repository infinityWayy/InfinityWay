package huix.infinity.datagen.tag;

import huix.infinity.common.core.tag.IFWEntityTypeTags;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class IFWEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public IFWEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, InfinityWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(IFWEntityTypeTags.ANIMAL).add(EntityType.CHICKEN, EntityType.SHEEP, EntityType.PIG, EntityType.COW);
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(IFWEntityType.CHICKEN.get());
    }
}
