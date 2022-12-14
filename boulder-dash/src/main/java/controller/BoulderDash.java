package controller;

import javafx.stage.Stage;
import model.Direction;
import model.Facade;
import model.Game;
import view.console.ConsoleView;
import view.javafx.MainWindow;

/**
 * The main controller of the game.
 * <p>
 * It creates the model and the view, then starts the game at a given level number.
 */
public class BoulderDash {
    private final Facade game;


    /**
     * Creates a new controller which creates the model and a console view.
     */
    public BoulderDash() {
        this.game = new Game();
        new ConsoleView(this, game);
    }

    /**
     * Creates a new controller which creates the model and a JavaFX view, with a given Stage.
     *
     * @param stage the stage that the view will be on
     */
    public BoulderDash(Stage stage) {
        this.game = new Game();
        new MainWindow(this, game, stage);
    }

    /**
     * Starts a given level.
     *
     * @param lvlNumber the number (from 0) of the level to start.
     */
    public void start(int lvlNumber) {
        game.start(lvlNumber);
    }

    /**
     * Initiates a move in a certain direction.
     *
     * @param dir the direction towards which the player will move
     */
    public void move(Direction dir) {
        game.move(dir);
    }

    /**
     * Abandons the currently playing level.
     */
    public void abandon() {
        game.abandon();
    }

    /**
     * Undoes the last move.
     */
    public void undo() {
        game.undo();
    }

    /**
     * Redoes the last undone move.
     */
    public void redo() {
        game.redo();
    }

    /**
     * Returns the total number of playable levels.
     *
     * @return the total number of playable levels
     */
    public int getNbOfLevels() {
        return game.getNbOfLevels();
    }
}
