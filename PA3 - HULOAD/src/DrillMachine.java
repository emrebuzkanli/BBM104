import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.util.Duration;
/**
 * Represents the drilling machine controlled by the player in the game.
 */

public class DrillMachine extends ImageView {
    private static final int SPEED = 50;  // Speed of movement

    private final DoubleProperty fuel = new SimpleDoubleProperty(5000);  // Initial fuel
    private final IntegerProperty money = new SimpleIntegerProperty(0);  // Initial money
    private final IntegerProperty haul = new SimpleIntegerProperty(0);  // Initial haul
    private Timeline gravity;

    // Images for each action
    private final Image drillLeft = new Image("file:src/assets/drill/drill_01.png");
    private final Image drillRight = new Image("file:src/assets/drill/drill_55.png");
    private final Image drillDown = new Image("file:src/assets/drill/drill_46.png");
    private final Image flyUp = new Image("file:src/assets/drill/drill_30.png");
    private final GameMap gameMap;
    /**
     * Constructs a DrillMachine with initial position and game map context.
     * @param initialX the initial x coordinate of the drill machine
     * @param initialY the initial y coordinate of the drill machine
     * @param gameMap the game map where the drill machine operates
     */
    public DrillMachine(double initialX, double initialY, GameMap gameMap) {
        super(new Image("file:src/assets/drill/drill_11.png"));  // Default image
        this.setX(initialX);
        this.setY(initialY);
        this.setFitWidth(50);
        this.setFitHeight(50);
        this.gameMap = gameMap; // Set the game map reference
        initGravity();
    }

    public DoubleProperty fuelProperty() {
        return fuel;
    }

    public IntegerProperty moneyProperty() {
        return money;
    }

    public IntegerProperty haulProperty() {
        return haul;
    }
    /**
     * Moves the drill machine based on the player's key press.
     * @param key the KeyCode representing the player's input
     */

    public void move(KeyCode key) {
        double newX = this.getX();
        double newY = this.getY();

        switch (key) {
            case DOWN:
                newY += SPEED;
                setImage(drillDown);
                if (newY > gameMap.getHeight()-95) {
                    newY = gameMap.getHeight()-80;
                }
                break;
            case LEFT:
                newX -= SPEED-30;
                setImage(drillLeft);
                if (newX < 0) {
                    newX = 0;
                }
                break;
            case RIGHT:
                newX += SPEED-30;
                setImage(drillRight);
                if (newX > gameMap.getWidth()-35) {
                    newX = gameMap.getWidth()-30;
                }
                break;
            case UP:
                moveUp();  // Call the specialized method for moving up
                return;

        }

        // Adjust to the center of the image for more accurate collision detection
        double centerX = newX + this.getFitWidth() / 2;
        double centerY = newY + this.getFitHeight() / 2;
        consumeFuel(10);
        if (isValidMove(centerX, centerY)) {
            this.setX(newX);
            this.setY(newY);
            gameMap.replaceWithDrilledImage(newX, newY);

        }
    }
    /**
     * Moves the drill machine upward.
     */
    private void moveUp() {
        double newY = this.getY() - SPEED;
        double newX = this.getX();
        consumeFuel(10);
        if (!isObstacle(newX, newY)) {
            if (newY > 0) {
                setImage(flyUp);
                if (isValidMove(newX, newY)) {
                    this.setY(newY);

                    gameMap.replaceWithDrilledImage(newX, newY);
                }
            }
        }
    }
    /**
     * Checks for obstacles in the path of the drill machine.
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @return true if there is an obstacle, false otherwise
     */
    private boolean isObstacle(double x, double y) {
        return gameMap.checkCollision(x+30, y+10) != null;
    }

    /**
     * Validates if the move to the specified coordinates is valid.
     * @param x the x coordinate to move to
     * @param y the y coordinate to move to
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(double x, double y) {
        GameEntity entity = gameMap.checkCollision(x, y);
        if (entity != null) {
            switch (entity.getType()) {
                case "Boulder":
                    return false;
                case "Lava":
                    handleGameOver(true);
                    return false;
                case "Mineral":
                    collectResources(entity);
                    break; // Break instead of return true
                case "Soil":
                    break; // Treat soil as a collectible for now
            }
            gameMap.getChildren().remove(entity);
            gameMap.replaceWithDrilledImage(x, y); // Replace image after collecting
        }
        return true;
    }

    /**
     * Collects resources from a mineral entity.
     * @param entity the GameEntity from which to collect resources
     */
    private void collectResources(GameEntity entity) {
        if (entity.getType().equals("Mineral")) {
            money.set(money.get() + entity.getValue());
            haul.set(haul.get() + entity.getWeight());

        }
    }
    /**
     * Initializes gravity effects on the drill machine.
     */
    private void initGravity() {
        gravity = new Timeline(new KeyFrame(Duration.millis(1000), e -> applyGravity()));
        gravity.setCycleCount(Timeline.INDEFINITE);
        gravity.play();
    }

    /**
     * Applies gravity to the drill machine, making it fall if there is no obstacle below.
     */
    private void applyGravity() {
        double newX = this.getX();
        double newY = (this.getY() +(50))  ; // Move down by one tile
        if (!isObstacle(newX, newY)){
            // Check if moving down is valid
            if (isValidMove(newX, newY)) {
                this.setY(newY);
                gameMap.replaceWithDrilledImage(newX, newY);
            }
        }}

    /**
     * Handles game over scenarios, specifically when the game ends due to lava.
     * @param isLava true if the game over was caused by lava, false otherwise
     */

    public void handleGameOver(boolean isLava) {
        if (isLava) {
            gameMap.handleGameOverByLava();
            gravity.stop();
        } else {
            handleGameOverByFuelDepletion();
        }
    }
    /**
     * Consumes fuel based on the amount specified.
     * @param amount the amount of fuel to consume
     */

    public void consumeFuel(double amount) {
        double newFuel = Math.max(0, fuel.get() - amount);
        fuel.set(newFuel);
        if (newFuel <= 0) {
            handleGameOver(false);
            fuel.set(100000000);//Set fuel to 100000000 to avoid multiple game overs caused by fuel increase
        }
    }

    /**
     * Handles game over due to fuel depletion.
     */
    public void handleGameOverByFuelDepletion() {
        // Use the get() method on the money property to retrieve its current integer value
        String message = "GAME OVER\nCollected Money: $" + money.get();
        gameMap.setupGameOverScreen(message, Color.GREEN);

    }
}