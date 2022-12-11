package controller;

import model.Direction;
import model.Facade;
import model.Game;
import view.View;
import view.console.ConsoleView;

/**
 * The main controller of the game.
 * <p>
 * It creates the model and the view, then starts the game at a given level number.
 */
public class BoulderDash {
    private final Facade game;
    private View view;

    // temporary
    public Facade getGame() {
        return game;
    }

    public BoulderDash() {
        this.game = new Game();
    }

    public void createConsoleView() {
        this.view = new ConsoleView(this, game);
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
