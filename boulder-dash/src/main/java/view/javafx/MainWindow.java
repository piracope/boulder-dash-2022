package view.javafx;

import controller.BoulderDash;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

/**
 * A JavaFX controller that will manage all view components necessary for a graphical Boulder Dash experience.
 * <p>
 * Upon starting the game, the player is presented with a level selection screen. When the level is selected,
 * it starts playing it, displaying an InfoBox and a GameBoard.
 * <p>
 * Upon death, the player can press Enter to respawn until they lost all their lives, which would bring them back
 * to the level selection menu. Upon winning, pressing Enter loads the next level, unless there are no more levels
 * to play, which will bring back the player to the main menu.
 */
public class MainWindow implements Observer {
    private final Stage primaryStage;
    private final VBox root = new VBox();
    private final VBox window = new VBox();
    private final InfoBox info;
    private final GameBoard board;

    private final BoulderDash controller;
    private final Facade game;

    private final EventHandler<KeyEvent> moveHandle;
    private final EventHandler<KeyEvent> respawn;
    private final EventHandler<KeyEvent> goBackToMenu;
    private final EventHandler<KeyEvent> nextLevel;

    /**
     * Creates a new MainWindow with a given controller, model and stage.
     *
     * @param controller the controller to interact with
     * @param game       the model to observe
     * @param stage      the stage this window will be displayed on
     */
    public MainWindow(BoulderDash controller, Facade game, Stage stage) {
        if (controller == null || game == null || stage == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
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
                board.getPrefHeight() + 120); // adding 120 as an arbitrary number (i'm not able to get the messagebox's size)

        primaryStage.setTitle("Boulder Dash - Projet ATLG3 2022-2023 - 58089 MOUFIDI Ayoub");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        // components setup
        board.setFocusTraversable(true); // makes the game board aware of KeyEvents
        info.setAlignment(Pos.CENTER);
        board.setAlignment(Pos.CENTER);

        root.getChildren().add(window);

        // showing everything
        setupMenu();
        showLevelSelect();
        primaryStage.show();

    }

    private void setupMenu() {
        MenuBar mb = new MenuBar();

        Menu about = new Menu("About");

        MenuItem help = new Menu("Help");

        help.setOnAction(e -> {

            Alert helpAlert = new Alert(Alert.AlertType.NONE, """
                    Arrow Keys : move
                    CTRL-Z : undo
                    CTRL-Y : redo
                    Escape : abandon""",
                    ButtonType.CANCEL);
            helpAlert.setTitle("Help");
            helpAlert.show();
        });

        about.getItems().add(help);
        mb.getMenus().add(about);



        root.getChildren().add(0, mb);
    }

    private void showLevelSelect() {
        window.getChildren().clear();
        window.getChildren().add(buildLevelSelection());
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
        window.getChildren().clear();
        window.getChildren().addAll(info, board);
        setPlayingListeners(); //
        controller.start(lvlNumber);
    }

    private void setPlayingListeners() {
        // we remove the different game over filters
        window.removeEventFilter(KeyEvent.KEY_PRESSED, respawn);
        window.removeEventFilter(KeyEvent.KEY_PRESSED, goBackToMenu);
        window.removeEventFilter(KeyEvent.KEY_PRESSED, nextLevel);

        // and we just keep the move handler
        board.addEventHandler(KeyEvent.KEY_PRESSED, moveHandle);
    }

    @Override
    public void update() {
        if (this.game.isGameOver()) {
            board.removeEventHandler(KeyEvent.KEY_PRESSED, moveHandle);
            if (this.game.getLevelState() == LevelState.WON) {
                // if game is won -> next level
                window.addEventFilter(KeyEvent.KEY_PRESSED, nextLevel);
            } else {
                // if game over -> back to menu :(
                window.addEventFilter(KeyEvent.KEY_PRESSED, goBackToMenu);
            }
        } else if (this.game.getLevelState() == LevelState.CRUSHED) {
            // if crushed but NOT game over -> respawn
            board.removeEventHandler(KeyEvent.KEY_PRESSED, moveHandle);
            window.addEventFilter(KeyEvent.KEY_PRESSED, respawn);
        }
    }
}
