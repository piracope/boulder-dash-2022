package view.javafx;

import controller.BoulderDash;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Facade;
import model.LevelState;
import util.Observer;

public class MainWindow implements Observer {
    private final Stage primaryStage;
    private final VBox root = new VBox();
    private final InfoBox info;
    private final GameBoard board;

    private final BoulderDash controller;
    private final Facade game;

    private final EventHandler<KeyEvent> moveHandle;
    private final EventHandler<KeyEvent> respawn;
    private final EventHandler<KeyEvent> goBackToMenu;
    private final EventHandler<KeyEvent> nextLevel;

    // Event Listeners


    public MainWindow(BoulderDash controller, Facade game, Stage stage) {
        // model
        this.controller = controller;
        this.game = game;

        // view components
        this.primaryStage = stage;
        this.info = new InfoBox(game);
        this.board = new GameBoard(game);

        this.game.registerObserver(this);

        // handlers
        this.moveHandle = new MoveHandler(game);
        this.respawn = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                play(game.getLvlNumber());
            }

            keyEvent.consume();
        };

        this.goBackToMenu = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                showLevelSelect();
            }

            keyEvent.consume();
        };

        this.nextLevel = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    play(game.getLvlNumber() + 1);
                } catch (ArrayIndexOutOfBoundsException e) {
                    showLevelSelect();
                }

                keyEvent.consume();
            }
        };

        setupStage();
    }

    private void setupStage() {
        primaryStage.setTitle("Boulder Dash - Projet ATLG3 2022-2023 - 58089 MOUFIDI Ayoub");

        Scene scene = new Scene(root, board.getPrefWidth(), board.getPrefHeight() + 100);
        primaryStage.setScene(scene);
        board.setFocusTraversable(true);
        info.setAlignment(Pos.CENTER);
        board.setAlignment(Pos.CENTER);
        showLevelSelect();
        primaryStage.show();

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
        setListeners();
        controller.start(lvlNumber);
    }

    private void setListeners() {
        root.removeEventFilter(KeyEvent.KEY_PRESSED, respawn);
        root.removeEventFilter(KeyEvent.KEY_PRESSED, goBackToMenu);
        root.removeEventFilter(KeyEvent.KEY_PRESSED, nextLevel);
        board.addEventHandler(KeyEvent.KEY_PRESSED, moveHandle);
    }

    @Override
    public void update() {
        if (this.game.isGameOver()) {
            if (this.game.getLevelState() == LevelState.WON) {
                root.addEventFilter(KeyEvent.KEY_PRESSED, nextLevel);
            } else {
                root.addEventFilter(KeyEvent.KEY_PRESSED, goBackToMenu);
            }
        } else if (this.game.getLevelState() == LevelState.CRUSHED) {
            root.addEventFilter(KeyEvent.KEY_PRESSED, respawn);
        }
    }
}
