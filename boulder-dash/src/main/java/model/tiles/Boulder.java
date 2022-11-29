package model.tiles;

import model.Direction;
import model.Level;

/**
 * A Boulder is a tile that can fall.
 * <p>
 * If the boulder falls on the player, the player dies and the game is over.
 */
public class Boulder extends FallingTile {
    /**
     * Creates a new ConcreteTile with the map it's in.
     *
     * @param level the level where this tile is
     */
    public Boulder(Level level) {
        super(level);
    }

    @Override
    public boolean canFallIn() {
        return false;
    }

    @Override
    public boolean canMoveIn() {
        return false;
    }

    @Override
    public void move(Direction dir) {

    }

    @Override
    public String toString() {
        return "b";
    }

    @Override
    public void fall() {
        if(level.getNeighbour(this, Direction.DOWN).canFallIn()) {
            level.setNeighbour(this, Direction.DOWN, this);
            level.setNeighbour(this, Direction.HERE, new EmptyTile(level));
        }
    }
}
