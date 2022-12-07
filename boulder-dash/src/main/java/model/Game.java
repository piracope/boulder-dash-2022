package model;

import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class Game implements Facade {
    private Level level;
    private int nbOfLives = 3;
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void start(int level) {
        if (isGameOver()) {
            throw new IllegalStateException("Game is over.");
        }
        this.level = new Level(level);
        notifyObservers();
    }

    @Override
    public boolean isGameOver() {
        return nbOfLives <= 0;
    }

    @Override
    public LevelState getLevelState() {
        return level.getState();
    }

    @Override
    public int getMinimumDiamonds() {
        return level.getMinimumDiamonds();
    }

    @Override
    public int getDiamondCount() {
        return level.getDiamondCount();
    }

    @Override
    public int getLvlNumber() {
        return level.getLvlNumber();
    }

    @Override
    public int getNbOfLives() {
        return nbOfLives;
    }

    @Override
    public void move(Direction dir) {
        if (isGameOver()) {
            throw new IllegalStateException("Game is over.");
        }
        level.move(dir);
        level.updateState();
        if(getLevelState() == LevelState.LOST) {
            nbOfLives--;
        }
        notifyObservers();
    }

    @Override
    public void undo() {
        // haha ouais de fou
    }

    @Override
    public void redo() {
        // haha comment j'ai pas envie
    }

    @Override
    public void abandon() {
        nbOfLives = 0;
    }

    @Override
    public String toString() {
        return level.toString();
    }

    @Override
    public void registerObserver(Observer o) {
        if (o != null) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
