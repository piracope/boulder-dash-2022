package model.tiles;

/**
 * An EmptyTile is a Tile with nothing in it.
 */
public class EmptyTile implements Tile {
    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public boolean canMoveIn() {
        return true;
    }

    @Override
    public String toString() {
        return " ";
    }
}
