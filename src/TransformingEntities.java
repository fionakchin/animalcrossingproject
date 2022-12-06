public interface TransformingEntities extends Entity{
    boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}
