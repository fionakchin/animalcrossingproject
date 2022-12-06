public interface AnimatingEntities extends SchedulingEntities{
    double getAnimationPeriod();

    default void nextImage() {
        setImageIndex(getImageIndex() + 1);
    }
    void setImageIndex(int newImageIndex);

    default void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Functions.createAnimationAction(this, 0), getAnimationPeriod());
    }

}
