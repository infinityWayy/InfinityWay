package huix.infinity.common.command;

import huix.infinity.common.world.entity.player.LevelBonusStats;
import huix.infinity.extension.func.FoodDataExtension;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class PlayerCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        // 注册 /exp 命令
        dispatcher.register(
                Commands.literal("exp")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            if (source.getEntity() instanceof Player player) {
                                int exp = player.totalExperience;
                                source.sendSuccess(() ->
                                        Component.translatable("commands.exp.message", exp)
                                                .withStyle(ChatFormatting.YELLOW), false);
                            }
                            return 1;
                        })
        );

        // 注册 /day 命令
        dispatcher.register(
                Commands.literal("day")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            if (source.getEntity() instanceof Player player) {
                                long dayTime = player.level().getDayTime();
                                int day = (int) (dayTime / 24000L) + 1;
                                source.sendSuccess(() ->
                                        Component.translatable("commands.day.message", day)
                                                .withStyle(ChatFormatting.YELLOW), false);
                            }
                            return 1;
                        })
        );

        // 注册 /stats 命令
        dispatcher.register(
                Commands.literal("stats")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            if (source.getEntity() instanceof Player player) {
                                FoodDataExtension foodData = player.getFoodData();

                                int phytonutrients = foodData.ifw_phytonutrients();
                                int protein = foodData.ifw_protein();
                                int insulinResponse = foodData.ifw_insulinResponse();

                                // 上限值
                                final int PHYTONUTRIENTS_MAX = 160000;
                                final int PROTEIN_MAX = 160000;
                                final int INSULIN_RESPONSE_MAX = 192000;

                                // 计算百分比
                                double phytPercent = (double) phytonutrients / PHYTONUTRIENTS_MAX * 100;
                                double proteinPercent = (double) protein / PROTEIN_MAX * 100;
                                double insulinResponsePercent = (double) insulinResponse / INSULIN_RESPONSE_MAX * 100;

                                // 构建带颜色的 Component
                                Component phytComponent = Component.translatable(
                                        "commands.stats.phytonutrients",
                                        phytonutrients, PHYTONUTRIENTS_MAX, phytPercent, 100.0
                                ).withStyle(ChatFormatting.GREEN);

                                Component proteinComponent = Component.translatable(
                                        "commands.stats.protein",
                                        protein, PROTEIN_MAX, proteinPercent, 100.0
                                ).withStyle(ChatFormatting.YELLOW);

                                Component insulinComponent = Component.translatable(
                                        "commands.stats.insulinResponse",
                                        insulinResponse, INSULIN_RESPONSE_MAX, insulinResponsePercent, 100.0
                                ).withStyle(ChatFormatting.LIGHT_PURPLE);

                                // 拼接为多行 Component
                                Component message = Component.empty()
                                        .append(phytComponent)
                                        .append(Component.literal("\n"))
                                        .append(proteinComponent)
                                        .append(Component.literal("\n"))
                                        .append(insulinComponent);

                                source.sendSuccess(() -> message, false);
                            }
                            return 1;
                        })
        );


        dispatcher.register(
                Commands.literal("status")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            if (source.getEntity() instanceof Player player) {
                                // 获取加成值
                                float harvestingBonus = LevelBonusStats.HARVESTING.calcBonusFor(player);
                                float craftingBonus = LevelBonusStats.CRAFTING.calcBonusFor(player);
                                float meleeDamageBonus = LevelBonusStats.MELEE_DAMAGE.calcBonusFor(player);
                                int armorValue = player.getArmorValue();

                                // 构建带颜色的 Component
                                Component harvestingComponent = buildStatComponent("commands.status.harvesting", harvestingBonus);
                                Component craftingComponent = buildStatComponent("commands.status.crafting", craftingBonus);
                                Component meleeDamageComponent = buildStatComponent("commands.status.melee_damage", meleeDamageBonus);
                                Component armorComponent = Component.translatable("commands.status.armor", armorValue)
                                        .withStyle(ChatFormatting.AQUA);

                                // 拼接为完整消息（每行显示一个属性）
                                Component message = Component.empty()
                                        .append(harvestingComponent)
                                        .append(Component.literal("\n"))
                                        .append(craftingComponent)
                                        .append(Component.literal("\n"))
                                        .append(meleeDamageComponent)
                                        .append(Component.literal("\n"))
                                        .append(armorComponent);

                                source.sendSuccess(() -> message, false);
                            }
                            return 1;
                        })
        );
    }

    // 构建带颜色的属性文本
    private static Component buildStatComponent(String key, float bonus) {
        String formattedBonus = formatBonus(bonus);
        return Component.translatable(key, formattedBonus)
                .withStyle(bonus >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED);
    }

    // 格式化加成值为百分比格式（如 +20.0% 或 -15.0%）
    private static String formatBonus(float bonus) {
        return String.format("%.1f%%", bonus * 100);
    }
}