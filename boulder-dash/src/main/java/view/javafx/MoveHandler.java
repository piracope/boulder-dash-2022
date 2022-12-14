package view.javafx;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
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

        if (keyEvent.isControlDown()) {
            if (keyEvent.getCode() == KeyCode.Z) { // CTRL + Z -> undo
                game.undo();
            } else if (keyEvent.getCode() == KeyCode.Y) { // CTRL + Y -> undo
                game.redo();
            }
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) { // Esc -> abandon
            game.abandon();
        } else {
            switch (keyEvent.getCode()) { // arrow keys -> move
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
}
