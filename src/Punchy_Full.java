import java.util.*;

import processing.core.PImage;
public class Punchy_Full implements PunchyEntities {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    public final double actionPeriod;
    private final double animationPeriod;

    public Punchy_Full(String id, Point position, List<PImage> images, int resourceLimit, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPosition) {
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
        Optional<Entity> fullTarget = world.findNearest(position, new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && PunchyEntities.super.moveTo(world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore);
        } else {
            PunchyEntities.super._executeActivity(world, imageStore, scheduler);
        }
    }

    public void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        SchedulingEntities punchy = Functions.createPunchyNotFull(id, position, actionPeriod, animationPeriod, resourceLimit, images);

        PunchyEntities.super._transform(world, scheduler, imageStore, punchy);
    }

    public void _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {}
}
