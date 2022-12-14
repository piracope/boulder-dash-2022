package model.tiles;

import model.Direction;
import model.Move;

import java.util.Stack;

/**
 * A Tile is a discrete element of the world.
 */
public interface Tile {
    /**
     * Checks if this tile can fall.
     *
     * @return true if this tile is a FallingTile
     */
    boolean canFall();

    /**
     * Checks if a FallingTile can fall on this tile.
     *
     * @return true if we can fall on this tile.
     */
    boolean canFallOn();

    /**
     * Checks if a FallingTile can slide on this tile.
     * <p>
     * By sliding, we mean that a FallingTile can move horizontally on it, like
     * during a diagonal fall or a push.
     *
     * @return true if we can slide on this tile
     */
    boolean canFallThrough();

    /**
     * Checks if the player can move in the direction of this tile.
     *
     * @param dir the direction of the move
     * @return true if the player can move on this tile
     */
    boolean canMoveIn(Direction dir);

    /**
     * Does an action whenever the player requests a move.
     * <p>
     * If the action of a Tile involves modifying other Tiles on a level,
     * it must return the original state of the affected tiles (itself + any other affected).
     * Otherwise, it will return null.
     *
     * @param dir the direction of the move
     * @return the original state of tiles affected by this method, or null if not applicable.
     */
    Stack<Move> onMove(Direction dir);
}
