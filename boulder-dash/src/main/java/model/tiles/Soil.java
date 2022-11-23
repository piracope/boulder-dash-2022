package model.tiles;

/**
 * A Soil tile is a tile that can be consumed by the player.
 * <p>
 * If the player moves into a tile, it will destroy that soil tile, making it empty.
 */
public class Soil implements Tile{
    @Override
    public String toString() {
        return ".";
    }
}
