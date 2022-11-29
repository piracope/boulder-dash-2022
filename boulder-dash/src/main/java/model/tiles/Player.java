package model.tiles;

import model.Direction;
import model.Level;

/**
 * The Player tile is the tile where the player is currently at.
 */
public class Player extends ConcreteTile{

    /**
     * Creates a new ConcreteTile with the map it's in.
     *
     * @param level the level where this tile is
     */
    public Player(Level level) {
        super(level);
    }

    @Override
    public boolean canFallIn() {
        return true;
    }

    @Override
    public boolean canMoveIn() {
        throw new UnsupportedOperationException("how can you bump into another player");
    }

    @Override
    public void move(Direction dir) {
        Tile destinationTile = level.getNeighbour(this, dir);
        if(!destinationTile.canMoveIn()) {
            throw new IllegalArgumentException("Cannot move player in this direction");
        }

        destinationTile.move(dir);
        level.setNeighbour(this, dir, this);
    }

    @Override
    public String toString() {
        return "X";
    }
}