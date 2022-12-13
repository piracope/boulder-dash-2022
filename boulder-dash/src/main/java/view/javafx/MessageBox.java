package view.javafx;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Facade;
import model.LevelState;
import util.Observer;

public class MessageBox extends BorderPane implements Observer {
    private final Facade game;
    private final Text text = new Text("");

    public MessageBox(Facade game) {
        this.game = game;
        this.game.registerObserver(this);

        this.text.setTextAlignment(TextAlignment.CENTER);
        this.setCenter(text);
    }

    @Override
    public void update() {
        String textToSet = "";

        switch (game.getLevelState()) {
            case WON -> {
                textToSet = "You Won !";
                textToSet += "\n" +
                        (game.getLvlNumber() + 1 >= game.getNbOfLevels()
                                ? "Press Enter to go back to the main menu" // if there are no more levels to play
                                : "Press Enter to go to the next level");
            }
            case CRUSHED -> {
                textToSet = "You were crushed...";
                if (!game.isGameOver()) {
                    textToSet += "\nPress Enter to start again.";
                }
            }
            case INVALID_MOVE -> textToSet = "Invalid Move !";
            case REVEAL -> textToSet = "The exit just opened...";
        }

        if (game.isGameOver() && game.getLevelState() != LevelState.WON) {
            textToSet += "\nGame Over\nPress Enter to go back to the main menu";
        }

        text.setText(textToSet);
    }
}
