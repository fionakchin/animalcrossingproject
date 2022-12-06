import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public interface Entity {
    default PImage getCurrentImage() {
        return getImages().get(getImageIndex() % getImages().size());
    }

    List<PImage> getImages();
    int getImageIndex();
    String getId();
    Point getPosition();
    void setPosition(Point newPosition);

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    default String log(){
        return this.getId().isEmpty() ? null :
                String.format("%s %d %d %d", this.getId(), this.getPosition().x, this.getPosition().y, this.getImageIndex());
    }

}
