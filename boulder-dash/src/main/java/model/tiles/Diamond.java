package model.tiles;

/**
 * A Diamond is a collectible tile that can fall.
 */
public class Diamond extends FallingTile {
    public Diamond(Tile[][] tiles) {
        super(tiles);
    }

    @Override
    public String toString() {
        return "d";
    }

    @Override
    public boolean canMoveIn() {
        return true;
    }
}
