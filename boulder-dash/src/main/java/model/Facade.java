package model;

import util.Observable;

/**
 * Facade of the Model.
 */
public interface Facade extends Observable {
    /**
     * Starts a game on a given level.
     *
     * @param level the level to start playing on
     */
    void start(int level);

    /**
     * Detects if the game is over.
     * <p>
     * A game is over when the player lost all his lives or if there are no more levels to play.
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

    int getMinimumDiamonds();

    int getDiamondCount();

    int getLvlNumber();

    int getNbOfLives();

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
     */
    void move(Direction dir);

    /**
     * Reverts the last move.
     */
    void undo();

    /**
     * Undoes the last move revert.
     */
    void redo();

    /**
     * Abandon the game.
     */
    void abandon();
}
