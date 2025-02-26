import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Main application class for the HU-Load game.
 * Initializes and displays the primary stage and scene containing the game map.
 */
public class Main extends Application {

    public void start(Stage primaryStage) {
        GameMap gameMap = new GameMap();  // Assuming GameMap instantiates and adds DrillMachine internally
        Scene scene = new Scene(gameMap);
        primaryStage.setTitle("HU-Load Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Setting the key pressed event handler
        scene.setOnKeyPressed(event -> {
            if (gameMap.getDrillMachine() != null) {
                gameMap.getDrillMachine().move(event.getCode());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}