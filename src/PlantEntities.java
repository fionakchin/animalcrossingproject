public interface PlantEntities extends AnimatingEntities, ExecutingEntities{
    int getHealth();
    void setHealth(int newHealth);
    default boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (getHealth() <= 0) {
            Stump hole = Functions.createStump(Functions.getStumpKey() + "_" + getId(), getPosition(), imageStore.getImageList(Functions.getStumpKey()));

            world.removeEntity(scheduler, this);

            world.addEntity(hole);

            return true;
        }
        return false;
    }
    default boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        return transform(world, scheduler, imageStore);
    }

    default void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!transformPlant(world, scheduler, imageStore)) {
            ExecutingEntities.super._executeActivity(world, imageStore, scheduler);
        }
    }
    default void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), getActionPeriod());
        AnimatingEntities.super.scheduleActions(scheduler, world, imageStore);
    }
}
