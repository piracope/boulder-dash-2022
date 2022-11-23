package model.tiles;

/**
 * A FallingTile is a Tile that is subject to gravity.
 * <p>
 * If the tile directly under it is empty, it will fall off until its fall is interrupted by a non-empty tile.
 * <p>
 * If the tile it sits/fell on can also fall (a pile of tiles that can fall),
 * and if there are empty tiles at the lower diagonals of the tile, the pile will topple
 * (the top tiles will fall at the free diagonal then continue their fall normally.)
 */
public abstract class FallingTile implements Tile {
    protected final Tile[][] tiles;

    public FallingTile(Tile[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public boolean canFall() {
        return true;
    }
}
