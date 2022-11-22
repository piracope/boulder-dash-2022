package model;

/**
 * Facade of the Model.
 */
public interface Model {
    /**
     * Starts a game on a given level.
     *
     * @param level the level to start playing on
     */
    void start(int level);

    /**
     * Detects if the game is over.
     * <p>
     * A game is over when a) the player got killed by falling boulders,
     * b) the player reached the exit or c) the player abandoned.
     *
     * @return true if the game is over
     */
    boolean isGameOver();

    /**
     * Detects if a level is won or if it's lost.
     * <p>
     * A level is won if the player got enough diamonds to reveal the exit,
     * and reached said exit. A level is lost if the game is over without
     * winning it.
     *
     * @return true if the game is won
     */
    boolean isGameWon();

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
    void move();

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
