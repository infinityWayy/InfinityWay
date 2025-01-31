package huix.infinity.datagen.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

// We cannot use a record because records cannot extend other classes.
public class JungleLeavesLootModifier extends LootModifier {
    // 定义 MapCodec
    public static final MapCodec<JungleLeavesLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).and(
                    inst.group(
                            ItemStack.CODEC.fieldOf("item").forGetter(m -> m.item),
                            Codec.INT.fieldOf("probability").forGetter(m -> m.probability)
                    )
            ).apply(inst, JungleLeavesLootModifier::new)
    );

    // Our extra properties.
    private final ItemStack item;
    private final int probability;

    // First constructor parameter is the list of conditions. The rest is our extra properties.
    public JungleLeavesLootModifier(LootItemCondition[] conditions, ItemStack item, int probability) {
        super(conditions);
        this.item = item;
        this.probability = probability;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, LootContext context) {

        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);

        // 检查是否是丛林树叶
        if (state == null || !state.is(Blocks.JUNGLE_LEAVES)) {
            return generatedLoot;
        }

        // 如果使用精准采集，则不掉落香蕉
        if (tool != null) {

            // 获取精准采集附魔的Holder
            Holder<Enchantment> silkTouchHolder = context.getLevel().registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolderOrThrow(Enchantments.SILK_TOUCH);

            // 检查是否有精准采集附魔
            if (tool.getEnchantmentLevel(silkTouchHolder) > 0) {
                return generatedLoot;
            }
        }

        // 计算时运加成后的实际概率
        int fortuneLevel = 0;

        // 获取时运附魔的Holder
        Holder<Enchantment> fortuneHolder = context.getLevel().registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolderOrThrow(Enchantments.FORTUNE);
        if (tool != null) {
            fortuneLevel = tool.getEnchantmentLevel(fortuneHolder);
        }

        int actualProbability = probability - (fortuneLevel * 20);

        // 确保概率不超过合理范围
        actualProbability = Math.max(actualProbability, 1);

        // 根据概率决定是否掉落
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