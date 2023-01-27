package model.tiles;

import model.Direction;
import model.Move;

import java.util.Stack;

/**
 * The Exit is a stationary Tile.
 * <p>
 * There is only ONE Exit per level.
 * <p>
 * The player can move on it if and only if this Exit is revealed. This reveal status is controlled externally.
 * <p>
 * Nothing can fall on it or through it as it would block the Exit and make the game unplayable.
 */
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
     * <p>
     * A revealed Exit should let the player walk into it.
     */
    public void reveal() {
        isRevealed = true;
    }

    /**
     * Sets this Exit as not revealed.
     * <p>
     * An unrevealed Exit should not let the player walk into it.
     */
    public void unReveal() {
        isRevealed = false;
    }

    @Override
    public String toString() {
        return "P";
    }
}
