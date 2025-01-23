package huix.infinity.common.player;

import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public enum LevelBonusStats {
   HARVESTING(0.02F, -0.02F),
   CRAFTING(0.02F, -0.02F),
   MELEE_DAMAGE(0.005F, -0.02F);

   private final float bonusPerLevel;
   private final float bonusPerNegativeLevel;

   LevelBonusStats(float bonusPerLevel, float bonusPerNegativeLevel) {
      this.bonusPerLevel = bonusPerLevel;
      this.bonusPerNegativeLevel = bonusPerNegativeLevel;
   }

   public float calcBonusFor(int level) {
      return level > 0 ? this.bonusPerLevel * (float)level : this.bonusPerNegativeLevel * (float)level;
   }

   public float calcBonusFor(Player player) {
      return this.calcBonusFor(player.experienceLevel);
   }


}
