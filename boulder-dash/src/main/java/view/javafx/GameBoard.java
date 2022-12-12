package view.javafx;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Facade;
import util.Observer;

import java.util.Objects;

public class GameBoard extends VBox implements Observer {
    private final Facade game;

    private final int BOARD_LENGTH = 30;
    private final int BOARD_HEIGHT = 16;

    private final int TILE_SIZE = 16;

    private final Image SPRITE_SHEET = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites.png")));


    private final GridPane board = new GridPane();

    public GameBoard(Facade game) {
        this.game = game;
        game.registerObserver(this);

        this.getChildren().addAll(board, new MessageBox(game));
    }

    @Override
    public void update() {
        String[] map = game.toString().split("\n");
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                char tile;
                try {
                    tile = map[i].charAt(j);
                } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                    tile = 'w';
                }

                this.board.add(charToTile(tile), j, i);
            }
        }
    }

    private ImageView charToTile(char c) {
        ImageView ret = new ImageView(SPRITE_SHEET);
        int x;
        int y;
        switch (c) {
            case '.' -> {
                x = 4;
                y = 3;
            }
            case 'w' -> {
                x = 2;
                y = 3;
            }
            case 'X' -> {
                x = 0;
                y = 0;
            }
            case 'P' -> {
                x = 0;
                y = 3;
            }
            case 'r' -> {
                x = 5;
                y = 3;
            }
            case 'd' -> {
                x = 0;
                y = 4;
            }

            default -> {
                x = 6;
                y = 3;
            }
        }
        ret.setViewport(new Rectangle2D(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE));
        return ret;
    }
}
