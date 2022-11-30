package model.tiles;

import model.Direction;

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
    public void onMove(Direction dir) {
    }

    public void reveal() {
        isRevealed = true;
    }

    @Override
    public String toString() {
        return "P";
    }
}
