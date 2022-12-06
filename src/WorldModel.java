import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel {
    private static int numRows;
    private static int numCols;
    private Background[][] background;
    private Entity[][] occupancy;
    private Set<Entity> entities;

    public WorldModel() {

    }

    public int getNumRows() {
        return numRows;
    }
    public void setNumRows(int newNumRows)
    {
        numRows = newNumRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int newNumCols) {
        numCols = newNumCols;
    }

    public Background[][] getBackground() {
        return background;
    }

    public void setBackground(Background[][] background) {
        this.background = background;
    }

    public void setOccupancy(Entity[][] occupancy) {
        this.occupancy = occupancy;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }

    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log(){
        List<String> list = new ArrayList<>();
        for (Entity entity : entities) {
            String log = entity.log();
            if(log != null) list.add(log);
        }
        return list;
    }
    public static boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < numRows && pos.x >= 0 && pos.x < numCols;
    }
    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            if (entity instanceof House){
                setOccupancyCell(new Point(entity.getPosition().x + 1, entity.getPosition().y + 1), entity);
                setOccupancyCell(new Point(entity.getPosition().x, entity.getPosition().y + 1), entity);
                setOccupancyCell(new Point(entity.getPosition().x + 1, entity.getPosition().y), entity);

            }
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }
    public void tryAddEntity(Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }
    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell( pos) != null;
    }
    public void moveEntity(EventScheduler scheduler, MovingEntities entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            Optional<Entity> occupant = getOccupant(pos);
            occupant.ifPresent(target -> removeEntity(scheduler, target));
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }
    public void removeEntity(EventScheduler scheduler, Entity entity) {
        scheduler.unscheduleAllEvents(entity);
        removeEntityAt(entity.getPosition());
    }
    public void removeEntityAt(Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            // Entity entity = getOccupancyCell(pos);
            Entity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }
    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }
    public Optional<PImage> getBackgroundImage(Point pos) {
        if (withinBounds(pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }
    public Entity getOccupancyCell(Point pos) {
        return occupancy[pos.y][pos.x];
    }
    private void setOccupancyCell(Point pos, Entity entity) {
        occupancy[pos.y][pos.x] = entity;
    }
    private Background getBackgroundCell(Point pos) {
        return background[pos.y][pos.x];
    }
    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.y][pos.x] = background;
    }
    public Optional<Entity> findNearest(Point pos, List<Class> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (Class kind : kinds) {
            for (Entity entity : entities) {
                if (entity.getClass() == kind) {
                    ofType.add(entity);
                }
            }
        }

        return pos.nearestEntity(ofType);
    }
    public void load(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        Functions.parseSaveFile(this, saveFile, imageStore, defaultBackground);
        if(background == null){
            background = new Background[numRows][numCols];
            for (Background[] row : background)
                Arrays.fill(row, defaultBackground);
        }
        if(occupancy == null){
            occupancy = new Entity[numRows][numCols];
            entities = new HashSet<>();
        }
    }
}
