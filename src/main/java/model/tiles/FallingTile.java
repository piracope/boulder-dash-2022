package model.tiles;

import model.Direction;
import model.Level;
import model.Move;
import model.Position;

import java.util.Stack;

/**
 * A FallingTile is a Tile that is subject to gravity.
 * <p>
 * As it is able to fall under certain conditions, it needs to be aware of its surroundings and as such
 * has access to the level it was placed on and knows its position on said level.
 * <p>
 * N.B. : Keeping this FallingTile's position up to date with the level is a responsibility of the level.
 */
public abstract class FallingTile implements Tile {
    protected final Level level;
    protected Position position;

    /**
     * Creates a new FallingTile.
     * <p>
     * The FallingTile, as it can fall, needs to be aware of its surroundings.
     * As such it requires to have access to the level it's on, as well as its position.
     *
     * @param level    the level this FallingTile is on
     * @param position the position of this FallingTile on the level
     */
    public FallingTile(Level level, Position position) {
        if (level == null || position == null) {
            throw new NullPointerException("Arguments can't be null.");
        }
        this.level = level;
        this.position = position;
    }

    @Override
    public boolean canFall() {
        return true;
    }

    @Override
    public boolean canFallOn() {
        return false;
    }

    @Override
    public boolean canFallThrough() {
        return false;
    }

    /**
     * Initiates a fall of this FallingTile.
     * <p>
     * The fall is vertical (downwards), but if it is sitting on another FallingTile and if there's empty
     * space around this pile of FallingTiles, this FallingTile will fall on the empty space (at its diagonal then).
     * If both diagonals are free, it will fall on its down-left diagonal by default.
     * <p>
     * This method keeps track of the original state of both affected tiles :
     * the original position of this tile and the original tile at the end position.
     * <p>
     * Any intermediary tile of the fall is not kept into account as they're either EmptyTiles (no particular behavior)
     * or the Player, but if the Player was crushed by a boulder, the level stops and any reversal of the move is
     * impossible.
     * <p>
     * If the fall didn't do anything, an empty stack will be returned.
     *
     * @return the original state of tiles affected by the fall (empty stack if none)
     */
    public Stack<Move> fall() {
        Stack<Move> ret = new Stack<>();
        Position oldPos = new Position(position); // we get the original position
        Tile affected = fallReal(null); // we get the tile we eventually fall on
        if (affected != null) { // if there was a fall
            ret.add(new Move(this, oldPos)); // this FallingTile was at the oldPos

            // the affected tile is at the position this tile is at now
            ret.add(new Move(affected, new Position(position)));
        }
        return ret;
    }

    /**
     * Recursively tries to make this tile fall in the three directions possible,
     * and stops when it can longer fall.
     *
     * @param toRet the tile that is (currently) the original tile at the place where the fall ends
     * @return toRet, but it changed after the recursive calls (if any)
     */
    private Tile fallReal(Tile toRet) {
        for (Direction dir : new Direction[]{Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT}) {
            Tile affectedTile = fallDown(dir);
            if (affectedTile != null) { // while we can fall
                return fallReal(affectedTile);
            }
        }
        return toRet;
    }

    /**
     * Makes this tile fall down once in a given direction, if it can.
     *
     * @param dir the direction of the fall.
     * @return the Tile that was at the place this tile will fall in, or null if the fall is impossible.
     */
    private Tile fallDown(Direction dir) {
        if (dir != Direction.DOWN && dir != Direction.DOWN_LEFT && dir != Direction.DOWN_RIGHT) {
            throw new IllegalArgumentException("Fall cannot be started in this direction");
        }
        Tile diag = level.getTile(position, dir);
        Tile side = level.getTile(position, dir.getComponents()[1]);
        Tile under = level.getTile(position, Direction.DOWN);
        if (under.canFallOn() || under.canFall() && side.canFallThrough() && diag.canFallOn()) {
            Tile affectedTile = level.getTile(position, dir);
            level.moveTile(position, dir);
            return affectedTile;
        }

        return null;
    }

    /**
     * Changes the position by one unit in a given direction.
     *
     * @param dir the direction to move this FallingTile to
     */
    public void updatePosition(Direction dir) {
        position.move(dir);
    }

    /**
     * Changes the position of this FallingTile to a given position.
     * <p>
     * Beware of its usage !! Please ensure that wherever this method is used, the position of this FallingTile
     * stays equivalent to its real position on the level!
     *
     * @param pos the new position of this FallingTile
     */
    public void updatePosition(Position pos) {
        position = pos;
    }
}
