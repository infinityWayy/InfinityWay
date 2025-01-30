package huix.infinity.datagen.lang;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class IFWCNLanguageProvider extends LanguageProvider {
    public IFWCNLanguageProvider(PackOutput output) {
        super(output, InfinityWay.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("foodtips.saturation", "+%s 饱和度");
        add("foodtips.nutrition", "+%s 饥饿值");
        add("foodtips.protein", "+%s 蛋白质");
        add("foodtips.phytonutrients", "+%s 植物营养素");
        add("foodtips.insulinresponse", "+%s 胰岛素反应");

        add(IFWBlocks.adamantium_block_item.get(), "艾德曼块");
        add(IFWBlocks.adamantium_ore_item.get(), "艾德曼矿石");
        add(IFWBlocks.adamantium_bars_item.get(), "艾德曼栏杆");
        add(IFWBlocks.adamantium_door_item.get(), "艾德曼门");
        add(IFWBlocks.mithril_block_item.get(), "秘银块");
        add(IFWBlocks.mithril_ore_item.get(), "秘银矿石");
        add(IFWBlocks.mithril_bars_item.get(), "秘银栏杆");
        add(IFWBlocks.mithril_door_item.get(), "秘银门");
        add(IFWBlocks.ancient_metal_block_item.get(), "古代金属块");
        add(IFWBlocks.ancient_metal_bars_item.get(), "古代金属栏杆");
        add(IFWBlocks.ancient_metal_door_item.get(), "古代金属门");
        add(IFWBlocks.gold_bars_item.get(), "金栏杆");
        add(IFWBlocks.gold_door_item.get(), "金门");
        add(IFWBlocks.silver_block_item.get(), "银块");
        add(IFWBlocks.silver_ore_item.get(), "银矿石");
        add(IFWBlocks.silver_bars_item.get(), "银栏杆");
        add(IFWBlocks.silver_door_item.get(), "银门");
        add(IFWBlocks.copper_bars_item.get(), "铜栏杆");
        add(IFWBlocks.copper_door_item.get(), "铜门");
        add(IFWBlocks.raw_adamantium_block_item.get(), "粗艾德曼块");
//        add(IFWBlocks.raw_mithril_block_item.get(), "粗秘银块");
        add(IFWBlocks.raw_silver_block_item.get(), "粗银块");
        add(IFWBlocks.deepslate_adamantium_ore_item.get(), "深层艾德曼矿石");
        add(IFWBlocks.deepslate_mithril_ore_item.get(), "深层秘银矿石");
        add(IFWBlocks.deepslate_silver_ore_item.get(), "深层银矿石");

        add(IFWItems.sinew.get(), "皮革绳");
        add(IFWItems.salad.get(), "沙拉");
        add(IFWItems.milk_bowl.get(), "牛奶碗");
        add(IFWItems.water_bowl.get(), "水碗");
        add(IFWItems.cheese.get(), "奶酪");
        add(IFWItems.dough.get(), "面团");
        add(IFWItems.chocolate.get(), "巧克力");
        add(IFWItems.cereal.get(), "麦片粥");
        add(IFWItems.pumpkin_soup.get(), "南瓜汤");
        add(IFWItems.mushroom_soup_cream.get(), "奶油蘑菇汤");
        add(IFWItems.vegetable_soup.get(), "蔬菜汤");
        add(IFWItems.vegetable_soup_cream.get(), "奶油蔬菜汤");
        add(IFWItems.chicken_soup.get(), "鸡汤");
        add(IFWItems.beef_stew.get(), "牛肉汤");
        add(IFWItems.porridge.get(), "燕麦粥");
        add(IFWItems.sorbet.get(), "雪糕");
        add(IFWItems.mashed_potato.get(), "马铃薯泥");
        add(IFWItems.ice_cream.get(), "冰淇淋");
        add(IFWItems.orange.get(), "橘子");
        add(IFWItems.banana.get(), "香蕉");
        add(IFWItems.cooked_worm.get(), "熟虫子");
        add(IFWItems.worm.get(), "虫子");
        add(IFWItems.flint_shard.get(), "燧石碎块");
        add(IFWItems.obsidian_shard.get(), "黑曜石碎片");
        add(IFWItems.glass_shard.get(), "玻璃碎片");
        add(IFWItems.emerald_shard.get(), "绿宝石碎片");
        add(IFWItems.diamond_shard.get(), "钻石碎片");
        add(IFWItems.quartz_shard.get(), "石英碎片");
        add(IFWItems.raw_adamantium.get(), "粗艾德曼");
        add(IFWItems.raw_mithril.get(), "粗秘银");
        add(IFWItems.raw_silver.get(), "粗银");
        add(IFWItems.adamantium_ingot.get(), "艾德曼锭");
        add(IFWItems.adamantium_pickaxe.get(), "艾德曼镐");
        add(IFWItems.adamantium_nugget.get(), "艾德曼粒");
        add(IFWItems.adamantium_shears.get(), "艾德曼剪刀");
        add(IFWItems.adamantium_shovel.get(), "艾德曼锹");
        add(IFWItems.adamantium_hoe.get(), "艾德曼锄");
        add(IFWItems.adamantium_sword.get(), "艾德曼剑");
        add(IFWItems.adamantium_axe.get(), "艾德曼斧");
        add(IFWItems.adamantium_scythe.get(), "艾德曼镰刀");
        add(IFWItems.adamantium_mattock.get(), "艾德曼鹤嘴锄");
        add(IFWItems.adamantium_battle_axe.get(), "艾德曼战斧");
        add(IFWItems.adamantium_war_hammer.get(), "艾德曼战锤");
        add(IFWItems.adamantium_dagger.get(), "艾德曼短剑");
        add(IFWItems.mithril_ingot.get(), "秘银锭");
        add(IFWItems.mithril_pickaxe.get(), "秘银镐");
        add(IFWItems.mithril_nugget.get(), "秘银粒");
        add(IFWItems.mithril_shears.get(), "秘银剪刀");
        add(IFWItems.mithril_shovel.get(), "秘银锹");
        add(IFWItems.mithril_hoe.get(), "秘银锄");
        add(IFWItems.mithril_sword.get(), "秘银剑");
        add(IFWItems.mithril_axe.get(), "秘银斧");
        add(IFWItems.mithril_scythe.get(), "秘银镰刀");
        add(IFWItems.mithril_mattock.get(), "秘银鹤嘴锄");
        add(IFWItems.mithril_battle_axe.get(), "秘银战斧");
        add(IFWItems.mithril_war_hammer.get(), "秘银战锤");
        add(IFWItems.mithril_dagger.get(), "秘银短剑");
        add(IFWItems.ancient_metal_ingot.get(), "古代金属锭");
        add(IFWItems.ancient_metal_pickaxe.get(), "古代金属镐");
        add(IFWItems.ancient_metal_nugget.get(), "古代金属粒");
        add(IFWItems.ancient_metal_shears.get(), "古代金属剪刀");
        add(IFWItems.ancient_metal_shovel.get(), "古代金属锹");
        add(IFWItems.ancient_metal_hoe.get(), "古代金属锄");
        add(IFWItems.ancient_metal_sword.get(), "古代金属剑");
        add(IFWItems.ancient_metal_axe.get(), "古代金属斧");
        add(IFWItems.ancient_metal_scythe.get(), "古代金属镰刀");
        add(IFWItems.ancient_metal_mattock.get(), "古代金属鹤嘴锄");
        add(IFWItems.ancient_metal_battle_axe.get(), "古代金属战斧");
        add(IFWItems.ancient_metal_war_hammer.get(), "古代金属战锤");
        add(IFWItems.ancient_metal_dagger.get(), "古代金属短剑");
        add(IFWItems.silver_ingot.get(), "银锭");
        add(IFWItems.silver_pickaxe.get(), "银镐");
        add(IFWItems.silver_nugget.get(), "银粒");
        add(IFWItems.silver_shears.get(), "银剪刀");
        add(IFWItems.silver_shovel.get(), "银锹");
        add(IFWItems.silver_hoe.get(), "银锄");
        add(IFWItems.silver_sword.get(), "银剑");
        add(IFWItems.silver_axe.get(), "银斧");
        add(IFWItems.silver_scythe.get(), "银镰刀");
        add(IFWItems.silver_mattock.get(), "银鹤嘴锄");
        add(IFWItems.silver_battle_axe.get(), "银战斧");
        add(IFWItems.silver_war_hammer.get(), "银战锤");
        add(IFWItems.silver_dagger.get(), "银短剑");
        add(IFWItems.copper_pickaxe.get(), "铜镐");
        add(IFWItems.copper_nugget.get(), "铜粒");
        add(IFWItems.copper_shears.get(), "铜剪刀");
        add(IFWItems.copper_shovel.get(), "铜锹");
        add(IFWItems.copper_hoe.get(), "铜锄");
        add(IFWItems.copper_sword.get(), "铜剑");
        add(IFWItems.copper_axe.get(), "铜斧");
        add(IFWItems.copper_scythe.get(), "铜镰刀");
        add(IFWItems.copper_mattock.get(), "铜鹤嘴锄");
        add(IFWItems.copper_battle_axe.get(), "铜战斧");
        add(IFWItems.copper_war_hammer.get(), "铜战锤");
        add(IFWItems.copper_dagger.get(), "铜短剑");
        add(IFWItems.rusted_iron_pickaxe.get(), "锈铁镐");
        add(IFWItems.rusted_iron_shears.get(), "锈铁剪刀");
        add(IFWItems.rusted_iron_shovel.get(), "锈铁锹");
        add(IFWItems.rusted_iron_hoe.get(), "锈铁锄");
        add(IFWItems.rusted_iron_sword.get(), "锈铁剑");
        add(IFWItems.rusted_iron_axe.get(), "锈铁斧");
        add(IFWItems.rusted_iron_scythe.get(), "锈铁镰刀");
        add(IFWItems.rusted_iron_mattock.get(), "锈铁鹤嘴锄");
        add(IFWItems.rusted_iron_battle_axe.get(), "锈铁战斧");
        add(IFWItems.rusted_iron_war_hammer.get(), "锈铁战锤");
        add(IFWItems.rusted_iron_dagger.get(), "锈铁短剑");
        add(IFWItems.iron_pickaxe.get(), "铁镐");
        add(IFWItems.iron_shears.get(), "铁剪刀");
        add(IFWItems.iron_shovel.get(), "铁锹");
        add(IFWItems.iron_hoe.get(), "铁锄");
        add(IFWItems.iron_sword.get(), "铁剑");
        add(IFWItems.iron_axe.get(), "铁斧");
        add(IFWItems.iron_scythe.get(), "铁镰刀");
        add(IFWItems.iron_mattock.get(), "铁鹤嘴锄");
        add(IFWItems.iron_battle_axe.get(), "铁战斧");
        add(IFWItems.iron_war_hammer.get(), "铁战锤");
        add(IFWItems.iron_dagger.get(), "铁短剑");
        add(IFWItems.golden_pickaxe.get(), "金镐");
        add(IFWItems.golden_shears.get(), "金剪刀");
        add(IFWItems.golden_shovel.get(), "金锹");
        add(IFWItems.golden_hoe.get(), "金锄");
        add(IFWItems.golden_sword.get(), "金剑");
        add(IFWItems.golden_axe.get(), "金斧");
        add(IFWItems.golden_scythe.get(), "金镰刀");
        add(IFWItems.golden_mattock.get(), "金鹤嘴锄");
        add(IFWItems.golden_battle_axe.get(), "金战斧");
        add(IFWItems.golden_war_hammer.get(), "金战锤");
        add(IFWItems.golden_dagger.get(), "金短剑");
        add(IFWItems.flint_axe.get(), "燧石斧");
        add(IFWItems.flint_knife.get(), "燧石小刀");
        add(IFWItems.flint_hatchet.get(), "燧石手斧");
        add(IFWItems.flint_shovel.get(), "燧石锹");
//        add(IFWItems.obsidian_axe.get(), "黑耀石斧");
//        add(IFWItems.obsidian_knife.get(), "黑耀石小刀");
//        add(IFWItems.obsidian_hatchet.get(), "黑耀石手斧");
//        add(IFWItems.obsidian_shovel.get(), "黑耀石锹");
        add(IFWItems.wooden_club.get(), "木短棒");
        add(IFWItems.wooden_shovel.get(), "木锹");
        add(IFWItems.adamantium_helmet.get(), "艾德曼头盔");
        add(IFWItems.adamantium_chestplate.get(), "艾德曼胸甲");
        add(IFWItems.adamantium_leggings.get(), "艾德曼护腿");
        add(IFWItems.adamantium_boots.get(), "艾德曼鞋子");
        add(IFWItems.adamantium_chainmail_helmet.get(), "艾德曼锁链头盔");
        add(IFWItems.adamantium_chainmail_chestplate.get(), "艾德曼锁链胸甲");
        add(IFWItems.adamantium_chainmail_leggings.get(), "艾德曼锁链护腿");
        add(IFWItems.adamantium_chainmail_boots.get(), "艾德曼锁链鞋子");
        add(IFWItems.mithril_helmet.get(), "秘银头盔");
        add(IFWItems.mithril_chestplate.get(), "秘银胸甲");
        add(IFWItems.mithril_leggings.get(), "秘银护腿");
        add(IFWItems.mithril_boots.get(), "秘银鞋子");
        add(IFWItems.mithril_chainmail_helmet.get(), "秘银锁链头盔");
        add(IFWItems.mithril_chainmail_chestplate.get(), "秘银锁链胸甲");
        add(IFWItems.mithril_chainmail_leggings.get(), "秘银锁链护腿");
        add(IFWItems.mithril_chainmail_boots.get(), "秘银锁链鞋子");
        add(IFWItems.ancient_metal_helmet.get(), "古代金属头盔");
        add(IFWItems.ancient_metal_chestplate.get(), "古代金属胸甲");
        add(IFWItems.ancient_metal_leggings.get(), "古代金属护腿");
        add(IFWItems.ancient_metal_boots.get(), "古代金属鞋子");
        add(IFWItems.ancient_metal_chainmail_helmet.get(), "古代金属锁链头盔");
        add(IFWItems.ancient_metal_chainmail_chestplate.get(), "古代金属锁链胸甲");
        add(IFWItems.ancient_metal_chainmail_leggings.get(), "古代金属锁链护腿");
        add(IFWItems.ancient_metal_chainmail_boots.get(), "古代金属锁链鞋子");
        add(IFWItems.silver_helmet.get(), "银头盔");
        add(IFWItems.silver_chestplate.get(), "银胸甲");
        add(IFWItems.silver_leggings.get(), "银护腿");
        add(IFWItems.silver_boots.get(), "银鞋子");
        add(IFWItems.silver_chainmail_helmet.get(), "银锁链头盔");
        add(IFWItems.silver_chainmail_chestplate.get(), "银锁链胸甲");
        add(IFWItems.silver_chainmail_leggings.get(), "银锁链护腿");
        add(IFWItems.silver_chainmail_boots.get(), "银锁链鞋子");
        add(IFWItems.copper_helmet.get(), "铜头盔");
        add(IFWItems.copper_chestplate.get(), "铜胸甲");
        add(IFWItems.copper_leggings.get(), "铜护腿");
        add(IFWItems.copper_boots.get(), "铜鞋子");
        add(IFWItems.copper_chainmail_helmet.get(), "铜锁链头盔");
        add(IFWItems.copper_chainmail_chestplate.get(), "铜锁链胸甲");
        add(IFWItems.copper_chainmail_leggings.get(), "铜锁链护腿");
        add(IFWItems.copper_chainmail_boots.get(), "铜锁链鞋子");
        add(IFWItems.rusted_iron_helmet.get(), "锈铁头盔");
        add(IFWItems.rusted_iron_chestplate.get(), "锈铁胸甲");
        add(IFWItems.rusted_iron_leggings.get(), "锈铁护腿");
        add(IFWItems.rusted_iron_boots.get(), "锈铁鞋子");
        add(IFWItems.rusted_iron_chainmail_helmet.get(), "锈铁锁链头盔");
        add(IFWItems.rusted_iron_chainmail_chestplate.get(), "锈铁锁链胸甲");
        add(IFWItems.rusted_iron_chainmail_leggings.get(), "锈铁锁链护腿");
        add(IFWItems.rusted_iron_chainmail_boots.get(), "锈铁锁链鞋子");
        add(IFWItems.iron_helmet.get(), "铁头盔");
        add(IFWItems.iron_chestplate.get(), "铁胸甲");
        add(IFWItems.iron_leggings.get(), "铁护腿");
        add(IFWItems.iron_boots.get(), "铁鞋子");
        add(IFWItems.iron_chainmail_helmet.get(), "铁锁链头盔");
        add(IFWItems.iron_chainmail_chestplate.get(), "铁锁链胸甲");
        add(IFWItems.iron_chainmail_leggings.get(), "铁锁链护腿");
        add(IFWItems.iron_chainmail_boots.get(), "铁锁链鞋子");
        add(IFWItems.golden_helmet.get(), "金头盔");
        add(IFWItems.golden_chestplate.get(), "金胸甲");
        add(IFWItems.golden_leggings.get(), "金护腿");
        add(IFWItems.golden_boots.get(), "金鞋子");
        add(IFWItems.golden_chainmail_helmet.get(), "金锁链头盔");
        add(IFWItems.golden_chainmail_chestplate.get(), "金锁链胸甲");
        add(IFWItems.golden_chainmail_leggings.get(), "金锁链护腿");
        add(IFWItems.golden_chainmail_boots.get(), "金锁链鞋子");
    }
}
