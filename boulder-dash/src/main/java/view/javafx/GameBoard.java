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
    // various constants
    private static final int BOARD_LENGTH = 30;
    private static final int BOARD_HEIGHT = 16;
    private static final int TILE_SIZE = 16;
    // model
    private final Facade game;
    private final Image SPRITE_SHEET = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites.png")));
    // layout
    private final GridPane board = new GridPane();
    // current upper left corner of the viewport rectangle
    private int viewportX = 0;
    private int viewportY = 0;

    public GameBoard(Facade game) {
        this.game = game;
        game.registerObserver(this);
        setupScreen();
    }

    private void setupScreen() {
        this.getChildren().addAll(board, new MessageBox(game));
        this.setPrefSize(BOARD_LENGTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);
    }

    @Override
    public void update() {
        board.getChildren().clear(); // prevent thousands of images being overlaid with each move
        String[] map = game.toString().split("\n");
        changeViewPort(map[0].length(), map.length);

        for (int i = viewportY; i < viewportY + BOARD_HEIGHT; i++) {
            for (int j = viewportX; j < viewportX + BOARD_LENGTH; j++) {
                char tile;
                try {
                    tile = map[i].charAt(j);
                } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                    tile = 'w'; // if board is smaller than the screen, we display walls
                }
                this.board.add(charToTile(tile), j - viewportX, i - viewportY);
            }
        }
    }

    private void changeViewPort(int mapL, int mapH) {
        int x = game.getPlayerPos().getX();
        int y = game.getPlayerPos().getY();

        int SCROLL_LIMIT_RIGHT = 5;
        int SCROLL_LIMIT_LEFT = 7;

        if (x + SCROLL_LIMIT_RIGHT > viewportX + BOARD_LENGTH) {
            viewportX = Math.min(viewportX + BOARD_LENGTH - SCROLL_LIMIT_RIGHT, mapL - BOARD_LENGTH);
            // we move so that the player is SCROLL_LIMIT_RIGHT squares from the left OR so that the right edge of the screen
            // is the right edge of the map, whichever is nearer
        } else if (x - SCROLL_LIMIT_LEFT < viewportX) {
            viewportX = Math.max(0, viewportX - BOARD_LENGTH + SCROLL_LIMIT_LEFT);
            // same logic but SCROLL_LIMIT_LEFT
        }
        if (y + 1 >= viewportY + BOARD_HEIGHT) {
            viewportY = Math.min(y, mapH - BOARD_HEIGHT);
        } else if (y - 1 < viewportY) {
            viewportY = Math.max(0, y - BOARD_HEIGHT);
        }
    }

    /*
    This uses the string representation of the model, originally meant for the ConsoleView.
    This creates coupling between the two views and is very prone to errors, as a typo would
    dramatically change the graphical output.

    That said, doing it by looking at the Tile's type themselves would mean 1. opening the map
    to a view and 2. comparing with classes defined in the model, which would break the Façade
    design pattern.

    This version is undesirable, but there's no time to change it.

    And I got green-lit by Mr. Nicodème.
     */
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

        // those two next one are cool if the images need to be resized on window resize
        // it looked pretty bad when I tried though, but eh won't hurt to keep them
        ret.setPreserveRatio(true);
        ret.setSmooth(false);
        return ret;
    }
}
