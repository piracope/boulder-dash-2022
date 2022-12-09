package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

import java.util.Stack;

/**
 * A Boulder is a tile that can fall.
 * <p>
 * If the boulder falls on the player, the player dies and the game is over.
 */
public class Boulder extends FallingTile {
    public Boulder(Level level, Position position) {
        super(level, position);
    }

    @Override
    public boolean canFallOn() {
        return false;
    }

    @Override
    public boolean canMoveIn(Direction dir) {
        return level.getTile(position, dir).canFallOn();
    }

    @Override
    public String toString() {
        return "r";
    }

    @Override
    public Stack<Move> onMove(Direction dir) {
        if (canMoveIn(dir)) {
            return level.moveTile(position, dir);
        } else {
            throw new IllegalArgumentException("Cannot move in this direction.");
        }
    }
}
