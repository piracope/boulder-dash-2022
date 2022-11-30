package model;

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

    public Direction[] getComponents() {
        switch(this) {
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
}
