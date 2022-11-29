package model.tiles;

import model.Direction;
import model.Level;

/**
 * A Diamond is a collectible tile that can fall.
 */
public class Diamond extends FallingTile {
    /**
     * Creates a new ConcreteTile with the map it's in.
     *
     * @param level the level where this tile is
     */
    public Diamond(Level level) {
        super(level);
    }

    @Override
    public String toString() {
        return "d";
    }

    @Override
    public boolean canFallIn() {
        return false;
    }

    @Override
    public boolean canMoveIn() {
        return true;
    }

    @Override
    public void move(Direction dir) {

    }

    @Override
    public void fall() {

    }
}
