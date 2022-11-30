package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

/**
 * A FallingTile is a Tile that is subject to gravity.
 * <p>
 * If the tile directly under it is empty, it will fall off until its fall is interrupted by a non-empty tile.
 * <p>
 * If the tile it sits/fell on can also fall (a pile of tiles that can fall),
 * and if there are empty tiles at the lower diagonals of the tile, the pile will topple
 * (the top tiles will fall at the free diagonal then continue their fall normally.)
 */
public abstract class FallingTile extends ConcreteTile {
    protected final Level level;
    protected final Position position;

    public FallingTile(Level level, Position position) {
        this.level = level;
        this.position = position;
    }

    @Override
    public boolean canFall() {
        return true;
    }

    public void fall() {
        if (fallDown(Direction.DOWN) || fallDown(Direction.DOWN_LEFT) || fallDown(Direction.DOWN_RIGHT)) {
            fall();
        }
    }

    public void updatePosition(Direction dir) {
        position.move(dir);
    }

    private boolean fallDown(Direction dir) {
        Tile diag = level.getTile(position, dir);
        Tile side = level.getTile(position, dir.getComponents()[1]);
        Tile under = level.getTile(position, Direction.DOWN);
        if (under.canFallOn() || under.canFall() && side.canFallOn() && diag.canFallOn()) {
            level.moveTile(position, dir);
            return true;
        }

        return false;
    }
}
