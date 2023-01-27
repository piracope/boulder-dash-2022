package model.tiles;

import model.Direction;
import model.Level;
import model.Move;
import model.Position;

import java.util.Stack;

/**
 * A Diamond is a collectible tile that can fall.
 * <p>
 * If the player moves on this tile, the diamond is collected and ceased to exist.
 * <p>
 * We cannot fall through or on this tile.
 */
public class Diamond extends FallingTile {
    /**
     * Creates a new FallingTile Diamond.
     *
     * @param level    the level it's on
     * @param position its position on the level
     */
    public Diamond(Level level, Position position) {
        super(level, position);
    }

    @Override
    public String toString() {
        return "d";
    }

    @Override
    public boolean canMoveIn(Direction dir) {
        return true;
    }

    @Override
    public Stack<Move> onMove(Direction dir) {
        level.collectDiamond();
        return null;
    }
}
