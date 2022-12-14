package view.javafx;

import controller.BoulderDash;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

        // other filters are too small to warrant a whole new class
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
        // stage setup
        Scene scene = new Scene(
                root,
                board.getPrefWidth(), // make the size of the window the size of the game's viewport
                board.getPrefHeight() + 100); // adding 100 as an arbitary number

        primaryStage.setTitle("Boulder Dash - Projet ATLG3 2022-2023 - 58089 MOUFIDI Ayoub");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        // components setup
        board.setFocusTraversable(true); // makes the game board aware of KeyEvents
        info.setAlignment(Pos.CENTER);
        board.setAlignment(Pos.CENTER);

        // showing everything
        showLevelSelect();
        primaryStage.show();

    }

    private void showLevelSelect() {
        root.getChildren().clear();
        root.getChildren().add(buildLevelSelection());
    }

    private BorderPane buildLevelSelection() {
        // Level Selection Screen creating
        BorderPane levelSelection = new BorderPane();
        Text levelSelectTxt = new Text("Choose your starting level");
        FlowPane chooseLevels = new FlowPane();

        // Setting up the layout
        levelSelection.setTop(levelSelectTxt);
        levelSelection.setCenter(chooseLevels);

        BorderPane.setAlignment(levelSelectTxt, Pos.CENTER);
        BorderPane.setMargin(chooseLevels, new Insets(10, 10, 10, 10));
        chooseLevels.setHgap(5);

        // Populating
        for (int i = 0; i < controller.getNbOfLevels(); i++) {
            Button level = new Button("" + (i + 1));
            level.setOnAction(e -> play(Integer.parseInt(level.getText()) - 1));
            chooseLevels.getChildren().add(level);
        }

        return levelSelection;
    }

    private void play(int lvlNumber) {
        root.getChildren().clear();
        root.getChildren().addAll(info, board);
        setPlayingListeners(); //
        controller.start(lvlNumber);
    }

    private void setPlayingListeners() {
        // we remove the different game over filters
        root.removeEventFilter(KeyEvent.KEY_PRESSED, respawn);
        root.removeEventFilter(KeyEvent.KEY_PRESSED, goBackToMenu);
        root.removeEventFilter(KeyEvent.KEY_PRESSED, nextLevel);

        // and we just keep the move handler
        board.addEventHandler(KeyEvent.KEY_PRESSED, moveHandle);
    }

    @Override
    public void update() {
        if (this.game.isGameOver()) {
            board.removeEventHandler(KeyEvent.KEY_PRESSED, moveHandle);
            if (this.game.getLevelState() == LevelState.WON) {
                // if game is won -> next level
                root.addEventFilter(KeyEvent.KEY_PRESSED, nextLevel);
            } else {
                // if game over -> back to menu :(
                root.addEventFilter(KeyEvent.KEY_PRESSED, goBackToMenu);
            }
        } else if (this.game.getLevelState() == LevelState.CRUSHED) {
            // if crushed but NOT game over -> respawn
            board.removeEventHandler(KeyEvent.KEY_PRESSED, moveHandle);
            root.addEventFilter(KeyEvent.KEY_PRESSED, respawn);
        }
    }
}
