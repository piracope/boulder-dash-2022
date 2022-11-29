package model;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(Direction dir) {
        Position newPos = addDirection(dir);
        this.x = newPos.x;
        this.y = newPos.y;
    }

    public Position addDirection(Direction dir) {
        return new Position(this.x + dir.getDx(), this.y + dir.getDy());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
