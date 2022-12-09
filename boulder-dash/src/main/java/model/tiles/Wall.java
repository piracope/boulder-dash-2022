package model.tiles;

import model.Direction;
import model.Position;

import java.util.Map;

/**
 * A Wall is a stationary Tile. It doesn't move and nothing can pass through it.
 */
public class Wall implements Tile {
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
        return false;
    }

    @Override
    public Map<Tile, Position> onMove(Direction dir) {

        return null;
    }

    @Override
    public String toString() {
        return "w";
    }
}
