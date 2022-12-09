package model.tiles;

import model.Direction;
import model.Position;

import java.util.Map;

public class Exit implements Tile {
    private boolean isRevealed = false;

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
        return isRevealed;
    }

    @Override
    public Map<Tile, Position> onMove(Direction dir) {
        return null;
    }

    public void reveal() {
        isRevealed = true;
    }

    @Override
    public String toString() {
        return "P";
    }
}
