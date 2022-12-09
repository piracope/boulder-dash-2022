package model.tiles;

import model.Direction;

import java.util.Stack;

/**
 * The Player tile is the tile where the player is currently at.
 */
public class Player implements Tile {
    @Override
    public boolean canMoveIn(Direction dir) {
        throw new IllegalStateException("how can you bump into another player");
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public Stack<Move> onMove(Direction dir) {

        return null;
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