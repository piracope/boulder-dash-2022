package model.tiles;

import model.Direction;
import model.Level;
import model.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
    protected final Level level;
    protected Position position;

    public FallingTile(Level level, Position position) {
        this.level = level;
        this.position = position;
    }

    @Override
    public boolean canFall() {
        return true;
    }

    public Map<Tile, Position> fall() {
        Map<Tile, Position> ret = new HashMap<>(2);
        Position oldPos = new Position(position);
        Tile affected = fallReal(null);
        if(affected != null) {
            ret.put(this, oldPos);
            ret.put(affected, new Position(position));
        }
        return ret;
    }

    private Tile fallReal(Tile toRet) {
        for(Direction dir : new Direction[]{Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT}) {
            Tile affectedTile = fallDown(dir);
            if(affectedTile != null) {
                return fallReal(affectedTile);
            }
        }
        return toRet;
    }

    public void updatePosition(Direction dir) {
        position.move(dir);
    }

    public void updatePosition(Position pos) {
        position = pos;
    }

    private Tile fallDown(Direction dir) {
        Tile diag = level.getTile(position, dir);
        Tile side = level.getTile(position, dir.getComponents()[1]);
        Tile under = level.getTile(position, Direction.DOWN);
        if (under.canFallOn() || under.canFall() && side.canFallOn() && diag.canFallOn()) {
            Tile affectedTile = level.getTile(position, dir);
            level.moveTile(position, dir);
            return affectedTile;
        }

        return null;
    }
}
