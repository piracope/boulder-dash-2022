package view.javafx;

import controller.BoulderDash;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Facade;
import model.LevelState;
import view.View;

public class MainWindow extends Stage implements View {
    private final VBox root = new VBox();
    private final InfoBox info;
    private final GameBoard board;

    private final BoulderDash controller;
    private final Facade game;

    // Event Listeners


    public MainWindow(BoulderDash controller, Facade game) {
        this.controller = controller;
        this.game = game;
        this.info = new InfoBox(game);
        this.board = new GameBoard(game);

        this.game.registerObserver(this);

        setupStage();
    }

    private void setupStage() {
        this.setTitle("Boulder Dash - Projet ATLG3 2022-2023 - 58089 MOUFIDI Ayoub");

        Scene scene = new Scene(root, 640, 380);
        this.setScene(scene);

        showLevelSelect();
    }

    private void showLevelSelect() {
        root.getChildren().clear();
        root.getChildren().addAll(info, buildLevelSelection());
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
        root.getChildren().set(1, board);
        controller.start(lvlNumber);
        setListeners();
    }

    private void setListeners() {
        MoveHandler move = new MoveHandler(game);
        this.setEventHandler(KeyEvent.KEY_PRESSED, move);
    }

    @Override
    public void update() {
        if(this.game.isGameOver()) {
            this.setEventHandler(KeyEvent.KEY_PRESSED, e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    showLevelSelect();
                }
            });
        } else if(this.game.getLevelState() == LevelState.CRUSHED) {
            this.setEventHandler(KeyEvent.KEY_PRESSED, e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    play(this.game.getLvlNumber()); // FIXME : huge spaghetti code
                }
            });
        }
    }
}
