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
            case WON -> textToSet = "You Won !";
            case CRUSHED -> textToSet = "You were crushed...\nPress Enter to start again.";
            case INVALID_MOVE -> textToSet = "Invalid Move !";
        }

        if (game.getLevelState() == LevelState.WON) {
            textToSet += "\n" +
                    (game.getLvlNumber() + 1 >= game.getNbOfLevels()
                            ? "Press Enter to go back to the main menu"
                            : "Press Enter to go to the next level");
        } else if (game.isGameOver()) {
            textToSet += "\nGame Over\nPress Enter to go back to the main menu";
        }

        text.setText(textToSet);
    }
}
