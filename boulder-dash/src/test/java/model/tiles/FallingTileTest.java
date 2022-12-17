package model.tiles;

import model.Direction;
import model.Level;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FallingTileTest {
    private Level lvl;

    @BeforeEach
    void setup() {
        lvl = new Level(0);
    }

    @Test
    void fall_SoilUnderneath_NoFall() {
        FallingTile rock = (FallingTile) lvl.getTile(new Position(2, 2));
        assertTrue(rock.fall().isEmpty());
    }

    @Test
    void fall_EmptyUnderneath_Fall() {
        FallingTile rock = (FallingTile) lvl.getTile(new Position(2, 2));
        lvl.move(Direction.DOWN);
        lvl.move(Direction.LEFT);
        lvl.move(Direction.RIGHT);
        assertNotEquals(rock, lvl.getTile(new Position(2, 2)));
        assertEquals(rock, lvl.getTile(new Position(2, 3)));
    }

    @Test
    void fall_LongFall_Fall() {
        FallingTile rock = (FallingTile) lvl.getTile(new Position(4, 2));
        lvl.move(Direction.DOWN);
        lvl.move(Direction.RIGHT);
        lvl.move(Direction.RIGHT);

        assertNotEquals(rock, lvl.getTile(new Position(4, 2)));
        assertNotEquals(rock, lvl.getTile(new Position(4, 3)));
        assertEquals(rock, lvl.getTile(new Position(4, 4)));
    }

    @Test
    void fall_EmptyLowerRight_Fall() {
        FallingTile rock = (FallingTile) lvl.getTile(new Position(4, 2));
        lvl.move(Direction.DOWN);
        lvl.move(Direction.RIGHT);
        lvl.move(Direction.RIGHT);
        lvl.move(Direction.DOWN);
        lvl.move(Direction.DOWN);
        assertNotEquals(rock, lvl.getTile(new Position(4, 4)));
        assertEquals(rock, lvl.getTile(new Position(5, 5)));
    }

    @Test
    void fall_EmptyLowerLeft_Fall() {
        lvl.move(Direction.DOWN);
        lvl.move(Direction.DOWN);
        for (int i = 0; i < 10; i++) {
            lvl.move(Direction.RIGHT);
        }

        Position oldPos = lvl.getPlayerPos().addDirection(Direction.RIGHT);
        FallingTile rock = (FallingTile) lvl.getTile(oldPos);
        lvl.move(Direction.DOWN);
        assertNotEquals(rock, lvl.getTile(oldPos));
        assertEquals(rock, lvl.getTile(oldPos.addDirection(Direction.DOWN_LEFT)));
    }

    @Test
    void fall_PushingBoulderOverHole_Fall() {
        FallingTile rock = (FallingTile) lvl.getTile(new Position(2, 2));
        lvl.move(Direction.DOWN);
        lvl.move(Direction.LEFT);
        lvl.move(Direction.LEFT);
        assertEquals(rock, lvl.getTile(new Position(2, 3)));
        lvl.move(Direction.RIGHT);
        assertNotEquals(rock, lvl.getTile(new Position(3, 3)));
        assertEquals(rock, lvl.getTile(new Position(3, 4)));
    }
}