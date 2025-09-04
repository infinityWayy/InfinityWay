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
        // 移除标签
        tag(IFWEntityTypeTags.REPLACE).add(
                EntityType.CHICKEN, EntityType.SHEEP, EntityType.PIG, EntityType.COW,
                EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.SLIME, EntityType.WITCH
        );

        // 基础标签
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(IFWEntityType.CHICKEN.get());
        this.tag(EntityTypeTags.ZOMBIES).add(IFWEntityType.ZOMBIE.get());
        this.tag(EntityTypeTags.RAIDERS).add(IFWEntityType.INFERNO_CREEPER.get());
        this.tag(EntityTypeTags.SKELETONS)
                .add(IFWEntityType.SKELETON.get())
                .add(IFWEntityType.LONGDEAD.get())
                .add(IFWEntityType.LONGDEAD_GUARDIAN.get())
                .add(IFWEntityType.BONE_LORD.get())
                .add(IFWEntityType.ANCIENT_BONE_LORD.get());

        // 亡灵生物标签
        this.tag(EntityTypeTags.UNDEAD)
                .add(IFWEntityType.ZOMBIE.get())
                .add(IFWEntityType.WIGHT.get())
                .add(IFWEntityType.SHADOW.get())
                .add(IFWEntityType.SKELETON.get())
                .add(IFWEntityType.LONGDEAD.get())
                .add(IFWEntityType.LONGDEAD_GUARDIAN.get())
                .add(IFWEntityType.BONE_LORD.get())
                .add(IFWEntityType.ANCIENT_BONE_LORD.get());

        // 节肢动物标签
        this.tag(EntityTypeTags.ARTHROPOD)
                .add(IFWEntityType.SPIDER.get())
                .add(IFWEntityType.WOOD_SPIDER.get())
                .add(IFWEntityType.BLACK_WIDOW_SPIDER.get())
                .add(IFWEntityType.PHASE_SPIDER.get())
                .add(IFWEntityType.CAVE_SPIDER.get())
                .add(IFWEntityType.DEMON_SPIDER.get());

        // 火焰免疫标签
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
                .add(IFWEntityType.DEMON_SPIDER.get())
                .add(IFWEntityType.INFERNO_CREEPER.get())
                .add(IFWEntityType.SHADOW.get());

        // 毒性生物标签
        this.tag(IFWEntityTypeTags.VENOMOUS)
                .add(IFWEntityType.WOOD_SPIDER.get())
                .add(IFWEntityType.BLACK_WIDOW_SPIDER.get())
                .add(IFWEntityType.CAVE_SPIDER.get())
                .add(IFWEntityType.DEMON_SPIDER.get());

        //腐蚀性生物标签
        this.tag(IFWEntityTypeTags.CORROSIVE)
                .add(IFWEntityType.SLIME.get())
                .add(IFWEntityType.JELLY.get())
                .add(IFWEntityType.BLOB.get())
                .add(IFWEntityType.OOZE.get())
                .add(IFWEntityType.PUDDING.get());

    }
}