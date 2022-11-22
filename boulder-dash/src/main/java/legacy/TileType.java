package legacy;

public enum TileType {
    /**
     * An Empty tile is a tile with nothing in it.
     * <p>
     * A tile can be empty either because it was its original state or because
     * the tile at that position either fell off or was consumed.
     */
    EMPTY(false),

    /**
     * A Wall is a stationary tile. It doesn't move and nothing can pass through it.
     */
    WALL(false),

    /**
     * A Soil tile is a tile that can be consumed by the player.
     * <p>
     * If the player moves into a tile, it will destroy that soil tile, making it empty.
     */
    SOIL(false),

    /**
     * The Player tile is the tile where the player is currently at.
     */
    PLAYER(false),

    /**
     * A Boulder is a tile that can fall.
     * <p>
     * If the boulder falls on the player, the player dies and the game is over.
     */
    BOULDER(true),

    /**
     * A Diamond is a collectible tile that can fall.
     */
    DIAMOND(true);

    private final boolean canFall;

    TileType(boolean canFall) {
        this.canFall = canFall;
    }

    /**
     * Returns whether that tile type can fall.
     * <p>
     * A Tile that can fall means that if the tile directly under it is empty,
     * it will fall off until its fall is interrupted by a non-empty tile.
     * <p>
     * If the tile it sits/fell on can also fall (a pile of tiles that can fall),
     * and if there are empty tiles at the lower diagonals of the tile, the pile will topple
     * (the top tiles will fall at the free diagonal then continue their fall normally.)
     *
     * @return true if that type of tile can fall
     */
    public boolean canFall() {
        return canFall;
    }
}
