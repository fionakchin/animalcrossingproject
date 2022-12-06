public interface ExecutingEntities extends SchedulingEntities{
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    default void _executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), getActionPeriod());
    }
    double getActionPeriod();
}
