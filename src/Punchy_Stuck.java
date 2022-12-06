import java.util.*;
import java.util.function.Predicate;

import processing.core.PImage;
public class Punchy_Stuck implements AnimatingEntities, ExecutingEntities{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    public final double actionPeriod;
    private final double animationPeriod;

    public Punchy_Stuck(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
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
        // if reach the end of the images replace with PunchyAngry
        if(imageIndex > images.size()) {
            Punchy_Angry punchyAngry = Functions.createPunchyAngry(Functions.getPunchyAngryKey(), position, Functions.getPunchyAngryActionPeriod(), Functions.getPunchyAngryAnimationPeriod(), imageStore.getImageList(Functions.getPunchyAngryKey()));
            world.removeEntity(scheduler, this);
            world.addEntity(punchyAngry);
            punchyAngry.scheduleActions(scheduler, world, imageStore);
        }

        ExecutingEntities.super._executeActivity(world, imageStore, scheduler);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), actionPeriod);
        AnimatingEntities.super.scheduleActions(scheduler, world, imageStore);
    }

}

