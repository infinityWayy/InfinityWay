package huix.infinity.common.world.entity.player;

import net.minecraft.world.entity.player.Player;

public enum LevelBonusStats {
   HARVESTING(0.02F, -0.02F),
   CRAFTING(0.02F, -0.02F),
   MELEE_DAMAGE(0.005F, -0.02F);

   private final float bonusPerLevel;
   private final float bonusPerNegativeLevel;

   LevelBonusStats(final float bonusPerLevel, final float bonusPerNegativeLevel) {
      this.bonusPerLevel = bonusPerLevel;
      this.bonusPerNegativeLevel = bonusPerNegativeLevel;
   }

   public float calcBonusFor(final int level) {
      return level > 0 ? this.bonusPerLevel * (float)level : this.bonusPerNegativeLevel * (float)level;
   }

   public float calcBonusFor(final Player player) {
      return this.calcBonusFor(player.experienceLevel);
   }


}
