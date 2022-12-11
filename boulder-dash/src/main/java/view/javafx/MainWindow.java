package view.javafx;

import controller.BoulderDash;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Facade;
import view.View;

public class MainWindow extends Stage implements View {
    private final VBox root = new VBox();
    private final InfoBox info;
    private final BoulderDash controller;

    public MainWindow(BoulderDash controller, Facade game) {
        this.controller = controller;
        this.info = new InfoBox(game);

        setupStage();
    }

    private void setupStage() {
        this.setTitle("Boulder Dash - Projet ATLG3 2022-2023 - 58089 MOUFIDI Ayoub");


        root.getChildren().addAll(info, buildLevelSelection());

        Scene scene = new Scene(root, 640, 380);
        this.setScene(scene);
    }

    private FlowPane buildLevelSelection() {
        FlowPane chooseLevels = new FlowPane();
        for (int i = 0; i < controller.getNbOfLevels(); i++) {
            Button level = new Button("" + (i + 1));
            level.setOnAction(e -> play(Integer.parseInt(level.getText()) - 1));
            chooseLevels.getChildren().add(level);
        }

        return chooseLevels;
    }

    private void play(int lvlNumber) {
        controller.start(lvlNumber);
    }
}
