package model;

import model.tiles.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    @Test
    void move_BadDirection() {
        Level l = new Level(0);
        assertThrows(IllegalArgumentException.class, () -> l.move(Direction.DOWN_RIGHT));
    }

    @Test
    void undoMove_SimpleMove() {
        Level l = new Level(0);
        assertInstanceOf(Player.class, l.getTile(new Position(3, 2)));
        assertInstanceOf(Soil.class, l.getTile(new Position(3, 3)));

        var originalState = l.getState();
        var originalDiamCnt = l.getDiamondCount();
        var original = l.move(Direction.DOWN);

        assertEquals(2, original.size());
        assertInstanceOf(Player.class, l.getTile(new Position(3, 3)));
        assertInstanceOf(EmptyTile.class, l.getTile(new Position(3, 2)));

        l.undoMove(original, originalDiamCnt, Direction.DOWN, originalState);
        assertInstanceOf(Player.class, l.getTile(new Position(3, 2)));
        assertInstanceOf(Soil.class, l.getTile(new Position(3, 3)));
    }

    @Test
    void undoMove_CollectDiamond() {
        Level l = new Level(1);
        l.move(Direction.DOWN);

        assertInstanceOf(Player.class, l.getTile(new Position(1, 2)));
        assertInstanceOf(Diamond.class, l.getTile(new Position(2, 2)));
        assertEquals(0, l.getDiamondCount());
        assertEquals(LevelState.PLAYING, l.getState());

        var originalState = l.getState();
        var originalDiamCnt = l.getDiamondCount();
        var original = l.move(Direction.RIGHT);

        assertEquals(2, original.size());
        assertInstanceOf(Player.class, l.getTile(new Position(2, 2)));
        assertInstanceOf(EmptyTile.class, l.getTile(new Position(1, 2)));
        assertEquals(LevelState.REVEAL, l.getState());
        assertEquals(1, l.getDiamondCount());

        l.undoMove(original, originalDiamCnt, Direction.DOWN, originalState);
        assertInstanceOf(Player.class, l.getTile(new Position(1, 2)));
        assertInstanceOf(Diamond.class, l.getTile(new Position(2, 2)));
        assertEquals(0, l.getDiamondCount());
        assertEquals(LevelState.PLAYING, l.getState());
    }

    @Test
    void undoMove_Fall() {
        Level l = new Level(0);
        l.move(Direction.DOWN);
        l.move(Direction.RIGHT);
        assertInstanceOf(Player.class, l.getTile(new Position(4, 3)));
        assertInstanceOf(Boulder.class, l.getTile(new Position(4, 2)));
        assertInstanceOf(EmptyTile.class, l.getTile(new Position(4, 4)));
        var original = l.move(Direction.LEFT);
        assertInstanceOf(Player.class, l.getTile(new Position(3, 3)));
        assertInstanceOf(Boulder.class, l.getTile(new Position(4, 4)));
        assertInstanceOf(EmptyTile.class, l.getTile(new Position(4, 2)));
        l.undoMove(original, l.getDiamondCount(), Direction.LEFT, LevelState.PLAYING);
        assertInstanceOf(Player.class, l.getTile(new Position(4, 3)));
        assertInstanceOf(Boulder.class, l.getTile(new Position(4, 2)));
        assertInstanceOf(EmptyTile.class, l.getTile(new Position(4, 4)));
    }

    /*
    I don't bother testing the other methods as their behaviour has been thoroughly tested
    in FacadeTest. I only test here behaviour that isn't handled in that
    larger Game class.

    I don't bother to check the content of the return value of move either as... honestly it
    looks like a pain to do, and is only ever useful for the undo operations,
    which have been tested.
     */
}