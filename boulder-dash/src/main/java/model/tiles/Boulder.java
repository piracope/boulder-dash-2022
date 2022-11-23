package model.tiles;

/**
 * A Boulder is a tile that can fall.
 * <p>
 * If the boulder falls on the player, the player dies and the game is over.
 */
public class Boulder extends FallingTile {
    @Override
    public String toString() {
        return "b";
    }
}
