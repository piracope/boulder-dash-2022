package controller;

import model.Direction;
import model.Facade;
import model.Game;

public class BoulderDash {
    private final Facade game;
    private int lvlNumber = 0;

    public BoulderDash() {
        this.game = new Game();
    }

    public void start() {
        game.start(lvlNumber);
    }

    public void move(Direction dir) {
        game.move(dir);
        handleState();
    }

    public void abandon() {
        game.abandon();
    }
    public void handleState() {
        switch (game.getLevelState()) {
            case WON -> {
                lvlNumber++;
                start();
            }
            case LOST -> {
                if (!game.isGameOver()) {
                    start();
                }
            }
        }
    }

    public Facade getFacade() {
        return game;
    }
}
