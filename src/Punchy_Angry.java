import java.util.*;

import processing.core.PImage;
public class Punchy_Angry implements AnimatingEntities, MovingEntities, ExecutingEntities{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    public final double actionPeriod;
    private final double animationPeriod;

    public Punchy_Angry(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public String getId()
    {
        return id;
    }
    public Point getPosition()
    {
        return position;
    }
    public void setPosition(Point newPosition) { position = newPosition; }
    public List<PImage> getImages() { return images; }
    public int getImageIndex() { return imageIndex; }
    public void setImageIndex(int newImageIndex)
    {
        imageIndex = newImageIndex;
    }
    public double getActionPeriod()
    {
        return actionPeriod;
    }
    public double getAnimationPeriod() {
        return animationPeriod;
    }
    public void executeActivity (WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> angryTarget = world.findNearest(position, new ArrayList<>(List.of(Bob.class)));
//        Optional<Entity> afterTarget = world.findNearest(position, new ArrayList<>(List.of(Resetti.class)));

        if (angryTarget.isPresent()) {
            Point tgtPos = angryTarget.get().getPosition();

            if (moveTo(world, angryTarget.get(), scheduler)) {
                Punchy_Not_Full punchy_not_full = Functions.createPunchyNotFull(Functions.getPunchyKey(), tgtPos, Functions.getNewPunchyActionPeriod(), Functions.getNewPunchyAnimationPeriod(), Functions.getPunchyLimit(), imageStore.getImageList(Functions.getPunchyKey()));
                world.addEntity(punchy_not_full);
                punchy_not_full.scheduleActions(scheduler, world, imageStore);
//                Sapling sapling = Functions.createSapling(Functions.getSaplingKey() + "_" + angryTarget.get().getId(), tgtPos, imageStore.getImageList(Functions.getSaplingKey()), 0);
//
//                world.addEntity(sapling);
//                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }
//        else{
//            if (afterTarget.isPresent()) {
//                Point tgtPos = afterTarget.get().getPosition();
//
//                if (moveTo(world, afterTarget.get(), scheduler))
//                {
//                    Resetti resetti = Functions.createResetti(Functions.getResettiKey(),
//                            tgtPos, Functions.getResettiActionPeriod(),
//                            Functions.getResettiAnimationPeriod(), imageStore.getImages().get(Functions.getResettiKey()));
//                    world.addEntity(resetti);
//                    resetti.scheduleActions(scheduler, world, imageStore);
//                }
//            }
//        }
        ExecutingEntities.super._executeActivity(world, imageStore, scheduler);
    }
    public void _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        world.removeEntity(scheduler, target);
    }

    public boolean _nextPos(WorldModel world, Point newPos) {
        return false;
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), actionPeriod);
        AnimatingEntities.super.scheduleActions(scheduler, world, imageStore);
    }

}

