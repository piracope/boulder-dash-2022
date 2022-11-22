package model;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(Direction dir) {
        this.x += dir.getDx();
        this.y += dir.getDy();
    }
}
