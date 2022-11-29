package model.tiles;

import model.Level;

public abstract class ConcreteTile implements Tile {
    protected final Level level;

    /**
     * Creates a new ConcreteTile with the map it's in.
     *
     * @param level the level where this tile is
     */
    public ConcreteTile(Level level) {
        this.level = level;
    }

    @Override
    public boolean canFall() {
        return false;
    }
}
