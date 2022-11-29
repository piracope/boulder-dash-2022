package model.tiles;

import model.Direction;
import model.Level;

/**
 * An EmptyTile is a Tile with nothing in it.
 */
public class EmptyTile extends ConcreteTile {
    public EmptyTile(Level level) {
        super(level);
        Tile[] neighbours = new Tile[3];
        neighbours[0] = level.getNeighbour(this, Direction.UP);
        neighbours[1] = level.getNeighbour(this, Direction.UP_LEFT);
        neighbours[2] = level.getNeighbour(this, Direction.UP_RIGHT);

        for(Tile falling : neighbours) {
            if(falling.canFall()) {
                ((FallingTile) falling).fall();
            }
        }
    }

    @Override
    public boolean canFallIn() {
        return true;
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
}
