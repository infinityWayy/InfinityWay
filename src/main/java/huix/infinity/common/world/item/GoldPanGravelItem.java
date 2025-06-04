package huix.infinity.common.world.item;

import huix.infinity.common.world.item.IFWItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GoldPanGravelItem extends Item {
    public static final int USE_TIME = 120;

    public GoldPanGravelItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_TIME;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return slotChanged || !ItemStack.isSameItem(oldStack, newStack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!isPlayerInWater(level, player)) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("tooltip.infinity.goldpan.need_water"), true);
            }
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {

        if (remainingUseDuration % 8 == 0 && !level.isClientSide) {
            if (level.random.nextBoolean()) {
                level.playSound(null, entity.blockPosition(), SoundEvents.WATER_AMBIENT, SoundSource.PLAYERS, 0.3f, 0.8f + level.random.nextFloat() * 0.4f);
            } else {
                level.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_SPLASH, SoundSource.PLAYERS, 0.2f, 1.0f + level.random.nextFloat() * 0.2f);
            }
        }

        if (level.isClientSide && remainingUseDuration % 4 == 0) {
            double x = entity.getX() + (level.random.nextDouble() - 0.5) * 0.8;
            double y = entity.getY() + entity.getEyeHeight() - 0.3;
            double z = entity.getZ() + (level.random.nextDouble() - 0.5) * 0.8;

            level.addParticle(ParticleTypes.SPLASH,
                    x, y, z,
                    (level.random.nextDouble() - 0.5) * 0.1,
                    0.1,
                    (level.random.nextDouble() - 0.5) * 0.1);

            if (level.random.nextInt(3) == 0) {
                level.addParticle(ParticleTypes.BUBBLE,
                        x, y, z,
                        (level.random.nextDouble() - 0.5) * 0.05,
                        0.05,
                        (level.random.nextDouble() - 0.5) * 0.05);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.infinity.goldpan.filled").withStyle(ChatFormatting.WHITE));
        tooltipComponents.add(Component.translatable("tooltip.infinity.goldpan.instruction").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && level instanceof ServerLevel serverLevel) {
            List<ItemStack> drops = generateGravelDrops(serverLevel);

            for (ItemStack drop : drops) {
                ItemHandlerHelper.giveItemToPlayer(player, drop);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound(null, entity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.8f, 1.2f);

            return new ItemStack(IFWItems.gold_pan.get());
        }
        return stack;
    }

    private boolean isPlayerInWater(Level level, Player player) {
        return level.getFluidState(player.blockPosition()).getType() == Fluids.WATER ||
                level.getFluidState(player.blockPosition().above()).getType() == Fluids.WATER;
    }

    private List<ItemStack> generateGravelDrops(ServerLevel level) {
        Object[][] drops = {
                {3749, IFWItems.flint_shard.get()},
                {1543, IFWItems.copper_nugget.get()},
                {495, IFWItems.silver_nugget.get()},
                {276, Items.FLINT},
                {163, Items.GOLD_NUGGET},
                {53, IFWItems.obsidian_shard.get()},
                {18, IFWItems.emerald_shard.get()},
                {6, IFWItems.diamond_shard.get()},
                {2, IFWItems.mithril_nugget.get()},
                {1, IFWItems.adamantium_nugget.get()}
        };

        int totalWeight = 6306;
        int random = level.random.nextInt(totalWeight);
        int weight = 0;

        for (Object[] drop : drops) {
            if (random < (weight += (int)drop[0])) {
                return List.of(new ItemStack((Item)drop[1]));
            }
        }

        return List.of(new ItemStack((Item)drops[drops.length-1][1]));
    }
}