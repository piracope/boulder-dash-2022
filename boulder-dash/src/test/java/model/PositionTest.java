package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void move() {
        Position pos = new Position(0, 0);
        pos.move(Direction.DOWN_LEFT);
        assertEquals(new Position(-1, 1), pos);
    }

    @Test
    void addDirection() {
        assertEquals(new Position(1, 1), new Position(0, 0).addDirection(Direction.DOWN_RIGHT));
    }
}