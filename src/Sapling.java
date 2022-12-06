import java.util.*;

import processing.core.PImage;
public class Sapling implements PlantEntities{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    public final double actionPeriod;
    private final double animationPeriod;
    private int health;
    private final int healthLimit;
    private static final double FLOWER_ANIMATION_MAX = 0.600;
    private static final double FLOWER_ANIMATION_MIN = 0.050;
    private static final double FLOWER_ACTION_MAX = 1.400;
    private static final double FLOWER_ACTION_MIN = 1.000;
    private static final int FLOWER_HEALTH_MAX = 3;
    private static final int FLOWER_HEALTH_MIN = 1;

    public Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
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
    public List<PImage> getImages()
    {
        return images;
    }
    public int getImageIndex()
    {
        return imageIndex;
    }
    public void setImageIndex(int newImageIndex)
    {
        imageIndex = newImageIndex;
    }
    public double getActionPeriod() { return actionPeriod; }
    public double getAnimationPeriod() {
        return animationPeriod;
    }
    public int getHealth()
    {
        return health;
    }
    public void setHealth(int newHealth)
    {
        health = newHealth;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        health++;
        PlantEntities.super.executeActivity(world, imageStore, scheduler);
    }
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if(!PlantEntities.super.transform(world, scheduler, imageStore) && health >= healthLimit)
        {
            Flower flower = Functions.createFlower(Functions.getFlowerKey() + "_" + id, position, Functions.getNumFromRange(FLOWER_ACTION_MAX, FLOWER_ACTION_MIN), Functions.getNumFromRange(FLOWER_ANIMATION_MAX, FLOWER_ANIMATION_MIN), Functions.getIntFromRange(FLOWER_HEALTH_MAX, FLOWER_HEALTH_MIN), imageStore.getImageList(Functions.getFlowerKey()));

            world.removeEntity(scheduler, this);

            world.addEntity(flower);
            flower.scheduleActions(scheduler, world, imageStore);
        }
        return false;
    }

}
