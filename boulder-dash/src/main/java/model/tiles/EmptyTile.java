package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

/**
 * An EmptyTile is a Tile with nothing in it.
 */
public class EmptyTile extends ConcreteTile {
    public EmptyTile(Level level, Position position) {
        super(level, position);
    }

    @Override
    public boolean canMoveIn() {
        return true;
    }

    @Override
    public void move(Direction dir) {
        ;
    }

    @Override
    public String toString() {
        return " ";
    }

    @Override
    public boolean canFallOn() {
        return true;
    }
}
