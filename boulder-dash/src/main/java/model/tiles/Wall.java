package model.tiles;

/**
 * A Wall is a stationary Tile. It doesn't move and nothing can pass through it.
 */
public class Wall implements Tile {
    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public boolean canMoveIn() {
        return false;
    }

    @Override
    public String toString() {
        return "w";
    }
}
