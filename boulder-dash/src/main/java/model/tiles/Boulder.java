package model.tiles;

import java.awt.*;

/**
 * A Boulder is a tile that can fall.
 * <p>
 * If the boulder falls on the player, the player dies and the game is over.
 */
public class Boulder extends FallingTile {
    public Boulder(Tile[][] tiles) {
        super(tiles);
    }

    @Override
    public boolean canMoveIn() {
        return false;
    }

    @Override
    public String toString() {
        return "b";
    }
}
