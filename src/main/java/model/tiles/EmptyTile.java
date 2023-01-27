package model.tiles;

import model.Direction;
import model.Move;

import java.util.Stack;

/**
 * An EmptyTile is a Tile with nothing in it.
 * <p>
 * As such, we can move on it as well as falling through and on it.
 * <p>
 * When a tile is displaced, an EmptyTile takes its place at its original position.
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

    @Override
    public boolean canFallThrough() {
        return true;
    }
}
