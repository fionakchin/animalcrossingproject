public interface PunchyEntities extends MovingEntities, AnimatingEntities, ExecutingEntities{

    default void _transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore, SchedulingEntities punchy)
    {
        world.removeEntity(scheduler, this);

        world.addEntity(punchy);
        punchy.scheduleActions(scheduler, world, imageStore);
    }
//    default Point nextPosition(WorldModel world, Point destPos)
//    {
//        PathingStrategy strat = new SingleStepPathingStrategy();
//        List<Point> path = strat.computePath(this.getPosition(), destPos,
//                p -> (world.withinBounds(p) && (!world.isOccupied(p) || _nextPos(world, p))),
//                (p, p2) -> p.adjacent(p2), PathingStrategy.CARDINAL_NEIGHBORS);
//
//        if (path.size() == 0)
//            return this.getPosition();
//        else
//            return path.get(0);
//
//    }
    default boolean _nextPos(WorldModel world, Point newPos) {
//        return world.getOccupancyCell(newPos).getClass() != Stump.class;
        return world.getOccupancyCell(newPos).getClass() == Stump.class;
    }
    default void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), getActionPeriod());
        AnimatingEntities.super.scheduleActions(scheduler, world, imageStore);
    }
}
