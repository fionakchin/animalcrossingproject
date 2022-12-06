import java.util.*;

import processing.core.PImage;
public class Flower implements PlantEntities {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    public final double actionPeriod;
    private final double animationPeriod;
    private int health;

    public Flower(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
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

}
