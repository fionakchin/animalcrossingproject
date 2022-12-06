import java.util.*;

import processing.core.PImage;
public class Punchy_Not_Full implements PunchyEntities {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private int resourceCount;
    public final double actionPeriod;
    private final double animationPeriod;

    public Punchy_Not_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
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
    public void setPosition(Point newPosition)
    {
        position = newPosition;

    }
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

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(position, new ArrayList<>(Arrays.asList(Flower.class, Sapling.class)));

        if (target.isEmpty() || !moveTo(world, target.get(), scheduler) || !transform(world, scheduler, imageStore)) {
            PunchyEntities.super._executeActivity(world, imageStore, scheduler);
        }
    }
    private boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (resourceCount >= resourceLimit) {
            Punchy_Full dude = Functions.createPunchyFull(id, position, actionPeriod, animationPeriod, resourceLimit, images);

            scheduler.unscheduleAllEvents(this);
            PunchyEntities.super._transform(world, scheduler, imageStore, dude);

            return true;
        }

        return false;
    }
    public void _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        resourceCount += 1;
        if (target instanceof PlantEntities) {

            ((PlantEntities) target).setHealth(((PlantEntities) target).getHealth() - 1);
        }
    }

}
