package model.tiles;

/**
 * The Player tile is the tile where the player is currently at.
 */
public class Player implements Tile{
    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public boolean canMoveIn() {
        throw new UnsupportedOperationException("bruh");
    }

    @Override
    public String toString() {
        return "X";
    }
}