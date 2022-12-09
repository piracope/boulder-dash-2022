package view.javafx;

import controller.BoulderDash;
import javafx.application.Application;
import javafx.stage.Stage;

public class GraphicView extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        BoulderDash controller = new BoulderDash();
        stage = new MainWindow(controller, controller.getGame());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
