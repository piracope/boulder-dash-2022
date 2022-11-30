package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

/**
 * A Diamond is a collectible tile that can fall.
 */
public class Diamond extends FallingTile {
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
    public void onMove(Direction dir) {
        level.collectDiamond();
    }
}
