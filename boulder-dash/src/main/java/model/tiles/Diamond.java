package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

import java.util.Map;

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
    public boolean canFallOn() {
        return false;
    }

    @Override
    public boolean canMoveIn(Direction dir) {
        return true;
    }

    @Override
    public Map<Tile, Position> onMove(Direction dir) {
        level.collectDiamond();
        return null;
    }
}
