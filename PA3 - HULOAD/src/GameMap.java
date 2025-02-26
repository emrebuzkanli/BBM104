import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * GameMap class represents the main game area, handling game object initialization and layout.
 */
public class GameMap extends Pane {
    private DrillMachine drillMachine;
    public static final int TILE_SIZE = 50; // Tile size is 50 pixels
    public static final int WIDTH = 16;
    public static final int HEIGHT = 15;
    private final Image drilledSoilImage = new Image("file:src/assets/underground/empty_15.png"); // Path to your brown tile image
    private final Image bronziumImage = new Image("file:src/assets/underground/valuable_bronzium.png");
    private final Image silveriumImage = new Image("file:src/assets/underground/valuable_silverium.png");
    private final Image goldiumImage = new Image("file:src/assets/underground/valuable_goldium.png");
    private final Image lavaImage = new Image("file:src/assets/underground/lava_02.png");
    private final Image soilImage = new Image("file:src/assets/underground/soil_03.png");
    private final Image topImage = new Image("file:src/assets/underground/top_01.png");

    public GameMap() {
        setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        createBackground();
        fillTop();
        placeMineralsAndLava();
        fillEmptySpacesWithSoil();
        createSky();
        createBorders();
        setupDrillMachine();
        setupIndicators();
        startFuelDecay();
    }

