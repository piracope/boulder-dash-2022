package view.javafx;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.Direction;
import model.Facade;

public class MoveHandler implements EventHandler<KeyEvent> {

    private final Facade game;

    public MoveHandler(Facade game) {
        this.game = game;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        Direction dir = null;
        switch (keyEvent.getCode()) {
            case LEFT -> dir = Direction.LEFT;
            case DOWN -> dir = Direction.DOWN;
            case UP -> dir = Direction.UP;
            case RIGHT -> dir = Direction.RIGHT;
        }

        if (dir != null) {
            game.move(dir);
        }
    }
}
