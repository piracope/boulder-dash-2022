import controller.BoulderDash;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        BoulderDash controller = new BoulderDash();
        stage = controller.createGraphicView();

        stage.show();
    }



    public static void main(String[] args) {
        //BoulderDash controller = new BoulderDash();
        //controller.createConsoleView();
        launch();
    }
}
