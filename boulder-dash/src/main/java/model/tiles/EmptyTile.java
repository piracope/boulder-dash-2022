package model.tiles;

import model.Direction;

import java.util.Stack;

/**
 * An EmptyTile is a Tile with nothing in it.
 */
public class EmptyTile implements Tile {
    @Override
    public boolean canMoveIn(Direction dir) {
        return true;
    }

    @Override
    public Stack<Move> onMove(Direction dir) {
        return null;
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
