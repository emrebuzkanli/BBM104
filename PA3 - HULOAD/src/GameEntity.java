import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an entity within the game, such as minerals, soil, or obstacles.
 */
public class GameEntity extends ImageView {
    private final String type;
    private final int value; // For mineral worth
    private final int weight; // For mineral weight

    /**
     * Constructs a new GameEntity with specified image and properties.
     * @param imageUrl URL of the image representing the entity
     * @param type the type of the entity (e.g., "Mineral", "Boulder")
     * @param value the point value or economic worth of the entity
     * @param weight the physical weight of the entity, affecting gameplay mechanics
     */
    public GameEntity(String imageUrl, String type, int value, int weight) {
        super(new Image(imageUrl));
        this.type = type;
        this.value = value;
        this.weight = weight;
    }

    /**
     * Returns the type of the entity.
     * @return the type of the entity
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the value of the entity.
     * @return the value of the entity
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the weight of the entity.
     * @return the weight of the entity
     */
    public int getWeight() {
        return weight;
    }
}