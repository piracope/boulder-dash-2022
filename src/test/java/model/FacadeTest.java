package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {

    private Facade game;

    @BeforeEach
    void setup() {
        game = new Game();
    }

    @Test
    void start() {
        game.start(1);
        assertEquals(LevelState.PLAYING, game.getLevelState());
        assertEquals(1, game.getLvlNumber());
        assertEquals(0, game.getDiamondCount());
        assertEquals(1, game.getRemainingDiamonds());
        assertEquals(new Position(1, 1), game.getPlayerPos());
    }

    @Test
    void start_FirstTime() {
        start();
        assertEquals(3, game.getNbOfLives());
    }

    @Test
    void start_AfterGameOver() {
        start();
        game.abandon();
        game.start(0);
        assertEquals(3, game.getNbOfLives());
    }

    @Test
    void start_AfterLosingALife() {
        game.start(0);
        game.move(Direction.DOWN);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        game.start(0);
        assertEquals(2, game.getNbOfLives());
    }

    @Test
    void start_ChangingLevel() {
        start_AfterLosingALife();
        game.start(1);
        assertEquals(3, game.getNbOfLives());
    }

    @Test
    void start_NoHistoryAfterStart() {
        start_AfterLosingALife(); // -> makes some moves then restarts
        assertThrows(EmptyStackException.class, () -> game.undo());
        assertThrows(EmptyStackException.class, () -> game.redo());
    }

    @Test
    void isGameOver_NoLives() {
        for (int i = 0; i < 3; i++) {
            game.start(0);
            game.move(Direction.DOWN);
            game.move(Direction.RIGHT);
            game.move(Direction.DOWN);
        }

        assertTrue(game.isGameOver());
    }

    @Test
    void isGameOver_Won() {
        game.start(1);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        game.move(Direction.RIGHT);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);

        assertTrue(game.isGameOver());
    }

    @Test
    void getLevelState_PlayingAfterStart() {
        game.start(0);
        assertEquals(LevelState.PLAYING, game.getLevelState());
    }

    @Test
    void getLevelState_Crushed() {
        game.start(0);
        game.move(Direction.DOWN);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertEquals(LevelState.CRUSHED, game.getLevelState());
    }

    @Test
    void getLevelState_Won() {
        isGameOver_Won();
        assertEquals(LevelState.WON, game.getLevelState());
    }

    @Test
    void getLevelState_InvalidMove() {
        game.start(1);
        game.move(Direction.LEFT);
        assertEquals(LevelState.INVALID_MOVE, game.getLevelState());
    }

    @Test
    void getLevelState_Reveal() {
        game.start(1);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertEquals(LevelState.REVEAL, game.getLevelState());
    }

    @Test
    void getMinimumDiamonds() {
        game.start(0);
        assertEquals(12, game.getMinimumDiamonds());
        game.start(1);
        assertEquals(1, game.getMinimumDiamonds());
    }

    @Test
    void getDiamondCount() {
        game.start(1);
        assertEquals(0, game.getDiamondCount());
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertEquals(1, game.getDiamondCount());
    }

    @Test
    void getRemainingDiamonds() {
        game.start(1);
        assertEquals(1, game.getRemainingDiamonds());
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertEquals(0, game.getRemainingDiamonds());
    }

    @Test
    void getLvlNumber() {
        game.start(0);
        assertEquals(0, game.getLvlNumber());
        game.start(1);
        assertEquals(1, game.getLvlNumber());
    }

    @Test
    void getNbOfLives() {
        game.start(0);
        assertEquals(3, game.getNbOfLives());
    }

    @Test
    void getNbOfLives_LifeLost() {
        getNbOfLives();
        game.move(Direction.DOWN);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertEquals(2, game.getNbOfLives());
    }

    @Test
    void getNbOfLives_abandon() {
        getNbOfLives();
        game.abandon();
        assertEquals(0, game.getNbOfLives());
    }

    @Test
    void getPlayerPos() {
        game.start(1);
        assertEquals(new Position(1, 1), game.getPlayerPos());
        game.move(Direction.RIGHT);
        assertEquals(new Position(2, 1), game.getPlayerPos());
    }

    @Test
    void move_InvalidMove() {
        game.start(1);
        game.move(Direction.LEFT);
        assertEquals(LevelState.INVALID_MOVE, game.getLevelState());
        assertThrows(EmptyStackException.class, () -> game.undo());
    }

    @Test
    void move_GameOver() {
        game.start(1);
        game.abandon();
        assertThrows(IllegalStateException.class, () -> game.move(Direction.RIGHT));
    }

    @Test
    void move_Won() {
        game.start(1);
        game.move(Direction.DOWN);
        game.move(Direction.RIGHT);
        game.move(Direction.RIGHT);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertThrows(IllegalStateException.class, () -> game.move(Direction.UP));
    }

    @Test
    void move_Crushed() {
        game.start(0);
        game.move(Direction.DOWN);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertThrows(IllegalStateException.class, () -> game.move(Direction.RIGHT));
    }

    @Test
    void undo() {
        game.start(1);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertEquals(1, game.getDiamondCount());
        assertEquals(0, game.getRemainingDiamonds());
        assertEquals(LevelState.REVEAL, game.getLevelState());

        game.undo();
        assertEquals(0, game.getDiamondCount());
        assertEquals(1, game.getRemainingDiamonds());
        assertEquals(LevelState.PLAYING, game.getLevelState());
    }

    @Test
    void undo_GameOver_Won() {
        game.start(1);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        game.move(Direction.RIGHT);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN); // -> won
        assertThrows(IllegalStateException.class, () -> game.undo());
    }

    @Test
    void undo_GameOver() {
        game.start(1);
        game.move(Direction.RIGHT);
        game.abandon();
        assertThrows(IllegalStateException.class, () -> game.undo());
    }

    @Test
    void undo_Crushed() {
        game.start(0);
        game.move(Direction.DOWN);
        game.move(Direction.RIGHT);
        game.move(Direction.DOWN);
        assertThrows(IllegalStateException.class, () -> game.undo());
    }

    @Test
    void redo() {
        undo();
        game.redo();
        assertEquals(1, game.getDiamondCount());
        assertEquals(0, game.getRemainingDiamonds());
        assertEquals(LevelState.REVEAL, game.getLevelState());
    }

    @Test
    void redo_AfterNewMove() {
        undo();
        game.move(Direction.RIGHT);
        assertThrows(EmptyStackException.class, () -> game.redo());

    }

    @Test
    void abandon() {
        game.start(0);
        game.move(Direction.DOWN);
        game.abandon();
        assertTrue(game.isGameOver());
        assertEquals(0, game.getNbOfLives());
        assertThrows(IllegalStateException.class, () -> game.move(Direction.RIGHT));
        assertThrows(IllegalStateException.class, () -> game.undo());
    }
}