package model;

import com.google.gson.Gson;
import model.tiles.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * A Level is a specific map of different tiles in which the player can play.
 * <p>
 * A level is made of a lot of different types of tiles. To win a level,
 * the player must get the minimum required (level-specific) amount of
 * diamonds. When that number is reached, an exit will be revealed.
 * The level is won when that exit is reached.
 */
public class Level {
    /* Positions */
    private final Tile[][] map;
    private Position playerPos;
    private Position exitPos;

    /* Diamonds */
    private final int minimumDiamonds;
    private int diamondCount = 0;

    /* Infos */
    private LevelState state;
    private final int lvlNumber;

    /* Utility */
    private void processMap(String mapStr) {
        mapStr = mapStr.toLowerCase();
        int line = 0;
        int col = 0;
        for (int i = 0; i < mapStr.length(); i++) {
            char c = mapStr.charAt(i);
            switch (c) {
                case 'w' -> {
                    map[line][col] = new Wall();
                    col++;
                }
                case '.' -> {
                    map[line][col] = new Soil();
                    col++;
                }
                case 'd' -> {
                    map[line][col] = new Diamond(this, new Position(col, line));
                    col++;
                }
                case 'x' -> {
                    if (playerPos != null) {
                        throw new IllegalStateException("Level has more than 1 spawn point.");
                    }
                    playerPos = new Position(col, line);
                    map[line][col] = new Player();

                    col++;
                }
                case 'r' -> {
                    map[line][col] = new Boulder(this, new Position(col, line));
                    col++;
                }
                case '\n' -> {
                    line++;
                    col = 0;
                }
                case 'p' -> {
                    if (exitPos != null) {
                        throw new IllegalStateException("Level has more than 1 exit point.");
                    }
                    map[line][col] = new Exit();
                    exitPos = new Position(col, line);
                    col++;
                }
                case ' ' -> {
                    map[line][col] = new EmptyTile();
                    col++;
                }
            }
        }
        if (playerPos == null) {
            throw new IllegalStateException("Level has no spawn point.");
        }
    }

    /**
     * Creates a given level.
     * <p>
     * This constructor reads all levels from the LEVELS_PATH file. It then takes the desired level and fill
     * in all the necessary values.
     * <p>
     * The level numbers are counted from 0.
     *
     * @param lvlNumber the desired level
     * @throws RuntimeException         if the LEVELS_PATH file isn't found
     * @throws IllegalArgumentException if there's no such level with this number.
     */
    public Level(int lvlNumber) {
        minimumDiamonds = JSONHandler.getInstance().getMinimumDiamonds(lvlNumber);
        map = new Tile[JSONHandler.getInstance().getHeight(lvlNumber)]
                [JSONHandler.getInstance().getLength(lvlNumber)];
        processMap(JSONHandler.getInstance().getMap(lvlNumber));
        this.state = LevelState.PLAYING;
        this.lvlNumber = lvlNumber;
        makeFall();
    }

    /**
     * Moves the player in a certain direction.
     *
     * @param dir the direction towards which the player will move.
     */
    public void move(Direction dir) {
        Tile destinationTile = getTile(playerPos, dir);
        if (!destinationTile.canMoveIn(dir)) {
            throw new IllegalArgumentException("Cannot move player in this direction");
        }
        destinationTile.onMove(dir);
        moveTile(playerPos, dir);
        if(!getTile(playerPos, Direction.UP).canFall()) {
            makeFall();
        }
    }

    public void updateState() {
        if (playerPos.equals(exitPos) && diamondCount >= minimumDiamonds) {
            state = LevelState.WON;
            return;
        }
        for (var line : map) {
            for (Tile t : line) {
                if (t instanceof Player) {
                    state = LevelState.PLAYING;
                    return;
                }
            }
        }

        state = LevelState.LOST;
    }

    public Tile getTile(Position pos, Direction dir) {
        return map[pos.getY() + dir.getDy()][pos.getX() + dir.getDx()];
    }

    public Tile getTile(Position pos) {
        return map[pos.getY()][pos.getX()];
    }

    public void moveTile(Position pos, Direction dir) {
        Tile t = getTile(pos);
        map[pos.getY() + dir.getDy()][pos.getX() + dir.getDx()] = t;
        map[pos.getY()][pos.getX()] = new EmptyTile();
        if (t.canFall()) {
            ((FallingTile) t).updatePosition(dir);
        }
        if (pos.equals(playerPos)) {
            playerPos.move(dir);
        }
    }

    public void collectDiamond() {
        diamondCount++;
        if (diamondCount == minimumDiamonds) {
            ((Exit) getTile(exitPos)).reveal();
        }
    }

    public int getMinimumDiamonds() {
        return minimumDiamonds;
    }

    public int getDiamondCount() {
        return diamondCount;
    }

    public void setDiamondCount(int diamondCount) {
        if(diamondCount > this.diamondCount) {
            throw new IllegalStateException("Suspicious setter usage.");
        }
        this.diamondCount = diamondCount;
    }

    public int getLvlNumber() {
        return lvlNumber;
    }

    public LevelState getState() {
        return state;
    }

    public void makeFall() {
        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x].canFall()) {
                    FallingTile f = (FallingTile) map[y][x];
                    f.fall();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var line : map) {
            for (Tile t : line) {
                sb.append(t.toString());
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
