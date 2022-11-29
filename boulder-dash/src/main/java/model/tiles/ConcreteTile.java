package model.tiles;

import model.Level;
import model.Position;

public abstract class ConcreteTile implements Tile {
    protected final Level level;
    protected final Position position; // FIXME : probably a bad idea

    /**
     * Creates a new ConcreteTile with the map it's in.
     *
     * @param level the level where this tile is
     */
    public ConcreteTile(Level level, Position position) {
        this.level = level;
        this.position = position;
    }

    @Override
    public boolean canFall() {
        return false;
    }
    @Override
    public boolean canFallOn() {
        return false;
    }
}
