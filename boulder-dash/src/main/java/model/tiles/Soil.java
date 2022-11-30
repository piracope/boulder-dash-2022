package model.tiles;

import model.Direction;

/**
 * A Soil tile is a tile that can be consumed by the player.
 * <p>
 * If the player moves into a tile, it will destroy that soil tile, making it empty.
 */
public class Soil extends ConcreteTile {
    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public boolean canMoveIn(Direction dir) {
        return true;
    }

    @Override
    public void onMove(Direction dir) {

    }

    @Override
    public String toString() {
        return ".";
    }
}
