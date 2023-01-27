package model.tiles;

import model.Direction;
import model.Move;

import java.util.Stack;

/**
 * The Player tile is the tile where the player is currently at.
 * <p>
 * There is only one Player per level.
 * <p>
 * We cannot move through it (meaning that a FallingTile won't go through the player during a diagonal fall),
 * but we can fall on the player, which would mean the end of the game.
 * <p>
 * If we try to move in another player, that "one player per level" rule must have been broken.
 */
public class Player implements Tile {
    @Override
    public boolean canMoveIn(Direction dir) {
        throw new IllegalStateException("how can you bump into another player");
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public Stack<Move> onMove(Direction dir) {
        return null;
    }

    @Override
    public boolean canFall() {
        return false;
    }

    @Override
    public boolean canFallOn() {
        return true;
    }

    @Override
    public boolean canFallThrough() {
        return false;
    }
}