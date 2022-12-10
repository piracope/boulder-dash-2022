package model.tiles;

import model.Direction;

import java.util.Stack;

/**
 * A Soil tile is a tile that can be consumed by the player.
 * <p>
 * If the player moves into a tile, it will destroy that soil tile, making it empty.
 */
public class Soil implements Tile {
    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public boolean canFallOn() {
        return false;
    }

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
        return ".";
    }
}
