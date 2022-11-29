package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

/**
 * The Player tile is the tile where the player is currently at.
 */
public class Player extends ConcreteTile{
    public Player(Level level, Position position) {
        super(level, position);
    }

    @Override
    public boolean canMoveIn() {
        throw new UnsupportedOperationException("how can you bump into another player");
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public void move(Direction dir) {
        ;
    }

    @Override
    public boolean canFallOn() {
        return true;
    }
}