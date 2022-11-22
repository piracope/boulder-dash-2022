package model.tiles;

/**
 * A Tile is a discrete element of the world.
 */
public interface Tile {

    /**
     * Checks if this tile can fall.
     * @return true if this tile is a FallingTile
     */
    default boolean canFall() {
        return this instanceof FallingTile;
    }
}
