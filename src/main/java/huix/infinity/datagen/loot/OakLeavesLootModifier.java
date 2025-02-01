package huix.infinity.datagen.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class OakLeavesLootModifier extends LootModifier {
    public static final MapCodec<OakLeavesLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).and(
                    inst.group(
                            ItemStack.CODEC.fieldOf("item").forGetter(m -> m.item),
                            Codec.INT.fieldOf("probability").forGetter(m -> m.probability)
                    )
            ).apply(inst, OakLeavesLootModifier::new)
    );

    private final ItemStack item;
    private final int probability;

    public OakLeavesLootModifier(LootItemCondition[] conditions, ItemStack item, int probability) {
        super(conditions);
        this.item = item;
        this.probability = probability;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, LootContext context) {

        Level level = context.getLevel();
        Vec3 pos = context.getParamOrNull(LootContextParams.ORIGIN);
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);

        if (state == null || !state.is(Blocks.OAK_LEAVES) ||
                (pos != null && !level.getBiome(new BlockPos((int) pos.x, (int) pos.y, (int) pos.z))
                        .is(Tags.Biomes.IS_JUNGLE))) {
            return generatedLoot;
        }

        if (tool != null) {

            Holder<Enchantment> silkTouchHolder = context.getLevel().registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolderOrThrow(Enchantments.SILK_TOUCH);

            if (tool.getEnchantmentLevel(silkTouchHolder) > 0) {
                return generatedLoot;
            }
        }

        int fortuneLevel = 0;

        Holder<Enchantment> fortuneHolder = context.getLevel().registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolderOrThrow(Enchantments.FORTUNE);
        if (tool != null) {
            fortuneLevel = tool.getEnchantmentLevel(fortuneHolder);
        }

        int actualProbability = probability - (fortuneLevel * 20);

        actualProbability = Math.max(actualProbability, 1);

        if (context.getRandom().nextInt(actualProbability) == 0) {
            generatedLoot.add(item.copy());
        }

        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
