package model.tiles;

/**
 * A Wall is a stationary Tile. It doesn't move and nothing can pass through it.
 */
public class Wall implements Tile {
    @Override
    public String toString() {
        return "w";
    }
}
