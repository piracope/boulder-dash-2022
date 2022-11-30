package model.tiles;

import model.Level;

public abstract class ConcreteTile implements Tile {
    @Override
    public boolean canFall() {
        return false;
    }
    @Override
    public boolean canFallOn() {
        return false;
    }
}
