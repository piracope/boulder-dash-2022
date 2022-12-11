package model;

import util.Observable;

/**
 * An Observable Facade of the model.
 * <p>
 * Every other non-model class should only interact with this class.
 * <p>
 * Observers should be notified after each move and each time the level's state changes.
 */
public interface Facade extends Observable {
    /**
     * Starts a game on a given level.
     * <p>
     * This method notifies observers.
     *
     * @param level the level to start playing on
     */
    void start(int level);

    /**
     * Detects if the game is over.
     * <p>
     * A game is over when the player lost all his lives or if he won the level.
     *
     * @return true if the game is over
     */
    boolean isGameOver();

    /**
     * Returns the state of play for the currently playing level.
     *
     * @return the level's state
     */
    LevelState getLevelState();

    /**
     * Getter for the minimum number of diamonds required to beat the currently playing level.
     *
     * @return minimumDiamonds
     */
    int getMinimumDiamonds();

    /**
     * Getter for the current number of diamonds collected.
     *
     * @return diamondCount
     */
    int getDiamondCount();

    /**
     * Getter for the number of the currently playing level.
     *
     * @return levelNumber
     */
    int getLvlNumber();

    /**
     * Getter for the number of lives remaining.
     *
     * @return nbOfLives
     */
    int getNbOfLives();

    /**
     * Getter for the number of playable levels.
     *
     * @return the number of playable levels
     */
    int getNbOfLevels();

    /**
     * Make a move.
     * <p>
     * The player can move in four directions : up, down, left and right.
     * It can move if the targeted tile is soil, which will destroy it,
     * diamond, which will collect it, an empty tile or a boulder.
     * <p>
     * If the targeted tile is a boulder, the player will push the boulder in
     * the direction of their move IF there's only one boulder. You cannot
     * move two boulders at once.
     * <p>
     * This method notifies observers.
     */
    void move(Direction dir);

    /**
     * Reverts the last move.
     * <p>
     * This method notifies observers.
     */
    void undo();

    /**
     * Undoes the last move revert.
     * <p>
     * This method notifies observers.
     */
    void redo();

    /**
     * Abandon the game.
     */
    void abandon();
}
