import controller.BoulderDash;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A class starting the game in JavaFX mode.
 */
public class MainGUI extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        new BoulderDash(stage);
    }
}
