package model;

import com.google.gson.Gson;

import java.io.*;
import java.util.Objects;

import model.tiles.*;

/**
 * A Level is a specific map of different tiles in which the player can play.
 * <p>
 * A level is made of a lot of different types of tiles. To win a level,
 * the player must get the minimum required (level-specific) amount of
 * diamonds. When that number is reached, an exit will be revealed.
 * The level is won when that exit is reached.
 */
public class Level {
    private static final String LEVELS_PATH = "/levels.json";
    private final Tile[][] map;
    private final int minimumDiamonds;
    private Position playerPos;
    private int diamondCount = 0;
    private boolean isWon = false;

    private static class LevelJSON {
        public String map;
        public int minimumDiamonds;
        public int length;
        public int height;

    }

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
                    map[line][col] = new Soil(this, new Position(col, line));
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
                    map[line][col] = new Player(this, playerPos);

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
                default -> {
                    map[line][col] = new EmptyTile(this, new Position(col, line));
                    col++;
                }
            }

            // TODO : update neighbours
        }

        if(playerPos == null) {
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
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(LEVELS_PATH)
                        )
                ))) {
            LevelJSON lv = gson.fromJson(br, LevelJSON[].class)[lvlNumber];
            minimumDiamonds = lv.minimumDiamonds;
            map = new Tile[lv.height][lv.length];
            processMap(lv.map.toLowerCase());
        } catch (IOException e) {
            throw new RuntimeException("Cannot find " + LEVELS_PATH);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Level does not exist.");
        }
    }

    /**
     * Moves the player in a certain direction.
     *
     * @param dir the direction towards which the player will move.
     */
    public void move(Direction dir) {
        Tile player = getTile(playerPos);
        Tile destinationTile = getTile(playerPos, dir);
        if(!destinationTile.canMoveIn()) {
            throw new IllegalArgumentException("Cannot move player in this direction");
        }

        destinationTile.move(dir);
        moveTile(player, playerPos, dir);
        playerPos.move(dir);
    }

    public Tile getTile(Position pos, Direction dir) {
        return map[pos.getY() + dir.getDy()][pos.getX() + dir.getDx()];
    }

    public Tile getTile(Position pos) {
        return map[pos.getY()][pos.getX()];
    }
    public void moveTile(Tile tile, Position pos, Direction dir) {
        map[pos.getY() + dir.getDy()][pos.getX() + dir.getDx()] = tile;
        map[pos.getY()][pos.getX()] = new EmptyTile(this, pos);
    }

    public void collectDiamond() {
        diamondCount++;
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

    public static void main(String[] args) {
        Level lvl = new Level(0);
        System.out.println(lvl);
        lvl.move(Direction.UP);
        System.out.println("Moved up");
        System.out.println(lvl);
        for (int i = 0; i < 6; i++) {
            lvl.move(Direction.RIGHT);
        }
        System.out.println("Moved 6 right");
        System.out.println(lvl);
        lvl.move(Direction.RIGHT);
        System.out.println("Caught diamond");
        System.out.println(lvl);
        System.out.println("Diamond count : " + lvl.diamondCount);
    }
}
