import controller.BoulderDash;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {
    @Override
    public void start(Stage stage) {
        new BoulderDash(stage);
    }


    public static void main(String[] args) {
        launch();
    }
}
