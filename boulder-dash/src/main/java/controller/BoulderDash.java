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


    public BoulderDash() {
        this.game = new Game();
        new ConsoleView(this, game);
    }

    public BoulderDash(Stage stage) {
        this.game = new Game();
        new MainWindow(this, game, stage);
    }

    public void start(int lvlNumber) {
        game.start(lvlNumber);
    }

    public void move(Direction dir) {
        game.move(dir);
    }

    public void abandon() {
        game.abandon();
    }

    public void undo() {
        game.undo();
    }

    public void redo() {
        game.redo();
    }

    public int getNbOfLevels() {
        return game.getNbOfLevels();
    }
}
