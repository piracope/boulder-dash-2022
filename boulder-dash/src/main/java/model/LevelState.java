package model;

/**
 * The different states of play a level can be in.
 */
public enum LevelState {
    /**
     * The level is neither won, nor lost. It's playing normally.
     */
    PLAYING,

    /**
     * The level is won.
     * <p>
     * The player collected enough diamonds, found and reached the level's exit.
     */
    WON,

    /**
     * The level is lost.
     * <p>
     * The player was crushed by a FallingTile.
     */
    CRUSHED,

    /**
     * The move that was just done is invalid.
     */
    INVALID_MOVE,
}
