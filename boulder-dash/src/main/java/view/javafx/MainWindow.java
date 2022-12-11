package view.javafx;

import controller.BoulderDash;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Direction;
import model.Facade;
import view.View;

public class MainWindow extends Stage implements View {
    private final VBox root = new VBox();
    private InfoBox info;
    private BoulderDash controller;

    public MainWindow(BoulderDash controller, Facade game) {
        this.controller = controller;
        this.info = new InfoBox(game);

        setupStage();
    }

    private void setupStage() {
        this.setTitle("Boulder Dash - Projet ATLG3 2022-2023 - 58089 MOUFIDI Ayoub");
        BorderPane temp = new BorderPane();
        root.getChildren().addAll(info, temp);
        Text temporaire = new Text("Temporaire");
        temp.setCenter(temporaire);

        Scene scene = new Scene(root, 640, 380);
        this.setScene(scene);
    }

    @Override
    public void play() {
        controller.start(0);
        controller.move(Direction.DOWN);
    }
}
