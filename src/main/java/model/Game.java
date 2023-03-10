package model;

import model.commands.MoveCommand;
import util.Command;
import util.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Game is an implementation of the Boulder Dash Façade.
 * <p>
 * In this implementation, game over status is defined by the player's lives. The initial number
 * of lives is defined in a static attribute.
 * <p>
 * If a level is started, the number of lives is either the current
 * number of lives we have, or the starting number, depending on various factors like is this the first level loaded,
 * are we starting this level again after dying, was there a game over, etc.
 */
public class Game implements Facade {
    private static final int STARTING_LIVES = 3;
    private final List<Observer> observers = new ArrayList<>();
    private final Stack<Command> history = new Stack<>();
    private final Stack<Command> redoHistory = new Stack<>();
    private Level level;
    private int nbOfLives = STARTING_LIVES;

    @Override
    public void start(int level) {
        /*
        if we start a level with no lives left -> restart => 3 lives
        if no level loaded yet -> start => 3 lives
        if level we're loading is different from the current level -> new level => 3 lives
         */
        if (nbOfLives == 0 || this.level == null || level != this.level.getLvlNumber()) {
            nbOfLives = STARTING_LIVES;
        }
        startLevel(level);
    }

    private void startLevel(int level) {
        history.clear();
        redoHistory.clear();
        this.level = new Level(level);
        notifyObservers();
    }

    @Override
    public boolean isGameOver() {
        return nbOfLives <= 0 || level.getState() == LevelState.WON;
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
    public int getRemainingDiamonds() {
        return level.getRemainingDiamonds();
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
    public Position getPlayerPos() {
        return level.getPlayerPos();
    }

    @Override
    public int getNbOfLevels() {
        return LevelJSONHandler.getInstance().getNbOfLevels();
    }

    @Override
    public void move(Direction dir) {
        if (isGameOver() || getLevelState() == LevelState.CRUSHED) {
            throw new IllegalStateException("You're not able to move");
        }
        Command move = new MoveCommand(level, dir);
        try {
            move.execute();
            history.add(move);
            redoHistory.clear();
        } catch (IllegalArgumentException ignored) {
        }
        if (getLevelState() == LevelState.CRUSHED) {
            nbOfLives--;
        }
        notifyObservers();

    }

    @Override
    public void undo() {
        if (isGameOver() || getLevelState() == LevelState.CRUSHED) {
            throw new IllegalStateException("You're not able to move");
        }
        Command toUndo = history.pop();
        toUndo.undo();
        redoHistory.add(toUndo);
        notifyObservers();
    }

    @Override
    public void redo() {
        if (isGameOver() || getLevelState() == LevelState.CRUSHED) {
            throw new IllegalStateException("You're not able to move");
        }
        Command toRedo = redoHistory.pop();
        toRedo.execute();
        history.add(toRedo);
        notifyObservers();
    }

    @Override
    public void abandon() {
        nbOfLives = 0;
        notifyObservers();
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
