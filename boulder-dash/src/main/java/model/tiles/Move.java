package model.tiles;

import model.Position;

/**
 * A Move is the original tile at a certain position before a move is initiated.
 * <p>
 * A new Move should be generated everytime a tile is moved to be able to reverse it later.
 *
 * @param tile     a tile affected by a movement
 * @param position the position it was at
 */
public record Move(Tile tile, Position position) {
}
