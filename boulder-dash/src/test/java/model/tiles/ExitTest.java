package model.tiles;

import model.Direction;
import model.Level;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExitTest {

    private Level lvl;

    @BeforeEach
    void setup() {
        lvl = new Level(1);
    }

    @Test
    void reveal() {
        lvl.move(Direction.RIGHT);
        lvl.move(Direction.DOWN);
        Exit exit = (Exit) lvl.getTile(new Position(4, 3));
        assertTrue(exit.canMoveIn(Direction.DOWN));
    }

    @Test
    void unReveal() {
    }
}