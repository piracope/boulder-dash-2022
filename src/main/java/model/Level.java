package model;

import model.tiles.*;

import java.util.List;
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
    private final Tile[][] map;
    private final int minimumDiamonds;
    private final int lvlNumber;
    private Position playerPos;
    private Position exitPos;
    private int diamondCount = 0;
    private int totalDiamonds = 0;
    private LevelState state;

    /**
     * Creates a given level.
     * <p>
     * This constructor gets all information relative to the level from the LevelJSONHandler class.
     * <p>
     * The state is set to PLAYING and a fall is initiated at the level's start
     *
     * @param lvlNumber the desired level, starting form 0.
     * @throws RuntimeException         if the file holding the levels isn't found
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
     * Processes the string representation of the map and converts it ta a Tile[][]
     * <p>
     * N.B. : this method, and this whole class, assumes that the info received from LevelJSONHandler are CORRECT.
     *
     * @param mapStr the string representation of the level's map
     */
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
                    totalDiamonds++;
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

        if (exitPos == null) {
            throw new IllegalStateException("Level has no exit point.");
        }
    }

    /**
     * Initiates a move of the player towards a given direction.
     * <p>
     * The direction of the move has to be a straight direction.
     *
     * @param dir the direction towards which the player will move
     * @return the original positions of all tiles affected by this move
     */
    public Stack<Move> move(Direction dir) {
        if (!List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT).contains(dir)) {
            throw new IllegalArgumentException("Illegal direction of movement");
        }
        Stack<Move> ret = new Stack<>(); // for move history
        Tile destinationTile = getTile(playerPos, dir);
        if (!destinationTile.canMoveIn(dir)) { // check if the player can move in that tile
            this.state = LevelState.INVALID_MOVE;
            return null;
        }
        this.state = LevelState.PLAYING;
        // possiblePush : a pair of Moves, if the destination tile is a boulder that was pushed
        var possiblePush = destinationTile.onMove(dir);
        if (possiblePush != null) {
            ret.addAll(possiblePush); // first, register the pushing of the boulder
        }
        ret.addAll(moveTile(playerPos, dir)); // then register the player's move
        ret.addAll(makeFall()); // then at last, register the tiles that fell
        updateState();
        return ret;
    }

    /**
     * Undoes a move represented by all positions affected.
     *
     * @param oldPositions    all the Moves originally present
     * @param oldDiamondCount the original diamond count
     * @param moveDir         the direction of the movement to undo
     * @param oldState        the original state of play
     */
    public void undoMove(Stack<Move> oldPositions, int oldDiamondCount, Direction moveDir, LevelState oldState) {
        this.setDiamondCount(oldDiamondCount); // we put the old diamond count back
        this.changePlayerPos(moveDir.getOpposite()); // we move the player to his original position
        if (oldDiamondCount < minimumDiamonds) {
            ((Exit) getTile(exitPos)).unReveal();
        }
        this.state = oldState;
        while (!oldPositions.isEmpty()) {
            Move move = oldPositions.pop();
            this.setTile(move.tile(), move.position()); // we put everything back in its place
            if (move.tile().canFall()) {
                // and we update the tile's pos if necessary
                ((FallingTile) move.tile()).updatePosition(move.position());
            }
        }

        this.updateState(); // should be unnecessary but hasn't caused me any bothers yet
    }

    private void updateState() {
        // The player cannot be on the exit if it hasn't been revealed.
        if (playerPos.equals(exitPos) && diamondCount >= minimumDiamonds) {
            state = LevelState.WON;
            return;
        }
        for (var line : map) {
            for (Tile t : line) {
                if (t instanceof Player) {
                    // prevent the instant rollback of REVEAL -> PLAYING
                    state = state == LevelState.REVEAL ? state : LevelState.PLAYING;
                    return;
                }
            }
        }

        // the game is lost when the player disappears (he's under a boulder D: )
        state = LevelState.CRUSHED;
    }

    /**
     * Returns the tile at one position removed from a given position in a given direction
     *
     * @param pos the starting position
     * @param dir the direction to move once towards
     * @return the tile at the end position
     */
    public Tile getTile(Position pos, Direction dir) {
        return map[pos.getY() + dir.getDy()][pos.getX() + dir.getDx()];
    }

    /**
     * Returns the tile at a given position
     *
     * @param pos the position of the tile
     * @return the tile at pos
     */
    public Tile getTile(Position pos) {
        return map[pos.getY()][pos.getX()];
    }

    private void setTile(Tile toSet, Position pos) {
        map[pos.getY()][pos.getX()] = toSet;
    }

    /**
     * Moves a tile at a given position once in a given direction.
     *
     * @param pos the position of the tile to move
     * @param dir the direction towards which the tile will be moved
     * @return the original tiles at the affected position
     */
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
            ((FallingTile) t).updatePosition(dir); // if the moved tile was a FallingTile, keep track of its position
        }
        if (pos.equals(playerPos)) {
            playerPos.move(dir);
        }

        return ret;
    }

    /**
     * Makes all FallingTiles present fall.
     * <p>
     * A FallingTile just above the player will not fall as the player most likely just moved under it.
     *
     * @return the original tiles at the affected positions
     */
    private Stack<Move> makeFall() {
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

    /**
     * Increments diamondCount.
     * <p>
     * If the minimum amount of diamonds necessary to open the exit is reached, said Exit will be revealed
     * (the player can now walk on it).
     */
    public void collectDiamond() {
        diamondCount++;
        if (diamondCount == minimumDiamonds) {
            ((Exit) getTile(exitPos)).reveal();
            state = LevelState.REVEAL;
        }
    }

    /**
     * Getter for minimumDiamonds.
     *
     * @return minimumDiamonds
     */
    public int getMinimumDiamonds() {
        return minimumDiamonds;
    }

    /**
     * Getter for diamondCount.
     *
     * @return diamondCount
     */
    public int getDiamondCount() {
        return diamondCount;
    }

    private void setDiamondCount(int diamondCount) {
        if (diamondCount > this.diamondCount) {
            throw new IllegalStateException("Suspicious setter usage.");
        }
        this.diamondCount = diamondCount;
    }

    /**
     * Returns the number of diamonds remaining on this level/
     *
     * @return remainingDiamonds
     */
    public int getRemainingDiamonds() {
        return totalDiamonds - diamondCount;
    }

    private void changePlayerPos(Direction dir) {
        playerPos.move(dir);
    }

    /**
     * Getter for lvlNumber.
     *
     * @return lvlNumber
     */
    public int getLvlNumber() {
        return lvlNumber;
    }

    /**
     * Getter for state.
     *
     * @return state
     */
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

    /**
     * Getter for playerPos.
     *
     * @return defensive copy of playerPos
     */
    public Position getPlayerPos() {
        return new Position(playerPos);
    }
}
