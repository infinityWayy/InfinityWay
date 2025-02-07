package huix.infinity.common.levelgen.structure;

import com.mojang.serialization.MapCodec;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasureStructure;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWStructureTypes {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, InfinityWay.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<VillageStructure>> village_structure = STRUCTURE_TYPES.register("village",
            () -> () -> VillageStructure.CODEC);
}
