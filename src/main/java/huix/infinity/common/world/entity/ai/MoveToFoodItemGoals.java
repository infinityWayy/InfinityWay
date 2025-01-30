package huix.infinity.common.world.entity.ai;

import huix.infinity.common.world.entity.animal.Livestock;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;

import java.util.Comparator;
import java.util.Optional;

public class MoveToFoodItemGoals extends Goal {
    protected final PathfinderMob mob;

    protected final TagKey<Item> tag;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected boolean isRunning;
    private final int max_path_length = 16;
    public MoveToFoodItemGoals(PathfinderMob mob, TagKey<Item> items) {
        this.mob = mob;
        this.tag = items;
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof Livestock livestock && livestock.isHungry() && !isRunning) {
            return this.findFoodPosition();
        } else {
            return false;
        }
    }

    private boolean findFoodPosition() {
        Optional<ItemEntity> itemEntity = this.mob.level().getEntitiesOfClass(ItemEntity.class,
                mob.getBoundingBox().inflate(max_path_length, (double) max_path_length / 4, max_path_length),
                entity -> entity.getItem().getTags().anyMatch(x -> x == tag))
                .stream().min(Comparator.comparing(x -> x.distanceTo(mob)));
        if (itemEntity.isPresent()) {
            this.posX = itemEntity.get().getBlockX();
            this.posY = itemEntity.get().getBlockY();
            this.posZ = itemEntity.get().getBlockZ();
            return true;
        }
        return false;
    }
}
