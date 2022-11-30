package model.tiles;

import model.Level;

/**
 * An EmptyTile is a Tile with nothing in it.
 */
public class EmptyTile extends ConcreteTile {
    @Override
    public boolean canMoveIn() {
        return true;
    }

    @Override
    public void onMove() {
    }

    @Override
    public String toString() {
        return " ";
    }

    @Override
    public boolean canFallOn() {
        return true;
    }
}
