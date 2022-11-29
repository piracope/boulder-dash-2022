package model.tiles;

import model.Direction;

/**
 * A Tile is a discrete element of the world.
 */
public interface Tile {
    /**
     * Checks if this tile can fall.
     * @return true if this tile is a FallingTile
     */
    boolean canFall();

    /**
     * Checks if the player can move towards this shape.
     *
     * @return true if we can move in this shape
     */
    boolean canMoveIn();

    /**
     * Does an action whenever the player requests a move.
     */
    void move(Direction dir);
}