    /**
     * Initializes and starts the fuel decay mechanism.
     */
    private void startFuelDecay() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (drillMachine != null) {
                drillMachine.consumeFuel(1);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    /**
     * Sets up the DrillMachine instance in the game map.
     */

    private void setupDrillMachine() {
        drillMachine = new DrillMachine((double) WIDTH / 2 * TILE_SIZE - 45, 65, this);
        this.getChildren().add(drillMachine);
    }
    /**
     * Returns the DrillMachine instance used in the game.
     * @return current instance of DrillMachine
     */
    public DrillMachine getDrillMachine() {
        return drillMachine;
    }
    /**
     * Checks if a specific tile on the map is occupied.
     * @param x horizontal tile index
     * @param y vertical tile index
     * @return true if the tile is occupied, false otherwise
     */

    private boolean isTileOccupied(int x, int y) {
        double xPos = x * TILE_SIZE;
        double yPos = y * TILE_SIZE;
        for (Node node : this.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imgView = (ImageView) node;
                if (imgView.getX() == xPos && imgView.getY() == yPos) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Fills unoccupied spaces with soil image.
     */

    private void fillEmptySpacesWithSoil() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 3; j < HEIGHT; j++) {
                // Check if a tile is already occupied
                if (!isTileOccupied(i, j)) {
                    addElement(i, j, soilImage);
                    addGameEntity(i, j, "file:src/assets/underground/soil_03.png", "Soil", 0, 0);
                }
            }
        }
    }
    /**
     * Fills the top layer of the game map.
     */
    private void fillTop() {
        for (int i = 0; i < WIDTH; i++) {
            addElement(i, 2, topImage);
            addGameEntity(i, 2, "file:src/assets/underground/top_01.png", "Soil", 0, 0);
        }
    }
    /**
     * Creates the initial background for the game map.
     */
    private void createBackground() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Rectangle ground = new Rectangle(TILE_SIZE, TILE_SIZE, Color.BROWN);
                ground.setX(i * TILE_SIZE);
                ground.setY(j * TILE_SIZE);
                this.getChildren().add(ground);
            }
        }
    }
    /**
     * Creates the sky visuals at the top of the game map.
     */

    private void createSky() {
        for (int i = 0; i < WIDTH; i++) {
            Rectangle sky = new Rectangle(TILE_SIZE, TILE_SIZE, Color.DARKBLUE);
            sky.setX(i * TILE_SIZE);
            sky.setY(0); // First row
            this.getChildren().add(sky);
            sky = new Rectangle(TILE_SIZE, TILE_SIZE, Color.DARKBLUE);
            sky.setX(i * TILE_SIZE);
            sky.setY(TILE_SIZE); // Second row
            this.getChildren().add(sky);
        }
    }
    /**
     * Creates borders around the game map using boulder images.
     */

    private void createBorders() {
        Image boulderImage = new Image("file:src/assets/underground/obstacle_02.png");
        for (int i = 0; i < WIDTH; i++) {
            int j = HEIGHT - 1;
            addElement(i, j, boulderImage);
            addGameEntity(i, j, "file:src/assets/underground/obstacle_02.png", "Boulder", 0, 0); // Bottom border
        }
        for (int j = 3; j < HEIGHT - 1; j++) {
            addElement(0, j, boulderImage);
            addGameEntity(0, j, "file:src/assets/underground/obstacle_02.png", "Boulder", 0, 0); // Left border
            int i = WIDTH - 1;
            addElement(i, j, boulderImage);
            addGameEntity(i, j, "file:src/assets/underground/obstacle_02.png", "Boulder", 0, 0); // Right border
        }
    }
    /**
     * Adds a game entity to a specific location on the game map.
     * @param x horizontal index of the tile
     * @param y vertical index of the tile
     * @param image URL of the image to use
     * @param type type of entity (e.g., "Mineral", "Soil")
     * @param value value or points associated with the entity
     * @param weight weight of the entity, if applicable
     */
    private void addGameEntity(int x, int y, String image, String type, int value, int weight) {
        GameEntity entity = new GameEntity(image, type, value, weight);
        entity.setX(x * TILE_SIZE);
        entity.setY(y * TILE_SIZE);
        this.getChildren().add(entity);
    }
    /**
     * Randomly places minerals and lava tiles throughout the game map.
     */
    private void placeMineralsAndLava() {
        Random random = new Random();

        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 3; j < HEIGHT - 1; j++) {
                double chance = random.nextDouble();
                if (chance < 0.1) {
                    addElement(i, j, lavaImage);
                    addGameEntity(i, j, "file:src/assets/underground/lava_02.png", "Lava", 0, 0);
                } else if (chance < 0.12) {
                    addElement(i, j, bronziumImage);
                    addGameEntity(i, j, "file:src/assets/underground/valuable_bronzium.png", "Mineral", 60, 10);
                } else if (chance < 0.20) {
                    addElement(i, j, silveriumImage);
                    addGameEntity(i, j, "file:src/assets/underground/valuable_silverium.png", "Mineral", 100, 10);
                } else if (chance < 0.25) {
                    addElement(i, j, goldiumImage);
                    addGameEntity(i, j, "file:src/assets/underground/valuable_goldium.png", "Mineral", 250, 20);
                }
            }
        }
    }
    /**
     * Sets up fuel, money, and haul indicators on the game map.
     */

    private void setupIndicators() {
        VBox indicators = new VBox(10);
        indicators.setLayoutX(5); // Slight padding from the left
        indicators.setLayoutY(5); // Slight padding from the top

        Label fuelLabel = new Label();
        fuelLabel.textProperty().bind(drillMachine.fuelProperty().asString("Fuel: %.0f"));
        Label moneyLabel = new Label();
        moneyLabel.textProperty().bind(drillMachine.moneyProperty().asString("Money: $%d"));
        Label haulLabel = new Label();
        haulLabel.textProperty().bind(drillMachine.haulProperty().asString("Haul: %d kg"));

        fuelLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        moneyLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        haulLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        indicators.getChildren().addAll(fuelLabel, moneyLabel, haulLabel);
        this.getChildren().add(indicators);
    }

    /**
     * Adds an image element to the game map.
     * @param x horizontal index where the element should be placed
     * @param y vertical index where the element should be placed
     * @param image the image to be placed at the specified location
     */
    private void addElement(int x, int y, Image image) {
        ImageView element = new ImageView(image);
        element.setX(x * TILE_SIZE);
        element.setY(y * TILE_SIZE);
        this.getChildren().add(element);
    }
    /**
     * Checks for collisions at a specified point on the game map.
     * @param x the x coordinate of the point to check
     * @param y the y coordinate of the point to check
     * @return the GameEntity at the specified point, if any
     */
    public GameEntity checkCollision(double x, double y) {
        for (Node node : this.getChildren()) {
            if (node instanceof GameEntity) {
                GameEntity entity = (GameEntity) node;
                // Ensure the collision detection is pixel-perfect
                if (entity.getBoundsInParent().contains(x, y)) { // assuming center point collision
                    return entity;
                }
            }
        }
        return null;
    }

    /**
     * Replaces the image at a specified location with a drilled soil image.
     * @param x the x coordinate of the location to update
     * @param y the y coordinate of the location to update
     */
    public void replaceWithDrilledImage(double x, double y) {
        for (Node node : this.getChildren()) {
            if (node instanceof ImageView && node.getBoundsInParent().contains(x, y)) {
                ImageView imageView = (ImageView) node;
                if ((imageView.getImage() == bronziumImage) || (imageView.getImage() == silveriumImage) || (imageView.getImage() == goldiumImage) || (imageView.getImage() == soilImage) || (imageView.getImage() == topImage)) { // ensure not to replace already drilled images
                    imageView.setImage(drilledSoilImage);
                    break;
                }
            }
        }
    }
    /**
     * Sets up the game over screen with a specified message and background color.
     * @param message the message to display on the game over screen
     * @param backgroundColor the background color for the game over screen
     */

    public void setupGameOverScreen(String message, Color backgroundColor) {
        // Create a new root pane for the game over screen
        StackPane gameOverRoot = new StackPane();
        gameOverRoot.setStyle("-fx-background-color: #" + backgroundColor.toString().substring(2) + ";"); // Remove the alpha component

        // Create and style the game over label
        Label gameOverLabel = new Label(message);
        gameOverLabel.setFont(new Font("Arial", 24));
        gameOverLabel.setTextFill(Color.WHITE);

        // Add the label to the root pane
        gameOverRoot.getChildren().add(gameOverLabel);

        // Create a new scene with the gameOverRoot
        Scene gameOverScene = new Scene(gameOverRoot, 800, 750);

        // Get the current stage and set the new scene
        Stage currentStage = (Stage) this.getScene().getWindow();
        currentStage.setScene(gameOverScene);
    }
    /**
     * Handles game over logic when the game ends due to lava.
     */
    public void handleGameOverByLava() {
        setupGameOverScreen("GAME OVER", Color.DARKRED);
    }
}