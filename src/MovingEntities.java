import java.util.List;

public interface MovingEntities extends Entity{
    default boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (getPosition().adjacent(target.getPosition())) {
            _moveTo(world, target, scheduler);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
    void _moveTo(WorldModel world, Entity target, EventScheduler scheduler);
    default Point nextPosition(WorldModel world, Point destPos)
    {
//        PathingStrategy strat = new SingleStepPathingStrategy();
//        List<Point> path = strat.computePath(
//                this.getPosition(),
//                destPos,
//                // write lambda that takes in a point and returns boolean,
//                // can pass through or not -> within bounds, occupied?,
//                // lambda/method reference operator to check if a point is adjacent to point,
//                PathingStrategy.CARDINAL_NEIGHBORS
//                // )
        PathingStrategy strat = new AStarPathingStrategy();
        List<Point> path = strat.computePath(this.getPosition(), destPos,
                p -> (world.withinBounds(p) && (!world.isOccupied(p) || _nextPos(world, p))),
                (p, p2) -> p.adjacent(p2), PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size() == 0)
            return this.getPosition();
        else
            return path.get(0);


//        int horiz = Integer.signum(destPos.x - getPosition().x);
//        Point newPos = new Point(getPosition().x + horiz, getPosition().y);
//
//        if (horiz == 0 || world.isOccupied(newPos) && _nextPos(world, destPos, newPos)) {
//            int vert = Integer.signum(destPos.y - getPosition().y);
//            newPos = new Point(getPosition().x, getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && _nextPos(world, destPos, newPos)) {
//                newPos = getPosition();
//            }
//        }
//        return newPos;
    }

    boolean _nextPos(WorldModel world, Point newPos);
}
