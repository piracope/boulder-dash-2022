package model.tiles;

import model.Direction;
import model.Level;
import model.Move;
import model.Position;

import java.util.Stack;

/**
 * A Boulder is a tile that can fall.
 * <p>
 * If the boulder falls on the player, the player dies and the game is over.
 * <p>
 * A Boulder can be pushed if we can fall through (slide on) the neighboring tile.
 * Unless a push is possible, we can't move in this tile, nor can we fall through or on this tile.
 */
public class Boulder extends FallingTile {
    /**
     * Creates a new FallingTile Boulder.
     *
     * @param level    the level it's on
     * @param position its position on the level
     */
    public Boulder(Level level, Position position) {
        super(level, position);
    }

    @Override
    public boolean canMoveIn(Direction dir) {
        return level.getTile(position, dir).canFallThrough();
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
