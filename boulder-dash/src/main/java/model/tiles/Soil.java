package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

/**
 * A Soil tile is a tile that can be consumed by the player.
 * <p>
 * If the player moves into a tile, it will destroy that soil tile, making it empty.
 */
public class Soil extends ConcreteTile {
    public Soil(Level level, Position position) {
        super(level, position);
    }

    @Override
    public boolean canFall() {
        return false;
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
        return ".";
    }
}
