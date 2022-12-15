package model.tiles;

import model.Direction;
import model.Level;
import model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiamondTest {

    @Test
    void onMove() {
        Level lvl = new Level(1);
        lvl.getTile(new Position(2, 2)).onMove(Direction.DOWN);
        assertEquals(1, lvl.getDiamondCount());
    }
}