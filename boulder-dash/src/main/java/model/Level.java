package model;

import model.tiles.Tile;

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
    private final int lvlNumber;
    private final int minimumDiamonds;
    private Position playerPos;
    private int diamondCount = 0;
    private boolean isWon = false;

    public Level(int lvlNumber) {
        // TODO : arbitrary -> file specific values
        this.map = new Tile[4][4];
        this.minimumDiamonds = 15;
        this.lvlNumber = lvlNumber;
    }

    /**
     * Moves the player in a certain direction.
     * @param dir the direction towards which the player will move.
     */
    void move(Direction dir){
        playerPos.move(dir);
    }
}
