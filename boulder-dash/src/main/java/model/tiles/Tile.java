package model.tiles;

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
}
