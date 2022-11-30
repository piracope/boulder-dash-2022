package model.tiles;

import model.Direction;

/**
 * An EmptyTile is a Tile with nothing in it.
 */
public class EmptyTile implements Tile {
    @Override
    public boolean canMoveIn(Direction dir) {
        return true;
    }

    @Override
    public void onMove(Direction dir) {
    }

    @Override
    public String toString() {
        return " ";
    }

    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public boolean canFallOn() {
        return true;
    }
}
