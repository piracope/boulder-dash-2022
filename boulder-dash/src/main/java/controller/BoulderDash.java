package controller;

import model.Direction;
import model.Facade;
import model.Game;
import view.View;
import view.javafx.MainWindow;

public class BoulderDash {
    private final Facade game;
    private int lvlNumber = 1;
    private boolean isPlayOn = true;

    // temporary


    public Facade getGame() {
        return game;
    }

    public BoulderDash() {
        this.game = new Game();
        //View view = new ConsoleView(this, game);
        View view = new MainWindow(this, game);
        view.play();
    }

    public void start() {
        if (isPlayOn) {
            game.start(lvlNumber);
        }
    }

    public void move(Direction dir) {
        game.move(dir);
        handleState();
    }

    public void abandon() {
        game.abandon();
        handleState();
    }

    public void undo() {
        game.undo();
    }

    public void redo() {
        game.redo();
    }

    private void handleState() {
        if (game.isGameOver()) {
            isPlayOn = false;
        } else {
            switch (game.getLevelState()) {
                case WON -> {
                    lvlNumber++;
                    start();
                }
                case LOST -> start();
            }
        }
    }

    public boolean isPlayOn() {
        return isPlayOn;
    }
}
