//import java.util.*;
//
//import processing.core.PImage;
//public class Villager implements AnimatingEntities, MovingEntities, ExecutingEntities{
//    private final String id;
//    private Point position;
//    private final List<PImage> images;
//    private int imageIndex;
//    public final double actionPeriod;
//    private final double animationPeriod;
//
//    public Villager(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
//        this.id = id;
//        this.position = position;
//        this.images = images;
//        this.imageIndex = 0;
//        this.actionPeriod = actionPeriod;
//        this.animationPeriod = animationPeriod;
//    }
//
//    public String getId()
//    {
//        return id;
//    }
//    public Point getPosition()
//    {
//        return position;
//    }
//    public void setPosition(Point newPosition) { position = newPosition; }
//    public List<PImage> getImages() { return images; }
//    public int getImageIndex() { return imageIndex; }
//    public void setImageIndex(int newImageIndex)
//    {
//        imageIndex = newImageIndex;
//    }
//    public double getActionPeriod()
//    {
//        return actionPeriod;
//    }
//    public double getAnimationPeriod() {
//        return animationPeriod;
//    }
//    public void executeActivity (WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> villagerTarget = world.findNearest(position, new ArrayList<>(List.of(Hole.class)));
//
//        if (villagerTarget.isPresent()) {
//            Point tgtPos = villagerTarget.get().getPosition();
//
//            if (moveTo(world, villagerTarget.get(), scheduler)) {
//
//                Sapling sapling = Functions.createSapling(Functions.getSaplingKey() + "_" + villagerTarget.get().getId(), tgtPos, imageStore.getImageList(Functions.getSaplingKey()), 0);
//
//                world.addEntity(sapling);
//                sapling.scheduleActions(scheduler, world, imageStore);
//            }
//        }
//        ExecutingEntities.super._executeActivity(world, imageStore, scheduler);
//    }
//    public void _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
//        world.removeEntity(scheduler, target);
//    }
//
//    public boolean _nextPos(WorldModel world, Point newPos) {
//        return false;
//    }
//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), actionPeriod);
//        AnimatingEntities.super.scheduleActions(scheduler, world, imageStore);
//    }
//
//}
//
