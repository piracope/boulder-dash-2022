package model.tiles;

import model.Direction;
import model.Move;

import java.util.Stack;

/**
 * A Wall is a stationary Tile.
 * <p>
 * We can't move in it, fall on it, fall through it etc.
 */
public class Wall implements Tile {
    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public boolean canFallThrough() {
        return false;
    }

    @Override
    public boolean canFallOn() {
        return false;
    }

    @Override
    public boolean canMoveIn(Direction dir) {
        return false;
    }

    @Override
    public Stack<Move> onMove(Direction dir) {
        return null;
    }

    @Override
    public String toString() {
        return "w";
    }
}
