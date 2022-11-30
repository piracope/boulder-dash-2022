package model.tiles;

import model.Level;

/**
 * The Player tile is the tile where the player is currently at.
 */
public class Player extends ConcreteTile{
    @Override
    public boolean canMoveIn() {
        throw new UnsupportedOperationException("how can you bump into another player");
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public void onMove() {

    }

    @Override
    public boolean canFallOn() {
        return true;
    }
}