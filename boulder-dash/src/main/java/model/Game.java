package model;

import model.commands.MoveCommand;
import util.Command;
import util.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game implements Facade {
    private Level level;
    private int nbOfLives = 3;
    private final List<Observer> observers = new ArrayList<>();

    private final Stack<Command> history = new Stack<>();
    private final Stack<Command> redoHistory = new Stack<>();

    @Override
    public void start(int level) {
        history.clear();
        redoHistory.clear();
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
        Command move = new MoveCommand(level, dir);
        move.execute();
        history.add(move);
        redoHistory.clear();
        if (getLevelState() == LevelState.LOST) {
            nbOfLives--;
        }
        notifyObservers();
    }

    @Override
    public void undo() {
        Command toUndo = history.pop();
        toUndo.undo();
        redoHistory.add(toUndo);
        notifyObservers();
    }

    @Override
    public void redo() {
        redoHistory.pop().execute();
        notifyObservers();
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
