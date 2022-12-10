package model;

/**
 * A Direction is a vector that gives the amount of displacement in both x and y-axis a movement of one unit
 * in that direction would cause.
 * <p>
 * N.B. : This class uses the Position x and y, meaning that x is the horizontal axis, and y is vertical.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(1, 1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    /**
     * Returns the 2 components of a Direction.
     * <p>
     * A Direction's components are its horizontal and vertical constituents. For example, DOWN_LEFT is composed
     * of both DOWN and LEFT. For straight directions, the components will be itself.
     *
     * @return a Direction[2], the components (twice "this" if the Direction is straight)
     */
    public Direction[] getComponents() {
        switch (this) {
            case UP_LEFT -> {
                return new Direction[]{UP, LEFT};
            }
            case DOWN_LEFT -> {
                return new Direction[]{DOWN, LEFT};
            }
            case UP_RIGHT -> {
                return new Direction[]{UP, RIGHT};
            }
            case DOWN_RIGHT -> {
                return new Direction[]{DOWN, RIGHT};
            }
            default -> {
                return new Direction[]{this, this};
            }
        }
    }

    /**
     * Returns the direction facing towards the opposite of this Direction.
     * <p>
     * For example, DOWN.getOpposite() would return UP.
     *
     * @return the opposite of this direction
     */
    public Direction getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP_LEFT -> DOWN_RIGHT;
            case UP_RIGHT -> DOWN_LEFT;
            case DOWN_LEFT -> UP_RIGHT;
            case DOWN_RIGHT -> UP_LEFT;
        };
    }
}
