public class Activity implements Action {
    //private final ActiveEntity entity; // change type to more specific interface, compiler wont accept the wrong kind
    private final ExecutingEntities entity;
    private final WorldModel world;
    private final ImageStore imageStore;

    public Activity(ExecutingEntities entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }
    public void executeAction(EventScheduler scheduler) {
        // single line (don't need to cast)
//        if(entity instanceof Sapling){
//            ((Sapling)this.entity).executeSaplingActivity
//        }
        entity.executeActivity(world, imageStore, scheduler);
    }
}
