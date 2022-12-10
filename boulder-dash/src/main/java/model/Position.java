package model;

import java.util.Objects;

/**
 * The Position in the game world.
 * <p>
 * This class assumes that x is the horizontal axis, and y the vertical, starting at (0,0) at the upper-left corner.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Creates a new Position with the given coordinates
     *
     * @param x the position on the x-axis
     * @param y the position on the y-axis
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor.
     *
     * @param p another position
     */
    public Position(Position p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Moves this Position one unit in a given Direction.
     *
     * @param dir the direction to move this position towards
     */
    public void move(Direction dir) {
        Position newPos = addDirection(dir);
        this.x = newPos.x;
        this.y = newPos.y;
    }

    /**
     * Returns a new Position, which is one unit away from this Position at a certain Direction.
     *
     * @param dir the direction to get the new Position
     * @return the position one move away from this one in the given direction
     */
    public Position addDirection(Direction dir) {
        return new Position(this.x + dir.getDx(), this.y + dir.getDy());
    }

    /**
     * Getter for x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ')';
    }
}
