import java.util.*;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * This class contains many functions written in a procedural style.
 * You will reduce the size of this class over the next several weeks
 * by refactoring this codebase to follow an OOP style.
 */
public final class Functions {
    public static final Random rand = new Random();

    private static final int COLOR_MASK = 0xffffff;
    private static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    private static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right", "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));

    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;

    private static final int PROPERTY_KEY = 0;
    private static final int PROPERTY_ID = 1;
    private static final int PROPERTY_COL = 2;
    private static final int PROPERTY_ROW = 3;
    private static final int ENTITY_NUM_PROPERTIES = 4;

    private static final String STUMP_KEY = "stump";
    private static final int STUMP_NUM_PROPERTIES = 0;

    private static final String SAPLING_KEY = "sapling";
    private static final int SAPLING_HEALTH = 0;
    private static final int SAPLING_NUM_PROPERTIES = 1;

    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_ANIMATION_PERIOD = 0;
    private static final int OBSTACLE_NUM_PROPERTIES = 1;

    private static final String PUNCHY_STUCK_KEY = "punchyStuck";
    private static final String PUNCHY_KEY = "punchy";
    private static final String PUNCHY_ANGRY_KEY = "punchyAngry";
    private static final double PUNCHY_ANGRY_ACTION_PERIOD = 0.18;
    private static final double PUNCHY_ANGRY_ANIMATION_PERIOD = 0.2;
    private static final double NEW_PUNCHY_ACTION_PERIOD = 0.4;
    private static final double NEW_PUNCHY_ANIMATION_PERIOD = 0.2;
    private static final int PUNCHY_ACTION_PERIOD = 0;
    private static final int PUNCHY_ANIMATION_PERIOD = 1;
    private static final int PUNCHY_LIMIT = 2;
    private static final int PUNCHY_NUM_PROPERTIES = 3;

    private static final String HOUSE_KEY = "house";
    private static final int HOUSE_NUM_PROPERTIES = 0;

    private static final String FAIRY_KEY = "fairy";
    private static final int FAIRY_ANIMATION_PERIOD = 0;
    private static final int FAIRY_ACTION_PERIOD = 1;
    private static final int FAIRY_NUM_PROPERTIES = 2;

    private static final String FLOWER_KEY = "flower";
    private static final int FLOWER_ANIMATION_PERIOD = 0;
    private static final int FLOWER_ACTION_PERIOD = 1;
    private static final int FLOWER_HEALTH = 2;
    private static final int FLOWER_NUM_PROPERTIES = 3;

    private static final String RESETTI_KEY = "resetti";
    private static final double RESETTI_ANIMATION_PERIOD = 0.18;
    private static final double RESETTI_ACTION_PERIOD = 0.84;


    public static int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }
    public static String getStumpKey()
    {
        return STUMP_KEY;
    }
    public static String getSaplingKey()
    {
        return SAPLING_KEY;
    }
    public static String getFlowerKey()
    {
        return FLOWER_KEY;
    }
    public static double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    public static int getDudeLimit() {
        return PUNCHY_LIMIT;
    }

    private static void parseSapling(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            Entity entity = createSapling(id, pt, imageStore.getImageList(SAPLING_KEY), health);
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SAPLING_KEY, SAPLING_NUM_PROPERTIES));
        }
    }

    private static void parsePunchy(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == PUNCHY_NUM_PROPERTIES) {
            Entity entity = createPunchyNotFull(id, pt, Double.parseDouble(properties[PUNCHY_ACTION_PERIOD]), Double.parseDouble(properties[PUNCHY_ANIMATION_PERIOD]), Integer.parseInt(properties[PUNCHY_LIMIT]), imageStore.getImageList(PUNCHY_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", PUNCHY_KEY, PUNCHY_NUM_PROPERTIES));
        }
    }
    private static void parseFairy(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            Entity entity = createFairy(id, pt, Double.parseDouble(properties[FAIRY_ACTION_PERIOD]), Double.parseDouble(properties[FAIRY_ANIMATION_PERIOD]), imageStore.getImageList(FAIRY_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FAIRY_KEY, FAIRY_NUM_PROPERTIES));
        }
    }

    private static void parseFlower(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FLOWER_NUM_PROPERTIES) {
            Entity entity = createFlower(id, pt, Double.parseDouble(properties[FLOWER_ACTION_PERIOD]), Double.parseDouble(properties[FLOWER_ANIMATION_PERIOD]), Integer.parseInt(properties[FLOWER_HEALTH]), imageStore.getImageList(FLOWER_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FLOWER_KEY, FLOWER_NUM_PROPERTIES));
        }
    }

    private static void parseObstacle(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Entity entity = createObstacle(id, pt, Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]), imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", OBSTACLE_KEY, OBSTACLE_NUM_PROPERTIES));
        }
    }

    private static void parseHouse(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            Entity entity = createHouse(id, pt, imageStore.getImageList(HOUSE_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }
    private static void parseStump(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            Entity entity = createStump(id, pt, imageStore.getImageList(STUMP_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", STUMP_KEY, STUMP_NUM_PROPERTIES));
        }
    }

    public static String getPunchyAngryKey() {
        return PUNCHY_ANGRY_KEY;
    }

    public static int getPunchyLimit() {
        return PUNCHY_LIMIT;
    }

    public static double getPunchyAngryAnimationPeriod() {
        return PUNCHY_ANGRY_ANIMATION_PERIOD;
    }

    public static double getPunchyAngryActionPeriod() {
        return PUNCHY_ANGRY_ACTION_PERIOD;
    }

    public static double getNewPunchyActionPeriod() {
        return NEW_PUNCHY_ACTION_PERIOD;
    }

    public static double getNewPunchyAnimationPeriod() {
        return NEW_PUNCHY_ANIMATION_PERIOD;
    }

    public static String getPunchyStuckKey() {
        return PUNCHY_STUCK_KEY;
    }

    /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */

    public static Action createAnimationAction(AnimatingEntities entity, int repeatCount) {
        return new Animation(entity, repeatCount);
    }

    public static Action createActivityAction(ExecutingEntities entity, WorldModel world, ImageStore imageStore) {
        return new Activity(entity, world, imageStore);
    }

    private static House createHouse(String id, Point position, List<PImage> images) {
        return new House(id, position, images);
    }

    private static Obstacle createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Obstacle(id, position, images, animationPeriod);
    }

    public static Punchy_Stuck createPunchyStuck(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Punchy_Stuck(id, position, images, actionPeriod, animationPeriod);
    }

    public static Flower createFlower(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Flower(id, position, images, actionPeriod, animationPeriod, health);
    }

    public static Stump createStump(String id, Point position, List<PImage> images) {
        return new Stump(id, position, images);
    }

    // health starts at 0 and builds up until ready to convert to Tree
    public static Sapling createSapling(String id, Point position, List<PImage> images, int health) {
        return new Sapling(id, position, images, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }

    private static Bob createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Bob(id, position, images, actionPeriod, animationPeriod);
    }

    public static Resetti createResetti(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Resetti(id, position, images, actionPeriod, animationPeriod);
    }

    public static Punchy_Not_Full createPunchyNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Punchy_Not_Full(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    // don't technically need resource count ... full
    public static Punchy_Full createPunchyFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Punchy_Full(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    public static Punchy_Angry createPunchyAngry(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Punchy_Angry(id, position, images, actionPeriod, animationPeriod);
    }
    public static void parseSaveFile(WorldModel world, Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;
        while(saveFile.hasNextLine()){
            lineCounter++;
            String line = saveFile.nextLine().strip();
            if(line.endsWith(":")){
                headerLine = lineCounter;
                lastHeader = line;
                switch (line){
                    case "Backgrounds:" -> world.setBackground(new Background[world.getNumRows()][world.getNumCols()]);
                    case "Entities:" -> {
                        world.setOccupancy(new Entity[world.getNumRows()][world.getNumCols()]);
                        world.setEntities(new HashSet<>());
                    }
                }
            }else{
                switch (lastHeader){
                    case "Rows:" -> world.setNumRows(Integer.parseInt(line));
                    case "Cols:" -> world.setNumCols(Integer.parseInt(line));
                    case "Backgrounds:" -> Functions.parseBackgroundRow(world, line, lineCounter-headerLine-1, imageStore);
                    case "Entities:" -> Functions.parseEntity(world, line, imageStore);
                }
            }
        }
    }
    private static void parseBackgroundRow(WorldModel world, String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if(row < world.getNumRows()){
            int rows = Math.min(cells.length, world.getNumCols());
            for (int col = 0; col < rows; col++){
                world.getBackground()[row][col] = new Background(cells[col], imageStore.getImageList(cells[col]));
            }
        }
    }

    private static void parseEntity(WorldModel world, String line, ImageStore imageStore) {
        String[] properties = line.split(" ", Functions.ENTITY_NUM_PROPERTIES + 1);
        if (properties.length >= Functions.ENTITY_NUM_PROPERTIES) {
            String key = properties[PROPERTY_KEY];
            String id = properties[Functions.PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[Functions.PROPERTY_COL]), Integer.parseInt(properties[Functions.PROPERTY_ROW]));

            properties = properties.length == Functions.ENTITY_NUM_PROPERTIES ?
                    new String[0] : properties[Functions.ENTITY_NUM_PROPERTIES].split(" ");

            switch (key) {
                case Functions.OBSTACLE_KEY -> Functions.parseObstacle(world, properties, pt, id, imageStore);
                case Functions.PUNCHY_KEY -> Functions.parsePunchy(world, properties, pt, id, imageStore);
                case Functions.FAIRY_KEY -> Functions.parseFairy(world, properties, pt, id, imageStore);
                case Functions.HOUSE_KEY -> Functions.parseHouse(world, properties, pt, id, imageStore);
                case Functions.FLOWER_KEY -> Functions.parseFlower(world, properties, pt, id, imageStore);
                case Functions.SAPLING_KEY -> Functions.parseSapling(world, properties, pt, id, imageStore);
                case Functions.STUMP_KEY -> Functions.parseStump(world, properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }
        }else{
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }


    public static void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    public static String getPunchyKey() {
        return PUNCHY_KEY;
    }

    public static double getResettiActionPeriod() {
        return RESETTI_ACTION_PERIOD;
    }

    public static String getResettiKey() {
        return RESETTI_KEY;
    }

    public static double getResettiAnimationPeriod() {
        return RESETTI_ANIMATION_PERIOD;
    }

    private static List<PImage> getImages(Map<String, List<PImage>> images, String key) {
        return images.computeIfAbsent(key, k -> new LinkedList<>());
    }

    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */
    private static void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }
}