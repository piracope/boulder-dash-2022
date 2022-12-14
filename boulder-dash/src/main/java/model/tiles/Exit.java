package model.tiles;

import model.Direction;
import model.Move;

import java.util.Stack;

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
    public boolean canFallThrough() {
        return false;
    }

    @Override
    public boolean canMoveIn(Direction dir) {
        return isRevealed;
    }

    @Override
    public Stack<Move> onMove(Direction dir) {
        return null;
    }

    /**
     * Reveals this Exit.
     */
    public void reveal() {
        isRevealed = true;
    }

    public void unReveal() {
        isRevealed = false;
    }

    @Override
    public String toString() {
        return "P";
    }
}
