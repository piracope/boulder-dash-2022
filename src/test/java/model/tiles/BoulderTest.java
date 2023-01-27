package model.tiles;

import model.Direction;
import model.Level;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoulderTest {

    private Level lvl;

    @BeforeEach
    void setup() {
        lvl = new Level(0);
    }

    @Test
    void canMoveIn_Cant() {
        assertFalse(lvl.getTile(new Position(4, 2)).canMoveIn(Direction.RIGHT));
    }

    @Test
    void canMoveIn_CantPlayer() {
        assertFalse(lvl.getTile(new Position(4, 2)).canMoveIn(Direction.LEFT));
    }

    @Test
    void canMoveIn_Can() {
        lvl.move(Direction.DOWN);
        assertTrue(lvl.getTile(new Position(4, 2)).canMoveIn(Direction.LEFT));
    }

    @Test
    void onMove_Push() {
        lvl.move(Direction.DOWN);
        assertEquals(2, lvl.getTile(new Position(4, 2)).onMove(Direction.LEFT).size());
    }

    @Test
    void onMove_CantPush() {
        assertThrows(IllegalArgumentException.class,
                () -> lvl.getTile(new Position(4, 2)).onMove(Direction.RIGHT)
        );
    }
}