package model;

import model.tiles.*;

import java.util.Stack;

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
        minimumDiamonds = LevelJSONHandler.getInstance().getMinimumDiamonds(lvlNumber);
        map = new Tile[LevelJSONHandler.getInstance().getHeight(lvlNumber)]
                [LevelJSONHandler.getInstance().getLength(lvlNumber)];
        processMap(LevelJSONHandler.getInstance().getMap(lvlNumber));
        this.state = LevelState.PLAYING;
        this.lvlNumber = lvlNumber;
        makeFall();
    }

    /**
     * Moves the player in a certain direction.
     *
     * @param dir the direction towards which the player will move.
     */
    public Stack<Move> move(Direction dir) {
        Stack<Move> ret = new Stack<>(); // for move history
        Tile destinationTile = getTile(playerPos, dir);
        if (!destinationTile.canMoveIn(dir)) { // check if the player can move in that tile
            this.state = LevelState.INVALID_MOVE;
            return null;
        }
        // possiblePush : a pair of Moves, if the destination tile is a boulder that was pushed
        var possiblePush = destinationTile.onMove(dir);
        if (possiblePush != null) {
            ret.addAll(possiblePush); // first, register the pushing of the boulder
        }
        ret.addAll(moveTile(playerPos, dir)); // then register the player's move
        ret.addAll(makeFall()); // then at last, register the tiles that fell
        return ret;
    }

    public void updateState() {
        // The player cannot be on the exit if it hasn't been revealed.
        if (playerPos.equals(exitPos) && diamondCount >= minimumDiamonds) {
            state = LevelState.WON;
            return;
        }
        for (var line : map) {
            for (Tile t : line) {
                if (t instanceof Player) { // FIXME : instanceof ok ?
                    state = LevelState.PLAYING;
                    return;
                }
            }
        }

        // the game is lost when the player disappears (he's under a boulder D: )
        state = LevelState.CRUSHED;
    }

    public Tile getTile(Position pos, Direction dir) {
        return map[pos.getY() + dir.getDy()][pos.getX() + dir.getDx()];
    }

    public Tile getTile(Position pos) {
        return map[pos.getY()][pos.getX()];
    }

    // FIXME : encapsulation :))))))
    public void setTile(Tile toSet, Position pos) {
        map[pos.getY()][pos.getX()] = toSet;
    }

    public Stack<Move> moveTile(Position pos, Direction dir) {
        Tile t = getTile(pos);
        // register the pre-move state of the affected tiles
        Stack<Move> ret = new Stack<>();
        ret.add(new Move(t, new Position(pos))); // the tile that will move
        // the tile that will disappear
        ret.add(new Move(map[pos.getY() + dir.getDy()][pos.getX() + dir.getDx()], pos.addDirection(dir)));

        map[pos.getY() + dir.getDy()][pos.getX() + dir.getDx()] = t;
        map[pos.getY()][pos.getX()] = new EmptyTile(); // a tile we leave is always an empty tile
        if (t.canFall()) {
            ((FallingTile) t).updatePosition(dir); // FIXME : should it be level that handles the position ?
        }
        if (pos.equals(playerPos)) {
            playerPos.move(dir);
        }

        return ret;
    }

    public Stack<Move> makeFall() {
        Stack<Move> ret = new Stack<>();
        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                Tile t = map[y][x];
                // condition to let everything fall except something JUST above the player
                // (if he's in that situation, it means he just made a move under a boulder)
                if (t.canFall() && getTile(playerPos, Direction.UP) != t) {
                    ret.addAll(((FallingTile) t).fall()); // add all moves made by that fall to the history stack
                }
            }
        }

        return ret;
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
        // FIXME : may be a useless setter
        if (diamondCount > this.diamondCount) {
            throw new IllegalStateException("Suspicious setter usage.");
        }
        this.diamondCount = diamondCount;
    }

    // FIXME : another dangerous setter
    public void changePlayerPos(Direction dir) {
        playerPos.move(dir);
    }

    public int getLvlNumber() {
        return lvlNumber;
    }

    public LevelState getState() {
        return state;
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
