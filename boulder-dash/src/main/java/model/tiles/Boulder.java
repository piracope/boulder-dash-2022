package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

/**
 * A Boulder is a tile that can fall.
 * <p>
 * If the boulder falls on the player, the player dies and the game is over.
 */
public class Boulder extends FallingTile {
    public Boulder(Level level, Position position) {
        super(level, position);
    }

    @Override
    public boolean canMoveIn() {
        return false; // TODO : implement pushing boulders
    }

    @Override
    public String toString() {
        return "b";
    }

    @Override
    public void move(Direction dir) {
        ;
    }
}
