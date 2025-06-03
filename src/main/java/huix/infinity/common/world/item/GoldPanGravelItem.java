package huix.infinity.common.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class GoldPanGravelItem extends Item {
    public static final int USE_TIME = 120;

    public GoldPanGravelItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration( ItemStack stack,  LivingEntity entity) {
        return USE_TIME;
    }

    @Override
    public boolean shouldCauseReequipAnimation( ItemStack oldStack,  ItemStack newStack, boolean slotChanged) {
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
    public void onUseTick( Level level,  LivingEntity entity,  ItemStack stack, int remainingUseDuration) {
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
    public void appendHoverText( ItemStack stack,  Item.TooltipContext context,  List<Component> tooltipComponents,  TooltipFlag tooltipFlag) {
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

            int newDamage = stack.getDamageValue() + 1;

            if (newDamage >= stack.getMaxDamage()) {

                level.playSound(null, entity.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.8f, 0.8f + level.random.nextFloat() * 0.4f);
                return ItemStack.EMPTY;
            } else {

                ItemStack result = stack.transmuteCopy(IFWItems.gold_pan.get(), 1);
                result.setDamageValue(newDamage);
                return result;
            }
        }
        return stack;
    }

    @Override
    public boolean isDamageable( ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxDamage( ItemStack stack) {
        return 16;
    }

    @Override
    public boolean isEnchantable( ItemStack stack) {
        return false;
    }

    private boolean isPlayerInWater( Level level,  Player player) {
        return level.getFluidState(player.blockPosition()).getType() == Fluids.WATER ||
                level.getFluidState(player.blockPosition().above()).getType() == Fluids.WATER;
    }

    private List<ItemStack> generateGravelDrops( ServerLevel level) {
        if (level.random.nextInt(7) == 0) {
            return List.of(new ItemStack(IFWItems.flint_shard.get()));
        } else if (level.random.nextInt(17) == 0) {
            return List.of(new ItemStack(IFWItems.copper_nugget.get()));
        } else if (level.random.nextInt(53) == 0) {
            return List.of(new ItemStack(IFWItems.silver_nugget.get()));
        } else if (level.random.nextInt(95) == 0) {
            return List.of(new ItemStack(Items.FLINT));
        } else if (level.random.nextInt(161) == 0) {
            return List.of(new ItemStack(Items.GOLD_NUGGET));
        } else if (level.random.nextInt(495) == 0) {
            return List.of(new ItemStack(IFWItems.obsidian_shard.get()));
        } else if (level.random.nextInt(1457) == 0) {
            return List.of(new ItemStack(IFWItems.emerald_shard.get()));
        } else if (level.random.nextInt(4373) == 0) {
            return List.of(new ItemStack(IFWItems.diamond_shard.get()));
        } else if (level.random.nextInt(13121) == 0) {
            return List.of(new ItemStack(IFWItems.mithril_nugget.get()));
        } else if (level.random.nextInt(26243) == 0) {
            return List.of(new ItemStack(IFWItems.adamantium_nugget.get()));
        } else {
            return List.of();
        }
    }
}